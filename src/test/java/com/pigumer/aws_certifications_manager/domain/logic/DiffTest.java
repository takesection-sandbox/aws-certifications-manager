package com.pigumer.aws_certifications_manager.domain.logic;

import com.pigumer.aws_certifications_manager.adapter.csv.AwsCertificationsReaderImpl;
import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class DiffTest {

    private List<AwsCertification> list;

    public DiffTest() throws Exception {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Certification Data.csv.csv")) {
            AwsCertificationsReaderImpl reader = new AwsCertificationsReaderImpl();
            list = reader.readList(is);
        }
    }

    @Test
    public void newRecordTest() {
        Diff target = new Diff(null, null);
        List<AwsCertification> res = target.newRecord(list, Collections.emptyList());
        assertEquals(13, res.size());
    }

    @Test
    public void missingRecordTest() {
        Diff target = new Diff(null, null);
        assertEquals(13, list.size());
        List<AwsCertification> res = target.missingRecord(Collections.emptyList(), list);
        assertEquals(13, res.size());
    }

    @Test
    public void updatedRecordTest() {
        Diff target = new Diff(null, null);
        List<AwsCertification> res = target.updatedRecord(list, list);
        assertEquals(0, res.size());
        res = target.updatedRecord(Collections.emptyList(), Collections.emptyList());
        assertEquals(0, res.size());
    }


}
