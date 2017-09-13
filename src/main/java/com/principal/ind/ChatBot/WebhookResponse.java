package com.principal.ind.ChatBot;

public class WebhookResponse {
    private String speech;
    private String displayText;


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

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

   
}
