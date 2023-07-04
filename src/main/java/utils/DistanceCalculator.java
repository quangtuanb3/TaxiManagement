package utils;

import database.Const;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.charset.StandardCharsets;

public class DistanceCalculator {
    public static double calculateDistance(String depart, String arrive) {
        String fromAddress = depart.replace(" ", "%20").replace(",", "%2C");
        String toAddress = arrive.replace(" ", "%20").replace(",", "%2C");
        // Construct the API URL
        String apiUrl = Const.Map_Web +
                "?key=" + Const.API_KEY +
                "&from=" + fromAddress +
                "&to=" + toAddress;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpClient client = HttpClients.createDefault();
            HttpResponse httpResponse = client.execute(httpGet);

            String content = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(content);

            String result = ((JSONObject) json.get("route")).get("distance").toString();
            return (Double.parseDouble(result) * 1.61);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static double getDistance(String str1, String str2) {
        try {
            String depart = AppUtils.getString(str1);
            String arrive = AppUtils.getString(str2);
            return calculateDistance(depart, arrive);
        } catch (Exception e) {
            System.out.println("Input invalid");
            return getDistance(str1, str2);
        }
    }
}