package com.snake.mcf.sysmgr.mapper.account;

import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgranttreasureDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AccountGivegoldMapper
 * @Author 大帅
 * @Date 2019/6/26 19:05
 */
@Repository
@Mapper
public interface AccountGivegoldMapper {


    List<RecordgranttreasureDTO> queryGivegoldWithPage(Map<String, Object> map);


}
