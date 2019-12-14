package com.snake.mcf.sysmgr.mapper.account;

import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AccountTaskMapper {


    List<ComboBoxDTO> loadGameRoomComboData(Map<String, Object> map);

    List<ComboBoxDTO> loadGameRoomInfoComboData(Map<String, Object> map);
}
