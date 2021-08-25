import boto3
import json
import os
import base64
import gzip

sns = boto3.resource('sns')

"""
This function is a destination for the CloudWatch event log subscription filter.
It receives an event in case if the filter has selected an error message from the
application log.
"""
def lambda_handler(event, context):
    
    # fetch the Base64 and compressed payload data from the event
    cw_data = event['awslogs']['data']
    # decode the payload
    compressed_payload = base64.b64decode(cw_data)
    # uncompress and extract the payload
    uncompressed_payload = gzip.decompress(compressed_payload)
    payload = json.loads(uncompressed_payload)    
    
    # publish the application error notification to an SNS topic
    topic = sns.Topic(os.environ['SNS_TOPIC'])
    log_events = payload['logEvents']
    for log_event in log_events:
        response = topic.publish(
            Message='An error has occured in the ProductCatalogUI application. Please check the application logs: ' + os.environ['CW_LOG_INSIGHTS_QUERY_LINK'],
            MessageAttributes={
                "error_notification": {
                    "DataType": "String",
                    "StringValue": "true"
                }
            }    
        )