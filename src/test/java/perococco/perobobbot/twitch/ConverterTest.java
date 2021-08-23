package perococco.perobobbot.twitch;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.plugin.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.plugin.twitch.chat.message.from.Notice;
import perobobbot.plugin.twitch.chat.message.from.NoticeId;
import perococco.perobobbot.plugin.twitch.chat.TwitchMessageConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

/**
 * @author perococco
 **/
public class ConverterTest {

    private static final TwitchMessageConverter CONVERTER = new TwitchMessageConverter();

    public static String[] chatSample() throws IOException {
        final URL url = ConverterTest.class.getResource("chat_2.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return reader.lines().toArray(String[]::new);
        }
    }

    @ParameterizedTest
    @MethodSource("chatSample")
    public void shouldParseMessageWithoutError(@NonNull String messageAsString) {
        final Optional<MessageFromTwitch> answer = CONVERTER.convert(messageAsString);
        Assertions.assertTrue(answer.isPresent());
    }

    @Test
    public void loginFailureConversion() {
        final var result = CONVERTER.convert(":tmi.twitch.tv NOTICE * :Login authentication failed");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(Notice.class, result.get().getClass());
        Assertions.assertEquals(NoticeId.AUTHENTICATION_FAILED, Notice.class.cast(result.get()).getMsgId());
    }

}
