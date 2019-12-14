package com.snake.mcf.sysmgr.mapper.ffar;

import com.snake.mcf.sysmgr.repertory.entity.dto.ImgroupmemberDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgrouppropertyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface FfarManageMapper {


    List<ImgrouppropertyDTO> queryIMGroupPropertyWithPage(Map<String, Object> map);

    List<ImgroupmemberDTO> queryGroupMemberWithPage(Map<String, Object> map);
}
