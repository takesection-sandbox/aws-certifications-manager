package com.pigumer.aws_certifications_manager.adapter.dynamodb;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Component
public class DynamoDbClientFactory {

    private final DynamoDbClient client = DynamoDbClient.builder().region(Region.AP_NORTHEAST_1).build();

    public DynamoDbClient getClient() {
        return client;
    }
}
