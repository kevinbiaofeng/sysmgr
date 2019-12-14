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
import com.snake.mcf.sysmgr.mapper.uphold.UpholdSignMapper;
import com.snake.mcf.sysmgr.repertory.entity.Gamepackage;
import com.snake.mcf.sysmgr.repertory.entity.Gamepackagegoods;
import com.snake.mcf.sysmgr.repertory.entity.Gamesignin;
import com.snake.mcf.sysmgr.repertory.entity.dto.*;
import com.snake.mcf.sysmgr.repertory.mapper.GamepackageMapper;
import com.snake.mcf.sysmgr.repertory.mapper.GamepackagegoodsMapper;
import com.snake.mcf.sysmgr.repertory.mapper.GamesigninMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdSignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @ClassName UpholdSignServiceImpl
 * @Author 大帅
 * @Date 2019/7/4 10:05
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdSignServiceImpl extends BaseServiceImpl implements UpholdSignService {

    @Autowired
    private UpholdSignMapper upholdSignMapper;

    @Autowired
    private GamepackageMapper gamepackageMapper;

    @Autowired
    private GamepackagegoodsMapper gamepackagegoodsMapper;

    @Autowired
    private GamesigninMapper gamesigninMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    /**
     * 签到礼包配置分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @Override
    public PageResult<GamepackageDTO> queryGamePackageWithPage(EasyPageFilter pageFilter, GamepackageDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<GamepackageDTO> pageResult = this.queryGamePackageWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<GamepackageDTO> result = (EasyPageResult<GamepackageDTO>) pageResult;
            List<GamepackageDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //是否启用
                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                //平台类型
                Integer platformkind = rd.getPlatformkind();
                if(platformkind != null){
                    String platformkindDesc = DictionaryConstant.getPlatformkindDesc(platformkind);
                    rd.setPlatformkindDesc(platformkindDesc);
                }

                Integer typeid = rd.getTypeid();
                if(typeid != null){
                    String typeidDesc = DictionaryConstant.getSignTypeidDesc(typeid);
                    rd.setTypeidDesc(typeidDesc);

                }


            });
        }
        return pageResult;
    }

    /**
     * 签到礼包配置分页查询
     *
     * @param filter
     * @param dto
     * @return
     */
    private PageResult<GamepackageDTO> queryGamePackageWithPage1(EasyPageFilter filter, GamepackageDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Gamepackage.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer typeid = dto.getTypeid();
            if(typeid != null){
                criteria.andEqualTo("typeid",typeid);
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
            example.setOrderByClause(" SortID ASC ");
        }
        //查询
        List<Gamepackage> list = gamepackageMapper.selectByExample(example);
        //结果
        PageResult<GamepackageDTO> pageResult = easyPageQuery.queryResult(list, GamepackageDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public GamepackageDTO queryGamePackageById(GamepackageDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer packageid = dto.getPackageid();
        if(packageid == null){
            throw new BusinessException("根据id查询,传入对象 packageid 为空", 1004);
        }
        Gamepackage gamepackage = gamepackageMapper.selectByPrimaryKey(packageid);
        GamepackageDTO record = new GamepackageDTO();
        CommonBeans.copyPropertiesIgnoreNull(gamepackage,record);
        return record;
    }

    @Override
    public boolean saveGamePackage(GamepackageDTO dto) {
        dto.setAccount(ShiroUtils.getSessionLoginName());
        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        dto.setCollectdate(new Date());
        Gamepackage record = new Gamepackage();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = gamepackageMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public boolean updateGamePackage(GamepackageDTO dto) {

        dto.setAccount(ShiroUtils.getSessionLoginName());
        dto.setUpdatedDate(new Date());

        Gamepackage record = new Gamepackage();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = gamepackageMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeGamePackageByIds(Integer[] packageids) {
        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(packageids)){
            list = Arrays.asList(packageids);
        }

        int count = 0;
        Example example = new Example(Gamepackage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("packageid",list);
        gamepackageMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }

    @Override
    public PageResult<GamepackagegoodsDTO> queryGamePackageGoodsWithPage(EasyPageFilter pageFilter, GamepackagegoodsDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<GamepackagegoodsDTO> pageResult = this.queryGamePackageGoodsWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<GamepackagegoodsDTO> result = (EasyPageResult<GamepackagegoodsDTO>) pageResult;
            List<GamepackagegoodsDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //物品类型 如果是 游戏币 goodsnum / 100
                Integer typeid = rd.getTypeid();
                if(typeid != null){
                    String goodsTypeidDesc = DictionaryConstant.getGoodsTypeidDesc(typeid);
                    rd.setTypeidDesc(goodsTypeidDesc);

                    //{id: 0,text: '游戏币'},{id: 2,text: '道具'}
                    if(typeid.intValue() == 0){

                        Long goodsnum = rd.getGoodsnum();
                        //游戏币 goodsnum / 100
                        //rd.setGoodsnum(goodsnum/100);
                        rd.setGoodsnum((long)GoldUtils.reduce(goodsnum.longValue()));
                    }
                }

                Integer propertyid = rd.getPropertyid();
                if(propertyid != null){
                    String propertyiddDesc = DictionaryConstant.getGoodsPropertyiddDesc(propertyid);
                    rd.setPropertyidDesc(propertyiddDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<GamepackagegoodsDTO> queryGamePackageGoodsWithPage1(EasyPageFilter filter, GamepackagegoodsDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Gamepackagegoods.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(dto != null){
            Integer typeid = dto.getTypeid();
            if(typeid != null){
                map.put("typeid",typeid);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        //List<Gamepackagegoods> list = gamepackagegoodsMapper.selectByExample(example);
        List<GamepackagegoodsDTO> list = upholdSignMapper.queryGamePackageGoodsWithPage(map);
        //结果
        PageResult<GamepackagegoodsDTO> pageResult = easyPageQuery.queryResult(list, GamepackagegoodsDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public List<ComboBoxDTO> loadGamePackageData(Integer nullity) {
        Map<String,Object> map = new HashMap<>();
        if(nullity != null){
            map.put("nullity",nullity);
        }else{
            map.put("nullity",0);
        }
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            map.put("merchant",ShiroUtils.getSessionMerchant());
        }
        List<ComboBoxDTO> list = upholdSignMapper.loadGamePackageData(map);
        return list;
    }

    @Override
    public boolean saveGamePackageGoods(GamepackagegoodsDTO dto) {
            //2 保存业务数据
            //物品类型 如果是 游戏币 goodsnum / 100
            Integer typeid = dto.getTypeid();
            //{id: 0,text: '游戏币'},{id: 2,text: '道具'}
            if(typeid.intValue() == 0){
                Long goodsnum = dto.getGoodsnum();
                //游戏币 goodsnum * 100
                //dto.setGoodsnum(goodsnum * 100);
                dto.setGoodsnum(GoldUtils.boots(goodsnum.longValue()));
            }
            Gamepackagegoods record = new Gamepackagegoods();
            CommonBeans.copyPropertiesIgnoreNull(dto,record);
            int count = 0;
            if(record.getGoodsid() == null){
                //设置日期
                record.setCollectdate(new Date());
                count = gamepackagegoodsMapper.insertSelective(record);
            }else{
                count = gamepackagegoodsMapper.updateByPrimaryKeySelective(record);
            }
            return count > 0;
    }

    @Override
    public GamepackagegoodsDTO queryGamePackageGoodsById(GamepackagegoodsDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer goodsid = dto.getGoodsid();
        if(goodsid == null){
            throw new BusinessException("根据id查询,传入对象 goodsid 为空", 1004);
        }
        Gamepackagegoods gamepackagegoods = gamepackagegoodsMapper.selectByPrimaryKey(goodsid);
        GamepackagegoodsDTO record = new GamepackagegoodsDTO();
        CommonBeans.copyPropertiesIgnoreNull(gamepackagegoods,record);
        String resourceurl = record.getResourceurl();
        record.setUploadUrl(FileUtils.getUploadUrl(resourceurl));
        //物品类型 如果是 游戏币 goodsnum / 100
        Integer typeid = record.getTypeid();
        //{id: 0,text: '游戏币'},{id: 2,text: '道具'}
        if(typeid.intValue() == 0){
            Long goodsnum = record.getGoodsnum();
            //游戏币 goodsnum / 100
            //record.setGoodsnum(goodsnum/100);
            record.setGoodsnum((long)GoldUtils.reduce(goodsnum.longValue()));
        }
        return record;
    }

    @Override
    public boolean removeGamePackageGoodsByIds(Integer[] goodsids) {
        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(goodsids)){
            list = Arrays.asList(goodsids);
        }

        int count = 0;
        Example example = new Example(Gamepackagegoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("goodsid",list);
        gamepackagegoodsMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }

    @Override
    public PageResult<GamesigninDTO> queryGameSignInWithPage(EasyPageFilter pageFilter, GamesigninDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<GamesigninDTO> pageResult = this.queryGameSignInWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<GamesigninDTO> result = (EasyPageResult<GamesigninDTO>) pageResult;
            List<GamesigninDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                Integer typeid = rd.getTypeid();
                if(typeid != null){
                    String typeidDesc = DictionaryConstant.getSigntypeDesc(typeid);
                    rd.setTypeidDesc(typeidDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<GamesigninDTO> queryGameSignInWithPage1(EasyPageFilter filter, GamesigninDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Gamepackagegoods.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(dto != null){
            Integer typeid = dto.getTypeid();
            if(typeid != null){
                map.put("typeId",typeid);
            }
            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }


        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        //List<Gamepackagegoods> list = gamepackagegoodsMapper.selectByExample(example);
        List<GamesigninDTO> list = upholdSignMapper.queryGameSignInWithPage(map);
        //结果
        PageResult<GamesigninDTO> pageResult = easyPageQuery.queryResult(list, GamesigninDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean saveGameSignIn(GamesigninDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer typeid = dto.getTypeid();
        if(typeid == null){
            throw new BusinessException("根据id查询,传入对象 typeid 为空", 1004);
        }
        if(typeid.intValue() == 0){
            // 每日签到
            //将 累计签到所需天数 设置为 1
            dto.setNeedday(1);
        }else{
            // 累计签到
            //将 签到抽奖获得礼包的概率 设置为 0
            dto.setProbability(0);
        }
        Gamesignin record = new Gamesignin();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = gamesigninMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public GamesigninDTO queryGameSignInById(GamesigninDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer signid = dto.getSignid();
        if(signid == null){
            throw new BusinessException("根据id查询,传入对象 signid 为空", 1004);
        }
        Gamesignin gamesignin = gamesigninMapper.selectByPrimaryKey(signid);
        GamesigninDTO record = new GamesigninDTO();
        CommonBeans.copyPropertiesIgnoreNull(gamesignin,record);
        return record;
    }

    @Override
    public boolean updateGameSignIn(GamesigninDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id编辑,传入对象为空", 1004);
        }
        Integer signid = dto.getSignid();
        if(signid == null){
            throw new BusinessException("根据id编辑,传入对象 signid 为空", 1004);
        }
        Integer typeid = dto.getTypeid();
        if(typeid == null){
            throw new BusinessException("根据id查询,传入对象 typeid 为空", 1004);
        }
        if(typeid.intValue() == 0){
            // 每日签到
            //将 累计签到所需天数 设置为 1
            dto.setNeedday(1);
        }else{
            // 累计签到
            //将 签到抽奖获得礼包的概率 设置为 0
            dto.setProbability(0);
        }
        Gamesignin record = new Gamesignin();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = gamesigninMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeGameSignInByIds(Integer[] signids) {
        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(signids)){
            list = Arrays.asList(signids);
        }

        int count = 0;
        Example example = new Example(Gamesignin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("signid",list);
        gamesigninMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }

    @Override
    public PageResult<RecordgamesigninDTO> queryRecordGameSignInWithPage(EasyPageFilter pageFilter, RecordgamesigninDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordgamesigninDTO> pageResult = this.queryRecordGameSignInWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordgamesigninDTO> result = (EasyPageResult<RecordgamesigninDTO>) pageResult;
            List<RecordgamesigninDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                Integer signtype = rd.getSigntype();
                if(signtype != null){
                    String signtypeDesc = DictionaryConstant.getSigntypeDesc(signtype);
                    rd.setSigntypeDesc(signtypeDesc);
                }


            });
        }
        return pageResult;
    }

    private PageResult<RecordgamesigninDTO> queryRecordGameSignInWithPage1(EasyPageFilter filter, RecordgamesigninDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Gamepackagegoods.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            Integer gameid = dto.getGameid();
            if(gameid != null){
                map.put("gameid",gameid);
            }

            Integer signtype = dto.getSigntype();
            if(signtype.intValue() != -1){
                map.put("signtype",signtype);
            }

            String collectdateStartStr = dto.getCollectdateStartStr();
            if(StringUtils.isNotBlank(collectdateStartStr)){
                map.put("collectdateStartStr",collectdateStartStr);
                /*Date date = DateUtils.parseDate((collectdateStartStr + DateUtils.DF_START),DateUtils.DF_YYYYMMDDHHMMSS);
                if(date != null){
                    String start = DateUtils.format(date, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
                    map.put("collectdateStartStr",start);
                }*/
            }

            String collectdateEndStr = dto.getCollectdateEndStr();
            if(StringUtils.isNotBlank(collectdateEndStr)){
                map.put("collectdateEndStr",collectdateEndStr);
                /*Date date = DateUtils.parseDate((collectdateEndStr + DateUtils.DF_END),DateUtils.DF_YYYYMMDDHHMMSS);
                if(date != null){
                    String end = DateUtils.format(date, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
                    map.put("collectdateEndStr",end);
                }*/
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        //List<Gamepackagegoods> list = gamepackagegoodsMapper.selectByExample(example);
        List<RecordgamesigninDTO> list = upholdSignMapper.queryRecordGameSignInWithPage(map);
        //结果
        PageResult<RecordgamesigninDTO> pageResult = easyPageQuery.queryResult(list, RecordgamesigninDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
