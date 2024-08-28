package com.pigumer.aws_certifications_manager.adapter.dynamodb;

import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsTable;
import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;
import com.pigumer.aws_certifications_manager.domain.valueobject.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AwsCertificationsTableImpl implements AwsCertificationsTable {

    private final Logger logger = LoggerFactory.getLogger(AwsCertificationsTableImpl.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DynamoDbClient client;

    private final String tableName = "AWSCertifications";

    public AwsCertificationsTableImpl(@Autowired DynamoDbClientFactory factory) {
        this.client = factory.getClient();
    }

    @Override
    public List<AwsCertification> findAll() {
        ScanRequest req = ScanRequest.builder().tableName(tableName).build();
        ArrayList<AwsCertification> list = new ArrayList<>();
        while (true) {
            ScanResponse res = client.scan(req);
            if (res.hasItems()) {
                for (Map<String, AttributeValue> item : res.items()) {
                    list.add(new AwsCertification(
                            item.get("UserName").s(),
                            item.get("WorkEmail").s(),
                            item.get("CertificationName").s(),
                            item.get("CertificationLevel").s(),
                            LocalDate.parse(item.get("AwardedDate").s(), formatter),
                            LocalDate.parse(item.get("ExpirationDate").s(), formatter)
                    ));
                }
            }
            if (!res.hasLastEvaluatedKey()) {
                break;
            }
            req = ScanRequest.builder().tableName(tableName).exclusiveStartKey(res.lastEvaluatedKey()).build();
        }
        return list;
    }

    @Override
    public void save(AwsCertification awsCertification) {
        PutItemRequest req = PutItemRequest
                .builder()
                .tableName(tableName)
                .item(Map.of(
                        "WorkEmail", AttributeValue.builder().s(awsCertification.workEmail()).build(),
                        "CertificationName", AttributeValue.builder().s(awsCertification.certificationName()).build(),
                        "CertificationLevel", AttributeValue.builder().s(awsCertification.certificationLevel()).build(),
                        "UserName", AttributeValue.builder().s(awsCertification.userName()).build(),
                        "AwardedDate", AttributeValue.builder().s(formatter.format(awsCertification.awardedDate())).build(),
                        "ExpirationDate", AttributeValue.builder().s(formatter.format(awsCertification.expirationDate())).build()
                )).build();
        PutItemResponse res = client.putItem(req);
        logger.debug(res.toString());
    }

    @Override
    public void delete(Key key) {
        DeleteItemRequest req = DeleteItemRequest
                .builder()
                .tableName(tableName)
                .key(Map.of(
                        "WorkEmail", AttributeValue.builder().s(key.workEmail()).build(),
                        "CertificationName", AttributeValue.builder().s(key.certificationName()).build()
                        ))
                .build();
        DeleteItemResponse res = client.deleteItem(req);
        logger.debug(res.toString());
    }
}
