import boto3
import os
import json

sfnClient = boto3.client('stepfunctions')

#
# This function handles the AWS StepFunctions task processing for the ACTIVITY_ARN.
# It unconditionally approves all price inscreases.
# The price decreases are approved if the product sales amount is equal or less than the LOW_SALES_AMOUNT only, which indicates the low popularity of this item.
#
def lambda_handler(event, context):
    
    print('Long polling a task for the activity {}, time left {} ms'.format(os.environ['ACTIVITY_ARN'], context.get_remaining_time_in_millis()))
    taskDetails = sfnClient.get_activity_task(
        activityArn=os.environ['ACTIVITY_ARN'],
        workerName='Greedy Approver'
    )
    
    print(taskDetails)
    
    # Check if there is anything to process
    taskToken = taskDetails ['taskToken']
    if not taskToken:
        print('No task is available')
        return None

    input = json.loads(taskDetails['input'])
    input = input['data']

    print (input)

    # Fetch the input data for the decision-making 
    productId = int(input['productId'])
    price = float(input['price'])
    currentPrice = float(input['computed'][0]['currentPrice'])
    itemsSold = int(input['computed'][1]['itemsSold'])

    # Prepare the result data to send back to the task 
    result = priceChangeApprove(productId, price, currentPrice, itemsSold)

    print(result)
   
   # Send result to the task
    response = sfnClient.send_task_success(
        taskToken=taskToken,
        output=json.dumps(result)
    )

    return None

#
# This is the core logic. We keep is separate from the Lambda handler
# for the ease of unit testing and for the reusability.
#
def priceChangeApprove(productId, price, currentPrice, itemsSold):
    if price == currentPrice:
        return {
            "approved": False,
            "message": 'Price change for the product {} denied, as the price matches the current price of {}.'.format(productId, currentPrice)
        }
    elif price > currentPrice:
       return {
            "approved": True,
            "message": 'Price increase from {} to {} for the product {} approved.'.format(currentPrice, price, productId)
        }  
    elif itemsSold <= int(os.environ['LOW_SALES_AMOUNT']):
        return {
            "approved": True,
            "message": 'Price decrease from {} to {} for the product {} approved due to the LOW popularity of the item.'.format(currentPrice, price, productId)
        }
    else:
       return {
            "approved": False,
            "message": 'Price decrease from {} to {} for the product {} denied due to the HIGH popularity of the item.'.format(currentPrice, price, productId)
        }          
