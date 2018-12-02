/*package com.nv.extendtestng.testlogic;


import com.nv.extendtestng.frameworkutils.WebDriverFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import com.nv.extendtestng.uimaps.BroadBand_SEMP_Locators;

public class BroadBand_SEMP_Logic extends WebDriverFactory {
	
	public BroadBand_SEMP_Logic(WebDriver driver, Map<String, String> dataBook) {
		super(driver, dataBook);
	}
	
	public boolean sempLogin() {

		String userName = dataBook.get("SempUserName"),
				password = dataBook.get("SempPassword"), url = dataBook.get("SempURL");
		String mainWindowsHandle = driver.getWindowHandle();
		boolean openedNewTab = openBlankTab();
//		if (openedNewTab.equalsIgnoreCase(""))
//		{
//			extentTest.log(LogStatus.FAIL, "Semp Login should be  Successful", "unable to open the semp page");
//			return false;
//		}
//		else
//		{
//			driver.switchTo().window(openedNewTab);
//			driver.navigate().to(url);
//		}
		driver.navigate().to(url);
		typeIn(BroadBand_SEMP_Locators.loginId, userName);
		typeIn(BroadBand_SEMP_Locators.password, password);
		clickOn(BroadBand_SEMP_Locators.btnLogin);
		waitForPageToLoad();

		if (isDisplayed(BroadBand_SEMP_Locators.lnkConfigurationManagement))

		{
			extentTest.log(LogStatus.PASS, "Semp Login should be Successful", "User is successfully logged in");
			return true;
		} else {

			extentTest.log(LogStatus.FAIL, "Semp Login should be  Successful", "User is not successfully logged in");
			return false;
		}
	}
	
	public void semplogout()
	{
		if (locateElements(BroadBand_SEMP_Locators.lnkLogout).size()>0) {
			clickOn(BroadBand_SEMP_Locators.lnkLogout);
			waitForPageToLoad();
			driver.close();		
			extentTest.log(LogStatus.PASS, "Semp Logout should be successful", "Used Logged out");			
		}
		else
		{
			extentTest.log(LogStatus.FAIL, "Semp Logout should be successful", "Used not Logged out");
			driver.close();
			return;
		}
	}

	public boolean sempSelectNavigation() {

		if (isDisplayed(BroadBand_SEMP_Locators.lnkConfigurationManagement))

		{
			extentTest.log(LogStatus.PASS, "Configration Management link should be displayed", "Configration Management link is displayed");
			clickOn(BroadBand_SEMP_Locators.lnkConfigurationManagement);
			waitForPageToLoad();

		} else {

			extentTest.log(LogStatus.FAIL, "Configration Management link should be displayed", "Configration Management link is not displayed");

		}

		if (isDisplayed(BroadBand_SEMP_Locators.lnkNotifications))

		{
			extentTest.log(LogStatus.PASS, "Login:","Notifications link is displayed");
			clickOn(BroadBand_SEMP_Locators.lnkNotifications);
			waitForPageToLoad();

		} else {
			extentTest.log(LogStatus.FAIL, "Login:","Notifications link is not displayed");
		}

		if (isDisplayed(BroadBand_SEMP_Locators.OrderStatusUpdate))
		{
			extentTest.log(LogStatus.PASS, "Login:","Order Status Drop down is displayed");
			return true;
		} else {

			extentTest.log(LogStatus.FAIL, "Login:","Order Status Drop down is not  displayed");
			return false;
		}		
	}

	public boolean sempTriggerKCI(String FlowKCIReceived)  {

		String orderType = null, KCIType = null, sellersItemIdentification = null;

		boolean blnresult = false;

		blnresult = sempLogin();

		if (!blnresult) {
//			Report.updateTestStepLog("sempLogin:", "Business Component Failed. Unable to proceed with Execution",
//					"Fail");
			
			extentTest.log(LogStatus.FAIL, "sempLogin:","Business Component Failed. Unable to proceed with Execution");
			
//			return false;
		}

		blnresult = sempSelectNavigation();
		if (!blnresult) {
			extentTest.log(LogStatus.FAIL, "sempSelectNavigation:","Business Component Failed. Unable to proceed with Execution");
			
		}



		if (FlowKCIReceived.contains("Acknowledged"))

			KCIType = "OrderStatusUpdate - Acknowledged (KCI - 1)";

		else if (FlowKCIReceived.contains("Committed"))

			KCIType = "OrderStatusUpdate - Committed (KCI - 2)";

		else if (FlowKCIReceived.contains("Completed"))

			KCIType = "OrderStatusUpdate - Completed (KCI - 3)";
		
		else if ((FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3300")))

			KCIType = "T2R";
				
		else {
			extentTest.log(LogStatus.FAIL, "KCIType","KCI type is incorrect");

			// TODO : Should exit from the function.
		}

		if (FlowKCIReceived.contains("Provide"))
			orderType = "Provision";
		else if (FlowKCIReceived.contains("Modify"))

			orderType = "Modify";

		else if (FlowKCIReceived.contains("Cease"))

			orderType = "Cease";
		
		else if ((FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3300")))

			orderType = FlowKCIReceived;
		
		else {
			extentTest.log(LogStatus.FAIL, "Order type","Order type is incorrect");

			// TODO : Should exit from the function.
		}

	
		if (FlowKCIReceived.contains("GEA"))
			sellersItemIdentification = "Generic Ethernet Access";

		else if (FlowKCIReceived.contains("TBC"))

			sellersItemIdentification = "TBC";

		else if (FlowKCIReceived.contains("TBC"))

			sellersItemIdentification = "TBC";
		
		else if ((FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3241")) || (FlowKCIReceived.equalsIgnoreCase("3300")))
		    sellersItemIdentification = dataBook.get("afarid");
		else {
			extentTest.log(LogStatus.FAIL, "sellersItemIdentification","sellersItemIdentification is incorrect");

			// TODO : Should exit from the function.
		}

		switch (KCIType) {

		case "OrderStatusUpdate - Acknowledged (KCI - 1)":
			blnresult = sempTriggerKCIAcknowledged(orderType, sellersItemIdentification);
			break;

		case "OrderStatusUpdate - Committed (KCI - 2)":
			blnresult = sempTriggerKCICommitted(orderType, sellersItemIdentification);
			break;

		case "OrderStatusUpdate - Completed (KCI - 3)":
			blnresult = sempTriggerKCICompleted(orderType, sellersItemIdentification);
			break;
			
		 case "T2R":
			blnresult = sempTriggerKCIT2R(orderType, sellersItemIdentification);
			break;	

		}
		
		semplogout();

		return blnresult;

	}

	public boolean sempTriggerKCIAcknowledged(String orderType, String sellersItemIdentification) {

		boolean businessComponentresult = false;

		String empVersion = dataBook.get("EMPVersion"),
				buyersId = dataBook.get("buyersId");

		String OrderStatusUpdate = "OrderStatusUpdate - Acknowledged (KCI - 1)";

		if (isDisplayed(BroadBand_SEMP_Locators.OrderStatusUpdate))

		{
			extentTest.log(LogStatus.PASS, OrderStatusUpdate,"User Should be able to trigger KCI");
			//selectDropDownByVisibleText(Test_BB_Semp.OrderStatusUpdate, OrderStatusUpdate);
			selectDropDownByVisibleText(OrderStatusUpdate,BroadBand_SEMP_Locators.OrderStatusUpdate);
			waitForPageToLoad();

		} else {

			extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"User is unable to trigger KCI");

		}

		if (isDisplayed(BroadBand_SEMP_Locators.EnvironmentDetails)
				&& getTextOf(BroadBand_SEMP_Locators.EnvironmentDetails).equalsIgnoreCase("Environment Details"))

		{

			extentTest.log(LogStatus.PASS, OrderStatusUpdate,"Environment Details page is displayed");

			clickOn(BroadBand_SEMP_Locators.stackName);
			waitForPageToLoad();

			selectDropDownByVisibleText(BroadBand_SEMP_Locators.sellersItemIdentification, sellersItemIdentification);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.empVersion, empVersion);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.orderType, orderType);
			waitForPageToLoad();
			typeIn(BroadBand_SEMP_Locators.buyersId, buyersId);

			jsClickOn(BroadBand_SEMP_Locators.Submit);
			waitForPageToLoad();

			String submitSuccess = driver.getPageSource();
			if (submitSuccess.contains("Your request has been sent")) {
				extentTest.log(LogStatus.PASS, OrderStatusUpdate,"KCI 1 is triggered succesfully");
				businessComponentresult = true;
			} else {
				extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"KCI 1 is not triggered");
				return false;
			}

		} else {

			extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"Environment Details page is not displayed");

		}

		return businessComponentresult;

	}

	public boolean sempTriggerKCICommitted(String orderType, String sellersItemIdentification) {

		boolean businessComponentresult = false;

		String empVersion = dataBook.get( "EMPVersion"),
				buyersId = dataBook.get( "buyersId"),
				appointmentDate = dataBook.get( "appointmentDate"),
				appointmentTimeslot = dataBook.get( "appointmentTimeslot"),
				appointmentChanged = dataBook.get( "appointmentChanged"),
				appointmentAllocated = dataBook.get( "appointmentAllocated"),
				appointmentNotNeeded = dataBook.get( "appointmentNotNeeded"),
				fttcAvailable = dataBook.get( "fttcAvailable"),
				outerTag = dataBook.get( "outerTag"),
				innerTag = dataBook.get( "innerTag"),
				serviceId = dataBook.get( "ServiceID"),
				installationDN = dataBook.get( "DirectoryNumber"),
				districtCode = dataBook.get( "ExchangeDistrictID");

		String OrderStatusUpdate = "OrderStatusUpdate - Committed (KCI - 2)";

		if (isDisplayed(BroadBand_SEMP_Locators.OrderStatusUpdate))

		{
			extentTest.log(LogStatus.PASS, OrderStatusUpdate,"User Should be able to trigger KCI");
			selectDropDownByVisibleText(OrderStatusUpdate,BroadBand_SEMP_Locators.OrderStatusUpdate);
			waitForPageToLoad();

		} else {

			extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"User is unable to trigger KCI");

		}

		if (isDisplayed(BroadBand_SEMP_Locators.EnvironmentDetails)
				&& getTextOf(BroadBand_SEMP_Locators.EnvironmentDetails).equalsIgnoreCase("Environment Details"))

		{

			extentTest.log(LogStatus.PASS, OrderStatusUpdate,"Environment Details page is displayed");
			clickOn(BroadBand_SEMP_Locators.stackName);
			waitForPageToLoad();

			selectDropDownByVisibleText(BroadBand_SEMP_Locators.sellersItemIdentification, sellersItemIdentification);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.empVersion, empVersion);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.orderType, orderType);
			waitForPageToLoad();
			typeIn(BroadBand_SEMP_Locators.buyersId, buyersId);
			if(dataBook.get("Order_Journey").equalsIgnoreCase("Provide"))
			{
			typeIn(BroadBand_SEMP_Locators.appointmentDate, appointmentDate);
			typeIn(BroadBand_SEMP_Locators.appointmentTimeslot, appointmentTimeslot);
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.appointmentChanged, appointmentChanged);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.appointmentAllocated, appointmentAllocated);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.appointmentNotNeeded, appointmentNotNeeded);
			waitForPageToLoad();
			typeIn(BroadBand_SEMP_Locators.fttcAvailable, fttcAvailable);
			typeIn(BroadBand_SEMP_Locators.outerTag, outerTag);
			typeIn(BroadBand_SEMP_Locators.innerTag, innerTag);
			typeIn(BroadBand_SEMP_Locators.serviceId, serviceId);
			typeIn(BroadBand_SEMP_Locators.installationDN, installationDN);
			typeIn(BroadBand_SEMP_Locators.districtCode, districtCode);
			}
			jsClickOn(BroadBand_SEMP_Locators.Submit);
			waitForPageToLoad();

			String submitSuccess = driver.getPageSource();
			if (submitSuccess.contains("Your request has been sent")) {
				extentTest.log(LogStatus.PASS, OrderStatusUpdate,"KCI 2 is triggered succesfully");
				businessComponentresult = true;
			} else {
				extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"KCI 2 is not triggered");

			}

		} else {

			extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"Environment Details page is not displayed");

		}
		return businessComponentresult;
	}

	public boolean sempTriggerKCICompleted(String orderType, String sellersItemIdentification) {

		boolean businessComponentresult = false;

		String empVersion = dataBook.get( "EMPVersion"),
				buyersId = dataBook.get( "buyersId"),
				appointmentDate = dataBook.get( "appointmentDate"),
				appointmentTimeslot = dataBook.get( "appointmentTimeslot"),
				appointmentChanged = dataBook.get( "appointmentChanged"),
				appointmentAllocated = dataBook.get( "appointmentAllocated"),
				appointmentNotNeeded = dataBook.get( "appointmentNotNeeded"),
				fttcAvailable = dataBook.get( "fttcAvailable"),
				outerTag = dataBook.get( "outerTag"),
				innerTag = dataBook.get( "innerTag"),
				serviceId = dataBook.get( "ServiceID"),
				installationDN = dataBook.get( "DirectoryNumber"),
				districtCode = dataBook.get( "ExchangeDistrictID");
		        

		String OrderStatusUpdate = "OrderStatusUpdate - Completed (KCI - 3)";

		if (isDisplayed(BroadBand_SEMP_Locators.OrderStatusUpdate))

		{
			extentTest.log(LogStatus.PASS, OrderStatusUpdate,"User Should be able to trigger KCI");
			selectDropDownByVisibleText(OrderStatusUpdate,BroadBand_SEMP_Locators.OrderStatusUpdate);
			waitForPageToLoad();

		} else {

			extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"User is unable to trigger KCI");

		}

		if (isDisplayed(BroadBand_SEMP_Locators.EnvironmentDetails)
				&& getTextOf(BroadBand_SEMP_Locators.EnvironmentDetails).equalsIgnoreCase("Environment Details"))

		{

			extentTest.log(LogStatus.PASS, OrderStatusUpdate,"Environment Details page is displayed");
			clickOn(BroadBand_SEMP_Locators.stackName);
			waitForPageToLoad();

			selectDropDownByVisibleText(BroadBand_SEMP_Locators.sellersItemIdentification, sellersItemIdentification);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.empVersion, empVersion);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.orderType, orderType);
			waitForPageToLoad();
			typeIn(BroadBand_SEMP_Locators.buyersId, buyersId);
			if(dataBook.get("Order_Journey").equalsIgnoreCase("Provide"))
			{
			typeIn(BroadBand_SEMP_Locators.appointmentDate, appointmentDate);
			typeIn(BroadBand_SEMP_Locators.appointmentTimeslot, appointmentTimeslot);
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.appointmentChanged, appointmentChanged);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.appointmentAllocated, appointmentAllocated);
			waitForPageToLoad();
			selectDropDownByVisibleText(BroadBand_SEMP_Locators.appointmentNotNeeded, appointmentNotNeeded);
			waitForPageToLoad();
			typeIn(BroadBand_SEMP_Locators.fttcAvailable, fttcAvailable);
			typeIn(BroadBand_SEMP_Locators.outerTag, outerTag);
			typeIn(BroadBand_SEMP_Locators.innerTag, innerTag);
			typeIn(BroadBand_SEMP_Locators.serviceId, serviceId);
			typeIn(BroadBand_SEMP_Locators.installationDN, installationDN);
			typeIn(BroadBand_SEMP_Locators.districtCode, districtCode);
			}
			jsClickOn(BroadBand_SEMP_Locators.Submit);
			waitForPageToLoad();

			String submitSuccess = driver.getPageSource();
			if (submitSuccess.contains("Your request has been sent")) {
				extentTest.log(LogStatus.PASS, OrderStatusUpdate,"KCI 3 is triggered succesfully");
				businessComponentresult = true;
			} else {
				extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"KCI 3 is not triggered");

			}

		} else {

			extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"Environment Details page is not displayed");

		}

		return businessComponentresult;
	}
	
	
	
	
	public boolean sempTriggerKCIT2R(String orderType, String sellersItemIdentification)  {

		boolean businessComponentresult = false;
		
		
		  String empVersion = dataBook.get("EMPVersion");
	      System.out.println("empVersion "+empVersion);
	      System.out.println("orderType "+orderType);
		  
		  String reportingReference = sellersItemIdentification;
		  
		  String maintenanceReference = sellersItemIdentification;
		  
		  
		   String status = "open";
		   String subStatus = "in progress";
		   String message1 = "NULL";
		   String clearCode1 = "0";
		
		   if (orderType.equalsIgnoreCase("3241")) {	   		   
			    message1 = "Notification: TR Creation Request is Pending";
		   }		   
		   else if (orderType.equalsIgnoreCase("3102")){			  
			    message1 = "Trouble Report Accepted: End User CPE is not disconnected, charges may incur";			   
		   }		   
           else if(orderType.equalsIgnoreCase("3200") ){			   
        	    message1 = "Notification: Engineer dispatched, further amendment may incur charges";			   
		   }
           
          else if(orderType.equalsIgnoreCase("3300") ){
			   
			    status = "Close";
			    subStatus = "Completed";
			    message1 = "Fault is Cleared and Closed";
			     clearCode1 = "83.9";
			   
			   
		   }
				 
		   snooze(5000);
		String OrderStatusUpdate = "TroubleReportStatusUpdate";
//			snooze(10000);
			//need to uncomment later

			if (isDisplayed(BroadBand_SEMP_Locators.FaultStatusUpdate))

			{
				extentTest.log(LogStatus.PASS, OrderStatusUpdate,"User Should be able to trigger KCI");
				selectDropDownByVisibleText(BroadBand_SEMP_Locators.FaultStatusUpdate, OrderStatusUpdate);
				waitForPageToLoad();
			} else {
				extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"User is unable to trigger KCI");			}
			//need to uncomment later

			if (isDisplayed(BroadBand_SEMP_Locators.FaultEnvironmentDetails)
					&& getTextOf(BroadBand_SEMP_Locators.FaultEnvironmentDetails).equalsIgnoreCase("Environment Details"))

			{

				extentTest.log(LogStatus.PASS, OrderStatusUpdate,"Environment Details page is displayed");
				selectDropDownByVisibleText(BroadBand_SEMP_Locators.empVersion, empVersion);
				waitForPageToLoad();				
								typeIn(BroadBand_SEMP_Locators.reportingReference, reportingReference);
				snooze(2000);
				typeIn(BroadBand_SEMP_Locators.maintenanceReference, maintenanceReference);
				snooze(2000);
				typeIn(BroadBand_SEMP_Locators.status, status);
				snooze(2000);
				typeIn(BroadBand_SEMP_Locators.subStatus, subStatus);
				waitForPageToLoad();
				
				//Select Message from dropdown
				Select sel=new Select(driver.findElement(By.name("message")));
				sel.selectByValue(message1);
				System.out.println("message1 is selected "+message1);
				 		
				//Click on add Message 
				clickOn(BroadBand_SEMP_Locators.messsageButton);
				waitForPageToLoad();
				
				Actions ac = new Actions(driver);
				ac.moveToElement(driver.findElement(By.xpath(".//*[contains(text(),'(TR)Trouble Report AcceptedXXXX')]"))).doubleClick().perform();
				
				snooze(3000);
	
				System.out.println("Double clicked on the message");
				
				boolean blnresult = isAlertPresent(20);
				System.out.println("blnresult  " +blnresult);
				
					if (blnresult) {
						Alert alert = driver.switchTo().alert();
						alert.accept();
						waitForPageToLoad();	
						driver.switchTo().defaultContent();
						
					}
					else
					{
						//Report.updateTestStepLog("Alert", "Alert popup not appeared", "Info");
						extentTest.log(LogStatus.INFO, "alert popup check","Alert Pop up is not dispalyed");
					}

				snooze(5000);
				 if (orderType.contains("3300")) {	   		   
					 typeIn(BroadBand_SEMP_Locators.clearCode,clearCode1);
				   }	
				
									
				 snooze(4000);
				jsClickOn(BroadBand_SEMP_Locators.Submit);
				waitForPageToLoad();
				snooze(4000);

				String submitSuccess = driver.getPageSource();
				if (submitSuccess.contains("Your request has been sent")) {
					extentTest.log(LogStatus.PASS, OrderStatusUpdate,"KCI  is triggered succesfully");
					businessComponentresult = true;
				} else {
					extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"KCI is not triggered");

				}

			} else {

				extentTest.log(LogStatus.FAIL, OrderStatusUpdate,"Environment Details page is not displayed");

			}

			return businessComponentresult;		
		
		
			
			
		
		
	}
	
	  protected boolean isAlertPresent(int alertWaitVal) {

	        try {

	                // driver.switchTo().alert();

	                WebDriverWait waitDriver = new WebDriverWait(driver, alertWaitVal);

	                if (waitDriver.until(ExpectedConditions.alertIsPresent()) == null)

	                        return false;

	                else

	                        return true;

	        } catch (NoAlertPresentException napex) {

	                System.out.println("No Alert present");

	                return false;

	        }
	  }

	
	
	public void trycall() {
		try {
			driver.get("http://10.213.247.145:61121/StubEMPUI/Login.do");

			snooze(1000);

			driver.navigate().to("www.google.com");
			driver.close();
		
		}

		catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
	
	
	//Semp Navigation for routing
	public void SempNavigationForRouting(){
		
		if (isDisplayed(BroadBand_SEMP_Locators.lnkConfigurationManagement))

		{
			extentTest.log(LogStatus.PASS, "Login:","Configration Management link is displayed");
			clickOn(BroadBand_SEMP_Locators.lnkConfigurationManagement);
			waitForPageToLoad();

		} else {

			extentTest.log(LogStatus.FAIL, "Login:","Configration Management link is not displayed");

		}

		if (isDisplayed(BroadBand_SEMP_Locators.lnkRouting))

		{
			extentTest.log(LogStatus.PASS, "Login:","Routing link is displayed");
			clickOn(BroadBand_SEMP_Locators.lnkRouting);
			waitForPageToLoad();

		} else {

			extentTest.log(LogStatus.FAIL, "Login:","Routing link is not displayed");

		}
		
		if (isDisplayed(BroadBand_SEMP_Locators.lnkRoutingMgmt))

		{
			extentTest.log(LogStatus.PASS, "Login:","Routing Management link is displayed");
			clickOn(BroadBand_SEMP_Locators.lnkRoutingMgmt);
			waitForPageToLoad();

		} else {

			extentTest.log(LogStatus.FAIL, "Login:","Routing Management link is not displayed");

		}
		
		
		int size = driver.findElements(By.xpath("//fieldset[legend[contains(text(),'Add/Edit Routing Entry')]]")).size();
		if (size>0){
			extentTest.log(LogStatus.PASS, "Routing Management page","Successfully Navigated to Routing Management page");
		}else
		{
			extentTest.log(LogStatus.PASS, "Routing Management page","Navigation to Routing Management page is Failed");
		}
		
		 
		
			
	}
	
	//DN Routing
	public void DNRouting(){
		
		SempNavigationForRouting();
		
		 String DN = dataBook.get( "DN");
		 String orderType = dataBook.get( "OrderType");
		 // Routing DN
		 if(orderType.equalsIgnoreCase("SEMP"))
		 {
			 if (!DN.isEmpty())
			 {
				 RoutingMsg(DN,"SEMP");
			 }
		 }
		 else if(orderType.equalsIgnoreCase("CVF"))
		 {
			if (!DN.isEmpty())
			{
				 RoutingMsg(DN,"CVF");
			}
		 }
		 else{
				 extentTest.log(LogStatus.FAIL, "DN OrderType","DN order type is invalid");
			 }
	}
		
	
	//NAD Routing
	public void NADRouting(){
		
		SempNavigationForRouting();
		String goldKey = dataBook.get( "GoldKey");
		 if (!goldKey.isEmpty()){
			 RoutingMsg(goldKey,"NAD");
			 }else{
				 extentTest.log(LogStatus.FAIL, "Gold Key","Gold key not available");
			}

	}
	
	
	
	public void RoutingMsg(String MsgKeyVal,String TypeOfOrder){
		
		String msgkeyVal = MsgKeyVal;
		
		switch (TypeOfOrder){
		
		case "NAD":
			addMsg(msgkeyVal,"AddAddress");
			snooze(3000);
			addMsg(msgkeyVal,"AddOrder");
			snooze(3000);
			addMsg(msgkeyVal,"AddAppointment");
			snooze(3000);
			addMsg(msgkeyVal,"AddLineCharacteristicsRequest");
			snooze(3000);
			addMsg(msgkeyVal,"AddLineCharacteristicsRequest2");
			snooze(3000);
			addMsg(msgkeyVal,"AddAppointmentDetailsQuery");
			snooze(3000);
			addMsg(msgkeyVal,"AddAddressDetailsQuery");
			snooze(3000);
			addMsg(msgkeyVal,"AddNetworkAvailabilityRequest");
			snooze(3000);
			addMsg(msgkeyVal,"AddAddressSearchQuery");
			snooze(3000);
			addMsg(msgkeyVal,"AddAddressMatchQuery");
			snooze(2000);
			//Test_BB_SempPage ts = new Test_BB_SempPage();
			validatedMsgsAdded(msgkeyVal,TypeOfOrder);
			//NADFlag = "NADRouted";
			//DataBook.putData( "NADFlag", NADFlag);
			//updateOutputValue("NADFlag", NADFlag);
			break;
		case "CVF":
			addMsg(msgkeyVal,"AddOrder");
			snooze(3000);
			addMsg(msgkeyVal,"AddAppointment");
			snooze(3000);
			addMsg(msgkeyVal,"AddLineCharacteristicsRequest2");
			snooze(3000);
			addMsg(msgkeyVal,"AddAppointmentAvailabilityRequest");
			snooze(3000);
			addMsg(msgkeyVal,"AddAppointmentDetailsQuery");
			snooze(2000);
			validatedMsgsAdded(msgkeyVal,TypeOfOrder);

			break;
		case "SEMP":
			addMsg(msgkeyVal,"AddAppointment");
			snooze(3000);
			addMsg(msgkeyVal,"AddLineCharacteristicsRequest2");
			snooze(3000);
			addMsg(msgkeyVal,"AddAppointmentAvailabilityRequest");
			snooze(3000);
			addMsg(msgkeyVal,"AddAppointmentDetailsQuery");
			snooze(2000);			
			validatedMsgsAdded(msgkeyVal,TypeOfOrder);
			
			break;
		default: 
		
		}
		
	}
	
	public void addMsg(String MsgVal,String MsgType ){
		
				
		//Enter Message Key Value
		typeIn(BroadBand_SEMP_Locators.msgKeyVal, MsgVal);
		waitForPageToLoad();
				
		//Enter Message type
		selectDropDownByVisibleText(BroadBand_SEMP_Locators.msgType, MsgType);
		waitForPageToLoad();
		
		//Enter Sender
		String Env = dataBook.get( "Environment");
        String Sender = "";
        if (Env.equalsIgnoreCase("MODELA")) {
                Sender = "BTW-Model-A";
                } else {
                Sender = "BTW-Model-C";
        }
        snooze(2000);
		selectDropDownByVisibleText(BroadBand_SEMP_Locators.Sender, Sender);
		waitForPageToLoad();
		
		//Enter Receiver
		selectDropDownByVisibleText(BroadBand_SEMP_Locators.Receiver, "Openreach-CVF");
		waitForPageToLoad();
		
		//Click on Add
		clickOn(BroadBand_SEMP_Locators.Add);
		waitForPageToLoad();
		snooze(5000);
		extentTest.log(LogStatus.PASS, "MessageAdd","Message Added is:<B>"+MsgType+"</B>");
		
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		int size = driver.findElements(By.xpath("//*[@id='sidebar']//center/b[text()='Operation performed successfully.']")).size();
		if (size==0){
			extentTest.log(LogStatus.FAIL, "Message Routing Status","Message Routing Failed");
		}
		
	}
	
	public void validatedMsgsAdded(String MsgKeyVal,String TypeOfOrder){
		
		String msgkeyVal = MsgKeyVal;
		
		
		//Enter Meassage Key to search
		typeIn(BroadBand_SEMP_Locators.MsgKeySearch,msgkeyVal);
		waitForPageToLoad();
		
		//Click on Search
		clickOn(BroadBand_SEMP_Locators.btnSearch);
		snooze(5000);
		
		switch (TypeOfOrder){
		
		case "NAD":
			
			int rowcount = rowCount(BroadBand_SEMP_Locators.MsgRoutingEntries);	
			int MsgCount = rowcount-1;   //Eliminating the first header row
			System.out.println("Row count"+MsgCount);
			if(MsgCount==10) {
				String MsgRouted ="";
				String allMsgRouted="";
				for(int i=2;i<=rowcount;i++){
					MsgRouted = getCellData(BroadBand_SEMP_Locators.MsgRoutingEntries,i,1);
					allMsgRouted = allMsgRouted +" " +MsgRouted;
					 	
				}
				
				if(allMsgRouted.contains("AddAddress")&&allMsgRouted.contains("AddOrder")&&allMsgRouted.contains("AddAppointment")&&allMsgRouted.contains("AddLineCharacteristicsRequest")&&allMsgRouted.contains("AddLineCharacteristicsRequest2")&&allMsgRouted.contains("AddAppointmentDetailsQuery")&&allMsgRouted.contains("AddAddressDetailsQuery")&&allMsgRouted.contains("AddNetworkAvailabilityRequest")&&allMsgRouted.contains("AddAddressSearchQuery")&&allMsgRouted.contains("AddAddressMatchQuery"))
					{extentTest.log(LogStatus.PASS, "NAD Routing","Successfully Routed with 9 Messages");}else
					{
						extentTest.log(LogStatus.FAIL, "NAD Routing","Failed NAD Routing");
					}
		
				
			}else{
				extentTest.log(LogStatus.FAIL, "NAD Message Count","Failed to Route with 9 Messages");
			}	
			break;
			
		case "SEMP":
			int Semprowcount = rowCount(BroadBand_SEMP_Locators.MsgRoutingEntries);	
			int SempMsgCount = Semprowcount-1;   //Eliminating the first header row
			System.out.println("Row count"+SempMsgCount);
			if(SempMsgCount==4) {
				String MsgRouted ="";
				String allMsgRouted="";
				for(int i=2;i<=Semprowcount;i++){
					MsgRouted = getCellData(BroadBand_SEMP_Locators.MsgRoutingEntries,i,1);
					allMsgRouted = allMsgRouted +" " +MsgRouted;
					 	
				}
				
				if(allMsgRouted.contains("AddAppointment")&&allMsgRouted.contains("AddLineCharacteristicsRequest2")&&allMsgRouted.contains("AddAppointmentAvailabilityRequest")&&allMsgRouted.contains("AddAppointmentDetailsQuery"))
					{extentTest.log(LogStatus.PASS, "SEMP Routing","Successfully Routed with 4 Messages");}else
					{
						extentTest.log(LogStatus.FAIL, "SEMP Routing","Failed NAD Routing");
					}
		
				
			}else{
				extentTest.log(LogStatus.FAIL, "SEMP Message Count","Failed to Route with 4 Messages");
			}
			break;
			
		case "CVF":
			int Cvfrowcount = rowCount(BroadBand_SEMP_Locators.MsgRoutingEntries);	
			int CvfMsgCount = Cvfrowcount-1;   //Eliminating the first header row
			System.out.println("Row count"+CvfMsgCount);
			if(CvfMsgCount==5) {
				String MsgRouted ="";
				String allMsgRouted="";
				for(int i=2;i<=Cvfrowcount;i++){
					MsgRouted = getCellData(BroadBand_SEMP_Locators.MsgRoutingEntries,i,1);
					allMsgRouted = allMsgRouted +" " +MsgRouted;
					 	
				}
				
				if(allMsgRouted.contains("AddLineCharacteristicsRequest2")&&allMsgRouted.contains("AddAppointment")&&allMsgRouted.contains("AddAppointmentAvailabilityRequest")&&allMsgRouted.contains("AddOrder")&&allMsgRouted.contains("AddAppointmentDetailsQuery"))
					{extentTest.log(LogStatus.PASS, "CVF Routing","Successfully Routed with 5 Messages");}else
					{
						extentTest.log(LogStatus.FAIL, "CVF Routing","Failed NAD Routing");
					}
		
				
			}else{
				extentTest.log(LogStatus.FAIL, "CVF Message Count","Failed to Route with 5 Messages");
			}
			
			break;
		
		}
		
		}
	
	
	
	
	
}// End of Class SemP Page
*/