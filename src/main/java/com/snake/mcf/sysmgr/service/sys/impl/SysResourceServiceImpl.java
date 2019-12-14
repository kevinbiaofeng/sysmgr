package com.snake.mcf.sysmgr.service.sys.impl;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.repertory.entity.TbSysResource;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysResourceDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.ResourceGroup;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysResourceMapper;
import com.snake.mcf.sysmgr.service.sys.SysResourceService;
import com.snake.mcf.sysmgr.service.sys.SysUserService;
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
 * @ClassName SysResourceServiceImpl
 * @Author 大帅
 * @Date 2019/6/21 11:45
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SysResourceServiceImpl extends BaseServiceImpl implements SysResourceService {

    private static final String TREEGRID_STATE_CLOSED = "closed";//折叠

    private static final String TREEGRID_STATE_OPEND = "opend";//展开

    @Autowired
    private TbSysResourceMapper sysResourceMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<ResourceGroup> queryResourceTree(String sysUserId) {
        //校验sysUserId
        if(StringUtils.isBlank(sysUserId)) {
            throw new BusinessException("查询树形菜单,传入参数sysUserId为空", 1003);
        }
        //根据sysUserId查询sysuser不为空
        TbSysUserDTO userDto = sysUserService.querySysUserById(sysUserId);
        if(userDto == null) {
            throw new BusinessException("查询树形菜单,传入参数sysUserId={}对应的系统管理员不存在", 1003,sysUserId);
        }
        String isLocked = userDto.getIsLocked();
        if("1".equals(isLocked)) {
            throw new BusinessException("查询树形菜单,传入参数sysUserId={}对应的系统管理员已经被锁定", 1003,sysUserId);
        }
        String isDeleted = userDto.getIsDeleted();
        if("1".equals(isDeleted)) {
            throw new BusinessException("查询树形菜单,传入参数sysUserId={}对应的系统管理员已经被删除", 1003,sysUserId);
        }
        List<ResourceGroup> list = this.queryResourceTree1();
        log.info("菜单返回结果:{}",JsonUtils.toString(list));
        return list;
    }

    private List<ResourceGroup> queryResourceTree1() {
        log.info("{}","查询树形表格");

        Example example = new Example(TbSysResource.class);
        example.setOrderByClause(" sort ASC ");
        List<TbSysResource> list = sysResourceMapper.selectByExample(example);

        //查询一级菜单
        List<ResourceGroup> groups = this.queryResourceList("0", 1, list);
        if(CollectionUtils.isNotEmpty(groups)) {
            groups.forEach(group -> {
                String menuid = group.getMenuid();
                //查询二级菜单
                List<ResourceGroup> seconds = this.queryResourceList(menuid, 2, list);
                if(CollectionUtils.isNotEmpty(seconds)) {
                    group.setChildren(seconds);
                    seconds.forEach(s -> {
                        //查询三级菜单
                        String menuid2 = s.getMenuid();
                        List<ResourceGroup> thres = this.queryResourceList(menuid2, 3, list);
                        if(CollectionUtils.isNotEmpty(thres)) {
                            s.setChildren(thres);
                            thres.forEach(third -> {
                                //查询四级菜单
                                String menuid3 = third.getMenuid();
                                List<ResourceGroup> fourth = this.queryResourceList(menuid3, 4,list);
                                if(CollectionUtils.isNotEmpty(fourth)) {
                                    third.setChildren(fourth);
                                }
                            });
                        }
                    });
                }

            });
        }
        log.info("菜单查询结果:{}",JsonUtils.toString(groups));
        return groups;
    }

    /**
     * 根据父菜单id和等级查询
     *
     * @author: hengoking
     * @param parentMenuid
     * @param level
     * @return
     */
    private List<ResourceGroup> queryResourceList(String parentMenuid,Integer level,List<TbSysResource> listAll){
        List<TbSysResource> list = new ArrayList<>();
        for(TbSysResource tbSysResource: listAll){
            if(parentMenuid.equals(tbSysResource.getParentMenuid()) && level == tbSysResource.getLev()){
                list.add(tbSysResource);
            }
        }
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        //将数据库数据转成dto
        List<TbSysResourceDTO> resoutceDtoList = CommonBeans.copyNewList(list,TbSysResourceDTO.class);
        resoutceDtoList.forEach(srDto -> {
            String createdBy = srDto.getCreatedBy();
            if(StringUtils.isNotBlank(createdBy)) {
                TbSysUserDTO userDto = sysUserService.querySysUserById(createdBy);
                srDto.setCreatedByName(userDto.getName());
            }

            String updatedBy = srDto.getUpdatedBy();
            if(StringUtils.isNotBlank(updatedBy)) {
                TbSysUserDTO userDto = sysUserService.querySysUserById(updatedBy);
                srDto.setUpdatedByName(userDto.getName());
            }
            //判断是否是叶子节点   true 叶子节点   false 不是叶子节点
            String menuid = srDto.getMenuid();//根据 meneuId查询是否存在 子节点  如果存在
            List<TbSysResource> list1 = new ArrayList<>();
            for(TbSysResource tbSysResource: listAll){
                if(menuid.equals(tbSysResource.getParentMenuid())){
                    list1.add(tbSysResource);
                }
            }

            if(CollectionUtils.isNotEmpty(list1)) {
                //存在子节点
                srDto.setState(TREEGRID_STATE_CLOSED);
                srDto.setLeaf(false);
            }else {
                //不存在子节点
                srDto.setState(TREEGRID_STATE_OPEND);
                srDto.setLeaf(true);
            }
        });
        List<ResourceGroup> groupList = CommonBeans.copyNewList(resoutceDtoList, ResourceGroup.class);
        return groupList;
    }

    @Override
    public boolean saveResource(TbSysResourceDTO resourceDto) {
        //校验
        if(resourceDto == null) {
            throw new BusinessException("传入对象为空", 1002);
        }
        if(StringUtils.isBlank(resourceDto.getMenuid())) {
            throw new BusinessException("传入参数menuid为空", 1002);
        }
        if(StringUtils.isBlank(resourceDto.getParentMenuid())) {
            throw new BusinessException("传入参数parentMenuid为空", 1002);
        }
        if(StringUtils.isBlank(resourceDto.getCode())) {
            throw new BusinessException("传入参数code为空", 1002);
        }
        if(StringUtils.isBlank(resourceDto.getName())) {
            throw new BusinessException("传入参数name为空", 1002);
        }
        if(resourceDto.getLev() == null ) {
            throw new BusinessException("传入参数level为空", 1002);
        }
        if(!(resourceDto.getLev() == 1 ||  resourceDto.getLev() == 2 || resourceDto.getLev() == 3 || resourceDto.getLev() == 4)) {
            throw new BusinessException("传入参数level不符合规范:{}", 1002, resourceDto.getLev());
        }
        if(resourceDto.getLev() == 3) {
            if(StringUtils.isBlank(resourceDto.getUrl())) {
                throw new BusinessException("传入参数url为空", 1002);
            }
        }
        if(resourceDto.getSort() == null ) {
            throw new BusinessException("传入参数sort为空", 1002);
        }
        //新建资源
        resourceDto.setId(GeneratotUtils.generateNumber());
        resourceDto.setCreatedDate(new Date());//设置创建时间
        resourceDto.setCreatedBy(ShiroUtils.getSessionUserId());//设置当前操作人
        resourceDto.setUpdatedDate(new Date());
        resourceDto.setUpdatedBy(ShiroUtils.getSessionUserId());

        TbSysResource record = new TbSysResource();
        CommonBeans.copyProperties(resourceDto, record);

        int count = sysResourceMapper.insertSelective(record);
        if(count == 0) {
            throw new BusinessException("新增资源失败", 1002);
        }
        log.info("新增返回结果:{}", JsonUtils.toString(resourceDto));
        return count > 0;
    }

    @Override
    public TbSysResourceDTO querySysResourceById(String id) {
        log.info("根据id查询资源:{}",id);
        if(id == null) {
            return null;
        }
        TbSysResource sysResource = sysResourceMapper.selectByPrimaryKey(id);
        TbSysResourceDTO sysResourceDto = new TbSysResourceDTO();
        CommonBeans.copyPropertiesIgnoreNull(sysResource, sysResourceDto);
        log.info("根据id查询资源:{}",JsonUtils.toString(sysResourceDto));
        return sysResourceDto;
    }

    @Override
    public Integer deleteSysResource(String[] ids) {
        log.info("传入参数 ： ids---{}",JsonUtils.toString(ids));
        Integer count = 0;
        if(ArrayUtils.isNotEmpty(ids)) {
            Example example = new Example(TbSysResource.class);
            Example.Criteria criteria = example.createCriteria();
            List<String> values = Arrays.asList(ids);
            criteria.andIn("id",values);
            count = sysResourceMapper.deleteByExample(example);
        }
        return count;
    }

    @Override
    public boolean updateResource(TbSysResourceDTO resourceDto) {
        log.info("修改资源:{}",JsonUtils.toString(resourceDto));
        if(resourceDto == null) {
            throw new BusinessException("修改资源,传入对象为空", 1004);
        }
        String id = resourceDto.getId();
        if(StringUtils.isBlank(id)) {
            throw new BusinessException("修改资源,传入id为空", 1004);
        }
        String menuid = resourceDto.getMenuid();
        if(StringUtils.isBlank(menuid)) {
            throw new BusinessException("传入参数menuid为空", 1002);
        }
        String parentMenuid = resourceDto.getParentMenuid();
        if(StringUtils.isBlank(parentMenuid)) {
            throw new BusinessException("传入参数parentMenuid为空", 1002);
        }
        String name = resourceDto.getName();
        if(StringUtils.isBlank(name)) {
            throw new BusinessException("传入参数name为空", 1002);
        }
        Integer level = resourceDto.getLev();
        if(level == null ) {
            throw new BusinessException("传入参数level为空", 1002);
        }
        if(!(level == 1 ||  level == 2 || level == 3 || level == 4)) {
            throw new BusinessException("传入参数level不符合规范:{}", 1002, resourceDto.getLev());
        }
        if(resourceDto.getLev() == 3 || resourceDto.getLev() == 4) {
            String url = resourceDto.getUrl();
            if(StringUtils.isBlank(url)) {
                throw new BusinessException("传入参数url为空", 1002);
            }
        }
        if(resourceDto.getLev() == 4){
            resourceDto.setPermission(resourceDto.getUrl());
        }
        Integer sort = resourceDto.getSort();
        if(sort == null ) {
            throw new BusinessException("传入参数sort为空", 1002);
        }
        resourceDto.setCode(menuid);
        resourceDto.setUpdatedBy(ShiroUtils.getSessionUserId());
        resourceDto.setUpdatedDate(new Date());
        log.info("修改资源,完整数据:{}",JsonUtils.toString(resourceDto));
        //调用远程服务器新增
        TbSysResource record = new TbSysResource();
        CommonBeans.copyPropertiesIgnoreNull(resourceDto, record);
        int count = sysResourceMapper.updateByPrimaryKeySelective(record);
        if(count == 0) {
            throw new BusinessException("修改资源失败", 1004);
        }
        return count > 0;
    }
}
