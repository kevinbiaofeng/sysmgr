package com.snake.mcf.sysmgr.service.website.impl;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.common.web.media.MediaFile;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.website.WebsiteStandMapper;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.ConfiginfoDTO;
import com.snake.mcf.sysmgr.repertory.mapper.ConfiginfoMapper;
import com.snake.mcf.sysmgr.service.website.WebsiteStandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WebsiteStandServiceImpl
 * @Author 大帅
 * @Date 2019/7/7 17:28
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WebsiteStandServiceImpl extends BaseServiceImpl implements WebsiteStandService {

    @Autowired
    private WebsiteStandMapper websiteStandMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private ConfiginfoMapper configinfoMapper;

    @Override
    public PageResult<ConfiginfoDTO> queryConfigInfoWithPage(EasyPageFilter pageFilter, ConfiginfoDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<ConfiginfoDTO> pageResult = this.queryConfigInfoWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<ConfiginfoDTO> result = (EasyPageResult<ConfiginfoDTO>) pageResult;
            List<ConfiginfoDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

            });
        }
        return pageResult;
    }

    private PageResult<ConfiginfoDTO> queryConfigInfoWithPage1(EasyPageFilter filter, ConfiginfoDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Configinfo.class);
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
        }
        //查询
        List<Configinfo> list = configinfoMapper.selectByExample(example);
        //结果
        PageResult<ConfiginfoDTO> pageResult = easyPageQuery.queryResult(list, ConfiginfoDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public ConfiginfoDTO queryConfigInfoById(ConfiginfoDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null){
            throw new BusinessException("根据id查询,传入对象 configid 为空", 1004);
        }

        Configinfo configinfo = configinfoMapper.selectByPrimaryKey(configid);
        ConfiginfoDTO record = new ConfiginfoDTO();
        CommonBeans.copyPropertiesIgnoreNull(configinfo,record);

        record.setUploadUrl(record.getField4());

        return record;
    }

    @Override
    public Configinfo queryConfigInfoByConfigKey(String configKey) {
        Example example = new Example(Configinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configKey", configKey);
        Configinfo configInfo = configinfoMapper.selectOneByExample(example);
        return configInfo;
    }

    @Override
    public boolean isExistConfigInfo(ConfiginfoDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        String configkey = dto.getConfigKey();
        if(StringUtils.isBlank(configkey)){
            throw new BusinessException("根据id查询,传入对象 configkey 为空", 1004);
        }

        Example example = new Example(Configinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configKey",configkey);

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()) {
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }

        List<Configinfo> list = configinfoMapper.selectByExample(example);

        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public boolean saveConfigInfo(MultipartFile file , ConfiginfoDTO dto) {
        try {

            //1 上传文件保存路径
            MediaFile mediaFile = FileUtils.getMediaFile(file);
            if(mediaFile != null){
                //throw new BusinessException("新增签到物品配置成功,传入文件对象为空", 1004);
                String path = FileUtils.uploadFile(mediaFile,"Site");
                if(StringUtils.isBlank(path)){
                    throw new BusinessException("新增/修改站点配置,上传文件失败", 1004);
                }
                dto.setField4(path);
            }

            //操作数据
            int count = 0;
            Integer configid = dto.getConfigid();
            if(configid == null){
                //新增
                dto.setCreatedDate(new Date());
                dto.setUpdatedDate(new Date());
                dto.setAccount(ShiroUtils.getSessionLoginName());
                //是否是管理员  不是 需要加一个字段过滤
                if(!ShiroUtils.isAdminSessionUserId()){
                    dto.setMerchant(ShiroUtils.getSessionMerchant());
                }
                Configinfo record = new Configinfo();
                CommonBeans.copyPropertiesIgnoreNull(dto,record);
                count = configinfoMapper.insertSelective(record);

            }else{
                //修改
                dto.setUpdatedDate(new Date());
                dto.setAccount(ShiroUtils.getSessionLoginName());

                Configinfo record = new Configinfo();
                CommonBeans.copyPropertiesIgnoreNull(dto,record);
                count = configinfoMapper.updateByPrimaryKeySelective(record);
            }
            return count > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateConfigInfo(ConfiginfoDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id修改,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null){
            throw new BusinessException("根据id修改,传入对象 configid 为空", 1004);
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Configinfo record = new Configinfo();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = configinfoMapper.updateByPrimaryKeySelective(record);

        return count > 0;
    }

    @Override
    public boolean removeConfigInfoByIds(Integer[] configids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(configids)){
            list = Arrays.asList(configids);
        }

        int count = 0;
        Example example = new Example(Configinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("configid",list);
        configinfoMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
