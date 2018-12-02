package com.nv.extendtestng.frameworkutils;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

/**
 * Provides methods to interact with excel workbook containing test execution
 * details
 * 
 * @author Nagaraj Venkobasa
 *
 */
public class DataBook {

	/**
	 * Updates the output value to test data sheet, invoked by WebDriverFactory method
	 * @param XLFileName Representing path of excel workbook with filename
	 * @param XLSheet Representing worksheet of workbook
	 * @param parameterName Representing the column name in worksheet
	 * @param parameterValue Representing the value that needs to be updated against testcase and column name
	 * @param testCaseID Representing test case id of test case
	 * @param iteration Representing test case iteration
	 */
	public synchronized static void updateData(String XLFileName, String XLSheet, String parameterName, String parameterValue, String testCaseID, String iteration) {
		Fillo fillo = new Fillo();
		try{
		Connection connection = fillo.getConnection(XLFileName);
		String strQuery = "Update " + XLSheet + " Set " + parameterName + "='" + parameterValue + "' where TestCaseID = '" + testCaseID	+ "' and Iteration = '" + iteration.replace("Iteration",  "") + "'";
		connection.executeUpdate(strQuery);
		connection.close();
		}catch(FilloException fllex){
			fllex.printStackTrace();
		}
	}
	
	/**
	 * Returns the test cases which are aligned for test execution
	 * @param fileName Excel workbook file name with path
	 * @return ArrayList containing list of test cases and its exection flow
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static ArrayList<ArrayList<HashMap<String, String>>> getExecutableTestCases(String fileName){
		if(!fileExists(fileName)) 
			return null; 
		
		Fillo fillo = new Fillo();
        Connection connection = null;
        Recordset recordSet = null;
        try {
            connection = fillo.getConnection(fileName);
	     } catch (FilloException e) {
	            System.out.println("File: " + fileName + " is not available."); e.printStackTrace();
	            return null;
	     }
        
        String strTCExecutableQuery = "SELECT * FROM MasterController WHERE ExecutionRequired ='Yes'";
        try{
        	recordSet = connection.executeQuery(strTCExecutableQuery);
        } catch (FilloException e) {
            System.out.println("No records found for execution"); e.printStackTrace();
            return null;
        }
        
		Map<String, Map<String, String>> masterMap = new HashMap<>();
		try {
			while (recordSet.next()) {
				for (int ivalcount = 0; ivalcount < recordSet.getCount(); ivalcount++) {
					String testCaseID = recordSet.getField("TestCaseID").toString();
					if (!masterMap.containsKey(testCaseID) && testCaseID.length() != 0) {
						Map<String, String> controlData = new HashMap<String, String>();
						controlData.put("Description", recordSet.getField("Description").toString());
						String iterationCount = recordSet.getField("IterationCount").toString();
						controlData.put("IterationCount", iterationCount.length()==0? "1": iterationCount );
						masterMap.put(testCaseID, controlData);
						if(recordSet.hasNext()){
							recordSet.moveNext();
						}
						
					} else {
						System.err.println("Duplicate Records for Testcase :: "	+ testCaseID + " found in Master Controller Sheet");
						//recordSet.close();
					}
				}
			}
		} catch (FilloException e) {
			System.out.println("record set is not valid."); e.printStackTrace();
		}
		recordSet.close();
		
		/**************************************************************************************
		 * Logic to Read Step details of test cases aligned for execution with iteration Count
		 **************************************************************************************/
		XSSFWorkbook workBook = null;
        ArrayList<ArrayList<HashMap<String, String>>> testCaseRecords =  new ArrayList<>();
		try{
			FileInputStream inputStream = new FileInputStream(new File(fileName));
	        workBook = new XSSFWorkbook(inputStream);
	        XSSFSheet shtExecutionFlow = workBook.getSheet("ExecutionFlow");
	        int rows = shtExecutionFlow.getLastRowNum();
	        HashMap<String, Integer> headerDetails = new HashMap<String, Integer>();
	        
	        Row headerRow = shtExecutionFlow.getRow(0);
	        int columns = headerRow.getLastCellNum();
	        for(int iCol = 0;iCol < columns; iCol++){
	        	String columnName = headerRow.getCell(iCol, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
	        	if(!headerDetails.containsKey(columnName))
	        		headerDetails.put(columnName, Integer.valueOf(iCol));
	        }
	        
	        Iterator<?> it = masterMap.entrySet().iterator();
	        while (it.hasNext()) {
	        	Map.Entry pair = (Map.Entry) it.next();
				String testCaseIDParent = pair.getKey().toString().trim();
				int iterationCount = Integer.valueOf(masterMap.get(testCaseIDParent).get("IterationCount"));
	            String description = masterMap.get(testCaseIDParent).get("Description");
	            for (int iCount = 1; iCount <= iterationCount; iCount++) {
	            	ArrayList<HashMap<String, String>> iterationRecord = new ArrayList<>();
	            	String iterationValue = "Iteration" + iCount;
	            	for(int iRow = 1; iRow <= rows; iRow++){
	            		Row executionFlow = shtExecutionFlow.getRow(iRow);
	            		String testCaseIDChild = executionFlow.getCell(headerDetails.get("TestCaseID"), Row.CREATE_NULL_AS_BLANK).getStringCellValue();
	            		String iterationStatus = executionFlow.getCell(headerDetails.get(iterationValue), Row.CREATE_NULL_AS_BLANK).getStringCellValue();
	            		
	            		if(testCaseIDChild.equals(testCaseIDParent) && !(iterationStatus.equalsIgnoreCase("pass") || iterationStatus.equalsIgnoreCase("skip")) ){
	            			HashMap<String, String> record = new HashMap<String, String>();
	            			record.put("RowNumber", String.valueOf(iRow));
	            			record.put("TestCaseID", executionFlow.getCell(headerDetails.get("TestCaseID"), Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	                        record.put("CallSequence", executionFlow.getCell(headerDetails.get("CallSequence"), Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	                        record.put("DataSheet", executionFlow.getCell(headerDetails.get("DataSheet"), Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	                        record.put("Iteration", iterationValue);
	                        record.put("Description", description);
	                        record.put("SuiteFileName", fileName);
	                        iterationRecord.add(record);
	            		}
	            	}//iRow

	            	if(iterationRecord.size()!=0)
	            		testCaseRecords.add(iterationRecord);
	            	else
	            		System.out.println("[info] For Given TestCase = '" + testCaseIDParent + " - No Call Sequence items found for ::" + iterationValue);
	            } //iCount
	            it.remove();
	        }
		}catch(IOException ioex){
			ioex.printStackTrace();
		}finally{
			try {
				workBook.close();
			} catch (IOException e) {
				;
			}
		}
		return testCaseRecords;
	}
	
	/**
	 * Returns HashMap of test data required for script execution 
	 * @param testCaseID String representing testCaseID
	 * @param testCaseIteration String representing Iteration of the test currently in execution
	 * @param fileName String representing TestSuite excel workbook path
	 * @param testDataSheetName String representing excel sheet name
	 * @return HashMap
	 */
	public static HashMap<String, String> getRunTimeData(String testCaseID, String testCaseIteration, String fileName, String testDataSheetName) {
		// This method will provide all the records in a HashMap
		String strQuery = "SELECT * FROM " + testDataSheetName + " WHERE TestCaseID ='" + testCaseID + "' AND Iteration = '" + testCaseIteration.replace("Iteration", "") + "'";
		
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(fileName);
		} catch (FilloException e) {
			System.out.println("File: " + fileName + " is not available."); e.printStackTrace();
			return null;
		}
		
		ArrayList<String> strMasterControllerfields = null;
		HashMap<String, String> fieldData = new HashMap<>();
		Recordset recordset = null;
		try {
			recordset = connection.executeQuery(strQuery);
		} catch (FilloException e) {
			System.out.println("No Records found to Execute for test case - " + testCaseID);
			e.printStackTrace();
			return null;
		}
		try {
			strMasterControllerfields = recordset.getFieldNames();
		} catch (FilloException e) {
			System.out.println("No Fields are available");
			e.printStackTrace();
			return null;
		}

		try {
			while (recordset.next()) {
				for (int ifieldCount = 0; ifieldCount < strMasterControllerfields.size(); ifieldCount++) {
					String fieldName = strMasterControllerfields.get(ifieldCount).toString();
					try {
						if (!fieldData.containsKey(fieldName) && fieldName != null) {
							String fieldValue = recordset.getField(strMasterControllerfields.get(ifieldCount)).toString();
							fieldData.put(fieldName, (fieldValue == null ? String.valueOf("") : fieldValue));
							//fieldData.put(strMasterControllerfields.get(ifieldCount), recordset.getField(strMasterControllerfields.get(ifieldCount)).toString());
						}else{
							System.out.println("duplicate column values are present: [" + fieldName + "]");
						}
					} catch (FilloException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FilloException e) {
			e.printStackTrace();
		}
		
		fieldData.put("DataSheet", testDataSheetName);
		fieldData.put("TestSuitePath", fileName);
		System.out.println(fieldData.toString());
		return fieldData;
	}
	

	/**
	 * Updates the test step status in ExecutionFlow Sheet
	 * @param stepDetails HasMap containing details of Execution Step Details
	 * @param status Status representing test status PASS/FAIL
	 */
	public static synchronized void updateTestStepStatus(HashMap<String, String> stepDetails, String status){
		//String fileName = "C:/Users/606813868/Automation/Extend_TestNG_Automation/test-data/ExecutionController.xlsx";
		String fileName = stepDetails.get("SuiteFileName");
		int rowNumber = Integer.valueOf(stepDetails.get("RowNumber"));
		String iteration = stepDetails.get("Iteration");
		Map<String, Integer> headerDetails = new HashMap<String, Integer>();
		FileInputStream fis = null;
		XSSFWorkbook workBook = null;
		try {
			fis = new FileInputStream(fileName);
			workBook = new XSSFWorkbook(fis);
			XSSFSheet shtExecutionFlow = workBook.getSheet("ExecutionFlow");
			
			Row headerRow = shtExecutionFlow.getRow(0);
	        int columns = headerRow.getLastCellNum();
	        for(int iCol = 0;iCol < columns; iCol++){
				headerDetails.put(headerRow.getCell(iCol, Row.CREATE_NULL_AS_BLANK).getStringCellValue(), Integer.valueOf(iCol));
	        }
			
			Row stepRow = shtExecutionFlow.getRow(rowNumber);
			int iterationColumn = Integer.valueOf(headerDetails.get(iteration));
			Cell cell = stepRow.getCell(iterationColumn, Row.CREATE_NULL_AS_BLANK); 
			if(cell == null)
				cell = stepRow.createCell(Integer.valueOf(headerDetails.get(iteration)));
			
			cell.setCellValue(status);
			fis.close();
			
			FileOutputStream outFile =new FileOutputStream(new File(fileName));
			workBook.write(outFile);
			outFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				workBook.close();
			} catch (IOException e) {
				;
			}
		}
		
	}
	
	/**
	 * Checks whether given file exists or not
	 * @param path Representing absolute or relative path of file name with path 
	 * @return true if file exists otherwise false
	 */
	private static boolean fileExists(String path) {
		File file = new File(path);
		return file.exists();
	}

} //EOC
