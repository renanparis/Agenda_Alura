package com.paris.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebClient {

    public String post(String json) {
        String addressServer = "https://www.caelum.com.br/mobile";
        return connectToServer(json, addressServer);
    }

    private String connectToServer(String json, String addressServer) {
        try {
            URL url = new URL(addressServer);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            PrintStream outPut = new PrintStream(connection.getOutputStream());
            outPut.println(json);

            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String responseServer = scanner.next();

            return responseServer;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insert(String json) {

        String addressServer = "http://192.168.0.103:8080/api/aluno";
        connectToServer(json, addressServer);


    }
}
