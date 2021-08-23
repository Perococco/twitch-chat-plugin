package perobobbot.plugin.twitch.chat;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.Looper;
import perobobbot.plugin.twitch.chat.event.ReceivedMessage;
import perobobbot.plugin.twitch.chat.event.TwitchChatEvent;
import perobobbot.plugin.twitch.chat.message.from.Join;
import perobobbot.plugin.twitch.chat.message.from.Part;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Log4j2
public class ViewerSaver extends Looper implements TwitchChatListener {

    private final @NonNull BlockingDeque<JoinPartInfo> messages = new LinkedBlockingDeque<>();

    private Path viewersFile;

    @Override
    protected void beforeLooping() {
        final var file = Path.of(System.getProperty("user.home")).resolve("twitch_viewers").resolve("viewers.txt");
        try {
            Files.createDirectories(file.getParent());
            viewersFile = file;
        } catch (IOException e) {
            viewersFile = null;
            LOG.error("Could not create viewer file {}",file,e);
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final List<JoinPartInfo> infoList = new ArrayList<>(messages.size()+10);
        messages.drainTo(infoList);

        if (viewersFile != null && !infoList.isEmpty()) {
            try (var writer = Files.newBufferedWriter(viewersFile, StandardCharsets.UTF_8,StandardOpenOption.APPEND, StandardOpenOption.WRITE,StandardOpenOption.CREATE)) {
                for (JoinPartInfo info : infoList) {
                    writer.write(info.toString());
                    writer.newLine();
                }
            }
        }

        sleep(Duration.ofSeconds(1));
        return IterationCommand.CONTINUE;
    }

    @Override
    public void onTwitchChatEvent(@NonNull TwitchChatEvent event) {
        if (event instanceof ReceivedMessage message) {
            final JoinPartInfo info;
            if (message.getMessage() instanceof Join join) {
                info = new JoinPartInfo("+", join.getUser(), join.getChannel().getName(), message.getReceptionTime());
            } else if (message.getMessage() instanceof Part part) {
                info = new JoinPartInfo("-", part.getUser(), part.getChannel().getName(), message.getReceptionTime());
            } else {
                info = null;
            }
            if (info != null) {
                messages.push(info);
            }
        }
    }

    private record JoinPartInfo(@NonNull String header, @NonNull String user,
                                @NonNull String channel, @NonNull Instant instant) {

        @Override
        public String toString() {
            return "%s%-30s  %-30s %s  %d".formatted(header, user, channel, instant, instant.getEpochSecond());
        }
    }
}
