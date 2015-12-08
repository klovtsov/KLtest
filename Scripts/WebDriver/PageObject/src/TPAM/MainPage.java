package TPAM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;

public class MainPage extends BasePageObject {
	
	public MainPage(WebDriver driver) {
		super(driver);
	}

	
	@FindBy(id="loginUserName")
	private WebElement loginUserName;

	@FindBy (id="loginPassword")
	private WebElement loginPassword;

	
	public MainPage login(String userName, String userPassword) {
		loginUserName.sendKeys(userName);
		loginPassword.sendKeys(userPassword + Keys.RETURN);
		
		waitForTextPresent(driver, "Recent Activity", 10);
		
		return PageFactory.initElements(driver, MainPage.class);
	}


	public static MainPage navigateToMainPage(WebDriver driver, String URL) {
		
		URL = "https://" + URL + "/tpam/";
		driver.get(URL);
		return PageFactory.initElements(driver, MainPage.class);
	}


	public static ManagedSystem navigateToManagedSystems(WebDriver driver, String URL) {
		
		URL = "https://" + URL + "/tpam/ManageSystems.asp";
		driver.get(URL);
		return PageFactory.initElements(driver, ManagedSystem.class);
	}

}
