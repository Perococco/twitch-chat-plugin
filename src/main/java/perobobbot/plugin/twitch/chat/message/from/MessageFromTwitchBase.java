package perobobbot.plugin.twitch.chat.message.from;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.irc.IRCParsing;
import perobobbot.plugin.twitch.chat.Badge;
import perobobbot.plugin.twitch.chat.Badges;
import perococco.perobobbot.plugin.twitch.chat.BadgesParser;

import java.util.Optional;

public abstract class MessageFromTwitchBase implements MessageFromTwitch {

    @NonNull
    private final Badges badges;

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    public MessageFromTwitchBase(@NonNull IRCParsing ircParsing) {
        this.ircParsing = ircParsing;
        this.badges = ircParsing.tagValue("badges")
                .<Badges>map(BadgesParser::parse)
                .orElse(EMPTY);
    }

    @Override
    public Optional<String> findTag(@NonNull String tagName) {
        return ircParsing.tagValue(tagName);
    }

    @Override
    public @NonNull Optional<Badge> findBadge(@NonNull String badgeName) {
        return badges.findBadge(badgeName);
    }

}
