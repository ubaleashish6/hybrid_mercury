package dataUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import driverScripts.Constants;

public class DataUtil {
	private static Logger logger = Logger.getLogger(DataUtil.class);
	
	public static Object[][] getData(String testCase,String path) throws Exception{
		long l_start = System.currentTimeMillis();
		logger.info("Getting the Data from the Excel sheet and storing into Object Array");
		TestUtil.setExcelFile(path, testCase);
		Object testData[][]=new Object[TestUtil.getRowCount(testCase)-1][1];
		Hashtable<String,String> table=null;
		int index=0;
		logger.info("No,Of Rows are "+TestUtil.getRowCount(testCase));
		for(int rowNum=1;rowNum<TestUtil.getRowCount(testCase);rowNum++){
			table=new Hashtable<String,String>();
			for(int colNum=0;colNum<TestUtil.getColumnCount(testCase);colNum++){
				String key=TestUtil.getCellData(1,colNum,testCase);
				String value=TestUtil.getCellData(rowNum+1, colNum, testCase);
				//System.out.println("Key is "+key+ " and Value is "+value);
				table.put(key, value);
				testData[index][0]=table;
			}
			index++;
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<DataUtil.java>:<getData>: "+ (l_end - l_start));
		return testData;
	}
	public static void setData(String testCase,String columnName,int rowNo,String result) throws Exception{
		long l_start = System.currentTimeMillis();
		logger.info("Printing the Result to Excel Sheet");
		TestUtil.setExcelFile(Constants.FILE_PATH, testCase);
		List<String> colNames=new ArrayList<String>();
		for(int colNo=0;colNo<TestUtil.getColumnCount(testCase);colNo++){
			String value=TestUtil.getCellData(1,colNo,testCase);
			colNames.add(value);
		}
		int colNo=0;
		if(colNames.contains(columnName))
			colNo=colNames.indexOf(columnName);
		TestUtil.setCellData(result,rowNo,colNo);
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<DataUtil.java>:<setData>: "+ (l_end - l_start));
	}
	//Logic to Skip the TestCase
	public static boolean isSkip(String testName) throws Exception{
		long l_start = System.currentTimeMillis();
		logger.info("Checking to Skip the Testcase/Data");
		TestUtil.setExcelFile(Constants.TestCase_Xls_Path,Constants.Suite_Xls_SheetName);
		for (int rowNum = 2; rowNum <=TestUtil.getRowCount(Constants.Suite_Xls_SheetName); rowNum++)
		{
			if(testName.equals(TestUtil.getCellData(Constants.Suite_Xls_SheetName,Constants.Suite_Xls_TestCaseId,rowNum))){
				if(TestUtil.getCellData(Constants.Suite_Xls_SheetName,Constants.Runmode,rowNum).equals(Constants.Runmode_YES))
					return true;
				else
					return false;
			}
		}	
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<DataUtil.java>:<isSkip>: "+ (l_end - l_start));
		return false;
	}

}
