package com.snake.mcf.sysmgr.service.account.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.account.AccountOnlineMapper;
import com.snake.mcf.sysmgr.repertory.entity.Gamescorelocker;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamescorelockerDTO;
import com.snake.mcf.sysmgr.repertory.mapper.GamescorelockerMapper;
import com.snake.mcf.sysmgr.service.account.AccountOnlineService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName AccountOnlineServiceImpl
 * @Author 大帅
 * @Date 2019/6/26 15:01
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class AccountOnlineServiceImpl extends BaseServiceImpl implements AccountOnlineService {

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private AccountOnlineMapper accountOnlineMapper;

    @Autowired
    private GamescorelockerMapper gamescorelockerMapper;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public List<ComboBoxDTO> loadGameRoomComboData(Integer nullity) {
        Map<String,Object> map = new HashMap<>();
        map.put("nullity",nullity);
        List<ComboBoxDTO> list = accountOnlineMapper.loadGameRoomComboData(map);
        //压入栈顶
        ComboBoxDTO comboBoxDTO = new ComboBoxDTO();
        comboBoxDTO.setId("0");
        comboBoxDTO.setText("全部房间");
        if(CollectionUtils.isNotEmpty(list)){
            list.add(0,comboBoxDTO);
        }
        return list;
    }

    @Override
    public PageResult<GamescorelockerDTO> queryGamescorelockerWithPage(EasyPageFilter pageFilter, GamescorelockerDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<GamescorelockerDTO> pageResult = this.queryGamescorelockerWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    private PageResult<GamescorelockerDTO> queryGamescorelockerWithPage1(EasyPageFilter pageFilter, GamescorelockerDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            String nickName = dto.getNickName();
            if(StringUtils.isNotBlank(nickName)){
                map.put("nickName", AESCBCUtils.encrypt(nickName, configResource.getAesUserKey()));
            }

            Integer serverid = dto.getServerid();
            if(serverid > 0){
                map.put("serverid",serverid);
            }

            Integer gameId = dto.getGameId();
            if(gameId != null){
                map.put("gameId",gameId);
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
        List<GamescorelockerDTO> list = accountOnlineMapper.queryGamescorelockerWithPage(map);
        
        for (GamescorelockerDTO gamescorelockerDTO : list) {
        	String nickName = gamescorelockerDTO.getNickName();
            if(StringUtils.isNotEmpty(nickName)) {
            	gamescorelockerDTO.setNickName(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
            }
		}
        //结果
        PageResult<GamescorelockerDTO> pageResult = easyPageQuery.queryResult(list, GamescorelockerDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;

    }

    @Override
    public boolean batchCleanLocker(GamescorelockerDTO dto) {
        if(dto == null){
            throw new BusinessException("清除卡线,传入对象为空", 1004);
        }

        String userIds = dto.getUserIds();
        if(StringUtils.isBlank(userIds)){
            throw new BusinessException("清除卡线,传入对象 userIds 为空", 1004);
        }

        List<String> useridList = new ArrayList<>();
        String[] arr = userIds.split(",");
        if(ArrayUtils.isNotEmpty(arr)){
            useridList = Arrays.asList(arr);
        }

        int count = 0;
        Example example = new Example(Gamescorelocker.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("userid",useridList);
        gamescorelockerMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }






}
