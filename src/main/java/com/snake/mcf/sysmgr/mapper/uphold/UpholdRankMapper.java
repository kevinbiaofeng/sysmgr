package com.snake.mcf.sysmgr.mapper.uphold;

import com.snake.mcf.sysmgr.repertory.entity.dto.CacherankawardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UpholdRankMapper
 * @Author 大帅
 * @Date 2019/7/2 10:51
 */
@Repository
@Mapper
public interface UpholdRankMapper {


    List<CacherankawardDTO> queryRankAwardWithPage(Map<String, Object> map);
}
