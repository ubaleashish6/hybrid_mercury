package dataUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import driverScripts.Constants;

public class TestUtil {
	private static XSSFSheet sheet;
	private static XSSFWorkbook workBook;
	private static XSSFCell cell;
	private static XSSFRow row;
	public static  FileInputStream fis=null;
	private static Logger logger = Logger.getLogger(TestUtil.class);

	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	public static void setExcelFile(String Path,String SheetName) throws Exception {
		long l_start = System.currentTimeMillis();
		
		try {
			// Open the Excel file
			fis = new FileInputStream(Path);
			// Access the required test data sheet
			workBook = new XSSFWorkbook(fis);
			sheet = workBook.getSheet(SheetName);
			fis.close();
		} catch (Exception e){
			throw (e);
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<TestUtil.java>:<setExcelFile>: "+ (l_end - l_start));

	}
	/*public static void copySheet(String sheetName) throws IOException{
		int index=workBook.getSheetIndex(sheetName);
		XSSFSheet copySheet=workBook.cloneSheet(index);
		FileOutputStream fileOut = new FileOutputStream(Constants.FILE_PATH1);
		workBook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}*/
	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
	public static String getCellData(int RowNum, int ColNum,String sheetName) throws Exception{
		long l_start = System.currentTimeMillis();
		try{
			if(RowNum <=0)
				return "";
			int index = workBook.getSheetIndex(sheetName);
			if(index==-1)
				return "";
			sheet = workBook.getSheetAt(index);
			row = sheet.getRow(RowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(ColNum);
			if(cell==null)
				return "";

			if(cell.getCellType()==CellType.STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){

				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal =Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText =
							(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH)+1 + "/" +
							cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cellText;
					// System.out.println(cellText);
				}
				long l_end = System.currentTimeMillis();
				logger.info("Instrumentation :<TestUtil.java>:<getCellData>: "+ (l_end - l_start));
				return cellText;
			}else if(cell.getCellType()==CellType.FORMULA)
				return "";
			else 
				return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){
			e.printStackTrace();
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<TestUtil.java>:<getCellData>: "+ (l_end - l_start));
			return "row "+RowNum+" or column "+ColNum +" does not exist  in xls";
		}
	}

	public static String  getCellData(String sheetName,String colName,int rowNum){
		long l_start = System.currentTimeMillis();
		try{
			if(rowNum <=0)
				return "";
			int index = workBook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";
			sheet = workBook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";
			sheet = workBook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);
			if(cell==null)
				return "";
			//System.out.println(cell.getCellType());
			if(cell.getCellType()==CellType.STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();
					Calendar cal =Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText =
							(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cal.get(Calendar.MONTH)+1 + "/" + 
							cellText;
					//System.out.println(cellText);
				}


				long l_end = System.currentTimeMillis();
				logger.info("Instrumentation :<TestUtil.java>:<getCellData>: "+ (l_end - l_start));

				return cellText;
			}else if(cell.getCellType()==CellType.BLANK)
				return ""; 
			else 
				return String.valueOf(cell.getBooleanCellValue());

		}
		catch(Exception e){

			e.printStackTrace();
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<TestUtil.java>:<getCellData>: "+ (l_end - l_start));

			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}


	public static String getNumericCellData(int RowNum, int ColNum) throws Exception{
		long l_start = System.currentTimeMillis();
		try{
			cell = sheet.getRow(RowNum).getCell(ColNum);
			Double cellData =cell.getNumericCellValue();
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<TestUtil.java>:<getNumericCellData>: "+ (l_end - l_start));
			return String.valueOf(cellData);
		}catch (Exception e){
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<TestUtil.java>:<getNumericCellData>: "+ (l_end - l_start));
			return ""+e.getMessage();
		}
	}

	//returns the row count in a sheet
	public static int getRowCount(String sheetName){
		long l_start = System.currentTimeMillis();
		int index = workBook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workBook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<TestUtil.java>:<getRowCount>: "+ (l_end - l_start));
			return number;
		}
				
	}

	public static int getColumnCount(String sheetName){
		// check if sheet exists
		/*if(!isSheetExist(sheetName))
		 return -1;
		 */
		long l_start = System.currentTimeMillis();
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(0);

		if(row==null)
			return -1;
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<TestUtil.java>:<getColumnCount>: "+ (l_end - l_start));
		return row.getLastCellNum();



	}
	//This method is to write in the Excel cell, Row num and Col num are the parameters
	public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception    {
		long l_start = System.currentTimeMillis();
		try{
			row  = sheet.getRow(RowNum);
			cell = row.getCell(ColNum);
			if (cell == null) {
				cell = row.createCell(ColNum);
				cell.setCellValue(Result);
			} else {
				cell.setCellValue(Result);
			}
			CellStyle hlink_style = workBook.createCellStyle();
			//hlink_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			hlink_style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());

			XSSFFont hlink_font = workBook.createFont();
			hlink_font.setBold(true);
			hlink_font.setColor(IndexedColors.RED.getIndex());
			//hlink_style.setBorderBottom(CellStyle.SOLID_FOREGROUND);
			hlink_style.setFont(hlink_font);
			cell.setCellStyle(hlink_style);
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(Constants.FILE_PATH);
			workBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(Exception e){
			long l_end = System.currentTimeMillis();
			logger.info("Instrumentation :<TestUtil.java>:<setCellData>: "+ (l_end - l_start));
			throw (e);
			
		}
		long l_end = System.currentTimeMillis();
		logger.info("Instrumentation :<TestUtil.java>:<setCellData>: "+ (l_end - l_start));
		
	}
}