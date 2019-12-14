package com.snake.mcf.common.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.utils.ExceptionUtils;
import com.snake.mcf.common.utils.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CommonController {

	/**
	 * ThreadLocal确保高并发下每个请求的request，response都是独立的
	 */
	private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
	private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();

	protected HttpServletRequest request;
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
		currentRequest.set(request);
		currentResponse.set(response);
	}

	@ModelAttribute
	public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
		log.info("init method -- > {}", "setReqAndResp");
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	//只需要加上下面这段即可，注意不能忘记注解
    /*@InitBinder
    public void initBinderYmd(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }*/

	/**
	 * 线程安全
	 * 
	 * @return
	 * @date 2015年11月24日
	 * @author 漂泊者及其影子
	 */
	public HttpServletRequest getRequest() {
		log.info("init method -- > {}", "getRequest");
		return (HttpServletRequest) currentRequest.get();
	}

	/**
	 * 线程安全
	 * 
	 * @return
	 * @date 2015年11月24日
	 * @author 漂泊者及其影子
	 */
	public HttpServletResponse getResponse() {
		log.info("init method -- > {}", "getResponse");
		return (HttpServletResponse) currentResponse.get();
	}

	/**
	 * 输出信息到页面
	 * 
	 * @param msg：要输出的信息（可以是js脚本等）
	 */
	public void printOutMsg(String msg) {
		log.info("init method -- > {}", "printOutMsg");
		try {
			this.response.setCharacterEncoding(PayplatformConstant.ENCODE_UTF_8);
			this.response.setContentType(PayplatformConstant.CONTENT_TYPE_ENCODE_UTF_8);
			PrintWriter out = null;
			out = this.response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			ExceptionUtils.getStackTrace(e);
			throw new BusinessException("输出信息到页面据失败: msg={}", msg);
		}
	}

	/**
	 * 输出信息到页面
	 * 
	 * @param response
	 * @param msg
	 */
	public void printOutMsg(HttpServletResponse response, String msg) {
		log.info("init method -- > {}", "printOutMsg");
		try {
			response.setCharacterEncoding(PayplatformConstant.ENCODE_UTF_8);
			response.setContentType(PayplatformConstant.CONTENT_TYPE_ENCODE_UTF_8);
			PrintWriter out = null;
			out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			ExceptionUtils.getStackTrace(e);
			throw new BusinessException("输出信息到页面据失败: msg={}", msg);
		}
	}

	/**
	 * 获取请求完整路径
	 * 
	 * @param request
	 * @return
	 */
	public String getUrl(HttpServletRequest request) {
		log.info("init method -- > {}", "getUrl");
		String url = request.getRequestURI();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			url = url + PayplatformConstant.SPLIT_SYMBOL_ASK + queryString;
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

		s.append(c.get(Calendar.YEAR) + PayplatformConstant.SPLIT_SYMBOL_MIDDLELINE);
		s.append((c.get(Calendar.MONTH) + 1) < 10 ? ("0" + (c.get(Calendar.MONTH) + 1)) : (c.get(Calendar.MONTH) + 1));
		s.append(PayplatformConstant.SPLIT_SYMBOL_MIDDLELINE);
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
				dataMap.put(constMap.get(Integer.valueOf(entry.getKey())) + PayplatformConstant.SPLIT_SYMBOL_AND
						+ Integer.valueOf(entry.getKey()), entry.getValue());
			}
		}
		return dataMap;
	}

	/**
	 * inStream 转 byte[]
	 * 
	 * @author: hengoking
	 * @param inStream
	 * @return
	 * @throws IOException
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
	 * @author: hengoking
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public ByteArrayInputStream parse(OutputStream out) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
		return swapStream;
	}

}
