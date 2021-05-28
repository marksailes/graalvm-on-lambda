---
title: "Infrastucture as Code (CDK)"
description: "How to define, deploy and test your Lambda function with CDK"
date: 2021-05-24T22:21:27+01:00
draft: false
weight: 30
---

Let's create the code required to deploy our initial Lambda function. Firstly you'll need to install the [AWS Cloud 
Development Kit (CDK)](https://docs.aws.amazon.com/cdk/latest/guide/work-with.html)

Now initialize a new CDK project called app.

```bash
cd infrastructure
cdk init app --language java
```

The initial project will look like this.

```
├── README.md
├── cdk.json
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── myorg
    │               ├── InfrastructureApp.java
    │               └── InfrastructureStack.java
    └── test
        └── java
            └── com
                └── myorg
                    └── InfrastructureTest.java

```

We'll rename the packages and add a Lambda function and API Gateway (HTTP API) to `InfrastructureStack.java`.

```java
package com.graalvmonlambda.infra;

import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.apigatewayv2.*;
import software.amazon.awscdk.services.apigatewayv2.integrations.LambdaProxyIntegration;
import software.amazon.awscdk.services.apigatewayv2.integrations.LambdaProxyIntegrationProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.logs.RetentionDays;

import static java.util.Collections.singletonList;

public class InfrastructureStack extends Stack {
    public InfrastructureStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public InfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Function productFunction = new Function(this, "ProductFunction", FunctionProps.builder()
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../software/products/target/product.jar"))
                .handler("com.graalvmonlambda.product.ProductRequestHandler")
                .memorySize(1024)
                .logRetention(RetentionDays.ONE_WEEK)
                .build());

        HttpApi httpApi = new HttpApi(this, "GraalVMOnLambdaAPI", HttpApiProps.builder()
                .apiName("GraalVMonLambdaAPI")
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/product")
                .methods(singletonList(HttpMethod.GET))
                .integration(new LambdaProxyIntegration(LambdaProxyIntegrationProps.builder()
                        .handler(productFunction)
                        .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                        .build()))
                .build());

        CfnOutput apiUrl = new CfnOutput(this, "ProductApiUrl", CfnOutputProps.builder()
                .exportName("ProductApiUrl")
                .value(httpApi.getApiEndpoint())
                .build());
    }
}

```

This will require a couple of extra CDK dependencies in your `pom.xml`.

```xml
<dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>lambda</artifactId>
    <version>${cdk.version}</version>
</dependency>
<dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>apigatewayv2</artifactId>
    <version>${cdk.version}</version>
</dependency>
<dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>apigatewayv2-integrations</artifactId>
    <version>${cdk.version}</version>
</dependency>
```

{{< hint info >}}
If you're new to CDK you'll need to run `cdk bootstrap` before you deploy anything.
{{< /hint >}}

Now you're ready to deploy

```bash
cdk deploy
```

You should see your API Gateway endpoint in the outputs.

```bash
Outputs:
InfrastructureStack.ProductApiUrl = https://xxxxxxxxxx.execute-api.eu-west-1.amazonaws.com
```

We can curl the output url to validate everything is ok.

```bash
curl -v https://xxxxxxxxxx.execute-api.eu-west-1.amazonaws.com/product
```