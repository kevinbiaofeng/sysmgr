package com.snake.mcf.sysmgr.service.index.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.mapper.index.EverdayStatDataMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.EverdayGroup;
import com.snake.mcf.sysmgr.repertory.entity.dto.index.MultipleLineDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.index.SingtonLineDTO;
import com.snake.mcf.sysmgr.service.index.EverdayStatDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName EverdayStatDataServiceImpl
 * @Author 大帅
 * @Date 2019/7/18 12:25
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class EverdayStatDataServiceImpl implements EverdayStatDataService {

    //往前推 7 天
    private static final int DAYS = 6;

    @Autowired
    private EverdayStatDataMapper everdayStatDataMapper;

    /**
     * 查询每日统计图表
     *
     * @return
     */
    @Override
    public List<EverdayGroup> queryEverdayStatData() {
        /**
         * 图表类型
         *
         * 1 注册统计
         * 2 充值统计
         * 3 金币统计
         * 4 服务费统计
         * 5 损耗统计
         * 6 开房统计
         *
         */
        List<EverdayGroup> list = new LinkedList<>();
        //1 注册统计
        EverdayGroup registGroup = this.dailyRegistStatData();
        list.add(registGroup);

        //2 充值统计
        EverdayGroup payGroup = this.dailyPayStatData();
        list.add(payGroup);

        //3 金币统计
        EverdayGroup goldGroup = this.dailyGoldStatData();
        list.add(goldGroup);

        //4 服务费统计
        EverdayGroup revenueGroup = this.dailyRevenueStatData();
        list.add(revenueGroup);

        //5 损耗统计
        EverdayGroup wasteGroup = this.dailyWasteStatData();
        list.add(wasteGroup);

        //6 开房统计
        EverdayGroup openRoomGroup = this.dailyOpenRoomStatData();
        list.add(openRoomGroup);

        return list;
    }

    /**
     * 6 开房统计
     *
     * @return
     */
    private EverdayGroup dailyOpenRoomStatData() {
        Map<String, Object> paramMap = getParamsDate();
        List<SingtonLineDTO> dtoList = everdayStatDataMapper.dailyOpenRoomStatData(paramMap);
        //定义返回值
        EverdayGroup group = new EverdayGroup(6);

        List<String> abscissaList = dtoList.stream().map(SingtonLineDTO::getAbscissa).collect(Collectors.toList());
        group.setXAxisDataList(abscissaList);

        List<Double> dataList = dtoList.stream().map(SingtonLineDTO::getSeriesData).collect(Collectors.toList());
        group.setSeriesSingtonDataList(dataList);

        return group;
    }

    /**
     * 5 损耗统计
     *
     * @return
     */
    private EverdayGroup dailyWasteStatData() {
        Map<String, Object> paramMap = getParamsDate();
        List<SingtonLineDTO> dtoList = everdayStatDataMapper.dailyWasteStatData(paramMap);
        //定义返回值
        EverdayGroup group = new EverdayGroup(5);

        List<String> abscissaList = dtoList.stream().map(SingtonLineDTO::getAbscissa).collect(Collectors.toList());
        group.setXAxisDataList(abscissaList);

        List<Double> dataList = dtoList.stream().map(SingtonLineDTO::getSeriesData).collect(Collectors.toList());
        group.setSeriesSingtonDataList(dataList);

        return group;
    }

    /**
     * 4 服务费统计
     *
     * @return
     */
    private EverdayGroup dailyRevenueStatData() {
        Map<String, Object> paramMap = getParamsDate();
        List<MultipleLineDTO> dtoList = everdayStatDataMapper.dailyRevenueStatData(paramMap);
        //定义返回值
        EverdayGroup group = new EverdayGroup(4);

        List<String> abscissaList = dtoList.stream().map(MultipleLineDTO::getAbscissa).collect(Collectors.toList());
        group.setXAxisDataList(abscissaList);

        // seriesData1 - 游戏服务费
        List<Double> seriesData1List = dtoList.stream().map(MultipleLineDTO::getSeriesData1).collect(Collectors.toList());
        Map<String, List<Double>> dataMap = new HashMap<>();
        dataMap.put("seriesData1",seriesData1List);
        // seriesData2 - 银行服务费
        List<Double> seriesData2List = dtoList.stream().map(MultipleLineDTO::getSeriesData2).collect(Collectors.toList());
        dataMap.put("seriesData2",seriesData2List);
        // seriesData3 - 合计服务费
        List<Double> seriesData3List = dtoList.stream().map(MultipleLineDTO::getSeriesData3).collect(Collectors.toList());
        dataMap.put("seriesData3",seriesData3List);

        group.setSeriesMultipleDataMap(dataMap);

        return group;
    }

    /**
     * 3 金币统计
     *
     * @return
     */
    private EverdayGroup dailyGoldStatData() {
        Map<String, Object> paramMap = getParamsDate();
        List<MultipleLineDTO> dtoList = everdayStatDataMapper.dailyGoldStatData(paramMap);
        //定义返回值
        EverdayGroup group = new EverdayGroup(3);

        List<String> abscissaList = dtoList.stream().map(MultipleLineDTO::getAbscissa).collect(Collectors.toList());
        group.setXAxisDataList(abscissaList);

        // seriesData1 - 平台金币
        List<Double> seriesData1List = dtoList.stream().map(MultipleLineDTO::getSeriesData1).collect(Collectors.toList());
        Map<String, List<Double>> dataMap = new HashMap<>();
        dataMap.put("seriesData1",seriesData1List);
        // seriesData2 - 充值金币
        List<Double> seriesData2List = dtoList.stream().map(MultipleLineDTO::getSeriesData2).collect(Collectors.toList());
        dataMap.put("seriesData2",seriesData2List);
        group.setSeriesMultipleDataMap(dataMap);

        return group;
    }

    /**
     * 2 充值统计
     *
     * @return
     */
    private EverdayGroup dailyPayStatData() {
        Map<String, Object> paramMap = getParamsDate();
        List<SingtonLineDTO> dtoList = everdayStatDataMapper.dailyPayStatData(paramMap);
        //定义返回值
        EverdayGroup group = new EverdayGroup();
        group.setType(2);
        List<String> abscissaList = dtoList.stream().map(SingtonLineDTO::getAbscissa).collect(Collectors.toList());
        group.setXAxisDataList(abscissaList);

        List<Double> dataList = dtoList.stream().map(SingtonLineDTO::getSeriesData).collect(Collectors.toList());
        group.setSeriesSingtonDataList(dataList);

        return group;
    }


    /**
     * 1 注册统计
     *
     * @return
     */
    private EverdayGroup dailyRegistStatData() {
        Map<String, Object> paramMap = getParamsDate();
        List<SingtonLineDTO> dtoList = everdayStatDataMapper.dailyRegistStatData(paramMap);
        //定义返回值
        EverdayGroup group = new EverdayGroup();
        group.setType(1);
        List<String> abscissaList = dtoList.stream().map(SingtonLineDTO::getAbscissa).collect(Collectors.toList());
        group.setXAxisDataList(abscissaList);

        List<Double> dataList = dtoList.stream().map(SingtonLineDTO::getSeriesData).collect(Collectors.toList());
        group.setSeriesSingtonDataList(dataList);

        return group;
    }

    /**
     * 获取两个日期
     *
     * @return  数组
     */
    private static Map<String,Object> getParamsDate(){
        LocalDateTime yesterdayDate = DateUtils.getYesterdayDate();
        //获取开始日期 往前推 7 天
        String start = DateUtils.getPlusdayStart(yesterdayDate, (-1) * DAYS);
        log.info("获取开始日期:{}",start);
        //获取结束日期
        String end = DateUtils.getYesterdayEnd();
        log.info("获取结束日期:{}",end);
        //String[] array = {start,end};
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
