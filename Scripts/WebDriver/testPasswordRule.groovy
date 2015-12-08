import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.util.concurrent.TimeUnit


String tempFileName = 'PasswordRuleTestExpression.txt'
def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def pwdRuleName = args[3]
boolean shouldContain = (args[4].toLowerCase() =='y')? true:false

/*
 * It is a problem to pass some nonalphanumeric symbols to the groovy script as parameter.
 * So, if expression is not specified, we will read it from a temporal file.
 */
def expr = args.size()>5?args[5]:readExpressionFromFile(tempFileName)

def pattern = ~/$expr/
def seeker = {pattern.isCase(it)}

println "Checking expression is $pattern"
println "Is expression expected? $shouldContain"
println ''

//open Password Rule
System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
libComn = new libCommon()

try {
	libComn.login(driver, TPAMaddress, "/admin/", adminName, adminPwd)
	//open Password Rule page
	driver.get("https://$TPAMaddress/admin/ManagePwdRules.asp")
	new Select(driver.findElement(By.id('PasswordRuleID'))).selectByVisibleText(pwdRuleName)
	driver.findElement(By.id('TestRuleButton')).click()
	//Wait while passwords are generating
	WebElement btnClose = (new WebDriverWait(driver, 120))
	.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='testRule']//input[@type='button']")))
	//Get generated passwords
	generatedPasswords=[]
	List<WebElement> displayPasswords = driver.findElements(By.xpath("//table[@id='passwordSamples']/*/tr/td[4]"))
	displayPasswords.each {
		generatedPassword = it.getText()
		//println generatedPassword
		if(!generatedPassword.contains('UNABLE TO CREATE PASSWORD AFTER')){
			generatedPasswords += generatedPassword.substring(1, generatedPassword.length()-1).replace('–', '-')
	}
		
	}
	btnClose.click()
	assert generatedPasswords.size() > 0 : 'Password list is empty'
	
	//Check passwords
	if (shouldContain){
		isEveryContain = generatedPasswords.every(seeker)
		if(isEveryContain){
			println 'Success'
		}else{
		    fit = generatedPasswords.findAll(seeker)
			generatedPasswords.removeAll(fit)
			println "Failed. As example, the password ${generatedPasswords[0]} does not match to the expression $pattern"
		}
	}else{
	    isContain = generatedPasswords.any(seeker)
		if(!isContain){
			println 'Success'
		}else{
		    wrongPwd = generatedPasswords.find(seeker)
		    println "Failed. As example, the password $wrongPwd matches to expression $pattern"
		}
	}
}
catch(FileNotFoundException ioex){
	println("Something went wrong: $ioex")
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "testPasswordRule")
	println("Catched: Something went wrong: " + toutex);
}
catch (e){
	println("Catched: Something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}

String readExpressionFromFile(String fileName){
	projDir = new File(getClass().protectionDomain.codeSource.location.path).getParentFile().getParentFile().parent
	TestExpressionFile = new File(projDir,fileName)
	txt = TestExpressionFile.text.trim()
	return txt.substring(1, txt.length()-1) //to remove double quotes
}