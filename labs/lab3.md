# Adding a Function to Store the Feedback

## Goal

The goal of this lab is to add a new QueueTriggered function which reads the `AttendeeFeedback` message from the queue and saves this to Azure Table Storage.

## Steps

### 1. Creating a new QueueTriggered Function

In VS Code, use the Command Palette (CTRL+Shift+P) and type/select

`Azure Functions: Create Function`

- choose `QueueTrigger`
- re-use the existing package name
- specify a meaningful function name: `StoreFeedbackQueueTrigger`
- select the existing `AzureWebJobsStorage` setting
- enter `feedback-queue` as the queue name.

When you get this message:

 `In order to debug, you must select a storage account for internal use by the Azure Functions runtime.`

Select the emulator, if that is working on your local machine, or select the storage account, if you have an Azure Storage Account setup.

Now VS Code will create a new QueueTrigger function to the project.

The resulting function will look like this:

```java
@FuctionName("QueueTrStoreFeedbackQueueTriggeriggerJava")
public void run(
    @QueueTrigger(name = "message", queueName = "feedback-queue",connection = "AzureWebJobsStorage") String message,
    final ExecutionContext context
) {
    context.getLogger().info("Java Queue trigger function processed a message: " + message);
}
```

### 2. Updating the QueueTrigger

Currently the QueueTrigger `message` parameter is of type `String`. Update the type to `AttendeeFeedback` and rename `message` to `feedback`. The QueueTrigger should look as follows:

```java
@QueueTrigger(name = "queueinput", queueName = "feedback-queue", connection="AzureWebJobsStorage") AttendeeFeedback feedback,
```

Both QueueTrigger and TableOuput require a connection setting. Since, in this case, we use the same connection setting for both, we can remove the connection property from both attributes and place it in a seperate `@StorageAccount` attribute which can be placed just below the `@FunctionName`:

```java
@FunctionName("StoreFeedbackQueueTrigger")
@StorageAccount("AzureWebJobsStorage")
```

### 3. Add the TableOutput Binding. 

This function reads the a feedback item from the queue and should store it to Table Storage. 
We can use the `TableOutput` binding for this where we specify a name and a tableName: 

```java
@TableOutput(name = "tableoutput", tableName = "feedback") OutputBinding<AttendeeFeedback> table,
```

> Note the type of the output is not `AttendeeFeedback` but a generic `OutputBinding<AttendeeFeedback>` type.

### 4. Add the Method Body

To save a record in Azure Table Storage it requires two properties: a partitionKey and a rowKey. These keys are used to allow partitioning of large volumes of data and efficiently quering the records.

The `AttendeeFeedback` class has properties for these keys but they have not been set yet. To do so call the `setKeys()` method on the `AttendeeFeedback` object.

Once the keys are set the `AttendeeFeedback` object can be saved to storage by using the `setValue()` method on the `OutputBinding<AttendeeFeedback>` table object.

The complete function should look like this:

```java
@FunctionName("StoreFeedbackQueueTrigger")
@StorageAccount("AzureWebJobsStorage")
public void run(
       @QueueTrigger(name = "queueinput", queueName = "feedback-queue") AttendeeFeedback feedback,
       @TableOutput(name = "tableoutput", tableName = "feedback") OutputBinding<AttendeeFeedback> table,
        final ExecutionContext context) {
    context.getLogger().info("Queue trigger started.");
    feedback.setKeys();
    table.setValue(feedback);
}
```

### 5. Run the Function App Locally

Start to run & debug the Function App (`F5`). Watch the terminal window to see if there are any issues.

### 6. Trigger the Function

Use the client of your choice to do a POST to the local endpoint (`http://localhost:7071/api/PostFeedbackHttpTrigger`) and provide a valid `AttendeeFeedback` object as the payload:

```json
{
  "sessionName": "Azure Functions Workshop",
  "attendeeName": "YOURNAME",
  "sessionScore": 4
}
```

> Did the function ran correctly once it has been triggered? Is there a record in the `feedback` table in the local emulated storage?

> What happens when you trigger the function again with the exact same payload?

## Want to know more?

You've reached the end of the labs, well done!

Here are some additional resources you might want to look into:
- [Azure Functions Java developer guide](https://docs.microsoft.com/en-us/azure/azure-functions/functions-reference-java)
- [Azure Functions Java Worker on GitHub](https://github.com/Azure/azure-functions-java-worker)
- [Spring On Azure](https://docs.microsoft.com/en-us/azure/java/spring-framework/?view=azure-java-stable)