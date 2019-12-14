package com.snake.mcf.sysmgr.service.goldmgr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.enums.cash.TreasureTypeEnum;
import com.snake.mcf.sysmgr.repertory.entity.Question;
import com.snake.mcf.sysmgr.repertory.entity.Recordtreasureserial;
import com.snake.mcf.sysmgr.repertory.mapper.RecordtreasureserialMapper;
import com.snake.mcf.sysmgr.service.goldmgr.RecordTreasureSerialService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName GoldmgrRoommdServiceImpl
 * @Author 大帅
 * @Date 2019/7/13 18:11
 */
@Slf4j
@Service
public class RecordTreasureSerialServiceImpl extends BaseServiceImpl implements RecordTreasureSerialService {

    @Autowired
    private RecordtreasureserialMapper recordTreasureSerialMapper;
    
    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public Map<String, Object> queryRecordTreasureSerialListByCondition(Integer page, Integer rows, String _userId, Integer apiVersion){
    	Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
    	EasyPageFilter filter = new EasyPageFilter();
    	filter.setPage(page);
    	filter.setRows(rows);
        easyPageQuery.startPage(filter);
        Example example = new Example(Recordtreasureserial.class);
        Example.Criteria criteria = example.createCriteria();
        example.selectProperties("serialnumber", "collectdate", "curscore", "curinsurescore", "changescore", "changescore", "typeid");
        if(_userId != null && StringUtils.isNotEmpty(_userId)) {
        	criteria.andEqualTo("userid", _userId);
        }else {
        	criteria.andIsNull("userid");
        }
        easyPageQuery.sortOrderByClause(filter);
        example.setOrderByClause("collectdate DESC");
        List<Recordtreasureserial> list = recordTreasureSerialMapper.selectByExample(example);
    	List<Map<String, Object>> dataList = null;
    	if(list != null && list.size() > 0) {
    		dataList = new ArrayList<Map<String,Object>>();
    		Map<String, Object> dataMap = null;
    		Integer typeId = null;
    		for (Recordtreasureserial rts : list) {
    			dataMap = new HashMap<String, Object>();
    			dataMap.put("serialNumber", rts.getSerialnumber());
    			dataMap.put("beforeGold", rts.getCurscore() + rts.getCurinsurescore());
    			dataMap.put("changeGold", rts.getChangescore());
    			typeId = rts.getTypeid();
    			dataMap.put("afterGold", rts.getCurscore() + rts.getCurinsurescore() + (typeId.intValue() == TreasureTypeEnum.DEPOSIT_BANK.getCode().intValue() || typeId.intValue() == TreasureTypeEnum.WITHDRAW_BANK.getCode().intValue() ? 0 : rts.getChangescore()));
				dataMap.put("serialTime", DateUtils.format(rts.getCollectdate(), "yyyy-MM-dd HH:mm:ss"));
				dataMap.put("typeName", TreasureTypeEnum.getDescByCode(rts.getTypeid()));
				dataList.add(dataMap);
			}
    	}
        data.put("list", dataList);
        return data;
    }
}
