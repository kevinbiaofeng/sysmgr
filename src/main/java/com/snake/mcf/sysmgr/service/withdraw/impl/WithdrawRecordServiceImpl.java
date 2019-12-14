package com.snake.mcf.sysmgr.service.withdraw.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.GoldUtils;
import com.snake.mcf.common.utils.GsonUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.withdraw.WithdrawRecordMapper;
import com.snake.mcf.sysmgr.repertory.entity.Cashoutorders;
import com.snake.mcf.sysmgr.repertory.entity.GameScoreInfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.CashoutordersDTO;
import com.snake.mcf.sysmgr.repertory.mapper.CashoutordersMapper;
import com.snake.mcf.sysmgr.repertory.mapper.GameScoreInfoMapper;
import com.snake.mcf.sysmgr.service.withdraw.WithdrawRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @ClassName WithdrawRecordServiceImpl
 * @Author 大帅
 * @Date 2019/7/26 20:55
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WithdrawRecordServiceImpl extends BaseServiceImpl implements WithdrawRecordService {

    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private CashoutordersMapper cashoutordersMapper;

    @Autowired
    private GameScoreInfoMapper gameScoreInfoMapper;

    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<CashoutordersDTO> queryCashOutOrdersWithPage(EasyPageFilter pageFilter, CashoutordersDTO dto) {
        log.info("param : {}", JsonUtils.toString(dto));
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter), JsonUtils.toString(dto));
        PageResult<CashoutordersDTO> pageResult = this.queryCashOutOrdersWithPage1(pageFilter, dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if (pageResult instanceof EasyPageResult) {
            EasyPageResult<CashoutordersDTO> result = (EasyPageResult<CashoutordersDTO>) pageResult;
            List<CashoutordersDTO> rows = result.getRows();

            rows.forEach((rd) -> {

                //风控审核状态
                Integer reviewstatus = rd.getReviewstatus();
                if (reviewstatus != null) {
                    String reviewstatusDesc = DictionaryConstant.getReviewstatusDesc(reviewstatus);
                    rd.setReviewstatusDesc(reviewstatusDesc);
                }

                //财务审核状态
                Integer financialStatus = rd.getFinancialStatus();
                if (financialStatus != null) {
                    String financialStatusDesc = DictionaryConstant.getFinancialStatusDesc(financialStatus);
                    rd.setFinancialStatusDesc(financialStatusDesc);
                }

                //兑换金币  / 100
                Integer score = rd.getScore();
                if (score != null) {
                    rd.setScoreDouble(GoldUtils.reduce(score.longValue()));
                }

                //实际提现金额
                Long realmoney = rd.getRealmoney();
                if (realmoney != null) {
                    rd.setRealMoneyDouble(GoldUtils.reduce(realmoney));
                }

                //提现手续费 / 100
                Long servicefee = rd.getServicefee();
                if (servicefee != null) {
                    rd.setServicefeeDouble(GoldUtils.reduce(servicefee));
                }

                //绑定类型
                Integer bankchoice = rd.getBankchoice();
                if (bankchoice != null) {
                    String bankchoiceDesc = DictionaryConstant.getBankchoiceDesc(bankchoice);
                    rd.setBankchoiceDesc(bankchoiceDesc);
                }

                String nickName = rd.getNickname();
                if (StringUtils.isNotEmpty(nickName)) {
                    rd.setNickname(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }

                String idCardName = rd.getIdcardname();
                if (StringUtils.isNotEmpty(idCardName)) {
                    rd.setIdcardname(StringUtils.replaceStar(AESCBCUtils.decrypt(idCardName, configResource.getAesUserKey()), null));
                }
            });
        }
        return pageResult;
    }

    private PageResult<CashoutordersDTO> queryCashOutOrdersWithPage1(EasyPageFilter filter, CashoutordersDTO dto) {
        log.info("服务端:page={}", filter.getPage());
        log.info("服务端:rows={}", filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}", easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Cashoutorders.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String, Object> map = new HashMap<>();
        if (dto != null) {

            //gameid
            Integer gameid = dto.getGameid();
            if (gameid != null) {
                map.put("gameid", gameid);
            }

            //nickname
            String nickname = dto.getNickname();
            if (StringUtils.isNotBlank(nickname)) {
                map.put("nickname", AESCBCUtils.encrypt(nickname, configResource.getAesUserKey()));
            }

            //reviewstatus
            Integer reviewstatus = dto.getReviewstatus();
            if (reviewstatus != null) {
                map.put("reviewstatus", reviewstatus);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if (!ShiroUtils.isAdminSessionUserId()) {
                map.put("merchant", ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}", orderByClause);

        //查询
        //List<Cashoutorders> list = cashoutordersMapper.selectByExample(example);
        List<CashoutordersDTO> list = withdrawRecordMapper.queryCashOutOrdersWithPage(map);
        log.info("map：{}", map.toString());
        log.info("查询出来的数据：{}", GsonUtils.toString(list));
        //结果
        PageResult<CashoutordersDTO> pageResult = easyPageQuery.queryResult(list, CashoutordersDTO.class);
        log.info("分页结果:pageResult={}", JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean updateCashOutOrders(CashoutordersDTO dto) {
        if (dto == null) {
            throw new BusinessException("审核,传入对象为空");
        }
        String orderids = dto.getOrderids();
        if (StringUtils.isBlank(orderids)) {
            throw new BusinessException("审核,传入对象 orderids 为空");
        }
        Integer reviewstatus = dto.getReviewstatus();
        if (reviewstatus == null) {
            throw new BusinessException("审核,传入对象 reviewstatus 为空");
        }

        int count = 0;
        String[] arr = orderids.split(",");
        if (reviewstatus.intValue() == 1) {
            /**
             * 审核通过
             * 1 修改状态
             */
            for (String id : arr) {
                Cashoutorders record = new Cashoutorders();
                record.setOrderid(id);
                record.setReviewstatus(reviewstatus);
                record.setRemark(dto.getRemark());
                record.setReviewer(ShiroUtils.getSessionLoginName());
                record.setReviewtime(new Date());
                cashoutordersMapper.updateByPrimaryKeySelective(record);
                count++;
            }
        } else if (reviewstatus.intValue() == 2) {
            /**
             * 审核不通过
             * 1 把 金币 返回到保险柜
             * 2 修改状态
             */
            for (String id : arr) {
                // 1 把 金币 返回到保险柜
                Cashoutorders cashoutorders = cashoutordersMapper.selectByPrimaryKey(id);
                if (cashoutorders != null) {
                    Integer userid = cashoutorders.getUserid();
                    GameScoreInfo gameScoreInfo = gameScoreInfoMapper.selectByPrimaryKey(userid);
                    if (null == gameScoreInfo) {
                        throw new BusinessException("未找到记录，风控审核单据失败！");
                    }
                    Long insurescore = gameScoreInfo.getInsurescore();
                    GameScoreInfo record1 = new GameScoreInfo();
                    record1.setUserid(userid);
                    record1.setInsurescore(insurescore.longValue() + cashoutorders.getScore().longValue());
                    gameScoreInfoMapper.updateByPrimaryKeySelective(record1);
                    // 2 修改状态
                    Cashoutorders record = new Cashoutorders();
                    record.setOrderid(id);
                    record.setReviewstatus(reviewstatus);
                    record.setRemark(dto.getRemark());
                    record.setReviewer(ShiroUtils.getSessionLoginName());
                    record.setReviewtime(new Date());
                    cashoutordersMapper.updateByPrimaryKeySelective(record);
                    count++;
                }
            }
        }
        return count > 0;
    }

    @Override
    public boolean updateFinancialStatus(CashoutordersDTO dto) {
        String orderids = dto.getOrderids();
        String[] arr = orderids.split(",");
        Integer financialStatus = dto.getFinancialStatus();
        int count = 0;
        if (financialStatus == 1) {
            /**
             * 财务审核通过
             * 1 判断是否风险审核通过 1代表风险审核通过
             * 2 修改状态
             */
            for (String id : arr) {
                Cashoutorders cashoutorders = cashoutordersMapper.selectByPrimaryKey(id);
                if (1 == cashoutorders.getReviewstatus()) {
                    cashoutorders.setFinancialStatus(financialStatus);
                    cashoutorders.setFinancialOperator(ShiroUtils.getSessionLoginName());
                    cashoutorders.setFinancialTime(new Date());
                    cashoutordersMapper.updateByPrimaryKeySelective(cashoutorders);
                    count++;
                } else {
                    throw new BusinessException("请先确保风险审核通过！");
                }
            }
        } else if (financialStatus == 2) {
            /**
             * 财务审核不通过
             * 1 修改状态
             */
            for (String id : arr) {
                Cashoutorders record = new Cashoutorders();
                record.setOrderid(id);
                record.setFinancialStatus(financialStatus);
                record.setFinancialOperator(ShiroUtils.getSessionLoginName());
                record.setFinancialTime(new Date());
                cashoutordersMapper.updateByPrimaryKeySelective(record);
                count++;
            }
        }
        return count > 0;
    }


}
