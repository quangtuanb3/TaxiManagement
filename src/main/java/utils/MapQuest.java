package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
// String apiKey = "tHhcvtGjlPCJsOE1HBmjKwL7u1j5wV1V";
// String depart = "28 Nguyễn Tri Phương, Huế, Việt Nam";
// String arrive = "Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam";
public class MapQuest {
    public static void main(String[] args) {
         String apiKey = "tHhcvtGjlPCJsOE1HBmjKwL7u1j5wV1V";
         String depart = "28 Nguyễn Tri Phương, Huế, Việt Nam";
         String arrive = "Sân Bay Phú Bài, Hương Thủy, Thừa Thiên Huế, Việt Nam";
         String fromAddress = depart.replace(" ", "%20").replace(",","%2C");
         String toAddress = arrive.replace(" ", "%20").replace(",","%2C");
        // Construct the API URL
        String apiUrl = "https://www.mapquestapi.com/directions/v2/route" +
                "?key=" + apiKey +
                "&from=" + fromAddress +
                "&to=" + toAddress;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String jsonResponse = response.toString();

                // Process the JSON response here
                // You can parse the JSON and access different fields as needed
                System.out.println(jsonResponse);
            } else {
                System.out.println("Request failed with HTTP error code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}