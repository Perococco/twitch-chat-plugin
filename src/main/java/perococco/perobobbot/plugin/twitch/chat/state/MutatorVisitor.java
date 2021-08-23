package perococco.perobobbot.plugin.twitch.chat.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Mutation;
import perobobbot.plugin.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.plugin.twitch.chat.message.from.MessageFromTwitchAdapter;
import perobobbot.plugin.twitch.chat.message.from.Part;
import perococco.perobobbot.plugin.twitch.chat.state.mutator.ChanelRemover;

@RequiredArgsConstructor
public class MutatorVisitor extends MessageFromTwitchAdapter<Mutation<ConnectionState>> {

    private final @NonNull String userUsedToJoin;

    @NonNull
    @Override
    protected Mutation<ConnectionState> fallbackVisit(@NonNull MessageFromTwitch messageFromTwitch) {
        return s -> s;
    }

    @NonNull
    @Override
    public Mutation<ConnectionState> visit(@NonNull Part part) {
        if (part.getUser().equals(userUsedToJoin)) {
            return new ChanelRemover(part.getChannel());
        } else {
            return Mutation.identity();
        }
    }

}
