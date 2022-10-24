package com.huliua.msg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "wechat")
public class AppConfig {
    private String appId;

    private String appSecret;

    private String templateId;
}
