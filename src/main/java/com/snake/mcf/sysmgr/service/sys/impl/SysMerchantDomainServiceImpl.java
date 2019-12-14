package com.snake.mcf.sysmgr.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.enums.StatusEnum;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchantDomain;
import com.snake.mcf.sysmgr.repertory.entity.TbSysUser;
import com.snake.mcf.sysmgr.repertory.entity.dto.MerchantDomainDTO;
import com.snake.mcf.sysmgr.repertory.mapper.TbMerchantDomainMapper;
import com.snake.mcf.sysmgr.service.sys.SysMerchantDomainService;
import com.snake.mcf.sysmgr.service.sys.SysUserService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SysMerchantDomainServiceImpl extends BaseServiceImpl implements SysMerchantDomainService {

    @Autowired
    private TbMerchantDomainMapper merchantDomainMapper;
    
    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;
    
    @Autowired
    private SysUserService sysUserService;

    @Override
    public TbMerchantDomain getMerchantDomainById(String domainId) {
        log.info("根据主键查询:domainId={}", domainId);
        if(domainId == null) {
            return null;
        }
        TbMerchantDomain domain = merchantDomainMapper.selectByPrimaryKey(domainId);
        if(domain == null) {
            return null;
        }
        return domain;
    }
    
    public List<TbMerchantDomain> getMerchantDomainByMerchantAndType(String merchant, String type){
    	TbSysUser tbSysUser = sysUserService.queryUserByMerchant(merchant);
        Example example = new Example(TbMerchantDomain.class) ;
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", tbSysUser.getId());
        criteria.andEqualTo("type", type); //3代表主域名，只有一个主域名
        criteria.andEqualTo("status", StatusEnum.SUCCESS.getCode()); //正在运行
        return merchantDomainMapper.selectByExample(example);
    }
    
    @Override
    public List<TbMerchantDomain> queryListByExample(EasyPageFilter pageFilter, String userId, String _status, String _type) {
    	log.info("服务端:page={}，rows={}",pageFilter.getPage(), pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
    	easyPageQuery.startPage(pageFilter);
    	
    	Example example = new Example(TbMerchantDomain.class);
        Example.Criteria criteria = example.createCriteria();
    	criteria.andEqualTo("userId", Integer.valueOf(userId));
        
        if(_status != null && StringUtils.isNotEmpty(_status)) {
        	criteria.andEqualTo("status", _status);
        }else {
        	List<Integer> stat = new ArrayList<Integer>();
        	stat.add(StatusEnum.DRAFT.getCode());
        	stat.add(StatusEnum.SUCCESS.getCode());
        	criteria.andIn("status", stat);
        }
        
        if(_type != null && StringUtils.isNotEmpty(_type)) {
        	criteria.andEqualTo("type", _type);
        }
        
        //排序
        example.setOrderByClause(" create_date desc");
        example.setOrderByClause(" update_date desc");
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        
        List<TbMerchantDomain> list = merchantDomainMapper.selectByExample(example);
		return list;
    }
    
    @Override
    public PageResult<MerchantDomainDTO> queryPageListByExample(EasyPageFilter pageFilter, String userId, String _status, String _type) {
        List<TbMerchantDomain> list = this.queryListByExample(pageFilter, userId, _status, _type);
        PageResult<MerchantDomainDTO> pageResult = easyPageQuery.queryResult(list, MerchantDomainDTO.class);
        if(list != null && list.size() > 0) {
        	Set<Long> set = new HashSet<Long>();
            for (TbMerchantDomain tbMerchantDomain : list) {
            	set.add(tbMerchantDomain.getCreateUser());
            	set.add(tbMerchantDomain.getUpdateUser());
    		}
            Map<String, Object> userMap = sysUserService.queryUserMapByIds(new ArrayList<Long>(set));
            
            if(pageResult instanceof EasyPageResult) {
                EasyPageResult<MerchantDomainDTO> result = (EasyPageResult<MerchantDomainDTO>) pageResult;
                List<MerchantDomainDTO> rows = result.getRows();
                rows.forEach(suDto -> {
                    Long createUser = suDto.getCreateUser();
                	if(createUser != null) {
                		suDto.setCreateUserName(userMap.get(String.valueOf(createUser)).toString());
                	}
                	Long updateUser = suDto.getUpdateUser();
                	if(updateUser != null) {
                		suDto.setUpdateUserName(userMap.get(String.valueOf(updateUser)).toString());
                	}

                });
            }
            
            log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
            return pageResult;
        }
		return pageResult;
    }

	@Override
	public boolean saveMerchantDomain(TbMerchantDomain merchantDomain) {
		String userId = ShiroUtils.getSessionUserId();
        merchantDomain.setUpdateUser(Long.valueOf(userId));
        merchantDomain.setUpdateDate(new Date());
        Long id = merchantDomain.getId();
        //判断该用户是否已绑定主域名，主域名只能有一个
        int count = 0;
        if(id == null) { //新增
//        	if (merchantDomain.getType() == 3) {
//                Example example = new Example(TbMerchantDomain.class);
//                Example.Criteria criteria = example.createCriteria();
//                criteria.andEqualTo("type", merchantDomain.getType());
//                criteria.andEqualTo("userId", merchantDomain.getUserId());
//                List<TbMerchantDomain> tbMerchantDomains = merchantDomainMapper.selectByExample(example);
//                if (tbMerchantDomains.size() > 0) {
//                    throw new BusinessException("主域名已存在，若想新增/编辑主域名，请修改其他域名配置", 1004);
//                }
//            }
        	
        	merchantDomain.setUserId(merchantDomain.getUserId());
        	merchantDomain.setCreateUser(Long.valueOf(userId));
            merchantDomain.setCreateDate(new Date());
            count = merchantDomainMapper.insert(merchantDomain);
        }else { //修改
        	count = merchantDomainMapper.updateByPrimaryKeySelective(merchantDomain);
        }
        if(count == 0) {
            throw new BusinessException("服务异常，请联系管理员", 1004);
        }
		return count > 0;
	}
	
	public boolean deleteMerchantDomainById(String id) {
		TbMerchantDomain merchantDomain = new TbMerchantDomain();
		merchantDomain.setId(Long.valueOf(id));
		merchantDomain.setStatus(StatusEnum.DELETE.getCode());
		int count = merchantDomainMapper.updateByPrimaryKeySelective(merchantDomain);
		if(count == 0) {
            throw new BusinessException("服务异常，请联系管理员", 1004);
        }
		return count > 0;
	}
}
