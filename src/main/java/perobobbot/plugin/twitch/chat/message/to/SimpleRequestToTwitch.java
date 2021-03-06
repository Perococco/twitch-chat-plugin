package perobobbot.plugin.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.lang.CastTool;
import perobobbot.lang.fp.TryResult;
import perobobbot.plugin.twitch.chat.TwitchChatState;
import perobobbot.plugin.twitch.chat.message.IRCCommand;
import perobobbot.plugin.twitch.chat.message.from.MessageFromTwitch;

import java.util.Optional;

/**
 * @author perococco
 **/
public abstract class SimpleRequestToTwitch<A> extends RequestToTwitch<A> {

    private final Class<A> expectedAnswerType;

    SimpleRequestToTwitch(@NonNull IRCCommand command, @NonNull Class<A> expectedAnswerType) {
        super(command, expectedAnswerType);
        this.expectedAnswerType = expectedAnswerType;
    }


    @Override
    public final @NonNull Optional<TryResult<Throwable, A>> isMyAnswer(@NonNull MessageFromTwitch messageFromTwitch, @NonNull TwitchChatState state) {
        return CastTool.cast(expectedAnswerType, messageFromTwitch)
                       .flatMap(a -> doIsMyAnswer(a, state))
                       .map(TryResult::success);
    }

    @NonNull
    protected abstract Optional<A> doIsMyAnswer(@NonNull A twitchAnswer, @NonNull TwitchChatState state);
}
