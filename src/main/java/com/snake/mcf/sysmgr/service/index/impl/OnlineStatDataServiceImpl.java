package com.snake.mcf.sysmgr.service.index.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.mapper.index.OnlineStatDataMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.OnlineGroup;
import com.snake.mcf.sysmgr.repertory.entity.dto.index.MultipleLineDTO;
import com.snake.mcf.sysmgr.service.index.OnlineStatDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName OnlineStatDataServiceImpl
 * @Author 大帅
 * @Date 2019/7/19 17:55
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class OnlineStatDataServiceImpl implements OnlineStatDataService {

    /**
     * 默认当前日期往前推 6小时
     */
    private static final int HOURS = 6;

    @Autowired
    private OnlineStatDataMapper onlineStatDataMapper;

    @Override
    public OnlineGroup queryOnlineStatData() {
        Map<String, Object> paramMap = getParamsDate();
        List<MultipleLineDTO> dtoList = onlineStatDataMapper.dailyOnlineStatData(paramMap);

        OnlineGroup group = new OnlineGroup();

        List<String> abscissaList = dtoList.stream().map(MultipleLineDTO::getAbscissa).collect(Collectors.toList());
        group.setXAxisDataList(abscissaList);

        //设置开始时间
        LocalDateTime time = LocalDateTime.now().plusHours((-1) * HOURS);
        String stratValue = time.format(DateTimeFormatter.ofPattern(DateUtils.DF_YYYY_MM_DD_HH_MM_SS));
        group.setStartValue(stratValue);

        // seriesData1 - 机器人在线
        List<Double> seriesData1List = dtoList.stream().map(MultipleLineDTO::getSeriesData1).collect(Collectors.toList());
        Map<String, List<Double>> dataMap = new HashMap<>();
        dataMap.put("seriesData1",seriesData1List);
        // seriesData2 - 用户在线
        List<Double> seriesData2List = dtoList.stream().map(MultipleLineDTO::getSeriesData2).collect(Collectors.toList());
        dataMap.put("seriesData2",seriesData2List);
        group.setSeriesMultipleDataMap(dataMap);

        log.info("{}", JsonUtils.toString(group));

        return group;
    }

    /**
     * 获取两个日期
     *
     * @return  数组
     */
    private static Map<String,Object> getParamsDate(){
        // 默认查询当天数据
        //获取开始日期
        String start = DateUtils.getNowStart();
        log.info("获取开始日期:{}",start);
        //获取结束日期
        String end = DateUtils.getNowEnd();
        log.info("获取结束日期:{}",end);
        Map<String,Object> map = new HashMap<>();
        map.put("start",start);
        map.put("end",end);
        //商户号
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            map.put("merchant",ShiroUtils.getSessionMerchant());
        }
        return map;
    }
}
