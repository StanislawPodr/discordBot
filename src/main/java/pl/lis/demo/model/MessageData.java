package pl.lis.demo.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.object.reaction.ReactionEmoji;

public class MessageData {
    private String content;
    private String messageId, channelId;
    private String author;
    private LocalDateTime messageSendTime;
    private List<ReactionData> reactionList;
    private String repliesTo;

    public MessageData(String content, String messageId, String channelId, String author, LocalDateTime messageSendTime,
            List<ReactionData> reactionList, String repliesTo) {
        this.content = content;
        this.messageId = messageId;
        this.channelId = channelId;
        this.author = author;
        this.messageSendTime = messageSendTime;
        this.reactionList = reactionList;
        this.repliesTo = repliesTo;
    }

    public static MessageData fromMessage(Message message) {
        String content = message.getContent();
        String messageId = message.getId().asString();
        String author = "";
        if (message.getAuthor().isPresent()) {
            author = message.getAuthor().get().getUsername();
        }
        LocalDateTime messageSendTime = message.getTimestamp().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String channelId = message.getChannelId().asString();
        List<ReactionData> reactionList = new ArrayList<>();
        for (Reaction reaction : message.getReactions()) {
            ReactionEmoji emoji = reaction.getEmoji();
            String id = "";
            String name = "";

            boolean isCustom = false;

            if (emoji.asUnicodeEmoji().isPresent()) {
                id = emoji.asUnicodeEmoji().get().getRaw();
                name = emoji.asUnicodeEmoji().get().asFormat();
            }

            if (emoji.asCustomEmoji().isPresent()) {
                id = emoji.asCustomEmoji().get().getId().asString();
                name = emoji.asCustomEmoji().get().getName();
                isCustom = true;
            }
            reactionList.add(new ReactionData(id, name, reaction.getCount(), isCustom));
        }
        String repliesTo = message.getMessageReference().isPresent()
                ? message.getMessageReference().get().getMessageId().get().asString()
                : null;
        return new MessageData(content, messageId, channelId, author, messageSendTime, reactionList, repliesTo);
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void addReaction(ReactionData reaction) {
        reactionList.add(reaction);
    }

    public String getContent() {
        return content;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getMessageSendTime() {
        return messageSendTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public List<ReactionData> getReactionList() {
        return reactionList;
    }

    public String getRepliesTo() {
        return repliesTo;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof MessageData data) {
            if(data.getMessageId().equals(getMessageId())) {
                return true;
            }
        }
        return false;
    }
}
