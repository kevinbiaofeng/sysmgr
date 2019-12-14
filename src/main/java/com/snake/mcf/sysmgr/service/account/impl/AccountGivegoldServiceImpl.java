package com.snake.mcf.sysmgr.service.account.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.snake.mcf.sysmgr.mapper.account.AccountGivegoldMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgranttreasureDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.service.account.AccountGivegoldService;
import com.snake.mcf.sysmgr.service.sys.SysUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AccountGivegoldServiceImpl
 * @Author 大帅
 * @Date 2019/6/26 19:04
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class AccountGivegoldServiceImpl extends BaseServiceImpl implements AccountGivegoldService {

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AccountGivegoldMapper accountGivegoldMapper;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<RecordgranttreasureDTO> queryGivegoldWithPage(EasyPageFilter pageFilter, RecordgranttreasureDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordgranttreasureDTO> pageResult = this.queryGivegoldWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordgranttreasureDTO> result = (EasyPageResult<RecordgranttreasureDTO>) pageResult;
            List<RecordgranttreasureDTO> rows = result.getRows();
            rows.forEach((rd) -> {

                /**
                 * 赠送前身上金币
                 */
                Long curgold = rd.getCurgold();
                if(curgold != null){
                    rd.setCurgoldDouble(GoldUtils.reduce(curgold.longValue()));
                }

                /**
                 * 赠送金币
                 */
                Long addgold = rd.getAddgold();
                if(addgold != null){
                    rd.setAddgoldDouble(GoldUtils.reduce(addgold.longValue()));
                }

                /**
                 * 赠送后身上金币
                 */
                Long allGold = rd.getAllGold();
                if(allGold != null){
                    rd.setAllGoldDouble(GoldUtils.reduce(allGold.longValue()));
                }
                
                String nickName = rd.getNickName();
                if(StringUtils.isNotEmpty(nickName)) {
                	rd.setNickName(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }
                
                String account = rd.getAccounts();
                if(StringUtils.isNotEmpty(account)) {
                	rd.setAccounts(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }

                //操作管理员
                Integer masterid = rd.getMasterid();
                if(masterid != null){
                    TbSysUserDTO userDTO = sysUserService.querySysUserById(String.valueOf(masterid));
                    if(userDTO != null){
                        rd.setMasterName(userDTO.getName() + "(" + userDTO.getLoginName() + ")");
                    }
                }
            });
        }
        return pageResult;
    }

    private PageResult<RecordgranttreasureDTO> queryGivegoldWithPage1(EasyPageFilter pageFilter, RecordgranttreasureDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            Integer gameId = dto.getGameId();
            if(gameId != null){
                map.put("gameId",gameId);
            }

            String collectdateStartStr = dto.getCollectdateStartStr();
            if(StringUtils.isNotBlank(collectdateStartStr)){
                    map.put("collectdateStartStr",collectdateStartStr);
            }

            String collectdateEndStr = dto.getCollectdateEndStr();
            if(StringUtils.isNotBlank(collectdateEndStr)){
                    map.put("collectdateEndStr",collectdateEndStr);
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
        List<RecordgranttreasureDTO> list = accountGivegoldMapper.queryGivegoldWithPage(map);
        //List<Recordgranttreasure> list = recordgranttreasureMapper.selectByExample(example);

        //结果
        PageResult<RecordgranttreasureDTO> pageResult = easyPageQuery.queryResult(list, RecordgranttreasureDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
