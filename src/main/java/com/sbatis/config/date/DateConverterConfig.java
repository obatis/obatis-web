package com.sbatis.config.date;

import com.sbatis.convert.date.DateCommonConvert;
import com.sbatis.convert.date.DefaultDateConstant;
import com.sbatis.validate.ValidateTool;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 日期转换处理类，体现为前端传入字符串日期(时间)类型时，也可以正常接收
 */
@Component
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
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

}
