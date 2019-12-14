package com.snake.mcf.sysmgr.mapper.index;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.snake.mcf.sysmgr.repertory.entity.dto.index.SingtonPieDTO;

/**
 * @ClassName DistributeStatDataMapper
 * @Author 大帅
 * @Date 2019/7/20 14:01
 */
@Repository
@Mapper
public interface DistributeStatDataMapper {


    /**
     * 查询总金额
     *
     * @param paramMap
     * @return
     */
    SingtonPieDTO queryTotalGoldStatData(Map<String, Object> paramMap);

    /**
     * 查询图表数据
     *
     * @param paramMap
     * @return
     */
    List<SingtonPieDTO> queryDistributeGoldStatData(Map<String, Object> paramMap);
}
