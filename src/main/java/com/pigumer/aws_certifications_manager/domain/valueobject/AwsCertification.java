package com.pigumer.aws_certifications_manager.domain.valueobject;

import java.time.LocalDate;

public record AwsCertification (
    String userName,
    String workEmail,
    String certificationName,
    String certificationLevel,
    LocalDate awardedDate,
    LocalDate expirationDate
) {
} 
