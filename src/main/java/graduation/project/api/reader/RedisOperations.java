package graduation.project.api.reader;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

public class RedisOperations {

	Jedis jedis;
	Map<String, String> userProperties;

	public void connect() {

		try {

			jedis = new Jedis("localhost");
			System.out.println("Connection to server sucessfully");
			// check whether server is running or not
			System.out.println("Server is running: " + jedis.ping());

		} catch (Exception e) {
			System.out.println("Can not connect to redis");
		}
	}

	public void addHashSet(JSONArray jsonArray) throws JSONException {

		userProperties = new HashMap<String, String>();
		String no = "";

		for (int y = 0; y < jsonArray.length(); y++) {

			try {

				JSONObject jsonObject = jsonArray.getJSONObject(y);
				no = jsonArray.getJSONObject(y).getString("NO");

				for (int i = 0; i < jsonObject.names().length(); i++) {

					String key = jsonObject.names().getString(i);
					String value = jsonObject.getString(jsonObject.names().getString(i));

					userProperties.put(key, value);
				}

			} catch (Exception e) {
				System.out.println("Json object problem!");
			}

			try {

				jedis.hmset("user:" + no, userProperties);

			} catch (Exception e) {
				System.out.println("Cannot made redis operation");
			}

		}

	}

	public void deleteHashSet(JSONArray jsonArray) throws JSONException {

		for (int y = 0; y < jsonArray.length(); y++) {
			String no = jsonArray.getJSONObject(y).getString("NO");
			String keys = "user:" + no;
			System.out.println(keys);
			jedis.del(keys);
		}

	}
}
