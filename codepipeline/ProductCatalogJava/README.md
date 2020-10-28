A Java-based implementation of the Product Catalog web application.
===================================================================

* README.md - this file
* appspec.yml - this file is used by AWS CodeDeploy when deploying this application to EC2
* buildspec.yml - this file is used by AWS CodeBuild to build this application
* pom.xml - Maven Project Object Model for the web application
* src/main - contains Java service source files
* src/test - contains Java service unit test files
* src/main/java/endpoints.properties - contains URLs of HTTP API service endpoints. This file is initialized during the release stage by the scripts/configure_release script
* scripts/ - contains scripts used by AWS CodeDeploy when installing and deploying this application to EC2 instance
* scripts/githooks - contains the Git hooks for the team to share. Run the following comand to make git recognize it as a hooks directory:
```
$ git config --local core.hooksPath scripts/githooks/
```
