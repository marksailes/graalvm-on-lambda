---
title: "Convert the Lambda function to GraalVM native-image"
description: "How to convert Lambda functions to GraalVM native-image"
date: 2021-05-27T22:15:40+01:00
draft: false
weight: 40
---

There are a series of changes that will need to be made in order for this project to produce a 
suitable native binary which can be run on AWS Lambda.

### Prerequisites

- Install [GraalVM](https://www.graalvm.org/downloads/) 
- Install [native-image](https://www.graalvm.org/reference-manual/native-image/#install-native-image) tool.

### Add the GraalVM native image build plugin.

You can use a maven profile to toggle which type of build you want. This means you can continue to
iterate quickly with the standard build and only build the slower native image when necessary.

```xml
<profiles>
    <profile>
        <id>native-image</id>
        <build>
            <plugins>
                <plugin>
                    <groupId>org.graalvm.nativeimage</groupId>
                    <artifactId>native-image-maven-plugin</artifactId>
                    <version>21.1.0</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>native-image</goal>
                            </goals>
                        </execution>
                    </executions>
                    <configuration>
                        <imageName>product-binary</imageName>
                        <mainClass>com.amazonaws.services.lambda.runtime.api.client.AWSLambda</mainClass>
                        <buildArgs combine.children="append">
                            --verbose
                            --no-fallback
                            --initialize-at-build-time=org.slf4j
                            --enable-url-protocols=http
                            -H:+AllowIncompleteClasspath
                        </buildArgs>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

Maven uses the `-P` flag to activate a profile.

```bash
mvn clean install -P native-image
```

Within the configuration the main class is set to `AWSLambda`. This class is the implementation of the
runtime interface client. A reference to our handler will be passed as an argument to it. So that 
it knows which class we want it to run.

```xml
<mainClass>com.amazonaws.services.lambda.runtime.api.client.AWSLambda</mainClass>
```

### Meet the requirements of the Lambda custom runtime

- Add the [AWS Java runtime interface client](https://github.com/aws/aws-lambda-java-libs/tree/master/aws-lambda-java-runtime-interface-client) dependency
- Add the bootstrap file
- Package it all up into a zip file

```xml
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-lambda-java-runtime-interface-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

I keep the bootstrap file in `src/main/config` there might be a more correct place.

```bash
└── products
    └── src
        └── main
            └── config
                └── bootstrap
```

The contents is very simple. Execute my binary passing my handler as the only argument.

```bash
#!/usr/bin/env bash

./product-binary com.graalvmonlambda.product.ProductRequestHandler
```

Finally use the `maven-assembly-plugin` to create a zip file including both the native binary
and the bootstrap. Add the plugin definition to the `native-image` profile.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.3.0</version>
    <executions>
        <execution>
            <id>zip-assembly</id>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
            <configuration>
                <finalName>function</finalName>
                <descriptors>
                    <descriptor>src/assembly/zip.xml</descriptor>
                </descriptors>
                <attach>false</attach>
                <appendAssemblyId>false</appendAssemblyId>
            </configuration>
        </execution>
    </executions>
</plugin>
```

The assembly descriptor is this.

```xml
<assembly xmlns="http://maven.apache.org/ASSEMBLY/2.1.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/ASSEMBLY/2.1.0 http://maven.apache.org/xsd/assembly-2.1.0.xsd">
    <id>lambda-package</id>
    <formats>
        <format>zip</format>
    </formats>
    <includeBaseDirectory>false</includeBaseDirectory>
    <files>
        <file>
            <source>${project.build.directory}${file.separator}${artifactId}</source>
            <outputDirectory>${file.separator}</outputDirectory>
            <destName>product-binary</destName>
            <fileMode>777</fileMode>
        </file>
        <file>
            <source>src/main/config/bootstrap</source>
            <outputDirectory>${file.separator}</outputDirectory>
            <destName>bootstrap</destName>
            <fileMode>777</fileMode>
        </file>
    </files>
</assembly>
```