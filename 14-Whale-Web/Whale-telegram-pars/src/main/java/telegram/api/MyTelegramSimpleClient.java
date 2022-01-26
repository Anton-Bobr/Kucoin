package telegram.api;

import it.tdlight.client.*;
import it.tdlight.client.AuthenticationData;
import it.tdlight.client.CommandHandler;
import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.client.TDLibSettings;
import it.tdlight.common.Init;
import it.tdlight.common.utils.CantLoadLibrary;
import it.tdlight.jni.TdApi;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import parser.html.WhaleMessage;
import parser.html.WhaleMessageDbWriter;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Example class for TDLight Java
 * <p>
 * The documentation of the TDLight functions can be found here: https://tdlight-team.github.io/tdlight-docs
 */

public class MyTelegramSimpleClient {

    private static SimpleTelegramClient client;

    public static void main(String[] args) throws CantLoadLibrary, InterruptedException {

        Config config = ConfigProvider.getConfig();

        int myApiId = Integer.parseInt(System.getenv().getOrDefault("MY_API_ID",
                config.getValue("telegram.my.api.id", String.class)));
        String myApiHash = System.getenv().getOrDefault("MY_API_HASH",
                config.getValue("telegram.my.api.hash", String.class));
        long myPhone = Long.parseLong(System.getenv().getOrDefault("MY_PHONE",
                config.getValue("telegram.my.api.phone", String.class)));

        // Initialize TDLight native libraries
        Init.start();

        // Obtain the API token
        APIToken apiToken = new APIToken(myApiId, myApiHash);

        // Configure the client
        TDLibSettings settings = TDLibSettings.create(apiToken);

        //TdApi.MessageForwardOriginChannel

        // Create a client
        client = new SimpleTelegramClient(settings);

        // Configure the authentication info
        // AuthenticationData authenticationData = AuthenticationData.consoleLogin();
        AuthenticationData authenticationData = AuthenticationData.user(myPhone);

        // Add an example update handler that prints when the bot is started
        client.addUpdateHandler(TdApi.UpdateAuthorizationState.class, MyTelegramSimpleClient::onUpdateAuthorizationState);

        // Add an example update handler that prints every received message
        client.addUpdateHandler(TdApi.UpdateNewMessage.class, MyTelegramSimpleClient::onUpdateNewMessage);

        // Add an example command handler that stops the bot
        client.addCommandHandler("stop", new StopCommandHandler());

        // Start the client
        client.start(authenticationData);

        // Wait for exit
        client.waitForExit();
    }

    /**
     * Print new messages received via updateNewMessage
     */
    private static void onUpdateNewMessage(TdApi.UpdateNewMessage update) {

        Config config = ConfigProvider.getConfig();
        long idWhaleAlert = Long.parseLong(System.getenv().getOrDefault("ID_WHALE_ALERT",
                config.getValue("telegram.id.Whale.Alert", String.class)));

        // Get the message content
        TdApi.MessageContent messageContent = update.message.content;

        long chatId = update.message.chatId;
        String textWhaleMessage;

        if (Objects.equals(chatId, idWhaleAlert)) {
            if (messageContent instanceof TdApi.MessageText) {
                textWhaleMessage = ((TdApi.MessageText) messageContent).text.text;
                long idWhaleMessage = update.message.id;
                int timeWhaleMessage = update.message.date;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String dateWhaleMessage = simpleDateFormat.format(Long.valueOf(timeWhaleMessage)*1000);

                System.out.println("Only Whale Message = " + textWhaleMessage + "  dateWhaleMessage = " + dateWhaleMessage + "  idWhaleMessage = " + idWhaleMessage);

                WhaleMessage whaleMessage = new WhaleMessage(Objects.toString(idWhaleMessage), dateWhaleMessage , textWhaleMessage);
                WhaleMessageDbWriter whaleMessageDbWriter = new WhaleMessageDbWriter(whaleMessage);

            } else { textWhaleMessage = "NOT TEXT";}
        }
    }

    /**
     * Close the bot if the /stop command is sent by the administrator
     */
    private static class StopCommandHandler implements CommandHandler {

        @Override
        public void onCommand(TdApi.Chat chat, TdApi.MessageSender commandSender, String arguments) {
            // Check if the sender is the admin
            if (isAdmin(commandSender)) {
                // Stop the client
                System.out.println("Received stop command. closing...");
                client.sendClose();
            }
        }
    }

    /**
     * Print the bot status
     */
    private static void onUpdateAuthorizationState(TdApi.UpdateAuthorizationState update) {
        TdApi.AuthorizationState authorizationState = update.authorizationState;
        if (authorizationState instanceof TdApi.AuthorizationStateReady) {
            System.out.println("Logged in");
        } else if (authorizationState instanceof TdApi.AuthorizationStateClosing) {
            System.out.println("Closing...");
        } else if (authorizationState instanceof TdApi.AuthorizationStateClosed) {
            System.out.println("Closed");
        } else if (authorizationState instanceof TdApi.AuthorizationStateLoggingOut) {
            System.out.println("Logging out...");
        }
    }

    /**
     * Check if the command sender is admin
     */
    private static boolean isAdmin(TdApi.MessageSender sender) {
        Config config = ConfigProvider.getConfig();
        int adminId = Integer.parseInt(System.getenv().getOrDefault("ADMIN_ID",
                config.getValue("telegram.admin.id", String.class)));

        TdApi.MessageSender admin = new TdApi.MessageSenderUser(adminId);
        return sender.equals(admin);
    }
}
