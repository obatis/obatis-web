package com.obatis.config.date;

import com.obatis.convert.date.DateCommonConvert;
import com.obatis.convert.date.DefaultDateConstant;
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
        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return DateCommonConvert.parseDate(source, DefaultDateConstant.YEAR_MONTH_PATTERN);
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return DateCommonConvert.parseDate(source, DefaultDateConstant.DATE_PATTERN);
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")){
            return DateCommonConvert.parseDate(source, DefaultDateConstant.DATE_HOUR_PATTERN);
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return DateCommonConvert.parseDate(source, DefaultDateConstant.DATE_HOUR_MINUTE_PATTERN);
        } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return DateCommonConvert.parseDate(source, DefaultDateConstant.DATE_TIME_PATTERN);
        } else {
            throw new IllegalArgumentException("error: invalid date value '" + source + "'");
        }
    }

}
