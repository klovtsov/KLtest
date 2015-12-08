package TPAM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;

public class ManagedSystem extends BasePageObject {

	public ManagedSystem(WebDriver driver) {
		super(driver);
	}

	
	@FindBy(id="bt_NewSystem")
	private WebElement locMenuNewSystem;

	@FindBy(id="SystemName")
	private WebElement locSystemName;

	@FindBy(id="NetworkAddress")
	private WebElement locNetworkAddress;

	@FindBy(id="PlatformID")
	private WebElement locPlatformID;
	
	@FindBy(id="Connection")
	private WebElement locConnection;

	@FindBy(id="PARFuncAcct")
	private WebElement locFuncAcctName;

	@FindBy(id="pword")
	private WebElement locFuncAcctPwd;

	@FindBy(id="ConfPword")
	private WebElement locFuncAcctPwdConfirm;

	@FindBy(id="bt_Save")
	private WebElement locBtnSave;

	@FindBy(id="Filter")
	private WebElement locFilterTab;
	
	@FindBy(id="SystemNm")
	private WebElement locFilterSystemName;
	
	@FindBy(id="Listing")
	private WebElement locListingTab;
	
	// Here, %s is to be changed with the real value.
	private static final String locSystemNameXpath = "//td[contains(text(),'%s')]";
	
	@FindBy(id="bt_Del")
	private WebElement locBtnDelete;
	
	
	public boolean addSystem (String systemName, String platform, String networkAddress, String funcAcctName, String funcAcctPwd) {

		locMenuNewSystem.click();
		locSystemName.sendKeys(systemName);
		locNetworkAddress.sendKeys(networkAddress);

		Select select = new Select(locPlatformID);
		select.selectByVisibleText(platform);
		
		locConnection.click();
		locFuncAcctName.clear();
		locFuncAcctName.sendKeys(funcAcctName);

		locFuncAcctPwd.sendKeys(funcAcctPwd);
		locFuncAcctPwdConfirm.sendKeys(funcAcctPwd);
				
		locBtnSave.click();

		// return PageFactory.initElements(driver, MainPage.class);

		return (waitForTextPresent(driver, "saved successfully", 5));
	}
	
	public boolean deleteSystem (String systemName) {
		
		locMenuNewSystem.click();
						
		locFilterTab.click();
		locFilterSystemName.sendKeys(systemName);
		locListingTab.click();
				
		WebElement locRequiredSystemName = findWebElementByXpath
											(driver, String.format(locSystemNameXpath, systemName));
				
		locRequiredSystemName.click();
		locBtnDelete.click();
		
		Helper.sleepFor(1000);
		driver.switchTo().alert().accept();
		
		return (waitForTextPresent(driver, "successfully deleted", 5));
				
	}

}
