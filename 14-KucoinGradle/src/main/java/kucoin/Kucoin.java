package kucoin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import com.google.gson.*;


public class Kucoin {

    public static void main(String[] args) {
        String symbol = "BTC-USDT";
        int deltTime = 24;
        Long endAt =  TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        Long startAt = endAt - TimeUnit.HOURS.toSeconds(deltTime);
        String type = "1hour";
        String MyUrl = "https://openapi-sandbox.kucoin.com/api/v1/market/candles?type=" + type + "&symbol=" + symbol + "&startAt=" + startAt.toString() + "&endAt=" + endAt.toString();
        System.out.println(MyUrl);

        HttpURLConnection connection = null;
        StringBuilder MyAnswer = new StringBuilder();

        try {
            connection = (HttpURLConnection) new URL(MyUrl).openConnection();
            connection.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(2000);
            connection.connect(); //Start connect


            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStreamReader inSR = new InputStreamReader (connection.getInputStream());
                BufferedReader MyBufferAnswer = new BufferedReader(inSR);
                String line;
                while ((line = MyBufferAnswer.readLine()) != null){MyAnswer.append(line);}
                System.out.println(MyAnswer.toString());
                inSR.close();
                MyBufferAnswer.close();
                }
            else {
                System.out.println("No connect  " + MyUrl);
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

        Gson gson = new Gson();
        KukoinStat kukoinStat = new KukoinStat();
        kukoinStat = gson.fromJson(MyAnswer.toString(), KukoinStat.class);

        double sumAveragePrice = 0;
            for (int i = 0; i <= (kukoinStat.data.length - 1); i++) {
                sumAveragePrice += (kukoinStat.data[i][1] + kukoinStat.data[i][2])/2;
            }


        System.out.println("Avarage kucoin price = " + sumAveragePrice / kukoinStat.data.length);

    }


    public static class KukoinStat {
        double code;
        double [] [] data;
    }

}
