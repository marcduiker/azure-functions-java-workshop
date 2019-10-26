package com.function.demo;

public class AttendeeFeedback {

    public AttendeeFeedback() {
        super();
    }
    
    public AttendeeFeedback(String attendee,  String session, Integer score) {
        partitionKey = session;
        rowKey = attendee;

        attendeeName = attendee;
        sessionName = session;
        sessionScore = score;
    }

    public void setKeys(){
        partitionKey = sessionName;
        rowKey = attendeeName;
    }

    public String partitionKey;
    public String rowKey;
    public String attendeeName;
    public String sessionName;
    public Integer sessionScore;
}