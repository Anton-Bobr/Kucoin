package coinSOLID_2;

import java.util.concurrent.*;


public class CoinsPriceInThreads {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        String [] coins = new String [] {"BTC", "ETH" , "XRP", "LUNA", "LTC", "KCS", "BNB", "VET", "DOGE", "BCH"};
        long startTime = System.currentTimeMillis();
        for (int i = 0; i <= (coins.length-1); i++) {
            executorService.submit(new MyRunable(coins[i]));
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);


        long finishTime = System.currentTimeMillis();
        System.out.println("Run time in two Threads = " + (finishTime - startTime));



    }
    static class MyRunable implements Runnable {

        String data;

        public MyRunable (String data) {this.data=data;}
        public String getData() {return data; }

        @Override
        public void run(){
            CoinAveragePrice coinAveragePrice = new CoinAveragePrice(getData());
            coinAveragePrice.sayCoinAveragePrice();
        }
    }
}
