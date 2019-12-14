package com.snake.mcf.sysmgr.mapper.invest;

import com.snake.mcf.sysmgr.repertory.entity.dto.OnlinepayorderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface InverstRecordMapper {


    List<OnlinepayorderDTO> queryRecordWithPage(Map<String, Object> map);
}
