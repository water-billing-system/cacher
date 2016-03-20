package graduation.project.api.reader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLConnection {

	private URL url;
	private HttpURLConnection request;
	
	URLConnection (String url) throws MalformedURLException {
		
		this.url= new URL(url);
		
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
	
	
	public HttpURLConnection connect() throws IOException {
		
		try {
			request = (HttpURLConnection) url.openConnection();
			request.setRequestMethod("GET");
			request.connect();
			
		} catch (Exception e) {
			System.out.println("Can not connect to the URL");
		}
		return request;
	

	}


}