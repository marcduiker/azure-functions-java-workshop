# Changing your Function

## Goal

The goal of this lab is to update the HttpTrigger function to read an `AttendeeFeedback` object from the body and to put this on a queue.

## Creating the `AttendeeFeedback` class

Lets start with creating an `AttendeeFeedback` class with the following definition: 

```java
public class AttendeeFeedback {

    public AttendeeFeedback() {
        super();
    }
    
    public AttendeeFeedback(String presenter, String attendee,  String session, Integer score) {
        partitionKey = session;
        rowKey = attendee;

        presenterName = presenter;
        attendeeName = attendee;
        sessionName = session;
        sessionScore = score;
    }

    public void SetKeys(){
        partitionKey = sessionName;
        rowKey = attendeeName;
    }

    public String partitionKey;
    public String rowKey;
    public String presenterName;
    public String attendeeName;
    public String sessionName;
    public Integer sessionScore;
}
```

> Note that this class has partitionKey and rowKey properties. These are required for saving this object in Azure Table Storage in the next lab.

## Add a QueueOutput Binding

Now add the following output binding to the function (right below the @HttpTrigger):

```java
@QueueOutput(name = "feedback", queueName = "feedback-queue", connection = "AzureWebJobsStorage") OutputBinding<AttendeeFeedback> queue,
```

## Local Emulated Storage

You just specified a connection name in the `QueueOupu`t attribute but this connection setting is not defined yet.

The `local.settings.json` file contains settings for the Function App which are only used on your local machine (this file should not be checked into source control).

Make sure your `local.settings.json` contains the `AzureWebJobsStorage` value as shown below:

```json
{
  "IsEncrypted": false,
  "Values": {
    "AzureWebJobsStorage": "UseDevelopmentStorage=true",
    "FUNCTIONS_WORKER_RUNTIME": "java"
  }
}
```

By using the `UseDevelopmentStorage=true` value the Azure Functions runtime knows it should use local emulated storage. Once the Function App is deployed to the cloud the `AzureWebJobsStorage` setting should point to an Azure Storage Account.

If you can't get the storage emulator to work you can also create an [Azure Storage Account](https://docs.microsoft.com/en-us/azure/storage/common/storage-quickstart-create-account?tabs=azure-portal) (or ask the proctor for help).

## Update the Method Body

Then add code which reads an `AttendeeFeedback` object from the request body and passes the object to the queue. The code should look something like this:

```java
Optional<String> body = request.getBody(); 

if (body.isPresent()) {
    Gson gson = new Gson();
    AttendeeFeedback feedback = gson.fromJson(body.get(), AttendeeFeedback.class);
    String thankYouMessage = "Thank you for submitting your feedback for session '" + feedback.sessionName + "' by " + feedback.presenterName + ".";
    queue.setValue(feedback);
    return request.createResponseBuilder(HttpStatus.OK).body(thankYouMessage).build();
 else {
    return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please provide an AttendeeFeedback object to the request body").build();
}
```

> Note that the code shown above requires some additional dependencies which need to be added in the POM.xml.

> The unit test are probably failing and need to be refactored due to the changes made.

## Run the Function App Locally

Start to run & debug the Function App (`F5`). Watch the terminal window to see if there are any issues.

## Trigger the function

Use the client of your choice to do a POST to the local endpoint and provide a valid `AttendeeFeedback` object as the payload:

```json
{
  "presenterName": "Marc Duiker",
  "sessionName": "Azure Functions Workshop",
  "attendeeName": "Someone else",
  "sessionScore": 4
}
```

> Did the function ran correctly once it has been triggered? Is there a message on a queue named `feedback-queue` in the local emulated storage?