package com.snake.mcf.sysmgr.mapper.goldmgr;

import com.snake.mcf.sysmgr.repertory.entity.dto.RecorduserinoutDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName GoldmgrInoutMapper
 * @Author 大帅
 * @Date 2019/7/12 10:23
 */
@Repository
@Mapper
public interface GoldmgrInoutMapper {


    List<RecorduserinoutDTO> queryRecordUserInoutWithPage(Map<String, Object> map);
}
