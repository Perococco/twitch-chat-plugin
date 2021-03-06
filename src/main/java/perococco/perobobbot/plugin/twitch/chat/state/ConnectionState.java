package perococco.perobobbot.plugin.twitch.chat.state;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.plugin.twitch.chat.TwicthChatNotConnected;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.plugin.twitch.chat.Channel;
import perobobbot.plugin.twitch.chat.TwitchChatState;
import perococco.perobobbot.plugin.twitch.chat.TwitchIO;
import perococco.perobobbot.plugin.twitch.chat.TwitchMessageChannelIO;

import java.util.Optional;

/**
 * The state of the connection.
 */
public interface ConnectionState extends TwitchChatState {

    /**
     * @return a new disconnected state
     */
    @NonNull
    static DisconnectedState disconnected(@NonNull ChatConnectionInfo chatConnectionInfo) {
        return DisconnectedState.create(chatConnectionInfo);
    }

    /**
     * @return the io to communicate to Twitch
     * @throws TwicthChatNotConnected if this is not a connected state
     */
    @NonNull TwitchIO getTwitchIO();

    /**
     * @return true if this is a connected state
     */
    @Override
    boolean isConnected();

    /**
     * @return the user associated to this connection
     * @throws TwicthChatNotConnected if this is not a connected state
     */
    @NonNull String getUserId();

    /**
     * @param channel the channel to find
     * @return an optional containing the {@link TwitchMessageChannelIO} associated to the provided channel, en empty optional if
     * the channel has not been joined
     * @throws TwicthChatNotConnected if this is not a connected state
     */
    @NonNull Optional<TwitchMessageChannelIO> findChannel(@NonNull Channel channel);

    default @NonNull Optional<DisconnectedState> asDisconnectedState(){ return Optional.empty(); }
    default @NonNull Optional<ConnectedState> asConnectedState(){ return Optional.empty(); }
    default @NonNull Optional<ConnectingState> asConnectingState(){ return Optional.empty(); }

    @NonNull ImmutableSet<ChannelInfo> getJoinedChannels();

    boolean hasJoined(@NonNull String channelName);
}
