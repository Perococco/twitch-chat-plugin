package perobobbot.plugin.twitch.chat.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.twitch.chat.TwitchChatState;
import perobobbot.plugin.twitch.chat.message.to.MessageToTwitch;

import java.time.Instant;

@RequiredArgsConstructor
public class PostedMessage implements TwitchChatEvent {

    @NonNull
    @Getter
    private final TwitchChatState state;

    @NonNull
    @Getter
    private final Instant dispatchingTime;

    @NonNull
    @Getter
    private final MessageToTwitch postedMessage;

    @Override
    public String toString() {
        return "PostedMessage{" + postedMessage +"}";
    }

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
