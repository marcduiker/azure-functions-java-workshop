package com.function.demo;

import com.google.gson.*;
import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit test for PostFeedbackHttpTrigger class.
 */
public class PostFeedbackHttpTriggerTest {
    /**
     * Unit test for HttpTriggerJava method.
     */
    @Test
    public void testHttpTriggerJava() throws Exception {
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);
        Gson gson = new Gson();
        AttendeeFeedback feedback = new AttendeeFeedback("Me", "Azure Functions", 5);
        String feedbackString = gson.toJson(feedback);
        final Optional<String> queryBody = Optional.of(feedbackString);
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        OutputBinding<AttendeeFeedback> feedbackItem = mock(OutputBinding.class);

        // Invoke
        final HttpResponseMessage ret = new PostFeedbackHttpTrigger().run(req, feedbackItem, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }
}
