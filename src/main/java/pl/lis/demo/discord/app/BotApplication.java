package pl.lis.demo.discord.app;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;


import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BotApplication {
    public BotApplication(GatewayDiscordClient client) {
        client.on(ChatInputInteractionEvent.class, event -> {
            if ("chat".equals(event.getCommandName())) {
                return handleChatCommand(event);
            }
            return Mono.empty();
        }).subscribe();
    }

    private Mono<Void> handleChatCommand(ChatInputInteractionEvent event) {
            return event.reply("nie mam jeszcze komend");
    }
}