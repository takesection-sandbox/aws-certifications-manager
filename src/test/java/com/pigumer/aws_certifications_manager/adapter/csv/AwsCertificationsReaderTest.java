package com.pigumer.aws_certifications_manager.adapter.csv;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;

public class AwsCertificationsReaderTest {

    @Test
    public void readTest() throws Exception {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Certification Data.csv.csv")) {
            AwsCertificationsReaderImpl reader = new AwsCertificationsReaderImpl();
            List<AwsCertification> list = reader.readList(is);
            assertEquals(list.size(), 13);
        }
    }
}
