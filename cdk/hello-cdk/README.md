# Welcome to your CDK Java project!

This is an expanded demo based upon an AWS CDK workshop available here: https://cdkworkshop.com/. Follow the steps described in the workshop to set up your dev environment.

Explore the contents of this project. It demonstrates a CDK app with an instance of the AWS CloudFormation stack, which contains a number of Amazon SQS queues that are subscribed to an Amazon SNS topic.

The name of the stack, the number of Amazon SQS queues to be created and the queue name prefix are provided to the application at the runtime via the named properties in the file `cdk.json`. This file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

## To deploy the stack
* Set the desired values of the named stack properties in the file cdk.json.
* `mvn package`
* `cdk deploy`      enter Y when prompted

## To see the application-level parameter validation in action
* Update the value of the named property totalQueues in the file cdk.json, to 15.
* `cdk deploy`
* Observe `java.lang.RuntimeException: The requested number of queues (15) exceeds the maximum allowed amount of 10.`

## To create and deploy the changeset
* Modify the values of the named stack properties in the file cdk.json.
* `cdk diff`        to see the changes to be performed by the changeset  
* `cdk deploy`      enter Y when prompted

## To delete the stack
* `cdk destroy`

## Useful commands
 * `mvn package`    compile and run tests
 * `cdk ls`         list all stacks in the app
 * `cdk synth`      emits the synthesized CloudFormation template
 * `cdk deploy`     deploy this stack to your default AWS account/region
 * `cdk diff`       compare deployed stack with current state
 * `cdk docs`       open CDK documentation

Enjoy!