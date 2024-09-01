package com.pigumer.aws_certifications_manager.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AwsPropertiesTest {

    @Autowired
    private AwsProperties awsProperties;

    @Test
    public void test() {
        assertEquals("ap-northeast-1", awsProperties.region());
    }
}