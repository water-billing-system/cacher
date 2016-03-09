package graduation.project.api.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONArray;
import org.json.JSONException;

public class App 
{
		  
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

		  public static void main(String[] args) throws IOException, JSONException {
		    
			URL url1 = new URL("http://192.168.1.64:3000/bgs_tahaks");
			HttpURLConnection request1 = (HttpURLConnection) url1.openConnection();
	        request1.setRequestMethod("GET");
	        request1.connect();
	        String responseBody = convertStreamToString(request1.getInputStream());
	        JSONArray jsonArray = new JSONArray(responseBody);
	        
	        for (int i = 0; i < jsonArray.length(); i++) {
	            String email = jsonArray.getJSONObject(i).getString("NO");
	            System.out.println(email);
	        }
			//JSONObject json = readJsonFromUrl("http://192.168.1.64:3000/bgs_tahaks/80712080");
		    //System.out.println(json.toString());
		    //System.out.println(json.get("NO"));
		  }
}
