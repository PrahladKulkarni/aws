import boto3
import os
import json

#
# This function handles task processing for the ACTIVITY_ARN.
# It approves all price inscreases.
# The price decreases are approved if the product stock is greater than LOW_STOCK only. 
#
def lambda_handler(event, context):
    
    sfnClient = boto3.client('stepfunctions')

    print('Long polling a task for the activity {}, time left {} ms'.format(os.environ['ACTIVITY_ARN'], context.get_remaining_time_in_millis()))
    taskDetails = sfnClient.get_activity_task(
        activityArn=os.environ['ACTIVITY_ARN'],
        workerName='Greedy Approver'
    )
    
    # Check if there is anything to process
    taskToken = taskDetails ['taskToken']
    if not taskToken:
        print('No task is available')
        return None
 
    input = json.loads(taskDetails['input'])
    print (input)    

    # Fetch the input data for the decision-making 
    productId = int(input['productId'])
    currentPrice = float(input['currentPrice'])
    newPrice = float(input['newPrice'])
    stock = int(input['stock'])
    
    # Prepare the result data to send back to the task 
    result = {
        "productId": productId,
        "currentPrice": currentPrice,
        "newPrice": newPrice,
        "Approval": priceChangeApprove(currentPrice, newPrice, stock)
    }
    
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
def priceChangeApprove(currentPrice, newPrice, stock):
    if newPrice == currentPrice:
        return {
            "approved": False,
            "message": 'Price change denied, as the prices {} and {} match.'.format(currentPrice, newPrice)
        }
    elif newPrice > currentPrice:
       return {
            "approved": True,
            "message": 'Price increase from {} to {} approved.'.format(currentPrice, newPrice)
        }  
    elif stock > int(os.environ['LOW_STOCK']):
        return {
            "approved": True,
            "message": 'Price decrease from {} to {} approved due to the sufficient stock of {}.'.format(currentPrice, newPrice, stock)
        }
    else:
       return {
            "approved": False,
            "message": 'Price decrease from {} to {} denied due to the low stock of {}.'.format(currentPrice, newPrice, stock)
        }          
