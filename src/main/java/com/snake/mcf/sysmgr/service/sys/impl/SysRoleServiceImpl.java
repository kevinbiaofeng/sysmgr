package com.snake.mcf.sysmgr.service.sys.impl;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.EasyPagingQuery;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.GeneratotUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.sys.SysRoleMapper;
import com.snake.mcf.sysmgr.repertory.entity.TbSysResource;
import com.snake.mcf.sysmgr.repertory.entity.TbSysRole;
import com.snake.mcf.sysmgr.repertory.entity.TbSysRoleResource;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleResourceDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.RoleResourceGroup;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysResourceMapper;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysRoleMapper;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysRoleResourceMapper;
import com.snake.mcf.sysmgr.service.sys.SysRoleService;
import com.snake.mcf.sysmgr.service.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @ClassName SysRoleServiceImpl
 * @Author 大帅
 * @Date 2019/6/21 10:34
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {

    private static final String TREEGRID_STATE_CLOSED = "closed";//折叠

    private static final String TREEGRID_STATE_OPEND = "opend";//展开

    @Autowired
    private TbSysRoleMapper tbSysRoleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private EasyPagingQuery easyPageQuery;

    @Autowired
    private TbSysResourceMapper sysResourceMapper;

    @Autowired
    private TbSysRoleResourceMapper sysRoleResourceMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageResult<TbSysRoleDTO> querySysRoleWithPage(EasyPageFilter pageFilter, TbSysRoleDTO roleDto) {
        log.info("分页参数:{},查询参数:{}",JsonUtils.toString(pageFilter) , JsonUtils.toString(roleDto));
        PageResult<TbSysRoleDTO> pageResult = this.querySysRoleWithPage1(pageFilter,roleDto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        //对创建者替换成名称
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<TbSysRoleDTO> result = (EasyPageResult<TbSysRoleDTO>) pageResult;
            List<TbSysRoleDTO> rows = result.getRows();
            rows.forEach(srDto -> {
                String createdBy = srDto.getCreatedBy();//创建者
                if(createdBy != null) {
                    TbSysUserDTO u = sysUserService.querySysUserById(createdBy);
                    srDto.setCreatedByName(u.getName());
                }

            });
        }
        return pageResult;
    }

    private PageResult<TbSysRoleDTO> querySysRoleWithPage1(EasyPageFilter pageFilter, TbSysRoleDTO roleDto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);

        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(TbSysRole.class);
        Example.Criteria criteria = example.createCriteria();
        if(roleDto != null) {
            String code = roleDto.getCode();
            if(StringUtils.isNotBlank(code)) {
                criteria.andLike("code", PayplatformConstant.SPLIT_SYMBOL_PER + code + PayplatformConstant.SPLIT_SYMBOL_PER);
            }

            String name = roleDto.getName();
            if(StringUtils.isNotBlank(name)) {
                criteria.andLike("name", PayplatformConstant.SPLIT_SYMBOL_PER + name + PayplatformConstant.SPLIT_SYMBOL_PER);
            }

            String isDeleted = roleDto.getIsDeleted();
            if(StringUtils.isNotBlank(isDeleted)) {
                criteria.andEqualTo("isDeleted",isDeleted);
            }

            //如果不是管理员 加条件 merchantAggent
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchantAggent",ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);

        log.info("排序:orderByClause={}",orderByClause);

        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<TbSysRole> roles = tbSysRoleMapper.selectByExample(example);
        //结果
        PageResult<TbSysRoleDTO> pageResult = easyPageQuery.queryResult(roles, TbSysRoleDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean checkRoleCode(String code) {
        log.info("校验角色code:",code);
        TbSysRole sysRoleDto = this.querySysRoleByCode(code);
        log.info("校验角色code:",JsonUtils.toString(sysRoleDto));
        return sysRoleDto != null;
    }

    @Override
    public TbSysRoleDTO querySysRoleByCode(String code) {
        log.info("根据角色code查询:{}",code);
        if(StringUtils.isBlank(code)) {
            throw new BusinessException("根据角色code查询,传入code为空", 1004);
        }
        Example example = new Example(TbSysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code",code);
        List<TbSysRole> list = tbSysRoleMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        TbSysRole sysRole = list.get(0);
        if(sysRole == null) {
            return null;
        }
        TbSysRoleDTO roleDTO = new TbSysRoleDTO();
        CommonBeans.copyPropertiesIgnoreNull(sysRole, roleDTO);
        log.info("根据角色code查询:{}",JsonUtils.toString(roleDTO));
        return roleDTO;
    }

    @Override
    public boolean saveSysRole(TbSysRoleDTO roleDto) {
        log.info("保存角色:{}", JsonUtils.toString(roleDto));
        if(roleDto == null) {
            throw new BusinessException("保存角色,传入对象为空", 1004);
        }
        String name = roleDto.getName();//角色名称
        if(StringUtils.isBlank(name)) {
            throw new BusinessException("保存角色,传入name为空", 1004);
        }
        String code = roleDto.getCode();//角色code
        if(StringUtils.isBlank(code)) {
            throw new BusinessException("保存角色,传入code为空", 1004);
        }
        //校验code是否存在
        boolean isExits = this.checkRoleCode(code);
        if(isExits) {
            //如果存在
            throw new BusinessException("该code已存在", 1004);
        }
        roleDto.setId(GeneratotUtils.generateNumber());
        //是否删除 默认 0
        roleDto.setIsDeleted("0");
        //创建者
        roleDto.setCreatedBy(ShiroUtils.getSessionUserId());
        //创建时间
        roleDto.setCreatedDate(new Date());
        //修改时间
        roleDto.setUpdatedDate(new Date());

        //设置创建者的 merchantAggent
        // 如果不是管理员操作 就需要 设置 merchantAggent
        if(!ShiroUtils.isAdminSessionUserId()){
            roleDto.setMerchantAggent(ShiroUtils.getSessionMerchant());
        }

        TbSysRole record = new TbSysRole();
        CommonBeans.copyPropertiesIgnoreNull(roleDto, record);
        int count = tbSysRoleMapper.insertSelective(record);
        if(count == 0) {
            throw new BusinessException("新增角色失败", 1004);
        }
        return count > 0;
    }

    @Override
    public boolean checkedStatus(TbSysRoleDTO roleDto) {
        log.info("修改状态:{}",JsonUtils.toString(roleDto));
        if(roleDto == null) {
            throw new BusinessException("修改状态,传入对象为空", 1004);
        }
        String id = roleDto.getId();
        if(StringUtils.isBlank(id)) {
            throw new BusinessException("修改状态,传入主键Id为空", 1004);
        }
        boolean checked = roleDto.isChecked();
        //是否删除
        String isDeleted = checked ? "0" : "1";
        roleDto.setIsDeleted(isDeleted);
        //修改日期
        roleDto.setUpdatedDate(new Date());
        //执行修改
        TbSysRole record = new TbSysRole();
        CommonBeans.copyPropertiesIgnoreNull(roleDto, record);
        int count = tbSysRoleMapper.updateByPrimaryKeySelective(record);
        if(count == 0) {
            throw new BusinessException("修改角色失败", 1004);
        }
        return count > 0;
    }

    @Override
    public TbSysRoleDTO querySysRoleById(String id) {
        log.info("根据id查询：id={}", id);
        if(id == null) {
            return null;
        }
        TbSysRole record = tbSysRoleMapper.selectByPrimaryKey(id);
        TbSysRoleDTO result = new TbSysRoleDTO();
        CommonBeans.copyPropertiesIgnoreNull(record, result);
        String permission = result.getPermission();
        String[] perm = permission.split(",");
        result.setPerm(perm);

        return result;
    }

    @Override
    public boolean updateSysRole(TbSysRoleDTO roleDto) {
        log.info("修改角色:{}",JsonUtils.toString(roleDto));
        if(roleDto == null) {
            throw new BusinessException("修改角色,传入对象为空", 1004);
        }
        String id = roleDto.getId();
        if(StringUtils.isBlank(id)) {
            throw new BusinessException("修改角色,传入id为空", 1004);
        }
        String name = roleDto.getName();//角色名称
        if(StringUtils.isBlank(name)) {
            throw new BusinessException("保存角色,传入name为空", 1004);
        }
        //修改时间
        roleDto.setUpdatedDate(new Date());
        log.info("修改角色,完整数据:{}",JsonUtils.toString(roleDto));
        //调用远程服务器新增
        TbSysRole record = new TbSysRole();
        CommonBeans.copyPropertiesIgnoreNull(roleDto, record);
        int count = tbSysRoleMapper.updateByPrimaryKeySelective(record);
        if(count == 0) {
            throw new BusinessException("修改角色失败", 1004);
        }
        return count > 0;
    }


    @Override
    public List<TbSysRoleDTO> queryRoleDataList(TbSysRoleDTO roleDto) {
        log.info("");
        Example example = new Example(TbSysRole.class);
        Example.Criteria criteria = example.createCriteria();
        if(roleDto != null){
            String isDeleted = roleDto.getIsDeleted();
            if(StringUtils.isNotBlank(isDeleted)){
                criteria.andEqualTo("isDeleted",isDeleted);
            }

            //是否是管理员 如果不是 加 merchantAggent
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchantAggent",ShiroUtils.getSessionMerchant());
            }


        }
        example.setOrderByClause(" code asc");
        List<TbSysRole> tbSysRoles = tbSysRoleMapper.selectByExample(example);
        List<TbSysRoleDTO> newList = CommonBeans.copyNewList(tbSysRoles, TbSysRoleDTO.class);
        return newList;

    }

    @Override
    public List<RoleResourceGroup> querySysRoleResource(String id) {
        log.info("{}","查询树形表格");
        //查询一级菜单
        List<RoleResourceGroup> groups = this.queryResourceList("0", 1,id);
        if(CollectionUtils.isNotEmpty(groups)) {
            groups.forEach(group -> {
                String menuid = group.getMenuid();
                //查询二级菜单
                List<RoleResourceGroup> seconds = this.queryResourceList(menuid, 2,id);
                if(CollectionUtils.isNotEmpty(seconds)) {
                    group.setChildren(seconds);
                    seconds.forEach(s -> {
                        //查询三级菜单
                        String menuid2 = s.getMenuid();
                        List<RoleResourceGroup> thres = this.queryResourceList(menuid2, 3,id);
                        if(CollectionUtils.isNotEmpty(thres)) {
                            s.setChildren(thres);
                            thres.forEach(third -> {
                                //查询四级菜单
                                String menuid3 = third.getMenuid();
                                List<RoleResourceGroup> fourth = this.queryResourceList(menuid3, 4,id);
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
    private List<RoleResourceGroup> queryResourceList(String parentMenuid,Integer level,String rid){
        List<TbSysResource> list = null;
        //如果是管理员 查询所有
        if(ShiroUtils.isAdminSessionUserId()){
            Example example = new Example(TbSysResource.class);
            Example.Criteria criteria = example.createCriteria();
            //父菜单Id
            criteria.andEqualTo("parentMenuid",parentMenuid);
            //菜单等级
            criteria.andEqualTo("lev",level);
            example.setOrderByClause(" sort ASC ");
            list = sysResourceMapper.selectByExample(example);
        }else{
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("parentMenuid",parentMenuid);
            paramMap.put("lev",level);
            paramMap.put("merchant",ShiroUtils.getSessionMerchant());
            list = sysRoleMapper.queryResourceByUserId(paramMap);
        }

        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        //将数据转换成group
        List<RoleResourceGroup> groups = new ArrayList<>();

        Example example1 = new Example(TbSysRoleResource.class);
        List<TbSysRoleResource> listAll = sysRoleResourceMapper.selectByExample(example1);
        List<TbSysRoleResource> list1 = new ArrayList<>();

        list.forEach( (tsr) -> {

            RoleResourceGroup group = new RoleResourceGroup();

            String id = tsr.getId();
            group.setId(id);

            if(level == 3){
                for (TbSysRoleResource tbSysRoleResource: listAll){
                    if(id.equals(tbSysRoleResource.getResourceId()) && rid.equals(tbSysRoleResource.getRoleId())){
                        list1.add(tbSysRoleResource);
                    }
                }
                if(CollectionUtils.isNotEmpty(list1)){
                    group.setChecked(true);
                }
            }

            String name = tsr.getName();
            group.setText(name);

            String menuid = tsr.getMenuid();
            group.setMenuid(menuid);

            group.setState(TREEGRID_STATE_OPEND);

            groups.add(group);

        });

        return groups;
    }

    @Override
    public boolean saveSysRoleResource(TbSysRoleResourceDTO roleResourceDTO) {
        if(roleResourceDTO == null) {
            throw new BusinessException("修改角色,传入对象为空", 1004);
        }
        String roleId = roleResourceDTO.getRoleId();

        if(StringUtils.isBlank(roleId)) {
            throw new BusinessException("修改角色,传入roleId为空", 1004);
        }

        String resourceIds = roleResourceDTO.getResourceIds();
        if(StringUtils.isBlank(resourceIds)) {
            throw new BusinessException("保存角色,传入resourceIds为空", 1004);
        }

        //根据roleid 先删除 所有的关联资源
        Example example = new Example(TbSysRoleResource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",roleId);
        sysRoleResourceMapper.deleteByExample(example);

        //新增所有的关联角色
        String[] rsids = resourceIds.split(",");

        int count = 0;
        for (String resid : rsids) {
            TbSysRoleResource resource = new TbSysRoleResource();
            resource.setId(GeneratotUtils.generateNumber());
            resource.setRoleId(roleId);
            resource.setResourceId(resid);
            sysRoleResourceMapper.insertSelective(resource);
            count ++;
        }

        return count > 0;

    }
}
