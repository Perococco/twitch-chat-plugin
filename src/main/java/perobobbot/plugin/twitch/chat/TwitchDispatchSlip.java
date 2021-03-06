package perobobbot.plugin.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.lang.ChannelInfo;
import perobobbot.plugin.twitch.chat.message.to.CommandToTwitch;

import java.time.Instant;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchDispatchSlip implements DispatchSlip {

    @NonNull
    @Getter
    private final ChannelInfo channelInfo;

    @NonNull
    @Getter
    private final CommandToTwitch sentCommand;

    @NonNull
    @Getter
    private final Instant dispatchTime;

}
