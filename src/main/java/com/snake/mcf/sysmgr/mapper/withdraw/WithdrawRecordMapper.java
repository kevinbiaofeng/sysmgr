package com.snake.mcf.sysmgr.mapper.withdraw;

import com.snake.mcf.sysmgr.repertory.entity.dto.CashoutordersDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface WithdrawRecordMapper {


    List<CashoutordersDTO> queryCashOutOrdersWithPage(Map<String, Object> map);
}
