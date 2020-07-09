package com.obatis.config.message;


import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.obatis.constant.CharsetConstant;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 设置数据返回格式为fastjson格式，设置规则、转换代理处理方法等
 * @author HuangLongPu
 */
@Configuration
public class HttpMessageConverterConfigure {

    @Bean
    public HttpMessageConverters configureMessageConverters() {

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                SerializerFeature.WriteBigDecimalAsPlain,
                //    输出key是包含双引号
//                SerializerFeature.QuoteFieldNames,ßßß
                //    是否输出为null的字段,若为null 则显示该字段
//                SerializerFeature.WriteMapNullValue,
                //    数值字段如果为null，则输出为0
//                SerializerFeature.WriteNullNumberAsZero,
                //     List字段如果为null,输出为[],而非null
//                SerializerFeature.WriteNullListAsEmpty,
                //    字符类型字段如果为null,输出为"",而非null
//                SerializerFeature.WriteNullStringAsEmpty,
                //    Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                //    Date的日期转换器
                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteDateUseDateFormat,
                //    循环引用
                SerializerFeature.DisableCircularReferenceDetect,
        };

        fastJsonConfig.setSerializerFeatures(serializerFeatures);

//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName(CharsetConstant.CHARSET_UTF8));
        // 对BigInt 和BigDecimal类型做序列化处理，防止出现科学计数
        SerializeConfig serializeConfig =  new SerializeConfig();
        serializeConfig.put(BigInteger.class, new HttpMessageBigIntConvertSerializer());
        serializeConfig.put(BigDecimal.class, new HttpMessageBigIntConvertSerializer());
        serializeConfig.put(Long.class, new HttpMessageBigIntConvertSerializer());
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        // fastjson设置MediaType
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XML);
//        supportedMediaTypes.add(MediaType.IMAGE_GIF);
//        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//        supportedMediaTypes.add(MediaType.IMAGE_PNG);
//        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
//        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//        supportedMediaTypes.add(MediaType.TEXT_XML);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        return new HttpMessageConverters(fastJsonHttpMessageConverter);
    }
}