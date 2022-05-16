#!/bin/bash
aws s3 presign s3://product-catalog8/why-aws.png --expires-in 30
