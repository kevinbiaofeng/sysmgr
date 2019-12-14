package com.snake.mcf.sysmgr.service.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.enums.account.AccountInfoTradeTypeEnum;
import com.snake.mcf.sysmgr.repertory.api.form.BankRecordForm;
import com.snake.mcf.sysmgr.repertory.api.vo.bankrecord.BankRecordVO;
import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import com.snake.mcf.sysmgr.repertory.entity.Recordinsure;
import com.snake.mcf.sysmgr.repertory.entity.Systemstatusinfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;
import com.snake.mcf.sysmgr.repertory.mapper.RecordinsureMapper;
import com.snake.mcf.sysmgr.repertory.mapper.SystemstatusinfoMapper;
import com.snake.mcf.sysmgr.service.account.AccountUserService;
import com.snake.mcf.sysmgr.service.api.BankRecordService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
@SuppressWarnings("all")
public class BankReordServiceImpl implements BankRecordService {

	@Autowired
    private SystemstatusinfoMapper systemstatusinfoMapper;
	
	@Autowired
    private AccountUserService accountUserService;
	
	@Autowired
    private RecordinsureMapper recordinsureMapper;
	
	@Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;
	
	
	@Override
	public Map<String, Object> getBankRecordByUserId(BankRecordForm bankRecordForm, Integer apiVersion) throws IllegalAccessException, InvocationTargetException {
		Integer userId = Integer.valueOf(bankRecordForm.getUserId());
		Map<String, Object> data = new HashMap<String, Object>();
		Example example = new Example(Systemstatusinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("statusname", "TransferStauts");
        Systemstatusinfo systemstatusinfo = systemstatusinfoMapper.selectOneByExample(example);
        
        AccountsInfoDTO dto = new AccountsInfoDTO();
        dto.setUserId(Integer.valueOf(userId));
        dto = accountUserService.queryAccountUserById(dto);
        
        /** 暂时按照源代码使用or， 将来替换union all **/
        EasyPageFilter filter = new EasyPageFilter();
        filter.setPage(bankRecordForm.getPage());
        filter.setRows(bankRecordForm.getNumber());
        easyPageQuery.startPage(filter);
        Map<String,Object> condition = new HashMap<>();
        if(null != systemstatusinfo && systemstatusinfo.getStatusvalue() == 1 && (dto.getAgentId() == 0 || dto.getNullity() ==1)){
        	condition.put("tradetype", 3);
        }
        condition.put("sourceuserid", userId);
        condition.put("targetuserid", userId);
    	//查询
        List<Recordinsure> list = recordinsureMapper.queryRecordInsureList(condition);
        List<BankRecordVO> bankRecordVOList = null;
        if(list != null && list.size() > 0) {
        	bankRecordVOList = new ArrayList<BankRecordVO>();
            PageResult<Recordinsure> pageResult = easyPageQuery.queryResult(list, Recordinsure.class);
            Set<Integer> userIdList = new HashSet<Integer>();
            
            BankRecordVO bankRecordVO = null;
            for (Recordinsure riDto : list) {
            	bankRecordVO = new BankRecordVO();
            	BeanUtils.copyProperties(bankRecordVO , riDto);
            	bankRecordVO.setCollectDate(riDto.getCollectDate().getTime());
            	if(riDto.getTradeType() == 1 || riDto.getTradeType() == 2 || riDto.getTradeType() == 4) { //存 1,取 2,转 3
            		bankRecordVO.setTradeTypeDescription(AccountInfoTradeTypeEnum.getDescByCode(riDto.getTradeType()));
            	}else {
            		if(riDto.getSourceUserId() != 0 || riDto.getTargetUserId() != 0) {
            			userIdList.add(riDto.getSourceUserId());
                    	userIdList.add(riDto.getTargetUserId());
            		}
            	}
            	bankRecordVOList.add(bankRecordVO);
			}
            if(userIdList != null && userIdList.size() > 0) { //通过状态3获取转入、转出list 查询转账汇款人账户信息
            	List<Integer> ids = new ArrayList<Integer>(userIdList);
            	Map<Integer, AccountsInfo> map = accountUserService.selectAccountInfoMapByUserList(ids);
            	for (BankRecordVO vo : bankRecordVOList) {
            		if(vo.getTradeType()!=1 && vo.getTradeType()!=2 && vo.getTradeType()!=4) {
            			if(vo.getSourceUserId() == userId) {
            				vo.setTradeTypeDescription(AccountInfoTradeTypeEnum.getDescByCode(vo.getTradeType()));
            				if(vo.getTargetUserId() != 0) {
            					AccountsInfo accountInfo = map.get(vo.getTargetUserId());
            					vo.setTransferAccounts(String.valueOf(accountInfo.getGameId()));
            					vo.setTransferNickName(accountInfo.getNickName());
            				}
            			}else {
            				AccountsInfo accountInfo = map.get(vo.getSourceUserId());
            				vo.setTradeTypeDescription(AccountInfoTradeTypeEnum.getDescByCode(199));
            				vo.setTransferAccounts(String.valueOf(accountInfo.getGameId()));
        					vo.setTransferNickName(accountInfo.getNickName());
            			}
            			
            		}
				}
            }
            
            data.put("list", bankRecordVOList);
            data.put("total", pageResult.getTotal());
        }else {
            data.put("total", 0);
        }
        data.put("apiVersion", 20191007);
    	data.put("valid", true);
    	data.put("dateTime", System.currentTimeMillis());
		return data;
	}

}
