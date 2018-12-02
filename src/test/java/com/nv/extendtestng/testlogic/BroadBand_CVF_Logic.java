/*package com.nv.extendtestng.testlogic;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import com.nv.extendtestng.frameworkutils.WebDriverFactory;
import com.nv.extendtestng.uimaps.BroadBand_CVF_Locators;

public class BroadBand_CVF_Logic extends WebDriverFactory {

	public BroadBand_CVF_Logic(WebDriver driver, Map<String, String> dataBook) {
		super(driver, dataBook);
	}

	public static String AddressKey = "", status = "", resStatus = "",
			DNnumber = "", Sellers_ID = "";

	public boolean cvfLogin() {

		boolean businessComponentresult = false;

		// DynamicDataManager.initializeAllDynamicData();

		String userName = dataBook.get("CVFUserName"), password = dataBook
				.get("CVFPassword"), url = dataBook.get("CVFURL");

		driver.navigate().to(url);

		waitForPageToLoad();
		snooze(8000);
		typeIn(BroadBand_CVF_Locators.loginusername, userName);
		typeIn(BroadBand_CVF_Locators.loginpassword, password);
		clickOn(BroadBand_CVF_Locators.submit);
		waitForPageToLoad();

		for (int i = 1; i <= 50; i++) {

			if (isDisplayed(BroadBand_CVF_Locators.submission))

			{
				extentTest.log(LogStatus.PASS, "Login:", "Login is Successful");

				businessComponentresult = true;
				break;
			} else {
				snooze(1000);

			}

		}

		return businessComponentresult;

	}

	public void checkDemo() {
		WebElement ele = driver
				.findElement(By
						.xpath("(//I[text()='A90000160957'])[1]/ancestor::table[not(@id)]"));
		System.out.println(ele.getText());

		int row = getRowWithCellText(
				By.xpath("(//I[text()='A90000160957'])[1]/ancestor::table[not(@id)]"),
				"Tracking ID");
		String TrackingID = getCellData(
				By.xpath("(//I[text()='A90000160957'])[1]/ancestor::table[not(@id)]"),
				row, 3);
		System.out.println(TrackingID);
		// WebElement ele1 =
		// driver.findElement(By.xpath("((//I[text()='A90000098375'])[1]/ancestor::table[1])/ancestor::tr/TD[4]"));
		// System.out.println(ele1.getText());

	}

	public boolean cvfCreateDN() {

		boolean businessComponentresult = false, blnresult = false;

		blnresult = cvfLogin();
		System.out.println(blnresult);
		if (blnresult) {

			// String eventType = dataBook.get("eventType"), event = DataBook
			// .getData("testData", "event"), AddressKey =
			// String.valueOf(DataBook
			// .getData("testData", "GoldKey")), ProductType = DataBook
			// .getData("testData", "ProductType"), CareLevel = DataBook
			// .getData("testData", "CareLevel"), LineType = DataBook
			// .getData("testData", "LineType"), CNF = DataBook.getData(
			// "testData", "CNF"), CRD = DataBook.getData("testData",
			// "CRD"), CPS = dataBook.get("CPS");

			String AddressKey = String.valueOf(dataBook.get("GoldKey"));
			String CRD = dataBook.get("CRD");

			System.out.println("Excel data");

			if (isDisplayed(BroadBand_CVF_Locators.submission))

			{

				extentTest.log(LogStatus.INFO, "DN Creation:",
						"DN Creation in Progress");

				// selectDropDownValue(Test_BB_CVF.eventType, eventType);
				selectDropDownValue(BroadBand_CVF_Locators.eventType,
						"Create an asset");
				waitForPageToLoad();

				// selectDropDownValue(Test_BB_CVF.event, event);
				selectDropDownValue(BroadBand_CVF_Locators.event,
						"PSTN - Create Asset for Other CP");
				waitForPageToLoad();

				typeIn(BroadBand_CVF_Locators.event0, AddressKey);
				waitForPageToLoad();
				// selectDropDownValue(Test_BB_CVF.event1, ProductType);
				selectDropDownValue(BroadBand_CVF_Locators.event1,
						"WLR3 PSTN Single Line");
				waitForPageToLoad();
				// selectDropDownValue(Test_BB_CVF.event2, CareLevel);
				selectDropDownValue(BroadBand_CVF_Locators.event2, "1");
				waitForPageToLoad();
				// selectDropDownValue(Test_BB_CVF.event3, LineType);
				selectDropDownValue(BroadBand_CVF_Locators.event3, "Basic");
				waitForPageToLoad();
				// selectDropDownValue(Test_BB_CVF.event4, CNF);
				selectDropDownValue(BroadBand_CVF_Locators.event4, "None");
				waitForPageToLoad();
				typeIn(BroadBand_CVF_Locators.event5, CRD);
				waitForPageToLoad();
				// selectDropDownValue(Test_BB_CVF.event6, CPS);
				selectDropDownValue(BroadBand_CVF_Locators.event6, "No");
				waitForPageToLoad();
				clickOn(BroadBand_CVF_Locators.Submit);
				waitForPageToLoad();
				snooze(20000);

				// Report.updateTestStepLog("DN Creation:","DN Creation Request Submitted",
				// "Pass");
				extentTest.log(LogStatus.PASS, "DN Creation Request check",
						"DN Creation Request Submitted");

				clickOn(BroadBand_CVF_Locators.submission);
				waitForPageToLoad();
				snooze(10000);

				for (int i = 1; i <= 10; i++) {

					if (isDisplayed(BroadBand_CVF_Locators.tablelastrequests)) {
						int rowcounter = rowCount(BroadBand_CVF_Locators.tablelastrequests);

						if (rowcounter > 1) {

							int row = getRowWithCellText(
									By.xpath("(//I[text()='"
											+ AddressKey
											+ "'])[1]/ancestor::table[not(@id)]"),
									"Tracking ID");

							if (row > 0) {

								String TrackingID = getCellData(
										By.xpath("(//I[text()='"
												+ AddressKey
												+ "'])[1]/ancestor::table[not(@id)]"),
										row, 3);
								System.out.println(TrackingID);
								// dataBook.put("SearchbyID",
								// String.valueOf(TrackingID));
								updateOutputValue("SearchbyID",
										"String.valueOf(TrackingID)");
								break;
							}

						}
					}

					else {
						clickOn(BroadBand_CVF_Locators.submission);
						waitForPageToLoad();
						snooze(5000);
					}

				}

				businessComponentresult = true;

			} else {

				extentTest.log(LogStatus.FAIL, "DN Creation:",
						"Unable to create DN");

			}
		} else {
			extentTest.log(LogStatus.FAIL, "Login:", "Login is unSuccessful");

		}

		return businessComponentresult;
	}

	public boolean cvfValidateDN(String ThrNumber) {

		boolean dnStatus = false;
		boolean blnresult = false;

		blnresult = cvfLogin();
		System.out.println(blnresult);

		// Click on the Submission tab
		clickOn(BroadBand_CVF_Locators.submission);
		waitForPageToLoad();
		snooze(5000);

		// Get the tracking ID from Datasheet
		String trackingID = dataBook.get("SearchbyID");

		// Enter Tracking ID
		if (isDisplayed(BroadBand_CVF_Locators.trackingID)) {
			// Enter the tracking ID
			typeIn(BroadBand_CVF_Locators.trackingID, trackingID);
			waitForPageToLoad();

		} else {
			extentTest.log(LogStatus.FAIL, "Tracking ID",
					"Tracking ID field not visible");
		}

		// Click on the Search
		clickOn(BroadBand_CVF_Locators.Search);
		waitForPageToLoad();
		snooze(10000);

		System.out.println("Thrfareno value" + ThrNumber);
		int ThrrNumber = Integer.valueOf(ThrNumber) + 1;
		// DataBook.putData("testData", "Thrfareno_Min_Range",
		// String.valueOf(ThrrNumber));
		updateOutputValue("Thrfareno_Min_Range", String.valueOf(ThrrNumber));
		System.out.println("Thrfareno incremented to" + ThrrNumber);

		status = "Status:Pending on Fulfilment";
		// Check for the status
		for (int i = 1; i <= 70; i++) {

			if (isDisplayed(BroadBand_CVF_Locators.tablelastrequests)) {
				int columno = -1;
				columno = getColumnWithCellText(
						By.xpath("//table[@id='lastrequests']"), "Response");
				if (columno != -1) {
					String xlocalpath = "//*[@id='lastrequests:tbody_element']/tr/td["
							+ columno + "]/label";
					resStatus = driver.findElement(By.xpath(xlocalpath))
							.getText();
					// System.out.println( "Response is "+resStatus);
				} else {
					extentTest.log(LogStatus.FAIL, "Check value of status",
							"Status field is not available");
				}

				switch (status) {

				case "Status:Pending on Fulfilment":
					if (resStatus.contains(status)) {
						status = "Status:Acknowledged";

					} else {
						clickOn(BroadBand_CVF_Locators.Search);
						snooze(60000);

					}
					break;

				case "Status:Acknowledged":
					if (resStatus.contains(status)) {
						status = "Status:Committed";

					} else {
						clickOn(BroadBand_CVF_Locators.Search);
						snooze(60000);
					}
					break;
				case "Status:Committed":
					if (resStatus.contains(status)) {
						status = "Status:Provision - New Provide";

					} else {
						clickOn(BroadBand_CVF_Locators.Search);
						snooze(60000);
					}
					break;

				case "Status:Provision - New Provide":
					if (resStatus.contains("Service Id:")) {
						String[] Resarr = resStatus.split("Service Id:");
						System.out.println(Resarr[1]);
						dnStatus = true;
						extentTest.log(LogStatus.PASS, "ServiceID",
								"ServiceID created <B>" + Resarr[1] + "</B>");
						DNnumber = Resarr[1];
						System.out.println("DNnumber" + DNnumber);
						// DataBook.putData("testData", "DN", DNnumber);
						updateOutputValue("DN", DNnumber);

					} else {
						clickOn(BroadBand_CVF_Locators.Search);
						snooze(60000);
					}
					break;
				default:
					extentTest.log(LogStatus.FAIL, "Status", "Status Invalid");

				}// EO switch

			}// EO IF
			if (dnStatus) {
				break;
			} else {
				// Report.updateTestStepLog("ServiceID",
				// "ServiceID not created ", "Info");
				snooze(5000);
			}

		}// EO For
		if (!dnStatus) {
			extentTest.log(LogStatus.FAIL, "ServiceID",
					"ServiceID not created ");
		}
		return dnStatus;

	}// EOF

	public boolean LOR_COR_Create() {

		boolean businessComponentresult = false, blnresult = false;

		blnresult = cvfLogin();
		System.out.println(blnresult);
		if (blnresult) {

			String AddressKey = String.valueOf(dataBook.get("GoldKey"));
			String CRD = dataBook.get("CRD");

			System.out.println("Excel data");

			if (isDisplayed(BroadBand_CVF_Locators.submission))

			{

				// Report.updateTestStepLog("DN Creation:","DN Creation in Progress",
				// "Pass");
				extentTest.log(LogStatus.PASS, "DN Creation in Progress",
						"Pass");

				// selectDropDownValue(Test_BB_CVF.eventType, eventType);
				selectDropDownValue(BroadBand_CVF_Locators.eventType,
						"Create an asset");
				waitForPageToLoad();

				// selectDropDownValue(Test_BB_CVF.event, event);
				selectDropDownValue(BroadBand_CVF_Locators.event,
						"PSTN - Place single line order with LOR");
				waitForPageToLoad();

				typeIn(BroadBand_CVF_Locators.event0, AddressKey);
				waitForPageToLoad();

				String Lorval = dataBook.get("LOR");
				int Randnum = (int) (Math.random() * (100000 - 1));
				String Lorvalue = Lorval + "Rel" + Randnum;
				// selectDropDownValue(Test_BB_CVF.event1, ProductType);
				selectDropDownValue(BroadBand_CVF_Locators.event1, Lorvalue);
				waitForPageToLoad();
				typeIn(BroadBand_CVF_Locators.event2, CRD);
				waitForPageToLoad();
				clickOn(BroadBand_CVF_Locators.Submit);
				waitForPageToLoad();
				snooze(20000);

				// Report.updateTestStepLog("LOR Creation:","LOR Creation Request Submitted",
				// "Pass");
				extentTest.log(LogStatus.PASS,
						"LOR Creation Request Submitted", "Pass");

				clickOn(BroadBand_CVF_Locators.submission);
				waitForPageToLoad();
				snooze(10000);

				for (int i = 1; i <= 10; i++) {

					if (isDisplayed(BroadBand_CVF_Locators.tablelastrequests)) {
						int rowcounter = rowCount(BroadBand_CVF_Locators.tablelastrequests);

						if (rowcounter > 1) {

							int row = getRowWithCellText(
									By.xpath("(//I[text()='"
											+ AddressKey
											+ "'])[1]/ancestor::table[not(@id)]"),
									"Tracking ID");

							if (row > 0) {

								String TrackingID = getCellData(
										By.xpath("(//I[text()='"
												+ AddressKey
												+ "'])[1]/ancestor::table[not(@id)]"),
										row, 3);
								System.out.println(TrackingID);
								// DataBook.putData("testData", "SearchbyID",
								// String.valueOf(TrackingID));
								updateOutputValue("SearchbyID",
										String.valueOf(TrackingID));
								break;
							}

						}
					}

					else {
						clickOn(BroadBand_CVF_Locators.submission);
						waitForPageToLoad();
						snooze(5000);
					}

				}

				businessComponentresult = true;

			} else {

				extentTest.log(LogStatus.FAIL, "COR Creation:",
						"Unable to create COR");

			}
		} else {
			extentTest.log(LogStatus.FAIL, "Login:", "Login is unSuccessful");

		}

		return businessComponentresult;
	}

	public boolean cvfLogout() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (driver.findElements(By.xpath("//input[@value='Logout']")).size() > 0) {
			clickOn(BroadBand_CVF_Locators.lnkLogout);
			waitForPageToLoad();
			// driver.close();
			extentTest.log(LogStatus.PASS, "Logout:",
					"Logout link is displayed");
			return true;
		}
		extentTest.log(LogStatus.FAIL, "Logout:",
				"Logout link is not displayed");
		// driver.close();
		return false;
	}

	public void Test_Pause_Scenario() throws InterruptedException {
		BroadBand_FlowPage_Logic Flow = new BroadBand_FlowPage_Logic(driver,
				dataBook);

		// Log in To Flow application
		Flow.flowLogin();

		Flow.navigateToFlowDesktop();

		// Search for the order
		Flow.searchFlowOrder();

		// Check for order Acknowlegement
		Flow.flowMilestoneCheck_ProgressWithCVF();
		// Capture the Sellers Id

	}

	// temp comments by phani krishna - 07-04-2017 - check with pause scenario

	public boolean CVF_Pause_Send_Details() {
		// Test_BB_CVFPage CVF = new Test_BB_CVFPage(driver, dataBook);
		// boolean login = CVF.cvfLogin();
		// if (login)
		// {
		//
		// //Create an Order event
		// if (isDisplayed(Test_BB_CVF.eventType)) {
		// extentTest.log(LogStatus.PASS,
		// "Eventype Check","EventType Field is available");
		// selectDropDownValue(Test_BB_CVF.eventType, "Create an Order event");
		// snooze(1000);
		// } else {
		// extentTest.log(LogStatus.FAIL,
		// "Eventype Check","EventType Field is not available");
		// }
		//
		// //Pause/Release an Order
		// if (isDisplayed(Test_BB_CVF.event)) {
		// extentTest.log(LogStatus.PASS,
		// "Eventype Check","Event Field is available");
		// selectDropDownValue(Test_BB_CVF.event, "Pause/Release an Order");
		// snooze(1000);
		// } else {
		// extentTest.log(LogStatus.FAIL,
		// "Eventype Check","Event Field is not available");
		// }
		//
		// //To Enter Orderid
		// String SellersId = dataBook.get("SellersId");
		// if (isDisplayed(Test_BB_CVF.Order_number)) {
		// extentTest.log(LogStatus.PASS,
		// "Order Number Check","Order Number Field is available"); //
		// extentTest.log(LogStatus.PASS,
		// "Order Number Check","Order Number Field updated as::"+SellersId);
		// typeIn(Test_BB_CVF.Order_number, SellersId);
		//
		// } else {
		// extentTest.log(LogStatus.FAIL,
		// "Order Number Check","Order Number Field is not available");
		// }
		//
		// //To Select Related Orders
		// if (isDisplayed(Test_BB_CVF.Related_Orders) ) {
		// extentTest.log(LogStatus.PASS,
		// "Related_Orders Check","Related_Orders Field is available");
		// selectDropDownValue(Test_BB_CVF.Related_Orders, "Yes");
		// snooze(1000);
		// } else {
		// extentTest.log(LogStatus.FAIL,
		// "Related_Orders Check","Related_Orders Field is not available");
		// }
		// //To Select KCI
		// String KCI = dataBook.get("KCIType");
		// if (isDisplayed(Test_BB_CVF.KCI)) {
		// extentTest.log(LogStatus.PASS,
		// "KCI Field Check","KCI Field is available");
		// selectDropDownValue(Test_BB_CVF.KCI, KCI);
		// extentTest.log(LogStatus.PASS,
		// "KCI Field Check","KCI Field is selectd as::"+KCI);
		// snooze(1000);
		// } else {
		// extentTest.log(LogStatus.FAIL,
		// "KCI Field Check","KCI Field is not selectd as::"+KCI);
		// }
		//
		// // To Click on submit button
		// if (isDisplayed(Test_BB_CVF.Submit_btn)) {
		// clickOn(Test_BB_CVF.Submit_btn);
		// extentTest.log(LogStatus.PASS,
		// "submit Check","submit button is clicked");
		// if (GetTracking_Id_And_Check_Pause_State())
		// {
		// return true;
		// }
		// else
		// {
		// extentTest.log(LogStatus.FAIL,
		// "Tracking Id check","Pause Scenario Failed while tracking with Id.");
		// }
		// } else {
		// extentTest.log(LogStatus.FAIL,
		// "submit Check","submit button is not available");
		// }
		// }
		//
		return false;
	}

	// temp comments by phani krishna - 07-04-2017

	// public boolean GetTracking_Id_And_Check_Pause_State()
	// {
	// //NAD_Creation CVF = new NAD_Creation();
	//
	// String SellersId = dataBook.get("SellersId");
	// String TrackingId = CVF.getTracking_Id(SellersId);
	// if (TrackingId!="")
	// {
	// String Response = CVF.Retrun_Address_Ref(TrackingId,
	// "Response:CPPauseOrder set to KSU2 for identified orders");
	// if (Response.contains("None")){
	// return false;
	// }
	// else
	// {
	// return true;
	// }
	// }
	//
	// return false;
	// }

}// EOC*/