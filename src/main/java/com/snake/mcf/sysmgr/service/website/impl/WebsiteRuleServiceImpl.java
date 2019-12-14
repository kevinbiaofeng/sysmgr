package com.snake.mcf.sysmgr.service.website.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.enums.cash.TreasureTypeEnum;
import com.snake.mcf.sysmgr.mapper.website.WebsiteRuleMapper;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.Gamerule;
import com.snake.mcf.sysmgr.repertory.entity.Recordtreasureserial;
import com.snake.mcf.sysmgr.repertory.entity.dto.GameruleDTO;
import com.snake.mcf.sysmgr.repertory.mapper.GameruleMapper;
import com.snake.mcf.sysmgr.service.website.WebsiteRuleService;
import com.snake.mcf.sysmgr.service.website.WebsiteStandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WebsiteRuleServiceImpl
 * @Author 大帅
 * @Date 2019/7/8 17:36
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WebsiteRuleServiceImpl extends BaseServiceImpl implements WebsiteRuleService {

    private static final String RULE_IMG_PATH = "RuleIoc";

    @Autowired
    private WebsiteRuleMapper websiteRuleMapper;

    @Autowired
    private GameruleMapper gameruleMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private WebsiteStandService websiteStandService;

    @Override
    public PageResult<GameruleDTO> queryGameRuleWithPage(EasyPageFilter pageFilter, GameruleDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<GameruleDTO> pageResult = this.queryGameRuleWithPage1(pageFilter,dto);
        Configinfo configInfo = websiteStandService.queryConfigInfoByConfigKey("WebSiteConfig");
        String field5 = configInfo.getField5();
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<GameruleDTO> result = (EasyPageResult<GameruleDTO>) pageResult;
            List<GameruleDTO> rows = result.getRows();

            rows.forEach( (rd) -> {
                //返回完整路径
                String ruleimg = rd.getRuleimg();
                if (null != ruleimg) {
                    rd.setRuleimg(field5 + ruleimg);
                }

                //禁用状态
                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                //图片类型
                Integer type = rd.getType();
                if(type != null){
                    String typeDesc = DictionaryConstant.getTypeDesc(type);
                    rd.setTypeDesc(typeDesc);
                }


            });
        }
        return pageResult;
    }
    
    @Override
    public Map<String, Object> queryGameRuleList(String _userId, Integer apiVersion) {
    	Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
        Example example = new Example(Gamerule.class);
        Example.Criteria criteria = example.createCriteria();
        example.selectProperties("kindid", "kindname", "kindicon", "kindintro", "ruleimg", "type", "logid");
        Configinfo configInfo = websiteStandService.queryConfigInfoByConfigKey("WebSiteConfig");
        String field2 = configInfo.getField2();
//        if(_userId != null && StringUtils.isNotEmpty(_userId)) {
//        	criteria.andEqualTo("userid", _userId);
//        }else {
//        	criteria.andIsNull("userid");
//        }
        criteria.andEqualTo("nullity", 0);
        example.setOrderByClause("SortID ASC,KindID DESC");
        List<Gamerule> list = gameruleMapper.selectByExample(example);
    	List<Map<String, Object>> dataList = null;
    	if(list != null && list.size() > 0) {
    		dataList = new ArrayList<Map<String,Object>>();
    		Map<String, Object> dataMap = null;
    		for (Gamerule game : list) {
    			dataMap = new HashMap<String, Object>();
    			dataMap.put("kindId", game.getKindid());
    			dataMap.put("kindName", game.getKindname());
    			dataMap.put("content", game.getKindintro());
    			dataMap.put("ruleImg", field2 + game.getRuleimg());
				dataMap.put("type", game.getType());
				dataMap.put("logId", game.getLogid());
				dataList.add(dataMap);
			}
    	}
        data.put("ruleList", dataList);
        return data;
    }
    

    private PageResult<GameruleDTO> queryGameRuleWithPage1(EasyPageFilter filter, GameruleDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Gamerule.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer type = dto.getType();
            if(type != null){
                criteria.andEqualTo("type",type);
            }

            Integer kindid = dto.getKindid();
            if(kindid != null){
                criteria.andEqualTo("kindid",kindid);
            }

        }
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()) {
            criteria.andEqualTo("merchant", ShiroUtils.getSessionMerchant());
        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" SortID ASC,KindID DESC ");
        }

        //查询
        List<Gamerule> list = gameruleMapper.selectByExample(example);
        //结果
        PageResult<GameruleDTO> pageResult = easyPageQuery.queryResult(list, GameruleDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public GameruleDTO queryGameRuleById(GameruleDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 id 为空", 1004);
        }
        Gamerule gamerule = gameruleMapper.selectByPrimaryKey(id);
        GameruleDTO record = new GameruleDTO();
        CommonBeans.copyPropertiesIgnoreNull(gamerule,record);
        record.setUploadUrl(record.getRuleimg());
        return record;
    }

    @Override
    public boolean isExistGameRule(GameruleDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer kindid = dto.getKindid();
        if(kindid == null){
            throw new BusinessException("根据id查询,传入对象 kindid 为空", 1004);
        }
        Integer type = dto.getType();
        if(type == null){
            throw new BusinessException("根据id查询,传入对象 type 为空", 1004);
        }
        //根据条件查询
        Example example = new Example(Gamerule.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("kindid",kindid);
        criteria.andEqualTo("type",type);
        List<Gamerule> list = gameruleMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public boolean saveGameRule(GameruleDTO dto) {
            Gamerule record = new Gamerule();
            CommonBeans.copyPropertiesIgnoreNull(dto,record);
            int count = 0;
            if(record.getId() == null){
                //设置日期
                record.setCollectdate(new Date());
                //设置日志ID
                record.setLogid(0);
                //设置有效标识 默认有效
                record.setNullity(0);
                //是否是管理员  不是 需要加一个字段过滤
                if(!ShiroUtils.isAdminSessionUserId()) {
                    record.setMerchant(ShiroUtils.getSessionMerchant());
                }
                count = gameruleMapper.insertSelective(record);
            }else{
                count = gameruleMapper.updateByPrimaryKeySelective(record);
            }
            return count > 0;
    }

    @Override
    public boolean batchNullityUpdate(GameruleDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        String ids = dto.getIds();
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("根据id查询,传入对象 ids 为空", 1004);
        }
        Integer nullity = dto.getNullity();
        if(nullity == null){
            throw new BusinessException("根据id查询,传入对象 nullity 为空", 1004);
        }
        int count = 0 ;
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            Gamerule record = new Gamerule();
            record.setId(Integer.valueOf(id));
            record.setNullity(nullity);
            gameruleMapper.updateByPrimaryKeySelective(record);
            count ++;
        }
        return count > 0;
    }

    @Override
    public boolean removeGameRuleByIds(Integer[] ids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(ids)){
            list = Arrays.asList(ids);
        }

        int count = 0;
        Example example = new Example(Gamerule.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",list);
        gameruleMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
