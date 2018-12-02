package com.nv.extendtestng.testlogic;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sourceforge.stripes.util.ResolverUtil;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nv.extendtestng.frameworkutils.DataBook;
import com.nv.extendtestng.frameworkutils.ExtentManager;
import com.nv.extendtestng.frameworkutils.ExtentTestManager;
import com.nv.extendtestng.frameworkutils.FileHandler;
import com.nv.extendtestng.frameworkutils.WebDriverFactory;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Base TestNG class which invokes multiple test cases in parallel for execution
 * 
 * @author Nagaraj Venkobasa
 *
 */
public class BaseTestNG extends TestListenerAdapter{ 
	private final static boolean quitDriver = false;	
	private final static int IMPLICIT_WAIT_TIMEOUT = 120; // in seconds
	private final static int PAGELOAD_WAIT_TIMEOUT = 120; // in seconds
	private final static int UPLOAD_DIALOG_WAIT_TIMEOUT = 60; // in seconds
	
	@DataProvider(name="DataProvider", parallel=true)
	public static Object[][] getData() {
		ArrayList<ArrayList<HashMap<String, String>>> executionDetails = new ArrayList<ArrayList<HashMap<String,String>>>();
		FileHandler.closeExcelFile();
		ArrayList<String> testSuites = getTestSuiteFiles();
		for(String testSuite: testSuites){
			ArrayList<ArrayList<HashMap<String, String>>> executionDetail = DataBook.getExecutableTestCases(testSuite);
			executionDetails.addAll(executionDetail);
		}
		//Iterate though arrayList to cast it into Object[][]
		Object[][] testExecutionSequence = new Object[executionDetails.size()][1];
		int i = 0;
		for (ArrayList<HashMap<String, String>> eachTestCaseSequnce : executionDetails) {
			testExecutionSequence[i++][0] = eachTestCaseSequnce;
		}
		return testExecutionSequence;
	}
	
	@Test(dataProvider="DataProvider")
	public void BaseTestNGExecutor(List<HashMap<String, String>> executionFlow)  {
		WebDriver driver = null;
		try {
			boolean initialized = false;
			Map<String, String> dataBook = null;
			
			Class<?> clazz = Object.class;
			Object testClassInstance = null;
			
			for(int exeCounter = 0; exeCounter < executionFlow.size(); exeCounter++){
				HashMap<String, String> callee = executionFlow.get(exeCounter);
				String testCaseID = callee.get("TestCaseID");
				String methodName = callee.get("CallSequence");
				String testDataSheetName = callee.get("DataSheet");
				String testCaseIteration = callee.get("Iteration");
				String testCaseDescription = callee.get("Description");
				String fileName = callee.get("SuiteFileName");
				
				if(exeCounter == 0){/*To rename the default TestCase Descriptions in Report*/
					ExtentTestManager.getTest().setDescription(testCaseDescription);
					ExtentTestManager.getTest().getTest().setName(testCaseID + "-" + testCaseIteration);
				}
				
				dataBook = DataBook.getRunTimeData(testCaseID, testCaseIteration, fileName, testDataSheetName);
				if(!initialized){
					driver = initializeWebDriver(dataBook.get("Browser"));
					initialized = true;
				}
				
				Class<? extends WebDriverFactory> tmpClazz = getClass(methodName);
				if(!clazz.equals(tmpClazz) && tmpClazz!=null) {
					clazz = tmpClazz;
					testClassInstance = clazz.asSubclass(clazz).getConstructor(WebDriver.class, Map.class).newInstance(driver, dataBook);
				}				
				
				Method method = getMethodofClass(clazz, methodName);
				System.out.println("Method Executing now --> " + methodName);
				if(method == null){
					ExtentTestManager.getTest().log(LogStatus.ERROR, "Specified method name <B>["+ methodName +"]</B> should exist", "Specified method name doesn't exist in any of classes which are extending <B>WebDriverFactory</B> class");
					Assert.fail();
				} else if(method.getParameterCount() > 0){
					ExtentTestManager.getTest().log(LogStatus.ERROR, "Method ["+ methodName +"]should not contain any parameters", "Method contains parameters, expected: method with no parameters");
					Assert.fail();
				} else{
					ExtentTestManager.getTest().log(LogStatus.INFO, "<B>** " + methodName + " **</B>", "<B>** " + methodName + " </B>** is being executed");
					method.invoke(testClassInstance);
					DataBook.updateTestStepStatus(callee, ExtentTestManager.getTest().getRunStatus().name());
					if(ExtentTestManager.getTest().getRunStatus().name().equalsIgnoreCase("FAIL")){
						Assert.fail();
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail();
		}catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail();
		} catch(Exception ex){
			ex.printStackTrace();
			ExtentTestManager.getTest().log(LogStatus.FAIL, ex.getMessage());
			Assert.fail();
		} finally{
			if (quitDriver) {
				if (!driver.toString().contains("null")) {
					driver.quit();
				}
			}
		}
	}
	
	
	/**
	 * Returns the Class which extends WebDriverFactory of a given method name
	 * @param methodName Representing the methodName which should be invoked
	 * @return Class
	 * @throws ClassNotFoundException 
	 */
	private static Class<? extends WebDriverFactory> getClass(String methodName){
		String frameworkUtilPackageName = WebDriverFactory.class.getPackage().getName();
		String testLogicPackageName = BaseTestNG.class.getPackage().getName();
		System.out.println(" ::::::::::::: " + frameworkUtilPackageName);
		ResolverUtil<WebDriverFactory> resolver = new ResolverUtil<WebDriverFactory>();
		//resolver.findImplementations(WebDriverFactory.class,currentClassPackageName, currentClassPackageName.replace("testlogic", "frameworkutils"));
		resolver.findImplementations(WebDriverFactory.class, frameworkUtilPackageName, testLogicPackageName);
		Set<Class<? extends WebDriverFactory>> classes = resolver.getClasses();	
		for (Class<? extends WebDriverFactory> clazz : classes) {
			if (!clazz.toString().contains("frameworkutils.WebDriverFactory")) {
				Method[] methods = clazz.getDeclaredMethods();
				for(Method method : methods){
					if(method.getName().toUpperCase().equals(methodName.toUpperCase())){
						return clazz;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns Method instance of a given method & its class
	 * @param clazz Representing class name which extends WebDriverFactory
	 * @param methodName methodName Representing the methodName which should be invoked
	 * @return Method
	 */
	private Method getMethodofClass(Class<?> clazz, String methodName){
		Method[] methods = clazz.getDeclaredMethods();			
		for (int mIndex = 0; mIndex < methods.length; mIndex++) {
			if(methods[mIndex].getName().toUpperCase().equals(methodName.toUpperCase())){
				return methods[mIndex];
			}
		}
		return null;
	}
	
	/**
	 * Returns the WebDriver instance based on BrowserName 
	 * @param browserName "IEXPLORE", "FIREFOX"
	 * @return Instance of WebDriver or null
	 */
	private WebDriver initializeWebDriver(String browserName) {
		Properties property = new Properties();
		WebDriver driver = null;
		try {
			property.load(new FileInputStream("SeleniumConstants.properties"));
			switch(browserName.toUpperCase()){
				case "IEXPLORE":
					String ieDriverPath = "./"+ property.getProperty("ieDriverFileName");
					System.setProperty("webdriver.ie.driver", ieDriverPath);
					InternetExplorerOptions ieOptions = new InternetExplorerOptions();
					ieOptions.ignoreZoomSettings()
							.enableNativeEvents()
							.introduceFlakinessByIgnoringSecurityDomains()
							.waitForUploadDialogUpTo(UPLOAD_DIALOG_WAIT_TIMEOUT, TimeUnit.SECONDS)
							.elementScrollTo(ElementScrollBehavior.TOP);                
					driver = new InternetExplorerDriver(ieOptions);
					break;
					
				case "CHROME":
					String chromeDriverPath = "./"+ property.getProperty("chromeDriverFileName");
					System.setProperty("webdriver.chrome.driver", chromeDriverPath);
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
					driver = new ChromeDriver(chromeOptions);
					break;
					
				case "FIREFOX":
					String geckoDriverPath = "./"+ property.getProperty("geckoDriverFileName");
					System.setProperty("webdriver.gecko.driver", geckoDriverPath);
					driver = new FirefoxDriver();
					break;
					
				default:
					ExtentTestManager.getTest().log(LogStatus.FAIL, "Valid Browser Names are [IEXPLORE, FIREFOX]", "Invalid Browser<B>" + browserName + "</B> has been passed");
					return null;
			}
			
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(PAGELOAD_WAIT_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			return driver;
		} catch (SessionNotCreatedException snfex) {
			if (snfex.getMessage().contains("Protected Mode settings are not the same for all zones"))
				ExtentTestManager.getTest().log(LogStatus.WARNING, "launch App", "Protected Mode settings are not the same for all zones. Goto Tools --> internet Options --> Security (Enable Protected Mode should be enabled/disabled for all zone.)");
			else
				ExtentTestManager.getTest().log(LogStatus.WARNING, "launch App", snfex.getMessage());
			return null;
		} catch (Exception ex) {
			ExtentTestManager.getTest().log(LogStatus.WARNING, "launch App", ex.getMessage());
			return null;
		}
	}
	
	@BeforeMethod
	public void beforeMethod(Method caller) {
		ExtentTestManager.startTest(caller.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed", "Test passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Test failed", "Test failed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped", "Test skipped");
		}
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	@AfterSuite
	public void afterSuite() {
		ExtentManager.getInstance().flush();
		try {
			Desktop.getDesktop().browse(new File(ExtentManager.reportFileName).getCanonicalFile().toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the path names of the excel file that are set for execution
	 * @return ArrayList - containing files names of excel that matches rules of TestSuite
	 */
	private static ArrayList<String> getTestSuiteFiles() {
		ArrayList<String> masterSheetColumnNames = new ArrayList<String>();
		ArrayList<String> executionFlowColumnNames = new ArrayList<String>();
		
		masterSheetColumnNames.add("TestCaseID");
		masterSheetColumnNames.add("Description");
		masterSheetColumnNames.add("IterationCount");
		masterSheetColumnNames.add("ExecutionRequired");
		
		executionFlowColumnNames.add("TestCaseID");
		executionFlowColumnNames.add("CallSequence");
		executionFlowColumnNames.add("DataSheet");	
		
		File dir = new File("./test-data");
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return (filename.endsWith(".xlsx") && !filename.startsWith("~$"));
			}
		});
		
		boolean isMasterControllerSheetPresent, isExecutionFlowSheetPresent;
		boolean areMasterColumnsPresent = true, areExecutionColumnPresent = true;
		ArrayList<String> fileNames = new ArrayList<String>();
		for(File file: files){
			//Initialised to check if expected MasterController,ExecutionFlow Sheets are present or not
			isMasterControllerSheetPresent = false;
			isExecutionFlowSheetPresent = false;
			
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis); 
				int sheetCount = workbook.getNumberOfSheets();
				for(int iSht = 0; iSht < sheetCount;iSht++){
					String sheetName = workbook.getSheetName(iSht);
					if(sheetName.equals("MasterController")){
						isMasterControllerSheetPresent = true;
						
						Row firstRow = workbook.getSheet(sheetName).getRow(0);
						int cellCount = firstRow.getLastCellNum();
						ArrayList <String> actualColumnNames = new ArrayList<String>();
						for(int iCell = 0; iCell < cellCount; iCell++){
							String cellValue = firstRow.getCell(iCell).getStringCellValue().toString();
							actualColumnNames.add(cellValue);
						}
						
						for(String columnName: masterSheetColumnNames){
							if(!actualColumnNames.contains(columnName)){
								areMasterColumnsPresent = false;
								break;
							}
						}
					} else if(sheetName.equals("ExecutionFlow")){
						isExecutionFlowSheetPresent = true;
						Row firstRow = workbook.getSheet(sheetName).getRow(0);
						int cellCount = firstRow.getLastCellNum();
						ArrayList <String> actualColumnNames = new ArrayList<String>();
						for(int iCell = 0; iCell < cellCount; iCell++){
							String cellValue = firstRow.getCell(iCell).getStringCellValue().toString();
							actualColumnNames.add(cellValue);
						}
						
						for(String columnName : executionFlowColumnNames){
							if(!actualColumnNames.contains(columnName)){
								areExecutionColumnPresent = false;
								break;
							}
						}
					}
				}
				
				if(isExecutionFlowSheetPresent && isMasterControllerSheetPresent){
					if(areMasterColumnsPresent && areExecutionColumnPresent){
						fileNames.add(file.getCanonicalPath());
					}else{
						if(!areMasterColumnsPresent){
							System.err.println("[info] Expected columns " + executionFlowColumnNames + " are not present in file: " + file.getName() + " for Sheet[MasterController]");
						}
						if(!areExecutionColumnPresent){
							System.err.println("[info] Expected columns " + executionFlowColumnNames + " are not present in file: " + file.getName() + " for Sheet[ExecutionFlow]");
						}
					}
				}else{
					System.err.println("[info] Either of [MasterController,ExecutionFlow] Sheet is missing in file: " + file.getName());
				}
				
				fis.close();
				workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return fileNames;
	}
}