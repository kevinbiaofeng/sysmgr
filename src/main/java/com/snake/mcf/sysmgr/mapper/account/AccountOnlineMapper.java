package com.snake.mcf.sysmgr.mapper.account;

import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamescorelockerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AccountOnlineMapper {


    List<ComboBoxDTO> loadGameRoomComboData(Map<String, Object> map);


    List<GamescorelockerDTO> queryGamescorelockerWithPage(Map<String, Object> map);
}
