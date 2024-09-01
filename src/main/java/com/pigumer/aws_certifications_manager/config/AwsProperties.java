package com.pigumer.aws_certifications_manager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String region;

    public void setRegion(String region) {
        this.region = region;
    }

    public String region() {
        return region;
    }
}
