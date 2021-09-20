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
import perococco.perobobbot.plugin.twitch.chat.TwitchChatConnection;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@Log4j2
public class TwitchChatPlatform implements ChatPlatform {

    private final @NonNull Instants instants;

    private final Listeners<TwitchChatListener> listeners = new Listeners<>();

    private final Map<String, ConnectionData> connectionsPerNick = new HashMap<>();

    public TwitchChatPlatform(@NonNull Instants instants) {
        this.instants = instants;
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    @Synchronized
    public @NonNull CompletionStage<ChatConnection> connect(@NonNull ChatConnectionInfo connectionInfo) {
        return connectionsPerNick.computeIfAbsent(connectionInfo.getNick(), n -> new Connector(connectionInfo).connect())
                                 .checkIsForBot(connectionInfo.getBotId())
                                 .getConnection()
                                 .thenApply(c -> c);
    }


    @Override
    @Synchronized
    public void dispose() {
        connectionsPerNick.values().forEach(c -> c.getConnection().whenComplete((r, t) -> {
            if (r != null) {
                r.requestStop();
            }
        }));
        connectionsPerNick.clear();
    }

    @Synchronized
    private void removeConnection(@NonNull String nick) {
        connectionsPerNick.remove(nick);
    }

    @Override
    @Synchronized
    public @NonNull Optional<CompletionStage<ChatConnection>> findConnection(@NonNull ChatConnectionInfo
                                                                                     chatConnectionInfo) {
        return Optional.ofNullable(connectionsPerNick.get(chatConnectionInfo.getNick()))
                       .map(ConnectionData::getConnection)
                       .map(c -> c.thenApply(Function.identity()));
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(TwitchChatListener.wrap(listener));
    }

    private final class ConnectionData {

        private final UUID botId;
        private final String nick;
        @Getter
        private final @NonNull CompletionStage<TwitchChatConnection> connection;

        public ConnectionData(@NonNull ChatConnectionInfo chatConnectionInfo) {
            this.botId = chatConnectionInfo.getBotId();
            this.nick = chatConnectionInfo.getNick();

            this.connection = new TwitchChatConnection(chatConnectionInfo, listeners, instants)
                    .start()
                    .whenComplete((result, error) -> {
                        if (error != null) {
                            LOG.warn("Could not connect to twitch chat for nick {} : {}", nick, error.getMessage());
                            LOG.debug(error);
                            removeConnection(nick);
                        }
                    });
        }

        public boolean isForBot(@NonNull UUID botId) {
            return Objects.equals(botId, this.botId);
        }

        public ConnectionData checkIsForBot(@NonNull UUID botId) {
            if (isForBot(botId)) {
                return this;
            }
            throw new PerobobbotException("Multiple bots try to connect with the same nickname : '" + nick + "'");
        }
    }

    @RequiredArgsConstructor
    private class Connector {

        private final ChatConnectionInfo chatConnectionInfo;

        private ConnectionData connectionData = null;

        private ConnectionData connect() {
            this.retrieveConnectionData();
            if (connectionData == null) {
                return this.createNewConnection();
            } else {
                return this.checkExistingConnection();
            }
        }

        private @NonNull ConnectionData createNewConnection() {
            return new ConnectionData(chatConnectionInfo);
        }

        private @NonNull ConnectionData checkExistingConnection() {
            assert connectionData != null;
            if (connectionData.isForBot(chatConnectionInfo.getBotId())) {
                return connectionData;
            }
            throw new PerobobbotException("Invalid authentication for chat connection");
        }

        private void retrieveConnectionData() {
            this.connectionData = connectionsPerNick.get(chatConnectionInfo.getNick());
        }

    }
}

