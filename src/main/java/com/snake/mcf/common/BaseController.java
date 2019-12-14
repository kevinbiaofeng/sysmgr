package com.snake.mcf.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.snake.mcf.common.constant.UniversalConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 组件化前端控制器
 * 
 * @ClassName:  BaseController
 * @author: dashuai
 * @date:   2019年3月12日 上午10:05:20   
 *     
 * @Copyright: 2019 www.wondersgroup.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public abstract class BaseController{
	/**
	 * ThreadLocal确保高并发下每个请求的request，response都是独立的
	 */
	private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
	private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	protected HttpSession session;

	/**
	 * 线程安全初始化reque，respose对象
	 * 
	 * @param request
	 * @param response
	 * @date 2015年11月24日
	 * @author 漂泊者及其影子
	 */
	@ModelAttribute
	public void initReqAndRep(HttpServletRequest request, HttpServletResponse response) {
		log.info("init method -- > {}", "initReqAndRep");
		try {
			request.setCharacterEncoding(UniversalConstant.ENCODE_UTF_8);
			response.setCharacterEncoding(UniversalConstant.ENCODE_UTF_8);
			response.setContentType(UniversalConstant.CONTENT_TYPE_ENCODE_UTF_8);
			currentRequest.set(request);
			currentResponse.set(response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("{}",e);
		}
	}

	@ModelAttribute
	public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	/**
	 * 线程安全
	 * 
	 * @return
	 * @date 2015年11月24日
	 * @author 漂泊者及其影子
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 线程安全
	 * 
	 * @return
	 * @date 2015年11月24日
	 * @author 漂泊者及其影子
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * 输出信息到页面
	 * 
	 * @param msg：要输出的信息（可以是js脚本等）
	 */
	public void printOutMsg(String msg) {
		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.setContentType("text/html;charset=utf-8");
			PrintWriter out = null;
			out = this.response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出信息到页面
	 * 
	 * @param response
	 * @param msg
	 */
	public void printOutMsg(HttpServletResponse response, String msg) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取请求完整路径
	 * 
	 * @param request
	 * @return
	 */
	public String getUrl(HttpServletRequest request) {
		String url = request.getRequestURI();
		String params = "";
		if (request.getQueryString() != null) {
			params = request.getQueryString().toString();
		}
		if (!"".equals(params)) {
			url = url + "?" + params;
		}
		return url;
	}

	/**
	 * 获取日期
	 * 
	 * @param day
	 *            天
	 */
	public String getDate(int day) {
		StringBuffer s = new StringBuffer();
		Calendar c = Calendar.getInstance();
		int currentDay = c.get(Calendar.DATE);
		if (day < 0) {
			c.add(Calendar.YEAR, -1);
			c.set(Calendar.DATE, currentDay);
		} else if (day == 29) {
			c.add(Calendar.MONTH, -1);
			c.set(Calendar.DATE, currentDay);
		} else {
			c.add(Calendar.DATE, -day);
		}

		s.append(c.get(Calendar.YEAR) + "-");
		s.append((c.get(Calendar.MONTH) + 1) < 10 ? ("0" + (c.get(Calendar.MONTH) + 1)) : (c.get(Calendar.MONTH) + 1));
		s.append("-");
		s.append(c.get(Calendar.DATE) < 10 ? ("0" + c.get(Calendar.DATE)) : c.get(Calendar.DATE));
		return s.toString();
	}

	/**
	 * 转换统计的map
	 * 
	 * @param statMap
	 *            统计的map
	 * @param constMap
	 *            常量的map
	 * @return
	 */
	public Map<String, Long> getFmtMap(Map<String, Long> statMap, Map<Integer, String> constMap) {
		Map<String, Long> dataMap = null;
		if (statMap != null) {
			dataMap = new LinkedHashMap<String, Long>();
			for (Entry<String, Long> entry : statMap.entrySet()) {
				dataMap.put(constMap.get(Integer.valueOf(entry.getKey())) + "&" + Integer.valueOf(entry.getKey()),
						entry.getValue());
			}
		}
		return dataMap;
	}

	/**
	 * inStream 转 byte[]
	 * 
	 * @Title: input2byte @Description: inStream 转 byte[] @param @param
	 *         inStream @param @return @param @throws IOException 设定文件 @return
	 *         byte[] 返回类型 @throws
	 */
	public byte[] parse(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	/**
	 * outputStream转inputStream
	 * 
	 * @Title: parse @Description: outputStream转inputStream @param @param
	 *         out @param @return @param @throws Exception 设定文件 @return
	 *         ByteArrayInputStream 返回类型 @throws
	 */
	public ByteArrayInputStream parse(OutputStream out) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
		return swapStream;
	}

	protected String getBasePath(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String basePath = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
		return basePath;
	}
}
