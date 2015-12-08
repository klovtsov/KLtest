package BFERS

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import java.util.regex.Matcher
import java.util.regex.Pattern	

		
System.out.println "BFERs in EDMZ BugTracker:\n";

String fileName = args[0];
File file = new File(fileName);
list = [];
file.eachLine {list << it };
String bferList = "";
theregex = /^ListBFERs: (.*)/;

for (String str: list){			
	
	
	if (str ==~ theregex){
		String str2 = str.replaceAll(theregex, '$1');
		//System.out.println str2;
		bferList+=str2+","
	}
	
}

//System.out.println bferList;

TestBFERs(bferList);

def TestBFERs (String bferList) {

	FirefoxProfile ffprofile = new FirefoxProfile(new File("edmzhq_profile"));
	//ffprofile.setPreference("network.proxy.type", 0);
	
	WebDriver driver = new FirefoxDriver(ffprofile);


	String EDMZAddress = "https://hq01.e-dmzsecurity.com/edmzhq/BFERFilter.asp";
	driver.get(EDMZAddress);
	
	Thread.sleep(5000);
	driver.findElement(By.name("BFERID")).sendKeys(bferList);
	
	new Select(driver.findElement(By.name("StatusIDs"))).deselectByVisibleText("All Open");
	new Select(driver.findElement(By.name("StatusIDs"))).selectByVisibleText("All Statuses");
		
	driver.findElement(By.name("B2")).click();

	Thread.sleep(10000);		

	WebElement tableNumbers = driver.findElement(By.id("tblLineItemLines"));
	List<WebElement> allNumbers = tableNumbers.findElements(By.tagName("tr"));
	
	Integer i = allNumbers.size();
	
	WebElement tableInformation = driver.findElement(By.id("tblLineItemData"));
	List<WebElement> allRows = tableInformation.findElements(By.tagName("tr"));
	
	
	for (Integer j=0; j<=i-1; j++){
		String str = "";
		str+=allNumbers[j].getText() + "\t";
		
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));

		str+=cells[0].getText() + "\t";
		str+=cells[1].getText() + "\t";
		str+=cells[2].getText() + "\t";
		str+=cells[4].getText() + "\t";
		str+=cells[6].getText() + "\t";
		str+=cells[7].getText() + "\t";
		str+=cells[8].getText() + "\t";
		str+=cells[11].getText();

		System.out.println str;

	}
	
	driver.quit();
}


