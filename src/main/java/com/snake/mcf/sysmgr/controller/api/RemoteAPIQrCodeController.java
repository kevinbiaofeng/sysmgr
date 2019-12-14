package com.snake.mcf.sysmgr.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.GsonUtils;
import com.snake.mcf.sysmgr.repertory.api.form.BaseRequestForm;
import com.snake.mcf.sysmgr.repertory.api.form.MerchantQrCodeForm;
import com.snake.mcf.sysmgr.service.api.QRCodeService;

@RequestMapping(value = "/api")
@RestController(value = "remoteAPIQrCodeController")
public class RemoteAPIQrCodeController {
	
	@Autowired
    private QRCodeService qrCodeService;
	
	@Autowired
	private ConfigResource configResource;
    /**
 	   *    生成二维码 返回二维码链接
     * content: 域名  +  userId + merchant + gameId（邀请码 ）
     * qrCodePath:二维码图片保存路径
     * fileName:二维码图片生成名称
     */
    @RequestMapping(path = "/getOrCreateQrCode", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getMobileLoginData(@Validated @RequestBody BaseRequestForm baseRequestForm) throws Exception{
    	MerchantQrCodeForm from = GsonUtils.toBean(baseRequestForm.getData(), MerchantQrCodeForm.class);
    	return qrCodeService.getQRCodeUrl(from.getUserId(), from.getMerchant(), from.getGameId(), Integer.valueOf(configResource.getApiVersion()));
    }
}
