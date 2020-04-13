package com.obatis.config;

import com.alibaba.fastjson.JSONObject;
import com.obatis.config.response.result.callback.ExceptionRestParam;
import com.obatis.convert.JsonCommonConvert;
import com.obatis.convert.date.DateCommonConvert;
import com.obatis.net.HttpResponseResult;
import com.obatis.net.factory.HttpHandle;
import com.obatis.tools.EncodingTool;
import com.obatis.tools.ValidateTool;
import org.springframework.core.env.Environment;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public final class SystemConstant {

    public static final String CORE_BASE_DIR = "com.obatis";
    public static String PROJECT_BASE_DIR = null;
    /**
     * 系统处理队列
     */
    public static final BlockingQueue<ExceptionRestParam> HANDLE_QUEUE = new LinkedBlockingQueue<>();
    /**
     * 系统处理线程池
     */
    public static final ExecutorService HANDLE_POOL = Executors.newFixedThreadPool(3);

    /**
     * springboot 配置属性
     */
    private static Environment ENV;
    /**
     * 服务名称
     */
    public static String SERVICE_NAME;
    /**
     * 系统名称
     */
    public static String SYSTEM_NAME;
    /**
     * 运行环境，true 为开发环境，false为生产环境
     */
    private static boolean RUN_DEV_ENV;

    public static synchronized void setSystemEnv(Environment environment) {
        if(ENV == null) {
            ENV = environment;

            SERVICE_NAME = environment.getProperty("spring.application.name");
            SYSTEM_NAME = environment.getProperty("system.name");

            /**
             * 进行中文转换，防止乱码
             */
            if(!ValidateTool.isEmpty(SYSTEM_NAME) && !EncodingTool.isChineseEncoding(SYSTEM_NAME)) {
                SYSTEM_NAME = EncodingTool.isoEncodingUTF8(SYSTEM_NAME);
            }

            String runEvn =  environment.getProperty("spring.profiles.active");
            if(ValidateTool.isEmpty(runEvn) || runEvn.toLowerCase().equals("dev")) {
                RUN_DEV_ENV = true;
            }
        }
    }

    /**
     * 获取环境配置
     * @return
     */
    public static Environment getSystemEnv() {
        return ENV;
    }

    /**
     * 获取运行环境是否为开发环境，true表示开发环境，false表示不为开发环境
     * @return
     */
    public static boolean getRunDevEnv() {
        return RUN_DEV_ENV;
    }

    public static void main(String[] args) {

//        Map<String, Object> params = new HashMap<>();
//        params.put("sn", "3175051481");
//        params.put("key", "20181029CWXT0MD2J12");
//        params.put("mapType", "baidu");
////
//        HttpResponseResult result = HttpHandle.get("http://www.008gps.com/api/Tracking.aspx", params);
//        System.out.println(JsonCommonConvert.objConvertJson(result));

        String token = "32_fkD0h_KmRMZ4txJQ1mxZoW5O21SKYyVO5mqG-vYPUUkuRCBjaAx-fdvV9SRe0Of39zUk5SoMM2KUMB9fc7XabulBIIXj81mKU03XCNjK5AkB7rPErlY7H8sS3Vy-Sxinupa1WUEUw9MRguy8HFDaAEAKIG";
        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/subscribe?access_token=" + token;
//
        JSONObject object = palmOrderTemplateMessageBody(token);
        HttpResponseResult postJson = HttpHandle.postJson(postUrl, object);
        System.out.println(JsonCommonConvert.objConvertJson(postJson));

    }

    private static JSONObject palmOrderTemplateMessageBody(String token) {

        JSONObject body = new JSONObject();
        body.put("access_token", token);
        body.put("touser", "oDLZH42yzCt-3n_rnUHh1Hp5gDls"); //是    填接收消息的用户 openid
        body.put("template_id", "qswiQ8NVP2tyGrvygI-FvMiX-5oNfLk78RfNLoLEGO8");//是    订阅消息模板 ID
        body.put("url", "http://transport.gclasp.com/api/palmOrder/wxback");//否    点击消息跳转的链接，需要有 ICP 备案
        body.put("scene", 0);//是 订阅场景值
        body.put("title", "您有一个新订单");
        body.put("reserved", "hello");   // 否
        // 用于保持请求和回调的状态，授权请后原样带回给第三方。
        // 该参数可用于防止 csrf 攻击（跨站请求伪造攻击），建议第三方带上该参数，
        // 可设置为简单的随机数加 session 进行校验，开发者可以填写 a-zA-Z0-9 的参数值，最多 128 字节，要求做 urlencode

//        DataContent hello = new DataContent("hello");
        JSONObject hello = new JSONObject();
        hello.put("value", "hello");
        hello.put("color", "#FF0000");
        JSONObject data = new JSONObject();
        data.put("keyword1",hello );
        body.put("data", data);//是 消息正文，value 为消息内容文本（200 字以内），没有固定格式，可用\n 换行，color 为整段消息内容的字体颜色（目前仅支持整段消息为一种颜色）

        return body;
    }
}
