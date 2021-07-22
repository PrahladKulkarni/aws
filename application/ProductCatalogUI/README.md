A Java-based implementation of the Product Catalog web application.
===================================================================
This purpose of this code is to provide a reference implementation of an AWS-hosted application and demonstrate some best DevOps practices. The implementation and the release process of this application are aligned with The Twelve-Factor App methodology (https://12factor.net/).    

This application implements a presentation layer for the data provided by the external microservices. The application obtains the endpoints of these microservices from AWS AppConfig service. The reference to an AWS AppConfig configuration is specific to a target deployment environment and is injected into the release.properties file during the application release build.

The application is implemented with the SpringBoot framework (https://spring.io/projects/spring-boot) and uses the Thymeleaf engine (https://www.thymeleaf.org/) for the HTML templating.

The references to the deployed application as well as the infrastructure diagram can be found at: https://www.cloud101.link/

* README.md - this file
* appspec.yml - this file is used by AWS CodeDeploy when deploying this application to EC2
* buildspec-build.yml - this file is used by AWS CodeBuild to perform the build of this application
* buildspec-release.yml - this file is used by AWS CodeBuild to build the environment-specific release of this application 
* pom.xml - Maven Project Object Model for this application
* src/main - contains Java source files as well as other application resources
* src/test - contains Java source files for the unit and the integration testing
* src/main/resources/release.properties - contains a reference to AWS AppConfig configuration used by this application. This file is initialized during the release build stage by the scripts/configure_release script
* scripts/ - contains various scripts used during the application build, release and deployment stages
* scripts/githooks - contains the Git hooks for the team to share. Run the following comand to make git recognize it as a hooks directory:
```
$ git config --local core.hooksPath scripts/githooks/
```