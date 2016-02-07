package thecollector.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class for performing web/internet related processing.
 */
public class NetUtil {

	private static Logger logger() { return Logger.getLogger(NetUtil.class.getName()); }
	
	public final static String USER_AGENT = "Mozilla/5.0";

	/**
	 * Return an Http connection given a URL path.
	 * 
	 * @param urlPath - String
	 * 
	 * @return HttpURLConnection - the Http connection
	 */
	public static HttpURLConnection getConnection(String urlPath) {
		URL url;
		HttpURLConnection httpConnection = null;
		try {
			url = new URL(urlPath);
			httpConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			logger().log(Level.SEVERE, "Exception occurred", e);
		}
		
		return httpConnection;
	}
	
	/**
	 * Get the Response Code for a given Http Connection.
	 * 
	 * @param httpConnection - HttpURLConnection
	 * 
	 * @return int - the Response Code
	 */
	public static int getResponseCode(HttpURLConnection httpConnection) {
		int responseCode = 0;

		try {
			httpConnection.setRequestMethod("GET");
			
			// Add request header.
			httpConnection.setRequestProperty("User-Agent", NetUtil.USER_AGENT);

			responseCode = httpConnection.getResponseCode();
			
		} catch (IOException e) {
			logger().log(Level.SEVERE, "Exception occurred", e);
		}

		return responseCode;
	}
	
}
