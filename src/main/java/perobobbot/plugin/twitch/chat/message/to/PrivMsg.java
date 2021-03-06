package perobobbot.plugin.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.Function1;
import perobobbot.plugin.twitch.chat.Channel;
import perobobbot.plugin.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
public class PrivMsg extends CommandToTwitch {

    @NonNull
    private final Channel channel;

    @NonNull
    private final Function1<? super DispatchContext, ? extends String> messageBuilder;

    public PrivMsg(
            @NonNull Channel channel,
            @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        super(IRCCommand.PRIVMSG);
        this.channel = channel;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        final String message = messageBuilder.apply(dispatchContext);
        PrivMsgValidator.validate(message);
        return "PRIVMSG #"+channel.getName()+" :"+message;
    }
}
