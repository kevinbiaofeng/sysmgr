package com.snake.mcf.sysmgr.mapper.index;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.snake.mcf.sysmgr.repertory.entity.dto.index.MultipleLineDTO;

/**
 * @ClassName OnlineStatDataMapper
 * @Author 大帅
 * @Date 2019/7/19 18:23
 */
@Repository
@Mapper
public interface OnlineStatDataMapper {


    /**
     * 查询在线人数
     *
     * @param paramMap
     * @return
     */
    List<MultipleLineDTO> dailyOnlineStatData(Map<String, Object> paramMap);
}
