package com.snake.mcf.sysmgr.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.GeneratotUtils;
import com.snake.mcf.common.utils.IdWorker;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.DesUtils;
import com.snake.mcf.common.utils.security.MD5Utils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.sys.SysUserMapper;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchant;
import com.snake.mcf.sysmgr.repertory.entity.TbSysUser;
import com.snake.mcf.sysmgr.repertory.entity.TbSysUserRole;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserRoleDTO;
import com.snake.mcf.sysmgr.repertory.mapper.TbMerchantMapper;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysUserMapper;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysUserRoleMapper;
import com.snake.mcf.sysmgr.service.sys.SysUserService;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName SysUserServiceImpl
 * @Author 大帅
 * @Date 2019/6/20 17:04
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private TbSysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TbMerchantMapper merchantMapper;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<TbSysUserDTO> queryUserWithPage(EasyPageFilter pageFilter, TbSysUserDTO sysUserDTO) {
    	String key = configResource.getMerchantKey();
        //默认查询有效
        //userDto.setIsDeleted("0");//默认只查询有效的
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(sysUserDTO));
        PageResult<TbSysUserDTO> pageResult = this.queryUserWithPage1(pageFilter, sysUserDTO);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        //对创建者替换成名称
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<TbSysUserDTO> result = (EasyPageResult<TbSysUserDTO>) pageResult;
            List<TbSysUserDTO> rows = result.getRows();
            rows.forEach(suDto -> {
                String createdBy = suDto.getCreatedBy();//创建者
                if(StringUtils.isNotBlank(createdBy)) {
                    TbSysUserDTO u = this.querySysUserById(createdBy);
                    suDto.setCreatedByName(u.getName());
                }

                String updatedBy = suDto.getUpdatedBy();//修改者
                if(StringUtils.isNotBlank(updatedBy)) {
                    TbSysUserDTO u = this.querySysUserById(updatedBy);
                    suDto.setUpdatedByName(u.getName());
                }
                
                String name = suDto.getName();
                if(StringUtils.isNotEmpty(name)) {
                    suDto.setName(DesUtils.decrypt(name, key));
                }
                
                String phone = suDto.getPhone();
                if(StringUtils.isNotEmpty(phone)) {
                    suDto.setPhone(DesUtils.decrypt(phone, key));
                }
                
                String qq = suDto.getQqAccount();
                if(StringUtils.isNotEmpty(qq)) {
                    suDto.setQqAccount(DesUtils.decrypt(qq, key));
                }
                
                String mail = suDto.getMail();
                if(StringUtils.isNotEmpty(mail)) {
                    suDto.setMail(DesUtils.decrypt(mail, key));
                }
                
                String wechat = suDto.getWechatAccount();
                if(StringUtils.isNotEmpty(wechat)) {
                    suDto.setWechatAccount(DesUtils.decrypt(wechat, key));
                }
            });
        }
        return pageResult;
    }

    @Override
    public TbSysUserDTO querySysUserById(String id) {
        log.info("根据主键查询:id={}",id);
        if(id == null) {
            return null;
        }
        TbSysUser record = tbSysUserMapper.selectByPrimaryKey(id);
        if(record == null) {
            return null;
        }
        TbSysUserDTO result = new TbSysUserDTO();
        CommonBeans.copyPropertiesIgnoreNull(record, result);
        return result;
    }

    private PageResult<TbSysUserDTO> queryUserWithPage1(EasyPageFilter pageFilter, TbSysUserDTO sysUserDTO) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(TbSysUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(sysUserDTO != null) {
            String name = sysUserDTO.getName();//用户名
            if(StringUtils.isNotBlank(name)) {
                //criteria.andEqualTo("name",PayplatformConstant.SPLIT_SYMBOL_PER + name + PayplatformConstant.SPLIT_SYMBOL_PER);
                criteria.andLike("name",PayplatformConstant.SPLIT_SYMBOL_PER + name + PayplatformConstant.SPLIT_SYMBOL_PER);
            }
            String loginName = sysUserDTO.getLoginName();//登录名
            if(StringUtils.isNotBlank(loginName)) {
                criteria.andLike("loginName",PayplatformConstant.SPLIT_SYMBOL_PER + loginName + PayplatformConstant.SPLIT_SYMBOL_PER);
            }
            String isDelete = sysUserDTO.getIsDeleted();//是否删除
            if(StringUtils.isNotBlank(isDelete)) {
                criteria.andEqualTo("isDeleted",isDelete);
            }
            String isLock = sysUserDTO.getIsLocked();//是否锁定
            if(StringUtils.isNotBlank(isLock)) {
                criteria.andEqualTo("isLocked",isLock);
            }

            //是否是管理员  如果不是 需要 加条件限制
            boolean adminSessionUserId = ShiroUtils.isAdminSessionUserId();
            if(!adminSessionUserId){
                String sessionMerchant = ShiroUtils.getSessionMerchant();
                if(StringUtils.isNotBlank(sessionMerchant)) {
                    //criteria.andEqualTo("merchant",sessionMerchant);
                    criteria.andEqualTo("merchantAggent",sessionMerchant);
                }
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<TbSysUser> users = tbSysUserMapper.selectByExample(example);
        //结果
        PageResult<TbSysUserDTO> pageResult = easyPageQuery.queryResult(users, TbSysUserDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean checkLoginName(String loginName) {
        log.info("校验登录名:{}",loginName);
        TbSysUserDTO sysUserDto = this.querySysUserByLoginName(loginName);
        log.info("校验登录名:{}",JsonUtils.toString(sysUserDto));
        return sysUserDto != null;
    }

    @Override
    public TbSysUserDTO querySysUserByLoginName(String loginName) {
        log.info("根据登录名查询登录名:{}",loginName);
        if(StringUtils.isBlank(loginName)) {
            throw new BusinessException("根据登录名查询登录名,传入loginName为空", 1004);
        }
        Example example = new Example(TbSysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginName",loginName);
        List<TbSysUser> users = tbSysUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(users)) {
            return null;
        }
        TbSysUser sysUser = users.get(0);
        TbSysUserDTO userDto = new TbSysUserDTO();
        CommonBeans.copyPropertiesIgnoreNull(sysUser, userDto);
        log.info("根据登录名查询登录名:{}",JsonUtils.toString(userDto));
        return userDto;
    }


    @Override
    public boolean saveSysUser(TbSysUserDTO userDto) {
        log.info("保存系统管理员:{}",JsonUtils.toString(userDto));
        if(userDto == null) {
            throw new BusinessException("保存系统管理员,传入对象为空", 1004);
        }
        String name = userDto.getName();//用户名
        if(StringUtils.isBlank(name)) {
            throw new BusinessException("保存系统管理员,传入name为空", 1004);
        }
        String loginName = userDto.getLoginName();//登录名
        if(StringUtils.isBlank(loginName)) {
            throw new BusinessException("保存系统管理员,传入loginName为空", 1004);
        }
        String loginPassword = userDto.getLoginPassword();//登录密码
        if(StringUtils.isBlank(loginPassword)) {
            throw new BusinessException("保存系统管理员,传入loginPassword为空", 1004);
        }
        String confirmPassword = userDto.getConfirmPassword();//确认密码
        if(StringUtils.isBlank(confirmPassword)) {
            throw new BusinessException("保存系统管理员,传入confirmPassword为空", 1004);
        }
        if(!loginPassword.equals(confirmPassword)) {
            throw new BusinessException("保存系统管理员,两次密码不一致", 1004);
        }
        //校验登录名是否存在
        boolean isExits = this.checkLoginName(loginName);
        if(isExits) {
            //如果存在
            throw new BusinessException("该登录名已存在", 1004);
        }
        log.info("新增系统用户:{}",JsonUtils.toString(userDto));
        userDto.setId(GeneratotUtils.generateNumber());
        //创建者ID
        //从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
        String sessionUserId = ShiroUtils.getSessionUserId();
        userDto.setCreatedBy(sessionUserId);
        userDto.setCreatedDate(new Date());
        userDto.setLoginPassword(MD5Utils.md5Digest(userDto.getLoginPassword()));
        //是否锁定默认正常
        userDto.setIsLocked("0");
        //是否删除默认正常
        userDto.setIsDeleted("0");

        //商户号
        // 只有角色为总裁才有商户号
        /*List<TbSysRoleDTO> sessionRole = ShiroUtils.getSessionRole();
        boolean existCeo = this.isExistCeo(sessionRole);
        if(existCeo){
            userDto.setMerchant(IdUtil.fastSimpleUUID());
        }else{

        }*/
        //是管理员
        if(ShiroUtils.isAdminSessionUserId()){
            //商户ID
            userDto.setMerchantid(Integer.valueOf(GeneratotUtils.generateNumber()));
            //商户号
            userDto.setMerchant(IdUtil.fastSimpleUUID());
            //秘钥
            userDto.setSecretKey(IdUtil.fastSimpleUUID() + IdUtil.fastSimpleUUID());
            String key = configResource.getMerchantKey();
            //给商户表添加数据
            TbMerchant record1 = new TbMerchant();
            record1.setMerchant(userDto.getMerchant());
//            record1.setMerchantid(userDto.getMerchantid());
            record1.setName(DesUtils.encrypt(userDto.getName(), key));
            record1.setCommissionratio(userDto.getCommissionratio());
            record1.setPhone(DesUtils.encrypt(userDto.getPhone(), key));
            record1.setMail(DesUtils.encrypt(userDto.getMail(), key));
            record1.setQqAccount(DesUtils.encrypt(userDto.getQqAccount(), key));
            record1.setWechatAccount(DesUtils.encrypt(userDto.getWechatAccount(), key));
            record1.setSafePassword(userDto.getSafePassword());
            merchantMapper.insert(record1);

        }else{
            userDto.setMerchantid(ShiroUtils.getSessionUser().getMerchantid());
            userDto.setMerchant(ShiroUtils.getSessionMerchant());
            userDto.setMerchantAggent(ShiroUtils.getSessionMerchant());
            userDto.setSecretKey(ShiroUtils.getSessionUser().getSecretKey());
        }

        TbSysUser record = new TbSysUser();
        CommonBeans.copyPropertiesIgnoreNull(userDto, record);
        int insert = tbSysUserMapper.insertSelective(record);
        if(insert == 0) {
            throw new BusinessException("新增系统用户失败", 1004);
        }
        return insert > 0;
    }

    /**
     * 判断是否存在管理员角色
     *
     * @return true 存在 false 不存在
     */
    private boolean isExistAdminUser(TbSysUserDTO sysUserDTO){
        if(sysUserDTO == null){
            return false;
        }
        return false;
    }

    /**
     * 判断是否是总裁 角色
     *
     * @return true 存在 false 不存在
     */
    private boolean isExistCeo(List<TbSysRoleDTO> list){
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        boolean fg = false;
        for (TbSysRoleDTO sr:list) {
            String code = sr.getCode();
            if("1004".equals(code)){
                fg = true;
                break;
            }
        }
        return fg;
    }


    /**
     * 判断是否存在管理员角色
     *
     * @return true 存在 false 不存在
     */
    private boolean isExistAdminUser(List<TbSysRoleDTO> list){
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        boolean fg = false;
        for (TbSysRoleDTO sr:list) {
            String code = sr.getCode();
            if("1".equals(code)){
                fg = true;
                break;
            }
        }
        return fg;
    }

    @Override
    public boolean updateSysUser(TbSysUserDTO userDto) {
        log.info("修改系统管理员:{}",JsonUtils.toString(userDto));
        if(userDto == null) {
            throw new BusinessException("修改系统管理员,传入对象为空", 1004);
        }
        String id = userDto.getId();
        if(StringUtils.isBlank(id)) {
            throw new BusinessException("修改系统管理员,传入id为空", 1004);
        }
        String name = userDto.getName();//用户名
        if(StringUtils.isBlank(name)) {
            throw new BusinessException("修改系统管理员,传入name为空", 1004);
        }
        String loginName = userDto.getLoginName();//登录名
        if(StringUtils.isBlank(loginName)) {
            throw new BusinessException("修改系统管理员,传入loginName为空", 1004);
        }
        String loginPassword = userDto.getLoginPassword();//登录密码
        if(StringUtils.isBlank(loginPassword)) {
            throw new BusinessException("修改系统管理员,传入loginPassword为空", 1004);
        }
        String confirmPassword = userDto.getConfirmPassword();//确认密码
        if(StringUtils.isBlank(confirmPassword)) {
            throw new BusinessException("修改系统管理员,传入confirmPassword为空", 1004);
        }
        if(!loginPassword.equals(confirmPassword)) {
            throw new BusinessException("修改系统管理员,两次密码不一致", 1004);
        }
        userDto.setLoginPassword(MD5Utils.md5Digest(loginPassword));
        //修改者ID
        userDto.setUpdatedBy(ShiroUtils.getSessionUserId());
        //修改时间
        userDto.setUpdatedDate(new Date());
        
        TbSysUser record = new TbSysUser();
        CommonBeans.copyPropertiesIgnoreNull(userDto, record);
        int count = tbSysUserMapper.updateByPrimaryKeySelective(record);
        if(count == 0) {
            throw new BusinessException("新增系统用户失败", 1004);
        }
        
        String merchant = userDto.getMerchant();
        String key = configResource.getMerchantKey();
        if(merchant != null && StringUtils.isNotEmpty(merchant)) {
        	TbMerchant record1 = new TbMerchant();
            record1.setMerchant(userDto.getMerchant());
            record1.setCommissionratio(userDto.getCommissionratio());
            record1.setPhone(DesUtils.encrypt(userDto.getPhone(), key));
            record1.setMail(DesUtils.encrypt(userDto.getMail(), key));
            record1.setQqAccount(DesUtils.encrypt(userDto.getQqAccount(), key));
            record1.setWechatAccount(DesUtils.encrypt(userDto.getWechatAccount(), key));
            record1.setSafePassword(userDto.getSafePassword());
            count = merchantMapper.updateByPrimaryKeySelective(record1);
            if(count == 0) {
                throw new BusinessException("新增系统用户失败", 1004);
            }
        }
        return count > 0;
    }

    @Override
    public boolean checkedStatus(TbSysUserDTO userDto) {
        log.info("修改状态:{}",JsonUtils.toString(userDto));
        if(userDto == null) {
            throw new BusinessException("修改状态,传入对象为空", 1004);
        }
        String id = userDto.getId();
        if(StringUtils.isBlank(id)) {
            throw new BusinessException("修改状态,传入主键Id为空", 1004);
        }
        Integer checkedType = userDto.getCheckedType();
        if(!(checkedType.intValue() == 1 || checkedType == 2)) {
            throw new BusinessException("传入参数checkedType不符合规范:{}", 1004, checkedType.intValue());
        }
        boolean checked = userDto.isChecked();
        if(checkedType.intValue() == 1) {
            // 1 是否锁定 true 正常 false 锁定
            String isLocked = checked ? "0" : "1";
            userDto.setIsLocked(isLocked);
        }else if(checkedType.intValue() == 2) {
            // 2 是否删除  true 正常 false 删除
            String isDeleted = checked ? "0" : "1";
            userDto.setIsDeleted(isDeleted);
        }
        userDto.setUpdatedDate(new Date());
        //从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
        userDto.setUpdatedBy(ShiroUtils.getSessionUserId());
        //执行修改
        TbSysUser record = new TbSysUser();
        CommonBeans.copyPropertiesIgnoreNull(userDto, record);
        int insert = tbSysUserMapper.updateByPrimaryKeySelective(record);
        if(insert == 0) {
            throw new BusinessException("新增系统用户失败", 1004);
        }
        return insert > 0;
    }

    @Override
    public boolean saveSysUserRole(TbSysUserRoleDTO sysUserRoleDTO) {
        log.info("用户角色关联信息：{}", JsonUtils.toString(sysUserRoleDTO));

        if(sysUserRoleDTO == null){
            throw new BusinessException("用户角色关联信息,传入对象为空", 1004);
        }

        String userId = sysUserRoleDTO.getUserId();
        if(StringUtils.isBlank(userId)){
            throw new BusinessException("用户角色关联信息,传入对象UserId为空", 1004);
        }

        String roleIds = sysUserRoleDTO.getRoleIds();

    /*    if(StringUtils.isBlank(roleIds)){
            throw new BusinessException("用户角色关联信息,传入对象 RoleIds 为空", 1004);
        }
*/
        //1 根据userid 把角色关联全部删除
        Example example = new Example(TbSysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        sysUserRoleMapper.deleteByExample(example);

        int count = 0;
        if(StringUtils.isNotBlank(roleIds)){
            //2 循环添加
            String[] split = roleIds.split(",");
            for (String roleId : split) {
                TbSysUserRole userRole = new TbSysUserRole();
                userRole.setId(GeneratotUtils.generateNumber());
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                sysUserRoleMapper.insert(userRole);
                count ++;
            }
        }
        count ++;
        return count > 0;
    }

    @Override
    public List<TbSysRoleDTO> queryCurrUserRole(String userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        List<TbSysRoleDTO> dtoList = sysUserMapper.queryRoleByUserId(paramMap);
        return dtoList;
    }
    
    @Override
    public Map<String, Object> queryUserMapByIds(List<Long> userList){
    	List<TbSysUser> userLists = this.queryUserListByIds(userList);
    	if(userLists != null && userLists.size() > 0) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		for (TbSysUser tbSysUser : userLists) {
    			map.put(tbSysUser.getId(), tbSysUser.getName());
    		}
    		return map;
    	}
    	return null;
    }
    
	@Override
	public List<TbSysUser> queryUserListByIds(List<Long> userList) {
		Example example = new Example(TbSysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", userList);
        return tbSysUserMapper.selectByExample(example);
	}
    
	@Override
	public TbSysUser queryUserByMerchant(String merchant) {
		Example exampleTbSysUser = new Example(TbSysUser.class);
        Example.Criteria criteriaTbSysUser = exampleTbSysUser.createCriteria();
        criteriaTbSysUser.andEqualTo("merchant", merchant);
        criteriaTbSysUser.andCondition("merchantAggent IS null");
        return tbSysUserMapper.selectOneByExample(exampleTbSysUser);
	}
}
