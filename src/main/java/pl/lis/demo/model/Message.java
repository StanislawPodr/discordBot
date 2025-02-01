package pl.lis.demo.model;

import java.time.LocalDateTime;

public class Message {
    private String content;
    private String senderId;
    private LocalDateTime timestamp;
    private boolean isPrivate;
    private String channelId;
    
    
    public Message(String content, String senderId, LocalDateTime timestamp, boolean isPrivate, String channelId) {
        this.content = content;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.isPrivate = isPrivate;
        this.channelId = channelId;
    }
    
    public String getContent() {
        return content;
    }
    public String getSenderId() {
        return senderId;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public boolean isPrivate() {
        return isPrivate;
    }
    public String getChannelId() {
        return channelId;
    }
}
