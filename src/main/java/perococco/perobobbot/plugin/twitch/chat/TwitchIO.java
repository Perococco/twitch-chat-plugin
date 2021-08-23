package perococco.perobobbot.plugin.twitch.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.plugin.twitch.chat.message.to.CommandToTwitch;
import perobobbot.plugin.twitch.chat.message.to.RequestToTwitch;
import perococco.perobobbot.plugin.twitch.chat.actions.TwitchIOAction;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletionStage;

public interface TwitchIO {

    @NonNull CompletionStage<DispatchSlip> sendToChat(@NonNull CommandToTwitch command);

    @NonNull <A> CompletionStage<ReceiptSlip<A>> sendToChat(@NonNull RequestToTwitch<A> request);

    void timeout(Duration duration);


    @NonNull
    default <T> CompletionStage<T> execute(@NonNull TwitchIOAction<T> action) {
        return action.evaluate(this);
    }


    @RequiredArgsConstructor
    @Builder
    class DispatchSlip {

        @NonNull
        @Getter
        private final TwitchIO io;

        @NonNull
        @Getter
        private final CommandToTwitch sentCommand;

        @NonNull
        @Getter
        private final Instant dispatchingTime;

    }

    @RequiredArgsConstructor
    @Builder
    class ReceiptSlip<A> {

        @NonNull
        @Getter
        private final TwitchIO io;

        @NonNull
        @Getter
        private final Instant dispatchingTime;

        @NonNull
        @Getter
        private final Instant receptionTime;

        @NonNull
        @Getter
        private final RequestToTwitch<A> sentRequest;

        @NonNull
        @Getter
        private final A answer;
    }


}
