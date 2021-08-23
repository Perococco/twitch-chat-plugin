package perococco.perobobbot.plugin.twitch.chat.actions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.twitch.chat.Channel;
import perobobbot.plugin.twitch.chat.message.from.UserState;
import perobobbot.plugin.twitch.chat.message.to.Join;
import perococco.perobobbot.plugin.twitch.chat.TwitchIO;
import perococco.perobobbot.plugin.twitch.chat.state.mutator.OperatorUsingIO;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class JoinChannel implements OperatorUsingIO<CompletionStage<UserState>> {

    @NonNull
    private final String nick;

    @NonNull
    private final Channel channel;

    @Override
    public @NonNull CompletionStage<UserState> withIO(@NonNull TwitchIO io) {
        return io.sendToChat(new Join(channel))
                 .thenApply(TwitchIO.ReceiptSlip::getAnswer);
    }
}
