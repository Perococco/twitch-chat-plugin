package perococco.perobobbot.twitch;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.plugin.twitch.chat.Badge;
import perococco.perobobbot.plugin.twitch.chat.BadgesParser;
import perococco.perobobbot.plugin.twitch.chat.MapBasedBadges;

import java.util.List;
import java.util.stream.Stream;

public class BadgesParserTest {


    public static Stream<Arguments> singleBadgeSample() {
        return Stream.of(
                arguments("predictions/pink-2", Badge.with("predictions","pink-2")),
                arguments("predictions/blue-1", Badge.with("predictions","blue-1")),
                arguments("subscriber/0,sub-gift-leader/3",
                          Badge.with("subscriber", "0"),
                          Badge.with("sub-gift-leader","3")
                ),
                arguments("subscriber/3,premium/1",
                          Badge.with("subscriber","3"),
                          Badge.with("premium","1")
                ),
                arguments(""),
                arguments("subscriber/9,premium/1", Badge.with("subscriber","9"), Badge.with("premium","1")),
                arguments("subscriber/10,premium/2,sub-gift-leader/10",
                          Badge.with("subscriber","10"),
                          Badge.with("premium","2"),
                          Badge.with("sub-gift-leader","10")
                          )
                );
    }

    @ParameterizedTest(name = "input={0}")
    @MethodSource("singleBadgeSample")
    public void shouldParseTheExactNumberOfBadges(@NonNull String badgesFromTag, @NonNull List<Badge> expectedBadges) {
        final MapBasedBadges parsedBadges = BadgesParser.parse(badgesFromTag);
        Assertions.assertEquals(expectedBadges.size(), parsedBadges.getBadgesByName().size());
    }

    @ParameterizedTest(name = "input={0}")
    @MethodSource("singleBadgeSample")
    public void shouldHaveOnlyTheGivenBadge(@NonNull String badgesFromTag, @NonNull List<Badge> expectedBadges) {
        final MapBasedBadges parsedBadges = BadgesParser.parse(badgesFromTag);

        for (Badge expectedBadge : expectedBadges) {
            Assertions.assertTrue(parsedBadges.findBadge(expectedBadge.getName()).isPresent(),"Should contains badge with name '"+expectedBadge.getName()+"'");
        }
    }

    private static Arguments arguments(@NonNull String input, Badge... expectedBadges) {
        if (expectedBadges == null || expectedBadges.length == 0) {
            return Arguments.arguments(input,List.of());
        }
        return Arguments.arguments(input,List.of(expectedBadges));
    }
}
