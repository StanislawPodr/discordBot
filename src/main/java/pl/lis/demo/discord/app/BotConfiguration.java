package pl.lis.demo.discord.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import discord4j.core.DiscordClient;

@Configuration
public class BotConfiguration {

    @Value("${discord.bot.token}")
    private String token;

    @Bean
    public DiscordClient discordClient() {
        return DiscordClient.create(token);
    }
}
