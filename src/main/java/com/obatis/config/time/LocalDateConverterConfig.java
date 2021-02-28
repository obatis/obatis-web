package com.obatis.config.time;

import com.obatis.convert.date.DateConvert;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * 日期转换处理类，体现为前端传入字符串日期(时间)类型时，也可以正常接收
 */
@Configuration
public class LocalDateConverterConfig implements Converter<String, LocalDate> {
	
    @Override
    public LocalDate convert(String source) {
    	return DateConvert.parseDate(source);
    }

}
