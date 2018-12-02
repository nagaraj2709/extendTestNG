package com.nv.extendtestng.testlogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import com.nv.extendtestng.frameworkutils.WebDriverFactory;
import com.nv.extendtestng.uimaps.BroadBand_SaaS_Locators;

public class BroadBand_SaaS_Logic extends WebDriverFactory {

	public BroadBand_SaaS_Logic(WebDriver driver, Map<String, String> dataBook) {
		super(driver, dataBook);
	}

	public void fn_SAAS_Navigate_to_Ecoplus() {

		launchAPP(dataBook.get("SAASURL"));
		waitForPageToLoad();

		// if (dataBook.get("Browser").equalsIgnoreCase("IEXPLORE")) {
		// if (isDisplayed(BroadBand_SaaS_Locators.continueLnkForIe)) {
		// jsClickOn(BroadBand_SaaS_Locators.continueLnkForIe);
		// }
		// waitForPageToLoad();
		// }

		login();

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_MY_APPS)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_MY_APPS);
			waitForPageToLoad();
			snooze(5000);
			driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		} else {
			extentTest.log(LogStatus.FAIL, "Navigate to My Applications page", "unable to navigate to my apps page" + "used object xpath as::" + BroadBand_SaaS_Locators.SAAS_MY_APPS.toString());
			return;
		}

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Ecoplus_Link)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Ecoplus_Link);
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Navigate to Eco Plus page", "unable to click on EcoPlus Link" + "used object xpath as::" + BroadBand_SaaS_Locators.SAAS_Ecoplus_Link.toString());
			return;
		}

		for (int chkCounter = 1; chkCounter <= 15; chkCounter++) {
			if (driver.getTitle().contains("Eco Plus")) {
				waitForPageToLoad();
				break;
			} else {
				snooze(2000);
				waitForPageToLoad();
			}
		}

		if (driver.getTitle().contains("Eco Plus")) {
			extentTest.log(LogStatus.PASS, "Navigate to Eco Plus page", "Navigated to EcoPlus Page");
			waitForPageToLoad();
			System.out.println("Link finding is started.");
		} else {
			extentTest.log(LogStatus.FAIL, "Navigate to Eco Plus page", "unable to Navigate to EcoPlus Page");
			return;
		}
	}

	public boolean login() {
		String userName, password;
		userName = dataBook.get("UserName");
		password = dataBook.get("Password");

		if (!isDisplayed(BroadBand_SaaS_Locators.txtUserName)) {
			jsClickOn(BroadBand_SaaS_Locators.lnkBTWholeSale);
			waitForPageToLoad();
			enterLoginCredentials(userName, password);
		} else {
			enterLoginCredentials(userName, password);
		}

		// closeHelpUsPopup();
		snooze(4000); // Firefox issue
		jsClickOn(BroadBand_SaaS_Locators.btnLogin);
		snooze(15000);// As the application is triggered after a delay
		waitForPageToLoad();
		// Test_MyAppsPage.handle21CWarningScreen();
		waitForPageToLoad();

		if (isDisplayed(BroadBand_SaaS_Locators.lnkLogout)) {
			extentTest.log(LogStatus.PASS, "User Login", "User has been Logged in Successfully");
		} else {
			extentTest.log(LogStatus.FAIL, "User Login", "User has not been Logged in Successfully");
			return false;
		}

		closeFirstTimeUserPopup();
		return true;
	}

	public void fn_SAAS_Logout() {
		driver.switchTo().defaultContent();
		if(locateElements(By.xpath("//frame[@name='_sweclient']")).size() > 0){
			switchToFrame_EcoPlusPage();
		}
	
		System.out.println("fn_SAAS_Logout" + dataBook.get("OrderID"));
		updateOutputValue("OrderID", "OS013-25091364549");
		
		if (isDisplayed(BroadBand_SaaS_Locators.lnkLogout)) {
			jsClickOn(BroadBand_SaaS_Locators.lnkLogout);
			waitForPageToLoad();
			extentTest.log(LogStatus.PASS, "User Logout", "User has been Logged out Successfully");
		} else {
			extentTest.log(LogStatus.FAIL, "User Logout", "User has not been Logged outis unSuccessfull");
		}
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 */
	private void enterLoginCredentials(String userName, String password) {

		waitForVisibleElement(BroadBand_SaaS_Locators.txtUserName);
		if (isDisplayed(BroadBand_SaaS_Locators.txtUserName)) {
			typeIn(BroadBand_SaaS_Locators.txtUserName, userName);
			typeIn(BroadBand_SaaS_Locators.txtpassword, password);
			snooze(1000);
		} else {
			extentTest.log(LogStatus.FAIL, "User Login", "Username text box is not displayed");
			return;
		}

	}

	public void closeFirstTimeUserPopup() {

		for (int i = 1; i <= 3; i++) {
			if (isDisplayed(BroadBand_SaaS_Locators.popupFirstTimeUser)) {
				jsClickOn(BroadBand_SaaS_Locators.lnkCloseCross_FirstTimePopup);
				snooze(2000);
				if (!isDisplayed(BroadBand_SaaS_Locators.popupFirstTimeUser))
					extentTest.log(LogStatus.INFO, "Closing First Time Popup", "First Time popup has been closed");
				else {
					extentTest.log(LogStatus.FAIL, "Closing First Time Popup", "First Time popup has NOT been closed");
					return;
				}
			}
		}
	}

	public void fn_SAAS_Start_Provide() {

		switchToFrame_EcoPlusPage();
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Order_new_Services_Link);

		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

		// Select new account
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Select_Account_List)) {
			// changeLstOption(BB_PortalOR.SAAS_Select_Account_List,"BT IMS");
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_Select_Account_List, "BT IMS");
			waitForPageToLoad();
			extentTest.log(LogStatus.PASS, "Selecting Account", "Selected Account as BT IMS");
		} else {
			extentTest.log(LogStatus.FAIL, "Selecting Account", "unable to Select Account as BT IMS");
			return;
		}

		// click on broadband link
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Broadband_Link)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Broadband_Link);
			extentTest.log(LogStatus.PASS, "Click on Broadband account link", "clicked on Broadband link");
		} else {
			extentTest.log(LogStatus.FAIL, "Click on Broadband account link", "unable to click on Broadband link");
			return;
		}
	}

	public void fn_SAAS_Proivde_details() {
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_BroadBand_Access)) {
			extentTest.log(LogStatus.PASS, "Standard Service Page Check", "Standard Service page is available");
		} else {
			extentTest.log(LogStatus.FAIL, "Standard Service Page Check", "Standard Service page is not available");
			return;
		}

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Select_btn)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Select_btn);
			extentTest.log(LogStatus.PASS, "Standard Service Page Check", "Select button is available for WBC product");
		} else {
			extentTest.log(LogStatus.FAIL, "Standard Service Page Check", "Select button is not available for WBC product");
			return;
		}

		// Select Provide using DN option

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_ItemSelection_list)) {
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_WBC_ItemSelection_list, "Provide using DN");
			extentTest.log(LogStatus.PASS, "Order Type Check", "Order type selection box is available for WBC product");
			extentTest.log(LogStatus.PASS, "Order Type Check", "Order type selection made as 'Provide using DN'");
		} else {
			extentTest.log(LogStatus.FAIL, "Order Type Check", "Order type selection box is not available for WBC product");
			return;
		}

		String DNNumber = dataBook.get("buyersId");

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_DN_Text)) {
			typeIn(BroadBand_SaaS_Locators.SAAS_WBC_DN_Text, DNNumber);
		} else {
			extentTest.log(LogStatus.FAIL, "Order Type Check", "Order type selection box is not available for WBC product");
			return;
		}

		snooze(1000);
		String PostCode = dataBook.get("PostCode");
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_PostCode_Text)) {
			typeIn(BroadBand_SaaS_Locators.SAAS_WBC_PostCode_Text, PostCode);
			extentTest.log(LogStatus.PASS, "Post Code Check", "Post Code Edit box is available for WBC product, Post Code Entered as::" + PostCode);
		} else {
			extentTest.log(LogStatus.FAIL, "Order Type Check", "Order type selection box is not available for WBC product");
			return;
		}

		// check availability
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_check_Avaibilink_link)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_check_Avaibilink_link);
			extentTest.log(LogStatus.PASS, "Check Availability Button check", "Check avaiability button exist and clicked on the same");

		} else {
			extentTest.log(LogStatus.FAIL, "Check Availability Button check", "Check avaiability button does not exist");
			return;
		}

		String AccessTechnology = dataBook.get("AccessTechnology");
		// Check for the Access technology selection Availability
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Access_Technology_Edit)) {
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_WBC_Access_Technology_Edit, AccessTechnology);
			extentTest.log(LogStatus.PASS, "Access technology Availability check", "Access Technology Exist and Selected -FTTC");
		} else {
			extentTest.log(LogStatus.FAIL, "Access technology Availability check", "Access Technology does not Exist");
			return;
		}

		// Check for the Result display after access technology selected
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Result_Details)) {
			extentTest.log(LogStatus.PASS, "Check Availability - Result Details check", "Results are available");

		} else {
			extentTest.log(LogStatus.FAIL, "Check Availability - Result Details check", "Results are not available");
			return;

		}

		// Click on Continue
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Continue)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_Continue);
			extentTest.log(LogStatus.PASS, "Click on Continue", "Clicked on continue succesfully");
		} else {
			extentTest.log(LogStatus.FAIL, "Click on Continue", "Clicked on Continue failed");
			return;
		}

		for (int i = 1; i <= 100; i++) {

			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Customer_Required_Date)) {
				break;
			} else {
				snooze(2000);
			}
		}

		driver.switchTo().defaultContent();
		switchToFrame_EcoPlusPage();
		// Check for the Customer Required Date Existence
		snooze(2000);
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Customer_Required_Date)) {
			System.out.println("customer requied date Filed check");
			extentTest.log(LogStatus.PASS, "Check for Customer Required Date", "Customer Required Date is Present in Item details");
			String currentCRDtime = getAttributeValueOf(BroadBand_SaaS_Locators.SAAS_WBC_CRD_Date, "value");
			String saasUrl = dataBook.get("SAASURL");

			if ((currentCRDtime != "") || saasUrl.equalsIgnoreCase("modelc")) {
				String crd = CustomerRequired_Date();
				typeIn(BroadBand_SaaS_Locators.SAAS_WBC_CRD_Date, crd);
				extentTest.log(LogStatus.PASS, "Check for Customer Required Date", "Customer Required Date is not set to standard default time. This is a defect. Raise PR if not already rised.Else Ignore this message");
				extentTest.log(LogStatus.INFO, "Check for Customer Required Date", "Customer Required Date is not set to standard default time. Script set to time as::" + crd + "to proceed further.");
			}

			extentTest.log(LogStatus.PASS, "Check for Customer Required Date", "Customer Required Date is value is::" + currentCRDtime);

			String Parent_Window = driver.getWindowHandle();
			// Enter Billing Account number
			String billingAcc = dataBook.get("BillingAcc");
			typeIn(BroadBand_SaaS_Locators.SAAS_WBC_Billing_Account, billingAcc);
			driver.findElement(By.xpath("//tr[td[contains(text(),'Billing account')]]//input")).sendKeys(Keys.TAB);
			snooze(5000);
			driver.switchTo().window(Parent_Window);
			driver.switchTo().defaultContent();
			switchToFrame_EcoPlusPage();

			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Retailer_ID)) {
				typeIn(BroadBand_SaaS_Locators.SAAS_WBC_Retailer_ID, "DFR");
				extentTest.log(LogStatus.PASS, "Retailer Id check", "Retailer id is  available and entered as 'DFR'");
			} else {
				extentTest.log(LogStatus.FAIL, "Retailer Id check", "Retailer id is not available");
				return;
			}

			waitForPageToLoad();

			if ((AccessTechnology.equalsIgnoreCase("FTTC")) || (AccessTechnology.equalsIgnoreCase("FTTP"))) {
				// Enter Site Contact
				typeIn(BroadBand_SaaS_Locators.SAAS_WBC_First_Name, "KRISHNA");
				waitForPageToLoad();
				typeIn(BroadBand_SaaS_Locators.SAAS_WBC_Last_Name, "PHANI");
				waitForPageToLoad();

				// To click on search button
				if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Search)) {
					jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_Search);
					extentTest.log(LogStatus.PASS, "Click on Search button for first name and last name", "Clicked on search button succesfully");
					waitForPageToLoad();
					snooze(5000);
					switchToBrowserTab(1);
					snooze(2000);
					if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Record_Selection)) {
						extentTest.log(LogStatus.PASS, "Contact Details Selection ", "search record is available");
						jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_Record_Select_Btn);
						extentTest.log(LogStatus.PASS, "Contact Details Selection ", "Clicked on 'select' button");
						snooze(2000);
					} else {
						extentTest.log(LogStatus.FAIL, "Contact Details Selection ", "search record is not available. Recheck your selection criteria");
						return;
					}
				} else {
					extentTest.log(LogStatus.FAIL, "Click on Search button for first name and last name", "unable to Click on search button");
					return;
				}
			}
			switchToBrowserTab(0);
			driver.switchTo().defaultContent();
			switchToFrame_EcoPlusPage();
			System.out.println(" === continue button ===");
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Continue)) {
				jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_Continue);
				extentTest.log(LogStatus.PASS, "Click on Continue", "Clicked on Continue succesfully");

			} else {
				extentTest.log(LogStatus.FAIL, "Click on Continue", "Clicked on Continue failed");
				return;
			}

		} else {
			extentTest.log(LogStatus.FAIL, "Check for Customer Required Date", "Customer Required Date is not Present in Item details");
			return;

		}

	}

	public void fn_SAAS_Product_Details() {
		waitForPageToLoad();
		// Switch to frame to enter managed install module details.
		driver.switchTo().defaultContent();
		switchToFrame_EcoPlusPage();
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='CfgMainFrame']")));
		String AccessTechnology = dataBook.get("AccessTechnology");
		// To select the manged install module 1 option check box
		if ((AccessTechnology.equalsIgnoreCase("FTTC")) || (AccessTechnology.equalsIgnoreCase("FTTP"))) {
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_ProductOptions_Managed_Install_Module1)) {
				extentTest.log(LogStatus.PASS, "Check for Managed Install Module1 check box", "Managed Install Module1 check box is available in the product options page");
				selectCheckBox(BroadBand_SaaS_Locators.SAAS_WBC_ProductOptions_Managed_Install_Module1);
				snooze(5000);
				waitForPageToLoad();
			} else {
				extentTest.log(LogStatus.FAIL, "Check for Managed Install Module1 check box", "Managed Install Module1 check box is not available in the product options page");
				return;
			}

			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_ProductOptions_Managed_Install_Module1_Select)) {
				extentTest.log(LogStatus.PASS, "Check for Managed Install Module1 select options", "Managed Install Module1 select option is available in the product options page");
				selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_WBC_ProductOptions_Managed_Install_Module1_Select, "Basic module (hub and computer)");
				extentTest.log(LogStatus.PASS, "Check for Managed Install Module1 select options", "Managed Install Module1 select option selected as 'Basic module (hub and computer)'");
				waitForPageToLoad();
				snooze(1000);

			} else {
				extentTest.log(LogStatus.FAIL, "Check for Managed Install Module1 select options", "Managed Install Module1 select option is not available in the product options page");
				return;
			}

		}

		// click on //img[@title='Done'] image

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_ProductOptions_done_Img)) {
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			extentTest.log(LogStatus.PASS, "Check for Done button", "Done button is available");
			snooze(10000);
			waitForPageToLoad();
			jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_ProductOptions_done_Img);
			snooze(10000);
		} else {
			extentTest.log(LogStatus.FAIL, "Check for Done button", "Done button is not available");
			return;
		}

	}

	public void fn_SAAS_Continue_From_Basket() {
		snooze(4000);
		driver.switchTo().defaultContent();
		switchToFrame_EcoPlusPage();
		String parentwindowhandle = driver.getWindowHandle();
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Continue)) {
			// jsClickOn(BB_PortalOR.SAAS_WBC_Continue);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(BroadBand_SaaS_Locators.SAAS_WBC_Continue));

			// Report.updateTestStepLog("Click on Continue","Clicked on continue
			// succesfully", "Pass");
			extentTest.log(LogStatus.PASS, "Click on Continue", "Clicked on continue succesfully");

			for (int intcount = 1; intcount <= 100; intcount++) {
				if (driver.getWindowHandles().size() >= 2) {
					System.out.println("fn_SAAS_Continue_From_Basket Browser size is found to be >=2");
					break;
				} else {
					snooze(10000);
				}
			}
		} else {
			// Report.updateTestStepLog("Click on Continue","Clicked on Continue
			// failed", "Fail");
			extentTest.log(LogStatus.FAIL, "Click on Continue", "Clicked on continue succesfully");
			return;
		}
		snooze(5000);

		switchToBrowserTab(1);

		// if (dataBook.get("Browser").equalsIgnoreCase("IEXPLORE")) {
		// if (isDisplayed(BroadBand_SaaS_Locators.continueLnkForIe)) {
		// jsClickOn(BroadBand_SaaS_Locators.continueLnkForIe);
		// }
		// waitForPageToLoad();
		// }
		boolean validation_Results_window = false;

		for (int i = 1; i <= 30; i++) {
			snooze(3000);
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_OrderValidation_Results)) {
				extentTest.log(LogStatus.PASS, "Checking for validation results window", "Validation results windows is displayed");
				validation_Results_window = true;
				break;
			} else if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_OrderValidation_Cease_Results)) {

				extentTest.log(LogStatus.PASS, "Checking for validation results window", "Validation results windows is displayed");
				validation_Results_window = true;
				break;
			}
			String pageSource = driver.getPageSource();
			if(pageSource.contains("Order validation results") || pageSource.contains("Validation results")){
				validation_Results_window = true;
				break;
			}
		}
		if (!validation_Results_window) {
			extentTest.log(LogStatus.FAIL, "Checking for validation results window", "Validation results windows is not displayed");
			return;
		}

		boolean ordervalidation_Results = false;
		for (int i = 1; i <= 30; i++) {
			snooze(3000);
			// Checking for successful message.
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_OrderValidation_conitune)) {
				jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_OrderValidation_conitune);
				snooze(10000);
				driver.switchTo().window(parentwindowhandle);

				extentTest.log(LogStatus.PASS, "Checking for validation results window", "Validation results are successful");
				ordervalidation_Results = true;
				break;
			}
		}

		if (!ordervalidation_Results) {
			extentTest.log(LogStatus.INFO, "Checking for validation results window", "Validation results windows is not displayed");
			return;
		}
	}

	public void fn_SAAS_Confirm_Order_Details() {

		switchToBrowserTab(0);
		// swithToWindow("Eco Plus");
		System.out.println("Current URL::" + driver.getCurrentUrl());
		System.out.println("Current URL::" + driver.getTitle());

		driver.switchTo().defaultContent();
		switchToFrame_EcoPlusPage();
		switchToFrame_EcoPlusPage();
		int Rvalue = ThreadLocalRandom.current().nextInt(10, 2000 + 1);

		// To Enter Order Reference
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Order_Ref)) {
			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order Refernce Field is available");
			String Oref = "AutoOrderRef_" + String.valueOf(Rvalue);
			typeIn(BroadBand_SaaS_Locators.SAAS_WBC_Order_Ref, Oref);
			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order Refernce entered as:" + Oref);
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Order Refernce Field is not available");
			return;
		}

		// To Enter Order Description.
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Order_Desc)) {

			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order Description Field is available");
			String Oref = "AutoOrderDesc_" + String.valueOf(Rvalue);
			typeIn(BroadBand_SaaS_Locators.SAAS_WBC_Order_Desc, Oref);
			;
			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order Refernce entered as:" + Oref);
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Order Description Field is not available");
			return;
		}

		// To Enter Order Update method.
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Order_Update_method)) {
			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order Update Method Field is available");
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_WBC_Order_Update_method, "Email");
			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order Update entered as:Email");
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Order Update Method Field is not available");
			return;
		}

		// String Ordertype = DataBook.getData("testdata","Order_Journey" );
		String Ordertype = dataBook.get("Order_Journey");
		if (Ordertype.equalsIgnoreCase("Provide")) {

			// To Enter Order Update method.
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details)) {
				extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order confirm Field is available");
				selectCheckBox(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details);
				extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order confirm Field is selected");
				waitForPageToLoad();
			} else {
				extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Order confirm Field is not available");
				return;
			}
		}

		else if ((Ordertype.equalsIgnoreCase("Cease"))) {
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details)) {
				extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order confirm Field is available");
				// selectCheckBox(BB_PortalOR.SAAS_WBC_confirm_Order_Details);
				if (!driver.findElement(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details_chkbox).isSelected()) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details));
				}
				extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order confirm Field is selected");
				snooze(5000);
			} else {
				extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Order confirm Field is not available");
				return;
			}

		} else {
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details_chkbox)) {
				extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order confirm Field is available");
				if (!driver.findElement(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details_chkbox).isSelected()) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(BroadBand_SaaS_Locators.SAAS_WBC_confirm_Order_Details_chkbox));
				}

				extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order confirm field check box is selected");
				snooze(5000);
			} else {
				// Report.updateTestStepLog("Order Confirmation Page","Order
				// confirm Field checkbox is not available", "Fail");
				extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Order confirm Field checkbox is not available");
				return;
			}
		}

		// To click on submit button
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Submit_btn)) {
			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Submit button is available");
			// jsClickOn(BB_PortalOR.SAAS_WBC_Submit_btn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(BroadBand_SaaS_Locators.SAAS_WBC_Submit_btn));
			extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Clicked on Submit button");
			snooze(5000);
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Portal_Order)) {
				String orderref = getTextOf(BroadBand_SaaS_Locators.SAAS_Portal_Order);
				updateOutputValue("OrderID", "OS01" + orderref);
				extentTest.log(LogStatus.PASS, "Order Confirmation Page", "Order Number is::" + orderref + captureScreenShot());

			} else {
				extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Order Number is not displayed::");
				return;
			}

		} else {
			extentTest.log(LogStatus.FAIL, "Order Confirmation Page", "Submit button is not available");
			return;
		}
	}

	// Stores current window handle
	public boolean swithToWindow(String title) {
		// String mainWindowsHandle;
		String mainWindowsHandle = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles(); // Gets all the
															// available windows

		for (String handle : handles) {
			System.out.println("Current title is::" + driver.getTitle());
			driver.switchTo().window(handle); // switching back to each window
												// in loop
			if (driver.getTitle().equals(title)) // Compare title and if title
													// matches stop loop and
													// return true
				return true; // We switched to window, so stop the loop and come
								// out of function with positive response
		}
		driver.switchTo().window(mainWindowsHandle); // Switch back to original
														// window handle
		return false; // Return false as failed to find window with given title.
	}

	public void SAAS_Start_cease_journey() {
		switchToFrame_EcoPlusPage();
		// String DNNumber = DataBook.getData("testdata", "buyersId");
		String DNNumber = dataBook.get("buyersId");
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Modify_Cease_Services_Link)) {

			jsClickOn(BroadBand_SaaS_Locators.SAAS_Modify_Cease_Services_Link);

			extentTest.log(LogStatus.PASS, "Eco Plus - Check for Modify and Cease Services Link", "Modify and cease services link is exist");
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - Check for Modify and Cease Services Link", "Modify and cease services link doesnot exist");
			return;
		}

		// To Select BT IMS account
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Select_accout)) {

			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_Select_accout, "BT IMS");
			extentTest.log(LogStatus.PASS, "Eco Plus - Account Selection", "Account selected as BT IMS");

		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - Account Selection", "Account not selected as BT IMS");
			return;
		}

		// To Enter the DN number
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_DN)) {

			typeIn(BroadBand_SaaS_Locators.SAAS_DN, DNNumber);
			extentTest.log(LogStatus.PASS, "Eco Plus - DN Updates", "Directory Number is entered as::" + DNNumber);

		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - DN Updates", "Unable to enter Directory Number");
			return;
		}

		// To select the Status
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Status)) {

			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_Status, "Active");
			extentTest.log(LogStatus.PASS, "Eco Plus - Status selection", "Status is selected as 'Active'");

		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - Status selection", "Status is not selected as 'Active'");
			return;
		}

		snooze(2000);
		waitForPageToLoad();
		// To click on search button
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_search)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_search);
			extentTest.log(LogStatus.PASS, "Eco Plus - Search button - Check Availability", "Search button is available");

		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - Search button - Check Availability", "Search button is not available");
			return;
		}

	}

	public void SAAS_Cease_Option_Selection() {
		snooze(4000);
		// To check that cease option is available or not..
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_select_Cease_select)) {
			// selectDropDownValue(BB_PortalOR.SAAS_select_Cease_select,"Cease");
			driver.findElement(By.xpath("//select")).sendKeys("CEASE");
			extentTest.log(LogStatus.PASS, "Eco Plus - Cease option - Check Availability", "Cease option is available and same is selected");

		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - Cease option - Check Availability", "Cease option is not available");
			return;
		}

		// To click on go button..
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_select_Cease_Go_btn)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_select_Cease_Go_btn);
			extentTest.log(LogStatus.PASS, "Eco Plus - Cease option - Check Availability", "Go button is available and same is selected");
			waitForPageToLoad();
			try{
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				if(driver.findElement(By.xpath("//td[font[contains(text(),'You cannot cease or modify this asset as there is already an open order in progress against this asset.')]]")).isDisplayed()){
					extentTest.log(LogStatus.FAIL, "There should be no open orders", "There is open order." + captureScreenShot());
					return;
				}
			}catch(Exception ex){
				;
			}
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - Go button - Check Availability", "Go button is not available");
			return;
		}

	}

	public void SAAS_CRD_and_Cease_Reason_updates() {

		// To check CRD
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_CRD_Validation)) {
			String CRDDate = getTextOf(BroadBand_SaaS_Locators.SAAS_CRD_Validation);
			extentTest.log(LogStatus.PASS, "Customer Required Date Field- Check Availability", "Field is available and value is::" + CRDDate);

		} else {
			extentTest.log(LogStatus.FAIL, "Customer Required Date Field- Check Availability", "Field is  not available");
			return;
		}

		// String Cease_Reason = DataBook.getData("testdata", "Cease_Reason");
		String Cease_Reason = dataBook.get("Cease_Reason");
		// to select reason
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_select_Cease_Reason)) {
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_select_Cease_Reason, Cease_Reason);
			extentTest.log(LogStatus.PASS, "Eco Plus - Cease reason selection - Check Availability", "cease reason option is available and updated as::" + Cease_Reason);

		} else {
			extentTest.log(LogStatus.FAIL, "Eco Plus - Cease reason selection - Check Availability", "cease reason option is not available");
			return;
		}

		// to click on continue button
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Continue_btn)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Continue_btn);
			extentTest.log(LogStatus.PASS, "Conitnue button - Check Availability", "continue button is available and same is selected");

		} else {
			extentTest.log(LogStatus.FAIL, "Conitnue button - Check Availability", "continue button is not available");
			return;
		}

	}

	// for Data
	public void fn_SAAS_Data_Track_Orders() {

		snooze(10000);
		switchToFrame_EcoPlusPage();
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_TrackOrder)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_TrackOrder);
			extentTest.log(LogStatus.PASS, "Track Order Check", "Track Order is available and clicked on the same");
		} else {
			extentTest.log(LogStatus.FAIL, "Track Order Check", "Track Order is not available and clicked on the same");
			return;
		}

		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

		// Select new account
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Select_Data_account)) {

			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_Select_Data_account, "BULLDOG COMMUNICATIONS LTD");
			extentTest.log(LogStatus.PASS, "Selecting Account", "Select Account as 'BULLDOG COMMUNICATIONS LTD'");
		} else {
			extentTest.log(LogStatus.FAIL, "Selecting Account", "unable to Select Account as 'BULLDOG COMMUNICATIONS LTD'");
			return;
		}

		// String strBTRef = DataBook.getData("testData", "BTRef");
		String strBTRef = dataBook.get("BTRef");
		// enter BT ref
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_BT_Ref)) {
			typeIn(BroadBand_SaaS_Locators.SAAS_BT_Ref, strBTRef);
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "BT Reference Field is entered as::" + strBTRef);
		} else {
			// Report.updateTestStepLog("Data - Cancel Journey", "BT Reference
			// Field is not available", "Fail");
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "BT Reference Field is not available");
			return;
		}

		// click on search Button button
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_WBC_Search)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_WBC_Search);
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "Search button is clicked");
			snooze(10000);
			fn_Saas_Data_verify_Order_Details_And_click_on_cancelButton(strBTRef);
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "Search button is not available");
			return;
		}
	}

	public void fn_Saas_Data_verify_Order_Details_And_click_on_cancelButton(String strBTRef) {

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_OrderDetails_Verify)) {
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "Order details page is visible");
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "Order details page is not visible");
			return;
		}

		if (strBTRef != "" && driver.findElements(By.xpath("//tr[td[contains(text(),'BT reference')]]//td//span[contains(text(),'" + strBTRef + "')]")).size() == 1) {
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "check for BT refernce[" + strBTRef + "] is successful");
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "check for BT refernce[" + strBTRef + "] is not available");
			return;
		}

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Designcomplete_status)) {
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "Given BT Refence staus is in 'Design complete'");
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "Given BT Refence staus is not 'Design complete'");
			return;
		}

		// ==============================================================================================================
		// To click on cancel button
		// ==============================================================================================================
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_CancelButton)) {
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "Cancel Button is available");
			jsClickOn(BroadBand_SaaS_Locators.SAAS_CancelButton);
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "Cancel Button is clicked");

		} else {
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "Cancel Button is not available");
			return;
		}
		// ==============================================================================================================
		// Check for cancellation Reason
		// ==============================================================================================================
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_CancelReason)) {
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "Cancel reason option is available");
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_CancelReason, "Change to customer requirement");
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "Cancel reason option selected as ''Change to customer requirement");
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "Cancel reason option is not available");
			return;
		}
		// ==============================================================================================================
		// To click on continue button
		// ==============================================================================================================
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_ContinueButton)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_ContinueButton);
			extentTest.log(LogStatus.PASS, "Data - Cancel Journey", "contunue button is available and its clicked");
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Cancel Journey", "contunue button is not available");
			return;
		}

	}

	// -Provide - Data Journey

	public void fn_Saas_Provide_Etherway() {
		// To click on My Orders page.
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Navigate_To_MyOrders);

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Place_New_Order_button)) {
			extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "Place New Order menu is displayed");
			snooze(3000);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Place_New_Order_button);
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_Place_New_Order_Account_select, "BULLDOG COMMUNICATIONS LTD");
			snooze(2000);

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			jsClickOn(BroadBand_SaaS_Locators.SAAS_clcikon_Etherway_Link);

			// ========================================================================================================

			this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_select_network, false);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_select_network);
			this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_search_network, false);
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_search_network)) {

				jsClickOn(BroadBand_SaaS_Locators.SAAS_search_network);
				this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_search_Field, false);

				selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_search_Field, "Network service reference");
				typeIn(BroadBand_SaaS_Locators.SAAS_search_Input, "ETHN00393270");
				this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_search_Img, true);
				jsClickOn(BroadBand_SaaS_Locators.SAAS_search_Img);
				snooze(1000);
				int size = getElementsCount(BroadBand_SaaS_Locators.SAAS_select_Network_Confirm);
				if (size >= 1) {
					// jsClickOn(BB_PortalOR.SAAS_select_Network_ref);
					this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_select_Network_Confirm, true);
					jsClickOn(BroadBand_SaaS_Locators.SAAS_select_Network_Confirm);
					waitForPageToLoad();
				}
			}

		} else {
			extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "Place New Order menu is not displayed");
			return;
		}
		// ==================================================================================================================================
		// check Network selection and proceed further
		// ==================================================================================================================================

		this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_selected_Network_Label, false);

		int networkLabel = getElementsCount(BroadBand_SaaS_Locators.SAAS_selected_Network_Label);
		int NetworkRef = getElementsCount(BroadBand_SaaS_Locators.SAAS_selected_Network_Label_ref);
		// int NextBtn = getElementsCount(BB_PortalOR.SAAS_Next_Label);

		if (networkLabel == 1 && NetworkRef == 1) {
			extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "selected Network details are displayed");
			this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_Next_Label, true);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Next_Label);
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "selected Network details are not displayed");
			return;
		}

		// ==================================================================================================================================
		// check bandwidth and post code selection and proceed further
		// ==================================================================================================================================
		this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_Bandwidth_input, false);
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Bandwidth_input)) {
			extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "selected Bandwidth details are displayed");
			typeIn(BroadBand_SaaS_Locators.SAAS_Bandwidth_input_search_input, "10Mbit/s");
			snooze(2000);
			typeIn(BroadBand_SaaS_Locators.SAAS_Post_code_search, "MA1 1AA");
			snooze(2000);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Post_code_search_btn);
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "selected Bandwidth details are not displayed");
			return;
		}

		this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_Post_code_availabiltiy, false);
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Post_code_availabiltiy)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Post_code_confirm);
			this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_Post_code_check_availabiltiy, false);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Post_code_check_availabiltiy);
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "Post code details are not displayed");
			return;
		}

		this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_check_availabiltiy_Message, false);
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_check_availabiltiy_Message)) {
			this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_Next_Label, false);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Next_Label);
			snooze(1000);
			waitForPageToLoad();
			extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "Next Button clicked after checking network availability");
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "check availabiltiy is failed after providng the post code and bandwidth details");
			return;
		}

	}

	public void fn_SAAS_Update_Location_and_Primary_contact_details() {

		waitForPageToLoad();
		WebElement locator = locateElement(BroadBand_SaaS_Locators.SAAS_Primary_contact);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOf(locator));
		extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "Primary Contact element is visibel now");
		this.elementclickable_Visible(BroadBand_SaaS_Locators.SAAS_Primary_contact, true);
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Primary_contact);
		snooze(5000);
		jsClickOn(BroadBand_SaaS_Locators.SAAS_existing_contact);
		snooze(2000);
		typeIn(BroadBand_SaaS_Locators.SAAS_Search_By_lastName_value, "JS");
		snooze(1000);
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Search_By_lastName_img);
		snooze(3000);

		locator = locateElement(BroadBand_SaaS_Locators.SAAS_Confirmlabel);
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(locator));
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Confirmlabel);
		snooze(3000);
		waitForPageToLoad();

		locator = locateElement(BroadBand_SaaS_Locators.SAAS_Friendly_Name_Ref);
		wait.until(ExpectedConditions.visibilityOf(locator));
		extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "Friendly Name field is avaialble");
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Friendly_Name_Ref)) {
			// To Enter friendly name - random generator
			// =======================================================================================
			typeIn(BroadBand_SaaS_Locators.SAAS_Friendly_Name_Ref, "SanityTest1");
			// =======================================================================================
			// To Enter Room,Floor and other details.
			typeIn(BroadBand_SaaS_Locators.SAAS_Floor, "Floor1");
			typeIn(BroadBand_SaaS_Locators.SAAS_Room, "Room1");
			typeIn(BroadBand_SaaS_Locators.SAAS_Location, "Location1");
			String strHotsite = "Hot Site";
			System.out.println("Value is::" + getAttributeValueOf(BroadBand_SaaS_Locators.SAAS_Sitetype, "value"));
			if (getAttributeValueOf(BroadBand_SaaS_Locators.SAAS_Sitetype, "value").equalsIgnoreCase(strHotsite)) {
				System.out.println("site is hot site");
			}
			typeIn(BroadBand_SaaS_Locators.SAAS_companyname, "TechM - Sanity");

			// =======================================================================================
		} else {
			// Report.updateTestStepLog("Data - Provide Jorney", "Friendly Name
			// field is not displayed", "Fail");
			extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "Friendly Name field is not displayed");
			return;
		}

		jsClickOn(BroadBand_SaaS_Locators.SAAS_Next_btn);
		snooze(5000);
		waitForPageToLoad();

	}// End of fn_SAAS_Update_Location_and_Primary_contact_details

	public void fn_SAAS_Customization() {
		// selectDropDownValue(BB_PortalOR.SAAS_segmentation_type, "VLAN
		// Segmentation");
		WebElement locator = locateElement(BroadBand_SaaS_Locators.SAAS_segmentation_type);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOf(locator));

		driver.findElement(By.xpath("//tr[td[div[div[div[contains(text(),'Segmentation type')]]]]]//select")).sendKeys("VLAN Segmentation");
		snooze(4000);
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Landlord_13ampsocket);
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Currently_occupy_premises);
		jsClickOn(BroadBand_SaaS_Locators.SAAS_Termination_Ready);
		typeIn(BroadBand_SaaS_Locators.SAAS_Pre_authorized_charges, "2");
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_customization_next)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_customization_next);
			waitForPageToLoad();
			// return true;
		} else {
			extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "Customization - Next button is not visible");
			return;
		}

	}

	public boolean Service_check_in_basket_And_Validate_Network() {
		waitForPageToLoad();
		int check_Service_Header_for_Basket = getElementsCount(BroadBand_SaaS_Locators.SAAS_Services_in_Basket);
		int check_Etherway_Basket = getElementsCount(BroadBand_SaaS_Locators.SAAS_Services_in_Basket);

		if (check_Service_Header_for_Basket >= 1 && check_Etherway_Basket >= 1) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Basket_Next);
			waitForPageToLoad();
		} else {
			return false;
		}

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Validate_network_Service_Header) && isDisplayed(BroadBand_SaaS_Locators.SAAS_Validate_network_Service_btn)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Validate_network_Service_btn);
			waitForPageToLoad();
			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Validate_network_Service_success_msg)) {
				String msg = getTextOf(BroadBand_SaaS_Locators.SAAS_Validate_network_Service_success_msg);
				System.out.println("n/w msg is::" + msg);
				jsClickOn(BroadBand_SaaS_Locators.SAAS_Basket_Next);
				waitForPageToLoad();
			}
		} else {
			return false;
		}

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Step2_Contacts_Header) && isDisplayed(BroadBand_SaaS_Locators.SAAS_KCI_Update)) {
			if (getAttributeValueOf(BroadBand_SaaS_Locators.SAAS_KCI_Update, "value").equalsIgnoreCase("Email")) {
				System.out.println("Email is already choosen as an option");
				jsClickOn(BroadBand_SaaS_Locators.SAAS_Basket_Next);
				waitForPageToLoad();
			} else {
				System.err.println("KCI option is differnt then email");

			}
		} else {
			return false;
		}

		return true;
	}

	public boolean Check_Billing_Accounts_and_Place_Order() {

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Step3_BillingAc_Header)) {
			String Billingac = getTextOf(BroadBand_SaaS_Locators.SAAS_Step3_BillingAc_value);
			System.out.println("billing account value::" + Billingac);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Basket_Next);
			waitForPageToLoad();
		} else {
			System.err.println("no billing acccount found");
			return false;
		}

		// ============================================================================================
		// Order Confirmation.
		// ============================================================================================

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Step4_Order_confirmation) && isDisplayed(BroadBand_SaaS_Locators.SAAS_Step4_Product_details_check)) {
			System.out.println("one of charge::" + getTextOf(BroadBand_SaaS_Locators.SAAS_one_off_charge));
			System.out.println("Month charge::" + getTextOf(BroadBand_SaaS_Locators.SAAS_Monthly_charge));
			extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "One of Charge is::" + getTextOf(BroadBand_SaaS_Locators.SAAS_one_off_charge));
			extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "Month charge is::" + getTextOf(BroadBand_SaaS_Locators.SAAS_Monthly_charge));
			typeIn(BroadBand_SaaS_Locators.SAAS_Order_ref, "TestOrderRef1");
			typeIn(BroadBand_SaaS_Locators.SAAS_Order_desc, "Automation Sanity Test");
			selectCheckBox(BroadBand_SaaS_Locators.SAAS_Order_Accept_Terms_condition_chekbox);
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Place_Order_Button);
			waitForPageToLoad();
			// ===================================================================================

			if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Order_confirmation_Header)) {
				String SAAS_OrderNum = getTextOf(BroadBand_SaaS_Locators.SAAS_Order_id);
				System.out.println("order number is::" + SAAS_OrderNum);
				extentTest.log(LogStatus.PASS, "Data - Provide Jorney", "Provide Order Number is::" + SAAS_OrderNum);
				// DataBook.putData("testData", "OrderID", SAAS_OrderNum);
				// dataBook.put("OrderID", SAAS_OrderNum);
				updateOutputValue("OrderID", SAAS_OrderNum);
				return true;
			} else {
				System.err.println("Order is not presetnt");
				extentTest.log(LogStatus.FAIL, "Data - Provide Jorney", "Provide Order Number is not available");
				return false;
			}

			// ===================================================================================
		}
		return true;
	}

	public void elementclickable_Visible(By locator, boolean clickable) {
		WebElement element = locateElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, 600);
		wait.until(ExpectedConditions.visibilityOf(element));
		if (clickable) {
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		snooze(3000);
	}

	// ======================================================================================
	public String CustomerRequired_Date() {
		// ======================================================================================
		// Purpose:- Retun the date format by adding the standard lead time.
		// Ex:- if today is 25/01/2017 it returns 31/01/2017 if standard lead
		// time is 5 days
		// Modc doesnt contains CRD as default value which is a defect. PR
		// number will be updated
		// Input:- Ensure that StandardLeadTime column is present in test data
		// sheet and value need to be updated correctly.
		// output:- string format of required date as below
		// dd/MM/YYYY hh:mm:ss"
		//
		// ======================================================================================
		// String DefaultLeadTime =
		// DataBook.getData("testData","StandardLeadTime");
		String DefaultLeadTime = dataBook.get("StandardLeadTime");

		Calendar cal = new GregorianCalendar();

		// cal now contains current date
		System.out.println(cal.getTime());

		// add the working days

		int workingDaysToAdd = Integer.valueOf(DefaultLeadTime);

		for (int i = 0; i < workingDaysToAdd; i++)

			do {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} while (!isWorkingDay(cal));

		System.out.println(cal.getTime());
		java.util.Date crd = cal.getTime();
		// 31/01/2017 14:48:00
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
		String dateString = sdf.format(crd);
		return String.valueOf(dateString);
	}

	public boolean isWorkingDay(Calendar cal) {

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)

			return false;
		// tests for other holidays here
		// ...
		return true;

	}

	public void switchToFrame_EcoPlusPage() {

		WebElement frameset = driver.findElement(By.xpath("//frame[@name='_sweclient']"));
		driver.switchTo().frame(frameset);
		System.out.println("Switched to _sweclient frame");
		frameset = driver.findElement(By.xpath("//frame[@name='_sweview']"));
		driver.switchTo().frame(frameset);
		System.out.println("Switched to _sweview");
		snooze(1000);

	}

	// Methods for T2R journey
	public static String ttref = "";

	public boolean fn_SAAS_BB_RaiseFault() {
		switchToFrame_EcoPlusPage();
		// Select Raise a fault option
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Raise_a_Fault)) {
			// Select Raise a fault
			selectDropDownValue(BroadBand_SaaS_Locators.SAAS_Raise_a_Fault, "Raise a fault");
			snooze(2000);
			// Report.updateTestStepLog("Select Raise a Fault",
			// "Selected Raise a fault", "Pass");
			extentTest.log(LogStatus.PASS, "Select Raise a Fault", "Selected Raise a fault");
		} else {
			// Report.updateTestStepLog("Select Raise a Fault",
			// "Selected Raise a fault", "Fail");
			extentTest.log(LogStatus.FAIL, "Select Raise a Fault", "Failed to select Raise a fault");
			return false;
		}

		// Variable declaration
		String serviceID = dataBook.get("ServiceID");

		// Enter Service ID to raise fault on that
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Enter_ServiceID)) {
			typeIn(BroadBand_SaaS_Locators.SAAS_Enter_ServiceID, serviceID);
			// Report.updateTestStepLog("Enter Service ID",
			// "Service ID entered successfully", "Pass");
			extentTest.log(LogStatus.PASS, "Enter Service ID", "Service ID entered successfully");
		} else {
			// Report.updateTestStepLog("Enter Service ID",
			// "Service ID failed to enter", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter Service ID", "Service ID failed to enter");
			return false;
		}

		// Click on GO
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Click_Go)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Click_Go);
			waitForPageToLoad();
			// Report.updateTestStepLog("Click on Go",
			// "Clicked on Go successfully", "Pass");
			extentTest.log(LogStatus.PASS, "Click on Go", "Clicked on Go successfully");

		} else {
			// Report.updateTestStepLog("Click on Go", "Failed to click on Go",
			// "Fail");
			extentTest.log(LogStatus.FAIL, "Click on Go", "Failed to click on Go");
			return false;
		}

		// Takes time to load the Page
		waitForPageToLoad();
		// try {
		// driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		// if
		// (driver.findElement(BroadBand_SaaS_Locators.oneFaultMsg).isDisplayed())
		// {
		// extentTest.log(LogStatus.FAIL, "Check if there is no fault raised",
		// "There exists open fault" + captureScreenShot());
		// return false;
		// }
		// } catch (Exception ex) {
		// ;
		// }
		//
		// driver.manage().timeouts().pageLoadTimeout(IMPLICIT_WAIT_TIMEOUT,
		// TimeUnit.SECONDS);
		snooze(15000);

		return true;
	}

	public void KBD_SQ_Validation() {
		System.out.println("snooze is completed");
		driver.switchTo().defaultContent();

		switchToFrame_EcoPlusPage();
		int count = driver.findElements(By.xpath("//*[contains(@id,'symbUrlIFrame')]")).size();
		if (count != 0) {
			WebElement frameset = driver.findElement(By.xpath("//*[contains(@id,'symbUrlIFrame')]"));
			driver.switchTo().frame(frameset);
			System.out.println("Switched to symbUrlIFrame1 frame");
		}

		// Check whether first SQ is available or not.
		// Please select the type of fault you wish to report.*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq1)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq1);
			System.out.println("SQ Nameis::" + SQname);
			// selectDropDownValue(BB_PortalOR.SAAS_T2R_KBD_Ans1,"KBD Fault");
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans1, "KBD Fault");
			waitForPageToLoad();
			// Report.updateTestStepLog("Select KBD Fault",
			// "Selected KBD fault", "Pass");
			extentTest.log(LogStatus.PASS, "Select KBD Fault", "Selected KBD fault");
		} else {
			// Report.updateTestStepLog("Select KBD Fault",
			// "KBD fault selection failed", "Fail");
			extentTest.log(LogStatus.FAIL, "Select KBD Fault", "KBD fault selection failed");
			return;
		}

		// Check whether Second SQ is available or not.
		// Please confirm that the selected circuit has been diagnosed with KBD
		// in last 2 hours.*

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq2)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq2);
			System.out.println("SQ Nameis::" + SQname);
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans2, "Not able to execute");
			waitForPageToLoad();
			// Report.updateTestStepLog("Select 'Not able to execute'",
			// "Selected Not able to execute", "Pass");
			extentTest.log(LogStatus.PASS, "Select 'Not able to execute'", "Selected Not able to execute");
		} else {
			// Report.updateTestStepLog("Select Not able to execute",
			// "'Not able to execute' selection failed", "Fail");
			extentTest.log(LogStatus.FAIL, "Select Not able to execute", "'Not able to execute' selection failed");
			return;
		}

		// Check whether Third SQ is available or not.
		// Is this an individual circuit failure or complete KBD system
		// failure?*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq3)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq3);
			System.out.println("SQ Nameis::" + SQname);
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans3, "Individual Circuit Failure");
			waitForPageToLoad();
			// Report.updateTestStepLog("Select 'Individual Circuit Failure'",
			// "Selected Individual Circuit Failure", "Pass");
			extentTest.log(LogStatus.PASS, "Select 'Individual Circuit Failure'", "Selected Individual Circuit Failure");
		} else {
			// Report.updateTestStepLog("Select Individual Circuit Failure",
			// "'Individual Circuit Failure' selection failed", "Fail");
			extentTest.log(LogStatus.FAIL, "Select Individual Circuit Failure", "'Individual Circuit Failure' selection failed");
			return;
		}

		// Variable declaration
		String faultDescription = dataBook.get("FaultDes");
		// Check whether Forth SQ is available or not.
		// Please provide brief description of issue not exceeding max character
		// length.(Example error message from KBD)*
		// int sqcount = driver.findElements(By.id("lbl1901")).size();
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq4)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq4);
			System.out.println("SQ Nameis::" + SQname);
			typeIn(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans4, faultDescription);
			waitForPageToLoad();
			// Report.updateTestStepLog("Enter Fault Desciption",
			// "Fault Desciption Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Enter Fault Desciption", "Fault Desciption Entered");
		} else {
			// Report.updateTestStepLog("Enter Fault Desciption",
			// "Fault Desciption not Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter Fault Desciption", "Fault Desciption not Entered");
			return;
		}

		// Check whether fifth SQ is available or not.
		// Has the End User currently got power switched on to their PC, Modem
		// and Router and has the End User re-booted and power cycled*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq5)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq5);
			System.out.println("SQ Nameis::" + SQname);
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans5, "Yes");
			waitForPageToLoad();
			// Report.updateTestStepLog("Rebooted value",
			// "Reboot option value has to be selected and Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Rebooted value", "Reboot option value has to be selected and Entered");
		} else {
			// Report.updateTestStepLog("Rebooted value",
			// "Reboot option value failed to Entered","Fail");
			extentTest.log(LogStatus.FAIL, "Rebooted value", "Reboot option value failed to Entered");
			return;
		}

		// Check whether Sixth SQ is available or not.
		// What type of fault is the End User experiencing?*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq6)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq6);
			System.out.println("SQ Nameis::" + SQname);
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans6, "Cannot connect");
			waitForPageToLoad();
			// Report.updateTestStepLog("Types of Fault",
			// "Type of fault is Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Types of Fault", "Type of fault is Entered");
		} else {
			// Report.updateTestStepLog("Types of Fault",
			// "Types of Fault not Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Types of Fault", "Types of Fault not Entered");
			return;
		}

		// Please enter a contact number for the Technical Assistance Service
		// Desk of the Communication Provider.*

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq7)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq7);
			System.out.println("SQ Nameis::" + SQname);
			typeIn(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans7, "02125425652");
			waitForPageToLoad();
			// Report.updateTestStepLog("Enter contact number",
			// "Contact number Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Enter contact number", "Contact number Entered");
		} else {
			// Report.updateTestStepLog("Enter contact number",
			// "Contact number not Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter contact number", "Contact number not Entered");
			return;
		}

		// Please enter contact availability times for the technical service
		// desk of the Communication Provider.*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq8)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq8);
			System.out.println("SQ Nameis::" + SQname);
			typeIn(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans8, "AM");
			waitForPageToLoad();
			// Report.updateTestStepLog("Enter Contact availability time",
			// "Contact availability time Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Enter Contact availability time", "Contact availability time Entered");
		} else {
			// Report.updateTestStepLog("Enter Contact availability time",
			// "Contact availability time Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter Contact availability time", "Contact availability time Entered");
			return;
		}

		// Please enter the End User Primary Contact's Name.*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq9)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq9);
			System.out.println("SQ Nameis::" + SQname);
			typeIn(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans9, "Test");
			waitForPageToLoad();
			// Report.updateTestStepLog("Enter Primary Contact Name",
			// "Primary Contact Name Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Enter Primary Contact Name", "Primary Contact Name Entered");
		} else {
			// Report.updateTestStepLog("Enter Primary Contact Name",
			// "Primary Contact Name not Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter Primary Contact Name", "Primary Contact Name not Entered");
			return;
		}

		// Please enter the End User's Primary contact Telephone Number*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq10)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq10);
			System.out.println("SQ Nameis::" + SQname);
			typeIn(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans10, "02125425652");
			waitForPageToLoad();
			// Report.updateTestStepLog("Enter Primary contact number",
			// "Primary contact number Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Enter Primary Contact Name", "Primary Contact number Entered");
		} else {
			// Report.updateTestStepLog("Enter Primary contact number",
			// "Primary contact number not Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter Primary Contact Name", "Primary Contact number not Entered");
			return;
		}

		// Please enter the best contact times for the primary contact.*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq11)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq11);
			System.out.println("SQ Nameis::" + SQname);
			typeIn(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans11, "AM");
			waitForPageToLoad();
			// Report.updateTestStepLog("Enter contact time",
			// "Contact time Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Enter contact time", "Contact time Entered");
		} else {
			// Report.updateTestStepLog("Enter contact time",
			// "Contact time Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter contact time", "Contact time Entered");
			return;
		}

		// The service is eligible for BT's Broadband Maintenance Category 4. Do
		// you wish to expedite to a Maintenance Category 14 -7 hour fix?
		// Note: This is a chargeable option.*
		
		 /* if (isDisplayed(BB_PortalOR.SAAS_T2R_KBD_Sq12)) { String SQname =
		 * getTextOf(BB_PortalOR.SAAS_T2R_KBD_Sq12);
		 * System.out.println("SQ Nameis::"+SQname);
		 * 
		 * selectDropDownValue(BB_PortalOR.SAAS_T2R_KBD_Ans12,"No");
		 * 
		 * Report.updateTestStepLog("Enter Fault Desciption",
		 * "Fault Desciption Entered", "Pass"); } else {
		 * Report.updateTestStepLog("Enter Fault Desciption",
		 * "Fault Desciption not Entered", "Fail"); return false; }
		 * 
		 * //Is there unrestricted 24 hour access to the customers premises?* if
		 * (isDisplayed(BB_PortalOR.SAAS_T2R_KBD_Sq13)) { String SQname =
		 * getTextOf(BB_PortalOR.SAAS_T2R_KBD_Sq13);
		 * System.out.println("SQ Nameis::"+SQname);
		 * 
		 * selectDropDownValue(BB_PortalOR.SAAS_T2R_KBD_Ans13,"No");
		 * 
		 * Report.updateTestStepLog("Enter Fault Desciption",
		 * "Fault Desciption Entered", "Pass"); } else {
		 * Report.updateTestStepLog("Enter Fault Desciption",
		 * "Fault Desciption not Entered", "Fail"); return false; }*/
		 

		// Are secondary contact details available?*
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq14)) {
			String SQname = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Sq14);
			System.out.println("SQ Nameis::" + SQname);
			selectDropDownByVisibleText(BroadBand_SaaS_Locators.SAAS_T2R_KBD_Ans14, "No");
			waitForPageToLoad();
			// Report.updateTestStepLog("Enter Fault Desciption",
			// "Fault Desciption Entered", "Pass");
			extentTest.log(LogStatus.PASS, "Enter Fault Desciption", "Fault Desciption Entered");
		} else {
			// Report.updateTestStepLog("Enter Fault Desciption",
			// "Fault Desciption not Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter Fault Desciption", "Fault Desciption not Entered");
			return;
		}

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_Next)) {

			WebElement tmpElement = locateElement(BroadBand_SaaS_Locators.SAAS_T2R_Next);

			JavascriptExecutor executor = (JavascriptExecutor) driver;

			executor.executeScript("arguments[0].click();", tmpElement);

			// jsClickOn(BB_PortalOR.SAAS_T2R_Next);
			// Report.updateTestStepLog("Click on Next",
			// "Clicked on Next successfully", "Pass");
			extentTest.log(LogStatus.PASS, "Click on Next", "Clicked on Next successfully");
			waitForPageToLoad();
			snooze(20000);

		} else {
			// Report.updateTestStepLog("Click on Next Button",
			// "Failed to click on Next", "Fail");
			extentTest.log(LogStatus.FAIL, "Click on Next Button", "Failed to click on Next");
			return;
		}

		driver.switchTo().defaultContent();

	}

	public boolean fn_submitFault() {

		switchToFrame_EcoPlusPage();

		// Enter TT Reference
		boolean blnflag = false;
		for (int i = 1; i <= 5; i++) {
			try {
				if (getDriver().findElement(BroadBand_SaaS_Locators.SAAS_T2R_Reference).isDisplayed()) {
					break;
				} else {
					snooze(20000);
				}
			} catch (Exception ex) {
				;
			}
		}

		typeIn(BroadBand_SaaS_Locators.SAAS_T2R_Reference, "TTSanityReference");
		blnflag = true;
		snooze(5000);
		// Report.updateTestStepLog("Enter TT Reference",
		// "TT Reference Entered", "Pass");
		extentTest.log(LogStatus.PASS, "Enter TT Reference", "TT Reference Entered");

		if (!blnflag) {
			// Report.updateTestStepLog("Enter TT Reference",
			// "TT Reference not Entered", "Fail");
			extentTest.log(LogStatus.FAIL, "Enter TT Reference", "TT Reference not Entered");
			return false;
		}

		// Select Checkbox to accept terms and condition

		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_Agree)) {

			System.out.println("Inside Checkbox");
			selectCheckBox(BroadBand_SaaS_Locators.SAAS_T2R_Agree);
			snooze(2000);

			// boolean str ;
			if (!isSelected(BroadBand_SaaS_Locators.SAAS_T2R_Agree)) {
				// Report.updateTestStepLog("Accept Terms Checkbox",
				// "Checkbox not Selected", "Fail");
				extentTest.log(LogStatus.FAIL, "Accept Terms Checkbox", "Checkbox not Selected");
				return false;

			} else {

				// Report.updateTestStepLog("Accept Terms Checkbox",
				// "Checkbox Selected", "Pass");
				extentTest.log(LogStatus.PASS, "Accept Terms Checkbox", "Checkbox Selected");

			}
		} else {
			// Report.updateTestStepLog("Accept Terms Checkbox",
			// "Checkbox not displaed", "Fail");
			extentTest.log(LogStatus.FAIL, "Accept Terms Checkbox", "Checkbox not displayed");
			return false;
		}

		// Click on Submit Button
		System.out.println("Click on submit button");
		// jsClickOn(BB_PortalOR.SAAS_T2R_Submit);

		WebElement tmpElement = locateElement(BroadBand_SaaS_Locators.SAAS_T2R_Submit);

		JavascriptExecutor executor = (JavascriptExecutor) driver;

		executor.executeScript("arguments[0].click();", tmpElement);

		// Report.updateTestStepLog("Click on Submit",
		// "Clicked on Submit successfully", "Pass");
		extentTest.log(LogStatus.PASS, "Click on Submit", "Clicked on Submit successfully");
		waitForPageToLoad();
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		// TTSanityReference
		// Fetching the TT Details from the page
		driver.switchTo().defaultContent();
		switchToFrame_EcoPlusPage();
		snooze(2000);

		boolean blnTTRef = false;
		for (int i = 1; i <= 20; i++) {
			if (!isDisplayed(BroadBand_SaaS_Locators.SAAS_T2R_Fault)) {

				waitForPageToLoad();

			} else {

				ttref = getTextOf(BroadBand_SaaS_Locators.SAAS_T2R_Fault);
				// dataBook.put("Ttid", ttref);
				updateOutputValue("Ttid", ttref);
				// Report.updateTestStepLog("TT Confirmation Page","TT Number is::"+ttref,
				// "Pass");
				extentTest.log(LogStatus.PASS, "TT Confirmation Page", "TT Number is:: " + ttref + captureScreenShot());
				blnTTRef = true;
				break;
			}

		}
		if (!blnTTRef) {
			// Report.updateTestStepLog("TT Confirmation Page","TT Number is not displayed::",
			// "Fail");
			extentTest.log(LogStatus.FAIL, "TT Confirmation Page", "TT Number is not displayed::");
			return false;
		}

		return true;

	}

	public void fn_SAAS_BB_AcceptFault() {

		switchToFrame_EcoPlusPage();
		// Select Raise a fault option
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Track_a_Fault)) {
			// Select Track a fault
			selectDropDownValue(BroadBand_SaaS_Locators.SAAS_Track_a_Fault, "Track a fault");
			snooze(2000);
			// Report.updateTestStepLog("Select Track a Fault",
			// "Selected Track a fault", "Pass");
			extentTest.log(LogStatus.PASS, "Select Track a Fault", "Selected Track a fault");
		} else {
			// Report.updateTestStepLog("Select Track a Fault",
			// "Selected Track a fault", "Fail");
			extentTest.log(LogStatus.FAIL, "Select Track a Fault", "Selected Track a fault");
			return;
		}

		// Variable declaration
		String TTID = dataBook.get("Ttid");

		// Enter Service ID to raise fault on that
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Enter_ServiceID)) {
			typeIn(BroadBand_SaaS_Locators.SAAS_Enter_ServiceID, TTID);
			// Report.updateTestStepLog("Enter TT ID",
			// "TT ID entered successfully", "Pass");
			extentTest.log(LogStatus.PASS, "Enter TT ID", "TT ID entered successfully");
		} else {
			// Report.updateTestStepLog("Enter TT ID", "TT ID failed to enter",
			// "Fail");
			extentTest.log(LogStatus.FAIL, "Enter TT ID", "TT ID failed to enter");
			return;
		}

		// Click on GO
		if (isDisplayed(BroadBand_SaaS_Locators.SAAS_Click_Go)) {
			jsClickOn(BroadBand_SaaS_Locators.SAAS_Click_Go);
			waitForPageToLoad();
			// Report.updateTestStepLog("Click on Go",
			// "Clicked on Go successfully", "Pass");
			extentTest.log(LogStatus.PASS, "Click on Go", "Clicked on Go successfully");
		} else {
			// Report.updateTestStepLog("Click on Go", "Failed to click on Go",
			// "Fail");
			extentTest.log(LogStatus.FAIL, "Click on Go", "Failed to click on Go");
			return;
		}

		// Takes time to load the Page
		waitForPageToLoad();
		snooze(20000);

		// Click on the Accept button

		if (isDisplayed(BroadBand_SaaS_Locators.btnAcceptFault)) {
			jsClickOn(BroadBand_SaaS_Locators.btnAcceptFault);
			waitForPageToLoad();
			snooze(20000);
			// Report.updateTestStepLog("Click on Accept",
			// "Clicked on Accept successfully", "Pass");
			extentTest.log(LogStatus.PASS, "Click on Accept", "Clicked on Accept successfully");
		} else {
			// Report.updateTestStepLog("Click on Accept",
			// "Failed to click on Accept", "Fail");
			extentTest.log(LogStatus.FAIL, "Click on Accept", "Failed to click on Accept");
			return;
		}

	}

	public void fn_BESID_Check() throws ClassNotFoundException, SQLException {

		String strBESID = "";

		// step1 load the driver class
		Class.forName("oracle.jdbc.OracleDriver");
		// step2 create the connection object

		String strModel = dataBook.get("Model");
		System.out.println("Model is" + strModel);

		// Connection
		// con=DriverManager.getConnection("jdbc:oracle:thin:@10.80.149.172:61901/osmoda8","btread","btr3ad");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.80.149.172:61901/'" + strModel + "'", "btread", "btr3ad");

		System.out.println("connection started");
		// step3 create the statement object
		Statement stmt = con.createStatement();

		String TTRef = dataBook.get("Ttid");

		// step4 execute query
		ResultSet rs = stmt.executeQuery("select X_BES_ID from siebel.s_srv_req where sr_num = '" + TTRef + "'");

		while (rs.next()) {
			strBESID = rs.getString("X_BES_ID");
			System.out.println("BES Id::" + strBESID);
		}

		boolean blnBesIDFlag = false;
		for (int i = 1; i <= 10; i++) {
			if (strBESID.length() > 0) {
				// Report.updateTestStepLog("Bes Id",
				// "Bes ID Created :"+strBESID, "Pass");
				extentTest.log(LogStatus.PASS, "Bes Id", "Bes ID Created :" + strBESID);
				blnBesIDFlag = true;
				break;
			} else {

				snooze(60000);
				System.out.println("BES ID is not created.");
				rs = stmt.executeQuery("select X_BES_ID from siebel.s_srv_req where sr_num = '" + TTRef + "'");
				while (rs.next())
					strBESID = rs.getString("X_BES_ID");
			}
		}

		if (!blnBesIDFlag) {
			// Report.updateTestStepLog("Bes Id", "Bes ID not Created", "fail");
			extentTest.log(LogStatus.FAIL, "Bes Id", "Bes ID not Created :" + strBESID);
		}

		// step5 close the connection object
		con.close();

	}

}// EOC