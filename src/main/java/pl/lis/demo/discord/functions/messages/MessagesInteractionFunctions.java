package pl.lis.demo.discord.functions.messages;
import discord4j.core.GatewayDiscordClient;
import org.springframework.ai.tool.annotation.Tool;
import pl.lis.demo.model.EmojiData;

import java.util.List;

public class MessagesInteractionFunctions {
        GatewayDiscordClient client;

        public MessagesInteractionFunctions(GatewayDiscordClient client) {
            this.client = client;
        }

        @Tool(description = "Gets list of IDs and info of all guild emojis available for usage. First param to this function is guild ID")
        public List<EmojiData> getGuildEmojiFunction(GetGuildEmojiFunction.GuildId guildId) {
            return new GetGuildEmojiFunction(client).apply(guildId);
        }

        @Tool(description = "Adds a custom (guild) emoji reaction to a message. Don't use if you don't know ID.")
        public void addCustomReactionToMessage(AddCustomReactionToMessage.MessageDataForCustomReaction messageData) {
            new AddCustomReactionToMessage(client).apply(messageData);
        }

        @Tool(description = "Adds a unicode reaction to a message")
        public void addUnicodeReactionToMessage(AddUnicodeReactionToMessage.MessageDataForUnicodeReaction  messageData) {
            new AddUnicodeReactionToMessage(client).apply(messageData);
        }

        @Tool(description = "Adds a message on channel with specified id. If reply message id specified, the message will be a reply")
        public void writeMessageOnDiscordChannel(WriteMessageOnDiscordChannel.MessageInput messageInput) {
            new WriteMessageOnDiscordChannel(client).apply(messageInput);
        }

}
