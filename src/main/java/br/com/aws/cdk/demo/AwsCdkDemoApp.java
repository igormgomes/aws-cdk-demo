package br.com.aws.cdk.demo;

import software.amazon.awscdk.App;

public class AwsCdkDemoApp {

    public static void main(final String[] args) {
        var app = new App();

        var vpcDemoStack = new VpcDemoStack(app, "VpcDemo");

        app.synth();
    }
}