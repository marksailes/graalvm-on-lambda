---
title: "GraalVM Config"
description: "Understanding what GraalVM config is required for your application"
date: 2021-06-07T21:15:51+01:00
draft: false
weight: 60
---

Curious people will have noticed that my example project has a number of additional `.json` and `.properties` files
in the `src/main/resouces` folder.

[src/main/resouces](https://github.com/marksailes/graalvm-on-lambda/tree/main/walkthrough/serverless-graalvm/software/products/src/main/resources)

```bash
└── software
    └── products
        └── src
            └── main
                └── resources
                    └── META-INF
                        └── native-image
                            ├── com.amazonaws
                            │   ├── aws-lambda-java-core
                            │   │   ├── native-image.properties
                            │   │   └── reflect-config.json
                            │   ├── aws-lambda-java-events
                            │   │   ├── native-image.properties
                            │   │   └── reflect-config.json
                            │   ├── aws-lambda-java-runtime-interface-client
                            │   │   ├── jni-config.json
                            │   │   ├── native-image.properties
                            │   │   ├── native-image.properties
                            │   │   ├── reflect-config.json
                            │   │   └── resource-config.json
                            │   └── aws-lambda-java-serialization
                            │       ├── native-image.properties
                            │       └── reflect-config.json
                            ├── native-image.properties
                            └── reflect-config.json

```

In my opinion, these are the biggest problem with using GraalVM `native-image` today. 

Let me explain.

The super power of `native-image` is that it produces a very low memory footprint native binary
of your application. It does this by statically analysing your code base and creating its own
model of what is and what is not required at runtime. Anything which isn't required is not included
in the final native binary. This isn't just whole classes, it can be individual methods and / or 
constructors.

This is great until your application includes dependencies which use features like reflection. This
dynamic loading at runtime means that `native-image` has no way of knowing what classes may be required.

{{< hint info >}}
Remember GraalVM purposely doesn't support reflection in order to gain performance improvements.
{{< /hint >}}

In my example application I'm using 4 different dependencies which require reflection.

```xml
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
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-lambda-java-runtime-interface-client</artifactId>
    <version>1.1.0</version>
</dependency>
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-lambda-java-serialization</artifactId>
    <version>1.0.0</version>
</dependency>
```

Ideally library owners will include these GraalVM config files within their dependencies so that user won't need to 
worry about them. But we're not quite at the adoption level where that is happening regularly. The 
[aws-sdk-java-v2](https://aws.amazon.com/blogs/developer/graalvm-native-image-support-in-the-aws-sdk-for-java-2-x/)
for example, does support GraalVM as of v2.16.1.
 
I would suggest that everyone creates GitHub issues against libraries which require additional GraalVM config. The 
interest from users is often a deciding factor.

I'm currently working on pull requests for the libraries listed above.

So, if those libraries had the correct config included, would we the application developer need to add anything?

Yes, we need to tell `native-image` that our handler is required.

[reflect-config.json](https://github.com/marksailes/graalvm-on-lambda/blob/main/walkthrough/serverless-graalvm/software/products/src/main/resources/META-INF/native-image/com.graalvmonlambda/products/reflect-config.json)

```json
[
  {
    "name": "com.graalvmonlambda.product.ProductRequestHandler",
    "allDeclaredConstructors": true,
    "allPublicConstructors": true,
    "allDeclaredMethods": true,
    "allPublicMethods": true,
    "allDeclaredClasses": true,
    "allPublicClasses": true
  }
]
```
