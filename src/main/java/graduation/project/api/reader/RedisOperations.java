package graduation.project.api.reader;

import org.json.JSONArray;
import org.json.JSONException;

import redis.clients.jedis.Jedis;


public class RedisOperations {

    Jedis jedis;

    public void connect() {

        try {

            jedis = new Jedis("localhost", 22122);
            //System.out.println("Connection to server sucessfully");
            // check whether server is running or not
            System.out.println("Server is running: " + jedis.ping());

        } catch (Exception e) {
            System.out.println("Can not connect to redis");
        }
    }

    public void set(String name, String jsonArray) throws JSONException {

        try {

            jedis.set(name, jsonArray);

        } catch (Exception e) {
            System.out.println("Cannot made redis operation");
        }
    }

    public void get(JSONArray jsonArray) throws JSONException {

        long startTime = System.currentTimeMillis();
        for (int y = 0; y < jsonArray.length(); y++) {
            String no = "user:" + jsonArray.getJSONObject(y).getString("NO");
            System.out.println(y);
            jedis.get(no);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

    }
}
