package pl.lis.demo.discord.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.rest.RestClient;

@Configuration
public class BotConfiguration {

    @Value("${DISCORD_BOT_TOKEN}")
    private String token;

    @Bean
    public GatewayDiscordClient discordClient() {
        return DiscordClient.create(token).login().block();
    }

    @Bean
    public RestClient restClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }
}
