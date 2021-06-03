---
title: "CDK Docker Build"
description: "Use Docker from within CDK to create a native binary for Amazon Linux 2"
date: 2021-06-03T21:07:46+01:00
draft: false
weight: 50
---

I imagine many developers use a Mac. If you use native-image to create a native binary, this
binary won't work on AWS Lambda (Amazon Linux 2). To solve this problem you can build the binary
with a docker file, using Amazon Linux 2 (AL2) as the base.

I've built and published my own on dockerhub [marksailes/al2graalvm](https://hub.docker.com/repository/docker/marksailes/al2-graalvm).

The Dockerfile is available on GitHub [marksailes/al2-graalvm](https://github.com/marksailes/al2-graalvm), and I'd be 
very happy to receive pull requests. Docker isn't something I'm particular deep with. 

This image uses AL2 as the base and then adds GraalVM, native-image and maven.

### Extend CDK to build and package our native binary

The functionality within CDK is called `BundlingOptions`. CDK puts our code into `/asset-input` and
expects the output in `/asset-output`. We change directory to our `products` folder and run our
normal `mvn clean install` with our `native-image` profile.

Bundling options allows us to specify the image, which can be for a public repo like dockerhub.
We mount our local `~/.m2` folder to speed up builds.

[InfrastructureStack.java](https://github.com/marksailes/graalvm-on-lambda/blob/a45e3505c2abb74e279ef13faaac31cc4ce2ce76/walkthrough/serverless-graalvm/infrastructure/src/main/java/com/graalvmonlambda/infra/InfrastructureStack.java#L33)

```java
List<String> functionOnePackagingInstructions = Arrays.asList(
        "-c",
        "cd products " +
                "&& mvn clean install -P native-image "
               + "&& cp /asset-input/products/target/function.zip /asset-output/"
);

BundlingOptions.Builder builderOptions = BundlingOptions.builder()
        .command(functionOnePackagingInstructions)
        .image(DockerImage.fromRegistry("marksailes/al2-graalvm"))
        .volumes(singletonList(
                DockerVolume.builder()
                        .hostPath(System.getProperty("user.home") + "/.m2/")
                        .containerPath("/root/.m2/")
                        .build()
        ))
        .user("root")
        .outputType(ARCHIVED);
```

Our function changes in a number of ways. We move from a `JAVA_11` runtime to `PROVIDED_AL2`.
The code come from the docker output, and we can reduce the memory size.

[InfrastructureStack.java](https://github.com/marksailes/graalvm-on-lambda/blob/a45e3505c2abb74e279ef13faaac31cc4ce2ce76/walkthrough/serverless-graalvm/infrastructure/src/main/java/com/graalvmonlambda/infra/InfrastructureStack.java#L52)

```java
Function productFunction = new Function(this, "ProductFunction", FunctionProps.builder()
        .runtime(Runtime.PROVIDED_AL2)
        .code(Code.fromAsset("../software/", AssetOptions.builder()
                .bundling(builderOptions.build())
                .build()))
        .handler("com.graalvmonlambda.product.ProductRequestHandler")
        .memorySize(256)
        .logRetention(RetentionDays.ONE_WEEK)
        .build());
```

Now when we do `cdk deploy`, CDK will run our maven project within our docker image 
produce a zip file with the bootstrap and native binary, then push this all to AWS.