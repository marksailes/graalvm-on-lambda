package com.graalvmonlambda.product;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.Map;

public class ProductRequestHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private final DynamoDbClient dynamoDbClient;

    public ProductRequestHandler() {
        dynamoDbClient = DependencyFactory.dynamoDbClient();
        performSimpleGetOnDynamoDB();
    }

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        context.getLogger().log(event.getRequestContext().getRequestId());

        long start = System.currentTimeMillis();
        performSimpleGetOnDynamoDB();
        long end = System.currentTimeMillis();

        System.out.println("dynamoDbClient.getItem() took " + (end - start) + " ms");

        return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(200)
                .build();
    }

    private void performSimpleGetOnDynamoDB() {
        dynamoDbClient.getItem(GetItemRequest.builder()
                .key(Map.of("PK", AttributeValue.builder().s("test").build()))
                .tableName("MoviesTable")
                .build());
    }
}
