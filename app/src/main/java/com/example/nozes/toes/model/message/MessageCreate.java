
package com.example.nozes.toes.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageCreate {

    @SerializedName("target")
    @Expose
    private Target target;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("source_app_id")
    @Expose
    private String sourceAppId;
    @SerializedName("message_data")
    @Expose
    private MessageData messageData;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MessageCreate() {
    }

    /**
     * 
     * @param target
     * @param senderId
     * @param messageData
     * @param sourceAppId
     */
    public MessageCreate(Target target, String senderId, String sourceAppId, MessageData messageData) {
        super();
        this.target = target;
        this.senderId = senderId;
        this.sourceAppId = sourceAppId;
        this.messageData = messageData;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSourceAppId() {
        return sourceAppId;
    }

    public void setSourceAppId(String sourceAppId) {
        this.sourceAppId = sourceAppId;
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

}
