package pl.lis.demo.chatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;

@Component
public class ChatbotRunner {

    public ChatbotRunner(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    ChatClient chatClient;

    public CallResponseSpec chatWithAllFunctions(String message) {
        return chatClient
                .prompt(new Prompt(message,
                        OpenAiChatOptions.builder().function("writeMessageOnDiscordChannel")
                                .function("addCustomReactionToMessage").function("addUnicodeReactionToMessage")
                                .function("getGuildEmojiFunction").build()))
                .call();
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

}
