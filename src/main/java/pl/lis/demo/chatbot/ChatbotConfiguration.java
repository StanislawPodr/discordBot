package pl.lis.demo.chatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import discord4j.core.GatewayDiscordClient;



@Configuration
public class ChatbotConfiguration {

    ChatClient.Builder chatBuilder;

    public ChatbotConfiguration(ChatClient.Builder chatBuilder) {
        this.chatBuilder = chatBuilder;
    }

    @Bean
    public ChatClient chatClient() {
        return chatBuilder.build();
    }

}
