package driverScripts;

public class Constants {

	public static String KEYWORD_PASS="PASS";
	public static String KEYWORD_SKIP="SKIP";
	public static String KEYWORD_FAIL="FAIL";
	public static String RESULT="Result";
	public static String AUTHORIZE_RESULT="AuthResult";
	public static String Runmode_YES="Y";
	public static String Runmode_NO="N";
	public static String REMARKS="Remarks";
	public static String TIMETAKEN_TO_COMPLETE_TX="totalTimeTaken";

	
	public static String DataSheetName="Data";
	public static String DataSkipMessage="Test Data has been skipped due to Runmode Set to 'NO'";
	public static String DataDependsSkipMessage="Test Data has been skipped due to PrimaryDetails Record is not Created";
	public static String TestCaseSkipMessage="Testcase has been Skipped due to Runmode Set to 'NO'";
	public static String Runmode="Runmode";
	public static String Suite_Xls_SheetName="TestCases";
	public static String Suite_Xls_TestCaseId="TCID";
	
	
	public static String PROPERTIES_FILE_PATH=System.getProperty("user.dir")+"\\src\\main\\java\\driverScripts\\or.properties";
	public static String FILE_PATH=System.getProperty("user.dir")+"\\src\\main\\java\\testData\\TestData.xlsx";
	public static String TestCase_Xls_Path=System.getProperty("user.dir")+"\\src\\main\\java\\testData\\TestCase.xlsx";
	public static String SCREENSHOTFOLDER_PATH=System.getProperty("user.dir")+"\\src\\main\\java\\screenShots\\";
}
