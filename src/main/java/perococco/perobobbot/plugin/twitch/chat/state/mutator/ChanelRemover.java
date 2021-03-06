package perococco.perobobbot.plugin.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.twitch.chat.Channel;
import perococco.perobobbot.plugin.twitch.chat.state.ConnectedState;
import perococco.perobobbot.plugin.twitch.chat.state.ConnectionState;

@RequiredArgsConstructor
public class ChanelRemover implements ConnectedStateMutation {

    @NonNull
    private final Channel channelToRemove;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectedState currentValue) {
        return currentValue.withRemovedJoinedChannel(channelToRemove);
    }

}
