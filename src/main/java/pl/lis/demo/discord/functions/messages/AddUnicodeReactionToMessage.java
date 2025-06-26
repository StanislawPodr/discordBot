package pl.lis.demo.discord.functions.messages;

import java.util.function.Function;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.entity.channel.MessageChannel;
import pl.lis.demo.discord.functions.messages.AddUnicodeReactionToMessage.MessageDataForUnicodeReaction;

public class AddUnicodeReactionToMessage implements
        Function<MessageDataForUnicodeReaction, Void> {

    GatewayDiscordClient client;

    public record MessageDataForUnicodeReaction(String channelId, String messageId, String unicode) {
    }

    public AddUnicodeReactionToMessage(GatewayDiscordClient client) {
        this.client = client;
    }

    @Override
    public Void apply(MessageDataForUnicodeReaction message) {
        try {
            client.getChannelById(Snowflake.of(message.channelId()))
                    .ofType(MessageChannel.class)
                    .flatMap(channel -> channel.getMessageById(Snowflake.of(message.messageId())))
                    .flatMap(msg -> msg.addReaction(ReactionEmoji.unicode(message.unicode())))
                    .subscribe();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

}
