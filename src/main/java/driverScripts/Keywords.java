package driverScripts;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
//import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.log4testng.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Keywords extends TestListenerAdapter {
	private static Logger logger = Logger.getLogger(Keywords.class);
	public static WebDriver driver;
	public static Properties properties;
	public static Connection conn;
	public static Statement stmt;
	public static ResultSet rs;
	// public static ScreenRecorder screenRecorder;

	public static boolean acceptNextAlert = false;
	String serverIP = "192.168.60.20";
	String port = "8800";
	public static int i = 0;
	long implicitWaitTime = Long.parseLong(Config.implicitwait);

	public void loginScreen(String baseUrl) {
		
		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();
		driver.get(baseUrl);
		waitForElementTobeLoaded();
		driver.manage().window().maximize();
	}

	public static boolean isAcceptNextAlert() {
		return acceptNextAlert;
	}

	public static void setAcceptNextAlert(boolean acceptNextAlert) {
		Keywords.acceptNextAlert = acceptNextAlert;
	}

	// Handling the Java script Alert
	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			setAcceptNextAlert(true);
			return true;

		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	// Closing the Java script Alert and Get its text
	public static String closeAlertAndGetItsText() throws UserAlreadyLoggedInException {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (isAcceptNextAlert() == true) {
				alert.accept();
				// throw new UserAlreadyLoggedInException("User is Already Logged In");
			} else {
				alert.dismiss();
			}
			System.out.println(alertText);
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	// To Open the Browser
	public void openBrowser(String data) {
		logger.info("Opening the Browser");
		if (data.equals("Mozilla")) {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.type", 1);
			profile.setPreference("network.proxy.ftp", serverIP);
			profile.setPreference("network.proxy.http", serverIP);
			profile.setPreference("network.proxy.socks", serverIP);
			profile.setPreference("network.proxy.ssl", serverIP);
			profile.setPreference("network.proxy.ftp_port", port);
			profile.setPreference("network.proxy.http_port", port);
			profile.setPreference("network.proxy.socks_port", port);
			profile.setPreference("network.proxy.ssl_port", port);
			driver = new FirefoxDriver();
		} else if (data.equals("IE")) {
			driver = new InternetExplorerDriver();
		} else if (data.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		long implicitWaitTime = Long.parseLong(Config.implicitwait);
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void closeBrowser() {
		logger.info("Closing the browser");
		driver.close(); // close newly opened window when done with it
		driver.quit();
	}

	public void navigate(String data) {
		long l_start = System.currentTimeMillis();
		logger.info("Navigating to URL");
		driver.get(data);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<navigate>: " + (l_end - l_start));
	}

	public static void login(String username, String password, String object) throws UserAlreadyLoggedInException {
		enterText(object, username);
		enterText(object, password);
		clickOnButton(object);
		waitForTitleToPresent(object);
	}

	public static String getActualTitle() {
		String ActualTitle = driver.getTitle();
		return ActualTitle;
	}

	public void clearFieldText(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("clearing field text");
		driver.findElement(By.xpath(properties.getProperty(object))).clear();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<clearFieldText>: " + (l_end - l_start));
	}

	public static void enterText(String object, String data) {
		long l_start = System.currentTimeMillis();
		logger.info("entering text into Textbox");
		driver.findElement(By.xpath(properties.getProperty(object))).sendKeys(data);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<enterText>: " + (l_end - l_start));
	}

	public static void clickOnButton(String object) throws UserAlreadyLoggedInException {
		long l_start = System.currentTimeMillis();
		logger.info("Clicking on the button");
		driver.findElement(By.xpath(properties.getProperty(object))).click();
		if (isAlertPresent()) {
			closeAlertAndGetItsText();
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<clickOnButton>: " + (l_end - l_start));
	}

	public void clickOnButtonByID(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Clicking on the button");
		driver.findElement(By.xpath(properties.getProperty(object))).click();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<clickOnButtonByID>: " + (l_end - l_start));

	}

	public void clickOnLink(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Clicking on the Link");
		driver.findElement(By.xpath(properties.getProperty(object))).click();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<clickOnLink>: " + (l_end - l_start));
	}

	public void clickOnLinkText(String object) throws InterruptedException {
		long l_start = System.currentTimeMillis();
		logger.info("Clicking on the Link");
		Thread.sleep(1000);
		driver.findElement(By.linkText(properties.getProperty(object))).click();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<clickOnLinkText>: " + (l_end - l_start));
	}

	public static void waitForElementTobeLoaded() {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static String getTextFromScreen(String object) {
		String textFromScreem = driver.findElement(By.xpath(properties.getProperty(object))).getText();
		return textFromScreem;
	}

	public static void generateWindowpopUp(String outputText, String title) throws InterruptedException {
		JOptionPane.showMessageDialog(null, outputText, "InfoBox: " + title, JOptionPane.INFORMATION_MESSAGE);

	}

	public void waitForElementToPresent(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Waiting for the Element to Present");
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty(object))));
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<waitForElementToPresent>: " + (l_end - l_start));
	}

	public void verifyAndEnterText(String data) {
		WebElement table = driver.findElement(By.xpath(properties.getProperty("forex_brkUpFundList_Srno")));
		List<WebElement> srNos = table.findElements(By.tagName("tr"));
		int size = srNos.size();
		logger.info("Brkup details size is " + size);
		for (int i = 0; i < size; i++) {
			WebElement element = driver.findElement(By.xpath(properties.getProperty("forex_brkUpFundList_strt") + i
					+ properties.getProperty("forex_brkUpFundList_end")));
			String details = element.getAttribute("value");
			if (details.equals("")) {
				element.sendKeys(data);
			}
		}
	}

	// Waiting for Value to present in dropdown.
	public void waitForValueToPresent(String object, String data) {
		long l_start = System.currentTimeMillis();
		logger.info("Waiting for the Element to Present");
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.getProperty(object) + data)));
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<waitForElementToPresent>: " + (l_end - l_start));
	}

	public static void waitForTitleToPresent(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Waiting for the Title");
		WebDriverWait waitFor = new WebDriverWait(driver, 10);
		waitFor.until(ExpectedConditions.titleIs(properties.getProperty(object)));
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<waitForTitleToPresent>: " + (l_end - l_start));
	}

	public void verifyTitle(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Verifying title");
		String actualTitle = driver.getTitle();
		String expectedTitle = properties.getProperty(object);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<verifyTitle>: " + (l_end - l_start));
		Assert.assertEquals(actualTitle, expectedTitle, "Title is not Equal");
		/*
		 * if(actualTitle.equals(expectedTitle)) return Constants.KEYWORD_PASS; else
		 * return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "
		 * +actualTitle;
		 */
	}

	public boolean isTitlePresent(String expectedTile) {
		logger.info("Checking for the title to be present ");

		String actualTitle = driver.getTitle();
		if (properties.getProperty(expectedTile).equals(actualTitle)) {
			logger.info("Remittance Authorize Page is present");
			return true;
		} else {
			logger.info("Remittance Authorize Page is not present");
			return false;
		}
	}

	public boolean isElementPresent(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Checking for the Element present ");
		try {
			driver.findElement(By.xpath(properties.getProperty(object)));
			// driver.findElements(By.xpath(properties.getProperty(object)));
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<isElementPresent>: " + (l_end - l_start));
			return true;
		} catch (NoSuchElementException e) {
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<isElementPresent>: " + (l_end - l_start));
			return false;
		}
	}

	public boolean isElementPresentInNextPage(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Checking for the Element present ");

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			boolean exists = driver.findElements(By.xpath(properties.getProperty(object))).size() != 0;
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<isElementPresentInNextPage>: " + (l_end - l_start));
			return exists;
		} catch (NoSuchElementException e) {
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<isElementPresent>: " + (l_end - l_start));
			return false;
		}
	}
	// Explicit Wait if element to present
	/*
	 * public boolean isElementPresentExplicit(String object){ long l_start =
	 * System.currentTimeMillis(); logger.info("Checking for the Element present ");
	 * 
	 * try{ WebElement waitForCommAmtZeroValidation = (new WebDriverWait(driver, 2))
	 * .until(ExpectedConditions.presenceOfElementLocated(By.xpath(properties.
	 * getProperty(object)))); long l_end = System.currentTimeMillis();
	 * logger.info("Instrumentation :<Keywords.java>:<verifyTitle>: "+ (l_end -
	 * l_start)); return true; }catch(NoSuchElementException e){ long l_end =
	 * System.currentTimeMillis();
	 * logger.info("Instrumentation :<Keywords.java>:<isElementPresent>: "+ (l_end -
	 * l_start)); return false; } }
	 */

	// Check the Element is Presented on the Web page or not
	public boolean isElementPresent(String object, int time) throws InterruptedException {
		for (int second = 0; second < time; second++) {
			try {
				driver.findElement(By.xpath(properties.getProperty(object))).isDisplayed();
			} catch (NoSuchElementException e) {
				continue;
			}

			return true;
		}

		return false;
	}

	// Check is pop up is present or not

	public boolean isPopupPresent(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Checking for the Element present ");
		if (true == driver.findElement(By.xpath(properties.getProperty(object))).isDisplayed()) {
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<isElementPresent>: " + (l_end - l_start));
			return true;
		} else {
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<isElementPresent>: " + (l_end - l_start));
			return false;
		}
	}

	public void waitForvalueToPresentInDropdwon(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Waiting for the Value to be present in Dropdown");
		By byValue = By.xpath(object);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(byValue));
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 10); wait.until(new
		 * ExpectedCondition<Boolean>() { public Boolean hasMoreThanOneOptions(WebDriver
		 * driver) { return
		 * driver.findElements(By.xpath("#alertSubCatSelectBox option")).size() > 1; }
		 * });
		 */
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<waitForvalueToPresentInDropdwon>: " + (l_end - l_start));
	}

	/*
	 * public void waitForvaluesToPresentInDropdwon(final String object){ long
	 * l_start = System.currentTimeMillis();
	 * logger.info("Waiting for the Value to be present in Dropdown"); By byValue =
	 * By.id(object); Select select=new Select(driver.findElement(byValue)); final
	 * List<WebElement> options=select.getOptions(); new
	 * WebDriverWait(driver,10).until (new ExpectedCondition<Boolean>() {
	 * 
	 * @Override public Boolean apply(WebDriver driver) { return
	 * hasMoreElements(object); } }); long l_end = System.currentTimeMillis();
	 * logger.
	 * info("Instrumentation :<Keywords.java>:<waitForvalueToPresentInDropdwon>: "+
	 * (l_end - l_start)); }
	 */
	public boolean hasMoreElements(String object) {
		By byValue = By.id(properties.getProperty(object));
		Select select = new Select(driver.findElement(byValue));
		List<WebElement> options = select.getOptions();
		if (options.size() > 1)
			return true;
		else
			return false;
	}

	public void verifyAndSelectDropdown(String object, String data) {
		By byValue = By.id(properties.getProperty(object));
		WebElement select = driver.findElement(byValue);
		Select option = new Select(select);
		List<WebElement> options = option.getOptions();
		if (hasMoreElements(object) == true) {
			for (WebElement value : options) {
				String appno = value.getAttribute("value");
				if (appno.equals(data)) {
					option.selectByValue(data);
				}
			}
		}
	}

	public void selectValueFromDropdown(String object, String data) {
		long l_start = System.currentTimeMillis();
		logger.info("Selecting the Value from Dropdwon");
		By byValue = By.xpath(properties.getProperty(object));
		// driver.findElement(byValue).click();
		Select selectValue = new Select(driver.findElement(byValue));
		selectValue.selectByValue(data);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<selectValueFromDropdown>: " + (l_end - l_start));
	}

	public void selectValueFromDropdowByVisibleText(String object, String data) {
		long l_start = System.currentTimeMillis();
		logger.info("Selecting the Value from Dropdwon");
		By byValue = By.xpath(properties.getProperty(object));
		// driver.findElement(byValue).click();
		Select selectValue = new Select(driver.findElement(byValue));
		selectValue.selectByVisibleText(data);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<selectValueFromDropdown>: " + (l_end - l_start));
	}

	public void pressTab(String object) throws InterruptedException {
		/*
		 * Wait for an AJAX Page element http://chon.techliminal.com/ajax_wait/#/slide5
		 */
		long l_start = System.currentTimeMillis();
		logger.info("Pressing Tab by ID");
		By by = By.id(properties.getProperty(object));
		WebElement element = driver.findElement(by);
		element.sendKeys(Keys.TAB);
		Thread.sleep(1000);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<pressTab>: " + (l_end - l_start));

	}

	public void pressTabXpath(String object) throws InterruptedException {
		/*
		 * Wait for an AJAX Page element http://chon.techliminal.com/ajax_wait/#/slide5
		 */
		long l_start = System.currentTimeMillis();
		logger.info("Pressing Tab by xpath");
		By by = By.xpath(properties.getProperty(object));
		WebElement element = driver.findElement(by);
		element.sendKeys(Keys.TAB);
		Thread.sleep(1000);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<pressTabXpath>: " + (l_end - l_start));
	}

	/*
	 * public void waitForElementToDisable(final String object, int timeout) throws
	 * Exception { long l_start = System.currentTimeMillis();
	 * logger.info("Waiting for Element to be disabled"); new WebDriverWait(driver,
	 * timeout).until(new ExpectedCondition<Boolean>() {
	 * 
	 * @Override public Boolean apply(WebDriver driver) { return
	 * isDisabledOrNot(object); } }); long l_end = System.currentTimeMillis();
	 * logger.info("Instrumentation :<Keywords.java>:<waitForElementToDisable>: " +
	 * (l_end - l_start)); }
	 */

	public boolean isDisabledOrNot(String object) {

		logger.info("Check element disabled or not");
		WebElement element = driver.findElement(By.xpath(properties.getProperty(object)));
		if (element.isEnabled() == true)
			return false;
		else
			return true;
	}

	/*
	 * public void waitForElementToEnable(final String object, int timeout) throws
	 * Exception { long l_start = System.currentTimeMillis();
	 * logger.info("Waiting for Element to be enable");
	 * 
	 * new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {
	 * 
	 * @Override public Boolean apply(WebDriver driver) { return
	 * isEnabledOrNot(object); } }); long l_end = System.currentTimeMillis();
	 * logger.info("Instrumentation :<Keywords.java>:<waitForElementToEnable>: " +
	 * (l_end - l_start)); }
	 */

	public boolean isEnabledOrNot(String object) {
		logger.info("Check element disabled or not");
		WebElement element = driver.findElement(By.xpath(properties.getProperty(object)));
		if (element.isEnabled() == true)
			return true;
		else
			return false;
	}

	public void loginToApplicaion(String username, String password) {
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		enterText("username_login_input", username);
		enterText("passwd_login_input", password);
		clickOnButton("button_login");
	}

	public void setFocusOnField(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Setting the focus on the field");

		driver.findElement(By.xpath(properties.getProperty(object))).sendKeys("");
		/*
		 * JavascriptExecutor jse = (JavascriptExecutor) driver;
		 * jse.executeScript("document.getElementById('"+object+"').focus();");
		 */
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<setFocusOnField>: " + (l_end - l_start));
	}

	public void setCursorPosition(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Setting the cursor position");
		Actions performAction = new Actions(driver);
		WebElement myID = driver.findElement(By.xpath(properties.getProperty(object)));
		performAction.moveToElement(myID).perform();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<setCursorPosition>: " + (l_end - l_start));
		// myID.sendKeys(data);
	}

	public String getSelectedValueFromDropdown(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Getting the value from dropdown");
		By byValue = By.id(properties.getProperty(object));
		Select dropdown = new Select(driver.findElement(byValue));
		String selectedValue = dropdown.getFirstSelectedOption().getAttribute("value");
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<getSelectedValueFromDropdown>: " + selectedValue
				+ (l_end - l_start));
		return selectedValue;
	}

	public String getTextOfLabel(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Getting the text from Label");
		// Get the Text from screen
		String text = driver.findElement(By.xpath(properties.getProperty(object))).getAttribute("value");
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<getTextOfLabel>: " + (l_end - l_start));
		return text;
	}

	// Get the Text from the popups etc
	public String getTextOfField(String object) {
		long l_start = System.currentTimeMillis();
		logger.info("Getting the text from field");
		// Get the Text from screen
		String text = driver.findElement(By.xpath(properties.getProperty(object))).getText();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<getTextOfField>: " + (l_end - l_start));
		return text;
	}
	// Application Dependent Keywords

	public static String getStringInt(String num) {
		try {
			Double num1 = Double.valueOf(num);
			int intnum = num1.intValue();
			return new Integer(intnum).toString();
		} catch (Exception e) {
			return num;
		}

	}

	public static String dbConnect() throws Exception {
		/*
		 * fis=new FileInputStream("D:\\TestData\\DataSheet_Cash.xls");
		 * wb=Workbook.getWorkbook(fis);
		 */
		logger.info("Connecting to Database");
		try {
			Class.forName(Config.driver).newInstance();
			System.out.println("loading");
			conn = DriverManager.getConnection(Config.dbURL, Config.dbUserName, Config.dbPassword);
			System.out.println("connected");

		} catch (Exception e) {
			System.out.println("Unable to Connect to the Database");
			return Constants.KEYWORD_FAIL + " Unable to Connect to the Database " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		long l_start = System.currentTimeMillis();
		logger.info("On Test Failure ");
		// call the superclass
		super.onTestFailure(testResult);

		// Get a driver instance from the WebDriverTest object
		// WebDriver driver = WebDriverTest.getDriverInstance();

		/*
		 * We can only take screen shots for those browsers that support screen shot
		 * capture, html unit does not support screenshots as part of its capabilities.
		 */

		if ((driver instanceof FirefoxDriver)) {
			// Create a calendar object so we can create a date and time for the screenshot
			Calendar calendar = Calendar.getInstance();

			// Get the users home path and append the screen shots folder destination
			String userHome = System.getProperty("user.home");
			String screenShotsFolder = userHome + "\\Desktop\\Test-Failure-Screenshots\\";

			// The file includes the the test method and the test class
			String testMethodAndTestClass = testResult.getMethod().getMethodName() + "("
					+ testResult.getTestClass().getName() + ")";

			System.out
					.println(" *** This is where the capture file is created for the Test \n" + testMethodAndTestClass);

			// Create the filename for the screen shots
			String filename = screenShotsFolder + testMethodAndTestClass + "-" + calendar.get(Calendar.YEAR) + "-"
					+ calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-"
					+ calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-"
					+ calendar.get(Calendar.SECOND) + "-" + calendar.get(Calendar.MILLISECOND) + ".png";

			// Take the screen shot and then copy the file to the screen shot folder
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			try {
				FileUtils.copyFile(scrFile, new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}

		} // end of if
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<Keywords.java>:<getTextOfField>: " + (l_end - l_start));

	} // end of onTestFailure()
		// Method for Click on Voucher Scroll bar List
	/*
	 * How it is working //*[@id=''voucherDetails:sc1_table'']/tbody/tr/td[{0}]
	 * String element_id= //*[@id=''voucherDetails:voucherList:{0}:j_id_jsp_{1}'']
	 * String eleId= MessageFormat.format(element_id, String []{i,j} )
	 */

	public void clickOnScrollbarList(String object) {
		long l_start = System.currentTimeMillis();
		int i = 4;
		// while(true)
		// {
		try {
			String ele_xpath = properties.getProperty(object);
			ele_xpath = MessageFormat.format(ele_xpath, new Integer(i).toString());
			driver.findElement(By.xpath(ele_xpath)).click();
			Thread.sleep(1500);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// i++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<clickOnScrollbarList>: " + (l_end - l_start));
			// break;
		}
		// }
	}

	public void clickOnRupeeImgForFunding(String object) {
		long l_start = System.currentTimeMillis();

		for (int rupessSymbolRowNo = 0; rupessSymbolRowNo <= 9; rupessSymbolRowNo++) {
			try {
				String ele_xpath = properties.getProperty(object);
				ele_xpath = MessageFormat.format(ele_xpath, new Integer(rupessSymbolRowNo).toString());
				driver.findElement(By.xpath(ele_xpath)).click();
				Thread.sleep(1000);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				long l_end = System.currentTimeMillis();
				logger.info("Instrumentation :<Keywords.java>:<clickOnRupeeImgForFunding>: " + (l_end - l_start));
			}
		}
	}

	public void clickVcrAuthLink(String object) {
		long l_start = System.currentTimeMillis();
		int i = 0;
		while (true) {
			try {
				String ele_xpath = properties.getProperty(object);
				ele_xpath = MessageFormat.format(ele_xpath, new Integer(i).toString());
				driver.findElement(By.xpath(ele_xpath)).click();
				Thread.sleep(1000);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				long l_end = System.currentTimeMillis();
				logger.info("Instrumentation :<Keywords.java>:<clickVcrAuthLink>: " + (l_end - l_start));
				break;
			}
		}
		i++;
	}

	public String getNumberFromString(String subStringToInt) {
		// String numberFromString=string;
		subStringToInt = subStringToInt.replaceAll("[a-zA-Z]", "");
		System.out.println("narrationText ==" + subStringToInt);
		return subStringToInt;

	}

	public String getTextOnlyFornarrationRtgsNeftMsgcreation(String object) {
		// String ele_xpath=properties.getProperty(object);
		String narrationText = getTextOfLabel(object);
		narrationText = narrationText.replaceAll("[@#%&*().,-]", "").replaceAll("\\[", "").replaceAll("\\]", "");
		return narrationText;
	}

	public void SelectRadioBtn(String object) {
		long l_start = System.currentTimeMillis();

		try {
			WebElement radioBtn = driver.findElement(By.xpath(properties.getProperty(object)));
			radioBtn.click();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<Keywords.java>:<SelectRadioBtn>: " + (l_end - l_start));

		}
	}

	public void takeScreenShot(String functionalityObj) throws IOException, InterruptedException {

		/*
		 * To avoid the overridden of previous screenshot with new one, we can add
		 * timestamp with the name of the file and code will be looklike this
		 * 
		 * File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		 * StringfileName=newSimpleDateFormat("yyyyMMddhhmmss'scenario_1.png'").format(
		 * newDate()); FileUtils.copyFile(scrFile, new File("D:\\"+fileName));
		 */

		String screenShotsFolder = Constants.SCREENSHOTFOLDER_PATH + functionalityObj;
		File folderFile = new File(screenShotsFolder);
		if (folderFile.exists()) {

		} else {
			folderFile.mkdir();
		}
		i++;
		String filename = "Screen_" + i + ".jpg";
		String completePath = screenShotsFolder + "/" + filename;
		System.out.println("File Name = " + filename);
		// Take the screen shot and then copy the file to the screen shot folder
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			FileUtils.copyFile(scrFile, new File(completePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void createXLSReport(){
	 * 
	 * String colName=Constants.RESULT +(currentTestDataSetID-1); boolean
	 * isColExist=false;
	 * 
	 * for(int
	 * c=0;c<currentTestSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET);c++){
	 * if(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET,c ,
	 * 1).equals(colName)){ isColExist=true; break; } }
	 * 
	 * if(!isColExist) currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET,
	 * colName); int index=0; for(int
	 * i=2;i<=currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);i++){
	 * 
	 * if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.
	 * TEST_STEPS_SHEET, Constants.TCID, i))){ if(resultSet.size()==0)
	 * currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i,
	 * Constants.KEYWORD_SKIP); else
	 * currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i,
	 * resultSet.get(index)); index++; }
	 * 
	 * 
	 * }
	 * 
	 * if(resultSet.size()==0){ // skip
	 * currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT,
	 * currentTestDataSetID, Constants.KEYWORD_SKIP); return; }else{ for(int
	 * i=0;i<resultSet.size();i++){
	 * if(!resultSet.get(i).equals(Constants.KEYWORD_PASS)){
	 * currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT,
	 * currentTestDataSetID, resultSet.get(i)); return; } } }
	 * currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT,
	 * currentTestDataSetID, Constants.KEYWORD_PASS); //
	 * if(!currentTestSuiteXLS.getCellData(currentTestCaseName,
	 * "Runmode",currentTestDataSetID).equals("Y")){} }
	 */
}