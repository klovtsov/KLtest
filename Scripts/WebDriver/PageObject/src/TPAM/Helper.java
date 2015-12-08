package TPAM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Helper {


	public Helper() {
		// A constructor with no argument 
	}
	

	public static void sleepFor (Integer sleepMillySeconds) {
		
		try {
			Thread.sleep(sleepMillySeconds);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public static Properties readConfiguration(String configFile) {
	 
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 		input = new FileInputStream(configFile);
	 		prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return prop;
   }

}
