A slightly modified version of the web page and the scripts from the AWS Security Blog "How to add authentication to a single-page web application with Amazon Cognito OAuth2 implementation": https://aws.amazon.com/blogs/security/how-to-add-authentication-single-page-web-application-with-amazon-cognito-oauth2-implementation/. 

The added functionality allows submitting an Id token to an Api Gateway API endpoint secured with a Cognito User Pool Authorizer. The data received from the APi will be presented on the page.

Follow the exact same steps spelled out in the blog, but use the content of this directory to upload to S3 bucket instead. Use your own Cognito User Pool data to initialize variables on lines 5-9 in the userprofile.js script.

Follow these steps to add social sign-in to your user pool: https://docs.aws.amazon.com/cognito/latest/developerguide/cognito-user-pools-configuring-federation-with-social-idp.html