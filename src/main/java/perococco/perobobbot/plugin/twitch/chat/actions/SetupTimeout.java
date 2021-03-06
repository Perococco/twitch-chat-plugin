package perococco.perobobbot.plugin.twitch.chat.actions;

import lombok.NonNull;
import perobobbot.plugin.twitch.chat.message.to.Ping;
import perococco.perobobbot.plugin.twitch.chat.TwitchIO;
import perococco.perobobbot.plugin.twitch.chat.state.mutator.OperatorUsingIO;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class SetupTimeout implements OperatorUsingIO<CompletionStage<Void>> {

    @NonNull
    public static SetupTimeout create() {
        return Holder.INSTANCE;
    }

    /**
     * Twitch handle command by batch every 10 seconds. We need
     * to take this into account to compute the timeout
     */
    public static final Duration _10_SECONDS = Duration.ofSeconds(10);

    private SetupTimeout() {}

    @Override
    public CompletionStage<Void> withIO(@NonNull TwitchIO io) {
        return io.sendToChat(new Ping())
                 .thenApply(this::evaluateTimeout)
                 .thenAccept(io::timeout);
    }

    private Duration evaluateTimeout(@NonNull TwitchIO.ReceiptSlip<?> receiptSlip) {
        final Duration dialogDuration = Duration.between(receiptSlip.getDispatchingTime(), receiptSlip.getReceptionTime());
        return dialogDuration.multipliedBy(2).plus(Duration.ofSeconds(10));
    }

    private static class Holder {
        public static final SetupTimeout INSTANCE = new SetupTimeout();
    }
}
