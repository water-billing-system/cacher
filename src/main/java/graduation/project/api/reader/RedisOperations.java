package graduation.project.api.reader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisOperations {

	Set<HostAndPort> jedisClusterNodes;
	Map<String, String> userProperties;
	JedisCluster jc;

	public void connect() {

		try {

			jedisClusterNodes = new HashSet<HostAndPort>();
		    jedisClusterNodes.add(new HostAndPort("192.168.1.46", 6380));
		    jedisClusterNodes.add(new HostAndPort("192.168.1.46", 6381));
		    jc = new JedisCluster(jedisClusterNodes);
			System.out.println("Connection to server sucessfully");
			// check whether server is running or not
		} catch (Exception e) {
			System.out.println("Can not connect to redis");
		}
	}

	public void set(JSONArray jsonArray) throws JSONException {
		


		userProperties = new HashMap<String, String>();
		String no = "";
		String json = "";
		
		for (int y = 0; y < jsonArray.length(); y++) {
			try {
				json = jsonArray.getJSONObject(y).toString();
				no = "user:" + jsonArray.getJSONObject(y).getString("NO");
			} catch (Exception e) {
				System.out.println("Json object problem!");
			}
			try {

				jc.set(no, json);

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
	public void get(JSONArray jsonArray) throws JSONException {

		long startTime = System.currentTimeMillis();
		for (int y = 0; y < jsonArray.length(); y++) {
			String no = "user:" + jsonArray.getJSONObject(y).getString("NO");
			System.out.println(y);
			jc.get(no);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");

	}
}
