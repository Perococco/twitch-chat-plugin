package perobobbot.plugin.twitch.chat;

import com.google.common.collect.ImmutableSet;
import jplugman.api.*;
import lombok.NonNull;
import perobobbot.lang.Instants;
import perobobbot.plugin.PerobobbotPlugin;

public class TwitchChatPlugin implements Plugin {

    private static final Version VERSION = Version.with("1.0.0");

    @Override
    public @NonNull ImmutableSet<Requirement<?>> getRequirements() {
        return ImmutableSet.of(Requirements.INSTANTS);
    }

    @Override
    public @NonNull Class<?> getServiceClass() {
        return PerobobbotPlugin.class;
    }

    @Override
    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return new TwitchChatPlatformPlugin(serviceProvider.getSingleService(Instants.class));
    }



}
