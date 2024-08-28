package com.pigumer.aws_certifications_manager.domain.port;

import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;
import com.pigumer.aws_certifications_manager.domain.valueobject.Key;

import java.util.List;

public interface AwsCertificationsTable {

    public List<AwsCertification> findAll();

    public void save(AwsCertification awsCertification);

    public void delete(Key key);
}
