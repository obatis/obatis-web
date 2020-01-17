package com.obatis.config.url;

import java.util.List;
import java.util.Map;

/**
 * 该接口提供url注册实现，如果需要将url信息注册到相应的服务或者进行处理，需要定义一个spring bean 实现该接口
 * 特别说明：接口中文描述信息获取目前是基于swagger信息，尚未开放拓展
 * @author HuangLongPu
 */
public interface RegisterUrlConfigure {

    /**
     * 注解url地址接口，map 中的url信息包含了notLogin url信息
     * url 存储的 map 类，key 为controller注解的URL地址，value 为注册URL的方法说明(借助于 swagger实现)
     * @param urlInfoList
     */
    void registerUrl(List<UrlBeanInfo> urlInfoList);

    /**
     * 注册无需登录即可访问的url地址接口
     * url 存储的 map 类，key 为controller注解的URL地址，value 为注册URL的方法说明(借助于 swagger实现)
     * @param notLoginUrlInfo
     */
    void registerNotLoginUrl(Map<String, String> notLoginUrlInfo);
}
