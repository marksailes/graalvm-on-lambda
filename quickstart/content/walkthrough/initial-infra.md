---
title: "Infrastucture as Code (CDK)"
date: 2021-05-24T22:21:27+01:00
draft: false
weight: 30
---

Let's create the code required to deploy our initial Lambda function. Firstly you'll need to install the [AWS Cloud 
Development Kit (CDK)](https://docs.aws.amazon.com/cdk/latest/guide/work-with.html)

Now initialize a new CDK project called app.

```bash
cd infrastructure
cdk init app --language java
```

The initial project will look like this.

```
├── README.md
├── cdk.json
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── myorg
    │               ├── InfrastructureApp.java
    │               └── InfrastructureStack.java
    └── test
        └── java
            └── com
                └── myorg
                    └── InfrastructureTest.java

```

We'll rename the packages.