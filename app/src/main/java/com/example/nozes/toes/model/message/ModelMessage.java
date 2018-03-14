
package com.example.nozes.toes.model.message;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMessage {

    @SerializedName("events")
    @Expose
    private List<Event> events = null;


    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelMessage() {
    }

    /**
     * 
     * @param events

     */
    public ModelMessage(List<Event> events) {
        super();
        this.events = events;

    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }


}
