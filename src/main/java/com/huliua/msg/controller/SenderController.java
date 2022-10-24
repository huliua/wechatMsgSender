package com.huliua.msg.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huliua.msg.config.AppConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号，发消息测试
 */
@RestController
@Log4j2
@RequestMapping("/wechat")
public class SenderController {

    @Resource
    private AppConfig appConfig;

    /**
     * 沈阳体育
     */
    private static final String APPID = "wx653dba3c4fb639c8";
    private static final String APPSECRET = "73a441da1eec1b9563976ad6a6e38c07";
    private static final String TPL_ID = "PCKyMF3sk9a1bokMtepaF2diB7gDKUSllc54c-RUfbo";

    // openId
    private static final String myOpenId = "ozq716HmzO3xTT4OFLXk-4qlnJsI";

    @RequestMapping("/send.do")
    public String sendMsg(String openid) {
        // getAccessToken
        String getAccessUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appConfig.getAppId() + "&secret=" + appConfig.getAppSecret();
        String result = HttpUtil.get(getAccessUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String token = jsonObject.getStr("access_token");
        if (StrUtil.isBlank(token)) {
            return result;
        }

        // sendMsg
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        Map<String, Object> paramMap = buildParam(myOpenId);
        log.info(paramMap.toString());
        String sendResult = HttpUtil.post(url, paramMap.toString());
        return sendResult;
    }

    /**
     * 构建参数信息
     */
    private Map<String, Object> buildParam(String openId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("touser", openId);
        paramMap.put("template_id", appConfig.getTemplateId());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("first", "{\"value\": \"您好，您明天要上1门课\",\"color\": \"#173177\"}");
        dataMap.put("keyword1", "{\"value\": \"2022年10月24日 星期一\",\"color\": \"#173177\"}");
        dataMap.put("keyword2", "{\"value\": \"第1-2节\",\"color\": \"#173177\"}");
        dataMap.put("remark", "{\"value\": \"您明天要上的课程有：研究生测试信息，第1-2节 综合楼101\",\"color\": \"#173177\"}");
        paramMap.put("data", dataMap);
        return paramMap;
    }
}
