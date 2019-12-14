package com.snake.mcf.sysmgr.mapper.uphold;

import com.snake.mcf.sysmgr.repertory.entity.dto.RecordbuynewpropertyDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordusepropertyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UpholdDotaMapper {


    List<RecordbuynewpropertyDTO> queryBuyDotaWithPage(Map<String, Object> map);

    List<RecordusepropertyDTO> queryUseDotaWithPage(Map<String, Object> map);
}
