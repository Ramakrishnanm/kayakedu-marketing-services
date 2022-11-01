package com.kayakedu.marketing.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "kayakedu.slovak")
@lombok.Getter
@lombok.Setter
public class SlovakConfig {

    private String visaTemplate;
    private String visaInputFileData;
    private EmailConfig email;

    @lombok.Getter
    @lombok.Setter
    public static class EmailConfig {
        private String[] receiver;
        private String subject;
    }
}
