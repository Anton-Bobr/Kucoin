package client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String [] clientRequests = {"COINS", "TRANSACTIONS" , "Bad request"};

        for (String request: clientRequests) {
            SimpleClient simpleClient = new SimpleClient(request);
            simpleClient.start();
        }

    }
}

class SimpleClient extends Thread {
    String request;
    public SimpleClient (String request){this.request = request;}

    @Override
    public void run () {
        try {
            System.out.println("Client " + request + " start");
            Socket socket = new Socket("127.0.0.1", 25225);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new  BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write("Hi from client thread " + request);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            System.out.println("Client " + request + " send HI");

            String getAnswer = bufferedReader.readLine();
            System.out.println(getAnswer);

            socket.close();
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}