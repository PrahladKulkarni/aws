A Java-based implementation of the Product Catalog web application.
===================================================================

* README.md - this file
* .githooks - contains the Git hooks for the team to share 
* appspec.yml - this file is used by AWS CodeDeploy when deploying the web
  application to EC2
* buildspec.yml - this file is used by AWS CodeBuild to build the web
  application
* pom.xml - Maven Project Object Model for the web application
* src/main - contains Java service source files
* src/test - contains Java service unit test files
* scripts/ - contains scripts used by AWS CodeDeploy when
  installing and deploying the application on the Amazon EC2 instance