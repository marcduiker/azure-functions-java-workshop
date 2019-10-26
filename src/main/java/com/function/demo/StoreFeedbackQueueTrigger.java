package com.function.demo;

import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueTrigger;
import com.microsoft.azure.functions.annotation.StorageAccount;
import com.microsoft.azure.functions.annotation.TableOutput;
import com.microsoft.azure.functions.*;

public class StoreFeedbackQueueTrigger {
    /**
     * This function is triggered by messages pushed to the 'feedback-queue' ".
     */
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
}
