package com.sbatis.config.request;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 设置后端允许跨域访问
 * @author HuangLongPu
 */
@Configuration
public class CorsConfig {

	/**
	 * 跨域设置
	 * @author HuangLongPu
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", this.buildCorsConfig());
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	/**
	 * 设置跨域的http请求头
	 * @author HuangLongPu
	 * @return
	 */
	private CorsConfiguration buildCorsConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setMaxAge(18000L);
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}
}
