---
date: 2021-04-29T21:21:46+01:00
draft: false
weight: 30
---

{{< button href="https://github.com/marksailes/graalvm-on-lambda/issues/new?assignees=marksailes&labels=&template=new-guide-submission.md&title=New+guide+suggestion" >}}Submit an example{{< /button >}}

{{< columns >}}
[<img src="/guides/aws-quarkus-demo.png" alt="demo" class="img-responsive">](https://github.com/aws-samples/aws-quarkus-demo/)

<span><img src="/aws/Arch_App-Integration/Arch_32/Arch_Amazon-API-Gateway_32.svg" title="Amazon API Gateway"></span>
<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>
<span><img src="/aws/Arch_Database/32/Arch_Amazon-DynamoDB_32.svg" title="Amazon DynamoDB"></span>

A sample application demonstrating API Gateway -> Lambda -> DynamoDB using the Quarkus Framework.

<--->
[<img src="/guides/kabisa-tech-blog.png" alt="api gateway authorizer" class="img-responsive">](https://www.kabisa.nl/tech/beat-java-cold-starts-in-aws-lambdas-with-graalvm/)

<span><img src="/aws/Arch_App-Integration/Arch_32/Arch_Amazon-API-Gateway_32.svg" title="Amazon API Gateway"></span>
<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>

A practical walk through of building an API Gateway Authorizer, including the full [source code](https://github.com/VR4J/aws-enriching-lambda-authorizer).

<--->
[<img src="/guides/cloudway-gramba.png" alt="graalvm benchmark" class="img-responsive">](https://www.cloudway.be/blog/gramba-graalvm-native-image-runtime-and-toolchain-aws-lambda)

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

[Arnold Galovics](https://twitter.com/ArnoldGalovics) 

[GitHub Repo](https://github.com/redskap/aws-lambda-java-runtime)

<--->
[<img src="/usage/examples/fast-cold-starts-for-clojure.png" alt="Fast Cold Starts for Clojure in AWS Lambda using GraalVM native-image" class="img-responsive">](https://nitor.com/en/articles/fast-cold-starts-for-clojure-in-aws-lambda-using-graalvm-native-image)

<span><img src="/aws/Arch_Compute/32/Arch_AWS-Lambda_32.svg" title="AWS Lambda"></span>
<span><img src="/icons/32px-Clojure_logo.svg.png" title="Clojure"></span>
<span><img src="/icons/terraform-icon.png" title="Terraform" width="32px"></span>

[Esko Luontola](https://twitter.com/EskoLuontola) takes us step by step how he packages his native images into a container
images and runs it on AWS Lambda. His project uses the [AWS Runtime Interface Client](https://github.com/aws/aws-lambda-java-libs/tree/master/aws-lambda-java-runtime-interface-client).
All his project code is available on Github.

[GitHub Repo](https://github.com/luontola/native-clojure-lambda)

{{< /columns >}}