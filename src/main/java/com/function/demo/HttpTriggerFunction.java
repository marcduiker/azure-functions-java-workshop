package com.function.demo;

import java.util.*;
import com.google.gson.*;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.QueueOutput;
import com.microsoft.azure.functions.*;
/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {
    
    @FunctionName("HttpTriggerFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @QueueOutput(name = "feedback", queueName = "feedback-queue", connection = "AzureWebJobsStorage") OutputBinding<AttendeeFeedback> queue,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");
        Optional<String> body = request.getBody(); 

        if (body.isPresent()) {
            Gson gson = new Gson();
            AttendeeFeedback feedback = gson.fromJson(body.get(), AttendeeFeedback.class);
            String thankYouMessage = "Thank you for submitting your feedback for session '" + feedback.sessionName + "' by " + feedback.presenterName + ".";
            queue.setValue(feedback);
            return request.createResponseBuilder(HttpStatus.OK).body(thankYouMessage).build();
        } else {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please provide an AttendeeFeedback object to the request body").build();
        }
    }
}
