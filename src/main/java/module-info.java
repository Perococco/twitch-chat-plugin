import jplugman.api.Plugin;
import perobobbot.plugin.twitch.chat.TwitchChatPlugin;

/**
 * @author perococco
 **/
module perobobbot.twitch.chat {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.chat.core;
    requires perobobbot.chat.advanced;
    requires perobobbot.plugin;
    requires transitive perobobbot.irc;

    requires io.github.bucket4j.core;
    requires org.apache.logging.log4j;
    requires com.google.common;

    requires jplugman.api;
    requires perobobbot.data.service;
    requires perobobbot.command;
    requires perobobbot.http;
    requires perobobbot.messaging;
    requires perobobbot.eventsub;
    requires perobobbot.twitch.client.api;

    provides Plugin with TwitchChatPlugin;

}
