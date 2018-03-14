
package com.example.nozes.toes.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("created_timestamp")
    @Expose
    private String createdTimestamp;
    @SerializedName("message_create")
    @Expose
    private MessageCreate messageCreate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Event() {
    }

    /**
     * 
     * @param id
     * @param createdTimestamp
     * @param messageCreate
     * @param type
     */
    public Event(String type, String id, String createdTimestamp, MessageCreate messageCreate) {
        super();
        this.type = type;
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.messageCreate = messageCreate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public MessageCreate getMessageCreate() {
        return messageCreate;
    }

    public void setMessageCreate(MessageCreate messageCreate) {
        this.messageCreate = messageCreate;
    }

}
