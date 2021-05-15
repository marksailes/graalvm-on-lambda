---
title: "How It Works"
date: 2021-05-08T12:30:39+01:00
draft: false
weight: 30
description: How GraalVM works on AWS Lambda
---

# Lambda with a Managed Java Runtime

For example the managed Java runtimes (Java 8, Java 8 Corretto and Java 11) you supply your code as a .zip or a .jar. 
AWS provisions a FireCracker MicroVM, downloads your code to it, starts the JVM and loads your code. On your
behalf an AWS managed implementation of the [Runtime API](https://docs.aws.amazon.com/lambda/latest/dg/runtimes-api.html)
is asking Lambda for the next event to process.

<img src="/summary/java-runtime.png" alt="demo" class="img-responsive">

# Using GraalVM with a Provided Runtime

When using a GraalVM native image you no longer want or need the managed Java runtimes. Instead you want the ability to
directly decision what is executed. This functionality is enabled by the `provided` 

<img src="/summary/managed-runtime.png" alt="demo" class="img-responsive">
