package com.snake.mcf.sysmgr.service.account;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.api.vo.user.UserInfoVO;
import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgrantgameidDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordtreasureserialDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface AccountUserService extends BaseService {


    PageResult<AccountsInfoDTO> queryAccountUserWithPage(EasyPageFilter pageFilter, AccountsInfoDTO infoDTO);

    boolean nullityUpate(AccountsInfoDTO infoDTO);

    boolean batchTransfer(AccountsInfoDTO infoDTO);

    boolean batchTransferAll(AccountsInfoDTO infoDTO);
    
    AccountsInfo queryAccountUserById(Integer userId);
    
    /**
     * 客户端调用接口
     * @param userId
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public UserInfoVO queryAPIAccountUserById(Integer userId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;

    AccountsInfoDTO queryAccountUserById(AccountsInfoDTO infoDTO);

    boolean updateAccountUser(AccountsInfoDTO infoDTO);

    PageResult<RecordtreasureserialDTO> queryRecordtreasureserialWithPage(EasyPageFilter pageFilter, RecordtreasureserialDTO dto);

    PageResult<RecordtreasureserialDTO> queryHandRecordWithPage(EasyPageFilter pageFilter, RecordtreasureserialDTO dto);

    Integer freshenGiveNum();

    boolean giveNumAccountUser(AccountsInfoDTO infoDTO);

    PageResult<RecordgrantgameidDTO> queryGivenumRecordWithPage(EasyPageFilter pageFilter, RecordgrantgameidDTO dto);

    boolean giveGoldAccountUser(AccountsInfoDTO infoDTO);

    boolean validatorGiveGold(Long giveGold, Integer userId);
    
    public List<AccountsInfo> selectAccountInfoByUserList(List<Integer> userIdList);
    public Map<Integer, AccountsInfo> selectAccountInfoMapByUserList(List<Integer> userIdList);
    
    
    public void updateall();
    
    int updateAccountInfoByUserId(AccountsInfo accountsInfo);
}
