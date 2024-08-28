package com.pigumer.aws_certifications_manager.domain.port;

import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;

import java.io.InputStream;
import java.util.List;

public interface AwsCertificationsReader {

    public List<AwsCertification> readList(InputStream is) throws Exception;
}
