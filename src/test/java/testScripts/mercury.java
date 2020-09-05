package testScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import driverScripts.Config;
import driverScripts.Constants;
import driverScripts.Keywords;

public class mercury extends Keywords {
 
	@Test
	public void login() throws IOException, InterruptedException
	{
		properties=new Properties();
		properties.load(new FileInputStream(Constants.PROPERTIES_FILE_PATH));

		loginScreen(Config.mecuryUrl);
		String ActualTitle =driver.getTitle();
		String expectedTitle="Welcome: Mercury Tours";
		Assert.assertEquals(ActualTitle,expectedTitle);
		
		enterText("userName","sunilw");
		enterText("password", "sunilw");
        waitForElementTobeLoaded();
		
		clickOnButton("loginButton");
		waitForElementTobeLoaded();
		
		String expectedTitleAfterLogin="Find a Flight: Mercury Tours:";
		Assert.assertEquals(getActualTitle(),expectedTitleAfterLogin);
		
		clickOnButton("oneway");
		waitForElementTobeLoaded();
		
		
		
		Select selectByVal =new Select(driver.findElement(By.xpath(properties.getProperty("passCount"))));
		selectByVal.selectByValue("1");
	    selectByVal.selectByValue("4");
	    selectByVal.selectByValue("3");
	    selectByVal.selectByValue("2");	    
	    waitForElementTobeLoaded();
		
		Select selectByVal1 = new Select(driver.findElement(By.xpath(properties.getProperty("deptFrom"))));
		selectByVal1.selectByValue("Acapulco");
		selectByVal1.selectByValue("London");
		selectByVal1.selectByValue("New York");
		selectByVal1.selectByValue("Paris");
		selectByVal1.selectByValue("Portland");
		waitForElementTobeLoaded();
		
		driver.findElement(By.xpath("//*[@name = 'fromMonth']")).sendKeys("June");
		waitForElementTobeLoaded();
		
		driver.findElement(By.xpath("//*[@name = 'fromDay']")).sendKeys("20");
		waitForElementTobeLoaded();
		
		driver.findElement(By.xpath("//*[@name = 'toPort']")).sendKeys("Portland");
		waitForElementTobeLoaded();
		
		driver.findElement(By.xpath("//*[@name = 'fromMonth']")).sendKeys("June");
		waitForElementTobeLoaded();
		
		driver.findElement(By.xpath("//*[@name = 'fromDay']")).sendKeys("30");
		waitForElementTobeLoaded();
		
		
		
		clickOnButton("servClass");
		waitForElementTobeLoaded();
		
		driver.findElement(By.xpath("//*[@name = 'airline']")).sendKeys("Unified Airlines");
		waitForElementTobeLoaded();
		
		
		clickOnButton("conButton");
		waitForElementTobeLoaded();
		
		
		clickOnButton("depart");
		waitForElementTobeLoaded();
		
		
		clickOnButton("return");
		waitForElementTobeLoaded();
		
		clickOnButton("conReserveFlightsButton");
		
		
		enterText("passFirst0", "sunil");
		waitForElementTobeLoaded();
		
		
		enterText("passLast0", "kumar");
		waitForElementTobeLoaded();
				
		driver.findElement(By.xpath("//*[@name='pass.0.meal']")).sendKeys("Vegetarian");
		waitForElementTobeLoaded();
		
		enterText("passFirst1", "Anil");
		waitForElementTobeLoaded();
		
		
		enterText("passLast1", "kumar");
		waitForElementTobeLoaded();
		
				
		driver.findElement(By.xpath("//*[@name='pass.1.meal']")).sendKeys("Vegetarian");
		waitForElementTobeLoaded();
		
		clickOnButton("purchaseButton");
		waitForElementTobeLoaded();
		
		System.out.println(getTextFromScreen("departureDetails"));
		
		System.out.println(getTextFromScreen("returnDetails"));
	  	
		driver.close();
		driver.quit();
		
	}

}
