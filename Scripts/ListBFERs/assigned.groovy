import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.Select
import java.util.regex.Matcher
import java.util.regex.Pattern	
import java.util.concurrent.TimeUnit

List People = ["ykuniver", "klovtsov", "abuyanov", "ibaranov"]

FirefoxProfile ffprofile = new FirefoxProfile(new File("edmzhq_profile"))
WebDriver driver = new FirefoxDriver(ffprofile)
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)


println ("BFERs in the EDMZ BugTracker:\n")


for (Person in People) {

	EDMZAddress = "https://hq01.e-dmzsecurity.com/edmzhq/BFERFilter.asp"

	driver.get(EDMZAddress)
	
	// new Select(driver.findElement(By.name("StatusIDs"))).deselectByVisibleText("All Open")
	// new Select(driver.findElement(By.name("StatusIDs"))).selectByVisibleText("All Statuses")

	Select Users = new Select(driver.findElement(By.id("AUser")))

	List<WebElement> listOfUsers = Users.getOptions()

	for (WebElement option : listOfUsers) {
		String currentUser = option.getText()
		if (currentUser.contains(Person)) {           // if sub-string is matched  
			Users.selectByVisibleText(currentUser);   // select this option 
			break;                                    // out of the loop
		}
	}
		
	driver.findElement(By.name("B2")).click()


	WebElement tableNumbers = driver.findElement(By.id("tblLineItemLines"))
	List<WebElement> allNumbers = tableNumbers.findElements(By.tagName("tr"))   // getting BFER Numbers
	
	Numbers = allNumbers.size()

	if (Numbers > 0) {

		WebElement tableInformation = driver.findElement(By.id("tblLineItemData"))  // getting BFERs' fields
		List<WebElement> allRows = tableInformation.findElements(By.tagName("tr"))
		
		println ("\n" + Person + "\n")
		
		fieldList = [0,1,2,4,6,7,8,11]            // Fields for a given BFER we are interested in
		
		for (i=0; i<=Numbers-1; i++){

			str = allNumbers[i].getText() + "\t"  // A BFER Number is located in a separate WebElement
			
			List<WebElement> cells = allRows[i].findElements(By.tagName("td"))  // BFER fields

			for (field in fieldList) {
				str += cells[field].getText() + "\t"
			}
			
			println (str)

		}
	
	} // if (Numbers > 0)	

} // for (Person in People)

driver.quit()

