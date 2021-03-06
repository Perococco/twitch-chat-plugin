package perobobbot.plugin.twitch.chat.message.from;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.irc.IRCParsing;
import perobobbot.lang.CastTool;
import perobobbot.plugin.twitch.chat.Channel;
import perobobbot.plugin.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
@Getter
public abstract class HostTarget extends KnownMessageFromTwitch {


    private final int numberOfViewers;

    public HostTarget(@NonNull IRCParsing ircParsing, int numberOfViewers) {
        super(ircParsing);
        this.numberOfViewers = numberOfViewers;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.HOSTTARGET;
    }

    /**
     * @author perococco
     **/
    @Getter
    public static class Start extends HostTarget {

        @NonNull
        private final Channel hostingChannel;

        public Start(
                @NonNull IRCParsing ircParsing,
                int numberOfViewers,
                @NonNull Channel hostingChannel) {
            super(ircParsing, numberOfViewers);
            this.hostingChannel = hostingChannel;
        }

        @Override
        public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
            return visitor.visit(this);
        }

    }

    /**
     * @author perococco
     **/
    @Getter
    public static class Stop extends HostTarget {

        public Stop(@NonNull IRCParsing ircParsing, int numberOfViewers) {
            super(ircParsing, numberOfViewers);
        }

        @Override
        public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
            return visitor.visit(this);
        }
    }


    public static @NonNull HostTarget build(@NonNull AnswerBuilderHelper helper) {
        final String[] parameters = helper.lastParameter().split(" ");
        final int nbViewers = parameters.length<2? -1: CastTool.castToInt(parameters[1], -1);
        if (parameters[0].equals("-")) {
            return new Stop(helper.getIrcParsing(), nbViewers);
        }
        return new Start(helper.getIrcParsing(), nbViewers, Channel.create(parameters[0]));
    }
}
