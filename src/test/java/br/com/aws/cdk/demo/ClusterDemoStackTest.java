package br.com.aws.cdk.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import java.util.HashMap;

@DisplayName("Cluster demo stack test")
public class ClusterDemoStackTest {

    @Test
    public void shouldTestClusterParameters() {
        var app = new App();
        var vpcDemoStack = new VpcDemoStack(app, "VpcDemo");
        var clusterDemo = new ClusterDemoStack(app, "ClusterDemo", vpcDemoStack.getVpc());

        var template = Template.fromStack(clusterDemo);

        template.hasResourceProperties("AWS::ECS::Cluster", new HashMap<String, String>() {{
            put("ClusterName", "cluster-demo");
        }});
    }
}