package com.snake.mcf.sysmgr.mapper.goldmgr;

import com.snake.mcf.sysmgr.repertory.entity.dto.RecordinsureDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface GoldmgrBankMapper {


    List<RecordinsureDTO> queryRecordInsureWithPage(Map<String, Object> map);
}
