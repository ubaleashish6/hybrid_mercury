package testScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.xml.crypto.Data;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import dataUtil.DataUtil;
import driverScripts.Config;
import driverScripts.Constants;
import driverScripts.Keywords;

public class Flipkart extends Keywords {
	String ActualTitle = " ";
	String expectedTitle = " ";
	private String className = this.getClass().getSimpleName();
	private static Logger logger = Logger.getLogger(Keywords.class);
	public int rowNo = 0;
	long implicitWaitTime = Long.parseLong(Config.implicitwait);

	@BeforeClass
	public void loginToApp() throws Exception {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(Constants.PROPERTIES_FILE_PATH));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public void logoutApp() {
		if (isElementPresent("logout_link1"))
			clickOnLink("logout_link1");
		driver.close();
		driver.quit();
		// launchApp.killDriverObject();
	}
	@BeforeMethod
	public void beforeTest() {
		System.out.println("Executing @Test Method");
	}
	@AfterMethod
	public void afterTest(ITestResult testResult) throws Exception {
		long l_start = System.currentTimeMillis();
		logger.info("Catch the result");
		int result = testResult.getStatus();
		if (result == 3) {
			System.out.println("Skipped");
			logger.info("****** " + testResult.getName());
			rowNo++;
			DataUtil.setData(className, Constants.RESULT, rowNo,
					Constants.KEYWORD_SKIP);
		} else if (result == 2) {
			rowNo++;
			logger.info("***** Test has been Failed ****** ");
			DataUtil.setData(className, Constants.RESULT, rowNo,
					Constants.KEYWORD_FAIL + " ");

		} else if (result == 1) {
			rowNo++;
			logger.info("***** Test has been Passed ****** ");
			DataUtil.setData(className, Constants.RESULT, rowNo,
					Constants.KEYWORD_PASS);
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<FunctionalTestClass.java>:<afterTest>: "
				+ (l_end - l_start));
	}

	
	@Test(description = "Flipkart", dataProvider = "flipkart")
	public void Flipkartlogin(Hashtable<String, String> data) throws Throwable {
		long l_start = System.currentTimeMillis();

		if (!DataUtil.isSkip(className))
			throw new SkipException(Constants.TestCaseSkipMessage);

		// Run mode of Test Data
		if (data.get(Constants.Runmode).equals(Constants.Runmode_NO)) {
			throw new SkipException(Constants.DataSkipMessage);
		}

		try {
			loginScreen(Config.flipUrl);
			takeScreenShot("Flipkart login page");
			logger.info("***** flipUrl has been entered ****** ");
			ActualTitle = driver.getTitle().trim();
			System.out.println("ActualTitle name:"+ActualTitle);
			expectedTitle = "Online Shopping India Mobile, Cameras, Lifestyle & more Online @ Flipkart.com";
			if(expectedTitle.equals(ActualTitle)){
			      System.out.println("Online Shopping India | Flipkart.com--page is loaded");
			   }
			 else {
				 System.out.println("Online Shopping India | Flipkart.com--page is not loaded");
			  }			
//			Assert.assertEquals(ActualTitle, expectedTitle);
			l_start = System.currentTimeMillis();
			clickOnLink("flip_Login_link");
			enterText("flip_userid",data.get("username"));
			logger.info("***** flip_userid has been entered ****** ");
			enterText("flip_password",data.get("password"));
			logger.info("***** flip_password has been entered ****** ");
			waitForElementTobeLoaded();
			clickOnButton("flip_Login_Button");
			waitForElementTobeLoaded();
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<FunctionalTestClass.java>:<afterTest>: "+ (l_end - l_start));
			ActualTitle = getTextFromScreen("flip_login_errorMessage").trim();
			expectedTitle = "Please enter a valid email/mobile";
			if(expectedTitle.equals(ActualTitle)){
			      System.out.println("Please enter a valid email/mobile--erorr is loaded");
			   }
			 else {
				 System.out.println("Please enter a valid email/mobile--erorr is not loaded");
			  }
			takeScreenShot("Flipkart login page");
//			Assert.assertEquals(getActualTitle(), expectedTitleAfterLogin);
			clickOnButton("flip_login_closeButton");
		} catch (Exception e) {
			e.printStackTrace();
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<FunctionalTestClass.java>:<FunctionalTestClass>: "
					+ e.getMessage() + (l_end - l_start));
			throw (e);
		}
	}
	@DataProvider(name = "flipkart")
	public Object[][] getData() throws Exception {
		return DataUtil.getData(className, Constants.FILE_PATH);
	}
}
