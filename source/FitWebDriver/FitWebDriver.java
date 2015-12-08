package FitWebDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FitWebDriver {
	
	
	public static WebDriver driver;
	public WebElement e;
	private static int TimeWait = 30;
	
 	public void open(String url)
 	{
 		System.setProperty("webdriver.firefox.profile", "TPAM");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(TimeWait, TimeUnit.SECONDS);
  //      driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);       
 		driver.get(url);
 	}
 	 		
 	public void stop()
 	{
		try {
			driver.close();
		}
		catch (NullPointerException|UnreachableBrowserException e) {
            System.out.print("Driver instance does not exist");
        }
 	}
 	
 	public void getConfirmation()
 	{
 	 	Alert alert = driver.switchTo().alert();
 	 	alert.accept();
 	 	System.out.println("alert accepted");
 	}
 	
 	public boolean isChecked(String locator)
 	{
 		boolean value = false;
 		boolean elementFound = findElement(locator);
 		if (elementFound)
 			{
 			if (e.isSelected())
 				{
 				value = true;
 			}
 		}
 		return value;
 	}
 	
 	public boolean isEnabled(String locator)
 	{
 		boolean value = false;
 		boolean elementFound = findElement(locator);
 		if (elementFound)
 			{
 			if (e.isEnabled())
 				{
 				value = true;
 			}
 		}
 		return value;
 	}

 	
 	public String getText(String locator)
 	{
 		String text = "";
 		boolean elementFound = findElement(locator);
 		if (elementFound)
 		{
 			text = e.getText();
 		}
 		System.out.println(text);
 		return text;
 	}
 	
//	public void setTimeoutSeconds(String seconds) {
//		timeoutSeconds = seconds;
//		timeoutMilliseconds = timeoutSeconds + "000";
//		driver.manage().timeouts().implicitlyWait(Long.parseLong(timeoutMilliseconds), TimeUnit.MILLISECONDS);
//	}
 	
	public void pause(int milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}
	
	private By defineLocator(String strategy,String path){
		By locator = null;
		switch (strategy.toLowerCase()){
		case "id":
			locator = By.id(path);
		    break;
		case "xpath":
			locator = By.xpath(path);
		    break;
		case "name":
			locator = By.name(path);
		    break;
		case "classname":
			locator = By.className(path);
		    break;
		case "cssselector":
			locator = By.cssSelector(path);
			break;
		case "link":
			locator = By.linkText(path);
			break;
		case "tagname":
			locator = By.tagName(path);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(path);
			break;
		default:
			locator = By.className(path);
			break;
	}
		return locator;
	}
	
	public boolean findElement(String locator) {
		String[] result = locator.split("=", 2);
		String strategy = result[0]; 
		String path = result[1];
		try {
			//e = driver.findElement(defineLocator(strategy,path));
			e = (new WebDriverWait(driver, TimeWait))
                    .until(ExpectedConditions.presenceOfElementLocated(defineLocator(strategy,path)));
			System.out.println("Element '" + path + "' found using '" + strategy +"'.");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
 	
		
	public boolean click(String locator) {
		boolean elementFound = findElement(locator);
		try{
			if (elementFound) {
				e.click();
				System.out.println("Click on: " + locator);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());	
		}
		return elementFound;
	}
	
	public boolean clickOnLink(String locator) {
		try{
			String[] result = locator.split("=", 2);
			String locat = result[1];
			String strlink = "//a[contains(text(), '"+locat+"')]";
			if (findElement("xpath="+strlink)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
				System.out.println("Click on: " + locator);
				return true;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());	
		}
		return false;
	}
	
	public boolean clickOnLink2(String locator) {
		try{
			String[] result = locator.split("=", 2);
			String locat = result[1];
            String strlink = "//a[.='"+locat+"']";
			if (findElement("xpath="+strlink)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
				System.out.println("Click on: " + locator);
				return true;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());	
		}
		return false;
	}

	public boolean type(String locator, String text) {

		boolean elementFound = findElement(locator);
		if (elementFound) {
			    try{
			e.clear();
			e.sendKeys(text);
			System.out.println("Typing: " + text);
				  //Element can be disabled (as example, set password scenario)
				}catch(InvalidElementStateException stateEx){
					System.out.println(stateEx.getMessage());
			}
		}
		return elementFound;
	}
	
	public boolean typeNoClear(String locator, String text) {

		boolean elementFound = findElement(locator);
		if (elementFound) {
			    try{
			e.sendKeys(text);
			System.out.println("Typing: " + text);
				  //Element can be disabled (as example, set password scenario)
				}catch(InvalidElementStateException stateEx){
					System.out.println(stateEx.getMessage());
			}
		}
		return elementFound;
	}
	
	//ib bug here "return true" in end ?
	private boolean isOptionAlreadySelected(String selectLocator, String optionLocator) {
		findElement(selectLocator);
		Select selectobject = new Select(e);
		
		List<WebElement> selectedoptions = selectobject.getAllSelectedOptions();
		for(WebElement option:selectedoptions) {
			findElement(optionLocator);
			if (e == option) {
				return true;
			}
		}
		
		return true;
	}
	
	public boolean select(String selectLocator, String optionLocator) {
		System.out.println("select" + optionLocator);
		String[] result = optionLocator.split("=", 2);
		String label = result[1];
		boolean elementFound = findElement(selectLocator);
		Select selectobject = new Select(e);
		if (elementFound) {
			//&& !isOptionAlreadySelected(selectLocator, optionLocator)
			selectobject.selectByVisibleText(label);
		}
		return elementFound;
	}
	
	public boolean waitForElementPresent(String locator,int specifiedTimeout){
		System.out.println("Timeout is: " + specifiedTimeout);
		Boolean out=false;
		String[] result = locator.split("=", 2);
		String strategy = result[0]; 
		String path = result[1];
		WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);	
		By locat = defineLocator(strategy,path);
		try{
			wait.until(ExpectedConditions.presenceOfElementLocated(locat));
			out=true;
		}catch(TimeoutException timeEx)
	    {
	    	System.out.println("Cannot find a element "+ path + " using strategy "+strategy+ " after "+specifiedTimeout+" sec.");
	    	out = false;
		}
        return out;
	}
	
	public boolean waitForElementPresent(String locator){
		System.out.println("Default timeout");
        return waitForElementPresent(locator,TimeWait);
	}
	
	public boolean mouseMoveOver(String locator) {
		boolean elementFound = findElement(locator);
		try{
			if (elementFound) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e).perform();
				System.out.println("Move to: " + locator);
				Thread.sleep(500);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());	
		}
		return elementFound;
	}
	
	public boolean waitForTextPresent(String text,int specifiedTimeout) throws InterruptedException {
		System.out.println("Timeout is: " + specifiedTimeout);
		Boolean result = true;
		System.out.println("waitForTextPresent " + text);
	    String [] cards = text.split("\\*");
	    for (String card : cards) {
	    	for(int i=1; i<specifiedTimeout; i++) {
	     		Thread.sleep(1000);
				if (!driver.getPageSource().contains(card)) {
					result = false;
				} else {
					result = true;
					break;
				}
			}
	    	
			if (!result) {
				return false;
			}
	    }
	    return true;
	}
	
	public boolean waitForTextPresent(String text) throws InterruptedException {
		System.out.println("Default timeout");
	    return waitForTextPresent(text,TimeWait);
	}

	public String getEval(String Argmnt){
        String result="";
        String[] Argz = Argmnt.split(";;");
        String jsToExecute = Argz[0];
        System.out.println("JavaScript to execute: " + jsToExecute);
        try{
            result = ((JavascriptExecutor) driver).executeScript(jsToExecute).toString();
        }catch(Exception e){
               System.out.println(e.getMessage());
        }
        return result;
  }
	
    public String getValue(String locator)
   {
         String text = "";
         boolean elementFound = findElement(locator);
         if (elementFound)
         {
                text = e.getAttribute("value");
         }
         System.out.println(text);
         return text;
   }
   
    public String getSelectedLabel(String locator)
   {
         String text = "";
         boolean elementFound = findElement(locator);
         
          if (elementFound)
         {
                Select myselect = new Select(e);
                text = myselect.getFirstSelectedOption().getText();
                System.out.println(text);
         }
         return text;
   }

    public boolean waitForResult(String result){
    	Boolean out  = false;
	    try{
	    	WebDriverWait wait = new WebDriverWait(driver, TimeWait);
	    	wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("ResultsDiv"), result));
	    	out=true;
	    }catch(TimeoutException timeEx)
	    {
	    	System.out.println("Cannot find a following text after" + TimeWait + "sec:" + result);
	    	out = false;
	    }
	    return out;
    }//end of waitForResultMethod
	
   public boolean selectWindow(String title) {
       for(String handle : driver.getWindowHandles()){  
           driver.switchTo().window(handle); 
           System.out.println(driver.getTitle());
           if (driver.getTitle().contains(title)) {
           	driver.manage().window().maximize();
           	return true;
           } 
        }
       return false;
   }
   
   public boolean deselect(String selectLocator, String optionLocator) {
       System.out.println("select" + optionLocator);
       String[] result = optionLocator.split("=", 2);
       String label = result[1];
       boolean elementFound = findElement(selectLocator);
       Select selectobject = new Select(e);
       if (elementFound) {
              //&& !isOptionAlreadySelected(selectLocator, optionLocator)
              selectobject.deselectByVisibleText(label);
       }
       return elementFound;
   }//end of deselect

}



