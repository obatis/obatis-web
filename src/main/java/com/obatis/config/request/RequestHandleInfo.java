package com.obatis.config.request;

import com.obatis.constant.http.HttpConstant;
import com.obatis.tools.ValidateTool;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RequestHandleInfo {

	/**
	 * 获取请求的IP地址
	 * @return
	 */
	public static String getRequestIp() {
		return getRequestIp(getHttpServletRequest());
	}

	/**
	 * @Description: 获取请求的IP地址
	 * @date 2019-05-09
	 * @param request
	 * @return String
	 */
	public static String getRequestIp(HttpServletRequest request) {

		String ipAddress;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("X-Real-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		
		if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
			// 根据网卡取本机配置的IP
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ipAddress = inet.getHostAddress();
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取请求的设备相关信息
	 * @return
	 */
	public static RequestInfo getRequestInfo() {
		return getRequestInfo(getHttpServletRequest());
	}

	/**
	 * 获取请求的设备相关信息
	 * @param request
	 * @return
	 */
	public static RequestInfo getRequestInfo(HttpServletRequest request) {
		String agentInfo = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(agentInfo);
		// 操作系统信息
		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
		// 获取浏览器信息
		String browser = userAgent.getBrowser().getName();
		if(userAgent.getBrowserVersion() != null && !ValidateTool.isEmpty(userAgent.getBrowserVersion().getVersion())) {
			browser += " " + userAgent.getBrowserVersion().getVersion();
		}
		// 设备类型
		eu.bitwalker.useragentutils.DeviceType deviceType = operatingSystem.getDeviceType();
		String device;

		// 转化为消息，方便比对
		String agentInfoLowerCase = agentInfo == null ? "" : agentInfo.toLowerCase();

		switch (deviceType) {
			case COMPUTER:
				// PC电脑
				if(agentInfoLowerCase.contains("windows")) {
					device = "Windows PC";
				} else if (agentInfoLowerCase.contains("mac")) {
					device = "Mac PC";
				} else {
					device = "Other PC";
				}
				break;
			case TABLET:
				// 平板
				if (agentInfoLowerCase.contains("android") || agentInfoLowerCase.contains("android pad")) {
					device = "Android Pad";
				} else if (agentInfoLowerCase.contains("ios") || agentInfoLowerCase.contains("ipad") || agentInfoLowerCase.contains("mac")) {
					device = "iPad";
				} else {
					device = "Other Pad";
				}
				break;
			case MOBILE:
				// 手机
				if (agentInfoLowerCase.contains("android")) {
					device = "Android Phone";
				} else if (agentInfoLowerCase.contains("ios") || agentInfoLowerCase.contains("iphone") || agentInfoLowerCase.contains("mac")) {
					device = "iPhone";
				} else {
					device = "Other Phone";
				}
				break;
			default:
				// 其他设备
				device = "Other";
				break;
		}

		RequestInfo info = new RequestInfo();
		info.setRequestBrowser(browser);
		info.setDeviceTypeName(device);
		info.setRequestSystem(operatingSystem.getName());
		info.setRequestIp(getRequestIp(request));
		info.setRemotePort(request.getRemotePort());
		return info;
	}

	/**
	 * 获取用户请求的token
	 * 由 getAccountToken 方法替代
	 * @return
	 */
	@Deprecated
	public static String getAuthUserToken() {
		return getAccountToken();
	}

	/**
	 * 获取用户请求的token
	 * @return
	 */
	public static String getAccountToken() {
		return getAccountToken(getHttpServletRequest());
	}

	/**
	 * 获取用户请求的token
	 * @param request
	 * @return
	 */
	public static String getAccountToken(HttpServletRequest request) {
		return request.getHeader(HttpConstant.HEADER_ACCOUNT_TOKEN);
	}

	/**
	 * 获取request
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		return ((ServletRequestAttributes)requestAttributes).getRequest();
	}

	/**
	 * 获取 Response
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		return ((ServletRequestAttributes)requestAttributes).getResponse();
	}
}
