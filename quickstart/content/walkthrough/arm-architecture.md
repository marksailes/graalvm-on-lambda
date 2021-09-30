---
title: "Native Image on ARM Architecture"
description: "Comparing GraalVM native-image performance against Java 11 on AWS Lambda"
date: 2021-06-10T20:58:10+01:00
draft: false
weight: 90
---

AWS Lambda with Graviton2 chips now supports ARM architecture as well as x86. In order to use this faster and cheaper
architecture you will need to compile your application on a machine with the same architecture.

Let's go through the changes you need to make to adapt your application to you the ARM architecture.

The Lambda function code itself has a dependency on the AWS Runtime Interface Client this will need to be upgraded. 
This module uses native bindings and it has been updated to work with both x86 and ARM architectures. Upgrade to version 
2.0.0.

`software/products/pom.xml`

```xml
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-lambda-java-runtime-interface-client</artifactId>
    <version>2.0.0</version>
</dependency>
```

On the infrastructure side we need to make a couple of changes. 

We need to upgrade the version of CDK we're using

```shell
npm install -g aws-cdk
```

Then upgrade the CDK dependencies in our infrastructure code to at least version `1.125.0`.

`infrastructure/src/pom.xml`

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <cdk.version>1.125.0</cdk.version>
    <junit.version>5.7.1</junit.version>
</properties>
```

Next we need to use a different Docker image to build our application. In our infrastructure code we'll change the 
bundling options.

`infrastructure/src/main/java/com/graalvmonlambda/infra/InfrastructureStack.java`

```java
BundlingOptions.Builder builderOptions = BundlingOptions.builder()
        .command(functionOnePackagingInstructions)
        .image(DockerImage.fromRegistry("marksailes/arm64-al2-graalvm:al2-21.2.0"))
        .volumes(singletonList(
                DockerVolume.builder()
                        .hostPath(System.getProperty("user.home") + "/.m2/")
                        .containerPath("/root/.m2/")
                        .build()
        ))
        .user("root")
        .outputType(ARCHIVED);
```

Finally we need to change the function definition to include the ARM architecture.

```java
Function productFunction = new Function(this, "ProductFunction", FunctionProps.builder()
        .runtime(Runtime.PROVIDED_AL2)
        .code(Code.fromAsset("../software/", AssetOptions.builder()
                .bundling(builderOptions.build())
                .build()))
        .handler("com.graalvmonlambda.product.ProductRequestHandler")
        .memorySize(256)
        .logRetention(RetentionDays.ONE_WEEK)
        .architectures(singletonList(Architecture.ARM_64))
        .build());
```