package br.com.aws.cdk.demo;

import br.com.aws.cdk.demo.VpcDemoStack;
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
        var stack = new VpcDemoStack(app, "VpcDemo");

        var template = Template.fromStack(stack);

        template.hasResourceProperties("AWS::EC2::VPC", new HashMap<String, Boolean>() {{
            put("EnableDnsHostnames", true);
        }});
    }
}