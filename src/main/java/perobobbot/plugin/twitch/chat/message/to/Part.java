package perobobbot.plugin.twitch.chat.message.to;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.DispatchContext;
import perobobbot.plugin.twitch.chat.Channel;
import perobobbot.plugin.twitch.chat.TwitchChatState;
import perobobbot.plugin.twitch.chat.message.IRCCommand;

import java.util.Optional;

/**
 * @author perococco
 **/
@Getter
public class Part extends SimpleRequestToTwitch<perobobbot.plugin.twitch.chat.message.from.Part> {

    @NonNull
    @Getter
    private final Channel channel;

    public Part(@NonNull Channel channel) {
        super(IRCCommand.PART, perobobbot.plugin.twitch.chat.message.from.Part.class);
        this.channel = channel;
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "PART #"+channel.getName();
    }

    @Override
    @NonNull
    protected Optional<perobobbot.plugin.twitch.chat.message.from.Part> doIsMyAnswer(@NonNull perobobbot.plugin.twitch.chat.message.from.Part twitchAnswer,
                                                                                     @NonNull TwitchChatState state) {
        if (twitchAnswer.getChannel().equals(channel) && twitchAnswer.getUser().equals(state.getNickOfConnectedUser())) {
            return Optional.of(twitchAnswer);
        }
        return Optional.empty();
    }
}
