package com.snake.mcf.sysmgr.service.uphold;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.*;

import java.util.List;

public interface UpholdSignService extends BaseService {


    PageResult<GamepackageDTO> queryGamePackageWithPage(EasyPageFilter pageFilter, GamepackageDTO dto);

    boolean saveGamePackage(GamepackageDTO dto);

    GamepackageDTO queryGamePackageById(GamepackageDTO dto);

    boolean updateGamePackage(GamepackageDTO dto);

    boolean removeGamePackageByIds(Integer[] packageids);

    PageResult<GamepackagegoodsDTO> queryGamePackageGoodsWithPage(EasyPageFilter pageFilter, GamepackagegoodsDTO dto);

    List<ComboBoxDTO> loadGamePackageData(Integer nullity);

    boolean saveGamePackageGoods(GamepackagegoodsDTO dto);

    GamepackagegoodsDTO queryGamePackageGoodsById(GamepackagegoodsDTO dto);

    boolean removeGamePackageGoodsByIds(Integer[] goodsids);

    PageResult<GamesigninDTO> queryGameSignInWithPage(EasyPageFilter pageFilter, GamesigninDTO dto);

    boolean saveGameSignIn(GamesigninDTO dto);

    GamesigninDTO queryGameSignInById(GamesigninDTO dto);

    boolean updateGameSignIn(GamesigninDTO dto);

    boolean removeGameSignInByIds(Integer[] signids);

    PageResult<RecordgamesigninDTO> queryRecordGameSignInWithPage(EasyPageFilter pageFilter, RecordgamesigninDTO dto);
}
