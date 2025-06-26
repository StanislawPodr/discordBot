package pl.lis.demo.chatbot;

import discord4j.core.GatewayDiscordClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lis.demo.discord.functions.messages.MessagesInteractionFunctions;

@Component
public class ChatbotRunner {

    private final ChatClient chatClient;
    private final GatewayDiscordClient client;

    @Autowired
    public ChatbotRunner(ChatClient chatClient, GatewayDiscordClient client) {
        this.chatClient = chatClient;
        this.client =  client;
    }

    public ChatClient.CallResponseSpec chatWithAllFunctions(String message) {
        return chatClient.prompt(message).tools(new MessagesInteractionFunctions(client)).call();
    }

}
