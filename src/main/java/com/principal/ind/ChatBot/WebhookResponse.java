package com.principal.ind.ChatBot;

public class WebhookResponse {
    private final String speech;
    private final String displayText;


    public WebhookResponse(String speech, String displayText) {
        this.speech = speech;
        this.displayText = displayText;
    }

    public String getSpeech() {
        return speech;
    }

    public String getDisplayText() {
        return displayText;
    }

   
}
