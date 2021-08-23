package perococco.perobobbot.plugin.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.perobobbot.plugin.twitch.chat.TwitchMessageChannelIO;
import perococco.perobobbot.plugin.twitch.chat.state.ConnectedState;
import perococco.perobobbot.plugin.twitch.chat.state.ConnectionState;

@RequiredArgsConstructor
public class ChanelAdder implements ConnectedStateMutation {

    @NonNull
    private final TwitchMessageChannelIO twitchChannelIO;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectedState currentValue) {
        return currentValue.withAddedJoinedChannel(twitchChannelIO);
    }

}
