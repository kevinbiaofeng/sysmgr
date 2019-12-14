package com.snake.mcf.sysmgr.mapper.goldmgr;

import com.snake.mcf.sysmgr.repertory.entity.dto.PersonalroomscoreinfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.StreamcreatetablefeeinfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface GoldmgrRoommdMapper {


    List<StreamcreatetablefeeinfoDTO> queryStreamCreateTableFeeInfoWithPage(Map<String, Object> map);

    List<PersonalroomscoreinfoDTO> queryPersonalRoomScoreInfoWithPage(Map<String, Object> map);
}
