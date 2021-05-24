package coinSOLID;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class KucoinSolid {
    public static void main (String[] agrs) {
                // get Klince for the last 24 hour
        // get everage by this lines
        // print
        // TDD Test driv develop


        StartEndTime myStartEndTime = new StartEndTime();
        System.out.println(Long.toString(myStartEndTime.getStartAt()) + "   " + Long.toString(myStartEndTime.getEndAt()));

        UrlForGet myUrlForGet = new UrlForGet("BTC-USDT", "1hour", myStartEndTime.getStartAt(),myStartEndTime.getEndAt());
        System.out.println(myUrlForGet.getMyURL());

        AnswerFromServer myAnswerFromServer = new AnswerFromServer(myUrlForGet.getMyURL());
        System.out.println(myAnswerFromServer.getAnswer());

        KuсoinJson kuсoinJson = new Gson().fromJson(myAnswerFromServer.getAnswer(),KuсoinJson.class);

        KuсoinInfo kuсoinInfo = new KuсoinInfo();

        kuсoinInfo.sayKuсoinInfo("Avarage kucoin price = ",  String.valueOf(kuсoinInfo.KuсoinInfoMiddleСourse(kuсoinJson.getData())));



    }
}

class StartEndTime {
    private Long endAt;
    private Long startAt;

    public StartEndTime(){
        endAt = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        int deltTimeDefault = 24;
        startAt = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - TimeUnit.HOURS.toSeconds(deltTimeDefault);
    }
    public StartEndTime(int deltTime) {
        endAt = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        startAt = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - TimeUnit.HOURS.toSeconds(deltTime);

    }

    public Long getStartAt(){ return startAt; }
    public Long getEndAt(){ return endAt; }
}

class UrlForGet {
    private String symbol;
    private String type;
    private String myURL;

    public UrlForGet(String symbol, String type, Long startAt, Long endAt){
        myURL = "https://openapi-sandbox.kucoin.com/api/v1/market/candles?type=" + type + "&symbol=" + symbol + "&startAt=" + startAt.toString() + "&endAt=" + endAt.toString();
    }
    public String getMyURL() { return myURL; }
}

class AnswerFromServer  {
    private String answer;

    public AnswerFromServer(String myURL){
        HttpURLConnection connection = null;
        StringBuilder MyAnswer = new StringBuilder();

        try {
            connection = (HttpURLConnection) new URL(myURL).openConnection();
            connection.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(2000);
            connection.connect(); //Start connect


            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStreamReader inSR = new InputStreamReader (connection.getInputStream());
                BufferedReader myBufferedReader = new BufferedReader(inSR);
                String line;
                while ((line = myBufferedReader.readLine()) != null){MyAnswer.append(line);}
                answer = MyAnswer.toString();
                inSR.close();
                myBufferedReader.close();
            }
            else {
                System.out.println("No connect  " + myURL);
                System.out.println("Eror" + connection.getResponseCode());
                System.out.println(connection.getResponseMessage());
                System.out.println(connection.getReadTimeout());
            }
        }
        catch (Throwable cause) {
            cause.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }
    public String getAnswer() { return answer; }

}

class KuсoinJson {

    private double code;
    private double[][] data;

    public KuсoinJson(double code, double[][] data) {
        this.code = code;
        this.data = data;
    }
    public double getCode() { return code;}

    public double[][] getData() { return data; }



}

class KuсoinInfo{
    double kuсoinInfoMiddleСourse;

    public double KuсoinInfoMiddleСourse (double[][] data) {
        double sumAveragePrice = 0;
        for (int i = 0; i <= (data.length - 1); i++) {
            sumAveragePrice += (data[i][1] + data[i][2]) / 2;
        }
            kuсoinInfoMiddleСourse = sumAveragePrice / data.length;
            return kuсoinInfoMiddleСourse;
    }

    public void sayKuсoinInfo (String message ,String some_data) {
        System.out.println(message + " " + some_data);
    }



}