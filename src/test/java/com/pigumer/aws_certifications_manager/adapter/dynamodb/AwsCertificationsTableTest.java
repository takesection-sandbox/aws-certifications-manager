package com.pigumer.aws_certifications_manager.adapter.dynamodb;

import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsReader;
import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsTable;
import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AwsCertificationsTableTest {

    @Test
    public void findAllTest(@Autowired AwsCertificationsTable table, @Autowired AwsCertificationsReader reader) throws Exception {
        List<AwsCertification> res = table.findAll();
        assertNotNull(res);
    }
}
