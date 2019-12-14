package com.snake.mcf.sysmgr.service.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.UniversalConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.QRCodeUtils;
import com.snake.mcf.common.utils.security.DesUtils;
import com.snake.mcf.sysmgr.enums.merchant.DomainTypeEnum;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchantDomain;
import com.snake.mcf.sysmgr.repertory.entity.TbSysQRCode;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysQRCodeMapper;
import com.snake.mcf.sysmgr.service.api.QRCodeService;
import com.snake.mcf.sysmgr.service.sys.SysMerchantDomainService;
import com.snake.mcf.sysmgr.service.website.WebsiteStandService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
@Transactional
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private SysMerchantDomainService sysMerchantDomainService;

    @Autowired
    private TbSysQRCodeMapper tbSysQRCodeMapper;

    @Autowired
    private ConfigResource configResource;
    
    @Autowired
    private WebsiteStandService websiteStandService;

    @Override
    public TbSysQRCode createQRCode(String userId, String merchant, String code) throws BusinessException {
        String serverBasePath = configResource.getUserFacePath();
        String qrCodeDesKey = configResource.getUserQrCodeDesKey();
        //根据userId获取主域名
        List<TbMerchantDomain> mainDomainList = sysMerchantDomainService.getMerchantDomainByMerchantAndType(merchant, DomainTypeEnum.Main.getCode());
        TbMerchantDomain mainDomain = null;
        if(mainDomainList != null && mainDomainList.size() > 0) {
        	mainDomain = mainDomainList.get(0);
        	log.info("主域名：{}", mainDomain.getDomainUrl());
        	//二维码链接跳转
            StringBuffer qrCodeUrl = new StringBuffer();
            qrCodeUrl.append(mainDomain.getDomainUrl()).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
            .append(DesUtils.encrypt(userId.toString(), qrCodeDesKey)).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
            .append(DesUtils.encrypt(merchant, qrCodeDesKey)).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
            .append(DesUtils.encrypt(code, qrCodeDesKey)).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL);

            //二维码图片命名规则
            String fileName = String.valueOf(System.currentTimeMillis());
            StringBuffer imgServerPath = new StringBuffer();
            imgServerPath.append(serverBasePath)
            .append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL).append("qrcode").append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
            .append(merchant).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL);
            try {
                fileName = QRCodeUtils.createQRCode(qrCodeUrl.toString(), null, imgServerPath.toString(), fileName, true);
                log.info("fileName:{}", fileName);
                log.info("商户号：{}，二维码创建成功！", merchant);
                //拼接二维码图片完整后缀路径
                String suffixPath = "/qrcode/" + merchant + "/" + fileName;
                //往数据库添加数据
                TbSysQRCode tbSysQRCode = new TbSysQRCode();
                tbSysQRCode.setUserId(Integer.valueOf(userId));
                tbSysQRCode.setMerchant(merchant);
                tbSysQRCode.setCode(code);
                tbSysQRCode.setPath(suffixPath);
                int flag = tbSysQRCodeMapper.insertSelective(tbSysQRCode);
                if (flag <= 0) {
                    throw new BusinessException("创建二维码失败", 0);
                }else {
                	return tbSysQRCode;
                }
            } catch (Exception e) {
            	log.error("商户号：{}，二维码创建失败！", merchant);
                log.error(e.getMessage());
                throw new BusinessException("二维码创建失败", 1001);
            }
        }else {
        	log.error("商户主域名未设置");
        	throw new BusinessException("商户主域名未设置", 1001);
        }
    }
    
    
    public TbSysQRCode selectQrCodeByUserId(String userId) {
    	Example example = new Example(TbSysQRCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
    	return tbSysQRCodeMapper.selectOneByExample(example);
    }
    
    @Override
    public Map<String, Object> getQRCodeUrl(String userId, String merchant, String code, Integer apiVersion) {
    	Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
    	TbSysQRCode tbSysQRCode = this.selectQrCodeByUserId(userId);
    	Configinfo configInfo = websiteStandService.queryConfigInfoByConfigKey("WebSiteConfig");
    	if(tbSysQRCode != null) {
    		data.put("qrCodeUrl", configInfo.getField2() + tbSysQRCode.getPath());
    	}else {
    		tbSysQRCode = this.createQRCode(userId, merchant, code);
    		data.put("qrCodeUrl", configInfo.getField2() + tbSysQRCode.getPath());
    	}
        return data;
    }
    
}
