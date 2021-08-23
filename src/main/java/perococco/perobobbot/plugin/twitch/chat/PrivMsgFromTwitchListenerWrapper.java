package perococco.perobobbot.plugin.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.twitch.chat.PrivMsgFromTwitchListener;
import perobobbot.plugin.twitch.chat.TwitchChatListener;
import perobobbot.plugin.twitch.chat.TwitchChatState;
import perobobbot.plugin.twitch.chat.event.ReceivedMessage;
import perobobbot.plugin.twitch.chat.event.ReceivedMessageExtractor;
import perobobbot.plugin.twitch.chat.event.TwitchChatEvent;
import perobobbot.plugin.twitch.chat.message.from.Join;
import perobobbot.plugin.twitch.chat.message.from.PrivMsgFromTwitch;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class PrivMsgFromTwitchListenerWrapper implements TwitchChatListener {

    @NonNull
    private final PrivMsgFromTwitchListener delegate;

    private final ReceivedMessageExtractor extractor = ReceivedMessageExtractor.create();

    @Override
    public void onTwitchChatEvent(@NonNull TwitchChatEvent event) {
        final var msg = event.accept(extractor).orElse(null);
        if (msg != null) {
             this.castToPrivateMessage(msg).ifPresent(delegate::onPrivateMessage);
             this.logJoinPart(msg);
        }

    }

    private void logJoinPart(ReceivedMessage<?> msg) {
        if (msg.getMessage() instanceof Join join) {
            if (join.getChannel().getName().equalsIgnoreCase("perococco")) {

            }
        }

    }

    @NonNull
    private Optional<ReceivedMessage<PrivMsgFromTwitch>> castToPrivateMessage(@NonNull ReceivedMessage<?> receivedMessage) {
        if (receivedMessage.getMessage() instanceof PrivMsgFromTwitch) {
            final TwitchChatState twitchChatState = receivedMessage.getState();
            final Instant receptionTime = receivedMessage.getReceptionTime();
            final PrivMsgFromTwitch message = (PrivMsgFromTwitch) receivedMessage.getMessage();
            return Optional.of(new ReceivedMessage<>(twitchChatState, receptionTime, message));
        }
        return Optional.empty();
    }

}
