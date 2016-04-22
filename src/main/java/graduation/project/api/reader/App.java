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

		URLConnection url = new
		URLConnection("http://192.168.1.64:3000/bgs_tahaks");
		HttpURLConnection is = url.connect();
		String responseBody = convertStreamToString(is.getInputStream());

		JSONArray jsonArray = new JSONArray(responseBody);
		//redis.deleteHashSet(jsonArray);
		redis.get(jsonArray);
		//redis.set(jsonArray);


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
