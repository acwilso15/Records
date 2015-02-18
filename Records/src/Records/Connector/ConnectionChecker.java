package Records.Connector;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionChecker.
 *
 * @author Alexander
 */
public class ConnectionChecker {

	/** The Connected. */
	private boolean Connected;

	/**
	 * Instantiates a new connection checker.
	 *
	 * @param http
	 *            the http
	 */
	public ConnectionChecker(String http) {
		try {
			checkIsConnected(new URL(http));
		} catch (MalformedURLException ex) {
			Logger.getLogger(ConnectionChecker.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Check is connected.
	 *
	 * @param url
	 *            the url
	 * @return true, if successful
	 */

	private boolean checkIsConnected(URL url) {
		System.out.println("ConnectionChecker.checkIsConnected("
				+ url.toString() + ")");

		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			if (con.getResponseCode() == 200) {
				System.out.println("Connection established!!");
				Connected = true;
			}
		} catch (Exception ex) {
			System.out.println("No Connection");
			Connected = false;
		}
		return isConnected();
	}

	/**
	 * Checks if is connected.
	 *
	 * @return the Connected
	 */
	public boolean isConnected() {
		return Connected;
	}
}
