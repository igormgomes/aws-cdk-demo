package br.com.aws.cdk.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Match;
import software.amazon.awscdk.assertions.Template;

import java.util.List;
import java.util.Map;

@DisplayName("DynamoDB demo stack test")
public class DynamoDBDemoStackTest {

    @Test
    public void shouldTestDynamoDBTableParameters() {
        var app = new App();
        var dynamoDbDemoStack = new DynamoDBDemoStack(app, "DynamoDBDemo");

        var template = Template.fromStack(dynamoDbDemoStack);

        template.resourceCountIs("AWS::DynamoDB::Table", 1);
        template.hasResourceProperties("AWS::DynamoDB::Table", Map.of(
                "TableName", "dynamodb_demo",
                "KeySchema", List.of(
                        Match.objectEquals(Map.of(
                                "AttributeName", "partition_key_demo",
                                "KeyType", "HASH"
                        )),
                        Match.objectEquals(Map.of(
                                "AttributeName", "sort_key_demo",
                                "KeyType", "RANGE"
                        ))
                ),
                "AttributeDefinitions", List.of(
                        Match.objectEquals(Map.of(
                                "AttributeName", "partition_key_demo",
                                "AttributeType", "S"
                        )),
                        Match.objectEquals(Map.of(
                                "AttributeName", "sort_key_demo",
                                "AttributeType", "S"
                        ))
                ),
                "ProvisionedThroughput", Match.objectEquals(Map.of(
                        "ReadCapacityUnits", 1,
                        "WriteCapacityUnits", 1
                )),
                "TimeToLiveSpecification", Match.objectEquals(Map.of(
                        "AttributeName", "ttl_demo",
                        "Enabled", true
                ))
        ));
    }

    @Test
    public void shouldTestDynamoDBAutoScalingTarget() {
        var app = new App();
        var dynamoDbDemoStack = new DynamoDBDemoStack(app, "DynamoDBDemo");

        var template = Template.fromStack(dynamoDbDemoStack);

        template.resourceCountIs("AWS::ApplicationAutoScaling::ScalableTarget", 2);
        template.hasResourceProperties("AWS::ApplicationAutoScaling::ScalableTarget", Map.of(
                "MaxCapacity", 6,
                "MinCapacity", 1
        ));
    }

    @Test
    public void shouldTestDynamoDBAutoScalingPolicy() {
        var app = new App();
        var dynamoDbDemoStack = new DynamoDBDemoStack(app, "DynamoDBDemo");

        var template = Template.fromStack(dynamoDbDemoStack);

        template.resourceCountIs("AWS::ApplicationAutoScaling::ScalingPolicy", 2);
        template.hasResourceProperties("AWS::ApplicationAutoScaling::ScalingPolicy", Map.of(
                "TargetTrackingScalingPolicyConfiguration", Match.objectLike(Map.of(
                        "ScaleInCooldown", 20,
                        "ScaleOutCooldown", 20,
                        "TargetValue", 60
                ))
        ));
    }
}