import boto3
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

"""
This function is an event handler on the S3 PUT event. It calls Amazon Rekognition
service to detect labels for the newly uploaded object.

The functions provides no exception handler, instead it forwards faulty invocation
records to a Lambda destination. To cause such a condition, upload a non-image object
to the source bucket.
"""
def lambda_handler(event, context):

    # fetch the data from the event
    sourceBucket = event['Records'][0]['s3']['bucket']['name']
    sourceKey = event['Records'][0]['s3']['object']['key']

    logger.info("Initiating image rekognition for key {} in bucket {}".format(sourceKey, sourceBucket))

    client = boto3.client('rekognition')
    response = client.detect_labels(Image={'S3Object': {'Bucket':sourceBucket, 'Name':sourceKey}}, MaxLabels=3)
        
    print('Detected the following labels for ' + sourceKey)
    result = []
    for label in response['Labels']:
        result.append("{}: {:.2f}".format(label['Name'], label['Confidence']))
        
    logger.info(result)    

    return result