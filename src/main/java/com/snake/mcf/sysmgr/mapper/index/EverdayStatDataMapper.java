package com.snake.mcf.sysmgr.mapper.index;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.snake.mcf.sysmgr.repertory.entity.dto.index.MultipleLineDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.index.SingtonLineDTO;

@Repository
@Mapper
public interface EverdayStatDataMapper {

    /**
     * 查询 注册统计
     *
     * @param paramMap
     * @return
     */
    List<SingtonLineDTO> dailyRegistStatData(Map<String, Object> paramMap);

    /**
     * 查询 充值统计
     *
     * @param paramMap
     * @return
     */
    List<SingtonLineDTO> dailyPayStatData(Map<String, Object> paramMap);

    /**
     * 查询金币统计
     *
     * @param paramMap
     * @return
     */
    List<MultipleLineDTO> dailyGoldStatData(Map<String, Object> paramMap);

    /**
     * 服务费统计
     *
     * @param paramMap
     * @return
     */
    List<MultipleLineDTO> dailyRevenueStatData(Map<String, Object> paramMap);

    /**
     * 损耗统计
     *
     * @param paramMap
     * @return
     */
    List<SingtonLineDTO> dailyWasteStatData(Map<String, Object> paramMap);

    /**
     * 房间模式
     *
     * @param paramMap
     * @return
     */
    List<SingtonLineDTO> dailyOpenRoomStatData(Map<String, Object> paramMap);
}
