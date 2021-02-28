package com.obatis.config.time;

import com.obatis.convert.date.DateConstant;
import com.obatis.convert.date.DateConvert;
import com.obatis.tools.ValidateTool;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * 日期转换处理类，体现为前端传入字符串日期(时间)类型时，也可以正常接收
 */
@Configuration
public class DateConverterConfig implements Converter<String, Date> {
	
    @Override
    public Date convert(String source) {
    	
    	if(ValidateTool.isEmpty(source)) {
    		return null;
    	}

        // 将 '/' 转为 '-'
        if(source.contains("/")) {
            source = source.replace("/", "-");
        }

        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return DateConvert.toDate(DateConvert.parseDate(source, DateConstant.YEAR_MONTH_PATTERN));
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return DateConvert.toDate(DateConvert.parseDate(source, DateConstant.DATE_PATTERN));
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")){
            return DateConvert.toDate(DateConvert.parseDateTime(source, DateConstant.DATE_HOUR_PATTERN));
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return DateConvert.toDate(DateConvert.parseDateTime(source, DateConstant.DATE_HOUR_MINUTE_PATTERN));
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return DateConvert.toDate(DateConvert.parseDateTime(source, DateConstant.DATE_TIME_PATTERN));
        } else {
            throw new IllegalArgumentException("error: invalid date value '" + source + "'");
        }
    }

}
