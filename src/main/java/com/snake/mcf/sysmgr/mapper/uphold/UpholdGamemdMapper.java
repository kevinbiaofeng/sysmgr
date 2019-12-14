package com.snake.mcf.sysmgr.mapper.uphold;

import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UpholdGamemdMapper {


    List<ComboBoxDTO> loadDataBaseInfo();
}
