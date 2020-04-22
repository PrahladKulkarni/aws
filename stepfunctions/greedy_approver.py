import boto3
import json

sfnClient = boto3.client('stepfunctions')

print('Waiting for the task...')
response = sfnClient.get_activity_task(
    activityArn='arn:aws:states:us-west-2:691995883950:activity:PriceUpdateApproval',
    workerName='Greedy Approver'
)

taskToken = response ['taskToken']

if not taskToken:
    print('No task is available')
    quit()

input = json.loads(response ['input'])
currentPrice = input ['Product']['Price']
newPrice = input ['price']

if newPrice < currentPrice:
    print('Price reduction from {} to {} denied'.format(currentPrice, newPrice))
    result='{"Approved": false}'
else:        
    print('Price increase from {} to {} approved'.format(currentPrice, newPrice))
    result='{"Approved": true}'

response = sfnClient.send_task_success(
    taskToken=taskToken,
    output=result
)

quit()