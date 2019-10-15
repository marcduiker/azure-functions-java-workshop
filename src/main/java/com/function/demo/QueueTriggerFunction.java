package com.function.demo;

import java.util.*;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueTrigger;
import com.microsoft.azure.functions.annotation.StorageAccount;
import com.microsoft.azure.functions.annotation.TableOutput;
import com.microsoft.azure.functions.*;

public class QueueTriggerFunction {
    /**
     * This function is triggered by messages pushed to the 'feedback-queue' ".
     */
    @FunctionName("QueueTriggerFunction")
    @StorageAccount("AzureWebJobsStorage")
    public void run(
           @QueueTrigger(name = "feedback", queueName = "feedback-queue") AttendeeFeedback feedback,
           @TableOutput(name = "bloboutput", tableName = "feedback") OutputBinding<AttendeeFeedback> table,
            final ExecutionContext context) {
        context.getLogger().info("Queue trigger started.");
        feedback.SetKeys();
        table.setValue(feedback);
    }
}
