package com.snake.mcf.sysmgr.service.back.impl;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.back.BackLogMapper;
import com.snake.mcf.sysmgr.repertory.entity.TbSysLog;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysLogMapper;
import com.snake.mcf.sysmgr.service.back.BackLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName BackLogServiceImpl
 * @Author 大帅
 * @Date 2019/7/16 11:43
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class BackLogServiceImpl extends BaseServiceImpl implements BackLogService {

    @Autowired
    private BackLogMapper backLogMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private TbSysLogMapper tbSysLogMapper;

    @Override
    public PageResult<TbSysLogDTO> querySysLogWithPage(EasyPageFilter pageFilter, TbSysLogDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<TbSysLogDTO> pageResult = this.querySysLogWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<TbSysLogDTO> result = (EasyPageResult<TbSysLogDTO>) pageResult;
            List<TbSysLogDTO> rows = result.getRows();
            rows.forEach((rd) -> {

            });
        }
        return pageResult;
    }

    private PageResult<TbSysLogDTO> querySysLogWithPage1(EasyPageFilter pageFilter, TbSysLogDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(TbSysLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null) {

            //账号
            String account = dto.getAccount();
            if(StringUtils.isNotBlank(account)){
                criteria.andEqualTo("account",account);
            }

            //开始时间
            String createdDateStartStr = dto.getCreatedDateStartStr();
            if(StringUtils.isNotBlank(createdDateStartStr)){
//                Date date = DateUtils.parseDate(createdDateStartStr + DateUtils.DF_START, DateUtils.DF_YYYYMMDDHHMMSS);
                criteria.andGreaterThanOrEqualTo("createdDate",createdDateStartStr);
            }

            //结束时间
            String createdDateEndStr = dto.getCreatedDateEndStr();
            if(StringUtils.isNotBlank(createdDateEndStr)){
//                Date date = DateUtils.parseDate(createdDateStartStr + DateUtils.DF_END, DateUtils.DF_YYYYMMDDHHMMSS);
                criteria.andLessThanOrEqualTo("createdDate",createdDateEndStr);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<TbSysLog> users = tbSysLogMapper.selectByExample(example);
        //结果
        PageResult<TbSysLogDTO> pageResult = easyPageQuery.queryResult(users, TbSysLogDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
