package CallNotificationService;

import java.net.*;
import java.io.*;

import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class CallNotificationService {
	
		
	public static void main(String[] args) throws Exception 
    { 
		
		String TPAM = args[0];
		String certificate = args[1];
		String certPassword = args[2];
		
		System.out.println(TPAM + " " + certificate + " " + certPassword);
		
		String notificationService = "https://" + TPAM + ":9443/available";
				 		
		URL url = new URL(notificationService);
        
		HostnameVerifier HostValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };      
        
        HttpsURLConnection.setDefaultHostnameVerifier(HostValid);
		
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		
		con.setSSLSocketFactory(getFactory(new File(certificate), certPassword));
		        	
		
		BufferedReader intpam = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = intpam.readLine()) != null) {
			response.append(inputLine);
		}
		intpam.close();

		System.out.println(response.toString());

    }
	
	public static SSLSocketFactory getFactory( File pKeyFile, String pKeyPassword ) throws Exception {
		
		
		  KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		  KeyStore keyStore = KeyStore.getInstance("PKCS12");

		  InputStream keyInput = new FileInputStream(pKeyFile);
		  keyStore.load(keyInput, pKeyPassword.toCharArray());
		  keyInput.close();

		  keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());

		  SSLContext context = SSLContext.getInstance("TLS");
		  context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
		  return context.getSocketFactory();
			  
	}

}


