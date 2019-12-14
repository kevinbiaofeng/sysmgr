package com.snake.mcf.sysmgr.service.authority.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.ui.menu.filter.LayMenuFilter;
import com.snake.mcf.common.utils.BeanUtils;
import com.snake.mcf.common.utils.CusAccessObjectUtil;
import com.snake.mcf.common.utils.GsonUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.sysmgr.authority.AuthorityUtils;
import com.snake.mcf.sysmgr.authority.token.AuthorityUsernamePasswordToken;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.authority.AuthorityMapper;
import com.snake.mcf.sysmgr.repertory.entity.TbSysUser;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysUserMapper;
import com.snake.mcf.sysmgr.service.authority.AuthorityService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName AuthorityServiceImpl
 * @Author 大帅
 * @Date 2019/6/20 10:31
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class AuthorityServiceImpl extends BaseServiceImpl implements AuthorityService {

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public TbSysUserDTO querySysUserByLoginName(String username) {
        Example example = new Example(TbSysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginName", username);
        List<TbSysUser> sysUsers = tbSysUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }
        TbSysUser tbSysUser = sysUsers.get(0);
        TbSysUserDTO tbSysUserDTO = new TbSysUserDTO();
        BeanUtils.copyProperties(tbSysUser, tbSysUserDTO);
        return tbSysUserDTO;
    }

    @Override
    public List<TbSysRoleDTO> queryUserRoleByUserId(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", id);
        List<TbSysRoleDTO> list = authorityMapper.queryUserRoleByUserId(paramMap);
        return list;
    }

    @Override
    public Map<String, Object> queryUserMenuByUserId(String id) {
        //Map<String,Object> paramMap = new HashMap<>();
        //paramMap.put("userId",id);
        List<LayMenuFilter> menuList = this.queryMenuList(id);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        Map<String, Object> menuMap = new HashMap<>();
        menuMap.put("list", menuList);
        map.put("data", menuMap);
        return map;
    }

    //查询所有菜单
    private List<LayMenuFilter> queryMenuList(String id) {
        Map map = new HashMap();
        map.put("userId", id);
        LinkedList<LayMenuFilter> list = authorityMapper.queryMenuListByUserId(map);
        //查询一级菜单
        List<LayMenuFilter> firstList = new LinkedList<>();
        if (list.size() > 0) {
            for (LayMenuFilter layMenuFilter : list) {
                if ("0".equals(layMenuFilter.getParentMenuid()) && 1 == layMenuFilter.getLev()) {
                    firstList.add(layMenuFilter);
                }
            }
        }

//        List<LayMenuFilter> firstList = this.queryMenuList1(id, "0", 1);
        //循环一级菜单
        if (CollectionUtils.isNotEmpty(firstList)) {
            firstList.forEach((fmf) -> {
                //查询二级菜单
                List<LayMenuFilter> secondList = new LinkedList<>();
                for (LayMenuFilter layMenuFilter : list) {
                    if (fmf.getId().equals(layMenuFilter.getParentMenuid()) && 2 == layMenuFilter.getLev()) {
                        secondList.add(layMenuFilter);
                    }
                }
                //循环二级菜单
                if (CollectionUtils.isNotEmpty(secondList)) {
                    fmf.setSub(secondList);
                    secondList.forEach((smf) -> {
                        //查询三级菜单
                        List<LayMenuFilter> thirdList = new LinkedList<>();
                        for (LayMenuFilter layMenuFilter : list) {
                            if (smf.getId().equals(layMenuFilter.getParentMenuid()) && 3 == layMenuFilter.getLev()) {
                                thirdList.add(layMenuFilter);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(thirdList)) {
                            smf.setSub(thirdList);//三级塞进二级
                            thirdList.forEach((sl) -> {
                                //查询四级菜单
                                List<LayMenuFilter> fourthList = new LinkedList<>();
                                for (LayMenuFilter layMenuFilter : list) {
                                    if (sl.getId().equals(layMenuFilter.getParentMenuid()) && 4 == layMenuFilter.getLev()) {
                                        fourthList.add(layMenuFilter);
                                    }
                                    if (CollectionUtils.isNotEmpty(thirdList)) {
                                        sl.setSub(fourthList);
                                    }
                                }
                            });
                        }
                    });
                }

            });
        }
        String toGsonString = GsonUtils.toString(firstList);
        log.info("toGsonString:{}", toGsonString);
        return firstList;
    }

    private LinkedList<LayMenuFilter> queryMenuList1(String userId, String parentMenuid, int lev) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("parentMenuid", parentMenuid);
        paramMap.put("lev", lev);
        LinkedList<LayMenuFilter> list = authorityMapper.queryMenuListByUserId(paramMap);
        return list;
    }

    /**
     * [{
     * "id": "06bbcde3c2b94743a2e6296896091bc7",
     * "text":"新增权限",
     * "desc":"具有新增权限"
     * },{
     * "id": "eadc19ecba59431892bd3eb2a55d9a16",
     * "text":"修改权限",
     * "desc":"具有修改权限"
     * },{
     * "id": "df6b5fa5cf224d3787c7b4197829fdac",
     * "text":"删除权限",
     * "desc":"具有删除权限"
     * }]
     * 0 -- 查询
     * 1 -- 新增
     * 2 -- 修改
     * 4 -- 删除
     */
    @Override
    public int getPermissionByUserId(String id) {
        List<TbSysRoleDTO> roleList = this.queryUserRoleByUserId(id);
        if (org.apache.shiro.util.CollectionUtils.isEmpty(roleList)) {
            return 0;
        }
        List<String> permList = roleList.stream().map(TbSysRoleDTO::getPermission).collect(Collectors.toList());
        if (org.apache.shiro.util.CollectionUtils.isEmpty(permList)) {
            return 0;
        }
        String rest = String.join(",", permList);

        int count = 0;
        Set<String> set = Stream.of(rest.split(",")).collect(Collectors.toSet());
		/*set.stream().forEach((k)->{
			int authorityCode = AuthorityUtils.getAuthorityCode(k);
			count += authorityCode;
		});*/
        for (String k : set) {
            int authorityCode = AuthorityUtils.getAuthorityCode(k);
            count += authorityCode;
        }
        return count;
    }

    @Override
    @Async
    public void recordLog(AuthorityUsernamePasswordToken token, TbSysUserDTO sysUserDto, List<TbSysRoleDTO> roleList) {
        TbSysLogDTO redord = new TbSysLogDTO();
        //功能模块名称
        redord.setModule("登录功能");
        //account
        redord.setAccount(token.getUsername());
        //操作人iP
        redord.setIp(CusAccessObjectUtil.getIpAddress(token.getRequest()));
        //请求方式
        redord.setMethod(CusAccessObjectUtil.getMethod(token.getRequest()));
        //请求参数
        Map<String, Object> map1 = new HashMap<>();
        map1.put("loginName", token.getUsername());
        //map1.put("loginPassword",sysUserDto.getLoginPassword());
        redord.setParams(JsonUtils.toString(map1));
        //角色名称
        //List<TbSysRoleDTO> sessionRole = ShiroUtils.getSessionRole();
        //只取roleName
        List<String> roleName = authorityService.getRoleName(roleList);
        redord.setRoleName(JsonUtils.toString(roleName));
        //操作功能
        redord.setDescription(token.getUsername() + "登录成功");
        //商户号
        redord.setMerchant(sysUserDto.getMerchant());
        authorityService.saveSysLong(redord);
    }

    @Override
    public List<LayMenuFilter> queryUserPermissionByUserId(String id) {
        Map map = new HashMap();
        map.put("userId", id);
        return authorityMapper.queryMenuListByUserId(map);
    }


}
