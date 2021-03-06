---
title: "Examples showing GraalVM on AWS Lambda"
description: "Examples showing various implementations of GraalVM native image deployed to AWS Lambda"
date: 2021-04-29T21:21:46+01:00
draft: false
weight: 30
---

{{< button href="https://github.com/marksailes/graalvm-on-lambda/issues/new?assignees=marksailes&labels=&template=new-guide-submission.md&title=New+guide+suggestion" >}}Submit an example{{< /button >}}

{{< columns >}}
[<img src="/guides/aws-quarkus-demo.png" alt="demo" class="img-responsive">](https://aws.amazon.com/blogs/architecture/field-notes-optimize-your-java-application-for-aws-lambda-with-quarkus/)

<span><img src="/aws/Arch_App-Integration/Arch_32/Arch_Amazon-API-Gateway_32.svg" title="Amazon API Gateway"></span>
<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>
<span><img src="/aws/Arch_Database/32/Arch_Amazon-DynamoDB_32.svg" title="Amazon DynamoDB"></span>
<span><img src="/aws/sam_squirrel.jpeg" title="AWS SAM" width="32"></span>
<span><img src="/quarkus_icon_rgb_32px_default.png" title="Quarkus"></span>

This blog post shows you an effective approach for implementing a Java-based application and compiling it into a native-image through Quarkus.

[<img src="/github/GitHub-Mark-32px.png">](https://github.com/aws-samples/aws-quarkus-demo/)

<--->
[<img src="/guides/kabisa-tech-blog.png" alt="api gateway authorizer" class="img-responsive">](https://www.kabisa.nl/tech/beat-java-cold-starts-in-aws-lambdas-with-graalvm/)

<span><img src="/aws/Arch_App-Integration/Arch_32/Arch_Amazon-API-Gateway_32.svg" title="Amazon API Gateway"></span>
<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>

A practical walk through of building an API Gateway Authorizer, including the full source code.

[<img src="/github/GitHub-Mark-32px.png">](https://github.com/VR4J/aws-enriching-lambda-authorizer)

<--->
[<img src="/guides/cloudway-gramba.png" alt="graalvm benchmark" class="img-responsive">](https://www.cloudway.be/blog/gramba-graalvm-native-image-runtime-and-toolchain-aws-lambda)

<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>

An analysis of a real world application with and without GraalVM native-image.

{{< /columns >}}

{{< columns >}}
[<img src="/usage/examples/aws-lambda-with-scala-and-graalvm.png" alt="AWS Lambda with Scala and GraalVM" class="img-responsive">](https://medium.com/@mateuszstankiewicz/aws-lambda-with-scala-and-graalvm-eb1cc46b7740)

<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>
<span><img src="/icons/scala-spiral.png" title="Scala"></span>

[Mateusz Stankiewicz](https://medium.com/@mateuszstankiewicz) discusses the step-by-step process he took in creating a 
Scala Lambda function to handle a maintenance job on a database. He uses a bash script to poll the Runtime API.

<--->
[<img src="/usage/examples/tackling-java-cold-startup-times.png" alt="Tackling Java cold startup times on AWS Lambda with GraalVM" class="img-responsive">](https://arnoldgalovics.com/tackling-java-cold-startup-times-on-aws-lambda-with-graalvm/)

<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>
<span><img src="/aws/Arch_Database/32/Arch_Amazon-DynamoDB_32.svg" title="Amazon DynamoDB"></span>

[Arnold Galovics](https://twitter.com/ArnoldGalovics) Writes an amazingly detailed piece about how he put together the 
puzzle of GraalVM on Lambda. Details of the Dockerfile he used to compile the native image as well as performance data. 
It really is a great read.

[<img src="/github/GitHub-Mark-32px.png">](https://github.com/redskap/aws-lambda-java-runtime)

<--->
[<img src="/usage/examples/fast-cold-starts-for-clojure.png" alt="Fast Cold Starts for Clojure in AWS Lambda using GraalVM native-image" class="img-responsive">](https://nitor.com/en/articles/fast-cold-starts-for-clojure-in-aws-lambda-using-graalvm-native-image)

<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>
<span><img src="/icons/32px-Clojure_logo.svg.png" title="Clojure"></span>
<span><img src="/icons/terraform-icon.png" title="Terraform" width="32px"></span>

[Esko Luontola](https://twitter.com/EskoLuontola) takes us step by step how he packages his native images into a container
images and runs it on AWS Lambda. His project uses the [AWS Runtime Interface Client](https://github.com/aws/aws-lambda-java-libs/tree/master/aws-lambda-java-runtime-interface-client).
All his project code is available on Github.

[<img src="/github/GitHub-Mark-32px.png">](https://github.com/luontola/native-clojure-lambda)

{{< /columns >}}

{{< columns >}}
[<img src="/usage/examples/does-not-have-to-be-slow.png" alt="Java with Lambda does not have to be slow" class="img-responsive">](https://www.luminis.eu/blog/cloud-en/java-with-lambda-does-not-have-to-be-slow/)

<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>
<span><img src="/aws/cdk.png" title="CDK" width="32"></span>
<span><img src="/quarkus_icon_rgb_32px_default.png" title="Quarkus"></span>
<span style="float:right">29 Mar 2021<span>

[Jettro Coenradie](https://twitter.com/jettroCoenradie) uses Quarkus to build a low latency Lambda function with an INIT
time of less than 200ms. He shows how to use CDK in combination with Quarkus to deploy to AWS.

[<img src="/github/GitHub-Mark-32px.png">](https://github.com/luminis-ams/aws-cdk-examples/tree/main/aws-quarkus-lambda)
<--->


<--->
{{< /columns >}}