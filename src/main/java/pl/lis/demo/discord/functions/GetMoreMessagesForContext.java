package pl.lis.demo.discord.functions;

import java.util.List;
import java.util.function.Function;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import pl.lis.demo.discord.functions.GetMoreMessagesForContext.DataForMoreMessages;
import pl.lis.demo.discord.functions.GetMoreMessagesForContext.MoreMessages;
import pl.lis.demo.model.MessageData;

public class GetMoreMessagesForContext implements Function<DataForMoreMessages, MoreMessages> {
    public GetMoreMessagesForContext(GatewayDiscordClient client) {
        this.client = client;
    }

    public record DataForMoreMessages(int numberOfMessagesToGet, String channelId,
            String oldestMessageInCurrentContextId) {
    }

    public record MoreMessages(List<String> messages) {
    }

    GatewayDiscordClient client;

    @Override
    public MoreMessages apply(DataForMoreMessages data) {
        return new MoreMessages(client.getChannelById(Snowflake.of(data.channelId())).ofType(MessageChannel.class)
                .flatMap(channel -> channel.getMessagesBefore(Snowflake.of(data.oldestMessageInCurrentContextId()))
                        .take(data.numberOfMessagesToGet())
                        .map(msg -> MessageData.fromMessage(msg).toString())
                        .collectList())
                .block());
    }

}
