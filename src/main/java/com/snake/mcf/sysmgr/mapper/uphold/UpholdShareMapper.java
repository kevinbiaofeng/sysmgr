package com.snake.mcf.sysmgr.mapper.uphold;

import com.snake.mcf.sysmgr.repertory.entity.dto.SharelogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UpholdShareMapper {


    List<SharelogDTO> queryShareLogWithPage(Map<String, Object> map);
}
