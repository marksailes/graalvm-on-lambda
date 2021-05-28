---
title: "Project Structure"
description: "Initial project structure for Java Lambda functions and CDK IaC"
date: 2021-05-24T22:21:27+01:00
draft: false
weight: 10
---


We'll be using my favoured project layout which is to create one folder for our function source code, and a separate
folder for our infrastructure source code.

```bash
mkdir serverless-graalvm
mkdir serverless-graalvm/software
mkdir serverless-graalvm/infrastructure
```

```
serverless-graalvm/
├── infrastructure       # This will contain our CDK Stacks
└── software             # This will contain all our Lambda functions
```