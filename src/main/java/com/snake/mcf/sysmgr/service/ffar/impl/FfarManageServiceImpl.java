package com.snake.mcf.sysmgr.service.ffar.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.ffar.FfarManageMapper;
import com.snake.mcf.sysmgr.repertory.entity.Imgroupproperty;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgroupmemberDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgrouppropertyDTO;
import com.snake.mcf.sysmgr.repertory.mapper.ImgrouppropertyMapper;
import com.snake.mcf.sysmgr.service.ffar.FfarManageService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName FfarManageServiceImpl
 * @Author 大帅
 * @Date 2019/7/15 12:10
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class FfarManageServiceImpl extends BaseServiceImpl implements FfarManageService {

    @Autowired
    private FfarManageMapper ffarManageMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private ImgrouppropertyMapper imgrouppropertyMapper;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<ImgrouppropertyDTO> queryIMGroupPropertyWithPage(EasyPageFilter pageFilter, ImgrouppropertyDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<ImgrouppropertyDTO> pageResult = this.queryIMGroupPropertyWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<ImgrouppropertyDTO> result = (EasyPageResult<ImgrouppropertyDTO>) pageResult;
            List<ImgrouppropertyDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //状态 （1：可用 2：不可用）
                Integer groupstatus = rd.getGroupstatus();
                if(groupstatus != null){
                    String groupstatusDesc = DictionaryConstant.getGroupstatusDesc(groupstatus);
                    rd.setGroupstatusDesc(groupstatusDesc);
                }
                
                String createrNickName = rd.getCreaternickname();
                if(StringUtils.isNotEmpty(createrNickName)) {
                	rd.setCreaternickname(StringUtils.replaceStar(AESCBCUtils.decrypt(createrNickName, configResource.getAesUserKey()), null));
                }

                //亲友圈人数/上限
                // 成员数量
                Integer membercount = rd.getMembercount();
                //最大成员数量
                Integer maxmembercount = rd.getMaxmembercount();
                String limitDesc = membercount + "/" + maxmembercount;
                rd.setLimitDesc(limitDesc);

            });
        }
        return pageResult;
    }

    private PageResult<ImgrouppropertyDTO> queryIMGroupPropertyWithPage1(EasyPageFilter filter, ImgrouppropertyDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Recordinsure.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            //圈主游戏ID
            Integer creatergameid = dto.getCreatergameid();
            if(creatergameid != null){
                map.put("creatergameid",creatergameid);
            }

            //圈主游戏昵称
            String creaternickname = dto.getCreaternickname();
            if(StringUtils.isNotBlank(creaternickname)){
                map.put("creaternickname", AESCBCUtils.encrypt(creaternickname, configResource.getAesUserKey()));
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        //List<Recordinsure> list = recordinsureMapper.selectByExample(example);
        List<ImgrouppropertyDTO> list = ffarManageMapper.queryIMGroupPropertyWithPage(map);
        //结果
        PageResult<ImgrouppropertyDTO> pageResult = easyPageQuery.queryResult(list, ImgrouppropertyDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean groupstatusUpate(ImgrouppropertyDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id编辑,传入对象为空", 1004);
        }
        String groupids = dto.getGroupids();
        if(StringUtils.isBlank(groupids)){
            throw new BusinessException("根据id编辑,传入对象 groupids 为空", 1004);
        }
        Integer groupstatus = dto.getGroupstatus();
        if(groupstatus == null){
            throw new BusinessException("根据id编辑,传入对象 groupstatus 为空", 1004);
        }

        int count = 0;
        String[] arr = groupids.split(",");
        for (String id : arr) {
            Imgroupproperty record = new Imgroupproperty();
            record.setGroupid(Long.valueOf(id));
            record.setGroupstatus(groupstatus);
            imgrouppropertyMapper.updateByPrimaryKeySelective(record);
            count ++;
        }
        return count > 0;
    }

    @Override
    public PageResult<ImgroupmemberDTO> queryGroupMemberWithPage(EasyPageFilter pageFilter, ImgroupmemberDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<ImgroupmemberDTO> pageResult = this.queryGroupMemberWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<ImgroupmemberDTO> result = (EasyPageResult<ImgroupmemberDTO>) pageResult;
            List<ImgroupmemberDTO> rows = result.getRows();

            rows.forEach( (rd) -> {
            	rd.setNickname(StringUtils.replaceStar(AESCBCUtils.decrypt(rd.getNickname(), configResource.getAesUserKey()), null));
            });
        }
        return pageResult;
    }

    private PageResult<ImgroupmemberDTO> queryGroupMemberWithPage1(EasyPageFilter filter, ImgroupmemberDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Recordinsure.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            Long groupid = dto.getGroupid();
            if(groupid != null){
                map.put("groupid",groupid);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        //List<Recordinsure> list = recordinsureMapper.selectByExample(example);
        List<ImgroupmemberDTO> list = ffarManageMapper.queryGroupMemberWithPage(map);
        //结果
        PageResult<ImgroupmemberDTO> pageResult = easyPageQuery.queryResult(list, ImgroupmemberDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
