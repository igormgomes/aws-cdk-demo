package br.com.aws.cdk.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Match;
import software.amazon.awscdk.assertions.Template;

import java.util.List;
import java.util.Map;

@DisplayName("DynamoDB demo stack test")
public class DynamoDBDemoStackTest {

    private DynamoDBDemoStack dynamoDBDemoStack;

    @BeforeEach
    public void before()  {
        var app = new App();
        this.dynamoDBDemoStack = new DynamoDBDemoStack(app, "DynamoDBDemo");
    }

    @Test
    public void shouldTestDynamoDBTableParameters() {
        var template = Template.fromStack(this.dynamoDBDemoStack);

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
    public void shouldTestDynamoDBAutoScalingTargetWrite() {
        var template = Template.fromStack(this.dynamoDBDemoStack);

        template.resourceCountIs("AWS::ApplicationAutoScaling::ScalableTarget", 2);
        template.hasResourceProperties("AWS::ApplicationAutoScaling::ScalableTarget", Map.of(
                "MaxCapacity", 6,
                "MinCapacity", 1
        ));
    }

    @Test
    public void shouldTestDynamoDBAutoScalingTargetRead() {
        var template = Template.fromStack(this.dynamoDBDemoStack);

        template.resourceCountIs("AWS::ApplicationAutoScaling::ScalableTarget", 2);
        template.hasResourceProperties("AWS::ApplicationAutoScaling::ScalableTarget", Map.of(
                "MaxCapacity", 3,
                "MinCapacity", 1
        ));
    }

    @Test
    public void shouldTestDynamoDBAutoScalingPolicyWrite() {
        var template = Template.fromStack(this.dynamoDBDemoStack);

        template.resourceCountIs("AWS::ApplicationAutoScaling::ScalingPolicy", 2);
        template.hasResourceProperties("AWS::ApplicationAutoScaling::ScalingPolicy", Map.of(
                "TargetTrackingScalingPolicyConfiguration", Match.objectEquals(Map.of(
                        "PredefinedMetricSpecification", Match.objectEquals(Map.of(
                                "PredefinedMetricType", "DynamoDBWriteCapacityUtilization"
                        )),
                        "ScaleInCooldown", 20,
                        "ScaleOutCooldown", 20,
                        "TargetValue", 60
                ))
        ));
    }

    @Test
    public void shouldTestDynamoDBAutoScalingPolicyRead() {
        var template = Template.fromStack(this.dynamoDBDemoStack);

        template.resourceCountIs("AWS::ApplicationAutoScaling::ScalingPolicy", 2);
        template.hasResourceProperties("AWS::ApplicationAutoScaling::ScalingPolicy", Map.of(
                "TargetTrackingScalingPolicyConfiguration", Match.objectEquals(Map.of(
                        "PredefinedMetricSpecification", Match.objectEquals(Map.of(
                                "PredefinedMetricType", "DynamoDBReadCapacityUtilization"
                        )),
                        "ScaleInCooldown", 30,
                        "ScaleOutCooldown", 30,
                        "TargetValue", 60
                ))
        ));
    }
}