package br.com.aws.cdk.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import java.util.HashMap;

@DisplayName("DynamoDB demo stack test")
public class DynamoDBDemoStackTest {

    @Test
    public void shouldTestDynamoDBParameters() {
        var app = new App();
        var dynamoDbDemoStack = new DynamoDBDemoStack(app, "DynamoDBDemo");

        var template = Template.fromStack(dynamoDbDemoStack);

        template.hasResourceProperties("AWS::DynamoDB::Table", new HashMap<String, String>() {{
            put("TableName", "dynamodb_demo");
        }});
    }
}