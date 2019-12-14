package com.snake.mcf.sysmgr.controller.web.withdraw;

import com.snake.mcf.common.exception.BusinessException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.CashoutordersDTO;
import com.snake.mcf.sysmgr.service.withdraw.WithdrawRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName WithdrawRecordController
 * @Author 大帅
 * @Date 2019/7/26 20:36
 */
@Slf4j
@Controller
@RequestMapping(path = "/withdraw/record")
public class WithdrawRecordController extends BaseController {

    @Autowired
    private WithdrawRecordService withdrawRecordService;

    /**
 	  * 分页查询提现订单
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryCashOutOrdersWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<CashoutordersDTO>> queryCashOutOrdersWithPage(EasyPageFilter pageFilter , CashoutordersDTO dto){
        PageResult<CashoutordersDTO> pageResult = withdrawRecordService.queryCashOutOrdersWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 打开审核界面
     *
     * @param orderids
     * @param reviewstatus
     * @return
     */
    @GetMapping(path = "/toRecordReviewPage")
    public String toRecordReviewPage(String orderids , Integer reviewstatus, ModelMap modelMap){
        modelMap.put("orderids",orderids);
        modelMap.put("reviewstatus",reviewstatus);
        /*modelMap.put("remark","");
        if(reviewstatus.intValue() == 1){
            // 审核通过
            modelMap.put("remark","通过");
        }else if(reviewstatus.intValue() == 2){
            // 审核不通过
            modelMap.put("remark","不通过");
        }*/
        return "withdraw/record/record_review";
    }

    /**
     * 审核
     *
     * @param dto
     * @return
     */
    @PostMapping(path = "/updateCashOutOrders")
    @ResponseBody
    @RequiresPermissions("/withdraw/record/updateCashOutOrders")
    public ResponseEntity<Result> updateCashOutOrders(CashoutordersDTO dto){
        try {
            boolean flag = withdrawRecordService.updateCashOutOrders(dto);
            Result result = flag ? Result.succ() : Result.fail("风控审核单据失败");
            return ResponseEntity.ok(result);
        } catch (BusinessException e) {
            log.error("风控审核单据失败");
            return ResponseEntity.ok(Result.fail(e.getMessage()));
        }

    }

    /**
     * 财务状态审核
     *
     * @param dto
     * @return
     */
    @PostMapping(path = "/updateFinancialStatus")
    @ResponseBody
    @RequiresPermissions("/withdraw/record/updateFinancialStatus")
    public ResponseEntity<Result> updateFinancialStatus(CashoutordersDTO dto){
        try {
            boolean flag = withdrawRecordService.updateFinancialStatus(dto);
            Result result = flag ? Result.succ() : Result.fail("财务审核单据失败");
            return ResponseEntity.ok(result);
        } catch (BusinessException e) {
            log.error("财务审核单据失败");
            return ResponseEntity.ok(Result.fail(e.getMessage()));
        }
    }


}
