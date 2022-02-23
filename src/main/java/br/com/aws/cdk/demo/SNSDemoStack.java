package br.com.aws.cdk.demo;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.events.targets.SnsTopic;
import software.amazon.awscdk.services.sns.Topic;
import software.amazon.awscdk.services.sns.subscriptions.SqsSubscription;
import software.amazon.awscdk.services.sqs.DeadLetterQueue;
import software.amazon.awscdk.services.sqs.Queue;
import software.constructs.Construct;

public class SNSDemoStack extends Stack {

    public SNSDemoStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public SNSDemoStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        var deadLetterQueue = DeadLetterQueue.builder()
                .queue(Queue.Builder.create(this, "DLQDemo")
                        .queueName("queue-demo-dlq")
                        .removalPolicy(RemovalPolicy.RETAIN)
                        .build())
                .maxReceiveCount(10)
                .build();
        var queue = Queue.Builder.create(this, "QueueDemo")
                .queueName("queue-demo")
                .deadLetterQueue(deadLetterQueue)
                .visibilityTimeout(Duration.seconds(30))
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        var sqsSubscription = SqsSubscription.Builder.create(queue)
                .build();
        var snsTopic = SnsTopic.Builder.create(Topic.Builder.create(this, id)
                        .topicName("sns-demo")
                        .build())
                .build();
        snsTopic.getTopic().addSubscription(sqsSubscription);
    }
}