package perococco.perobobbot.plugin.twitch.chat;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.advanced.MessageConverter;
import perobobbot.irc.IRCParser;
import perobobbot.irc.IRCParsing;
import perobobbot.lang.ThrowableTool;
import perobobbot.plugin.twitch.chat.TwitchMarkers;
import perobobbot.plugin.twitch.chat.message.IRCCommand;
import perobobbot.plugin.twitch.chat.message.from.AnswerBuilderHelper;
import perobobbot.plugin.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.plugin.twitch.chat.message.from.UnknownMessageFromTwitch;

import java.util.Optional;

/**
 * @author perococco
 **/
@Log4j2
public class TwitchMessageConverter implements MessageConverter<MessageFromTwitch> {

    @NonNull
    private final IRCParser ircParser;

    public TwitchMessageConverter() {
        this.ircParser = IRCParser.createForPlugin();
    }

    /**
     * Convert a message from the twitch chat to a {@link MessageFromTwitch}
     * @param messageAsString the text message
     * @return the message from Twitch
     */
    @Override
    public @NonNull Optional<MessageFromTwitch> convert(@NonNull String messageAsString) {
        try {
            final IRCParsing ircParsing = ircParser.parse(messageAsString);
            final Optional<IRCCommand> command = IRCCommand.findFromString(ircParsing.getCommand());

            final MessageFromTwitch messageFromTwitch = command.map(cmd -> buildKnownAnswer(cmd, ircParsing))
                                                               .orElseGet(() -> buildUnknownAnswer(ircParsing));

            return Optional.of(messageFromTwitch);
        } catch (Exception e) {
            e.printStackTrace();
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            LOG.warn(TwitchMarkers.TWITCH_CHAT, "Fail to convert message '" + messageAsString + "'", e);
            return Optional.empty();
        }
    }

    @NonNull
    private MessageFromTwitch buildKnownAnswer(@NonNull IRCCommand command, @NonNull IRCParsing ircParsing) {
        final AnswerBuilderHelper helper = new AnswerBuilderHelper(ircParsing);
        return command.buildTwitchAnswer(helper);
    }

    @NonNull
    private MessageFromTwitch buildUnknownAnswer(@NonNull IRCParsing ircParsing) {
        LOG.warn(TwitchMarkers.TWITCH_CHAT, "Unknown command : '{}' for message '{}'", ircParsing.getCommand(), ircParsing.getRawMessage());
        return new UnknownMessageFromTwitch(ircParsing);
    }

}
