package driverScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
public class LaunchApp extends Keywords {

	//public static Keywords keyword=null;
	//public static Properties properties=null;
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();

	private static Logger logger = Logger.getLogger(LaunchApp.class);
	private String className=this.getClass().getSimpleName();
	public int rowNo=0;
	File file=new File("");
	String exception;
	@BeforeSuite
	public  void driverObjectCreation() throws InterruptedException, IOException{
		DOMConfigurator.configure("log4j.xml");
		long l_start = System.currentTimeMillis();
		/*long l_start = System.currentTimeMillis();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<LoginTest.java>:<driverObjectCreation>: "+ (l_end - l_start));
		 */
		openBrowser(Config.browser);
		navigate(Config.mecuryUrl);
		Thread.sleep(1500);
		for (String winHandle : driver.getWindowHandles()) {
		    driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
		}
	   // testTakeScreenshot();
	 	//takeScreenShot("Cash VoucherAuth Page Screen shot");
		Thread.sleep(2000);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<LoginTest.java>:<driverObjectCreation>: "+ (l_end - l_start));

	}
	@AfterSuite
	public  void killDriverObject(){
		//driver.close();
		long l_start = System.currentTimeMillis();
		/*logger.info("Killing the Driver Object");
		if(isElementPresent("logout_link1"))
			clickOnLink("logout_link1");
		waitForElementToPresent("username_login_input");*/
		closeBrowser();
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<LoginTest.java>:<killDriverObject>: "+ (l_end - l_start));

	}
	
	@Test
	public void titleCheckTest(){
		try{
			properties=new Properties();
			properties.load(new FileInputStream(Constants.PROPERTIES_FILE_PATH));
			verifyTitle("SwiftCore_Title");

		}catch(Throwable e){
			e.printStackTrace();
			exception=e.getMessage();
		}
	}
	
	/*@BeforeMethod
	public void testApp(){
		logger.info("Before Test");
	}
	@AfterMethod
	public void afterTest(ITestResult testResult) throws Exception{
		long l_start = System.currentTimeMillis();
		logger.info("Catch the result");

		int testStatus=testResult.getStatus();
		if(testStatus==3){
			logger.info("****** "+testResult.getName());
			rowNo++;
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_SKIP);
		}
		else if(testStatus==2){
			rowNo++;
			logger.info("***** Test has been Failed ****** ");
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_FAIL+" Due to "+exception);
		}
		else if(testStatus==1){
			rowNo++;
			logger.info("***** Test has been Passed ****** ");
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_PASS);
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<LoginTest.java>:<afterTest>: "+ (l_end - l_start));

	}
	@Test(description="Logging into the Application ", dataProvider="getData")
	public  void loginTest(Hashtable<String,String> data) throws Exception{
		long l_start = System.currentTimeMillis();
		try{	

			properties=new Properties();
			try {
				properties.load(new FileInputStream(Constants.PROPERTIES_FILE_PATH));
			} catch (Exception e) {
				e.printStackTrace();
			} 
			//Runmode of TestCase
			if(!DataUtil.isSkip("LoginTest"))
				throw new SkipException("Testcase has been Skipped due to Runmode Set to 'NO'");
			//Runmode of Test Data
			if(data.get(Constants.Runmode).equals(Constants.Runmode_NO))
				throw new SkipException("Test Data has been skipped due to Runmode Set to 'NO'");
			//keyword.openBrowser(Config.browser);

			enterText("username_login_input",data.get("Username"));
			enterText("passwd_login_input",data.get("Password"));
			clickOnButton("button_login");
			//Thread.sleep(2000);

			waitForElementToPresent("logout_link1");

			Thread.sleep(1000);
		}catch(Exception e){
			long l_end = System.currentTimeMillis();
			exception=e.getMessage();
			logger.info("Failed due to "+e.getMessage());
			logger.info("Instrumentation :<LoginTest.java>:<loginTest>: "+ (l_end - l_start));

		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<LoginTest.java>:<loginTest>: "+ (l_end - l_start));

	}
	@DataProvider(name="getData")
	public Object[][] getData() throws Exception{
		return DataUtil.getData(className, Constants.FILE_PATH);
	}*/
}
