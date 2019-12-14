package com.snake.mcf.sysmgr.mapper.invest;

import com.snake.mcf.sysmgr.repertory.entity.dto.RecordtreasureserialDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface InvestRecordtreasureserialMapper {

    List<RecordtreasureserialDTO> selectHandRecord(RecordtreasureserialDTO dto);

}
