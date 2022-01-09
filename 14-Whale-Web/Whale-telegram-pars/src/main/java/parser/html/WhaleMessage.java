package parser.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhaleMessage {
    private String messageID;
    private String messageTime;
    private String messageFULL;
    private String symbol;
    private String amount;
    private String amountUsd;

    public WhaleMessage(String messageID, String messageTime, String messageFULL) {
        this.messageID = messageID;
        this.messageTime = messageTime;
        this.messageFULL = messageFULL;
        parsMessageFULL(messageFULL);
    }

    public void parsMessageFULL(String messageFULL) {
        Pattern pattern = Pattern.compile("(\\(.*\\))");
        Matcher matcher = pattern.matcher(messageFULL);
        if (matcher.find()) {
            amountUsd = matcher.group(0).replaceAll("[^0-9]", "");
        } else {
            amountUsd = "UNDEFINED";
        }
        Pattern pattern1 = Pattern.compile("(.*?#)");
        Matcher matcher1 = pattern1.matcher(messageFULL);
        if (matcher1.find()) {
            amount = matcher1.group(0).replaceAll("[^0-9]", "");
        } else {
            amount = "UNDEFINED";
        }
        Pattern pattern2 = Pattern.compile("(#.*?\\()");
        Matcher matcher2 = pattern2.matcher(messageFULL);
        if (matcher2.find()) {
            symbol = matcher2.group(0).replaceAll("[^A-Z&a-z]", "");
        } else {
            symbol = "UNDEFINED";
        }
    }

    public String getSymbol() {return symbol;}
    public String getAmount() {return amount;}
    public String getAmountUsd() {return amountUsd;}
    public String getMessageID() {
        return messageID;
    }
    public String getMessageTime() {
        return messageTime;
    }
    public String getMessageFULL() {
        return messageFULL;
    }
}
