package com.aws.vokunev.cdk;

import software.amazon.awscdk.core.App;

public final class HelloCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        new HelloCdkStack(app, "HelloCdkStack", 5, "MyDemoQueue");

        app.synth();
    }
}
