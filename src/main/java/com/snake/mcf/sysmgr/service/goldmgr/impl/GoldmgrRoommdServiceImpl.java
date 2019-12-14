package com.snake.mcf.sysmgr.service.goldmgr.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.GoldUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.goldmgr.GoldmgrRoommdMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.PersonalroomscoreinfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.StreamcreatetablefeeinfoDTO;
import com.snake.mcf.sysmgr.repertory.mapper.StreamcreatetablefeeinfoMapper;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrRoommdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GoldmgrRoommdServiceImpl
 * @Author 大帅
 * @Date 2019/7/13 18:11
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class GoldmgrRoommdServiceImpl extends BaseServiceImpl implements GoldmgrRoommdService {

    @Autowired
    private GoldmgrRoommdMapper goldmgrRoommdMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<StreamcreatetablefeeinfoDTO> queryStreamCreateTableFeeInfoWithPage(EasyPageFilter pageFilter, StreamcreatetablefeeinfoDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<StreamcreatetablefeeinfoDTO> pageResult = this.queryStreamCreateTableFeeInfoWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<StreamcreatetablefeeinfoDTO> result = (EasyPageResult<StreamcreatetablefeeinfoDTO>) pageResult;
            List<StreamcreatetablefeeinfoDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //房间状态
                Integer roomstatus = rd.getRoomstatus();
                if(roomstatus != null){
                    String roomstatusDesc = DictionaryConstant.getRoomstatusDesc(roomstatus);
                    rd.setRoomstatusDesc(roomstatusDesc);
                }

                //服务费 /100
                Long taxcount = rd.getTaxcount();
                if(taxcount != null){
                    rd.setTaxcountDouble(GoldUtils.reduce(taxcount.longValue()));
                }

                
                String nickName = rd.getNickname();
                if(StringUtils.isNotEmpty(nickName)) {
                	rd.setNickname(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }
            });
        }
        return pageResult;
    }

    private PageResult<StreamcreatetablefeeinfoDTO> queryStreamCreateTableFeeInfoWithPage1(EasyPageFilter filter, StreamcreatetablefeeinfoDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            // gameID
            Integer gameid = dto.getGameid();
            if(gameid != null){
                map.put("gameid",gameid);
            }

            //roomid
            Integer roomid = dto.getRoomid();
            if(roomid != null){
                map.put("roomid",roomid);
            }

            // userid
            Integer userid = dto.getUserid();
            if(userid != null){
                map.put("userid",userid);
            }

            String createdateStartStr = dto.getCreatedateStartStr();
            if(StringUtils.isNotBlank(createdateStartStr)){
                map.put("createdateStartStr",createdateStartStr);
            }

            String createdateEndStr = dto.getCreatedateEndStr();
            if(StringUtils.isNotBlank(createdateEndStr)){
                map.put("createdateEndStr",createdateEndStr);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        List<StreamcreatetablefeeinfoDTO> list = goldmgrRoommdMapper.queryStreamCreateTableFeeInfoWithPage(map);
        //结果
        PageResult<StreamcreatetablefeeinfoDTO> pageResult = easyPageQuery.queryResult(list, StreamcreatetablefeeinfoDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public PageResult<PersonalroomscoreinfoDTO> queryPersonalRoomScoreInfoWithPage(EasyPageFilter pageFilter, PersonalroomscoreinfoDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<PersonalroomscoreinfoDTO> pageResult = this.queryPersonalRoomScoreInfoWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<PersonalroomscoreinfoDTO> result = (EasyPageResult<PersonalroomscoreinfoDTO>) pageResult;
            List<PersonalroomscoreinfoDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                Long score = rd.getScore();
                if(score != null){
                    rd.setScoreDouble(GoldUtils.reduce(score.longValue()));
                }

                String nickName = rd.getNickname();
                if(StringUtils.isNotEmpty(nickName)) {
                	rd.setNickname(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }
            });
        }
        return pageResult;
    }

    private PageResult<PersonalroomscoreinfoDTO> queryPersonalRoomScoreInfoWithPage1(EasyPageFilter filter, PersonalroomscoreinfoDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Map<String,Object> map = new HashMap<>();
        if(dto != null){

            //记录Id
            Integer recordid = dto.getRecordid();
            if(recordid == null){
                throw new BusinessException("传入房间记录id为空");
            }

            map.put("recordid",recordid);

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        List<PersonalroomscoreinfoDTO> list = goldmgrRoommdMapper.queryPersonalRoomScoreInfoWithPage(map);
        //结果
        PageResult<PersonalroomscoreinfoDTO> pageResult = easyPageQuery.queryResult(list, PersonalroomscoreinfoDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
