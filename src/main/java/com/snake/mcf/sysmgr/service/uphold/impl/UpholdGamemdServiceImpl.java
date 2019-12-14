package com.snake.mcf.sysmgr.service.uphold.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.VersionUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.uphold.UpholdGamemdMapper;
import com.snake.mcf.sysmgr.repertory.entity.Gamegameitem;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamegameitemDTO;
import com.snake.mcf.sysmgr.repertory.mapper.GamegameitemMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdGamemdService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName UpholdGamemdServiceImpl
 * @Author 大帅
 * @Date 2019/6/29 16:54
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdGamemdServiceImpl extends BaseServiceImpl implements UpholdGamemdService {

    @Autowired
    private UpholdGamemdMapper upholdGamemdMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private GamegameitemMapper gamegameitemMapper;

    @Override
    public PageResult<GamegameitemDTO> queryGamemdWithPage(EasyPageFilter pageFilter, GamegameitemDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<GamegameitemDTO> pageResult = this.queryGamemdWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<GamegameitemDTO> result = (EasyPageResult<GamegameitemDTO>) pageResult;
            List<GamegameitemDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //服务端版本
                Integer serverversion = rd.getServerversion();
                if(serverversion != null){
                    String serverversionDesc = VersionUtils.deserializeVersion(serverversion.intValue());
                    rd.setServerversionDesc(serverversionDesc);
                }

                //客户端版本
                Integer clientversion = rd.getClientversion();
                if(clientversion != null){
                    String clientversionDesc = VersionUtils.deserializeVersion(clientversion.intValue());
                    rd.setClientversionDesc(clientversionDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<GamegameitemDTO> queryGamemdWithPage1(EasyPageFilter filter, GamegameitemDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Gamegameitem.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            String gamename = dto.getGamename();
            if(StringUtils.isNotBlank(gamename)){
                criteria.andLike("gamename", PayplatformConstant.SPLIT_SYMBOL_PER + gamename + PayplatformConstant.SPLIT_SYMBOL_PER);
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" gameid asc");
        }
        //查询
        List<Gamegameitem> list = gamegameitemMapper.selectByExample(example);
        //结果
        PageResult<GamegameitemDTO> pageResult = easyPageQuery.queryResult(list, GamegameitemDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public List<ComboBoxDTO> loadDataBaseInfo() {
        List<ComboBoxDTO> list = upholdGamemdMapper.loadDataBaseInfo();
        return list;
    }

    @Override
    public GamegameitemDTO queryGamemdById(GamegameitemDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer gameid = dto.getGameid();
        if(gameid == null){
            throw new BusinessException("根据id查询,传入对象 gameid 为空", 1004);
        }
        Gamegameitem gamegameitem = gamegameitemMapper.selectByPrimaryKey(gameid);
        GamegameitemDTO gamegameitemDTO = new GamegameitemDTO();
        CommonBeans.copyPropertiesIgnoreNull(gamegameitem,gamegameitemDTO);
        Integer serverversion = gamegameitemDTO.getServerversion();
        if(serverversion != null){
            String serverversionDesc = VersionUtils.deserializeVersion(serverversion.intValue());
            gamegameitemDTO.setServerversionDesc(serverversionDesc);
        }
        Integer clientversion = gamegameitemDTO.getClientversion();
        if(clientversion != null){
            String clientversionDesc = VersionUtils.deserializeVersion(clientversion.intValue());
            gamegameitemDTO.setClientversionDesc(clientversionDesc);
        }
        return gamegameitemDTO;
    }

    @Override
    public boolean isExistGamemd(GamegameitemDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer gameid = dto.getGameid();
        if(gameid == null){
            throw new BusinessException("根据id查询,传入对象 gameid 为空", 1004);
        }
        Gamegameitem gamegameitem = gamegameitemMapper.selectByPrimaryKey(gameid);
        return gamegameitem != null;
    }

    @Override
    public boolean saveGamemd(GamegameitemDTO dto) {
        String clientversionDesc = dto.getClientversionDesc();
        if(StringUtils.isNotBlank(clientversionDesc)){
            int clientversion = VersionUtils.serializeVersion(clientversionDesc);
            dto.setClientversion(clientversion);
        }
        String serverversionDesc = dto.getServerversionDesc();
        if(StringUtils.isNotBlank(serverversionDesc)){
            int serverversion = VersionUtils.serializeVersion(serverversionDesc);
            dto.setServerversion(serverversion);
        }
        dto.setSuporttype(31);
        Gamegameitem record = new Gamegameitem();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = gamegameitemMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public boolean updateGamemd(GamegameitemDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id修改,传入对象为空", 1004);
        }
        Integer gameid = dto.getGameid();
        if(gameid == null){
            throw new BusinessException("根据id修改,传入对象 gameid 为空", 1004);
        }
        String clientversionDesc = dto.getClientversionDesc();
        if(StringUtils.isNotBlank(clientversionDesc)){
            int clientversion = VersionUtils.serializeVersion(clientversionDesc);
            dto.setClientversion(clientversion);
        }
        String serverversionDesc = dto.getServerversionDesc();
        if(StringUtils.isNotBlank(serverversionDesc)){
            int serverversion = VersionUtils.serializeVersion(serverversionDesc);
            dto.setServerversion(serverversion);
        }
        Gamegameitem record = new Gamegameitem();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = gamegameitemMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeGamemdByIds(Integer[] gameids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(gameids)){
            list = Arrays.asList(gameids);
        }

        int count = 0;
        Example example = new Example(Gamegameitem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("gameid",list);
        gamegameitemMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }




}
