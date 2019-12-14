package com.snake.mcf.sysmgr.mapper.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;

/**
 * @ClassName AccountUserMapper
 * @Author 大帅
 * @Date 2019/6/24 12:32
 */
@Repository
@Mapper
public interface AccountUserMapper {

    /**
     * 分页查询
     *
     * @param map
     * @return
     */
    List<AccountsInfoDTO> queryAccountUserWithPage(Map<String, Object> map);

    /**
     * 批量转账
     *
     * @param paramMap
     * @return
     */
    public int batchTransfer(Map<String,Object> paramMap);

    /**
     * 所有人批量转账权限
     *
     * @param paramMap
     * @return
     */
    public int batchTransferAll(Map<String,Object> paramMap);

    /**
     * 设置批量转账
     *
     * @param paramMap
     * @return
     */
    public int batchSetTransfer(Map<String,Object> paramMap);

    /**
     * 取消批量转账
     *
     * @param paramMap
     * @return
     */
    public int batchCancleTransfer(Map<String,Object> paramMap);

    /**
     * 根据用户id集合查询用户信息
     * @param paramMap
     * @return
     */
    public List<AccountsInfo> selectAccountInfoByUserIds(Map<String,Object> paramMap);
}
