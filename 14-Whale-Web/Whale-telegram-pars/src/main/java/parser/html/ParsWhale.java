package parser.html;

import psql.WriterReaderPsql;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class ParsWhale {
    public static void main(String[] args) throws IOException {

        File file = new File("/home/anton/work/Kucoin/14-Whale-Web/Whale-telegram-pars/src/main/resources/whaleDownload/messages50.html");

        //System.out.println(file.isFile());

        WhaleMessageFromHTML whaleMessageFromHTML = new WhaleMessageFromHTML(file);
        System.out.println(whaleMessageFromHTML.getWhaleMessageList().size());
        for (WhaleMessage whaleMessage:
                whaleMessageFromHTML.getWhaleMessageList()) {
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
                    whaleMessage.getMessageFULL() + "') " +
                    "ON CONFLICT (id) DO NOTHING;";

            //System.out.println(sqlRequest1);
            //System.out.println(sqlRequest2);


            WriterReaderPsql writerPsql = new WriterReaderPsql();
            try {
                writerPsql.setDbRequest("SET datestyle = 'ISO, DMY';");
                writerPsql.setDbRequest(sqlRequest1);
                writerPsql.setDbRequest(sqlRequest2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }
}
