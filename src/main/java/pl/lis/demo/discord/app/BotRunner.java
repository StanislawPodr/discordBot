package pl.lis.demo.discord.app;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BotRunner {

    private final DiscordClient discordClient;

    public BotRunner(DiscordClient discordClient) {
        this.discordClient = discordClient;
    }

    @PostConstruct
    public void startBot() {
        discordClient.withGateway((GatewayDiscordClient gateway) -> {
            // Możesz tu dodać logikę rejestracji zdarzeń itp.
            return Mono.empty();
        }).block();
    }
}
