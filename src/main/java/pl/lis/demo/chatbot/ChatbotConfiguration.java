package pl.lis.demo.chatbot;

import java.util.List;
import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import discord4j.core.GatewayDiscordClient;
import pl.lis.demo.discord.functions.AddCustomReactionToMessage;
import pl.lis.demo.discord.functions.AddCustomReactionToMessage.MessageDataForCustomReaction;
import pl.lis.demo.discord.functions.GetGuildEmojiFunction;
import pl.lis.demo.discord.functions.WriteMessageOnDiscordChannel;
import pl.lis.demo.model.EmojiData;
import pl.lis.demo.discord.functions.GetGuildEmojiFunction.GuildId;
import pl.lis.demo.discord.functions.WriteMessageOnDiscordChannel.MessageInput;
import pl.lis.demo.discord.functions.AddUnicodeReactionToMessage.MessageDataForUnicodeReaction;
import pl.lis.demo.discord.functions.AddUnicodeReactionToMessage;



@Configuration
public class ChatbotConfiguration {

    ChatClient.Builder chatBuilder;
    GatewayDiscordClient client;

    public ChatbotConfiguration(ChatClient.Builder chatBuilder, GatewayDiscordClient client) {
        this.chatBuilder = chatBuilder;
        this.client = client;
    }

    @Bean 
    public ChatClient chatClient() {
        return chatBuilder.build();
    }

    @Bean
    @Description("Gets list of IDs and info of all guild emojis avalible for usage. First param to this function is guild ID")
    public Function<GuildId, List<EmojiData>> getGuildEmojiFunction() {
        return new GetGuildEmojiFunction(client);
    }

    @Bean
    @Description("Adds a custom (guild) emoji reaction to a message. Don't use if you don't know ID.")
    public Function<MessageDataForCustomReaction, Void> addCustomReactionToMessage() {
       return new AddCustomReactionToMessage(client);
    }

    @Bean
    @Description("Adds a unicode reaction to a message")
    public Function<MessageDataForUnicodeReaction, Void> addUnicodeReactionToMessage() {
       return new AddUnicodeReactionToMessage(client);
    }

    @Bean
    @Description("Adds a message on channel with specified id. If reply message id specified, the message will be a reply")
    public Function<MessageInput, Void> writeMessageOnDiscordChannel() {
       return new WriteMessageOnDiscordChannel(client);
    }

}
