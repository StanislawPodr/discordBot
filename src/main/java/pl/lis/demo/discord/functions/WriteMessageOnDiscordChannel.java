package pl.lis.demo.discord.functions;

import java.util.function.Function;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateMono;
import pl.lis.demo.discord.functions.WriteMessageOnDiscordChannel.MessageInput;

public class WriteMessageOnDiscordChannel implements Function<MessageInput, Void> {
    public WriteMessageOnDiscordChannel(GatewayDiscordClient client) {
        this.client = client;
    }

    public record MessageInput(String channelId, String content, String messageToReplyToIdOrNull) {
    }

    GatewayDiscordClient client;

    @Override
    public Void apply(MessageInput input) {
        client.getChannelById(Snowflake.of(input.channelId())).ofType(MessageChannel.class)
                .flatMap(channel -> {
                    MessageCreateMono mono = channel.createMessage(input.content());
                    if (input.messageToReplyToIdOrNull() != null
                            && !input.messageToReplyToIdOrNull().toLowerCase().contains("null")) {
                        mono = mono.withMessageReference(Snowflake.of(input.messageToReplyToIdOrNull()));
                    }
                    return mono;
                }).subscribe();
        return null;
    }

}
