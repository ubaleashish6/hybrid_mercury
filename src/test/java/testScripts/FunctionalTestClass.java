package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.openqa.selenium.firefox.FirefoxDriver;
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
import driverScripts.LaunchApp;

public class FunctionalTestClass  extends Keywords{

	File file=new File("");
	private String className=this.getClass().getSimpleName();
	private static Logger logger = Logger.getLogger(Keywords.class);
	public int rowNo=0;
	long implicitWaitTime=Long.parseLong(Config.implicitwait);
	LaunchApp launchApp=new LaunchApp();

	@BeforeClass
	public void loginToApp() throws Exception{
		try{
			properties=new Properties();
			properties.load(new FileInputStream(Constants.PROPERTIES_FILE_PATH));

		}catch(Exception e){
			e.printStackTrace();
		}
		loginScreen(Config.mecuryUrl);
		String ActualTitle =driver.getTitle();
		String expectedTitle="Welcome: Mercury Tours";
		Assert.assertEquals(ActualTitle,expectedTitle);
		enterText("userName","ashishu");
		enterText("password", "Test@123");
        waitForElementTobeLoaded();
		
		clickOnButton("loginButton");
		
		takeScreenShot("Login Sucess");
		
		waitForElementTobeLoaded();
		
		String expectedTitleAfterLogin="Find a Flight: Mercury Tours:";
		Assert.assertEquals(getActualTitle(),expectedTitleAfterLogin);
	}
	@AfterClass
	public void logoutApp(){
		if(isElementPresent("logout_link1"))
			clickOnLink("logout_link1");
		driver.close();
		driver.quit();
		//launchApp.killDriverObject();
	}
	@BeforeMethod
	public void beforeTest(){
		
		System.out.println("Executing @Test Method");
		}
	
	@AfterMethod
	public void afterTest(ITestResult testResult) throws Exception{
		long l_start = System.currentTimeMillis();
		logger.info("Catch the result");
		int result=testResult.getStatus();
		if(result==3){
			System.out.println("Skipped");
			logger.info("****** "+testResult.getName());
			rowNo++;
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_SKIP);

		}
		else if(result==2)
		    {
			rowNo++;
			logger.info("***** Test has been Failed ****** ");
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_FAIL+" ");
			
			}
		else if(result==1){
			rowNo++;
			logger.info("***** Test has been Passed ****** ");
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_PASS);
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<FunctionalTestClass.java>:<afterTest>: "+ (l_end - l_start));
	}
	@Test(description=" Ticket Booking in Mercury Tours ",dataProvider="mercury")
	public void mercuryTicketBooking(Hashtable<String,String> data) throws Throwable {
		long l_start = System.currentTimeMillis();

		if(!DataUtil.isSkip(className))
			throw new SkipException(Constants.TestCaseSkipMessage);

		//Run mode of Test Data
		if(data.get(Constants.Runmode).equals(Constants.Runmode_NO)){
			throw new SkipException(Constants.DataSkipMessage);	
		}
		
try {
	
	clickOnButton("oneway");
	
	takeScreenShot("Booking Online Ticket");
	
	selectValueFromDropdown("passCount", getStringInt(data.get("NoofPassengers")));
	
    selectValueFromDropdown("deptFrom", data.get("DepartingFrom").trim());
    takeScreenShot("Booking Online Ticket");
	selectValueFromDropdown("fromMonth", getStringInt(data.get("fromMonth")));
	
	selectValueFromDropdown("fromDay", getStringInt(data.get("fromDay")));
	takeScreenShot("Booking Online Ticket");
	
	selectValueFromDropdown("arrivingIn",data.get("ArrivingIn"));
	
	selectValueFromDropdown("toMonth", getStringInt(data.get("ToMonth")));
	takeScreenShot("Booking Online Ticket");
	
	selectValueFromDropdown("toDay", getStringInt(data.get("ToDay")));
	
	if (data.get("ServiceClass").trim().equals("EconomyClass"))
	{
	clickOnButton("economyClass");
	waitForElementTobeLoaded();
	}
	else if(data.get("ServiceClass").trim().equals("Business"))
	{
		clickOnButton("businessClass");
		waitForElementTobeLoaded();
	}
	
	else if(data.get("ServiceClass").trim().equals("First"))
	{
		clickOnButton("firstClass");
		waitForElementTobeLoaded();
	}
	
	selectValueFromDropdowByVisibleText("airLineSelection", data.get("AirLineSelection"));
	waitForElementTobeLoaded();
	
	clickOnButton("conButton");
	waitForElementTobeLoaded();
	
	clickOnButton("depart");
	waitForElementTobeLoaded();
	
	clickOnButton("return");
	waitForElementTobeLoaded();
	
	clickOnButton("conReserveFlightsButton");
	
	enterText("passFirst0", data.get("Paasenger1FirstName").trim());
	waitForElementTobeLoaded();
	takeScreenShot("Booking Online Ticket");
	
	enterText("passLast0", data.get("Paasenger1SecongName").trim());
	waitForElementTobeLoaded();
	takeScreenShot("Booking Online Ticket");
		
	selectValueFromDropdowByVisibleText("mealType0", data.get("Pass1MealSelection").trim());
	waitForElementTobeLoaded();
	takeScreenShot("Booking Online Ticket");
	
	enterText("passFirst1", data.get("Paasenger2FirstName").trim());
	waitForElementTobeLoaded();
	
	enterText("passLast1", data.get("Paasenger2SecongName").trim());
	waitForElementTobeLoaded();
			
	selectValueFromDropdowByVisibleText("mealType1", data.get("Pass2MealSelection").trim());
	waitForElementTobeLoaded();
	
	clickOnButton("purchaseButton");
	waitForElementTobeLoaded();
	takeScreenShot("Booking Online Ticket");
	System.out.println(getTextFromScreen("departureDetails").trim());
	
	System.out.println(getTextFromScreen("returnDetails").trim());
	
	clickOnButton("backToFlightBooking");
	waitForElementTobeLoaded();
      }

       catch (Exception e)
       {
	    e.printStackTrace();
	    long l_end = System.currentTimeMillis();
	    logger.info("Instrumentation :<FunctionalTestClass.java>:<FunctionalTestClass>: "+e.getMessage()+(l_end - l_start));
	     throw(e);
        }
	  }
	
	@DataProvider(name="mercury")
	public Object[][] getData() throws Exception
	{
		return DataUtil.getData(className,Constants.FILE_PATH);
	}


}
