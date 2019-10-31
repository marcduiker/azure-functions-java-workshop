# Deployment to Azure

## Goal

The goal of this lab is to create Azure resources and deploy your functions to the cloud. You need an Azure account in order to complete this lab. 

## Steps

### 1. Azure CLI Login

Use the Azure CLI to login into Azure:

`az login`

### 2. Run the Deploy Command

Running this command will create all required Azure resources and deploys your function code to Azure:

`mvn azure-functions:deploy`

> What is the name of the Function App that is created?

### 3. Trigger the Function

Use the client of your choice to do a POST to the Azure endpoint (`https://<AZURE_FUNCTION_APP>/api/PostFeedbackHttpTrigger?code=<FUNCTIONKEY>`) and provide a valid `AttendeeFeedback` object as the payload:

```json
{
  "sessionName": "Azure Functions Workshop",
  "attendeeName": "YOURNAME",
  "sessionScore": 4
}
```

Note that you now need to provide a function key in order to authenticate. This key can be found in the Azure portal.

> Did the function ran correctly once it has been triggered? Is there a record in the `feedback` table in Azure?

## Want to know more?

You've reached the end of the labs, well done!

Here are some additional resources you might want to look into:
- [Azure Functions Java developer guide](https://docs.microsoft.com/en-us/azure/azure-functions/functions-reference-java)
- [Azure Functions Java Worker on GitHub](https://github.com/Azure/azure-functions-java-worker)
- [Spring On Azure](https://docs.microsoft.com/en-us/azure/java/spring-framework/?view=azure-java-stable)