package testScripts;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
@SuppressWarnings("serial")
class RecordCreateException extends Exception {
	RecordCreateException(String message) {
		super(message);
	}
}
public class RtgsNeftVcrPosting extends Keywords {
	File file=new File("");
	private String className=this.getClass().getSimpleName();
	private static Logger logger = Logger.getLogger(Keywords.class);
	private String setNumber ;
	private String warningErrorMsgOnPopupOfSubmit;
	private String exception;
	public int rowNo=0;
	long implicitWaitTime=Long.parseLong(Config.implicitwait);
	LaunchApp launchApp=new LaunchApp();
	private static String rtgsNeftMessageType;
	private static String rtgsNeftMsgAmount;
	private static String shadowClearBalance;
	private static String shadowTotalBalance;
	private static String actualClearBalance;
	private static String actualTotalBalance;
	

	@BeforeClass
	public void loginToApp() throws Exception{
		try{
			properties=new Properties();
			properties.load(new FileInputStream(Constants.PROPERTIES_FILE_PATH));

		}catch(Exception e){
			e.printStackTrace();
		}
		launchApp.driverObjectCreation();
		enterText("username_login_input","S1");
		enterText("passwd_login_input","ABC123");
		clickOnButton("button_login");
		dbConnect();
	}
	@AfterClass
	public void logoutApp(){
		if(isElementPresent("logout_link1"))
			clickOnLink("logout_link1");
		//launchApp.killDriverObject();
	}
	@BeforeMethod
	public void beforeTest(){
		System.out.println("Executing the @Test Method ");
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
			setNumber=" ";
			warningErrorMsgOnPopupOfSubmit=" ";
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_SKIP);

		}
		else if(result==2)
		    {
			rowNo++;
			setNumber=" ";
			logger.info("***** Test has been Failed ****** ");
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_FAIL+"--"+exception);
			
			}
		else if(result==1){
			rowNo++;
			logger.info("***** Test has been Passed ****** ");
			DataUtil.setData(className,Constants.RESULT,rowNo,Constants.KEYWORD_PASS);
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<RtgsNeftVcrPosting.java>:<afterTest>: "+ (l_end - l_start));
	}
	@Test(description=" RTGS/NEFT  Voucher Posting ",dataProvider="rtgsNeft")
	public void rtgsNeftVcrPosting(Hashtable<String,String> data) throws Throwable {
	/*	Posting.CrVcrPosting();
		System.out.println("Posting Done SucessFully");
		Funding.CrVcrFunding();*/
		long l_start = System.currentTimeMillis();

		if(!DataUtil.isSkip(className))
			throw new SkipException(Constants.TestCaseSkipMessage);

		//Run mode of Test Data
		if(data.get(Constants.Runmode).equals(Constants.Runmode_NO)){
			throw new SkipException(Constants.DataSkipMessage);	
		}
		
try {		
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		clickOnLinkText("retail_masters_Link");
        takeScreenShot("RTGS NEFT Message Creation Screen Shot");

		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		clickOnLink("retail_rtgs_Link");
        takeScreenShot("RTGS NEFT Message Creation Screen Shot");

		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        clickOnLink("retail_rtgsNeft_message_create_Link");
        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        clickOnButton("retail_rtgsNeft_newMessage_Btn");
		logger.info("Selecting Batch Code");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        selectValueFromDropdown("retail_rtgsNeftselect_batchCd", getStringInt(data.get("Batch")));
        pressTab("retail_rtgsNeftselect_batchCd");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		logger.info("Selecting Message Type");
        selectValueFromDropdown("retail_rtgsNeft_MessageType", getStringInt(data.get("MessageType")));
		pressTab("retail_rtgsNeft_MessageType");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		logger.info("Selecting Benficiary IFSC Code");
        enterText("retail_rtgsNeftMsg_benIfscCd", getStringInt(data.get("BeneficiaryIFSCCd")));
		pressTabXpath("retail_rtgsNeftMsg_benIfscCd");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		logger.info("Enter Transfer Amount");
        enterText("retail_rtgsNeft_MsgAmt","1000");
		pressTabXpath("retail_rtgsNeft_MsgAmt");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        clickOnButton("retail_rtgsNeft_warningBtnForAmt");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    		logger.info("Selecting Oredring Account Product Code");
            selectValueFromDropdown("retail_rtgsNeft_orderingAcc_prdcd", data.get("OrderingAccount"));
            pressTab("retail_rtgsNeft_orderingAcc_prdcd");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    		logger.info("Enter ordering Account Number");
            enterText("retail_rtgsNeft_accountNumber",getStringInt(data.get("AcctNumber")));
            pressTabXpath("retail_rtgsNeft_accountNumber");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    		Thread.sleep(2000);
        	driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
   
         if (true== isPopupPresent("retail_rtgs_accountBlnLess_warning"))
    	     
         {  
        	driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
   			clickOnButton("retail_rtgs_accountBlnLess_warning_OkBtn");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    		logger.info("Enter Benficiary Details");
    		// BenFi Details Entries
    		enterText("retail_rtgsNeft_ordDesc1", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc1"));
            pressTabXpath("retail_rtgsNeft_ordDesc1");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
            enterText("retail_rtgsNeft_ordDesc2", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc2"));
            pressTabXpath("retail_rtgsNeft_ordDesc2");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
            enterText("retail_rtgsNeft_ordDesc3", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc3"));
            pressTabXpath("retail_rtgsNeft_ordDesc3");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
            enterText("retail_rtgsNeft_ordDesc4", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc4"));
            pressTabXpath("retail_rtgsNeft_ordDesc4");
            // Mobile number Entry
    		logger.info("Enter Benficiary Mobile Number");
            SelectRadioBtn("retail_rtgsNeft_mobileNumberRadionBtn");
            pressTabXpath("retail_rtgsNeft_mobileNumberRadionBtn");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
            enterText("retail_rtgsNeft_mobileNumberEntry",(new BigDecimal(data.get("Mobile")).toString()));
            pressTabXpath("retail_rtgsNeft_mobileNumberEntry");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    		// Mobile number Entry End
    		selectValueFromDropdown("retail_rtgsNeft_InstrType",getStringInt(data.get("InstrumentType"))); 
    		pressTab("retail_rtgsNeft_InstrType");
    		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
            Thread.sleep(1000);
            enterText("retail_rtgsNeft_benfiAccNumber",(new BigDecimal(data.get("BenfiAccountNo")).toString()));
            pressTabXpath("retail_rtgsNeft_benfiAccNumber");
    		// Trasaction Code selection
            selectValueFromDropdown("retail_rtgsNeft_TransactionCd_sel", getStringInt(data.get("TransactionCd"))); 
            pressTab("retail_rtgsNeft_TransactionCd_sel");
            // Trasaction Code selection end 
            enterText("retail_rtgsNeft_benfiDetails2", getStringInt(data.get("BenfiNarration1")));
            pressTabXpath("retail_rtgsNeft_benfiDetails2");
            enterText("retail_rtgsNeft_benfiDetails3", getStringInt(data.get("BenfiNarration2")));
            pressTabXpath("retail_rtgsNeft_benfiDetails3");
            enterText("retail_rtgsNeft_benfiDetails4", getStringInt(data.get("BenfiNarration3")));
            pressTabXpath("retail_rtgsNeft_benfiDetails4");
            enterText("retail_rtgsNeft_benfiDetails5", getStringInt(data.get("BenfiNarration4")));
            pressTabXpath("retail_rtgsNeft_benfiDetails5");
            
           selectValueFromDropdown("retail_rtgsNeft_ChargeType_Sel", getStringInt(data.get("DetailsofChrgs")));
           pressTab("retail_rtgsNeft_ChargeType_Sel");
    	   driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    	   
    	   enterText("retail_rtgsNeft_gridField1", getStringInt(data.get("GridField2")));
    	   pressTabXpath("retail_rtgsNeft_gridField1");
    	   enterText("retail_rtgsNeft_gridField2", getStringInt(data.get("GridField3")));
    	   pressTabXpath("retail_rtgsNeft_gridField2");
    	   enterText("retail_rtgsNeft_gridField3", getStringInt(data.get("GridField4")));
    	   pressTabXpath("retail_rtgsNeft_gridField3");
    	   enterText("retail_rtgsNeft_gridField4",getStringInt(data.get("GridField5")));
    	   pressTabXpath("retail_rtgsNeft_gridField4");
           takeScreenShot("RTGS NEFT Message Creation Screen Shot");
    	   driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    	   clickOnButton("retail_rtgsNeft_SubmitBtn");
    	   Thread.sleep(8000);
    	   driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
   		if (true == isPopupPresent("retail_rtgsNeft_Warning"))
    		{
   			warningErrorMsgOnPopupOfSubmit= getTextOfField("retail_rtgsNeft_WarningList");		
			System.out.println("Account validation on GetDetails = " +warningErrorMsgOnPopupOfSubmit);
			clickOnButton("retail_rtgsNeft_WarningOkBtn");
			Thread.sleep(8000);
	        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
	        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
			setNumber= getStringInt(getTextOfField("retail_rtgsNeft_SetNumber_InfoPanel")) ;
			String utrSequenceNumber=setNumber;	
			utrSequenceNumber=utrSequenceNumber.substring(24,29);	
		    System.out.println(utrSequenceNumber);
			logger.info("Generated Set Number is " + setNumber);
            System.out.println("generated set Number= " +setNumber);
			logger.info("Warning Message is "+warningErrorMsgOnPopupOfSubmit);
			System.out.println("Account validation on GetDetails = " +warningErrorMsgOnPopupOfSubmit);
			clickOnButton("retail_rtgsNeft_SetNumber_InfoPanelOkBtn");
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			takeScreenShot("RTGS NEFT Message Creation Screen Shot");
			
			// Connection opened for D946020 Table	
			Statement stmt= conn.createStatement();
			ResultSet rsrtgsneft=stmt.executeQuery("SELECT * FROM D946020 WHERE OBRCODE=8 AND UTRSEQNO ="+utrSequenceNumber);
			while (rsrtgsneft.next())
			{
				rtgsNeftMessageType=rsrtgsneft.getString("MSGSTYPE");
				System.out.println("princepalAmt="+rtgsNeftMessageType);
				
				rtgsNeftMsgAmount=rsrtgsneft.getString("AMOUNT");
				System.out.println("maturityAmt="+rtgsNeftMsgAmount);
		
			}
	// Connection Closed for D020004 table		
			rsrtgsneft.close();
	// Connection Opened for D009022		
			String casaProduct=data.get("OrderingAccount");
			String casaMainAccNo=getStringInt(data.get("AcctNumber"));
			for (int i=casaProduct.length();i<8;i++)
			{
				casaProduct=casaProduct+" ";
			}
			casaMainAccNo="'"+casaProduct+String.format("%016d",Long.valueOf(casaMainAccNo))+"00000000"+"'";
			System.out.println(casaMainAccNo);	
			
           ResultSet rsAccount=stmt.executeQuery("SELECT * FROM d009022 WHERE LBRCODE =8 AND PRDACCTID ="+casaMainAccNo);
           while (rsAccount.next())
           {
        	   shadowClearBalance=rsAccount.getString("SHDCLRBALFCY");
				System.out.println("princepalAmt="+shadowClearBalance);
				
				shadowTotalBalance=rsAccount.getString("SHDTOTBALFCY");
				System.out.println("maturityAmt="+shadowTotalBalance);
				
				actualClearBalance=rsAccount.getString("ACTCLRBALFCY");
				System.out.println("princepalAmt="+actualClearBalance);
				
				actualTotalBalance=rsAccount.getString("ACTTOTBALFCY");
				System.out.println("maturityAmt="+actualTotalBalance);
           }
		rsAccount.close();
}
   		
   		else if (true==isPopupPresent("retail_rtgsNeft_errorList"))
   		  {
   			setNumber="Unable to create voucher";
   			warningErrorMsgOnPopupOfSubmit=getTextOfField("retail_rtgsNeft_errorList");
		    logger.info("Error Message is "+warningErrorMsgOnPopupOfSubmit);
   			clickOnButton("retail_rtgsNeft_errorOk");
   			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			//throw new RecordCreateException("Unable to create due to "+warningErrorMsgOnPopupOfSubmit);
   			
   		   }
    		
    		else if (true==isPopupPresent("retail_rtgsNeft_SetNumber_InfoPanel"))
    		{
    	        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
    			setNumber= getStringInt(getTextOfField("retail_rtgsNeft_SetNumber_InfoPanel")) ;
    			logger.info("Generated Set Number is " + setNumber);
                System.out.println("generated set Number= " +setNumber);
    			clickOnButton("retail_rtgsNeft_SetNumber_InfoPanelOkBtn");
    			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    	           takeScreenShot("RTGS NEFT Message Creation Screen Shot");

    		/*	
    			String setNumber=getStringInt(getTextOfField("retail_rtgsNeft_SetNumber_InfoPanel"));
    		    logger.info("Error Message is "+setNumber);
    			System.out.println("generated set Number= "+setNumber);
    			clickOnButton("retail_rtgsNeft_SetNumber_InfoPanelOkBtn");
    			Thread.sleep(1000);
    			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);*/
    		}
    		
        }
       else if (true==isTitlePresent("retail_rtgsNeft_vcrEntryScreen"))
	   {
    	   // BenFi Details Entries
   		enterText("retail_rtgsNeft_ordDesc1", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc1"));
        pressTabXpath("retail_rtgsNeft_ordDesc1");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        enterText("retail_rtgsNeft_ordDesc2", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc2"));
        pressTabXpath("retail_rtgsNeft_ordDesc2");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        enterText("retail_rtgsNeft_ordDesc3", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc3"));
        pressTabXpath("retail_rtgsNeft_ordDesc3");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        enterText("retail_rtgsNeft_ordDesc4", getTextOnlyFornarrationRtgsNeftMsgcreation("retail_rtgsNeft_ordDesc4"));
        pressTabXpath("retail_rtgsNeft_ordDesc4");
        // Mobile number Entry
        SelectRadioBtn("retail_rtgsNeft_mobileNumberRadionBtn");
        pressTabXpath("retail_rtgsNeft_mobileNumberRadionBtn");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        enterText("retail_rtgsNeft_mobileNumberEntry",(new BigDecimal(data.get("Mobile")).toString()));
        pressTabXpath("retail_rtgsNeft_mobileNumberEntry");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);

		// Mobile number Entry End
		selectValueFromDropdown("retail_rtgsNeft_InstrType",getStringInt(data.get("InstrumentType"))); 
		pressTab("retail_rtgsNeft_InstrType");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        Thread.sleep(1000);
        enterText("retail_rtgsNeft_benfiAccNumber",(new BigDecimal(data.get("BenfiAccountNo")).toString()));
        pressTabXpath("retail_rtgsNeft_benfiAccNumber");
		// Trasaction Code selection
        selectValueFromDropdown("retail_rtgsNeft_TransactionCd_sel", getStringInt(data.get("TransactionCd"))); 
        pressTab("retail_rtgsNeft_TransactionCd_sel");
        // Trasaction Code selection end 

        enterText("retail_rtgsNeft_benfiDetails2", getStringInt(data.get("BenfiNarration1")));
        pressTabXpath("retail_rtgsNeft_benfiDetails2");
        enterText("retail_rtgsNeft_benfiDetails3", getStringInt(data.get("BenfiNarration2")));
        pressTabXpath("retail_rtgsNeft_benfiDetails3");
        enterText("retail_rtgsNeft_benfiDetails4", getStringInt(data.get("BenfiNarration3")));
        pressTabXpath("retail_rtgsNeft_benfiDetails4");
        enterText("retail_rtgsNeft_benfiDetails5", getStringInt(data.get("BenfiNarration4")));
        pressTabXpath("retail_rtgsNeft_benfiDetails5");
        
       selectValueFromDropdown("retail_rtgsNeft_ChargeType_Sel", getStringInt(data.get("DetailsofChrgs")));
       pressTab("retail_rtgsNeft_ChargeType_Sel");
	   driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
	   
	   enterText("retail_rtgsNeft_gridField1", getStringInt(data.get("GridField2")));
	   pressTabXpath("retail_rtgsNeft_gridField1");
	   enterText("retail_rtgsNeft_gridField2", getStringInt(data.get("GridField3")));
	   pressTabXpath("retail_rtgsNeft_gridField2");
	   enterText("retail_rtgsNeft_gridField3", getStringInt(data.get("GridField4")));
	   pressTabXpath("retail_rtgsNeft_gridField3");
	   enterText("retail_rtgsNeft_gridField4",getStringInt(data.get("GridField5")));
	   pressTabXpath("retail_rtgsNeft_gridField4");
       takeScreenShot("RTGS NEFT Message Creation Screen Shot");
	   driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
	   clickOnButton("retail_rtgsNeft_SubmitBtn");
	   driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
	   Thread.sleep(8000);
	   if (true == isPopupPresent("retail_rtgsNeft_Warning"))
		{
		warningErrorMsgOnPopupOfSubmit= getTextOfField("retail_rtgsNeft_WarningList");		
		System.out.println("Account validation on GetDetails = " +warningErrorMsgOnPopupOfSubmit);
		clickOnButton("retail_rtgsNeft_WarningOkBtn");
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
		setNumber= getStringInt(getTextOfField("retail_rtgsNeft_SetNumber_InfoPanel")) ;
		String utrSequenceNumber=setNumber;	
		utrSequenceNumber=utrSequenceNumber.substring(24,29);	
	    System.out.println(utrSequenceNumber);
		logger.info("Generated Set Number is " + setNumber);
        System.out.println("generated set Number= " +setNumber);
		logger.info("Warning Message is "+warningErrorMsgOnPopupOfSubmit);
		System.out.println("Account validation on GetDetails = " +warningErrorMsgOnPopupOfSubmit);
		clickOnButton("retail_rtgsNeft_SetNumber_InfoPanelOkBtn");
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
//Connection opened for D946020 Table	
    	Statement stmt= conn.createStatement();
			ResultSet rsrtgsneft=stmt.executeQuery("SELECT * FROM D946020 WHERE OBRCODE=8 AND UTRSEQNO ="+utrSequenceNumber);
			while (rsrtgsneft.next())
			{
				rtgsNeftMessageType=rsrtgsneft.getString("MSGSTYPE");
				System.out.println("princepalAmt="+rtgsNeftMessageType);
				
				rtgsNeftMsgAmount=rsrtgsneft.getString("AMOUNT");
				System.out.println("maturityAmt="+rtgsNeftMsgAmount);
		
			}
	// Connection Closed for D020004 table		
			rsrtgsneft.close();
	// Connection Opened for D009022		
			String casaProduct=data.get("OrderingAccount");
			String casaMainAccNo=getStringInt(data.get("AcctNumber"));
			for (int i=casaProduct.length();i<8;i++)
			{
				casaProduct=casaProduct+" ";
			}
			casaMainAccNo="'"+casaProduct+String.format("%016d",Long.valueOf(casaMainAccNo))+"00000000"+"'";
			System.out.println(casaMainAccNo);	
        ResultSet rsAccount=stmt.executeQuery("SELECT * FROM d009022 WHERE LBRCODE =8 AND PRDACCTID ="+casaMainAccNo);
        while (rsAccount.next())
        {
     	   shadowClearBalance=rsAccount.getString("SHDCLRBALFCY");
				System.out.println("princepalAmt="+shadowClearBalance);
				
				shadowTotalBalance=rsAccount.getString("SHDTOTBALFCY");
				System.out.println("maturityAmt="+shadowTotalBalance);
				
				actualClearBalance=rsAccount.getString("ACTCLRBALFCY");
				System.out.println("princepalAmt="+actualClearBalance);
				
				actualTotalBalance=rsAccount.getString("ACTTOTBALFCY");
				System.out.println("maturityAmt="+actualTotalBalance);
        }
		rsAccount.close();

		}
		
		else if (true==isPopupPresent("retail_rtgsNeft_errorList"))
		  {
			setNumber="Unable to create voucher";
			warningErrorMsgOnPopupOfSubmit=getTextOfField("retail_rtgsNeft_errorList");
	        logger.info("Error Message is "+warningErrorMsgOnPopupOfSubmit);
			clickOnButton("retail_rtgsNeft_errorOk");
		
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		//throw new RecordCreateException("Unable to create due to "+warningErrorMsgOnPopupOfSubmit);
			
		   }

		else if (true==isPopupPresent("retail_rtgsNeft_SetNumber_InfoPanel"))
		{
	       	driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
	        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
			setNumber= getStringInt(getTextOfField("retail_rtgsNeft_SetNumber_InfoPanel")) ;
			logger.info("Generated Set Number is " + setNumber);
            System.out.println("generated set Number= " +setNumber);
            String utrSequenceNumber=setNumber;	
			utrSequenceNumber=utrSequenceNumber.substring(24, 30);	
		    System.out.println(utrSequenceNumber);
			clickOnButton("retail_rtgsNeft_SetNumber_InfoPanelOkBtn");
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
	        takeScreenShot("RTGS NEFT Message Creation Screen Shot");
			// Connection opened for D946020 Table	
	    	Statement stmt= conn.createStatement();
			ResultSet rsrtgsneft=stmt.executeQuery("SELECT * FROM D946020 WHERE OBRCODE=8 AND UTRSEQNO ="+utrSequenceNumber);
			while (rsrtgsneft.next())
			{
				rtgsNeftMessageType=rsrtgsneft.getString("MSGSTYPE");
				System.out.println("princepalAmt="+rtgsNeftMessageType);
				
				rtgsNeftMsgAmount=rsrtgsneft.getString("AMOUNT");
				System.out.println("maturityAmt="+rtgsNeftMsgAmount);
		
			}
	// Connection Closed for D020004 table		
			rsrtgsneft.close();
	// Connection Opened for D009022		
			String casaProduct=data.get("OrderingAccount");
			String casaMainAccNo=getStringInt(data.get("AcctNumber"));
			for (int i=casaProduct.length();i<8;i++)
			{
				casaProduct=casaProduct+" ";
			}
			casaMainAccNo="'"+casaProduct+String.format("%016d",Long.valueOf(casaMainAccNo))+"00000000"+"'";
			System.out.println(casaMainAccNo);	
           ResultSet rsAccount=stmt.executeQuery("SELECT * FROM d009022 WHERE LBRCODE =8 AND PRDACCTID ="+casaMainAccNo);
           while (rsAccount.next())
           {
        	   shadowClearBalance=rsAccount.getString("SHDCLRBALFCY");
				System.out.println("princepalAmt="+shadowClearBalance);
				
				shadowTotalBalance=rsAccount.getString("SHDTOTBALFCY");
				System.out.println("maturityAmt="+shadowTotalBalance);
				
				actualClearBalance=rsAccount.getString("ACTCLRBALFCY");
				System.out.println("princepalAmt="+actualClearBalance);
				
				actualTotalBalance=rsAccount.getString("ACTTOTBALFCY");
				System.out.println("maturityAmt="+actualTotalBalance);
           }
		rsAccount.close();
		   }
		  }
       }

       catch (Exception e)
       {
	    e.printStackTrace();
	    exception=e.getMessage();
	    long l_end = System.currentTimeMillis();
	    logger.info("Instrumentation :<RtgsNeftVcrPosting.java>:<rtgsNeftVcrPosting>: "+e.getMessage()+(l_end - l_start));
	     throw(e);
        }
	  }
	
	@DataProvider(name="rtgsNeft")
	public Object[][] getData() throws Exception
	{
		return DataUtil.getData(className,Constants.FILE_PATH);
	}
}
