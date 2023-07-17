package utils;


import models.Distance;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static utils.AppUtils.round;

public class MapQuest {
    public static Double calculateDistance(String depart, String arrive) {
        String apiUrl = Constant.MAP_GET_DISTANCE +
                "?key=" + Constant.API_KEY +
                "&from=" + encodeURL(depart) +
                "&to=" + encodeURL(arrive);
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpClient client = HttpClients.createDefault();
            HttpResponse httpResponse = client.execute(httpGet);
            String content = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(content);

            String result = ((JSONObject) json.get("route")).get("distance").toString();
            return round(Double.parseDouble(result) * 1.61);

        } catch (Exception e) {
            throw new RuntimeException("Invalid Location. Please try again");
        }
    }

    public static Distance getDistance(String str1, String str2) {
        try {
            String depart = AppUtils.getString(str1);
            String arrive;
            do {
                arrive = AppUtils.getString(str2);
                if (Objects.equals(depart, arrive)) {
                    System.out.println("Please input destination different from depart");
                }
            } while (Objects.equals(depart, arrive));


            Double distance = calculateDistance(depart, arrive);
            return new Distance(depart, arrive, distance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return getDistance(str1, str2);
        }
    }

    public static String getAddress(String label) {
        String addressToValidate = AppUtils.getString(label);
        String requestUrl = String.format(
                Constant.MAP_GET_ADDRESS,
                Constant.API_KEY,
                encodeURL(addressToValidate));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(requestUrl);
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(content);


                JSONObject resultsObject = (JSONObject) ((JSONArray) json.get("results")).get(0);
                JSONArray locationsArray = (JSONArray) resultsObject.get("locations");
                JSONObject firstLocation = (JSONObject) locationsArray.get(0);
                String adminArea5 = (String) firstLocation.get("adminArea5");

                if (Objects.equals(adminArea5, "")) {
                    throw new RuntimeException("Invalid address. Please input again!");
                }
                return addressToValidate;
            } else {
                throw new RuntimeException("Invalid address. Please input again!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            getAddress(label);
        }
        return addressToValidate;
    }

    public static String encodeURL(String address) {
        return address.replace(" ", "%20").replace(",", "%2C");
    }
}

