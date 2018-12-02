/*package com.nv.extendtestng.testlogic;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nv.extendtestng.frameworkutils.WebDriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import com.nv.extendtestng.uimaps.BroadBand_Flow_Locators;

public class BroadBand_FlowPage_Logic extends WebDriverFactory {

	public BroadBand_FlowPage_Logic(WebDriver driver,
			Map<String, String> dataBook) {
		super(driver, dataBook);
	}

	// variable name, functionname : loweCamelCase
	// Class Name: UpperCamelCase
	// Constants : UPPERCASE

	protected boolean KCI1flag = false, KCI2flag = false, KCI3flag = false;
	public String empVersion = "", serviceID = "", exchangeDistrictID = "",
			flowOrderlineNo = "", flowOrderNo = "", SellersId = "";

	public void flowLogin() {
		String userName = dataBook.get("FlowUserName"), password = dataBook
				.get("FlowPassword"), url = dataBook.get("FlowURL");
		System.out.println("flowLogin" + dataBook.get("OrderID"));
		driver.get(url);
		waitForPageToLoad();

		typeIn(BroadBand_Flow_Locators.txtmyuserid, userName);
		typeIn(BroadBand_Flow_Locators.txtmypassword, password);
		clickOn(BroadBand_Flow_Locators.btnLogin);
		waitForPageToLoad();
		if (isDisplayed(BroadBand_Flow_Locators.txtmyauthorised)) {

			typeIn(BroadBand_Flow_Locators.txtmyauthorised, "Y");

			extentTest.log(LogStatus.PASS, "Login:",
					"Authorised page is  displayed");

			clickOn(BroadBand_Flow_Locators.btnConfirm);

		} else {
			extentTest.log(LogStatus.FAIL, "Login:",
					"Authorised page is not displayed");

		}

		waitForPageToLoad();

		if (isDisplayed(BroadBand_Flow_Locators.lnkNewFlowDesktop)) {
			extentTest.log(LogStatus.PASS, "Login:", "Login is Successful");
		} else {
			extentTest.log(LogStatus.FAIL, "Login:", "Login is unSuccessful");
		}

	}

	public void navigateToFlowDesktop() throws InterruptedException {

		clickOn(BroadBand_Flow_Locators.lnkNewFlowDesktop);

		waitForPageToLoad();
		Thread.currentThread().sleep(10000);

		if (isDisplayed(BroadBand_Flow_Locators.txtAttrId))

			extentTest.log(LogStatus.PASS, "FlowDesktop Login:",
					"FlowDesktop page is displayed");
		else

			extentTest.log(LogStatus.FAIL, "FlowDesktop Login:",
					"FlowDesktop page is not displayed");

	}

	public void searchFlowOrder() throws InterruptedException {

		boolean blnSearchSuccess = false;
		int searchCounter = 1;

		String FlowSearchID = dataBook.get("FlowSearchID"), OrderID = dataBook
				.get("OrderID");

		selectDropDownByVisibleText(BroadBand_Flow_Locators.cmbAttrId,
				FlowSearchID);
		typeIn(BroadBand_Flow_Locators.txtAttrId, OrderID);

		while (!blnSearchSuccess) {
			clickOn(BroadBand_Flow_Locators.btnSubmitID);
			waitForPageToLoad();
			snooze(5000);
			System.out.println(OrderID);

			for (int i = 1; i <= 100; i++) {
				if (locateElements(BroadBand_Flow_Locators.IdResultTable)
						.size() > 0) {
					break;
				} else {
					snooze(2000);
					typeIn(BroadBand_Flow_Locators.txtAttrId, OrderID);
					clickOn(BroadBand_Flow_Locators.btnSubmitID);
					waitForPageToLoad();
				}
			}

			if (isDisplayed(BroadBand_Flow_Locators.IdResultTable)) {
				extentTest.log(LogStatus.PASS, "FlowDesktop Search Results:",
						"FlowDesktop Search Results Table is displayed");
			} else {
				extentTest.log(LogStatus.FAIL, "FlowDesktop Search Results:",
						"FlowDesktop Search Results Table is not displayed");
			}

			int rowcounter = rowCount(BroadBand_Flow_Locators.IdResultTable);
			if (rowcounter >= 3) {

				int row = getRowWithCellText(
						BroadBand_Flow_Locators.IdResultTable, OrderID);
				int column = getColumnWithCellText(
						BroadBand_Flow_Locators.IdResultTable,
						"Flow Orderline No");

				String flowOrderLineNumber = getCellData(
						BroadBand_Flow_Locators.IdResultTable, row, column);
				System.out.println("Flow Order Line Number: "
						+ flowOrderLineNumber);
				if ((row > 0) && (column > 0)) {
					boolean repeat = true;

					while (repeat) {
						// driver.findElement(By.linkText(flowOrderLineNumber)).click();
						jsClickOn(By.linkText(flowOrderLineNumber));
						// clickOn(By.linkText(flowOrderLineNumber));
						// clickOnTableChildItem(Test_BB_Flow.IdResultTable),
						// row, column, "a", 0);
						snooze(5000);
						Set<String> windows = driver.getWindowHandles();
						if (windows.size() == 2)
							repeat = false;
						snooze(10000);
					}
					blnSearchSuccess = true;

					extentTest.log(LogStatus.PASS,
							"FlowDesktop Search Results:",
							"FlowDesktop Search Results is displayed");
				}
			}
			searchCounter++;
			if (searchCounter == 10 || blnSearchSuccess)
				break;
			else
				snooze(1000 * 60 * 1);
		}

		if (!blnSearchSuccess) {
			extentTest.log(LogStatus.FAIL, "FlowDesktop Search Results:",
					"FlowDesktop Search Results is not displayed");
		}

	}

	public void flowMilestoneAndExceptionProgress() {
		boolean switchWindowSuccess = false, isComplete = false, isOrderComplete = false;
		String current_Flow_URL = "";
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			driver.switchTo().window(window);
			String title = driver.getTitle();
			if (title.equals("Monitor Progress Service Details")) {
				switchWindowSuccess = true;
				current_Flow_URL = driver.getCurrentUrl();
				driver.close();
				break;
			}
		}

		driver.switchTo().window(parentWindow);
		driver.navigate().to(current_Flow_URL);

		if (!switchWindowSuccess) {
			extentTest
					.log(LogStatus.FAIL, "Navigation",
							"Could not be navigated to <B>Monitor Progress Service Details<B> page");
		} else {
			extentTest.log(LogStatus.PASS, "Navigation",
					"Navigated to <B>Monitor Progress Service Details<B> page");
		}

		snooze(5000);
		String showHide = locateElement(BroadBand_Flow_Locators.lblShowHide)
				.getText().trim();
		if (showHide.equals("Show")) {
			// locateElement(Test_BB_Flow.imgShowHide).click();
			// clickOn(Test_BB_Flow.imgShowHide);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("EventsPanel.expandEvents();");
		}

		for (int loopCounter = 1; loopCounter <= 20; loopCounter++) {
			snooze(5000);
			if (!locateElement(BroadBand_Flow_Locators.tblEvents).isDisplayed()) {
				// TODO Should repeat
			}

			WebElement tblElement = locateElement(BroadBand_Flow_Locators.tblEvents);
			List<WebElement> rows = tblElement.findElements(By.tagName("tr"));
			int rowCount = rows.size();
			if (rowCount == 0) {
				break;
			}

			for (int iRow = rowCount; iRow >= 3; iRow--) {
				String eventType = getCellData(
						BroadBand_Flow_Locators.tblEvents, iRow, 1);
				String description = getCellData(
						BroadBand_Flow_Locators.tblEvents, iRow, 2);
				String actualFinishTime = getCellData(
						BroadBand_Flow_Locators.tblEvents, iRow, 4);
				System.out.println(description);
				// String eventType =
				// rows.get(iRow).findElements(By.xpath(".//*[local-name(.)='th' or local-name(.)='td']")).get(0).getText().trim();

				switch (eventType) {
				case "EXCEPTION":

					break;

				case "MILESTONE":
					if (actualFinishTime.isEmpty()) {
						captureAttributeValues();
						refreshDataBook();
						flowHandleMilestone(description);
						isComplete = checkForMileStoneCompletion(description);
						if (!isComplete)
							extentTest.log(LogStatus.FAIL,
									"Milestone Completion", "Milestone - "
											+ description
											+ " is still not completed");

					} else if (description
							.equals("Order Completed on FlowStream")) {
						isOrderComplete = checkForMileStoneCompletion(description);
						if (!isOrderComplete)
							extentTest.log(LogStatus.FAIL,
									"Milestone Completion", "Milestone - "
											+ description
											+ " is still not completed");
						else
							extentTest.log(LogStatus.PASS,
									"Milestone Completion", "Milestone - "
											+ description);
					}

					break;
				}
				if (isOrderComplete) {
					System.out.println("Break after Switch");
					break;
				}
			}
			System.out.println("Before snooze");
			if (isOrderComplete)
				break;
			snooze(30 * 1000 * 1);
			System.out.println("After snooze");
			flowRefreshPageAndExpand();
		}

	}

	private void flowHandleMilestone(String description) {
		BroadBand_SEMP_Logic Semp = new BroadBand_SEMP_Logic(driver, dataBook);
		switch (description) {
		case "Wait for GEA Provide Acknowledged event":
			captureAttributeValues();
			String windowBeforeKCI1 = driver.getWindowHandle();
			boolean blnresult = Semp.sempTriggerKCI(description);
			driver.switchTo().window(windowBeforeKCI1);
			KCI1flag = blnresult;
			break;
		case "Wait for GEA Cease Acknowledged event":
			captureAttributeValues();
			windowBeforeKCI1 = driver.getWindowHandle();
			blnresult = Semp.sempTriggerKCI(description);
			driver.switchTo().window(windowBeforeKCI1);
			KCI1flag = blnresult;
			break;

		case "Wait for GEA Provide Committed event":
			captureAttributeValues();
			KCI1flag = true;
			KCI2flag = true;
			KCI3flag = false;

			String windowBeforeKCI2 = driver.getWindowHandle();
			blnresult = Semp.sempTriggerKCI(description);
			driver.switchTo().window(windowBeforeKCI2);
			KCI2flag = blnresult;
			break;

		case "Wait for GEA Cease Committed event":
			captureAttributeValues();
			KCI1flag = true;
			KCI2flag = true;
			KCI3flag = false;

			windowBeforeKCI2 = driver.getWindowHandle();
			blnresult = Semp.sempTriggerKCI(description);
			driver.switchTo().window(windowBeforeKCI2);
			KCI2flag = blnresult;
			break;

		case "Wait for GEA Provide Completed event":

			KCI1flag = true;
			KCI2flag = true;
			KCI3flag = true;
			String windowBeforeKCI3 = driver.getWindowHandle();
			blnresult = Semp.sempTriggerKCI(description);
			driver.switchTo().window(windowBeforeKCI3);
			KCI3flag = blnresult;

			break;

		case "Wait for GEA Cease Completed event":

			KCI1flag = true;
			KCI2flag = true;
			KCI3flag = true;
			windowBeforeKCI3 = driver.getWindowHandle();
			blnresult = Semp.sempTriggerKCI(description);
			driver.switchTo().window(windowBeforeKCI3);
			KCI3flag = blnresult;
			break;

		case "Wait till CPD-0 Days":
			String windowBeforeRipen = driver.getWindowHandle();
			BroadBand_FlowGate_Logic Flowgate = new BroadBand_FlowGate_Logic(
					driver, dataBook);
			if (!Flowgate.flowgateOrderRipen(flowOrderlineNo))
				extentTest.log(LogStatus.FAIL, "FlowGate Ripen",
						"Ripening was unsuccessful");
			else
				extentTest.log(LogStatus.PASS, "FlowGate Ripen",
						"Ripening was Successful");

			driver.switchTo().window(windowBeforeRipen);

			if (!checkForMileStoneCompletion(description)) {
				extentTest
						.log(LogStatus.FAIL,
								"Milestone Completion",
								description
										+ " - is not completed and still found to be Open");
			} else {
				extentTest.log(LogStatus.PASS, "Milestone Completion",
						description + " - is Completed");
			}

		default:

			break;
		}
	}

	private void flowRefreshPageAndExpand() {
		driver.navigate().refresh();
		for (int loader = 1; loader <= 5; loader++) {
			waitForPageToLoad();
		}

		String showHide = locateElement(BroadBand_Flow_Locators.lblShowHide)
				.getText().trim();
		if (showHide.equals("Show")) {
			// clickOn(Test_BB_Flow.imgShowHide);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("EventsPanel.expandEvents();");
		}

		for (int loopCounter = 1; loopCounter <= 20; loopCounter++) {
			if (locateElements(BroadBand_Flow_Locators.tblEvents).size() > 0) {
				if (!locateElement(BroadBand_Flow_Locators.tblEvents)
						.isDisplayed()) {
					snooze(10000);
				} else {
					break;
				}
			} else {
				snooze(10000);
			}

		}
	}

	private boolean checkForMileStoneCompletion(String description) {

		for (int checkCounter = 1; checkCounter <= 45; checkCounter++) {
			int row = getRowWithCellText(BroadBand_Flow_Locators.tblEvents,
					description);
			if (row == -1)
				return false;
			String finsihTime = getCellData(BroadBand_Flow_Locators.tblEvents,
					row, 4);
			if (finsihTime.isEmpty()) {
				snooze(1000 * 60 * 1);
				flowRefreshPageAndExpand();
			} else
				return true;
		}
		return false;
	}

	private void captureAttributeValues() {
		if (empVersion.isEmpty()) {
			empVersion = getTextOf(BroadBand_Flow_Locators.lblEMPVersion)
					.trim();
			if (!empVersion.isEmpty())
				try {
					// dataBook.put("EMPVersion",
					// "EMP"+String.valueOf(empVersion));
					updateOutputValue("EMPVersion",
							"EMP" + String.valueOf(empVersion));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

		}

		if (serviceID.isEmpty()) {
			serviceID = getTextOf(BroadBand_Flow_Locators.lblServiceID).trim();

			if (!serviceID.isEmpty())
				try {
					// dataBook.put("ServiceID", String.valueOf(serviceID));
					updateOutputValue("ServiceID", String.valueOf(serviceID));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

		}

		if (exchangeDistrictID.isEmpty()) {
			exchangeDistrictID = getTextOf(
					BroadBand_Flow_Locators.lblExchangeDistrictID).trim();
			if (!exchangeDistrictID.isEmpty())
				// dataBook.put( "ExchangeDistrictID",
				// String.valueOf(exchangeDistrictID));
				updateOutputValue("ExchangeDistrictID",
						String.valueOf(exchangeDistrictID));
		}

		if (flowOrderlineNo.isEmpty()) {
			flowOrderlineNo = getTextOf(
					BroadBand_Flow_Locators.lblFlowOrderlineNo).trim();
			if (!flowOrderlineNo.isEmpty())
				// dataBook.put( "FlowOrderlineNo",
				// String.valueOf(flowOrderlineNo));
				updateOutputValue("FlowOrderlineNo",
						String.valueOf(flowOrderlineNo));
		}

		if (flowOrderNo.isEmpty()) {
			flowOrderNo = getTextOf(BroadBand_Flow_Locators.lblFlowOrderNo)
					.trim();
			if (!flowOrderNo.isEmpty())
				// dataBook.put( "FlowOrderNo", String.valueOf(flowOrderNo));
				updateOutputValue("FlowOrderNo", String.valueOf(flowOrderNo));
		}

		String flowOrderType = getTextOf(BroadBand_Flow_Locators.lblOrderType)
				.trim();
		String flowGEAID;

		if ((!flowOrderType.isEmpty()) && flowOrderType.contains("Cease")) {
			flowGEAID = getTextOf(BroadBand_Flow_Locators.lblGEAID).trim();
			if (!flowGEAID.isEmpty())
				// dataBook.put( "buyersId", String.valueOf(flowGEAID));
				updateOutputValue("buyersId", String.valueOf(flowGEAID));
		}

	}

	public void flowMilestoneCheck_ProgressWithCVF() {
		boolean switchWindowSuccess = false, isComplete = false, isOrderComplete = false;
		String current_Flow_URL = "";
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			driver.switchTo().window(window);
			String title = driver.getTitle();
			if (title.equals("Monitor Progress Service Details")) {
				switchWindowSuccess = true;
				current_Flow_URL = driver.getCurrentUrl();
				driver.close();
				break;
			}
		}

		driver.switchTo().window(parentWindow);
		driver.navigate().to(current_Flow_URL);

		if (!switchWindowSuccess) {
			extentTest
					.log(LogStatus.FAIL, "Navigation",
							"Could not be navigated to <B>Monitor Progress Service Details<B> page");
		} else {
			extentTest.log(LogStatus.PASS, "Navigation",
					"Navigated to <B>Monitor Progress Service Details<B> page");
		}

		snooze(5000);
		String showHide = locateElement(BroadBand_Flow_Locators.lblShowHide)
				.getText().trim();
		if (showHide.equals("Show")) {
			// locateElement(Test_BB_Flow.imgShowHide).click();
			// clickOn(Test_BB_Flow.imgShowHide);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("EventsPanel.expandEvents();");
		}

		for (int loopCounter = 1; loopCounter <= 20; loopCounter++) {
			snooze(5000);
			if (!locateElement(BroadBand_Flow_Locators.tblEvents).isDisplayed()) {
				// TODO Should repeat
			}

			WebElement tblElement = locateElement(BroadBand_Flow_Locators.tblEvents);
			List<WebElement> rows = tblElement.findElements(By.tagName("tr"));
			int rowCount = rows.size();
			if (rowCount == 0) {
				break;
			}

			for (int iRow = rowCount; iRow >= 3; iRow--) {
				String eventType = getCellData(
						BroadBand_Flow_Locators.tblEvents, iRow, 1);
				String description = getCellData(
						BroadBand_Flow_Locators.tblEvents, iRow, 2);
				String actualFinishTime = getCellData(
						BroadBand_Flow_Locators.tblEvents, iRow, 4);
				System.out.println(description);
				// String eventType =
				// rows.get(iRow).findElements(By.xpath(".//*[local-name(.)='th' or local-name(.)='td']")).get(0).getText().trim();

				switch (eventType) {
				case "EXCEPTION":

					break;

				case "MILESTONE":
					if (actualFinishTime.isEmpty()) {
						isComplete = flow_CheckMilestone(description);

						if (!isComplete)
							extentTest.log(LogStatus.FAIL, "Pause Completion",
									"@Milestone - " + description
											+ " Pause is not successful");
					} else
						extentTest.log(LogStatus.PASS, "Pause Completion",
								"@Milestone - " + description
										+ " Pause is successful");

					break;
				}
				if (isComplete) {
					System.out.println("Break after Switch");
					break;
				}
			}
			System.out.println("Before snooze");
			if (isOrderComplete)
				break;
			snooze(30 * 1000 * 1);
			System.out.println("After snooze");
			flowRefreshPageAndExpand();
		}

	}

	private boolean flow_CheckMilestone(String description) {
		boolean blnresult = false;
		switch (description) {
		case "Wait for GEA Provide Acknowledged event":
			if (SellersId.isEmpty()) {
				SellersId = getTextOf(BroadBand_Flow_Locators.SellersId).trim();
				if (!SellersId.isEmpty())
					try {
						// dataBook.put( "SellersId",
						// String.valueOf(SellersId));
						updateOutputValue("SellersId",
								String.valueOf(SellersId));
						BroadBand_CVF_Logic CVF = new BroadBand_CVF_Logic(
								driver, dataBook);
						boolean returnvalue = CVF.CVF_Pause_Send_Details();
						return returnvalue;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
			// String windowBeforePause = driver.getWindowHandle();
			// for cvf we need to create
			// driver.switchTo().window(windowBeforePause);

			KCI1flag = blnresult;
			break;
		default:
			break;
		}
		return false;
	}// flow_CheckMilestone
}// Class END

*/