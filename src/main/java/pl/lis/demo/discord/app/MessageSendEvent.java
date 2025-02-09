package pl.lis.demo.discord.app;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import pl.lis.demo.chatbot.ChatbotRunner;
import pl.lis.demo.model.MessageData;
import reactor.core.publisher.Mono;

@Component
public class MessageSendEvent {

    ChatbotRunner chat;
    int minutes = 10;

    public MessageSendEvent(GatewayDiscordClient client, ChatbotRunner chat) {
        client.on(MessageCreateEvent.class, this::handleNewMessage)
                .subscribe();
        this.chat = chat;
    }

    private Mono<Void> handleNewMessage(MessageCreateEvent event) {
        Message message = event.getMessage();
        User botUser = message.getClient().getSelf().block();
        if (botUser == null || message.getAuthor().get().getUsername().equals(botUser.getUsername()))
            return Mono.empty();

        // boolean isUserMentioned =
        // message.getUserMentionIds().contains(botUser.getId());

        // List<Long> mentionedRoles = message.getRoleMentionIds()
        // .stream().map(id -> id.asLong()).toList();

        // Mono<Boolean> isRoleMentioned = message.getGuild()
        // .flatMap(guild -> botUser.asMember(guild.getId()))
        // .flatMapMany(Member::getRoles)
        // .any(role -> mentionedRoles.contains(role.getId().asLong()));

        System.out.println(getMessagesInInterval(message, minutes));

        return Mono.just(chat.chatWithAllFunctions(
                """
                        Jesteś botem na serwerze discord.
                        Jesteś członkiem serwera niewidzialny Lis.
                        Nazywasz się Lis i będziemy cię wywoływać w ten sposób.
                        Gdy wiadomość skierowana jest do wszytskich skierowana jest też do ciebie.
                        Jesteś wywoływany przez event, po każdej napisanej wiadomości i nie zawsze musisz reagować.
                        Masz dostęp do różnych funkcji.
                        Nie reaguj na każdą wiadomość,
                        odpowiadaj krótko na pytania do ciebie,
                        dodawaj czasem reakcje i czasami dołączaj się do żartów.
                        Pamiętaj aby nie odpowiadać niepotrzebnie kilka razy na tą samą widaomość.
                        NIE REAGUJ GDY WIADOMOŚĆ JEST DO KOGOŚ INNEGO, CZYLI NIE DO LISA ALBO WSZYSTKICH!!!
                        Poniżej wątek ostatnich wiadomości z konkretnych kanałów, wraz z kontekstem w JSON: \n"""
                        + "Wiadomości z ilu ostatnich minut w wątku: " + minutes + "\n"
                        + "ID serwera discord: " + message.getGuildId().get().asString() + "\n"
                        + getMessagesInInterval(message, minutes)))
                .doOnNext(response -> System.out.println(response.content())).then();

        // return isRoleMentioned.flatMap(roleMentioned -> {
        // if (isUserMentioned || roleMentioned) {
        // return message.getChannel().ofType(GuildMessageChannel.class)
        // .flatMap(channel -> channel.createMessage()
        // .withMessageReference(message.getId())
        // .then());
        // }
        // return Mono.empty();
        // });

    }

    private List<MessageData> getMessageContext(Message newMessage) {
        Message message = newMessage;
        List<MessageData> messageList = new ArrayList<>();
        MessageChannel channel = message.getChannel().block();
        messageList.add(MessageData.fromMessage(message));
        while (message.getMessageReference().isPresent()) {
            message = channel.getMessageById(message.getMessageReference().get().getMessageId().get()).block();
            messageList.add(MessageData.fromMessage(message));
            if (message == null) {
                break;
            }
        }
        return messageList;
    }

    public List<List<MessageData>> getMessagesInInterval(Message newMessage, int minutes) {
        List<List<MessageData>> list = newMessage.getChannel()
                .flatMapMany(channel -> channel.getMessagesBefore(newMessage.getId()))
                .takeWhile(message -> message.getTimestamp().isAfter(Instant.now().minus(Duration.ofMinutes(minutes))))
                .map(message -> {
                    List<MessageData> replyData = getMessageContext(message);
                    List<MessageData> toReply = new ArrayList<>();
                    toReply.add(replyData.getLast());
                    for (MessageData data : replyData) {
                        if (data.getMessageSendTime().isBefore(LocalDateTime
                                .ofInstant(Instant.now().minus(Duration.ofMinutes(minutes)), ZoneId.systemDefault()))) {
                            toReply.add(data);
                        }
                    }
                    return toReply.reversed();
                }).collectList().block().reversed();
        list.add(getMessageContext(newMessage));
        return list;
    }
}
