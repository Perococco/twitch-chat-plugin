package perococco.perobobbot.plugin.twitch.chat.state;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.ChannelInfo;
import perobobbot.plugin.twitch.chat.Channel;
import perobobbot.plugin.twitch.chat.TwicthChatNotConnected;
import perococco.perobobbot.plugin.twitch.chat.TwitchIO;
import perococco.perobobbot.plugin.twitch.chat.TwitchMessageChannelIO;

import java.util.Optional;

public abstract class NotConnectedState implements ConnectionState {

    @Override
    public @NonNull TwitchIO getTwitchIO() {
        throw new TwicthChatNotConnected();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public @NonNull String getUserId() {
        throw new TwicthChatNotConnected();
    }

    @Override
    public @NonNull Optional<TwitchMessageChannelIO> findChannel(@NonNull Channel channel) {
        throw new TwicthChatNotConnected();
    }

    @Override
    public @NonNull ImmutableSet<ChannelInfo> getJoinedChannels() {
        return ImmutableSet.of();
    }

    @Override
    public boolean hasJoined(@NonNull String channelName) {
        return false;
    }
}
