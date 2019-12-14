package com.snake.mcf.sysmgr.mapper.authority;

import com.snake.mcf.sysmgr.repertory.entity.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HelloMapper {

    public int countNum();

    public List<UserDTO> query();

}
