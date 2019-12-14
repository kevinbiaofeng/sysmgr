package com.snake.mcf.sysmgr.mapper.uphold;

import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamepackagegoodsDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamesigninDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgamesigninDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UpholdSignMapper {

    List<GamepackagegoodsDTO> queryGamePackageGoodsWithPage(Map<String, Object> map);

    List<ComboBoxDTO> loadGamePackageData(Map<String,Object> map);

    List<GamesigninDTO> queryGameSignInWithPage(Map<String, Object> map);

    List<RecordgamesigninDTO> queryRecordGameSignInWithPage(Map<String, Object> map);
}
