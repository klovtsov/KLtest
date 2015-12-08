//Released under the terms of the GNU GPL v2
//SeleNesse is maintained by Marisa Seal and Chris McMahon
//Portions of SeleNesse based on code originally written by Gojko Adzic http://gojko.net

package TPAM;

import org.openqa.selenium.server.*;

//import com.tests.Keyboard;

import java.io.*;
import java.util.*;
//import java.util.regex.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.sikuli.script.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.*;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Sikuli {
	private static Screen s;
	private static boolean IS_INSTANCE_AVAILABLE = false;

	public static Screen getScreen() {
		if (IS_INSTANCE_AVAILABLE == true) {
			return s;
		} else {
			s = new Screen();
			IS_INSTANCE_AVAILABLE = true;
			return s;
		}
	}

}

class StreamGobbler2 extends Thread {
	InputStream is;
	String type;
	OutputStream os;

	StreamGobbler2(InputStream is, String type) {
		this(is, type, null);
	}

	StreamGobbler2(InputStream is, String type, OutputStream redirect) {
		this.is = is;
		this.type = type;
		this.os = redirect;
	}

	public void run() {

		try {
			PrintWriter pw = null;
			if (os != null)
				pw = new PrintWriter(os);

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (pw != null)
					pw.println(line);
				tpam.outres += line + "\n";
				System.out.println(type + ">" + line);
			}
			if (pw != null)
				pw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
}

public class tpam {
	private Keyboard Keys;
	String Scripts;
	String Platforms;
	String APIKeys;
	String Images;
	String cwd;
	static String protocol = "pop3";
	static String host = null;
	static String user = null;
	static String password = null;
	static String mbox = "INBOX";
	static String url = null;
	static String outres;
	String Log = "";

	static boolean expunge = true;
	static Date from = null;

	public SeleniumServer seleniumServer;

	public tpam(String Args) throws Exception {

		this.Scripts = "\\Scripts\\";
		this.Platforms = Scripts + "Platforms\\";
		this.APIKeys = Scripts + "keys\\";
		this.Images = this.Scripts + "Images\\";
		this.cwd = new File(".").getCanonicalPath();
		this.Keys = new Keyboard();
			
		String[] Argz = Args.split(";;");
		String Launcher = Argz[0];
		int Port = Integer.parseInt(Argz[1]);
		
		boolean WebDriverUsed = false;
		if (Argz.length ==3) {
			WebDriverUsed = true;
		}
		
		// PrintCurrentProxy();
		
		if (!WebDriverUsed) {
		
			System.out.println("Starting selenium server.");
			RemoteControlConfiguration rcc = new RemoteControlConfiguration();
			if (Launcher.toLowerCase().contains("firefox")) {
				System.out.println("Using firefox launcher");
				String cwd = new File(".").getCanonicalPath();
				File ffp = new File(cwd + "\\ff4s_profile");
				if (!ffp.exists()) {
					System.out.println("Creating firefox profile.");
					String argsstr = "createffprofile.vbs;" + cwd + ";fake";
					runBatch(argsstr);
				}
	
				rcc.setFirefoxProfileTemplate(ffp);
			} else {
				System.out.println("Using non-firefox launcher");
				System.out.println("Trust all certificates.");
				rcc.setTrustAllSSLCertificates(true);
				rcc.trustAllSSLCertificates();
				// rcc.reuseBrowserSessions();
				// rcc.setAvoidProxy(true);
			}
			rcc.setPort(Port);
			//
			// rcc.reuseBrowserSessions();
	
			// if (proxy.isEmpty())
			// {
			// System.out.println("Running Selenium server in Avoid Proxy Mode.");
			// rcc.setAvoidProxy(true);
	
			// }
			// else
			// {
	
			// System.setProperty("http.proxyHost", "proxy.sitelocal");
			// System.setProperty("http.proxyPort", "8080");
			// System.setProperty("http.proxyUser", "prod\\svc-sqb-osqabuilder");
			// System.setProperty("http.proxyPassword", "q`w1e2r3osqa");
			// System.setProperty("http.nonProxyHosts","10.30.44.210");
			// System.setProperty("java.net.useSystemProxies","true");
			// System.out.println("no proxy for: " +
			// System.getProperty("http.nonProxyHosts"));
			// System.out.println("Running Selenium server in Proxy Mode.");
			// }
	
			seleniumServer = new SeleniumServer(rcc);
			seleniumServer.start();
			rcc = null;
			
		} // if (!WebDriverUsed)
	}

	public void keytype(CharSequence c) throws Exception {
		System.out.println("Typing: " + c);
		this.Keys.keytype(c);
	}

	public boolean SClick(String Arg) throws Exception {

		Boolean Result = true;
		String cwd = new File(".").getCanonicalPath();
		String Path = cwd + this.Images + Arg;
		System.out.println("Sikuli click: " + Path);
		// Screen Sikuli = new Screen();
		Screen S = Sikuli.getScreen();
		try {
			S.click(Path, 0);
		} catch (FindFailed e) {
			System.out.println(e.toString());
			Result = false;
		}
		return Result;
	}
	
	
	public boolean SClickMultiple(String Args) throws Exception {

		String[] Argz = Args.split(";");
		
		Boolean Result = true;
		
		System.out.println("SClickMultiple in action...");
				
		String cwd = new File(".").getCanonicalPath();
		
		for (int i = 0; i < Argz.length; i++) {
		
			Result = true;
			
			String Path = cwd + this.Images + Argz[i];
			System.out.println("Sikuli click: " + Path);
			
			Screen S = Sikuli.getScreen();
			
			try {
				S.click(Path, 0);
			} catch (FindFailed e) {
				System.out.println(e.toString());
				Result = false;
			}
			
			if (Result) {
				return Result;    // true
			}
		}
		
		return Result;            // false
	}
	


	public boolean SWait(String Arg) throws Exception {

		Boolean Result = true;
		String cwd = new File(".").getCanonicalPath();
		String Path = cwd + this.Images + Arg;
		System.out.println("Sikuli wait: " + Path);
		// Screen Sikuli = new Screen();
		Screen S = Sikuli.getScreen();
		try {
			S.wait(Path, 60);
		} catch (FindFailed e) {
			System.out.println(e.toString());
			Result = false;
		}
		return Result;
	}
	
	
	public boolean SWaitMultiple(String Args) throws Exception {   // V1. Waiting each image for 30 sec, then waiting the next one and so forth
		
		
		String[] Argz = Args.split(";");
		
		Boolean Result = true;
		
		System.out.println("SWaitMultiple in action...");
				
		String cwd = new File(".").getCanonicalPath();
		
		for (int i = 0; i < Argz.length; i++) {
		
			Result = true;
			
			String Path = cwd + this.Images + Argz[i];
			System.out.println("Sikuli wait: " + Path);
			
			Screen S = Sikuli.getScreen();
						
			try {
				S.wait(Path, 30);
			} catch (FindFailed e) {
				System.out.println(e.toString());
				Result = false;
			}
			
			if (Result) {
				return Result;    // true
			}
		}
		
		return Result;            // false
	}
	

	public boolean SWaitMultipleV2(String Args) throws Exception {   // V2. Waiting each image in cycle (short iterations) within WaitTotal
		
		String[] Argz = Args.split(";");   // the first parameter is WaitTotal; all the others are images

		int WaitTotal = Integer.parseInt(Argz[0]);  // indicates how many seconds we are going to wait these images in total. 

		int NumberOfImages = Argz.length - 1;

		int WaitImage = 5;        // how many seconds we are going to wait each image in every iteration. 
		
		int HowMuchLoop = (int) WaitTotal/(NumberOfImages*WaitImage);

		Boolean Result = true;
				
		String cwd = new File(".").getCanonicalPath();
		
		System.out.println("SWaitMultipleV2 in action. Parameters: " + Args + ". WaitTotal: no more than " + WaitTotal + " sec.");
		System.out.println("Wait each image in cycle " + WaitImage + " sec for " + HowMuchLoop + " times.");
		
		for (int j = 0; j < HowMuchLoop; j++) {
		
			for (int i = 1; i <= NumberOfImages; i++) {  // starting from the second element of Argz
			
				Result = true;
				
				String Path = cwd + this.Images + Argz[i];
				System.out.println("Sikuli wait: " + Path);
				
				Screen S = Sikuli.getScreen();
							
				try {
					S.wait(Path, WaitImage);
				} catch (FindFailed e) {
					System.out.println(e.toString());
					Result = false;
				}
				
				if (Result) {
					return Result;    // true
				}
			}
		}
		
		return Result;            // false
	}


	public boolean SType(String Arg) throws Exception {

		Boolean Result = true;
		System.out.println("Sikuli type: " + Arg);
		// Screen Sikuli = new Screen();
		Screen S = Sikuli.getScreen();
		try {
			S.type(Arg, 0);
		} catch (FindFailed e) {
			System.out.println(e.toString());
			Result = false;
		}
		return Result;
	}

	public void restart(String type) throws Exception {

		System.out.println("Stopping & starting selenium server.");
		PrintCurrentProxy();
		// System.setProperty("http.proxyHost", "");
		// System.setProperty("http.proxyPort", "");
		// System.setProperty("http.proxyUser", "");
		// System.setProperty("http.proxyPassword", "");
		// System.setProperty("http.nonProxyHosts", "");
		seleniumServer.stop();
		seleniumServer = null;

		RemoteControlConfiguration rcc = new RemoteControlConfiguration();
		rcc.setTrustAllSSLCertificates(true);
		rcc.trustAllSSLCertificates();

		if (type.equals("noproxy")) {
			System.out
					.println("Restaring Selenium server in Avoid Proxy Mode.");
			System.getProperties().put("proxySet", "false");

			// rcc.setAvoidProxy(true);
		} else {
			System.setProperty("http.proxyHost", "proxy.sitelocal");
			System.setProperty("http.proxyPort", "8080");
			System.setProperty("http.proxyUser", "prod\\svc-sqb-osqabuilder");
			System.setProperty("http.proxyPassword", "q`w1e2r3osqa");
			System.setProperty("http.nonProxyHosts", "10.30.44.210");
			System.out.println("Restaring Selenium server in Proxy Mode.");
		}

		seleniumServer = new SeleniumServer(rcc);
		seleniumServer.start();
		rcc = null;

	}

	public void PrintCurrentProxy() {
		String CurPHost = System.getProperty("http.proxyHost");
		String CurPPort = System.getProperty("http.proxyPort");
		String CurPUser = System.getProperty("http.proxyUser");
		String CurPPwd = System.getProperty("http.proxyPassword");
		String CurPNon = System.getProperty("http.nonProxyHosts");
		System.out.println("Current Proxy Host: " + CurPHost);
		System.out.println("Current Proxy Port: " + CurPPort);
		System.out.println("Current Proxy User: " + CurPUser);
		System.out.println("Current Proxy Password: " + CurPPwd);
		System.out.println("Current Proxy nonProxyHosts: " + CurPNon);

	}

	public void finalize() throws Exception {
		System.out.println("Killing selenium server.");
		seleniumServer.stop();
		seleniumServer = null;
	}

	public boolean Verify_Contain_OR_alnum(String Args) throws Exception {
		boolean res = false;
		String[] Argz = Args.split(";;");

		String string1 = Argz[0];
		System.out.println("Searching in BEFORE: " + string1);

		string1 = string1.replaceAll("[^a-zA-Z_0-9 ]", "");
		System.out.println("Searching in AFTER: " + string1);

		for (int i = 1; i < Argz.length; i++) {
			String string2 = Argz[i];
			System.out.println("Searching BEFORE: " + string2);

			string2 = string2.replaceAll("[^a-zA-Z_0-9 ]", "");
			System.out.println("Searching AFTER: " + string2);

			String Expr = ".*" + string2 + ".*";
			System.out.println("Searching for expression: " + Expr);
			if (string1.matches(Expr))
				res = true;
		}
		return res;
	}

	public static String ClearMailbox(String args) {

		String[] argz = args.split(";");

		host = argz[0];
		user = argz[1];
		password = argz[2];

		try {
			// Get a Properties object
			Properties props = System.getProperties();

			// Get a Session object
			javax.mail.Session session = javax.mail.Session.getInstance(props,
					null);

			// Get a Store object
			Store store = null;
			if (url != null) {
				URLName urln = new URLName(url);
				store = session.getStore(urln);
				store.connect();
			} else {
				if (protocol != null)
					store = session.getStore(protocol);
				else
					store = session.getStore();

				// Connect
				if (host != null || user != null || password != null)
					store.connect(host, user, password);
				else
					store.connect();
			}
			// Open source Folder
			Folder folder = store.getFolder(mbox);
			if (folder == null || !folder.exists()) {
				return "Invalid folder: " + mbox;
			}

			folder.open(Folder.READ_WRITE);

			int count = folder.getMessageCount();
			if (count == 0) { // No messages in the source folder
				// Close folder, store and return
				folder.close(false);
				store.close();
				return folder.getName() + " is empty";
			}

			// Get the message objects to copy
			Message[] msgs = folder.getMessages();
			System.out.println("Deleting " + msgs.length + " messages");
			if (msgs.length != 0) {
				folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);

			}
			// Close folders and store
			folder.close(expunge);
			store.close();
			return "Messages deleted: " + count;
		} catch (MessagingException mex) {
			Exception ex = mex;
			do {
				System.out.println(ex.getMessage());
				if (ex instanceof MessagingException)
					ex = ((MessagingException) ex).getNextException();
				else
					ex = null;
			} while (ex != null);
		}
		return "ret";
	}

	public static String CheckMail(String args) {

		String subject = null;
		String from = null;
		String mailbody = null;
		String[] argz = args.split(";");

		host = argz[0];
		user = argz[1];
		password = argz[2];
		from = argz[3];
		subject = argz[4];
		mailbody = argz[5];

		try {

			// Get a Properties object
			Properties props = System.getProperties();

			// Get a Session object
			javax.mail.Session session = javax.mail.Session.getInstance(props,
					null);

			// Get a Store object
			Store store = null;
			if (url != null) {
				URLName urln = new URLName(url);
				store = session.getStore(urln);
				store.connect();
			} else {
				if (protocol != null)
					store = session.getStore(protocol);
				else
					store = session.getStore();

				// Connect
				if (host != null || user != null || password != null)
					store.connect(host, user, password);
				else
					store.connect();
			}

			// Open the Folder

			Folder folder = store.getDefaultFolder();
			if (folder == null) {
				return "Cant find default namespace";
			}

			folder = folder.getFolder(mbox);
			if (folder == null) {
				return "Invalid folder";
			}

			folder.open(Folder.READ_ONLY);
			SearchTerm term = null;
			SearchTerm bodyTerm = null;

			if (subject != null)
				term = new SubjectTerm(subject);
			if (from != null) {
				FromStringTerm fromTerm = new FromStringTerm(from);
				if (term != null) {

					term = new AndTerm(term, fromTerm);
				} else
					term = fromTerm;
			}
			if (mailbody != null) {
				bodyTerm = new BodyTerm(mailbody);
				if (term != null) {

					term = new AndTerm(term, bodyTerm);
				} else
					term = bodyTerm;
			}

			Message[] msgs = folder.search(term);
			System.out.println("FOUND " + msgs.length + " MESSAGES");
			if (msgs.length == 0) { // no match
				folder.close(false);
				store.close();
				return "no messages";

			} else if (msgs.length >= 2) {
				folder.close(false);
				store.close();
				return "too many messages";
			}

			Message m = msgs[0];

			Object o = m.getContent();
			if (o instanceof String) {
				System.out.println("This is a String");
				System.out.println((String) o);
				folder.close(false);
				store.close();
				return (String) o;
			} else if (o instanceof Multipart) {
				folder.close(false);
				store.close();
				return "This is a Multipart";
			} else if (o instanceof InputStream) {
				folder.close(false);
				store.close();
				return "This is just an input stream";
			}

			folder.close(false);

			store.close();

		} catch (Exception ex) {
			System.out.println("Oops, got exception! " + ex.getMessage());
			ex.printStackTrace();

		}

		return "ret";

	}

	public String tpam_timezone(String tz) {

		if (tz.contains("(GMT+13:00) Nuku'alofa")) {
			return "(GMT+13:00) Nuku'alofa";
		}
		if ((tz.contains("(GMT+12:00) Coordinated Universal Time+12"))
				|| (tz.contains("(GMT+12:00) Magadan"))
				|| (tz.equals("(GMT+12:00) Auckland, Wellington"))) {
			return "(GMT+12:00) Auckland, Wellington";
		}
		if (tz.contains("(GMT+12:00) Fiji")) {
			return "(GMT+12:00) Fiji, Kamchatka, Marshall Is.";
		}
		if ((tz.contains("(GMT+11:00) Vladivostok"))
				|| (tz.contains("(GMT+11:00) Solomon Is., New Caledonia"))) {
			return "(GMT+11:00) Magadan, Solomon Is., New Caledonia";
		}
		if ((tz.contains("(GMT+10:00) Brisbane"))
				|| (tz.contains("(GMT+10:00) Yakutsk"))) {
			return "(GMT+10:00) Brisbane";
		}
		if (tz.contains("(GMT+10:00) Canberra, Melbourne, Sydney")) {
			return "(GMT+10:00) Canberra, Melbourne, Sydney";
		}
		if (tz.contains("(GMT+10:00) Guam, Port Moresby")) {
			return "(GMT+10:00) Guam, Port Moresby";
		}
		if (tz.contains("(GMT+10:00) Hobart")) {
			return "(GMT+10:00) Hobart";
		}
		if (tz.contains("(GMT+09:30) Adelaide")) {
			return "(GMT+09:30) Adelaide";
		}
		if (tz.contains("(GMT+09:30) Darwin")) {
			return "(GMT+09:30) Darwin";
		}
		if (tz.contains("(GMT+09:00) Osaka, Sapporo, Tokyo")) {
			return "(GMT+09:00) Osaka, Sapporo, Tokyo";
		}
		if ((tz.contains("(GMT+09:00) Seoul"))
				|| (tz.contains("GMT+09:00) Irkutsk"))) {
			return "(GMT+09:00) Seoul";
		}
		if (tz.contains("(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi")) {
			return "(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi";
		}
		if (tz.contains("(GMT+08:00) Ulaanbataar")) {
			return "(GMT+08:00) Irkutsk, Ulaan Bataar";
		}
		if (tz.contains("(GMT+08:00) Kuala Lumpur, Singapore")) {
			return "(GMT+08:00) Kuala Lumpur, Singapore";
		}
		if (tz.contains("(GMT+08:00) Perth")) {
			return "(GMT+08:00) Perth";
		}
		if ((tz.contains("(GMT+08:00) Taipei"))
				|| (tz.contains("(GMT+08:00) Krasnoyarsk"))) {
			return "(GMT+08:00) Taipei";
		}
		if ((tz.contains("(GMT+07:00) Bangkok, Hanoi, Jakarta"))
				|| (tz.contains("(GMT+07:00) Novosibirsk"))) {
			return "(GMT+07:00) Bangkok, Hanoi, Jakarta";
		}
		if (tz.contains("(GMT+06:30) Yangon (Rangoon)")) {
			return "(GMT+06:30) Yangon (Rangoon)";
		}
		if ((tz.contains("(GMT+06:00) Astana"))
				|| (tz.contains("(GMT+06:00) Ekaterinburg"))
				|| (tz.contains("(GMT+06:00) Dhaka"))) {
			return "(GMT+06:00) Astana, Dhaka";
		}
		if (tz.contains("(GMT+05:45) Kathmandu")) {
			return "(GMT+05:45) Kathmandu";
		}
		if (tz.contains("(GMT+05:30) Chennai, Kolkata, Mumbai, New Delhi")) {
			return "(GMT+05:30) Chennai, Kolkata, Mumbai, New Delhi";
		}
		if (tz.contains("(GMT+05:30) Sri Jayawardenepura")) {
			return "(GMT+05:30) Sri Jayawardenepura";
		}
		if ((tz.contains("(GMT+05:00) Tashkent"))
				|| (tz.contains("(GMT+05:00) Islamabad, Karachi"))) {
			return "(GMT+05:00) Islamabad, Karachi, Tashkent";
		}
		if (tz.contains("(GMT+04:30) Kabul")) {
			return "(GMT+04:30) Kabul";
		}
		if (tz.contains("Abu Dhabi, Muscat")) {
			return "(GMT+04:00) Abu Dhabi, Muscat";
		}
		if (tz.contains("(GMT+04:00) Baku")) {
			return "(GMT+04:00) Baku";
		}
		if ((tz.contains("(GMT+04:00) Caucasus Standard Time"))
				|| (tz.contains("(GMT+04:00) Moscow, St. Petersburg, Volgograd"))
				|| (tz.contains("(GMT+04:00) Tbilisi"))
				|| (tz.contains("(GMT+04:00) Port Louis"))) {
			return "(GMT+04:00) Caucasus Standard Time";
		}
		if (tz.contains("(GMT+04:00) Yerevan")) {
			return "(GMT+04:00) Yerevan";
		}
		if (tz.contains("(GMT+03:30) Tehran")) {
			return "(GMT+03:30) Tehran";
		}
		if (tz.contains("(GMT+03:00) Baghdad")) {
			return "(GMT+03:00) Baghdad";
		}
		if ((tz.contains("(GMT+03:00) Kuwait, Riyadh"))
				|| (tz.contains("(GMT+03:00) Kaliningrad"))) {
			return "(GMT+03:00) Kuwait, Riyadh";
		}
		if (tz.contains("(GMT+03:00) Nairobi")) {
			return "(GMT+03:00) Nairobi";
		}
		if (tz.contains("(GMT+02:00) Amman")) {
			return "(GMT+02:00) Amman";
		}
		if ((tz.contains("(GMT+02:00) Istanbul"))
				|| (tz.contains("(GMT+02:00) Athens, Bucharest"))) {
			return "(GMT+02:00) Athens, Bucharest, Istanbul";
		}
		if (tz.contains("(GMT+02:00) Beirut")) {
			return "(GMT+02:00) Beirut";
		}
		if (tz.contains("(GMT+02:00) Cairo")) {
			return "(GMT+02:00) Cairo";
		}
		if (tz.contains("(GMT+02:00) Harare, Pretoria")) {
			return "(GMT+02:00) Harare, Pretoria";
		}
		if (tz.contains("(GMT+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius")) {
			return "(GMT+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius";
		}
		if ((tz.contains("(GMT+02:00) Jerusalem"))
				|| (tz.contains("(GMT+02:00) Damascus"))) {
			return "(GMT+02:00) Jerusalem";
		}
		if (tz.contains("(GMT+02:00) Minsk")) {
			return "(GMT+02:00) Minsk";
		}
		if (tz.contains("(GMT+02:00) Windhoek")) {
			return "(GMT+02:00) Windhoek";
		}
		if (tz.contains("(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna")) {
			return "(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna";
		}
		if (tz.contains("(GMT+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague")) {
			return "(GMT+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague";
		}
		if (tz.contains("(GMT+01:00) Brussels, Copenhagen, Madrid, Paris")) {
			return "(GMT+01:00) Brussels, Copenhagen, Madrid, Paris";
		}
		if (tz.contains("(GMT+01:00) Sarajevo, Skopje, Warsaw, Zagreb")) {
			return "(GMT+01:00) Sarajevo, Skopje, Warsaw, Zagreb";
		}
		if (tz.contains("(GMT+01:00) West Central Africa")) {
			return "(GMT+01:00) West Central Africa";
		}
		if ((tz.contains("(GMT) Monrovia, Reykjavik"))
				|| (tz.equals("(GMT) Coordinated Universal Time"))) {
			return "(GMT) Casablanca, Monrovia, Reykjavik";
		}
		if ((tz.contains("(GMT) Greenwich Mean Time : Dublin, Edinburgh, Lisbon, London"))
				|| (tz.contains("(GMT) Casablanca"))) {
			return "(GMT) Greenwich Mean Time : Dublin, Edinburgh, Lisbon, London";
		}
		if (tz.contains("(GMT-01:00) Azores")) {
			return "(GMT-01:00) Azores";
		}
		if (tz.contains("(GMT-01:00) Cape Verde Is.")) {
			return "(GMT-01:00) Cape Verde Is.";
		}
		if ((tz.contains("(GMT-02:00) Mid-Atlantic"))
				|| (tz.contains("(GMT-02:00) Coordinated Universal Time-02"))) {
			return "(GMT-02:00) Mid-Atlantic";
		}
		if (tz.contains("(GMT-03:00) Brasilia")) {
			return "(GMT-03:00) Brasilia";
		}
		if ((tz.contains("(GMT-03:00) Cayenne, Fortalezza"))
				|| (tz.contains("(GMT-03:00) Buenos Aires"))) {
			return "(GMT-03:00) Buenos Aires, Georgetown";
		}
		if (tz.contains("(GMT-03:00) Greenland")) {
			return "(GMT-03:00) Greenland";
		}
		if (tz.contains("(GMT-03:00) Montevideo")) {
			return "(GMT-03:00) Montevideo";
		}
		if (tz.contains("(GMT-03:30) Newfoundland")) {
			return "(GMT-03:30) Newfoundland";
		}
		if (tz.contains("(GMT-04:00) Atlantic Time (Canada)")) {
			return "(GMT-04:00) Atlantic Time (Canada)";
		}
		if ((tz.contains("(GMT-04:00) Georgetown, La Paz, Manaus, San Juan"))
				|| (tz.contains("(GMT-04:30) Caracas"))) {
			return "(GMT-04:00) Caracas, La Paz";
		}
		if ((tz.contains("(GMT-04:00) Cuiaba"))
				|| (tz.contains("(GMT-04:00) Asuncion"))) {
			return "(GMT-04:00) Manaus";
		}
		if (tz.contains("(GMT-04:00) Santiago")) {
			return "(GMT-04:00) Santiago";
		}
		if (tz.contains("(GMT-05:00) Bogota, Lima, Quito")) {
			return "(GMT-05:00) Bogota, Lima, Quito, Rio Branco";
		}
		if (tz.contains("(GMT-05:00) Eastern Time (US & Canada)")) {
			return "(GMT-05:00) Eastern Time (US & Canada)";
		}
		if (tz.contains("(GMT-05:00) Indiana (East)")) {
			return "(GMT-05:00) Indiana (East)";
		}
		if (tz.contains("(GMT-06:00) Central America")) {
			return "(GMT-06:00) Central America";
		}
		if (tz.contains("(GMT-06:00) Central Time (US & Canada)")) {
			return "(GMT-06:00) Central Time (US & Canada)";
		}
		if (tz.contains("(GMT-06:00) Guadalajara, Mexico City, Monterrey - New")) {
			return "(GMT-06:00) Guadalajara, Mexico City, Monterrey - New";
		}
		if (tz.contains("(GMT-06:00) Guadalajara, Mexico City, Monterrey - Old")) {
			return "(GMT-06:00) Guadalajara, Mexico City, Monterrey - Old";
		}
		if (tz.contains("(GMT-06:00) Saskatchewan")) {
			return "(GMT-06:00) Saskatchewan";
		}
		if (tz.contains("(GMT-07:00) Arizona")) {
			return "(GMT-07:00) Arizona";
		}
		if (tz.contains("(GMT-07:00) Chihuahua, La Paz, Mazatlan - New")) {
			return "(GMT-07:00) Chihuahua, La Paz, Mazatlan - New";
		}
		if (tz.contains("(GMT-07:00) Chihuahua, La Paz, Mazatlan - Old")) {
			return "(GMT-07:00) Chihuahua, La Paz, Mazatlan - Old";
		}
		if (tz.contains("(GMT-07:00) Mountain Time (US & Canada)")) {
			return "(GMT-07:00) Mountain Time (US & Canada)";
		}
		if (tz.contains("(GMT-08:00) Pacific Time (US & Canada)")) {
			return "(GMT-08:00) Pacific Time (US & Canada)";
		}
		if (tz.contains("(GMT-08:00) Baja California")) {
			return "(GMT-08:00) Tijuana, Baja California";
		}
		if (tz.contains("(GMT-09:00) Alaska")) {
			return "(GMT-09:00) Alaska";
		}
		if (tz.contains("(GMT-10:00) Hawaii")) {
			return "(GMT-10:00) Hawaii";
		}
		if ((tz.contains("(GMT-11:00) Samoa"))
				|| (tz.contains("(GMT-11:00) Coordinated Universal Time-11"))) {
			return "(GMT-11:00) Midway Island, Samoa";
		}
		if (tz.contains("(GMT-12:00) International Date Line West")) {
			return "(GMT-12:00) International Date Line West";
		} else
			return "The timezone was not found; please set the time zone information manually";

	}

	public boolean Verify_Contain_NOT(String Args) throws Exception {
		boolean res = true;
		try {
			String[] Argz = Args.split(";;");
			String Where = Argz[0];
			Where = Where.replaceAll("[\n\r]", "");
			System.out.println("Searching in: " + Where);
			for (int i = 1; i < Argz.length; i++) {
				String Expr = ".*" + Argz[i] + ".*";
				System.out.println("Searching for expression: " + Expr);
				if (Where.matches(Expr))
					res = false;
			}
		} catch (Exception e) {
			System.out.println("Exception occured: " + e.toString());
		}
		return res;
	}

	public boolean Verify_Contain_OR(String Args) throws Exception {
		boolean res = false;
		try {
			String[] Argz = Args.split(";;");
			String Where = Argz[0];
			Where = Where.replaceAll("[\n\r]", "");
			;
			System.out.println("Searching in: " + Where);
			for (int i = 1; i < Argz.length; i++) {
				String Expr = ".*" + Argz[i] + ".*";
				System.out.println("Searching for expression: " + Expr);
				if (Where.matches(Expr))
					res = true;
			}
		} catch (Exception e) {
			System.out.println("Exception occured: " + e.toString());
		}
		return res;
	}

	// A hack for Internet Explorer
	public boolean Verify_Contain_OR_exp(String Args) throws Exception {
		boolean res = false;
		String[] Argz = Args.split(";;");

		String Where = Argz[0];
		System.out.println("Searching in BEFORE: " + Where);

		Where = Where.replaceAll("[\n\r]", "");
		System.out.println("Searching in AFTER: " + Where);

		for (int i = 1; i < Argz.length; i++) {
			String Expr = ".*" + Argz[i] + ".*";
			System.out.println("Searching for expression: " + Expr);
			if (Where.matches(Expr))
				res = true;
		}
		return res;
	}

	public boolean Verify_Contain_AND(String Args) throws Exception {
		boolean res = true;
		String[] Argz = Args.split(";;");
		String Where = Argz[0];
		System.out.println("Searching in: " + Where);
		for (int i = 1; i < Argz.length; i++) {
			String Expr = ".*" + Argz[i] + ".*";
			System.out.println("Searching for expression: " + Expr);
			if (!Where.matches(Expr))
				res = false;
		}
		return res;
	}

	// To get RequestID after addPwdRequest
	public static String get_RequestID(String RequestMessage) throws Exception {
		String[] RequestMessageParts = RequestMessage.split(" "); // RequestID:
																	// 1590 for
																	// password
																	// release
																	// of
																	// KL2K8R2DC/klremote
			return RequestMessageParts[1];
	}

	public static String get_session_RequestID(String RequestMessage)
			throws Exception {
		String[] RequestMessageParts = RequestMessage.split(" ");
		return RequestMessageParts[1];
	}

	// Getting a password
		
	/*public static String get_password(String PasswordXPath) throws Exception {
		String[] PasswordParts = PasswordXPath.split("\\r?\\n");
		return PasswordParts[1].replace("]", "").replace("[", "").trim();
	}
	 */
	
	public static String get_password(String passwordField){
        
        String expr = "part of the password[^\\[]+\\[(.*?)]\\s+A\\s+B";
        Pattern pattern = Pattern.compile(expr);
        Matcher matcher = pattern.matcher(passwordField);
        
        String password = "";
        
        if(matcher.find()){
               password = matcher.group(1);
        }else{
               System.out.println("Failed to extract password.");
        }
        return password;
  }

		
	
	public static String remove_RN(String inputString) throws Exception {

        String clearString = inputString.replaceAll("[\n\r]", "");
        return clearString;
	}

	
	
	public boolean ssh(String Args) throws IOException, InterruptedException {
		boolean result = true;
		String cwd = new File(".").getCanonicalPath();

		String[] Argz = Args.split(";;");

		String SSH_Host = Argz[0];
		String SSH_User = Argz[1];
		String SSH_Pwd = Argz[2];
		String scenario = cwd + this.Platforms + Argz[3];
		int ai = 3;

		FileInputStream fstream = new FileInputStream(scenario);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		List<String> lines = new ArrayList<String>();
		// Read File Line By Line
		System.out.println("Ssh script file contents:\n");
		while ((strLine = br.readLine()) != null) {
			// Parse variables
			while (true) {
				int pi = strLine.indexOf("%");
				if (pi == -1)
					break;
				// We've found an argument
				String NumStr = strLine.substring(pi + 1, pi + 2);
				int Num = Integer.parseInt(NumStr) + ai;
				// replace with a real value
				strLine = strLine.substring(0, pi) + Argz[Num]
						+ strLine.substring(pi + 2);
			}
			lines.add(strLine);
			System.out.println(strLine);
		}
		System.out.println("Executing the script:\n");
		String[] Script = lines.toArray(new String[lines.size()]);

		// Connect to host
		Connection conn = new Connection(SSH_Host);
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(SSH_User,
				SSH_Pwd);
		if (isAuthenticated == false)
			return false;

		// Open Session
		Session sess = conn.openSession();
		sess.requestDumbPTY();
		sess.startShell();
		InputStream stdout = sess.getStdout();
		InputStream stderr = sess.getStderr();
		boolean output = true;

		for (int i = 0; i < Script.length - 1; i++) {
			// Execute command
			String Command = Script[i];
			if (Command.length() == 0)
				continue;

			i++;
			String WaitingFor = Script[i];
			// System.out.println("Executing command: " + Command);
			BufferedWriter b;
			OutputStream os = sess.getStdin();
			b = new BufferedWriter(new OutputStreamWriter(os));
			b.write(Command + "\n");
			// b.newLine();
			b.flush();

			// Analyze output
			// long start = System.currentTimeMillis();
			long timeout = 1000;
			byte[] buffer = new byte[4096];
			boolean found = false;
			int fcount = 60;
			while (!found && fcount > 0) {
				fcount--;
				Thread.sleep(1000);
				// if ((stdout.available() == 0) && (stderr.available() == 0))
				// {
				// int conditions = sess.waitForCondition(
				// ChannelCondition.STDOUT_DATA
				// | ChannelCondition.STDERR_DATA
				// | ChannelCondition.EOF, timeout);
				//
				// if ((conditions & ChannelCondition.TIMEOUT) != 0)
				// {
				// // A timeout has occurred.
				// System.err.println("Timeout while waiting for data from the server.");
				// }
				//
				// if ((conditions & ChannelCondition.EOF) != 0)
				// {
				// // The remote side won't send us further data...
				//
				// if ((conditions & (ChannelCondition.STDOUT_DATA |
				// ChannelCondition.STDERR_DATA)) == 0)
				// {
				// // ... and we have consumed all data in the local
				// // arrival window.
				// System.err.println("Finished receiving all the messages");
				// }
				// }
				//
				// }

				if (output) {
					StringBuffer outBuffer = new StringBuffer();
					StringBuffer errBuffer = new StringBuffer();
					// logger.debug("checking stdout");
					while (stdout.available() > 0) {
						int len = stdout.read(buffer);
						outBuffer.append(new String(buffer, 0, len));
						System.out.println(outBuffer);

						if (outBuffer.indexOf(WaitingFor) != -1) {
							System.out.println("Found: " + WaitingFor);
							found = true;
						}

						// if (len > 0)
						// {// this check is somewhat paranoid
						// System.out.print("Buffer: ");
						// System.out.write(buffer, 0, len);
						// outBuffer.append(new String(buffer, 0, len));
						// // response+= new String(buffer);
						// // String line = stdoutReader.readLine();
						// // response+=line+"\n";
						// // System.out.println("SSHCONNECTION SYSTEM OUT:
						// // "+line);
						// }
					}
					// logger.debug("checking stderr");
					/*
					 * while (stderr.available() > 0) { int len =
					 * stderr.read(buffer); if (len > 0) {// this check is
					 * somewhat paranoid System.err.write(buffer, 0, len);
					 * errBuffer.append(new String(buffer, 0, len)); //
					 * response+= new String(buffer); // String line =
					 * stdoutReader.readLine(); // response+=line+"\n"; //
					 * System.out.println("SSHCONNECTION SYSTEM OUT: // "+line);
					 * } }
					 * 
					 * // System.out.println(outBuffer.toString() + "\n" +
					 * errBuffer.toString());
					 */
				}

			}
		}

		/* Show exit status, if available (otherwise "null") */
		int Stat = 0;
		try {
			Stat = sess.getExitStatus();
		} catch (NullPointerException e) {
			Stat = 0;
		}
		System.out.println("ExitCode: " + Stat);

		/* Close this session */
		sess.close();

		/* Close the connection */
		conn.close();

		if (Stat > 0)
			result = false;
		return result;
	}

	public String CLI(String Args) throws IOException, InterruptedException {

		outres = "";
		
		String cwd = new File(".").getCanonicalPath();
		String plink = cwd + Scripts + "plink.exe";
		
		Args = Args.replace("-i ", "-i " + cwd + APIKeys);
		// System.out.println(Args);
		
		String CallLine = plink + " " + Args;
		System.out.println(CallLine);
		
		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		
		Gobble(proc, "CLI");  
		return outres.trim();
				
	}

	public String CLItoFile(String Args) throws IOException,
			InterruptedException {

		String cwd = new File(".").getCanonicalPath();
		String plink = cwd + Scripts + "plink.exe";
		// String mysplit [] = Args.split(" ");
		// String outFileName = mysplit[4] + ".out";
		Args = Args.replace("-i ", "-i " + cwd + APIKeys);
		// System.out.println(Args);
		String CallLine = plink + " " + Args;
		System.out.println(CallLine);

		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		String lines = "";
		String line = null;
		String outFileName = "cliout.tmp";
		String outFilePath = cwd + "\\" + outFileName;
		File outFile = new File(outFilePath);
		if (outFile.exists()) {
			outFile.delete();
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		FileWriter fw = new FileWriter(outFile);
		PrintWriter pw = new PrintWriter(fw);
		System.out.println("Writing command output to " + outFilePath);
		int i;
		while ((line = input.readLine()) != null) {
			if (CallLine.indexOf("ListUsers") != -1) {
				String splitarray[] = line.split("\t");
				line = splitarray[0];
				for (i = 1; i <= (splitarray.length - 1); i++) {
					if (i == 13) {
						i += 1;
					}
					line += ("\t" + splitarray[i]);
				}
			}
			if (CallLine.indexOf("ListAccounts") != -1) {
				String splitarray[] = line.split("\t");
				line = splitarray[0];
				for (i = 1; i <= (splitarray.length - 1); i++) {
					if (i == 7) {
						i += 1;
					}
					line += ("\t" + splitarray[i]);
				}
			}
			if (CallLine.indexOf("ListGroupMembership") != -1) {
				if (line.indexOf("\t") != -1) {
				String splitarray[] = line.split("\t");
				line = splitarray[0];
				line += ("\t" + splitarray[1]);
				}
			}
			if (CallLine.indexOf("ListAssignedPolicies") != -1) {
				String splitarray[] = line.split("\t");
				line = splitarray[0];
				for (i = 1; i <= (splitarray.length - 1); i++) {
					if (i == 3) {
						i += 1;
					}
					line += ("\t" + splitarray[i]);
				}
			}
			pw.println(line);
			lines += line;

		}
		input.close();
		fw.close();
		// System.out.println(lines);
		return lines;
	}

	public String PerlAPI(String Args) throws IOException, InterruptedException {

		String cwd = new File(".").getCanonicalPath();
		String perlcmd = "perl " + cwd + Scripts + "perlapi.pl";

		Args = Args.replace("-i ", cwd + APIKeys);
		// System.out.println(Args);
		String CallLine = perlcmd + " " + Args;
		System.out.println(CallLine);

		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		String lines = "";
		String line = null;

		BufferedReader input = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		while ((line = input.readLine()) != null) {
			lines += line;
		}
		input.close();
		System.out.println(lines);
		return lines;
	}

	public String PowerShellScript(String Args) throws IOException, InterruptedException {

		String cwd = new File(".").getCanonicalPath();
		String pscmd = "cmd /c powershell.exe -NoLogo -NonInteractive -NoProfile -ExecutionPolicy RemoteSigned " + 
		"-inputformat none -File " + cwd + Scripts + "psScripts\\";
		
		// System.out.println(Args);
		
		String CallLine = pscmd + Args;
		
		System.out.println(CallLine);

		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		String lines = "";
		String line = null;

		BufferedReader input = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		while ((line = input.readLine()) != null) {
			lines += line;
		}
		
		input.close();
		
		proc.getInputStream().close();
		proc.getOutputStream().close();
		
		System.out.println(lines);
		return lines;
	}
	
	
	
	
	public String NetAPI(String Args) throws IOException, InterruptedException {

		String cwd = new File(".").getCanonicalPath();
		String netcmd = cwd + Scripts + "TPAMAPINet.exe";

		Args = Args.replace("-i ", cwd + APIKeys);

		// System.out.println(Args);
		String CallLine = netcmd + " " + Args;
		System.out.println(CallLine);

		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		String lines = "";
		String line = null;

		BufferedReader input = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		while ((line = input.readLine()) != null) {
			lines += line;
		}
		input.close();
		System.out.println(lines);
		return lines;
	}

	public String CppAPI(String Args) throws IOException, InterruptedException {

		String cwd = new File(".").getCanonicalPath();
		String cppcmd = cwd + Scripts + "TPAMAPICpp.exe";

		Args = Args.replace("-i ", cwd + APIKeys);

		// System.out.println(Args);
		String CallLine = cppcmd + " " + Args;
		System.out.println(CallLine);

		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		String lines = "";
		String line = null;

		BufferedReader input = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		while ((line = input.readLine()) != null) {
			lines += line;
		}
		input.close();
		System.out.println(lines);
		return lines;
	}

	// public boolean createUser(String Host, String Admin, String Pwd, String
	// User, String UsrPwd) throws IOException
	public boolean accountAction(String Args) throws IOException,
			InterruptedException
	// public boolean createUser() throws IOException
	{
		try {
			System.out.println("Account Action method started.");
			String[] Argz = Args.split(";;");
			String Platform = Argz[0];
			String CmdLine = Argz[1] + " " + Argz[2] + " ";
			// log file header
			Date now = new Date();
			outres = now.toString() + ": " + Platform + " " + Argz[1];

			for (int i = 3; i < Argz.length; i++) {
				CmdLine += "\"" + Argz[i] + "\" ";
			}

			String cwd = new File(".").getCanonicalPath();
			File folder = new File(cwd + Platforms);
			File[] listOfFiles = folder.listFiles();
			boolean scriptFound = false;
			String SExt = "";
			String[] ExtArr = new String[4];
			ExtArr[0] = ".bat";
			ExtArr[1] = ".js";
			ExtArr[2] = ".cmd";
			ExtArr[3] = ".vbs";

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String FName = listOfFiles[i].getName();

					if (FName.contains(Platform)) {
						for (int j = 0; j < ExtArr.length; j++) {
							if (FName.indexOf(ExtArr[j]) != -1) {
								if (FName.replace(ExtArr[j], "").equals(
										Platform)) {
									SExt = FName;
									scriptFound = true;
									break;
								}
							}

						}
					}
				}
			}
			if (!scriptFound) {
				System.out.println("Script for platform: " + Platform
						+ " wasn't found.");
				return false;
			}
			if (SExt.contains(" "))
				SExt = "\"" + SExt + "\"";
			// adding cscript for command line echo
			String CallLine = "cmd /c ";
			if (SExt.contains(".vbs"))
				CallLine += "cscript.exe " + cwd + Platforms + SExt;
			else
				CallLine += cwd + Platforms + SExt;
			CallLine += " " + CmdLine;
			System.out.println("Running: \"" + CallLine + "\"");
			Runtime run = Runtime.getRuntime();
			Process proc = run.exec(CallLine);
			return Gobble(proc, "accountAction");
		} catch (Exception e) {
			System.out.println("Exception occured: " + e.toString());
			return false;
		}
	}

	public boolean Gobble(Process proc, String whoRun) throws IOException,
			InterruptedException {
		// error stream
		StreamGobbler2 errorGobbler = new StreamGobbler2(proc.getErrorStream(),
				"ERROR");

		// any output?
		StreamGobbler2 outputGobbler = new StreamGobbler2(
				proc.getInputStream(), "OUTPUT");

		// kick them off
		errorGobbler.start();
		outputGobbler.start();

		// any error???
		int exitVal = proc.waitFor();
		System.out.println("ExitValue: " + exitVal);

		/* YK: we don't need tpam.log. Gonna rely on FitNesseRoot\ErrorLogs 
		if (!whoRun.equals("CLI")) {
		
			// Write to the logfile
			String LogPath = cwd + "\\tpam.log";
			String lockFileName = cwd + "\\tpam.lock";

			File Log = new File(LogPath);
			if (!Log.exists())
				Log.createNewFile();

			MyLockCreate(lockFileName);

			while (!Log.canWrite()) {
				System.out.println("File: " + LogPath + " is locked, waiting...");
				Thread.sleep(2000);
			}
			BufferedWriter AppendLog = new BufferedWriter(new FileWriter(LogPath,
					true));
			AppendLog.write(outres);
			AppendLog.close();

			MyLockDelete(lockFileName);
		
		}
		*/
		
		if (exitVal != 0)
			return false;
		return true;
	}

	public String findScript(String Script) throws IOException,
			InterruptedException {
		// System.out.println("Trying to find the script: " + Script);
		String AdditionalPath = "";
		if (Script.contains("\\")) {
			AdditionalPath = Script.substring(0, Script.lastIndexOf("\\"));
			Script = Script.substring(Script.lastIndexOf("\\") + 1);
		}
		System.out.println("Searching the \"" + Script + "\" script here:"
				+ this.cwd + this.Scripts + AdditionalPath);
		File folder = new File(this.cwd + this.Scripts + AdditionalPath);
		File[] listOfFiles = folder.listFiles();
		// boolean scriptFound = false;
		String SExt = "";
		if (!AdditionalPath.isEmpty())
			SExt = AdditionalPath + "\\";
		String[] ExtArr = new String[5];
		ExtArr[0] = ".bat";
		ExtArr[1] = ".js";
		ExtArr[2] = ".cmd";
		ExtArr[3] = ".vbs";
		ExtArr[4] = ".exe";

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String FName = listOfFiles[i].getName();
				for (int j = 0; j < ExtArr.length; j++) {
					if (FName.indexOf(ExtArr[j]) != -1) {
						String FileOnDisk = FName.toLowerCase().replace(
								ExtArr[j], "");
						String FileToCheck = Script.toLowerCase().replace(
								ExtArr[j], "");
						if (FileOnDisk.equals(FileToCheck)) {
							if (FName.contains(" "))
								FName = "\"" + FName + "\"";
							SExt += FName;
							break;
						}
					}

				}

			} // item is file
		}
		return SExt;

	}

	public boolean runBatch(String Args) throws IOException,
			InterruptedException
	// public boolean createUser() throws IOException
	{
		outres = "";
		String[] Argz = Args.split(";");
		String Script = Argz[0];
		String CmdLine = Argz[1] + " " + Argz[2] + " ";
		for (int i = 3; i < Argz.length; i++) {
			CmdLine += "\"" + Argz[i] + "\" ";
		}

		String SExt = findScript(Script);
		if (SExt.isEmpty()) {

			System.out.println("Script for " + Script + " wasn't found.");
			return false;
		}

		String CallLine = "cmd /c " + this.cwd + this.Scripts + SExt;
		CallLine += " " + CmdLine;
		System.out.println(CallLine);
		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		return Gobble(proc, "runBatch");

	}

	public String RunBatchGetOutput(String Args) throws IOException,
			InterruptedException {

		String cwd = new File(".").getCanonicalPath();

		String[] Argz = Args.split(";");
		String Script = Argz[0];
		String CmdLine = Argz[1] + " " + Argz[2] + " ";

		for (int i = 3; i < Argz.length; i++) {
			CmdLine += "\"" + Argz[i] + "\" ";
		}

		String CallLine = "cmd /c " + cwd + "\\Scripts\\" + Script + " "
				+ CmdLine;

		System.out.println(CallLine);

		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);

		String lines = "";
		String line = null;

		BufferedReader input = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		while ((line = input.readLine()) != null) {
			lines += line;
		}
		input.close();
		System.out.println(lines);
		return lines;
	}

	public String RunBatchGetOutputFull(String Args) throws IOException,
			InterruptedException {

		String[] Argz = Args.split(";");
		String Script = Argz[0];
		String CmdLine = "";
		for (int i = 1; i < Argz.length; i++) {
			CmdLine += "\"" + Argz[i] + "\" ";
		}

		String SExt = findScript(Script);
		if (SExt.isEmpty()) {
			return "ERROR: Script for " + Script + " wasn't found.";
		}

		String CallLine = "cmd /c " + this.cwd + this.Scripts + SExt;
		CallLine += " " + CmdLine;
		System.out.println(CallLine);

		outres = "";
		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		Gobble(proc, "runBatchGetOutputFull");
		return outres;
	}

	
	public String GroovyWebDriver(String Args) throws IOException, InterruptedException {

		String[] Argz = Args.split(";");
		String Script = Argz[0];
		String CmdLine = "";
		for (int i = 1; i < Argz.length; i++) {
			CmdLine += "\"" + Argz[i] + "\" ";
		}
		

		String CallLine = "cmd /c " + this.cwd + "\\Scripts\\WebDriver\\groovy_runner.bat ";
		
		CallLine += Script + ".groovy "	+ CmdLine;
		
		System.out.println(CallLine);

		outres = "";
		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		Gobble(proc, "GroovyWebDriver");
		return outres;
	}

	
	
	public void AddLog(String Item) {
		// Log.concat(Item + "\n");
		this.Log += "\n" + Item;
		System.out.println(Item);
	}

	public String compareDump(String strArgs) {
		boolean result = true;
		String[] Argz = strArgs.split(";;");
		System.out.println("Comparing files:");
		System.out.println("\t" + Argz[0]);
		System.out.println("\t" + Argz[1]);
		this.Log = "";
		try {

			BufferedReader buffReader1 = new BufferedReader(new FileReader(
					Argz[0].trim()));
			BufferedReader buffReader2 = new BufferedReader(new FileReader(
					Argz[1].trim()));
			String strRead1;
			String strRead2;
			int errCount = 0;
			int strCount = 1;
			while ((strRead1 = buffReader1.readLine()) != null) {
				if ((strRead2 = buffReader2.readLine()) != null) {

					String[] strArray1 = strRead1.split("\t");
					String[] strArray2 = strRead2.split("\t");
					if (strArray1.length != strArray2.length) {
						AddLog("String "
								+ Integer.toString(strCount)
								+ ": Check columns count!");
						AddLog("Ethalon - "
								+ Integer.toString(strArray1.length)
								+ " columns:\t" + strRead1);
						AddLog("Dump - "
								+ Integer.toString(strArray2.length)
								+ " columns:\t" + strRead2);
						result = false;
						break;
					}
					for (int i = 0; i < strArray1.length; i++) {
						String str1 = strArray1[i].trim();
						String str2 = strArray2[i].trim();
						if (!str1.equalsIgnoreCase(str2)) {
							result = false;
							errCount++;
							AddLog("String "
									+ Integer.toString(strCount) + " column "
									+ Integer.toString(i + 1) + " " + str2
									+ " is not equal " + str1);
						}
					}
					strCount++;

				} else {
					AddLog("Ethalon has more strings than dump!");
					return this.Log;
				}

			}
			if (errCount > 50) {
				AddLog("Too many mismatches; check the ethalon file.");
			}

			if (result) AddLog("Dump and ethalon are equal.");
			buffReader1.close();
			buffReader2.close();
		} catch (FileNotFoundException ex) {
			AddLog("File Not Found: " + ex.getMessage());
		} catch (IOException ex) {
			AddLog("I/O Exception: " + ex.getMessage());
		}
		return this.Log;
	}

	public String FitnesseVarToFile(String Args) throws IOException,
			InterruptedException {

		String[] Argz = Args.split(";;;;;");
		// Argz[0] - variable value, Argz[1] - file name

		String cwd = new File(".").getCanonicalPath();

		String CallLine = "cmd /c echo \"" + Argz[0] + "\" >" + cwd + "\\"
				+ Argz[1];
		System.out.println("CallLine: " + CallLine);

		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(CallLine);
		String lines = "";
		String line = null;

		BufferedReader input = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		while ((line = input.readLine()) != null) {
			lines += line;
		}
		input.close();
		System.out.println(lines);
		return lines;
	}

	public static void MyLockCreate(String lockFileName) throws IOException, InterruptedException
	{
		
		File lockFile = new File(lockFileName);
		
		int tryNumber = 0;
		
		while(lockFile.exists() && (tryNumber < 5)) {
			 System.out.println("Lock exists. Waiting for a while.");
			 Thread.sleep(2000);
			 tryNumber ++;
		}

		try {
		    lockFile.createNewFile();
		} catch (IOException ioe) {
		    System.out.println("Error while creating Lock File: " + ioe);
		}

		System.out.println("Lock file " + lockFile.getPath() + " created. ");

	}
	

	public static void MyLockDelete(String lockFileName) throws IOException, InterruptedException
	{

		File lockFile = new File(lockFileName);
		
		try {
		    lockFile.delete();
		} catch (Exception ioe) {
		     System.out.println("Error while deleting Lock File: " + ioe);
		}

		System.out.println("Lock file " + lockFile.getPath() + " deleted. ");

	}

	
}
