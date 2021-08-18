# Welcome to your CDK Java project!

This is an expanded demo based upon an AWS CDK workshop available here: https://cdkworkshop.com/. Follow the steps described in the workshop to set up your dev environment.

Explore the contents of this project. It demonstrates a CDK app with an instance of a stack (`HelloCdkStack`), which contains a number of Amazon SQS queues that are subscribed to an Amazon SNS topic.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

## To deploy the stack
* `mvn compile`
* `cdk synth`
* `cdk deploy`      enter Y when prompted

## To see the application-level parameter validation in action
* Update the number of queues parameter value in HelloCdkApp to 15.
* `mvn compile`
* `cdk synth`
* Observe `java.lang.RuntimeException: The requested number of queues (15) exceeds the maximum allowed amount of 10.`

## To create and deploy the changeset
* Update the number of queues parameter value in HelloCdkApp file to a number below or equal 10.
* `mvn compile`
* `cdk synth`
* `cdk diff`        to see the additional resource that will be created in the changeset  
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