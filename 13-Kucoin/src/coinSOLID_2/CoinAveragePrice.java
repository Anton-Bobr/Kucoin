package coinSOLID_2;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CoinAveragePrice  {



    public static void main(String[] args) {

        String [] coins = new String [] {"BTC", "ETH" , "XRP", "LUNA", "LTC", "KCS", "BNB", "VET", "DOGE", "BCH"};

        long startTime = System.currentTimeMillis();
        for (int i = 0; i <= (coins.length-1); i++) {
            CoinAveragePrice coinAveragePrice = new CoinAveragePrice(coins[i]);
            coinAveragePrice.sayCoinAveragePrice();
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("Run time in one Thread = " + (finishTime-startTime));
    }

    String coinName;
    double averagePrice;

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
    public String getCoinName() {
        return coinName;
    }
    public double getAveragePrice(){
        return averagePrice;
    }

    public CoinAveragePrice (String coinName) {
        try {
            Coin coin = new Coin(coinName);
            averagePrice = new CalcAverage(coin.getCoinPrice()).getAverage();
            this.coinName = coinName;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    public void sayCoinAveragePrice(){
        System.out.println("Average Price " + coinName + " = " + String.valueOf(averagePrice) +
                "  Thread ID = " + Thread.currentThread().getId());
    }



}
class CalcAverage{

    private double average;

    public CalcAverage (double [][] priceArray) {
        double sumPrice = 0;
        for (int i = 0; i<= (priceArray.length-1) ; i++) {sumPrice += (priceArray [i][0] + priceArray [i][1]) / 2 ;}
        this.average = sumPrice / priceArray.length;
    }

    public double getAverage() { return average; }
}
