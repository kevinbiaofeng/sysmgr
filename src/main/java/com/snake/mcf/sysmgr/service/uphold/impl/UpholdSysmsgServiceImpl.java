package com.snake.mcf.sysmgr.service.uphold.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.uphold.UpholdSysmsgMapper;
import com.snake.mcf.sysmgr.repertory.entity.Systemmessage;
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemmessageDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.GameInfoGroup;
import com.snake.mcf.sysmgr.repertory.mapper.SystemmessageMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdSysmsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UpholdSysmsgServiceImpl
 * @Author 大帅
 * @Date 2019/7/1 11:14
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdSysmsgServiceImpl extends BaseServiceImpl implements UpholdSysmsgService {

    private static final String TREEGRID_STATE_CLOSED = "closed";//折叠

    private static final String TREEGRID_STATE_OPEND = "opend";//展开

    @Autowired
    private SystemmessageMapper systemmessageMapper;

    @Autowired
    private UpholdSysmsgMapper upholdSysmsgMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;


    @Override
    public PageResult<SystemmessageDTO> querySysmsgWithPage(EasyPageFilter pageFilter, SystemmessageDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<SystemmessageDTO> pageResult = this.querySysmsgWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<SystemmessageDTO> result = (EasyPageResult<SystemmessageDTO>) pageResult;
            List<SystemmessageDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //消息类型
                Integer messagetype = rd.getMessagetype();
                if(messagetype != null){
                    String messagetypeDesc = DictionaryConstant.getMessagetypeDesc(messagetype);
                    rd.setMessagetypeDesc(messagetypeDesc);
                }

                //禁用标识
                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }


            });
        }
        return pageResult;
    }

    private PageResult<SystemmessageDTO> querySysmsgWithPage1(EasyPageFilter filter, SystemmessageDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Systemmessage.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer messagetype = dto.getMessagetype();
            if(messagetype != null){
                criteria.andEqualTo("messagetype",messagetype);
            }

            Integer nullity = dto.getNullity();
            if(nullity != null){
                criteria.andEqualTo("nullity",nullity);
            }

            //是否是管理员  不是 需要加一个商户号
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchant", ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Systemmessage> list = systemmessageMapper.selectByExample(example);
        //结果
        PageResult<SystemmessageDTO> pageResult = easyPageQuery.queryResult(list, SystemmessageDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public List<GameInfoGroup> loadGameIndoData(Integer id) {
        //查询那些选中的房间
        List<String> idList = new ArrayList<>();
        if(id != null){
            Systemmessage systemmessage = systemmessageMapper.selectByPrimaryKey(id);
            String serverrange = systemmessage.getServerrange();
            String[] arr = serverrange.split(",");
            idList = Arrays.asList(arr);
        }
        List<GameInfoGroup> list = new ArrayList<>();

        //一级目录
        GameInfoGroup rootGroup = new GameInfoGroup();
        rootGroup.setId("-1");
        rootGroup.setText("游戏列表");
        rootGroup.setState(TREEGRID_STATE_CLOSED);

        //查询二级目录
        //List<GameInfoGroup> secondGroup = new ArrayList<>();
        List<GameInfoGroup> secondGroup = upholdSysmsgMapper.loadSecondGroup();

        if(CollectionUtils.isNotEmpty(secondGroup)){
            /*secondGroup.forEach( (kind) -> {

                String kindId = kind.getId();
                List<GameInfoGroup> threeGroup = upholdSysmsgMapper.loadThreeGroup(Integer.valueOf(kindId));
                kind.setChildren(threeGroup);
            });*/
            for (GameInfoGroup sg : secondGroup) {
                String kindId = sg.getId();
                List<GameInfoGroup> threeGroup = upholdSysmsgMapper.loadThreeGroup(Integer.valueOf(kindId));
                if(CollectionUtils.isNotEmpty(idList)){
                    for (GameInfoGroup tg : threeGroup) {
                        String id1 = tg.getId();
                        if(idList.contains(id1)){
                            //如果包含
                            tg.setChecked(true);
                        }
                    }
                }
                sg.setChildren(threeGroup);
            }
        }

        rootGroup.setChildren(secondGroup);

        list.add(rootGroup);

        log.info("树形房间下拉:{}",JsonUtils.toString(list));

        return list;
    }

    @Override
    public boolean saveSysmsg(SystemmessageDTO dto) {

        String starttimeStr = dto.getStarttimeStr();
        if(StringUtils.isNotBlank(starttimeStr)){
            Date date = DateUtils.parseDate(starttimeStr, DateUtils.DF_YYYYMMDDHHMMSS);
            dto.setStarttime(date);
        }

        String concludetimeStr = dto.getConcludetimeStr();
        if(StringUtils.isNotBlank(concludetimeStr)){
            Date date = DateUtils.parseDate(concludetimeStr, DateUtils.DF_YYYYMMDDHHMMSS);
            dto.setConcludetime(date);
        }

        dto.setCreatedate(new Date());
        dto.setCreatemasterid(Integer.valueOf(ShiroUtils.getSessionUserId()));
        dto.setUpdatedate(new Date());
        dto.setUpdatemasterid(Integer.valueOf(ShiroUtils.getSessionUserId()));
        dto.setUpdatecount(0);

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        Systemmessage record = new Systemmessage();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = systemmessageMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public SystemmessageDTO querySysmsgById(SystemmessageDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入 对象 为空");
        }
        if(dto.getId() == null){
            throw new BusinessException("根据id查询,传入 对象 id 为空");
        }
        Systemmessage systemmessage = systemmessageMapper.selectByPrimaryKey(dto.getId());
        SystemmessageDTO record = new SystemmessageDTO();
        CommonBeans.copyPropertiesIgnoreNull(systemmessage,record);

        Date concludetime = record.getConcludetime();
        if(concludetime != null){
            String format = DateUtils.format(concludetime, DateUtils.DF_YYYYMMDDHHMMSS);
            record.setConcludetimeStr(format);
        }

        Date starttime = record.getStarttime();
        if(starttime != null){
            String format = DateUtils.format(starttime, DateUtils.DF_YYYYMMDDHHMMSS);
            record.setStarttimeStr(format);
        }

        return record;
    }

    @Override
    public boolean updateSysmsg(SystemmessageDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id修改,传入 对象 为空");
        }
        if(dto.getId() == null){
            throw new BusinessException("根据id修改,传入 对象 id 为空");
        }

        String starttimeStr = dto.getStarttimeStr();
        if(StringUtils.isNotBlank(starttimeStr)){
            Date date = DateUtils.parseDate(starttimeStr, DateUtils.DF_YYYYMMDDHHMMSS);
            dto.setStarttime(date);
        }

        String concludetimeStr = dto.getConcludetimeStr();
        if(StringUtils.isNotBlank(concludetimeStr)){
            Date date = DateUtils.parseDate(concludetimeStr, DateUtils.DF_YYYYMMDDHHMMSS);
            dto.setConcludetime(date);
        }

        dto.setUpdatedate(new Date());
        dto.setUpdatemasterid(Integer.valueOf(ShiroUtils.getSessionUserId()));
        dto.setUpdatecount(0);

        Systemmessage record = new Systemmessage();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = systemmessageMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean batchTransfer(SystemmessageDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id修改,传入 对象 为空");
        }
        String ids = dto.getIds();
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("根据id修改,传入 对象 ids 为空");
        }

        int count = 0;
        String[] arr = ids.split(",");
        for (String id: arr) {
            Systemmessage record = new Systemmessage();
            record.setId(Integer.valueOf(id));
            record.setNullity(dto.getNullity());
            systemmessageMapper.updateByPrimaryKeySelective(record);
            count++;
        }
        return count > 0;
    }

    @Override
    public boolean removeSysmsgByIds(Integer[] ids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(ids)){
            list = Arrays.asList(ids);
        }

        int count = 0;
        Example example = new Example(Systemmessage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",list);
        systemmessageMapper.deleteByExample(example);
        count ++;
        return count > 0;

    }
}
