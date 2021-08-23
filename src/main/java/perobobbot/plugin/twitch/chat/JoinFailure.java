package perobobbot.plugin.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.twitch.chat.message.from.NoticeId;

@RequiredArgsConstructor
public class JoinFailure extends TwitchChatException{

    @NonNull
    private final NoticeId msgId;

    @NonNull
    private final String message;


}
