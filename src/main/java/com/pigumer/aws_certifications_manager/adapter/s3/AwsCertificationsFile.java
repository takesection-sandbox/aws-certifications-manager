package com.pigumer.aws_certifications_manager.adapter.s3;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AwsCertificationsFile {

    private final S3Client client;

    public AwsCertificationsFile() {
        this.client = S3Client.builder().region(Region.AP_NORTHEAST_1).build();
    }

    public InputStream getFile(String bucket, String key) throws IOException {
        GetObjectRequest req = GetObjectRequest.builder().bucket(bucket).key(key).build();
        return client.getObject(req);
    }
}
