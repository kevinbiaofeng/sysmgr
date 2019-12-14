package com.snake.mcf.sysmgr.service.ffar.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.ffar.FfarStandMapper;
import com.snake.mcf.sysmgr.repertory.entity.Imgroupoption;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgroupoptionDTO;
import com.snake.mcf.sysmgr.repertory.mapper.ImgroupoptionMapper;
import com.snake.mcf.sysmgr.service.ffar.FfarStandService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName FfarStandServiceImpl
 * @Author 大帅
 * @Date 2019/7/15 10:34
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class FfarStandServiceImpl extends BaseServiceImpl implements FfarStandService {

    @Autowired
    private FfarStandMapper ffarStandMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private ImgroupoptionMapper imgroupoptionMapper;

    @Override
    public List<ImgroupoptionDTO> queryIMGroupOptionList() {

        Example example = new Example(Imgroupoption.class);
        List<Imgroupoption> list = imgroupoptionMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        return CommonBeans.copyNewList(list, ImgroupoptionDTO.class);
    }

    @Override
    public ImgroupoptionDTO queryIMGroupOptionInfoByName(ImgroupoptionDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        String optionname = dto.getOptionname();
        if(StringUtils.isBlank(optionname)){
            throw new BusinessException("根据id查询,传入对象 optionname 为空", 1004);
        }

        Example example = new Example(Imgroupoption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("optionname",optionname);

        List<Imgroupoption> list = imgroupoptionMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Imgroupoption imgroupoption = list.get(0);
        ImgroupoptionDTO record = new ImgroupoptionDTO();
        CommonBeans.copyPropertiesIgnoreNull(imgroupoption,record);
        return record;
    }

    @Override
    public boolean updateIMGroupOption(ImgroupoptionDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        String optionname = dto.getOptionname();
        if(StringUtils.isBlank(optionname)){
            throw new BusinessException("根据id查询,传入对象 optionname 为空", 1004);
        }

        Example example = new Example(Imgroupoption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("optionname",optionname);

        Imgroupoption record = new Imgroupoption();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = imgroupoptionMapper.updateByExampleSelective(record, example);
        return count > 0;
    }

    /*@Override
    public PageResult<ImgroupoptionDTO> queryIMGroupOptionWithPage(EasyPageFilter pageFilter, ImgroupoptionDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<ImgroupoptionDTO> pageResult = this.queryIMGroupOptionWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<ImgroupoptionDTO> result = (EasyPageResult<ImgroupoptionDTO>) pageResult;
            List<ImgroupoptionDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

            });
        }
        return pageResult;
    }*/

    /*private PageResult<ImgroupoptionDTO> queryIMGroupOptionWithPage1(EasyPageFilter filter, ImgroupoptionDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Imgroupoption.class);
        Example.Criteria criteria = example.createCriteria();

        if(dto != null){

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
            example.setOrderByClause(" SortID ASC ");
        }
        //查询
        List<Imgroupoption> list = imgroupoptionMapper.selectByExample(example);
        //结果
        PageResult<ImgroupoptionDTO> pageResult = easyPageQuery.queryResult(list, ImgroupoptionDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }*/

    /*@Override
    public ImgroupoptionDTO queryIMGroupOptionById(ImgroupoptionDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        String optionname = dto.getOptionname();
        if(StringUtils.isBlank(optionname)){
            throw new BusinessException("根据id查询,传入对象 optionname 为空", 1004);
        }

        Example example = new Example(Imgroupoption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("optionname",optionname);

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()) {
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }

        List<Imgroupoption> list = imgroupoptionMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Imgroupoption imgroupoption = list.get(0);
        ImgroupoptionDTO record = new ImgroupoptionDTO();
        CommonBeans.copyPropertiesIgnoreNull(imgroupoption,record);
        return record;
    }*/

   /* @Override
    public boolean isExistIMGroupOption(ImgroupoptionDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        String optionname = dto.getOptionname();
        if(StringUtils.isBlank(optionname)){
            throw new BusinessException("根据id查询,传入对象 optionname 为空", 1004);
        }

        Example example = new Example(Imgroupoption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("optionname",optionname);

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()) {
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }

        List<Imgroupoption> list = imgroupoptionMapper.selectByExample(example);

        return CollectionUtils.isNotEmpty(list);
    }*/

   /* @Override
    public boolean saveIMGroupOption(ImgroupoptionDTO dto) {

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        Imgroupoption record = new Imgroupoption();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = imgroupoptionMapper.insertSelective(record);
        return count > 0;
    }*/



    /*@Override
    public boolean removeIMGroupOptionByIds(String[] optionnames) {

        int count = 0;
        for (String optionname: optionnames) {
            Example example = new Example(Imgroupoption.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("optionname",optionname);
            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }
            imgroupoptionMapper.deleteByExample(example);
            count++;
        }
        return count > 0;
    }*/
}
