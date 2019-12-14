package com.snake.mcf.sysmgr.service.api;

import java.util.Map;

import com.snake.mcf.sysmgr.repertory.entity.TbSysQRCode;

public interface QRCodeService {

    //生成二维码图片
	TbSysQRCode createQRCode(String userId, String merchant, String code);

	TbSysQRCode selectQrCodeByUserId(String userId);
	
    //获取二维码地址，根据地址得到图片
	Map<String, Object> getQRCodeUrl(String userId, String merchant, String code, Integer apiVersion);

}
