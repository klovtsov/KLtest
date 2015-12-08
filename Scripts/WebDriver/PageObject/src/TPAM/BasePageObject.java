package TPAM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePageObject {
	
	protected WebDriver driver;
	
	public BasePageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement findWebElementByXpath (WebDriver driver, String xpath ) {
		
		return driver.findElement(By.xpath(xpath));	
		
	}

	public boolean waitForTextPresent (WebDriver driver, String textRequired, Integer tryNumber) {

		for (int i=1; i<tryNumber; i++) {

			Helper.sleepFor(1000);
			
			if (driver.getPageSource().contains(textRequired)) {
				return true;
			} 
		}	
	
		return false;

	}
		
}
