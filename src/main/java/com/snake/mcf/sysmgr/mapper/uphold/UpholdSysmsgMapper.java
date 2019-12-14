package com.snake.mcf.sysmgr.mapper.uphold;

import com.snake.mcf.sysmgr.repertory.entity.dto.group.GameInfoGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UpholdSysmsgMapper
 * @Author 大帅
 * @Date 2019/7/1 11:14
 */
@Repository
@Mapper
public interface UpholdSysmsgMapper {


    List<GameInfoGroup> loadSecondGroup();

    List<GameInfoGroup> loadThreeGroup(Integer kindid);
}
