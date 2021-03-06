package perobobbot.plugin.twitch.chat.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.twitch.chat.TwitchChatState;

@RequiredArgsConstructor
public class Disconnection implements TwitchChatEvent {

    @NonNull
    @Getter
    private final TwitchChatState state;

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
