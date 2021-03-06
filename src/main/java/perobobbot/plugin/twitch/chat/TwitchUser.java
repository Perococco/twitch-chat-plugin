package perobobbot.plugin.twitch.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.lang.*;
import perobobbot.plugin.twitch.chat.message.TagKey;
import perobobbot.plugin.twitch.chat.message.Tags;
import perobobbot.plugin.twitch.chat.message.from.PrivMsgFromTwitch;

import java.awt.*;
import java.util.Optional;

@RequiredArgsConstructor
@EqualsAndHashCode(of = {"userId"})
public class TwitchUser implements ChatUser, TagsAndBadges {

    @NonNull
    @Getter
    private final String userId;

    @NonNull
    @Getter
    private final String userName;

    @NonNull
    @Delegate(types = {Tags.class,Badges.class})
    private final TagsAndBadges tagsAndBadges;

    @Override
    public @NonNull Optional<Color> findUserColor() {
        return tagsAndBadges.flatFindTag(TagKey.COLOR, CastTool::castToColor);
    }

    @Override
    public @NonNull String getHighlightedUserName() {
        return "@"+userName;
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull UserIdentity toUserIdentity() {
        return new TwitchIdentity(userId,userName.toLowerCase(),userName);
    }

    @Override
    public boolean canActAs(@NonNull Role role) {
        if (role == Role.ANY_USER || tagsAndBadges.hasBadge(BadgeName.BROADCASTER)) {
            return true;
        } else if (tagsAndBadges.hasBadge(BadgeName.MODERATOR)) {
            return role.isNotBetterThan(Role.TRUSTED_USER);
        } else if (tagsAndBadges.hasBadge(BadgeName.SUBSCRIBER)) {
            return role.isNotBetterThan(Role.STANDARD_USER);
        }
        return false;
    }

    public static ChatUser createFromPrivateMessage(@NonNull PrivMsgFromTwitch message) {
        final String userId = message.getUser();
        final String userName = message.getTag(TagKey.DISPLAY_NAME, userId);
        return new TwitchUser(userId, userName, message);
    }

}
