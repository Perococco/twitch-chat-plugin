
package perobobbot.plugin.twitch.chat;

import jplugman.api.Requirement;
import perobobbot.chat.core.IO;
import perobobbot.data.service.BankService;
import perobobbot.data.service.BotService;
import perobobbot.lang.*;

public class Requirements {

        public static final Requirement<BotService> BOT_SERVICE = new Requirement<>(BotService.class,1);
        public static final Requirement<Instants> INSTANTS = new Requirement<>(Instants.class,1);
        public static final Requirement<BankService> BANK_SERVICE = new Requirement<>(BankService.class,1);
        public static final Requirement<MessageDispatcher> MESSAGE_DISPATCHER = new Requirement<>(MessageDispatcher.class,1);
        public static final Requirement<IO> IO = new Requirement<>(IO.class,1);
        public static final Requirement<StandardInputProvider> STANDARD_INPUT_PROVIDER = new Requirement<>(StandardInputProvider.class,1);
        public static final Requirement<ApplicationCloser> APPLICATION_CLOSER = new Requirement<>(ApplicationCloser.class,1);

}
