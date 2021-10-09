import boto3
import os
import json

sns = boto3.resource('sns')

"""
This function is an event handler on the S3 PUT event. It calls Amazon Rekognition
service to detect labels for the newly uploaded object. The results are posted to
an SNS_TOPIC.

The functions provides no exception handler, instead it forwards faulty invocation
records to a Lambda destination. To cause such a condition, upload a non-image object
to the source bucket.
"""
def lambda_handler(event, context):

    # fetch the data from the event
    sourceBucket = event['Records'][0]['s3']['bucket']['name']
    sourceKey = event['Records'][0]['s3']['object']['key']

    print("Initiating amazing image rekognition for key {} in bucket {}".format(sourceKey, sourceBucket))

    # Invoke Rekognition API
    rekognition_client = boto3.client('rekognition')
    response = rekognition_client.detect_labels(Image={'S3Object': {'Bucket':sourceBucket, 'Name':sourceKey}}, MaxLabels=3)
        
    # Read the returned labels
    labels = []
    for label in response['Labels']:
        labels.append("{}: {:.2f}".format(label['Name'], label['Confidence']))
        
    result = {
       "Bucket": sourceBucket,
       "Name": sourceKey,
       "Labels": labels
    }    
    
    print(result)
        
    # Publish the results to an SNS topic
    topic = sns.Topic(os.environ['SNS_TOPIC'])
    response = topic.publish(
        Message=json.dumps(result)
    )  

    return result