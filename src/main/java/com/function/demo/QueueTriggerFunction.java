package com.function.demo;

import java.util.*;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueTrigger;
import com.microsoft.azure.functions.annotation.StorageAccount;
import com.microsoft.azure.functions.annotation.BlobOutput;
import com.microsoft.azure.functions.*;

public class QueueTriggerFunction {
    /**
     * This function is triggered by messages pushed to the 'feedback-queue' ".
     */
    @FunctionName("QueueTriggerFunction")
    @StorageAccount("AzureWebJobsStorage")
    public AttendeeFeedback run(
           @QueueTrigger(name = "feedback", queueName = "feedback-queue") AttendeeFeedback message,
           @BlobOutput(name = "bloboutput", path = "feedback-output/{rand-guid}.json") OutputBinding<AttendeeFeedback> blob,
            final ExecutionContext context) {
        context.getLogger().info("Queue trigger started.");
        blob.setValue(message);

        return message;
    }
}
