package com.snake.mcf.sysmgr.service.uphold.impl;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.uphold.UpholdSpreadMapper;
import com.snake.mcf.sysmgr.repertory.entity.Spreadtypeproperty;
import com.snake.mcf.sysmgr.repertory.entity.dto.SpreadtypepropertyDTO;
import com.snake.mcf.sysmgr.repertory.mapper.SpreadtypepropertyMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdSpreadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @ClassName UpholdSpreadServiceImpl
 * @Author 大帅
 * @Date 2019/7/6 15:37
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdSpreadServiceImpl extends BaseServiceImpl implements UpholdSpreadService {

    @Autowired
    private SpreadtypepropertyMapper spreadtypepropertyMapper;

    @Autowired
    private UpholdSpreadMapper upholdSpreadMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<SpreadtypepropertyDTO> querySpreadTypeWithPage(EasyPageFilter pageFilter, SpreadtypepropertyDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<SpreadtypepropertyDTO> pageResult = this.querySpreadTypeWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<SpreadtypepropertyDTO> result = (EasyPageResult<SpreadtypepropertyDTO>) pageResult;
            List<SpreadtypepropertyDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

            });
        }
        return pageResult;
    }

    private PageResult<SpreadtypepropertyDTO> querySpreadTypeWithPage1(EasyPageFilter filter, SpreadtypepropertyDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Spreadtypeproperty.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer gradeid = dto.getGradeid();
            if(gradeid != null){
                criteria.andEqualTo("gradeid",gradeid);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }
        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" gradeid asc");
        }
        //查询
        List<Spreadtypeproperty> list;
        list = spreadtypepropertyMapper.selectByExample(example);
        //如果list=0，则说明无该商户相关信息，那么查询无商户号信息的数据
        if (list.size() == 0) {
            List<Spreadtypeproperty> list2 = spreadtypepropertyMapper.selectAll();
            log.info("商户号为null的数据:--{}----", list2);
            if (list2.size() > 0) {
                //查询无商户号信息的数据
                for (Spreadtypeproperty spreadtypeproperty : list2) {
                    if (StringUtils.isBlank(spreadtypeproperty.getMerchant())) {
                        spreadtypeproperty.setId(null);
                        spreadtypeproperty.setMerchant(ShiroUtils.getSessionMerchant());
                        //将赋了商户值后的对象新增到数据库
                        spreadtypepropertyMapper.insertSelective(spreadtypeproperty);
                    }
                }
            }
            //全部新增完后再根据商户查出数据
            list = spreadtypepropertyMapper.selectByExample(example);
        }

        //结果
        PageResult<SpreadtypepropertyDTO> pageResult = easyPageQuery.queryResult(list, SpreadtypepropertyDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public SpreadtypepropertyDTO querySpreadTypeById(SpreadtypepropertyDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 id 为空", 1004);
        }
        Spreadtypeproperty spreadtypeproperty = spreadtypepropertyMapper.selectByPrimaryKey(id);
        SpreadtypepropertyDTO record = new SpreadtypepropertyDTO();
        CommonBeans.copyPropertiesIgnoreNull(spreadtypeproperty,record);
        return record;
    }

    @Override
    public boolean isExistSpreadType(SpreadtypepropertyDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer gradeid = dto.getGradeid();
        if(gradeid == null){
            throw new BusinessException("根据id查询,传入对象 gradeid 为空", 1004);
        }
        Example example = new Example(Spreadtypeproperty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gradeid",gradeid);
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }
        List<Spreadtypeproperty> list = spreadtypepropertyMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public boolean saveSpreadType(SpreadtypepropertyDTO dto) {

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        Spreadtypeproperty record = new Spreadtypeproperty();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = spreadtypepropertyMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public boolean updateSpreadType(SpreadtypepropertyDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 id 为空", 1004);
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Spreadtypeproperty record = new Spreadtypeproperty();
        CommonBeans.copyPropertiesIgnoreNull(dto, record);
        int count = spreadtypepropertyMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeSpreadTypeByIds(Integer[] ids) {
        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(ids)){
            list = Arrays.asList(ids);
        }

        int count = 0;
        Example example = new Example(Spreadtypeproperty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",list);
        spreadtypepropertyMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
