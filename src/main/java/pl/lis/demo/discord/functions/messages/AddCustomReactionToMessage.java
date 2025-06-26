package pl.lis.demo.discord.functions.messages;

import java.util.function.Function;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.entity.channel.MessageChannel;
import pl.lis.demo.discord.functions.messages.AddCustomReactionToMessage.MessageDataForCustomReaction;

public class AddCustomReactionToMessage implements
        Function<MessageDataForCustomReaction, Void> {

    GatewayDiscordClient client;

    public record MessageDataForCustomReaction(String channelId, String messageId, String emoteId, String emoteName) {
    }

    public AddCustomReactionToMessage(GatewayDiscordClient client) {
        this.client = client;
    }

    @Override
    public Void apply(MessageDataForCustomReaction message) {
        try {
            client.getChannelById(Snowflake.of(message.channelId()))
                    .ofType(MessageChannel.class)
                    .flatMap(channel -> channel.getMessageById(Snowflake.of(message.messageId())))
                    .map(msg -> ReactionEmoji.custom(Snowflake.of(message.emoteId()), message.emoteName(), false))
                    .subscribe();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

}
