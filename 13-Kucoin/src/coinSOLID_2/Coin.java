package coinSOLID_2;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Coin {

    private String coinCourceName;
    private double [] [] coinPrice;

    public Coin (String coinName) {
        int deltTime = 24;
        Long endAt = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        Long startAt = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - TimeUnit.HOURS.toSeconds(deltTime);
        String type = "1hour";
        String symbol = coinName + "-USDT";
        String myURL = "https://api.kucoin.com/api/v1/market/candles?type=" + type + "&symbol=" + symbol + "&startAt=" + startAt.toString() + "&endAt=" + endAt.toString();

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
                BufferedReader MyBufferAnswer = new BufferedReader(inSR);
                String line;
                while ((line = MyBufferAnswer.readLine()) != null){MyAnswer.append(line);}
                //System.out.println(MyAnswer.toString());
                inSR.close();
                MyBufferAnswer.close();
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
                Gson gson = new Gson();
        CoinPars coinPars = new CoinPars();
        coinPars = gson.fromJson(MyAnswer.toString(), CoinPars.class);

        double coinPrice [][]= new double[coinPars.data.length][2];
        for (int i = 0; i <= (coinPars.data.length -1); i++) {
            coinPrice[i][0] = coinPars.data[i][1];
            coinPrice[i][1] = coinPars.data[i][2];
        }
        this.coinCourceName = coinName + "-USDT";
        this.coinPrice = coinPrice;
    }

    public String getCoinCourceName() {return coinCourceName;}

    public double[][] getCoinPrice() {return coinPrice;}

    private class CoinPars {
        double code;
        double [] [] data;
        String msg;
    }
}
