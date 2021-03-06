package perobobbot.plugin.twitch.chat.message.from;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.irc.IRCParsing;
import perobobbot.plugin.twitch.chat.Channel;
import perobobbot.plugin.twitch.chat.ChannelSpecific;
import perobobbot.plugin.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
@Getter
public class Part extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @Builder
    public Part(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull Channel channel) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.PART;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull Part build(@NonNull AnswerBuilderHelper helper) {
        return Part.builder()
                   .ircParsing(helper.getIrcParsing())
                   .channel(helper.channelFromParameterAt(0))
                   .user(helper.userFromPrefix())
                   .build();
    }
}
