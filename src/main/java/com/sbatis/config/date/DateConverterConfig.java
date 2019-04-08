package com.sbatis.config.date;

import com.sbatis.convert.date.DateCommonConvert;
import com.sbatis.convert.date.DefaultDateConstant;
import com.sbatis.validate.ValidateTool;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

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
