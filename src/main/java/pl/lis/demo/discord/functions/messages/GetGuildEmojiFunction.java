package pl.lis.demo.discord.functions.messages;

import java.util.List;
import java.util.function.Function;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import pl.lis.demo.model.EmojiData;
import pl.lis.demo.discord.functions.messages.GetGuildEmojiFunction.GuildId;

public class GetGuildEmojiFunction implements Function<GuildId, List<EmojiData>> {

    public record GuildId(String id) {}

    public GetGuildEmojiFunction(GatewayDiscordClient client) {
        this.client = client;
    }

    GatewayDiscordClient client;

    @Override
    public List<EmojiData> apply(GuildId id) {
        return client.getGuildById(Snowflake.of(id.id())).flatMap(guild -> guild.getEmojis()
                .map(guildEmoji -> new EmojiData(guildEmoji.getId().asString(), true,
                        guildEmoji.getName()))
                .collectList())
                .block();
    }

}
