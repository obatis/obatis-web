package com.obatis.config.request;

import com.obatis.constant.http.HttpConstant;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
		String agentString = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(agentString);
		OperatingSystem operatingSystem = userAgent.getOperatingSystem(); // 操作系统信息
		String browser = userAgent.getBrowser() + " " + userAgent.getBrowserVersion();
		eu.bitwalker.useragentutils.DeviceType deviceType = operatingSystem.getDeviceType(); // 设备类型
		String device;
		switch (deviceType) {
			case COMPUTER:
				// PC电脑
				device = "PC";
				break;
			case TABLET:
				// 平板
				if (agentString.contains("Android")) {
					device = "Android Pad";
				} else if (agentString.contains("iOS")) {
					device = "iPad";
				} else {
					device = "Other Pad";
				}
				break;
			case MOBILE:
				// 手机
				if (agentString.contains("Android")) {
					device = "Android";
				} else if (agentString.contains("iOS")) {
					device = "IOS";
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
		info.setDeviceType(device);
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
}
