package perobobbot.plugin.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.lang.DispatchContext;
import perobobbot.plugin.twitch.chat.TwitchChatState;
import perobobbot.plugin.twitch.chat.message.IRCCommand;
import perobobbot.plugin.twitch.chat.message.from.PongFromTwitch;

import java.util.Optional;

public class Ping extends SimpleRequestToTwitch<PongFromTwitch> {

    public Ping() {
        super(IRCCommand.PING, PongFromTwitch.class);
    }

    @Override
    public String commandInPayload() {
        return "PING";
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "PING :tmi.twitch.tv";
    }

    @Override
    protected @NonNull Optional<PongFromTwitch> doIsMyAnswer(@NonNull PongFromTwitch twitchAnswer, @NonNull TwitchChatState state) {
        return Optional.of(twitchAnswer);
    }
}
