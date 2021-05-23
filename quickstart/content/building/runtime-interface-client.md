---
title: "Selecting a Runtime Interface Client"
description: "Selecting the best Runtime Inferface Client (RIC) for your application"
date: 2021-04-29T21:21:46+01:00
draft: false
weight: 10
---

Your GraalVM function will run on a [custom runtime](https://docs.aws.amazon.com/lambda/latest/dg/runtimes-custom.html). Which means your function will have to meet the requirements of
the [AWS Lambda Runtime API](https://docs.aws.amazon.com/lambda/latest/dg/runtimes-api.html). Although you can write 
your own implementation of this API, a number of open source alternatives already exists. Including the open source AWS project
[aws-lambda-java-runtime-interface-client](https://github.com/aws/aws-lambda-java-libs/tree/master/aws-lambda-java-runtime-interface-client).

This implementation is used in the managed Java runtimes and therefore is known to be battle hardened, having support
for all the features and highly optimized for performance.

When deciding which implementation to use, make sure that the events you want to integrate with are supported. Most
common are clients which can only handle String or Amazon API Gateway events.

<img src="runtime-interface-client.png">