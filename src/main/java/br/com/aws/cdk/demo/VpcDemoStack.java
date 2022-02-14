package br.com.aws.cdk.demo;

import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class VpcDemoStack extends Stack {

    public VpcDemoStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public VpcDemoStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Vpc.Builder.create(this, "vpc-demo")
                .maxAzs(3)
                .build();
    }
}