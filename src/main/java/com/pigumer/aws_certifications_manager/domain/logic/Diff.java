package com.pigumer.aws_certifications_manager.domain.logic;

import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsReader;
import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsTable;
import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;
import com.pigumer.aws_certifications_manager.domain.valueobject.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Diff {

    private final Logger logger = LoggerFactory.getLogger(Diff.class);

    private AwsCertificationsTable table;

    private AwsCertificationsReader reader;

    public Diff(AwsCertificationsTable table, AwsCertificationsReader reader) {
        this.table = table;
        this.reader = reader;
    }

    List<AwsCertification> newRecord(List<AwsCertification> csv, List<AwsCertification> db) {
        Set<Key> a = db.stream().map(item -> new Key(item.workEmail(), item.certificationName())).collect(Collectors.toUnmodifiableSet());
        return csv.stream().filter(item -> !a.contains(new Key(item.workEmail(), item.certificationName()))).toList();
    }

    List<AwsCertification> missingRecord(List<AwsCertification> csv, List<AwsCertification> db) {
        Set<Key> a = csv.stream().map(item -> new Key(item.workEmail(), item.certificationName())).collect(Collectors.toUnmodifiableSet());
        return db.stream().filter(item -> !a.contains(new Key(item.workEmail(), item.certificationName()))).toList();
    }

    List<AwsCertification> updatedRecord(List<AwsCertification> csv, List<AwsCertification> db) {
        Map<Key, AwsCertification> a = csv.stream().collect(Collectors.toMap(item -> new Key(item.workEmail(), item.certificationName()), item -> item));
        return db.stream().filter(item -> {
            Key key = new Key(item.workEmail(), item.certificationName());
            AwsCertification b = a.get(key);
            return b == null
                    || !item.awardedDate().equals(b.awardedDate())
                    || !item.expirationDate().equals(b.expirationDate());
        }).toList();
    }

    public void diff(InputStream is) throws Exception {
        List<AwsCertification> csv = reader.readList(is);
        List<AwsCertification> db = table.findAll();

        List<AwsCertification> updated = updatedRecord(csv, db);
        List<AwsCertification> newRecord = newRecord(csv, db);
        List<AwsCertification> missingRecord = missingRecord(csv, db);

        logger.info(String.format("New: %d", newRecord.size()));
        for (AwsCertification item : newRecord) {
            logger.info(String.format("New!! %s, %s", item.workEmail(), item.certificationName()));
            table.save(item);
        }

        logger.info(String.format("Updated: %d", updated.size()));
        for (AwsCertification item : updated) {
            logger.info(String.format("Updating %s, %s", item.workEmail(), item.certificationName()));
            table.save(item);
        }

        logger.info(String.format("Deleted: %d", missingRecord.size()));
        for (AwsCertification item : missingRecord) {
            logger.info(String.format("Deleting %s, %s", item.workEmail(), item.certificationName()));
            table.delete(new Key(item.workEmail(), item.certificationName()));
        }
    }
}
