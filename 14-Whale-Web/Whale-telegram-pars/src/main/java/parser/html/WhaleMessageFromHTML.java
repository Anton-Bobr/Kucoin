package parser.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhaleMessageFromHTML {


    private List<WhaleMessage> whaleMessageList= new ArrayList<>();
    public List<WhaleMessage> getWhaleMessageList() {return whaleMessageList;}

    public static void main(String[] args) throws IOException {
        File file = new File("/home/anton/work/Kucoin/14-Whale-Web/Whale-telegram-pars/src/main/resources/whaleDownload/messages50.html");
        WhaleMessageFromHTML whaleMessageFromHTML = new WhaleMessageFromHTML(file);
        System.out.println(whaleMessageFromHTML.whaleMessageList.get(1));
    }

    public  WhaleMessageFromHTML (File file) throws IOException {
        Document document = Jsoup.parse(file,"UTF-8");
        System.out.println(file.exists());
        Elements messageIDelement = document.getElementsByClass("message default clearfix joined");
        for (Element i:messageIDelement) {
            String messageID = i.attr("id");
            String messageTime = i.getElementsByClass("pull_right date details").attr("title");
            String messageFULL = i.getElementsByClass("text").text();
            WhaleMessage whaleMessage  = new WhaleMessage(messageID, messageTime, messageFULL);
            whaleMessageList.add(whaleMessage);
            //System.out.println(whaleMessage);
        }
    }
}
