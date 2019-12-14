package com.snake.mcf.sysmgr.mapper.uphold;

import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UpholdGamelsMapper
 * @Author 大帅
 * @Date 2019/6/30 15:53
 */
@Repository
@Mapper
public interface UpholdGamelsMapper {


    List<ComboBoxDTO> loadGameModuleData();
}
