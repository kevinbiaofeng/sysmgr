package com.snake.mcf.sysmgr.service.index.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.mapper.index.DistributeStatDataMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.DistributeGroup;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.EchartPieDataGroup;
import com.snake.mcf.sysmgr.repertory.entity.dto.index.SingtonPieDTO;
import com.snake.mcf.sysmgr.service.index.DistributeStatDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DistributeStatDataServiceImpl
 * @Author 大帅
 * @Date 2019/7/20 12:03
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class DistributeStatDataServiceImpl implements DistributeStatDataService {

    @Autowired
    private DistributeStatDataMapper distributeStatDataMapper;

    @Override
    public List<DistributeGroup> queryDistributeStatData() {
        /**
         * 类型
         * 1 查询金币统计
         */
        List<DistributeGroup> list = new ArrayList<>();

        //1 查询金币统计
        DistributeGroup goldGroup = this.queryDistributeGoldStatData();
        list.add(goldGroup);

        return list;
    }

    /**
     * 1 查询金币统计
     *
     * @return
     */
    private DistributeGroup queryDistributeGoldStatData() {
        DistributeGroup group = new DistributeGroup(1);
        //参数
        Map<String, Object> paramMap = getParamsData();
        //查询总金额
        SingtonPieDTO totalDto = distributeStatDataMapper.queryTotalGoldStatData(paramMap);
        //查询图表数据
        List<SingtonPieDTO> pieDtoList = distributeStatDataMapper.queryDistributeGoldStatData(paramMap);

        //设置总金额
        //Number1 - 身上总金币
        group.setTotalNumber1(totalDto.getTotalScore());
        //Number2 - 保险柜总金币
        group.setTotalNumber2(totalDto.getTotalInsureScore());

        //设置图表数据
        List<String> nameList = pieDtoList.stream().map(SingtonPieDTO::getName).collect(Collectors.toList());
        group.setNameList(nameList);
        List<EchartPieDataGroup> dataList = pieDtoList.stream().map((s) -> {
            EchartPieDataGroup dataGroup = new EchartPieDataGroup();
            CommonBeans.copyPropertiesIgnoreNull(s, dataGroup);
            return dataGroup;
        }).collect(Collectors.toList());
        group.setDataList(dataList);

        return group;
    }

    private static Map<String,Object> getParamsData(){
        Map<String,Object> map = new HashMap<>();
        //商户号
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            map.put("merchant",ShiroUtils.getSessionMerchant());
        }
        return map;
    }


}
