package com.snake.mcf.sysmgr.service.back.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.repertory.entity.BindBankCardsChangeRecord;
import com.snake.mcf.sysmgr.repertory.entity.dto.BindBankCardsChangeRecordDto;
import com.snake.mcf.sysmgr.repertory.entity.vo.BindBankCardsChangeRecordVo;
import com.snake.mcf.sysmgr.repertory.mapper.BindBankCardsChangeRecordMapper;
import com.snake.mcf.sysmgr.service.back.BindBankCardsChangeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class BindBankCardsChangeRecordServiceImpl extends BaseServiceImpl implements BindBankCardsChangeRecordService {

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private BindBankCardsChangeRecordMapper bindBankCardsChangeRecordMapper;

    @Override
    public PageResult<BindBankCardsChangeRecordDto> queryBindBankCardsChangeRecordWithPage(EasyPageFilter pageFilter, BindBankCardsChangeRecordVo vo) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter), JsonUtils.toString(vo));
        PageResult<BindBankCardsChangeRecordDto> pageResult = this.queryBindBankCardsChangeRecordWithPage1(pageFilter, vo);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if (pageResult instanceof EasyPageResult) {
            EasyPageResult<BindBankCardsChangeRecordDto> result = (EasyPageResult<BindBankCardsChangeRecordDto>) pageResult;
            List<BindBankCardsChangeRecordDto> rows = result.getRows();
            rows.forEach((rd) -> {
                Integer bankChoice = rd.getBankChoice();
                String bankchoiceDesc = DictionaryConstant.getBankchoiceDesc(bankChoice);
                rd.setBankChoiceDesc(bankchoiceDesc);
            });
        }
        return pageResult;
    }

    private PageResult<BindBankCardsChangeRecordDto> queryBindBankCardsChangeRecordWithPage1(EasyPageFilter pageFilter, BindBankCardsChangeRecordVo vo) {
        log.info("服务端:page={}", pageFilter.getPage());
        log.info("服务端:rows={}", pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}", easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(BindBankCardsChangeRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if (null != vo) {
            //用户ID
            Integer userId = vo.getUserId();
            if (null != userId) {
                criteria.andEqualTo("userId", userId);
            }
            //开始时间
            Date createTimeStart = vo.getCreateTimeStart();
            if (null != createTimeStart) {
                criteria.andGreaterThanOrEqualTo("createTime", createTimeStart);
            }
            //结束时间
            Date createTimeEnd = vo.getCreateTimeEnd();
            if (null != createTimeEnd) {
                criteria.andLessThanOrEqualTo("createTime", createTimeEnd);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if (!ShiroUtils.isAdminSessionUserId()) {
                criteria.andEqualTo("merchant", ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}", orderByClause);
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<BindBankCardsChangeRecord> users = bindBankCardsChangeRecordMapper.selectByExample(example);
        //结果
        PageResult<BindBankCardsChangeRecordDto> pageResult = easyPageQuery.queryResult(users, BindBankCardsChangeRecordDto.class);
        log.info("分页结果:pageResult={}", JsonUtils.toString(pageResult));
        return pageResult;
    }
}
