package perobobbot.plugin.twitch.chat;

import lombok.NonNull;
import perobobbot.plugin.twitch.chat.event.ReceivedMessage;
import perobobbot.plugin.twitch.chat.message.from.MessageFromTwitch;

public interface MessageFromTwitchListener<M extends MessageFromTwitch> {

    void onMessage(@NonNull ReceivedMessage<M> message);

}
