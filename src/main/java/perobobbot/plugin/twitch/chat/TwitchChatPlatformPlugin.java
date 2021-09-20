package perobobbot.plugin.twitch.chat;

import jplugman.annotation.Extension;
import jplugman.api.Disposable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.lang.*;
import perobobbot.plugin.ChatPlatformPluginData;
import perobobbot.plugin.PerobobbotPlugin;
import perobobbot.plugin.PerobobbotPluginData;
import perococco.perobobbot.plugin.twitch.chat.TwitchChatConnection;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@Log4j2
@Extension(point = PerobobbotPlugin.class, version = "1.0.0")
public class TwitchChatPlatformPlugin implements PerobobbotPlugin, Disposable {

    @Getter
    private final ChatPlatformPluginData data;


    public TwitchChatPlatformPlugin(@NonNull Instants instants) {
        final var chatPlatform = new TwitchChatPlatform(instants);
        this.data = new ChatPlatformPluginData(chatPlatform);
    }

    @Override
    public @NonNull String getName() {
        return Platform.TWITCH.name();
    }


    @Override
    public void dispose() {
        data.chatPlatform().dispose();
    }

}

