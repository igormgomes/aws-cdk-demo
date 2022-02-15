package br.com.aws.cdk.demo;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.constructs.Construct;

public class ClusterDemoStack extends Stack {

    public ClusterDemoStack(final Construct scope, final String id, Vpc vpc) {
        this(scope, id, null, vpc);
    }

    public ClusterDemoStack(final Construct scope, final String id, final StackProps props, Vpc vpc) {
        super(scope, id, props);

        Cluster.Builder.create(this, id)
                .clusterName("cluster-demo")
                .vpc(vpc)
                .build();
    }
}