package com.obatis.config.message;

import com.obatis.constant.CharsetConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 设置编码
 * @author HuangLongPu
 */
@ConditionalOnBean(HttpMessageConverterConfigure.class)
@Configuration
public class HttpEncodingConfiguration {

	@Bean
	@ConditionalOnMissingBean(CharacterEncodingFilter.class)
	public CharacterEncodingFilter encodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new OrderedCharacterEncodingFilter();
		characterEncodingFilter.setEncoding(CharsetConstant.CHARSET_UTF8);
		characterEncodingFilter.setForceRequestEncoding(true);
		characterEncodingFilter.setForceResponseEncoding(true);
		return characterEncodingFilter;
	}
}
