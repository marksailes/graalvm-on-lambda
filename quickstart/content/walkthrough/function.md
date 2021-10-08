---
title: "Initial Lambda handler function"
description: "Simple Lambda handler function to respond to an API Gateway event"
date: 2021-05-24T22:21:27+01:00
draft: false
weight: 20
---

Lets start with a simple `pom.xml`

[pom.xml](https://github.com/marksailes/graalvm-on-lambda/blob/main/walkthrough/serverless-graalvm/software/products/pom.xml)

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.graalvmonlambda</groupId>
    <artifactId>products</artifactId>
    <version>1.0</version>
    <packaging>jar</packaging>
    <name>Products</name>
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-lambda-java-core</artifactId>
            <version>1.2.1</version>
        </dependency>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-lambda-java-events</artifactId>
            <version>3.8.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version>
                <configuration>
                    <createDependencyReducedPom>false</createDependencyReducedPom>
                    <finalName>function</finalName>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

And a simple `RequestHandler` which recieves events from Amazon API Gateway.

[ProductRequestHandler.java](https://github.com/marksailes/graalvm-on-lambda/blob/main/walkthrough/serverless-graalvm/software/products/src/main/java/com/graalvmonlambda/product/ProductRequestHandler.java)

```java
package com.graalvmonlambda.product;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

public class ProductRequestHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(200)
                .build();
    }
}
```

Giving us a project looking something like this.

```
serverless-graalvm/
├── infrastructure
└── software
    └── products
        ├── pom.xml
        └── src
            └── main
                └── java
                    └── com
                        └── graalvmonlambda
                            └── product
                                └── ProductRequestHandler.java
```


Let's get this deployed and working before we go any further.