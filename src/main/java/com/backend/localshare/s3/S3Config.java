package com.backend.localshare.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

//    @Value("${aws.region}")
//    private String awsRegion;

    // Todo: use environment variable for access key and secret key
    @Bean
    public S3Client s3Client() {

        return S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of("ap-south-1"))
                .build();
    }

}