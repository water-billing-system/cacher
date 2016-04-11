package graduation.project.api.reader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisOperations {

	Set<HostAndPort> jedisClusterNodes;
	Map<String, String> userProperties;
	 JedisCluster jc;

	public void connect() {

		try {

			jedisClusterNodes = new HashSet<HostAndPort>();
		    jedisClusterNodes.add(new HostAndPort("192.168.1.64", 6381));
		    jedisClusterNodes.add(new HostAndPort("192.168.1.46", 6380));
		    jc = new JedisCluster(jedisClusterNodes);
			System.out.println("Connection to server sucessfully");
			// check whether server is running or not
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

				jc.hmset("user:" + no, userProperties);

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
			jc.del(keys);
		}

	}
	public void getHashSet(JSONArray jsonArray) throws JSONException {

		long startTime = System.currentTimeMillis();
		for (int y = 0; y < jsonArray.length(); y++) {
			String no = jsonArray.getJSONObject(y).getString("NO");
			String keys = "user:" + no;
			jc.hmget(keys,"NO");
			System.out.println(y);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");

	}
}
