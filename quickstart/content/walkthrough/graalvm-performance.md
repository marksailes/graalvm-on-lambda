---
title: "Native Image Performance"
description: "Comparing GraalVM native-image performance against Java 11 on AWS Lambda"
date: 2021-06-10T20:58:10+01:00
draft: false
weight: 80
---

Lets compare the performance of GraalVM native-image and Java11 on AWS Lambda.

Quick reminder, the handler isn't doing much at all. This project has minimal functionality
and few dependencies. 

```java
@Override
public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
    context.getLogger().log(event.getRequestContext().getRequestId());

    return APIGatewayV2HTTPResponse.builder()
            .withStatusCode(200)
            .build();
}
```

| Test | Memory | Init | Duration |
|:----------------|:------:|:------:|:-------:|
|   Java 11 (Cold)   |   2048 MB   |   418.41 ms   | 14.71 ms |
|   Java 11 (Warm)   |   2048 MB   |   -   | 1.71 ms |
|   Native Image (Cold)   |   256 MB  |   256.62 ms   | 12.70 ms |
|   Native Image (Warm)   |   256 MB  |   -   | 1.01 ms |

Not only is GraalVM `native-image` faster, it's faster with a vastly less resources. CPU is allocated
proportionally to memory when you use AWS Lambda.