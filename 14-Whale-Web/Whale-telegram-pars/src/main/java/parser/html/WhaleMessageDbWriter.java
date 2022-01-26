package parser.html;

import psql.WriterReaderPsql;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class WhaleMessageDbWriter {
    public static void main(String[] args) throws IOException {

        File file = new File("/home/anton/work/Kucoin/14-Whale-Web/Whale-telegram-pars/src/main/resources/whaleDownload/messages55.html");

        WhaleMessageFromHTML whaleMessageFromHTML = new WhaleMessageFromHTML(file);
        for (WhaleMessage whaleMessage:
                whaleMessageFromHTML.getWhaleMessageList()) {
            WhaleMessageDbWriter whaleMessageDbWriter = new WhaleMessageDbWriter(whaleMessage);
        }
    }



    public WhaleMessageDbWriter (WhaleMessage whaleMessage) {
        String sqlRequest1 = "INSERT INTO coins (coin) VALUES ('"
                + whaleMessage.getSymbol() + "') " +
                "ON CONFLICT (coin) DO NOTHING;";
        String sqlRequest2 = "INSERT INTO whale_message " +
                "(id, time, coin, amount, amount_usd, full_message) VALUES " +
                "('" + whaleMessage.getMessageID().replace("message", "") + "' , '" +
                whaleMessage.getMessageTime() + "' , '" +
                whaleMessage.getSymbol() + "' , '" +
                whaleMessage.getAmount() + "' , '" +
                whaleMessage.getAmountUsd() + "' , '" +
                whaleMessage.getMessageFULL() + "');" ;


        String sqlRequest = "SET datestyle = 'ISO, DMY';\n" + sqlRequest1 + "\n" + sqlRequest2;

        WriterReaderPsql writerPsql = new WriterReaderPsql();
        try {
            writerPsql.setDbRequest(sqlRequest);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
