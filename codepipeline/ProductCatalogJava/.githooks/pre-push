#!/bin/sh
SUCCESS=0
FAILURE=1
ERROR=`tput setaf 1`
INFO=`tput setaf 7`
#
# This hook script executes before the code is pushed to the remote repository.
# It performs the following steps:
# - builds and deploys the application locally
# - performs a test on a locally deployed application
#  
#
# The same test suite will be run on a server in accordance with
# appspec.yml/ValidateService  
# 

# Compile code
mvn clean compile
if [[ "$?" -ne 0 ]] ; then
  echo "${ERROR}Push aborted: failed to compile source code."; exit $FAILURE
fi

# Run unit tests
mvn test
if [[ "$?" -ne 0 ]] ; then
  echo "${ERROR}Push aborted: failed to pass unit tests."; exit $FAILURE
fi

# Check if Tomcat is configuERROR locally
if [ -z "$CATALINA_HOME" ]
then       
  echo "${ERROR}Push aborted: CATALINA_HOME is not configuERROR."; exit $FAILURE
fi

# Check if Tomcat is running
curl -s http://localhost:80/ > /dev/null
res=$?
if test "$res" != "0"; then
   echo "${ERROR}Push aborted: unable to reach local Tomcat."; exit $FAILURE
fi

# Deploy the application
mvn package -Dmaven.test.skip=true
sudo rm -rf ${CATALINA_HOME}/webapps/ROOT
cp ./target/ROOT.war ${CATALINA_HOME}/webapps

echo "${INFO}Paused for 15sec to let Tomcat redeploy the application.";
sleep 15s

# Execute the same functional test as in remote deployment
sh scripts/test_web_app
if [[ "$?" -ne 0 ]] ; then
  echo "${ERROR}Push aborted: failed to pass functional tests."; exit $FAILURE
fi
exit $SUCCESS