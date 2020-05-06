import boto3
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

"""
This function is an event handler on the S3 PUT event.
It calls Amazon Rekognition service to detect labels for the newly uploaded object.   
"""
def lambda_handler(event, context):

    sourceBucket = event['Records'][0]['s3']['bucket']['name']
    sourceKey = event['Records'][0]['s3']['object']['key']

    logger.info("Initiating image rekognition for key {} in bucket {}".format(sourceKey, sourceBucket))

    client = boto3.client('rekognition')

    response = client.detect_labels(Image={'S3Object': {'Bucket':sourceBucket, 'Name':sourceKey}},
        MaxLabels=3)

    result = []
    print('Detected the following labels for ' + sourceKey)
    for label in response['Labels']:
        result.append(label['Name'] + ' : ' + str(label['Confidence']))
    
    logger.info(result)

    return result