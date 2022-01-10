package serv;
import answer.ServerAnswer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ServerSocket serverSocket = new ServerSocket(25225);

        Map<String, ServerAnswer> handlersMap = loadHandlers();

        while (true) {
            Socket socketClient = serverSocket.accept();
            new SimpleServer(socketClient, handlersMap).start();
        }
    }

    private static Map<String,ServerAnswer> loadHandlers() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String,ServerAnswer> result = new HashMap<>();

        try (InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (Object command : properties.keySet()) {
                String className = properties.getProperty(command.toString());
                Class<ServerAnswer> serverAnswerClass = (Class<ServerAnswer>) Class.forName(className);
                ServerAnswer serverAnswer = serverAnswerClass.getConstructor().newInstance();
                result.put(command.toString(), serverAnswer);
            }
        }
        return result;
    }
}

class SimpleServer extends Thread {
    private final Socket socketClient;
    private final Map<String, ServerAnswer> handlersMap;

    public SimpleServer (Socket socketClient, Map<String, ServerAnswer> handlersMap) {
        this.socketClient=socketClient;
        this.handlersMap = handlersMap;
    }
    @Override
    public void run() {
        try {
            handleRequest();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
    private void handleRequest() throws Exception {
        try {
            System.out.println("Server start");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            String clientRequest = bufferedReader.readLine();
            System.out.println("Server get message" + clientRequest);

            String answer = "answer from server" + buildResponse(clientRequest);
            sleep(1000);
            bufferedWriter.write(answer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("Server send answer for " +clientRequest);
            bufferedReader.close();
            bufferedWriter.close();
            socketClient.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String buildResponse (String clientRequest) throws Exception {
        ServerAnswer serverAnswer;
        serverAnswer = handlersMap.get(clientRequest);
        if (serverAnswer!= null) {
            return serverAnswer.buildAnswer(clientRequest);
        }
        return "unknown client request";
    }
}