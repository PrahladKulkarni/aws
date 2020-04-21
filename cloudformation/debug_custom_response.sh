curl --location --request PUT 'https://cloudformation-custom-resource-response-uswest2.s3-us-west-2.amazonaws.com/arn%3Aaws%3Acloudformation%3Aus-west-2%3A691995883950%3Astack/myCustResource/1acf6910-3225-11e9-b9ce-066c9aefcac8%7CTableNameGenerator%7C7bc4382a-4d19-4581-8538-c213478b95c5?AWSAccessKeyId=AKIAJZG5TWW7RZCZ3KYQ&Expires=1550354259&Signature=c0hkjgppr1FGVfAGVlu%2BzeXmLBM%3D' \
--header 'Content-Type: ' \
--header 'Content-Type: application/octet-stream' \
--data-binary '@{
   "Status" : "SUCCESS",
   "PhysicalResourceId" : "MyCustomResourceVendorID",
   "StackId" : "arn:aws:cloudformation:us-west-2:691995883950:stack/myCustResource/fa4607b0-317c-11e9-9d08-0688a7bf983a",
   "RequestId" : "398c9429-46ef-4d28-870c-0de1e2d04f98",
   "LogicalResourceId" : "TableNameGenerator",
   "Data" : {
      "tableName" : "instructor-happy"
   }
}'