package com.aws.vokunev.cdk;

import software.amazon.awscdk.core.App;

/**
 * This application uses the following named properties (e.g. -DstackName=DemoStack):
 * <ul>
 * <li>stackName - name of the AWS CloudFormation stack to be created
 * <li>totalQueues - number of Amazon SQS queues to be created   
 * <li>namePrefix - prefix for the Amazon SQS queue name
 * </ul>
 * If not provided, the values of "MyStack", 1 and "MyQueue" will be used respectively.
 */
public final class HelloCdkApp {
    public static void main(final String[] args) {

        // Read the values of the named properties
        String stackName = System.getProperty( "stackName", "MyStack" );
        String totalQueues = System.getProperty( "totalQueues", "1" );
        String namePrefix = System.getProperty( "namePrefix", "MyQueue" );

        App app = new App();

        new HelloCdkStack(app, stackName, Integer.parseInt(totalQueues), namePrefix);

        // Synthesize a cloud assembly for this app
        app.synth();
    }
}
