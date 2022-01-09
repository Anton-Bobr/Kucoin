package сurrency;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class CurrencySel {
    public static void main(String[] args) {



        System.out.println("выбирите валюту из списка");
        System.out.print("USD     EUR");
        System.out.println("");
        Scanner scan = new Scanner(System.in);

        String MyCurrency ;
        boolean i = true;

        do  {
            MyCurrency = scan.nextLine();
            if ((MyCurrency.equals("USD")) || (MyCurrency.equals("EUR"))) {  break; }
            else {
                System.out.println("Вы неправильно ввели валюту");
                System.out.print("Введите   USD или EUR");
                System.out.println("");
            }
        } while ( i );

        System.out.println("Вы выбрали " + MyCurrency);
        scan.close();

        CurrensyTipe Curr1 = new CurrensyTipe();
        Curr1.Cur_Abbreviation = "USD";
        Curr1.Cur_Code = 840;
        Curr1.Cur_ID = 145;

        CurrensyTipe Curr2 = new CurrensyTipe();
        Curr2.Cur_Abbreviation = "EUR";
        Curr2.Cur_Code = 987;
        Curr2.Cur_ID = 292;

        CurrensyTipe Curr = new CurrensyTipe();

        if (MyCurrency.equals(Curr1.Cur_Abbreviation)) {
            Curr = Curr1; }
        if (MyCurrency.equals(Curr2.Cur_Abbreviation)) {
            Curr = Curr2; }

        String MyGetUrl = "https://www.nbrb.by/api/exrates/rates/" + Curr.Cur_ID;
        HttpURLConnection connection = null;
        StringBuilder MyAnswer = new StringBuilder();

        try {
            connection = (HttpURLConnection) new URL(MyGetUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.connect(); //Start connect


            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStreamReader inSR = new InputStreamReader (connection.getInputStream());
                BufferedReader MyBufferAnswer = new BufferedReader(inSR);

               // BufferedReader MyBufferAnswer = new BufferedReader((new InputStreamReader(connection.getInputStream())));

                String line;
                while ((line = MyBufferAnswer.readLine()) != null){
                    MyAnswer.append(line);
                    MyAnswer.append("\n");
                }
                System.out.println(MyAnswer.toString());
                inSR.close();
                MyBufferAnswer.close();
            } else {
                System.out.println("No connect" + MyGetUrl);
                System.out.println("Eror" + connection.getResponseCode());
            }
        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        Gson gson = new Gson();
        Curr = gson.fromJson(MyAnswer.toString(), CurrensyTipe.class);
        System.out.println("Официальный курс нацбанка на сегодня 1 " + Curr.Cur_Abbreviation + " составляет " + Curr.Cur_OfficialRate);
        }

    static class   CurrensyTipe {
        String Cur_Abbreviation;
        int Cur_Code;
        int Cur_ID;
        float Cur_OfficialRate;



        }
}



