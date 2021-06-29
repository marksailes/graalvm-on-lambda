package com.graalvmonlambda.product;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DependencyFactory {

    private DependencyFactory() {

    }

    public static DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .region(Region.EU_WEST_1)
                .build();
    }
}
