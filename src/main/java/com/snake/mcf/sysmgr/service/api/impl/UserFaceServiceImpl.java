package com.snake.mcf.sysmgr.service.api.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.UniversalConstant;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.FileUtils;
import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import com.snake.mcf.sysmgr.repertory.entity.Accountsface;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.mapper.AccountsfaceMapper;
import com.snake.mcf.sysmgr.repertory.mapper.ConfiginfoMapper;
import com.snake.mcf.sysmgr.service.account.AccountUserService;
import com.snake.mcf.sysmgr.service.api.UserFaceService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
@SuppressWarnings("all")
@Transactional(rollbackFor = Exception.class)
public class UserFaceServiceImpl implements UserFaceService {

	@Autowired
    private AccountsfaceMapper accountsfaceMapper;
	
	@Autowired
    private ConfiginfoMapper configinfoMapper;
	
	@Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;
	
	@Autowired
	private ConfigResource configResource;
	
	@Autowired
	private AccountUserService accountUserService;
	
	private final static String imgFirstFolderName = "head";
	
	
	@Override
	public Map<String, Object> getUserFaceByCustomId(String userId, String customId, Integer apiVersion) {
		Accountsface accountsface = accountsfaceMapper.selectByPrimaryKey(customId);
		Map<String, Object> data = null;
		if(null != accountsface) {
			data = new HashMap<String, Object>();
			Example example = new Example(Configinfo.class);
	        Example.Criteria criteria = example.createCriteria();
	        criteria.andEqualTo("configKey", "WebSiteConfig");
			Configinfo configInfo = configinfoMapper.selectOneByExample(example);
			if(configInfo != null) {
				String imageServerHost = configInfo.getField2();
				if(StringUtils.isNotEmpty(imageServerHost)) {
					String faceUrl = accountsface.getFaceurl();
					if(StringUtils.isNotEmpty(faceUrl) && faceUrl.indexOf("http") > -1) {
						data.put("faceUrl", accountsface.getFaceurl());
					}else {
						StringBuffer sb = new StringBuffer();
						sb.append(imageServerHost).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
						.append(imgFirstFolderName).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
						.append(accountsface.getFaceurl());
						data.put("faceUrl", String.valueOf(sb));
					}
				}
				data.put("userId", userId);
			}
		}
		data.put("apiVersion", apiVersion);
    	data.put("valid", true);
    	data.put("dateTime", System.currentTimeMillis());
		return data;
	}

	@Override
	public Map<String, Object> setUserFaceByCustomId(String userId, String customId, String faceUrl, Integer apiVersion) {
		Map<String, Object> data = new HashMap<String, Object>();
		int count = 0;
		if("0".equals(customId)) {
			Accountsface account = new Accountsface();
			account.setFaceurl(faceUrl);
			account.setUserid(Integer.valueOf(userId));
			account.setInserttime(new Date());
			account.setInsertaddr("");
			account.setInsertmachine("");
			int faceCount = accountsfaceMapper.insert(account);
			
			if(faceCount > 0) {
				AccountsInfo accountsInfo = new AccountsInfo();
				accountsInfo.setCustomId(account.getId());
				accountsInfo.setUserId(Integer.valueOf(userId));
				count = accountUserService.updateAccountInfoByUserId(accountsInfo);
			}
		}else {
			Accountsface account = accountsfaceMapper.selectByPrimaryKey(customId);
			account.setFaceurl(faceUrl);
			count = accountsfaceMapper.updateByPrimaryKey(account);
		}
		data.put("msg", count > 0 ? "success" : "faild");
		data.put("apiVersion", apiVersion);
    	data.put("valid", true);
    	data.put("dateTime", System.currentTimeMillis());
		return data;
	}

	@Override
	public Map<String, Object> getUserFaceList(Integer page, Integer count, Integer apiVersion) {
		Example example = new Example(Configinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configKey", "WebSiteConfig");
		Configinfo configInfo = configinfoMapper.selectOneByExample(example);
		Map<String, Object> data = new HashMap<String, Object>();
		if(configInfo != null) {
			String imageServerHost = configInfo.getField2();
			List<String> urlList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(configResource.getUserFacePath()).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
			.append(imgFirstFolderName).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL);
			String localBasePath = String.valueOf(sb);
			log.info("localBasePath：{}", localBasePath);
			for(int i = (page-1)*count + 1; i <= page*count; i++) {
				sb.delete(0, sb.length());
				sb.append(localBasePath).append(imgFirstFolderName).append(i).append(UniversalConstant.IMG_PNG_SUFFIX);
				File file = new File(String.valueOf(sb));
				if(FileUtils.judeFileExists(file)) {
					sb.delete(0, sb.length());
					sb.append(imageServerHost).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
					.append(imgFirstFolderName).append(UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL)
					.append(imgFirstFolderName).append(i).append(UniversalConstant.IMG_PNG_SUFFIX);
					urlList.add(String.valueOf(sb));
				}
			}
			data.put("page", 1); //当前页
			int totalFileCount = FileUtils.getFolderFilesCount(localBasePath);
			if(totalFileCount > 0) {
				data.put("totalPage", (totalFileCount + count / 2) / count); //总页数
				data.put("lists", urlList);
			}else {
				data.put("page", 0);
				data.put("totalPage", 0);
			}
			
		}else {
			data.put("page", 0);
			data.put("totalPage", 0);
		}
		data.put("apiVersion", apiVersion);
    	data.put("valid", true);
    	data.put("dateTime", System.currentTimeMillis());
		return data;
	}
	
}
