package perobobbot.plugin.twitch.chat.message.from;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.irc.IRCParsing;
import perobobbot.plugin.twitch.chat.Capability;
import perobobbot.plugin.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
@Getter
@ToString
public class CapAck extends KnownMessageFromTwitch {


    @NonNull
    private final ImmutableSet<Capability> capabilities;

    public CapAck(@NonNull IRCParsing ircParsing, @NonNull ImmutableSet<Capability> capabilities) {
        super(ircParsing);
        this.capabilities = capabilities;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.CAP;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static CapAck build(@NonNull AnswerBuilderHelper helper) {
        return new CapAck(helper.getIrcParsing(), helper.capabilities());
    }


}
