package br.com.aws.cdk.demo;

import org.junit.jupiter.api.DisplayName;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

@DisplayName("Vpc demo stack test")
public class VpcDemoStackTest {

    @Test
    public void shouldTestVpcParameters() {
        var app = new App();
        var vpcDemoStack = new VpcDemoStack(app, "VpcDemo");

        var template = Template.fromStack(vpcDemoStack);

        template.hasResourceProperties("AWS::EC2::VPC", new HashMap<String, Object>() {{
            put("EnableDnsHostnames", true);
            put("EnableDnsSupport", true);
            put("InstanceTenancy", "default");
        }});
    }
}