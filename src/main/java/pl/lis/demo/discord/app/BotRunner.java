package pl.lis.demo.discord.app;

import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;

import org.springframework.stereotype.Component;

@Component
public class BotRunner {
    public BotRunner(RestClient restClient) {
        ApplicationCommandRequest chatCommand = ApplicationCommandRequest.builder()
                .name("chat")
                .description("Rozpoczyna rozmowę z botem")
                .build();

        restClient.getApplicationId()
                .flatMap(appId -> restClient.getApplicationService()
                        .createGlobalApplicationCommand(appId, chatCommand))
                .subscribe();
    }
}
