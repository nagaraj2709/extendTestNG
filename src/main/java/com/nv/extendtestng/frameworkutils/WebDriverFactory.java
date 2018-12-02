package com.nv.extendtestng.frameworkutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * The WebDriverFactory Class provides methods that are required to interact
 * with WebDriver
 * @author Nagaraj Venkobasa
 * @version 1.0
 * @since 13/03/2017
 */
public class WebDriverFactory {
	
	private final static int MAX_ATTEMP = 100; // in milliseconds
	private final static int WEBDRIVER_WAIT_TIMEOUT = 60; // in seconds
	protected final static int IMPLICIT_WAIT_TIMEOUT = 120; // in seconds
	private final static int PAGELOAD_WAIT_TIMEOUT = 120; // in seconds
	
	public WebDriver driver = null;
	private WebElement element;	
	
	private String parentWindow;
	private String reportPathName;
	private Properties property;
	private final static String USERDIR = System.getProperty("user.dir");
	private String snapshotPath = ExtentManager.snapshotPath;
	
	//Extent Report
	public ExtentTest extentTest = null;
	
	public Map<String, String> dataBook;
	
	public WebDriverFactory(WebDriver driver, Map<String, String> dataBook){
		property = new Properties();
		try {
			this.extentTest = ExtentTestManager.getTest();
			this.driver = driver;
			this.dataBook = dataBook;
			property.load(new FileInputStream("SeleniumConstants.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDataBook(Map<String,String> dataBook){
		this.dataBook = dataBook;
	}
	
	public Map<String,String> getDataBook(){
		return this.dataBook;
	}
	
	public ExtentTest getExtentTest() {
		return extentTest;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getReportPathName() {
		return new File(reportPathName).getAbsolutePath();
	}

	public String getSnapshotPath() {
		return ExtentManager.snapshotPath;
	}
	
	public String getReportFileName(){
		return reportPathName;
	}
	
	/**
	 * Refreshes the data in databook with updated values from datasheet
	 */
	public void refreshDataBook(){
		String testCaseID = this.dataBook.get("TestCaseID");
		String testDataSheetName = this.dataBook.get("DataSheet");
		String testCaseIteration = this.dataBook.get("Iteration");
		String fileName = this.dataBook.get("TestSuitePath");
		HashMap<String, String> tempDataBook = DataBook.getRunTimeData(testCaseID, testCaseIteration, fileName, testDataSheetName);
		if(tempDataBook !=null)
			this.dataBook = tempDataBook;
	}
	
	/**
	 * Updates the output value to datasheet
	 * @param parameterName Column Name
	 * @param parameterValue Output Value
	 */
	public void updateOutputValue(String parameterName, String parameterValue) {
		String sheetName = this.dataBook.get("DataSheet");
		String testSuitePath = this.dataBook.get("TestSuitePath"); 
		String testCaseID = this.dataBook.get("TestCaseID");
		String iteration = this.dataBook.get("Iteration"); 
		
		if(!this.dataBook.containsKey(parameterName)){
			extentTest.log(LogStatus.WARNING, "Update Output value to Sheet", parameterName + " - could not be found in " + testSuitePath + "[" + sheetName +"]");
			return;
		}else{
			DataBook.updateData(testSuitePath, sheetName, parameterName, parameterValue, testCaseID, iteration);
		}
	}
	
	
	/**
	 * Launches the application with specified
	 * 
	 * @param browserName Accepted values are "IEXPLORE", "FIREFOX"
	 * @param URL Target URL which needs to be launched
	 * @return boolean - true if application is launched successfully otherwise false
	 */

	public boolean launchAPP(String URL) {
	//public boolean launchAPP(String browserName, String URL) {
		boolean blnNavigated;
		// Return false if URL is empty
		if(URL == null) {
			extentTest.log(LogStatus.ERROR, "Launch Application", "URL is passed as blank or does not exist in Test Data Sheet");
			return false;
		}else if (URL.isEmpty()) {
			extentTest.log(LogStatus.ERROR, "Launch Application", "URL is empty");
			return false;
		}

		try {
			driver.get(URL);
			waitForPageToLoad();

			for (int iCounter = 1; iCounter <= 5; iCounter++) {
				if (driver.getPageSource().contains(
						"Web Server Temporarily Unavailable")) {
					driver.navigate().to(URL);
					snooze(5000);
				} else {
					extentTest.log(LogStatus.INFO, "Launch App", "launchApp was successful for URL <B>" + URL + "</B>");
					blnNavigated = true;
					return blnNavigated;
				}
			}
		} catch (SessionNotCreatedException snfex) {
			String message = snfex.getMessage().toString().replace("\n", " ");
			
			if (message.contains("Protected Mode settings are not the same for all zones"))
				extentTest.log(LogStatus.WARNING, "launch App", "Protected Mode settings are not the same for all zones. Goto Tools --> internet Options --> Security (Enable Protected Mode should be enabled/disabled for all zone.)");
			else if(message.matches("session (.*) does not exist(.*)"))
				extentTest.log(LogStatus.WARNING, "launch App", "Trying to access WebDriver which is closed: SessionNotFound");
			else	
				extentTest.log(LogStatus.WARNING, "launch App", message);
			Assert.fail();
			return false;
		} catch (Exception ex) {
			extentTest.log(LogStatus.WARNING, "launch App", ex.getMessage());
			return false;
		}
		extentTest.log(LogStatus.FAIL, "launch App", "launchApp was unsuccessful for URL <B>" + URL + "</B>");
		return false;
	}
	
	/**
	 * Initialises the WebDriver
	 * 
	 * @param browserName
	 *            Accepted values are "IEXPLORE", "FIREFOX"
	 * @return boolean if WebDriver is launched successfully
	 * 
	 */
	public boolean initializeDriver(String browserName) {
		try {
			if(browserName.equalsIgnoreCase("IEXPLORE")){
				String ieDriverPath = USERDIR + File.separator + property.getProperty("ieDriverFileName");
				System.setProperty("webdriver.ie.driver", ieDriverPath);
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability("nativeEvents", false);    
                ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
                ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
                ieCapabilities.setCapability("disable-popup-blocking", true);
                ieCapabilities.setCapability("enablePersistentHover", true);
                ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				driver = new InternetExplorerDriver();
				
			}else if(browserName.equalsIgnoreCase("FIREFOX")){
				String geckoDriverPath = USERDIR + File.separator + property.getProperty("geckoDriverFileName");
				System.setProperty("webdriver.gecko.driver", geckoDriverPath);
				driver = new FirefoxDriver();				
			}else{
				extentTest.log(LogStatus.FAIL, "Valid Browser Names are [IEXPLORE, FIREFOX]", "Invalid Browser<B>" + browserName + "</B> has been passed");
				return false;
			}
						
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(PAGELOAD_WAIT_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			parentWindow = driver.getWindowHandle();
			return true;
		} catch (SessionNotCreatedException snfex) {
			if (snfex.getMessage().contains(
					"Protected Mode settings are not the same for all zones"))
				extentTest.log(LogStatus.WARNING, "launch App", "Protected Mode settings are not the same for all zones. Goto Tools --> internet Options --> Security (Enable Protected Mode should be enabled/disabled for all zone.)");
			else if(snfex.getMessage().toString().matches("session (.*) does not exist Command duration or timeout:.*"))
				extentTest.log(LogStatus.WARNING, "launch App", "Trying to access WebDriver which is closed: SessionNotFound");
			else	
				extentTest.log(LogStatus.WARNING, "launch App", snfex.getMessage());
			Assert.fail();
			return false;
		} catch (Exception ex) {
			extentTest.log(LogStatus.WARNING, "launch App", ex.getMessage());
			return false;
		}
	}
	
	/**
	 * Switches to default content
	 */
	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception ex) {
			ex.printStackTrace();
			extentTest.log(LogStatus.ERROR, "switchToDefaultContent", ex.getLocalizedMessage());
			Assert.fail();
		}
	}
	
	/**
	 * Switches to parent window when WebDriver was initialised
	 */
	public void switchToParentWindow(){
		try {
			driver.switchTo().window(parentWindow);
		} catch (Exception ex) {
			extentTest.log(LogStatus.ERROR, "switchToParentWindow", ex.getLocalizedMessage());
			Assert.fail();
		}
	}
	
	/**
	 * Waits for page to get loaded for default amount of timeout
	 * 
	 * @return boolean
	 */
	public boolean waitForPageToLoad(){
		try{
			for(int attempts = 1; attempts <= 5; attempts++){
				try{
					ExpectedCondition <Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver){
							return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
						}
					};
					snooze(100);
					WebDriverWait wait = new WebDriverWait(this.driver, WEBDRIVER_WAIT_TIMEOUT);
					wait.until(expectedCondition);
					break;
				} catch (TimeoutException tmex) {
					if (attempts == 5) {
						return false;
					} else {
						snooze(2000);
					}
				} catch (WebDriverException edex) {
					if (attempts == 5) {
						return false;
					} else {
						snooze(2000);
					}
				}
			}
			return true;
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}

	/**
	 * Opens a new blank tab
	 * @return true if tab is opened successfully otherwise false
	 */
	public boolean openBlankTab() {
		String initialHandle = driver.getWindowHandle();
		((JavascriptExecutor) driver).executeScript("window.open()");
		snooze(2000);
		for (String handle : driver.getWindowHandles()) {
			if(driver.switchTo().window(handle).getCurrentUrl().equalsIgnoreCase("about:blank")){
				extentTest.log(LogStatus.INFO, "New Tab is to be opened", "New Tab is opened");
				return true;
			}
		}
		
		driver.switchTo().window(initialHandle);
		extentTest.log(LogStatus.FAIL, "New Tab is to be opened", "New Tab could not be opened");
		return false;
	}
	
	/**
	 * Checks whether WebElement is present in DOM or not
	 * 
	 * @param byRef
	 *            By instance of WebElement
	 * @return boolean, true if WebElement is found otherwise false
	 */
	private boolean findElement(By byRef) {
		int attempts = 0;
		waitForPageToLoad();

		if (byRef == null)
			return false;
		
		try {
			while (attempts < MAX_ATTEMP) {
				try {
					element = driver.findElement(byRef);
					return true;
				} catch (StaleElementReferenceException serex) {
					attempts++;
					if (attempts == MAX_ATTEMP) {
						extentTest.log(LogStatus.FAIL, "findElement:= " + byRef.toString(), "In method <B>findElement</B> exceeded max attempts for checking StaleElementReferenceException</BR>" + serex.getMessage() + captureScreenShot());
						return false;
					} else {
						snooze(100);
					}
				}catch(InvalidElementStateException iesex){
					attempts++;
					if (attempts == MAX_ATTEMP) {
						extentTest.log(LogStatus.FAIL, "findElement:= " + byRef.toString(), "In method <B>findElement</B> exceeded max attempts for checking InvalidElementStateException</BR>" + iesex.getMessage() + captureScreenShot());
						return false;
					} else {
						snooze(100);
					}
				}
			}
		} catch (NoSuchElementException nsex) {
			extentTest.log(LogStatus.FAIL, "findElement:= " + byRef.toString(), "WebElement could not located" + captureScreenShot());
			return false;
		} catch (NoSuchWindowException isex) {
			extentTest.log(LogStatus.FAIL, "findElement:= " + byRef.toString(), "During location of WebElement Browser has been closed");
			return false;
		} catch (UnreachableBrowserException ubex) {
			extentTest.log(LogStatus.FAIL, "findElement:= " + byRef.toString(), "During location of WebElement Browser has been closed");
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * Types the value into WebElement/Control
	 * @param by
	 *            By instance of a WebElement
	 * @param value
	 *            The value that needs to entered to a WebElement
	 * @return boolean
	 */
	public boolean typeIn(By by, String value) {
		String enteredTextValue;
		
		// return false if not found
		if (!findElement(by))
			return false;
		
		element.clear();
		element.sendKeys(value);
		enteredTextValue = element.getAttribute("value");
		if (enteredTextValue.equals(value)) {
			extentTest.log(LogStatus.INFO, "Type In:= " + by.toString(), "Value has been set with <B>[" + enteredTextValue + "]</B>");
			return true;
		} else {
			extentTest.log(LogStatus.FAIL, "Type In:= " + by.toString(), "Value has been set with <B>[" + enteredTextValue + "]</B>" + captureScreenShot());
			return false;
		}
	}

	
	/**
	 * Performs click operation on a WebElement
	 * 
	 * @param by
	 *            By instance of a WebElement
	 */
	public boolean clickOn(By by) {
		// return false if WebElement is not found
		if (!findElement(by)){
			return false;
		}
		try {
			new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT)
					.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			extentTest.log(LogStatus.INFO, "Click On: " + by.toString(), by.toString() + " is clicked");
			return true;
		} catch (UnhandledAlertException uaex) {
			extentTest.log(LogStatus.INFO, "Click On: " + by.toString(), "Unhandled Alert is Present" + "</BR>" + captureScreenShot());
			return false;
		}
	}
	
	/**
	 * Performs click operation on a WebElement
	 * 
	 * @param by
	 *            By instance of a WebElement
	 */
	public boolean synchronizedClickOn(By by) {
		// return false if WebElement is not found
		if (!findElement(by)){
			return false;
		}
		try {
			new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT)
					.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			waitForPageToLoad();
			extentTest.log(LogStatus.INFO, "Click On: " + by.toString(), by.toString() + " is clicked");
			return true;
		} catch (UnhandledAlertException uaex) {
			extentTest.log(LogStatus.INFO, "Click On: " + by.toString(), "Unhandled Alert is Present" + "</BR>" + captureScreenShot());
			return false;
		}
	}
	
	/**
	 * Action Click on WebElement
	 * 
	 * @return boolean true if click was successful otherwise false
	 */
	public boolean actionClick(By by) {
		// return false if WebElement is not found
		if (!findElement(by))
			return false;
		try {
			new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT)
					.until(ExpectedConditions.elementToBeClickable(element));

			Actions action = new Actions(driver);
			action.moveToElement(element).click().perform();
			waitForPageToLoad();
			extentTest.log(LogStatus.INFO, "Action Click On: " + by.toString(), by.toString() + " is clicked");
			return true;
		} catch (UnhandledAlertException uaex) {
			extentTest.log(LogStatus.FAIL, "Action Click On: " + by.toString(), "Unhandled Alert is Present. Please handle it." + captureScreenShot());
			return false;
		}
	}
	
	/**
	 * Performs click operation on a WebElement
	 * 
	 * @param by
	 *            By instance of a WebElement
	 */
	public boolean jsClickOn(By by) {
		// return false if WebElement is not found
		if (!findElement(by)){
			return false;
		}
		try {
			new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT)
					.until(ExpectedConditions.elementToBeClickable(element));
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
			
			extentTest.log(LogStatus.INFO, "Click On: " + by.toString(), by.toString() + " is clicked");
			return true;
		} catch (UnhandledAlertException uaex) {
			extentTest.log(LogStatus.INFO, "Click On: " + by.toString(), "Unhandled Alert is Present" + "</BR>" + captureScreenShot());
			return false;
		}
	}
	
	/**
	 * Double Click on WebElement
	 * @param by Locator of WebElement
	 */
	public boolean doubleClick(By by) {
		// return false if WebElement is not found
		if (!findElement(by))
			return false;

		new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(element));
		Actions builder = new Actions(driver);
		builder.doubleClick(element).build().perform();
		waitForPageToLoad();
		extentTest.log(LogStatus.INFO, "Double Click On: " + by.toString(), by.toString() + " is clicked");
		return true;
	}
	
	public String captureScreenShot() {
		String screenShotFileName = new File(snapshotPath).getAbsolutePath() + "\\temp" + String.valueOf(System.currentTimeMillis()) + ".png";
		String htmlImg = "<p>Snapshot:<a href='"+ screenShotFileName +"' target=\"_blank\"><img width=\"100\" height=\"18\" src='"+ screenShotFileName +"'/></a></p>";		
		File srcFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(screenShotFileName));
			
		} catch (IOException e) {
			return null;
		}
		return htmlImg;
	}
	
	
	/**
	 * Checks whether WebElement is displayed or not
	 * 
	 * @param by
	 *            By instance of a WebElement
	 * @return boolean true if WebElement is displayed otherwise false
	 */
	public boolean isDisplayed(By by) {
		if (findElement(by)){
			return element.isDisplayed();
		}else
			return false;
	}

	/**
	 * Selects the value from DropDown list
	 * @param by Locator of a WebElement
	 * @param value value from dropdown that needs to be selected
	 */
	public boolean selectDropDownValue(By by, String value) {
		String selectedValue = null;
		if (value.equalsIgnoreCase("")) {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for :: " + by.toString(), "value is not provided in the parameter.");
			return false;
		}

		if (!findElement(by))
			Assert.fail();
		
		Select listElement = new Select(element);
		// Check whether the value needs to be selected contained in the SelectBox
		if (!assertTextInSelectElement(listElement, value)) {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "</B>Doesn't contain specified text - <B>" + value + "</B>");
			return false;
		}
		
		listElement.selectByValue(value);
		waitForPageToLoad();
		for (int attempt = 1; attempt <= 2; attempt++) {
			selectedValue = getSelectedText(by);
			if (selectedValue != null)
				break;
		}

		if (selectedValue == null) {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "getFirstSelectedOption returned null");
			return false;
		} else if (selectedValue.equals(value)) {
			extentTest.log(LogStatus.INFO, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "Selected with text - <B>" + selectedValue + "</B>");
			return true;
		} else {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "Selected with text - <B>" + selectedValue + "</B>");
			return false;
		}
	}
	
	/**
	 * Selects the Visible Text from DropDown list
	 * @param by Locator of a WebElement
	 * @param value value from dropdown that needs to be selected
	 */
	public boolean selectDropDownByVisibleText(By by, String value) {
		String selectedValue = null;
		if (value.equalsIgnoreCase("")) {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for :: " + by.toString(), "value is not provided in the parameter.");
			return false;
		}

		if (!findElement(by))
			Assert.fail();
		
		Select listElement = new Select(element);
		// Check whether the value needs to be selected contained in the SelectBox
		if (!assertTextInSelectElement(listElement, value)) {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "</B>Doesn't contain specified text - <B>" + value + "</B>");
			return false;
		}
		
		listElement.selectByVisibleText(value);
		waitForPageToLoad();
		for (int attempt = 1; attempt <= 2; attempt++) {
			selectedValue = getSelectedText(by);
			if (selectedValue != null)
				break;
		}

		if (selectedValue == null) {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "getFirstSelectedOption returned null");
			return false;
		} else if (selectedValue.equals(value)) {
			extentTest.log(LogStatus.INFO, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "Selected with text - <B>" + selectedValue + "</B>");
			return true;
		} else {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "Selected with text - <B>" + selectedValue + "</B>");
			return false;
		}
	}
	
	public boolean selectDropDownByVisibleText(String value,By by ) {
		if (value.equalsIgnoreCase("")) {
			extentTest.log(LogStatus.FAIL, "Select DropDown visible text for :: " + by.toString(), "value is not provided in the parameter.");
			return false;
		}

		if (!findElement(by))
			Assert.fail();
		
		Select listElement = new Select(element);
		// Check whether the value needs to be selected contained in the SelectBox
		if (!assertTextInSelectElement(listElement, value)) {
			extentTest.log(LogStatus.FAIL, "Select DropDown visible text for <B>[" + by.toString() + "]</B>", "</B>Doesn't contain specified text - <B>" + value + "</B>");
			return false;
		}
		
		listElement.selectByVisibleText(value);
		waitForPageToLoad();
		return true;
		
	}
	
	
	/**
	 * Selects the value by index from DropDown list
	 * @param by Locator of a WebElement
	 * @param index index the select option that needs to be selected
	 */
	public boolean selectDropDownByIndex(By by, int index) {
		String selectedValue = null;
		if (!findElement(by))
			return false;
		
		Select listElement = new Select(element);
		int size = listElement.getOptions().size();
		if (index >= size) {
			extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "Index [" +index+"] exceeds the size of Select options");
			return false;
		}
		
		listElement.selectByIndex(index);
		waitForPageToLoad();
		for (int attempt = 1; attempt <= 2; attempt++) {
			selectedValue = getSelectedText(by);
			if (selectedValue != null)
				return true;
		}

		extentTest.log(LogStatus.FAIL, "Select DropDown Value for <B>[" + by.toString() + "]</B>", "getFirstSelectedOption returned null");
		return false;
	}
	
	
	
	/**
	 * Returns the Selected Text from a WebElement
	 * 
	 * @param select
	 *            instance of Select Class
	 * @return Boolean True - if selection matches otherwise False
	 */
	private String getSelectedText(By byRef) {
		int attempts = 0;
		try {
			while (attempts < MAX_ATTEMP) {
				try {
					Select select = new Select(driver.findElement(byRef));
					return select.getFirstSelectedOption().getText();
				} catch (StaleElementReferenceException serex) {
					attempts++;
					if (attempts == MAX_ATTEMP) {
						extentTest.log(LogStatus.WARNING, "getSelectedText", "In method <B>getSelectedText</B> exceeded max attempts for checking StaleElementReferenceException</BR>"
								+ serex.getMessage());
						return null;
					} else {
						snooze(100);
					}
				}
			}
		}  catch (Exception ex) {
			return null;
		}

		return null;
	}
	
	/**
	 * Forces Current thread to wait for specified amount of time
	 * 
	 * @param timeout
	 *            timeout in milliseconds
	 */
	@SuppressWarnings("static-access")
	public void snooze(int timeout) {
		try {
			Thread.currentThread().sleep(timeout);
		} catch (InterruptedException e) {
			System.out.println("Sleep Interrupted (Exception Caused)");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the text of WebElement
	 * @param by Locator of WebElement
	 * @return String representation of the value contained by WebElement otherwise null
	 */
	public String getTextOf(By by) {
		return findElement(by) ? element.getText(): null;
	}
	
	/**
	 * Returns the list of WebElements
	 * @param by Locator of WebElement
	 * @return List
	 */
	public List<WebElement> locateElements(By by) {
		return driver.findElements(by);
	}
	
	/**
	 * Finds an element, if found returns an WebElement
	 * @param by Locator of WebElement
	 * @return WebElement if found otherwise null
	 */
	public WebElement locateElement(By by) {
		return findElement(by) ? element : null;
	}
	
	/**
	 * Returns the text contained in the specified cell.
	 * @param by
	 * 		By instance of a WebElement
	 * @param row
	 * 		The row number where the cell is located. The first row in the table is numbered 0.
	 * @param column
	 * 		The column number where the cell is located. The first column in the table is numbered 0. 
	 * @return
	 * 		the text contained in the specified cell
	 */
	public String getCellData(By by, int row, int column) {
		if (!findElement(by)){
			Assert.fail();
		}

		List<WebElement> rows = element.findElements(By.tagName("tr"));
		int rowCount = rows.size();
		if (row > rowCount) {
			extentTest.log(LogStatus.INFO, "GetCellData", "Mentioned row [" + row
					+ "] exceeds rowCount [" + rowCount + "]");
			return null;
		}

		List<WebElement> columns = rows.get(row - 1).findElements(
				By.xpath(".//*[local-name(.)='th' or local-name(.)='td']"));
		
		int columnCount = columns.size();
		if (column > columnCount) {
			extentTest.log(LogStatus.INFO, "GetCellData", "Mentioned column ["
					+ column + "] exceeds columnCount [" + columnCount + "]");
			return null;
		}
		return columns.get(column - 1).getText();
	}
	

	/**
	 * Returns the number of the first column found that contains a cell with
	 * the specified text
	 * 
	 * @param by
	 *            By instance of a WebElement
	 * @param value
	 *            The text string for which to search.
	 * @return A integer value
	 * 
	 */
	public int getColumnWithCellText(By by, String value) {
		int rowCount;
		if (!findElement(by))
			Assert.fail();

		// End if rowCount method returns -1
		rowCount = rowCount(by);
		if (rowCount <= 0)
			return -1;

		List<WebElement> rows = element.findElements(By.tagName("tr"));
		for (int iRow = 0; iRow < rows.size(); iRow++) {
			WebElement row = rows.get(iRow);
			List<WebElement> columns = row.findElements(By
					.xpath(".//*[local-name(.)='th' or local-name(.)='td']"));
			for (int iCol = 0; iCol < columns.size(); iCol++) {
				WebElement column = columns.get(iCol);
				String cellValue = column.getText().trim();
				if (cellValue.equals(value)) {
					return (iCol + 1);
				}
			}
		}
		return -1;
	}
	
	/**
	 * Returns the number of the first row found that contains a cell with the
	 * specified text
	 * 
	 * @param by
	 *            By instance of a WebElement
	 * @param value
	 *            The text string for which to search.
	 * @return A integer value
	 * 
	 */
	public int getRowWithCellText(By by, String value) {
		int rowCount;

		if (!findElement(by)) {
			Assert.fail();
		}
		
		// End if rowCount method returns -1
		rowCount = rowCount(by);
		if (rowCount <= 0)
			return -1;

		List<WebElement> rows = element.findElements(By.tagName("tr"));
		for (int iRow = 0; iRow < rows.size(); iRow++) {
			WebElement row = rows.get(iRow);
			List<WebElement> columns = row.findElements(By.xpath(".//*[local-name(.)='th' or local-name(.)='td']"));
			for (int iCol = 0; iCol < columns.size(); iCol++) {
				WebElement column = columns.get(iCol);
				String cellValue = column.getText();
				if (cellValue.equals(value)) {
					return (iRow + 1);
				}
			}
		}
		
		return -1;
	}
	
	
	/**
	 * Returns the number of rows in the table
	 * 
	 * @param by
	 *            By instance of a WebElement
	 * @return the row size of a WebElement
	 */
	public int rowCount(By by) {
		// return -1 if WebElement is not found
		if (!findElement(by))
			return -1;

		return element.findElements(By.tagName("tr")).size();
	}

	public boolean selectCheckBox(By by) {
		if (findElement(by)) {
			if (!element.isSelected()) {
				element.click();
				extentTest.log(LogStatus.PASS, element.toString() + "- is to be selected", element.toString() + "- is selected");
				return true;
			} else {
				extentTest.log(LogStatus.PASS, element.toString() + "- is to be selected", element.toString() + "- is already selected");
				return true;
			}
		}
		extentTest.log(LogStatus.FAIL, element.toString() + "- is to be selected", element.toString() + "- could not be selected");
		Assert.fail();
		return false;
	}
	
	
	public String getAttributeValueOf(By by, String attributeName) {
		return findElement(by) ? element.getAttribute(attributeName): null;
	}

	
	public int getElementsCount(By by) {
		return locateElements(by).size();
	}
	
	
	/**
	 * Wait for element to be visible for default timeout
	 * @param by
	 * 		By instance of WebElement
	 */
	public void waitForVisibleElement(By by) {
		if (driver instanceof InternetExplorerDriver) {
			int secs = 0;
			while (secs <= WEBDRIVER_WAIT_TIMEOUT) {
				snooze(1000);
				secs++;
				if (isDisplayed(by))
					break;
			}
		} else {
			new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT).until(ExpectedConditions.visibilityOf(locateElement(by)));
		}
	}
	
	/**
	 * Wait for element to be visible for default timeout
	 * @param by
	 * 			By instance of WebElement
	 * @param timeOut
	 * 			timeout in seconds
	 */
	public void waitForVisibleElement(By by, int timeOut) {
		if (driver instanceof InternetExplorerDriver) {
			int secs = 0;
			while (secs <= timeOut) {
				snooze(1000);
				secs++;
				if (isDisplayed(by))
					break;
				else
					continue;
			}
		} else {
			new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(locateElement(by)));
		}
	}
/**
 * Switch to specified Browser index
 * @param index
 * 				index of the Browser that needs to be switched
 */
	public void switchToBrowserTab(int index) {
		ArrayList<String> tabs = new ArrayList<String>(
				driver.getWindowHandles());
		if (tabs.size() > 0) {
			if(index < tabs.size()){
				driver.switchTo().window(tabs.get(index));
				waitForPageToLoad();
			}else{
				extentTest.log(LogStatus.ERROR, "Number of Browsers opened are: " + tabs.size(), index + " - browser tab is being referred which doesn't exist");
				Assert.fail();
			}
		} else{
			extentTest.log(LogStatus.ERROR, "Switching to other tab", "There is no other window opened");
			Assert.fail();
		}
	}
	
	/**
	 * Verify the value selected in combobox
	 * @param select
	 *          instance of Select Class
	 * @param selectText
	 *         	Text that should be verified in combobox
	 * @return boolean
	 * 			true if selection matches otherwise false
	 */
	private boolean assertTextInSelectElement(Select select, String selectText) {
		boolean isTextPresent = false;
		List<WebElement> allOptions = select.getOptions();
		for (WebElement webElement : allOptions) {
			if (webElement.getText().equals(selectText)) {
				isTextPresent = true;
				break;
			}
		}
		return isTextPresent;
	}
	
	public boolean isSelected(By locator) {
		return locateElement(locator).isSelected();
	}
}
