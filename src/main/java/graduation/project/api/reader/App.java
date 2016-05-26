package graduation.project.api.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;

public class App {

    public static void main(String[] args) throws IOException, JSONException {

        RedisOperations redis = new RedisOperations();
        redis.connect();

        for (int i = 1; i <= 44413; i++) {

            String urlname = "/api/customers/"+i;
            URLConnection url = new
                    URLConnection("http://localhost:9000/api/customers/"+i);
            HttpURLConnection is = url.connect();
            String responseBody = convertStreamToString(is.getInputStream());

            redis.set(urlname, responseBody);
            System.out.println(i);

            for (int x=0; x<7; x=x+3){
                String urlname1="/api/customers/"+i+"/payments/"+x;
                URLConnection url1 = new
                        URLConnection("http://localhost:9000/api/customers/"+i+"/payments/"+x);
                HttpURLConnection is1 = url1.connect();
                String responseBody1 = convertStreamToString(is1.getInputStream());
                redis.set(urlname1, responseBody1);

                String urlname2="/api/customers/"+i+"/debts/"+x;
                URLConnection url2 = new
                        URLConnection("http://localhost:9000/api/customers/"+i+"/debts/"+x);
                HttpURLConnection is2 = url2.connect();
                String responseBody2 = convertStreamToString(is2.getInputStream());
                redis.set(urlname2, responseBody2);
            }
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }
}
