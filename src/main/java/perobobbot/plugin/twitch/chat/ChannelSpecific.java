package perobobbot.plugin.twitch.chat;

import lombok.NonNull;

public interface ChannelSpecific {

    @NonNull
    Channel getChannel();
}
