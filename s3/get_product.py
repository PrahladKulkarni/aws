import boto3
import os
import json
import decimal
from botocore.client import Config

dynamodb = boto3.resource('dynamodb')

"""
Set the addressing_style to 'path' when using s3v4 signature version, 
see https://github.com/boto/boto3/issues/1644#issuecomment-451611263 for details.
"""
s3_client = boto3.client('s3', config=Config(s3={'addressing_style': 'path'}, signature_version='s3v4'))

# This function uses resource API and performs proper error handling 
def lambda_handler(event, context):
    
    print("Event received:" + json.dumps(event))

    # Validate input
    if 'productId' not in event:
        raise ValueError("[BadRequest] Validation error: Missing required parameter [productId]")
    if 'environment' not in event:
        raise ValueError("[BadRequest] Validation error: Missing required parameter [environment]")        
        
    # Use the environment-specific table name
    table = dynamodb.Table(os.environ[event['environment']])
    
    print("Querying table {} for productId={}".format(table.table_name, event['productId']))
    
    try:
        # Convert input data to numeric
        productId = int(event['productId'])
        
        if productId == -1:
            # Special case, return a first available item
            result = table.scan(
                Limit=1
            )

            item = result['Items'][0]
            
        else:
            # Regular case, retrieve an item by key
            result = table.get_item(
                Key={
                    'Id': productId
                }
            )
        
            # Item was not found
            if 'Item' not in result:
                raise ValueError("[BadRequest] A product with Id={} does not exist.".format(productId))   
        
            item = result['Item']

    except Exception as ex:
        print(ex)
        raise ValueError("[BadRequest] Unable to complete request. Cause: {}".format(ex))    
        
    # If image is provided, pre-sign the url
    if 'Image' in item:
        presigned_url = s3_client.generate_presigned_url(
            ClientMethod='get_object',
            Params= {
                'Bucket': item['Bucket'],
                'Key': item['Image']
            }, 
            ExpiresIn=os.environ['EXPIRATION_SEC']
        )       
            
        item['Image'] = presigned_url
        
    print(item)

    return item
    
# A decimal type encoder for json.dumps()
class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            if "." not in str(o): 
                return int(o)
            else:    
                return float(o)
        return super(DecimalEncoder, self).default(o)    