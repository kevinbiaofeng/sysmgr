package com.snake.mcf.sysmgr.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.DesUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchant;
import com.snake.mcf.sysmgr.repertory.mapper.TbMerchantMapper;
import com.snake.mcf.sysmgr.service.sys.SysMerchantService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SysMerchantServiceImpl extends BaseServiceImpl implements SysMerchantService {

    @Autowired
    private TbMerchantMapper merchantMapper;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public TbMerchant queryMerchantById(String id) {
    	String key = configResource.getMerchantKey();
        log.info("根据主键查询:id={}",id);
        if(id == null) {
            return null;
        }
        Example example = new Example(TbMerchant.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchant", id);
        TbMerchant tbMerchant = merchantMapper.selectOneByExample(example);
        if(tbMerchant != null) {
        	String name=tbMerchant.getName();
            if(name != null && StringUtils.isNotEmpty(name)) {
            	tbMerchant.setName(DesUtils.decrypt(name, key));
            }
            
            String phone = tbMerchant.getPhone();
            if(phone != null && StringUtils.isNotEmpty(phone)) {
            	tbMerchant.setPhone(DesUtils.decrypt(phone, key));
            }
            
            String qq = tbMerchant.getQqAccount();
            if(qq != null && StringUtils.isNotEmpty(qq)) {
            	tbMerchant.setQqAccount(DesUtils.decrypt(qq, key));
            }
            
            String mail = tbMerchant.getMail();
            if(mail != null && StringUtils.isNotEmpty(mail)) {
            	tbMerchant.setMail(DesUtils.decrypt(mail, key));
            }
            
            String wechat = tbMerchant.getWechatAccount();
            if(wechat != null && StringUtils.isNotEmpty(wechat)) {
            	tbMerchant.setWechatAccount(DesUtils.decrypt(wechat, key));
            }
            return tbMerchant;
        }
        return null;
    }
}
