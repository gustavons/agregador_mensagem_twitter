
package com.example.nozes.toes.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageData {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("entities")
    @Expose
    private Entities entities;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MessageData() {
    }

    /**
     * 
     * @param text
     * @param entities
     */
    public MessageData(String text, Entities entities) {
        super();
        this.text = text;
        this.entities = entities;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

}
