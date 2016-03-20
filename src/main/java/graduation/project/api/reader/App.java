package graduation.project.api.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

public class App {

	static Jedis redis;

	public static void main(String[] args) throws IOException, JSONException {

		RedisOperations redis = new RedisOperations();
		redis.connect();
		URLConnection url = new URLConnection("http://192.168.1.64:3000/sbs_muhatabs");
		String responseBody = convertStreamToString(url.connect().getInputStream());
		JSONArray jsonArray = new JSONArray(responseBody);
		redis.deleteHashSet(jsonArray);
		redis.addHashSet(jsonArray);

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
