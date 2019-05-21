package com.papb.prima.jogingkuy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventReadResponse {

    @SerializedName("event")
    @Expose
    private List<Event> event = null;

    public EventReadResponse(List<Event> event) {
        this.event = event;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

}