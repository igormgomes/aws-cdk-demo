package br.com.aws.cdk.demo;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.EnableScalingProps;
import software.amazon.awscdk.services.dynamodb.UtilizationScalingProps;
import software.constructs.Construct;

public class DynamoDBDemoStack extends Stack {

    public DynamoDBDemoStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public DynamoDBDemoStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        var table = Table.Builder.create(this, id)
                .tableName("dynamodb_demo")
                .billingMode(BillingMode.PROVISIONED)
                .writeCapacity(1)
                .readCapacity(1)
                .partitionKey(Attribute.builder()
                        .name("partition_key_demo")
                        .type(AttributeType.STRING)
                        .build())
                .sortKey(Attribute.builder()
                        .name("sort_key_demo")
                        .type(AttributeType.STRING)
                        .build())
                .timeToLiveAttribute("ttl_demo")
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        table.autoScaleWriteCapacity(EnableScalingProps.builder()
                        .minCapacity(1)
                        .maxCapacity(6)
                        .build())
                .scaleOnUtilization(UtilizationScalingProps.builder()
                        .targetUtilizationPercent(60)
                        .scaleInCooldown(Duration.seconds(20))
                        .scaleOutCooldown(Duration.seconds(20))
                        .build());

        table.autoScaleReadCapacity(EnableScalingProps.builder()
                        .minCapacity(1)
                        .maxCapacity(3)
                        .build())
                .scaleOnUtilization(UtilizationScalingProps.builder()
                        .targetUtilizationPercent(60)
                        .scaleInCooldown(Duration.seconds(30))
                        .scaleOutCooldown(Duration.seconds(30))
                        .build());
    }
}