package com.snake.mcf.sysmgr.controller.web.withdraw;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.BindbankcardsDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.service.withdraw.WithdrawBindcardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 绑定银行卡
 *
 * @ClassName WithdrawBindcardController
 * @Author 大帅
 * @Date 2019/7/24 15:08
 */
@Slf4j
@Controller
@RequestMapping(path = "/withdraw/bindcard")
public class WithdrawBindcardController extends BaseController {

    @Autowired
    private WithdrawBindcardService withdrawBindcardService;

    /**
     * 分页查询绑卡信息
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryAccountUserWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<AccountsInfoDTO>> queryAccountUserWithPage(EasyPageFilter pageFilter , AccountsInfoDTO dto){
        PageResult<AccountsInfoDTO> pageResult = withdrawBindcardService.queryAccountUserWithPage(pageFilter , dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 跳转绑卡界面
     *
     * @param userId
     * @param modelMap
     * @return
     */
    @GetMapping(path = "/toBindcardListPage")
    public String toBindcardAdd(Integer userId, ModelMap modelMap){
        modelMap.put("userId",userId);
        return "withdraw/bindcard/bindcard_list";
    }

//    /**
//     * 获取绑定银行的所有名称
//     */
//    @GetMapping(path = "/getBankNameMap")
//    @ResponseBody
//    public ResponseEntity<List<Map<Integer, String>>> getBankNameMap(){
//        Map<Integer, String> bankNameMap = DictionaryConstant.getBankNameMap();
//        LinkedList<Map<Integer, String>> list = new LinkedList<>();
//        list.add(bankNameMap);
//        return ResponseEntity.ok(list);
//    }

    /**
     * 获取绑定银行的所有名称
     */
    @GetMapping(path = "/getBankNameMap")
    @ResponseBody
    public ResponseEntity<List<ComboBoxDTO>> getBankNameMap(){
        Map<Integer, String> bankNameMap = DictionaryConstant.getBankNameMap();
        List<ComboBoxDTO> list = new LinkedList<>();
        for (Map.Entry<Integer, String> entry : bankNameMap.entrySet()) {
            ComboBoxDTO comboBoxDTO = new ComboBoxDTO();
            entry.getKey();
            comboBoxDTO.setId(entry.getKey().toString());
            comboBoxDTO.setText(entry.getValue());
            list.add(comboBoxDTO);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 分页查询 卡号信息
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryBindBankCardsWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<BindbankcardsDTO>> queryBindBankCardsWithPage(EasyPageFilter pageFilter , BindbankcardsDTO dto){
        PageResult<BindbankcardsDTO> pageResult = withdrawBindcardService.queryBindBankCardsWithPage(pageFilter , dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryBindBankCardsById")
    @ResponseBody
    public ResponseEntity<Result> queryBindBankCardsById(BindbankcardsDTO dto){
        dto = withdrawBindcardService.queryBindBankCardsById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增/修改卡号信息
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改卡号信息" , description = "提现系统->卡号绑定管理->修改用户卡号信息功能:修改用户卡号信息")
    @PostMapping(path = "/saveBindBankCards")
    @ResponseBody
    public ResponseEntity<Result> saveBindBankCards(BindbankcardsDTO dto){
        boolean flag = withdrawBindcardService.saveBindBankCards(dto);
        Result result = flag ? Result.succ() : Result.fail("修改卡号信息失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除卡号信息
     *
     * @param ids
     * @return
     */
    @AopLog(module = "删除卡号信息" , description = "提现系统->卡号绑定管理->删除用户卡号信息功能:删除用户卡号信息")
    @DeleteMapping(path = "/removeBindBankCardsByIds")
    @ResponseBody
    public ResponseEntity<Result> removeBindBankCardsByIds(Long[] ids){
        boolean flag = withdrawBindcardService.removeBindBankCardsByIds(ids);
        Result result = flag ? Result.succ() : Result.fail("删除卡号信息失败");
        return ResponseEntity.ok(result);
    }





}
