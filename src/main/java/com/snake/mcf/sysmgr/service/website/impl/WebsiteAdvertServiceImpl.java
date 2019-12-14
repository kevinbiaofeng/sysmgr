package com.snake.mcf.sysmgr.service.website.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.website.WebsiteAdvertMapper;
import com.snake.mcf.sysmgr.repertory.entity.Ads;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.AdsDTO;
import com.snake.mcf.sysmgr.repertory.mapper.AdsMapper;
import com.snake.mcf.sysmgr.service.website.WebsiteAdvertService;
import com.snake.mcf.sysmgr.service.website.WebsiteStandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WebsiteAdvertServiceImpl
 * @Author 大帅
 * @Date 2019/7/9 15:29
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WebsiteAdvertServiceImpl extends BaseServiceImpl implements WebsiteAdvertService {

    @Autowired
    private WebsiteAdvertMapper websiteAdvertMapper;

    @Autowired
    private AdsMapper adsMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private WebsiteStandService websiteStandService;

    @Override
    public PageResult<AdsDTO> queryAdsWithPage(EasyPageFilter pageFilter, AdsDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<AdsDTO> pageResult = this.queryAdsWithPage1(pageFilter,dto);
        Configinfo configInfo = websiteStandService.queryConfigInfoByConfigKey("WebSiteConfig");
        String field5 = configInfo.getField5();
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<AdsDTO> result = (EasyPageResult<AdsDTO>) pageResult;
            List<AdsDTO> rows = result.getRows();

            rows.forEach( (rd) -> {
                //返回完整路径
                String resourceurl = rd.getResourceurl();
                if (null != resourceurl) {
                    rd.setResourceurl(field5 + resourceurl);
                }

                //广告类型
                Integer type = rd.getType();
                if(type != null){
                    String typeDesc = DictionaryConstant.getAdTypeDesc(type);
                    rd.setTypeDesc(typeDesc);
                }
                
                //平台类型
                Integer platformtype = rd.getPlatformtype();

                if(platformtype != null){
                    String platformtypeDesc = DictionaryConstant.getAdPlatformtypeDesc(platformtype);
                    rd.setPlatformtypeDesc(platformtypeDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<AdsDTO> queryAdsWithPage1(EasyPageFilter filter, AdsDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Ads.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer platformtype = dto.getPlatformtype();
            if(platformtype != null){
                criteria.andEqualTo("platformtype",platformtype);
            }

            Integer type = dto.getType();
            if(type != null){
                criteria.andEqualTo("type",type);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
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
        List<Ads> list = adsMapper.selectByExample(example);
        //结果
        PageResult<AdsDTO> pageResult = easyPageQuery.queryResult(list, AdsDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public AdsDTO queryAdsById(AdsDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 id 为空", 1004);
        }
        Ads ads = adsMapper.selectByPrimaryKey(id);
        AdsDTO record = new AdsDTO();
        CommonBeans.copyPropertiesIgnoreNull(ads,record);
        String resourceurl = record.getResourceurl();
        record.setUploadUrl(FileUtils.getUploadUrl(resourceurl));
        return record;
    }

    @Override
    public boolean saveAds(AdsDTO dto) {
            Ads record = new Ads();
            CommonBeans.copyPropertiesIgnoreNull(dto,record);
            int count = 0;
            if(record.getId() == null){
                record.setCreatedDate(new Date());
                record.setUpdatedDate(new Date());
                record.setAccount(ShiroUtils.getSessionLoginName());
                //是否是管理员  不是 需要加一个字段过滤
                if(!ShiroUtils.isAdminSessionUserId()) {
                    record.setMerchant(ShiroUtils.getSessionMerchant());
                }
                count = adsMapper.insertSelective(record);
            }else{
                record.setUpdatedDate(new Date());
                record.setAccount(ShiroUtils.getSessionLoginName());
                count = adsMapper.updateByPrimaryKeySelective(record);
            }
            return count > 0;
    }

    @Override
    public boolean removeAdsByIds(Integer[] ids) {
        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(ids)){
            list = Arrays.asList(ids);
        }

        int count = 0;
        Example example = new Example(Ads.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",list);
        adsMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }


}
