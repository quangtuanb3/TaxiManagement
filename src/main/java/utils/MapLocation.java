//package utils;
//
//import database.Const;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClients;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//
//public class MapLocation {
//    public static String getLocation(String latitude, String longitude) {
//        String url = Const.MAP_GET_LOCATION + Const.API_KEY
//                + "&location=" + latitude + "," + longitude;
//
//        String result;
//        try {
//            HttpGet httpGet = new HttpGet(url);
//            HttpClient client = HttpClients.createDefault();
//            HttpResponse httpResponse = client.execute(httpGet);
//            System.out.println(url);
//            String content = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
//            JSONParser parser = new JSONParser();
//            JSONObject json = (JSONObject) parser.parse(content);
//
//            result = ((JSONObject)((JSONObject) json.get("results")).get("locations")).get("street").toString();
//
//        } catch (ParseException | IOException e) {
//            throw new RuntimeException(e);
//        }
//        return result;
//    }
//
//    public static void main(String[] args) {
//        String result = getLocation("16.458380293427428", "107.57802382718894");
//        System.out.println(result);
//    }
//}
