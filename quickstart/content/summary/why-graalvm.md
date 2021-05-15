---
title: "Why Graalvm"
date: 2021-05-08T12:19:54+01:00
draft: false
weight: 20
title: GraalVM on Lambda
description: Why should you use GraalVM
---

Java is an enormously popular programming language for business applications. These applications have typically run on
large application servers of fixed capacity. They are booted up, left on and scaled for expected peak capacity. The only
time they are restarted is when a new release is released. If scaling of capacity is done, then it is done in large 
steps well a head of time to cater for the time required to provision a new server.

Moving this style of application to AWS Lambda is difficult as start up time has never been an issue before and suddenly
it's very important. These applications typically make use of runtime dependency injection which slows down time to first 
request.

GraalVM and specifically GraalVM Native Image is a project which aims to match the performance of native languages and 
reduce the start up time for JVM-based languages by compiling them ahead-of-time.

These goals align perfectly with Lambda which benefits greatly from fast start up times.