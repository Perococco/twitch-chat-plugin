package perococco.perobobbot.plugin.twitch.chat.state.mutator;

import lombok.NonNull;
import perobobbot.lang.Operator;
import perococco.perobobbot.plugin.twitch.chat.TwitchIO;
import perococco.perobobbot.plugin.twitch.chat.state.ConnectionState;

public interface OperatorUsingIO<T> extends Operator<ConnectionState,T> {

    @Override
    @NonNull
    default ConnectionState mutate(ConnectionState state) {
        return state;
    }

    @Override
    @NonNull
    default T getValue(@NonNull ConnectionState oldState, @NonNull ConnectionState newState) {
        return withIO(newState.getTwitchIO());
    }

    T withIO(@NonNull TwitchIO io);
}
