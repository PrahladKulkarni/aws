#
# # s3 Image Rekognition Microservice
#

from __future__ import print_function
import boto3

def handler(event, context):

    print('Initiating image rekognition')

    bucket = event['ourBucket']
    key = event['ourKey']

    client = boto3.client('rekognition')

    response = client.detect_labels(Image={'S3Object': {'Bucket':bucket, 'Name':key}},
        MaxLabels=10,
        MinConfidence=75)

    print('Detected the following labels for ' + key)
    for label in response['Labels']:
        print(label['Name'] + ' : ' + str(label['Confidence']))
        if label['Name'] == "Human":
            found = 'human'
            break
        else:
            found = 'other'

    discovery = {
        "found": found,
        }

    return discovery