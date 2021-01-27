#!/bin/bash
aws lambda update-function-code --function-name "$1" --zip-file fileb://target/exasol-cloudwatch-adapter.jar --publish
