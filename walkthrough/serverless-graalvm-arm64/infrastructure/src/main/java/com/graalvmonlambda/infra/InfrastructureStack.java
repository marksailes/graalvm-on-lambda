package com.graalvmonlambda.infra;

import java.util.Arrays;
import java.util.List;

import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.apigatewayv2.AddRoutesOptions;
import software.amazon.awscdk.services.apigatewayv2.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.HttpApiProps;
import software.amazon.awscdk.services.apigatewayv2.HttpMethod;
import software.amazon.awscdk.services.apigatewayv2.PayloadFormatVersion;
import software.amazon.awscdk.services.apigatewayv2.integrations.LambdaProxyIntegration;
import software.amazon.awscdk.services.apigatewayv2.integrations.LambdaProxyIntegrationProps;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.s3.assets.AssetOptions;

import static java.util.Collections.singletonList;
import static software.amazon.awscdk.core.BundlingOutput.ARCHIVED;

public class InfrastructureStack extends Stack {

    public InfrastructureStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public InfrastructureStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        List<String> functionOnePackagingInstructions = Arrays.asList(
                "-c",
                "cd products " +
                        "&& mvn clean install -P native-image "
                       + "&& cp /asset-input/products/target/function.zip /asset-output/"
        );

        BundlingOptions.Builder builderOptions = BundlingOptions.builder()
                .command(functionOnePackagingInstructions)
                .image(DockerImage.fromRegistry("marksailes/arm64-al2-graalvm:al2-21.2.0"))
                .volumes(singletonList(
                        DockerVolume.builder()
                                .hostPath(System.getProperty("user.home") + "/.m2/")
                                .containerPath("/root/.m2/")
                                .build()
                ))
                .user("root")
                .outputType(ARCHIVED);

        Function productFunction = new Function(this, "ProductFunction", FunctionProps.builder()
                .runtime(Runtime.PROVIDED_AL2)
                .code(Code.fromAsset("../software/", AssetOptions.builder()
                        .bundling(builderOptions.build())
                        .build()))
                .handler("com.graalvmonlambda.product.ProductRequestHandler")
                .memorySize(256)
                .logRetention(RetentionDays.ONE_WEEK)
                .architectures(singletonList(Architecture.ARM_64))
                .build());

        HttpApi httpApi = new HttpApi(this, "GraalVMOnLambdaAPI", HttpApiProps.builder()
                .apiName("GraalVMonLambdaAPI")
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/product")
                .methods(singletonList(HttpMethod.GET))
                .integration(new LambdaProxyIntegration(LambdaProxyIntegrationProps.builder()
                        .handler(productFunction)
                        .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                        .build()))
                .build());


        Function productFunctionJvm = new Function(this, "ProductFunctionJVM", FunctionProps.builder()
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../software/products/target/product.jar"))
                .handler("com.graalvmonlambda.product.ProductRequestHandler")
                .memorySize(2048)
                .logRetention(RetentionDays.ONE_WEEK)
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/product-jvm")
                .methods(singletonList(HttpMethod.GET))
                .integration(new LambdaProxyIntegration(LambdaProxyIntegrationProps.builder()
                        .handler(productFunctionJvm)
                        .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                        .build()))
                .build());

        CfnOutput apiUrl = new CfnOutput(this, "ProductApiUrl", CfnOutputProps.builder()
                .exportName("ProductApiUrl")
                .value(httpApi.getApiEndpoint())
                .build());
    }
}
