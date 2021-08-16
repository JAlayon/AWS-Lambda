package com.alayon.aws.lambda.api;

import com.alayon.aws.lambda.api.model.Order;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateOrderLambda {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final  DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    private final Table ordersTable;

    public CreateOrderLambda(){
        ordersTable = dynamoDB.getTable(System.getenv("ORDERS_TABLE"));
    }

    public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent requestEvent)
            throws JsonProcessingException {
        Order order = objectMapper.readValue(requestEvent.getBody(), Order.class);


        Item item = new Item()
                        .withPrimaryKey("id", order.id)
                        .withString("itemId", order.itemName)
                        .withInt("quantity", order.quantity);
        ordersTable.putItem(item);
       return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Order created with ID: " + order.id);
    }
}
