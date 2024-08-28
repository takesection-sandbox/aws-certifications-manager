package com.pigumer.aws_certifications_manager;

import com.pigumer.aws_certifications_manager.adapter.s3.AwsCertificationsFile;
import com.pigumer.aws_certifications_manager.domain.logic.Diff;
import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsReader;
import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;

@SpringBootApplication
public class AwsCertificationsManagerApplication implements CommandLineRunner {

	@Autowired
	private AwsCertificationsFile awsCertificationsFile;

	@Autowired
	private AwsCertificationsTable awsCertificationsTable;

	@Autowired
	private AwsCertificationsReader awsCertificationsReader;

	public static void main(String[] args) {
		SpringApplication.run(AwsCertificationsManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length == 2) {
			System.out.println(String.format("Bucket: %s, Key: %s", args[0], args[1]));
			try (InputStream is = awsCertificationsFile.getFile(args[0], args[1])) {
				Diff diff = new Diff(awsCertificationsTable, awsCertificationsReader);
				diff.diff(is);
			}
		}
	}
}
