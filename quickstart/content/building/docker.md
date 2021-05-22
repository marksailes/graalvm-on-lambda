---
title: "Building a GraalVM native binary for Amazon Linux 2 with Docker"
description: "How to use Docker to build a GraalVM native binary for Amazon Linux 2"
date: 2021-04-29T21:21:46+01:00
draft: false
weight: 10
---

When using GraalVM native-image it's easy to forget that the native binary produced needs to be built for a specific OS
and architecture. As Java developers we're spoilt by the build once and run anywhere mindset.

The easiest way to build a native binary for AWS Lambda is to use Docker. I've pushed my image to docker hub for others
to use.

[<img src="/docker/docker.png" alt="docker hub" class="img-responsive">](https://hub.docker.com/r/marksailes/al2-graalvm) [marksailes/al2-graalvm](https://hub.docker.com/r/marksailes/al2-graalvm)

If you would like to contribute to this effort / make improvements, please open an issue or pull request.

[<img src="/github/GitHub-Mark-32px.png">](https://github.com/marksailes/al2-graalvm) [marksailes/al2-graalvm](https://github.com/marksailes/al2-graalvm)

