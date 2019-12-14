package com.snake.mcf.sysmgr.service.uphold.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.uphold.UpholdDotaMapper;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.Gameproperty;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamepropertyDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordbuynewpropertyDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordusepropertyDTO;
import com.snake.mcf.sysmgr.repertory.mapper.ConfiginfoMapper;
import com.snake.mcf.sysmgr.repertory.mapper.GamepropertyMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdDotaService;
import com.snake.mcf.sysmgr.service.website.WebsiteStandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UpholdDotaServiceImpl
 * @Author 大帅
 * @Date 2019/7/2 17:57
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdDotaServiceImpl extends BaseServiceImpl implements UpholdDotaService {

    @Autowired
    private UpholdDotaMapper upholdDotaMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private GamepropertyMapper gamepropertyMapper;

    @Autowired
    private ConfiginfoMapper configinfoMapper;

    @Autowired
    private WebsiteStandService  websiteStandService;

    @Override
    public PageResult<GamepropertyDTO> queryDotaWithPage(EasyPageFilter pageFilter, GamepropertyDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<GamepropertyDTO> pageResult = this.queryDotaWithPage1(pageFilter,dto);
        Configinfo configInfo = websiteStandService.queryConfigInfoByConfigKey("WebSiteConfig");
        String field5 = configInfo.getField5();
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<GamepropertyDTO> result = (EasyPageResult<GamepropertyDTO>) pageResult;
            List<GamepropertyDTO> rows = result.getRows();

            rows.forEach( (rd) -> {
                //返回完整路径
                String imgUrl = rd.getImgUrl();
                if (null != imgUrl) {
                    rd.setImgUrl(field5 + imgUrl);
                }

                //购买增加金币 /100
                Long buyresultsgold = rd.getBuyresultsgold();
                if(buyresultsgold != null){
                    rd.setBuyresultsgoldDouble(GoldUtils.reduce(buyresultsgold.longValue()));
                }

                //使用增加金币 /100
                Long useresultsgold = rd.getUseresultsgold();
                if(useresultsgold != null){
                    rd.setUseresultsgoldDouble(GoldUtils.reduce(useresultsgold.longValue()));
                }

                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                Integer exchangetype = rd.getExchangetype();
                if(exchangetype != null){
                    String exchangetypeDesc = DictionaryConstant.getExchangetypeDesc(exchangetype);
                    rd.setExchangetypeDesc(exchangetypeDesc);
                }

                Integer usearea = rd.getUsearea();
                if(usearea != null){
                    List<Integer> nums = VersionUtils.transferBinary(usearea.intValue());
                    StringBuilder sb = new StringBuilder();
                    if(CollectionUtils.isNotEmpty(nums)){
                        nums.forEach( (n) -> {
                            String useareaDesc = DictionaryConstant.getUseareaDesc(n);
                            sb.append(useareaDesc).append(",");
                        });
                    }
                    String res = sb.toString();
                    if(StringUtils.isNotBlank(res)){
                        String rest = res.substring(0, res.length() - 1);
                        rd.setUseareaDesc(rest);
                    }
                }

                Integer servicearea = rd.getServicearea();
                if(servicearea != null){
                    List<Integer> nums = VersionUtils.transferBinary(servicearea.intValue());
                    StringBuilder sb = new StringBuilder();
                    if(CollectionUtils.isNotEmpty(nums)){
                        nums.forEach( (n) -> {
                            String serviceareaDesc = DictionaryConstant.getServiceareaDesc(n);
                            sb.append(serviceareaDesc).append(",");
                        });
                    }
                    String res = sb.toString();
                    if(StringUtils.isNotBlank(res)){
                        String rest = res.substring(0, res.length() - 1);
                        rd.setServiceareaDesc(rest);
                    }
                }

                //是否推荐
                Integer recommend = rd.getRecommend();
                if(recommend != null){
                    String recommendDesc = DictionaryConstant.getRecommendDesc(recommend);
                    rd.setRecommendDesc(recommendDesc);
                }

                //道具比例
                Integer exchangeratio = rd.getExchangeratio();
                String name = rd.getName();
                String dotaRatio = this.getDotaRatio(exchangetype.intValue(),exchangeratio.intValue(),name);
                rd.setDotaRatio(dotaRatio);

            });
        }
        return pageResult;
    }

    private String getDotaRatio(int typeid, int ratio, String name) {
        if (typeid == 1) {
            //return ratio + "游戏币：" + "1" + name;
            return GoldUtils.reduce(ratio) + "游戏币：" + "1" + name;
        } else if (typeid == 0) {
            return "1钻石：" + ratio + name;
        } else {
            return ratio + "未知：" + "1" + name;
        }
    }

    private PageResult<GamepropertyDTO> queryDotaWithPage1(EasyPageFilter filter, GamepropertyDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Gameproperty.class);
        Example.Criteria criteria = example.createCriteria();
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
            example.setOrderByClause("  Kind ASC,SortID ASC");
        }
        //查询
        List<Gameproperty> list = gamepropertyMapper.selectByExample(example);
        //结果
        PageResult<GamepropertyDTO> pageResult = easyPageQuery.queryResult(list, GamepropertyDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public GamepropertyDTO queryDotaById(GamepropertyDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 id 为空", 1004);
        }
        Integer exchangetype = dto.getExchangetype();
        if(exchangetype == null){
            throw new BusinessException("根据id查询,传入对象 exchangetype 为空", 1004);
        }

        Example example = new Example(Gameproperty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        criteria.andEqualTo("exchangetype",exchangetype);
        List<Gameproperty> list = gamepropertyMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Gameproperty gameproperty = list.get(0);
        GamepropertyDTO record = new GamepropertyDTO();
        CommonBeans.copyPropertiesIgnoreNull(gameproperty,record);
//        String imgUrl =record.getImgUrl();
//        record.setImgUrl(FileUtils.getUploadUrl(imgUrl));
        return record;
    }

    @Override
    public boolean updateDota(GamepropertyDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 id 为空", 1004);
        }
        Integer exchangetype = dto.getExchangetype();
        if(exchangetype == null){
            throw new BusinessException("根据id查询,传入对象 exchangetype 为空", 1004);
        }

        //购买增加金币 * 100
        Long buyresultsgold = dto.getBuyresultsgold();
        if(buyresultsgold != null){
            dto.setBuyresultsgold(GoldUtils.boots(buyresultsgold.longValue()));
        }

        //使用增加金币 * 100
        Long useresultsgold = dto.getUseresultsgold();
        if(useresultsgold != null){
            dto.setUseresultsgold(GoldUtils.boots(useresultsgold.longValue()));
        }

        Gameproperty record = new Gameproperty();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);

        Example example = new Example(Gameproperty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        criteria.andEqualTo("exchangetype",exchangetype);
        int count = gamepropertyMapper.updateByExampleSelective(record, example);
        return count > 0;
    }

    @Override
    public boolean isExistDota(GamepropertyDTO dto) {
        if(dto == null){
            throw new BusinessException("传入对象为空", 1004);
        }
        String name = dto.getName();
        if (StringUtils.isBlank(name)){
            throw new BusinessException("道具名称不能为空", 1004);
        }
        //根据条件查询
        Example example = new Example(Gameproperty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",name);
        List<Gameproperty> list = gamepropertyMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public PageResult<RecordbuynewpropertyDTO> queryBuyDotaWithPage(EasyPageFilter pageFilter, RecordbuynewpropertyDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordbuynewpropertyDTO> pageResult = this.queryBuyDotaWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordbuynewpropertyDTO> result = (EasyPageResult<RecordbuynewpropertyDTO>) pageResult;
            List<RecordbuynewpropertyDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //消耗货币 /100
                Long currency = rd.getCurrency();
                if(currency != null){
                    rd.setCurrencyDouble(GoldUtils.reduce(currency.longValue()));
                }

                // 购买前货币 /100
                Long beforecurrency = rd.getBeforecurrency();
                if(beforecurrency != null){
                    rd.setBeforecurrencyDouble(GoldUtils.reduce(beforecurrency.longValue()));
                }

                Integer exchangetype = rd.getExchangetype();
                if(exchangetype != null){
                    String exchangetypeDesc = DictionaryConstant.getExchangetypeDesc(exchangetype);
                    rd.setExchangetypeDesc(exchangetypeDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<RecordbuynewpropertyDTO> queryBuyDotaWithPage1(EasyPageFilter pageFilter, RecordbuynewpropertyDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            Integer gameId = dto.getGameid();
            if(gameId != null){
                map.put("gameid",gameId);
            }

            String collectdateStartStr = dto.getCollectdateStartStr();
            if(StringUtils.isNotBlank(collectdateStartStr)){
                map.put("collectdateStartStr",collectdateStartStr);
            }
//            if(StringUtils.isNotBlank(collectdateStartStr)){
//                Date date = DateUtils.parseDate((collectdateStartStr + DateUtils.DF_START),DateUtils.DF_YYYYMMDDHHMMSS);
//                if(date != null){
//                    String start = DateUtils.format(date, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
//                    map.put("collectdateStartStr",start);
//                }
//            }

            String collectdateEndStr = dto.getCollectdateEndStr();
            if(StringUtils.isNotBlank(collectdateEndStr)){
                map.put("collectdateEndStr",collectdateEndStr);
            }
//            if(StringUtils.isNotBlank(collectdateEndStr)){
//                Date date = DateUtils.parseDate((collectdateEndStr + DateUtils.DF_END),DateUtils.DF_YYYYMMDDHHMMSS);
//                if(date != null){
//                    String end = DateUtils.format(date, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
//                    map.put("collectdateEndStr",end);
//                }
//            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        List<RecordbuynewpropertyDTO> list = upholdDotaMapper.queryBuyDotaWithPage(map);
        //List<Recordgranttreasure> list = recordgranttreasureMapper.selectByExample(example);

        //结果
        PageResult<RecordbuynewpropertyDTO> pageResult = easyPageQuery.queryResult(list, RecordbuynewpropertyDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public PageResult<RecordusepropertyDTO> queryUseDotaWithPage(EasyPageFilter pageFilter, RecordusepropertyDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordusepropertyDTO> pageResult = this.queryUseDotaWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordusepropertyDTO> result = (EasyPageResult<RecordusepropertyDTO>) pageResult;
            List<RecordusepropertyDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                // 使用增加游戏币 /100
                Long useresultsgold = rd.getUseresultsgold();
                if(useresultsgold != null){
                    rd.setUseresultsgoldDouble(GoldUtils.reduce(useresultsgold.longValue()));
                }

            });
        }
        return pageResult;
    }

    @Override
    public boolean saveOrUpdateDota(Gameproperty vo) {
        //购买增加金币 * 100
        Long buyresultsgold = vo.getBuyresultsgold();
        if(buyresultsgold != null){
            vo.setBuyresultsgold(GoldUtils.boots(buyresultsgold.longValue()));
        }

        //使用增加金币 * 100
        Long useresultsgold = vo.getUseresultsgold();
        if(useresultsgold != null){
            vo.setUseresultsgold(GoldUtils.boots(useresultsgold.longValue()));
        }

        vo.setAccount(ShiroUtils.getSessionLoginName());
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()) {
            vo.setMerchant(ShiroUtils.getSessionMerchant());
        }
        vo.setUpdateDate(new Date());

        if (null == vo.getId()){
            vo.setCreateDate(new Date());
            int result = gamepropertyMapper.insertSelective(vo);
            if (0 == result){
                throw new BusinessException("新增保存失败");
            }
            return true;
        } else {
            int result = gamepropertyMapper.updateByPrimaryKeySelective(vo);
            if (0 == result){
                throw new BusinessException("编辑失败");
            }
            return true;
        }
    }

    private PageResult<RecordusepropertyDTO> queryUseDotaWithPage1(EasyPageFilter pageFilter, RecordusepropertyDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            Integer gameId = dto.getGameid();
            if(gameId != null){
                map.put("gameid",gameId);
            }

            String usedateStartStr = dto.getUsedateStartStr();
            if(StringUtils.isNotBlank(usedateStartStr)){
                map.put("usedateStartStr",usedateStartStr);
            }

            String usedateEndStr = dto.getUsedateEndStr();
            if(StringUtils.isNotBlank(usedateEndStr)){
                map.put("usedateEndStr",usedateEndStr);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        List<RecordusepropertyDTO> list = upholdDotaMapper.queryUseDotaWithPage(map);
        //List<Recordgranttreasure> list = recordgranttreasureMapper.selectByExample(example);

        //结果
        PageResult<RecordusepropertyDTO> pageResult = easyPageQuery.queryResult(list, RecordusepropertyDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }


}
