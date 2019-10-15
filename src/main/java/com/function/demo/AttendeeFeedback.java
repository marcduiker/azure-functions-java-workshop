package com.function.demo;

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