import json
import logging
from botocore.vendored import requests

logger = logging.getLogger()
logger.setLevel(logging.INFO)

##
## This Lambda function is an implementation of a Custom Resource
## for AWS CloudFormation. It generates a name for a DynamoDB table
## by appending a string to a provided prefix.
##
def lambda_handler(event, context):
    logger.info("Event received: " + json.dumps(event))   
    
    # Extract CloudFormation message from the SNS event
    message = json.loads(event["Records"][0]["Sns"]["Message"])
    logger.info("Message extracted: " + json.dumps(message))   

    # Create response based on the request data
    response = {}
    response["Status"] = "SUCCESS"
    response["PhysicalResourceId"] = context.invoked_function_arn
    response["StackId"] = message["StackId"]
    response["RequestId"] = message["RequestId"]
    response["LogicalResourceId"] = message["LogicalResourceId"]
    response["Data"] = {}
    tableNamePrefix=message["ResourceProperties"]["TableNamePrefix"]
    
    # Generate table name based on the prefix
    response["Data"]["tableName"] = tableNamePrefix + "DemoTable"
    
    logger.info("Response created: " + json.dumps(response))
    
    # Use provided pre-signed URL to upload response to s3 busket 
    responseURL = message["ResponseURL"]
    
    # Important: set headers to None
    logger.info(requests.put(responseURL, data=json.dumps(response), headers=None).text)