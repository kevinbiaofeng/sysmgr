package com.snake.mcf.sysmgr.service.account.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.mapper.account.AccountTaskMapper;
import com.snake.mcf.sysmgr.repertory.entity.Taskinfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TaskinfoDTO;
import com.snake.mcf.sysmgr.repertory.mapper.TaskinfoMapper;
import com.snake.mcf.sysmgr.service.account.AccountTaskService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName AccountTaskServiceImpl
 * @Author 大帅
 * @Date 2019/6/27 11:53
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class AccountTaskServiceImpl implements AccountTaskService {

    @Autowired
    private TaskinfoMapper taskinfoMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private AccountTaskMapper accountTaskMapper;

    @Override
    public PageResult<TaskinfoDTO> queryTaskWithPage(EasyPageFilter pageFilter, TaskinfoDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<TaskinfoDTO> pageResult = this.queryTaskWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<TaskinfoDTO> result = (EasyPageResult<TaskinfoDTO>) pageResult;
            List<TaskinfoDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //任务类型
                Integer tasktype = rd.getTasktype();
                if(tasktype != null){
                    String tasktypeDesc = DictionaryConstant.getTasktypeDesc(tasktype);
                    rd.setTasktypeDesc(tasktypeDesc);
                }

                //时间类型
                Integer timetype = rd.getTimetype();
                if(timetype != null){
                    String timetypeDesc = DictionaryConstant.getTimetypeDesc(timetype);
                    rd.setTimetypeDesc(timetypeDesc);
                }

                //用户类型
                Integer usertype = rd.getUsertype();
                if(usertype != null){
                    String usertypeDesc = DictionaryConstant.getUsertypeDesc(usertype);
                    rd.setUsertypeDesc(usertypeDesc);
                }

                //冻结状态
                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                StringBuilder sb = new StringBuilder();
                String taskaward = rd.getTaskaward();
                if(StringUtils.isNotBlank(taskaward)){
                    String[] awards = taskaward.split(";");
                    for (String award : awards) {
                        String[] item = award.split(":");
                        String awardtypeDesc = DictionaryConstant.getAwardtypeDesc(Integer.valueOf(item[0]));
                        //awardtypeDesc + ":" + item[1] + ",";
                        sb.append(awardtypeDesc);
                        sb.append(":");
                        sb.append(item[1]);
                        sb.append(",");
                    }
                    String value = sb.toString().substring(0,sb.toString().length() - 1);
                    rd.setTaskawardDesc(value);
                }
            });

        }
        return pageResult;
    }

    private PageResult<TaskinfoDTO> queryTaskWithPage1(EasyPageFilter filter, TaskinfoDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Taskinfo.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer nullity = dto.getNullity();
            if(nullity != null){
                criteria.andEqualTo("nullity",nullity);
            }

            String taskname = dto.getTaskname();
            if(StringUtils.isNotBlank(taskname)){
                //criteria.andEqualTo("taskname",taskname);
                criteria.andLike("taskname", PayplatformConstant.SPLIT_SYMBOL_PER + taskname + PayplatformConstant.SPLIT_SYMBOL_PER);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Taskinfo> list = taskinfoMapper.selectByExample(example);
        //结果
        PageResult<TaskinfoDTO> pageResult = easyPageQuery.queryResult(list, TaskinfoDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public List<ComboBoxDTO> loadGameRoomComboData(Integer nullity) {
        Map<String,Object> map = new HashMap<>();
        map.put("nullity",nullity);
        List<ComboBoxDTO> list = new LinkedList<>();
        //压入栈顶
        ComboBoxDTO comboBoxDTO = new ComboBoxDTO();
        comboBoxDTO.setId("0");
        comboBoxDTO.setText("不限游戏");
        list.add(0,comboBoxDTO);

        //查询
        List<ComboBoxDTO> list1 = accountTaskMapper.loadGameRoomComboData(map);
        if(CollectionUtils.isNotEmpty(list1)){
            list.addAll(list1);
        }
        return list;
    }

    @Override
    public List<ComboBoxDTO> loadGameRoomInfoComboData(Integer kindid) {
        Map<String,Object> map = new HashMap<>();
        map.put("kindid",kindid);
        List<ComboBoxDTO> list = new LinkedList<>();
        //压入栈顶
        ComboBoxDTO comboBoxDTO = new ComboBoxDTO();
        comboBoxDTO.setId("0");
        comboBoxDTO.setText("不限房间");
        list.add(0,comboBoxDTO);

        //查询
        List<ComboBoxDTO> list1 = accountTaskMapper.loadGameRoomInfoComboData(map);
        if(CollectionUtils.isNotEmpty(list1)){
            list.addAll(list1);
        }
        return list;
    }


    @Override
    public boolean saveTask(TaskinfoDTO dto) {
        String taskaward = this.getTaskaward(dto.getGold(),dto.getDiamond(),dto.getMedal());
        dto.setTaskaward(taskaward);
        // 比赛id
        dto.setMatchid(0);
        //设置
        //是否是管理员  不是 需要加一个商户号
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }
        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());
        Taskinfo record = new Taskinfo();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = taskinfoMapper.insertSelective(record);
        return count > 0;
    }

    private String getTaskaward(Long gold,Long diamond,Long medal){
        String taskaward = null;
        StringBuilder sb = new StringBuilder();
        if(gold > 0){
            sb.append("1:").append(gold.toString()).append(";");
        }
        if(diamond > 0){
            sb.append("2:").append(diamond.toString()).append(";");
        }
        if(medal > 0){
            sb.append("3:").append(medal.toString()).append(";");
        }
        taskaward = sb.toString();
        if(StringUtils.isNotBlank(taskaward)){
            taskaward = taskaward.substring(0,taskaward.length() - 1);
        }
        return taskaward;
    }

    @Override
    public TaskinfoDTO queryTaskById(TaskinfoDTO dto) {
        if(dto == null){
            throw new BusinessException("查询任务,传入对象为空", 1004);
        }
        if(dto.getTaskid() == null){
            throw new BusinessException("查询任务,传入对象为空", 1004);
        }
        Taskinfo taskinfo = taskinfoMapper.selectByPrimaryKey(dto.getTaskid());
        TaskinfoDTO taskinfoDTO = new TaskinfoDTO();
        CommonBeans.copyPropertiesIgnoreNull(taskinfo,taskinfoDTO);
        String taskaward = taskinfoDTO.getTaskaward();
        /**
         * 奖励类型
         * 0 其他
         * 1 金币
         * 2 钻石
         * 3 奖券
         */
        if(StringUtils.isBlank(taskaward)){
            taskinfoDTO.setGold(0L);
            taskinfoDTO.setDiamond(0L);
            taskinfoDTO.setMedal(0L);
        }else{
            String[] awardArr = taskaward.split(";");
            for (String award : awardArr) {
                String[] typeArr = award.split(":");
                if("1".equals(typeArr[0])){
                    if(StringUtils.isNotBlank(typeArr[1])){
                        taskinfoDTO.setGold(Long.valueOf(typeArr[1]));
                    }else{
                        taskinfoDTO.setGold(0L);
                    }
                }else if("2".equals(typeArr[0])){
                    if(StringUtils.isNotBlank(typeArr[1])){
                        taskinfoDTO.setDiamond(Long.valueOf(typeArr[1]));
                    }else {
                        taskinfoDTO.setDiamond(0L);
                    }
                }else if("3".equals(typeArr[0])){
                    if(StringUtils.isNotBlank(typeArr[1])){
                        taskinfoDTO.setMedal(Long.valueOf(typeArr[1]));
                    }else{
                        taskinfoDTO.setMedal(0L);
                    }
                }
            }
        }
        return taskinfoDTO;
    }

    @Override
    public boolean updateTask(TaskinfoDTO dto) {
        if(dto == null){
            throw new BusinessException("查询任务,传入对象为空", 1004);
        }
        if(dto.getTaskid() == null){
            throw new BusinessException("查询任务,传入对象为空", 1004);
        }
        String taskaward = this.getTaskaward(dto.getGold(),dto.getDiamond(),dto.getMedal());
        dto.setTaskaward(taskaward);

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Taskinfo record = new Taskinfo();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = taskinfoMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeTaskByIds(Integer[] taskids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(taskids)){
            list = Arrays.asList(taskids);
        }

        int count = 0 ;
        Example example = new Example(Taskinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("taskid",list);
        taskinfoMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
