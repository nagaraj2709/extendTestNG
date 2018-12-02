/*package com.nv.extendtestng.testlogic;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;

import com.nv.extendtestng.frameworkutils.WebDriverFactory;
import com.nv.extendtestng.uimaps.BroadBand_FlowGate_Locators;

public class BroadBand_FlowGate_Logic extends WebDriverFactory {

	public BroadBand_FlowGate_Logic(WebDriver driver, Map<String, String> dataBook) {
		super(driver, dataBook);
	}

	public boolean flowgateOrderRipen(String FlowOrderLineNo) {

		boolean businessComponentresult = false, blnnavigate = false;

		String url = dataBook.get("FlowgateURL"), 
				 FlowgateSourceSystem = dataBook.get("FlowgateSourceSystem");//, Floworderline = "250323999";
		boolean openedNewTab = openBlankTab();
		//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
		//switchToBrowserTab(1);
		
		driver.navigate().to(url);
		waitForPageToLoad();

		driver.switchTo().frame(locateElement(BroadBand_FlowGate_Locators.frameTop));
		snooze(1000);
		if (isDisplayed(BroadBand_FlowGate_Locators.flowgateSourceSystem)) {
			extentTest.log(LogStatus.PASS, "Login:","Login is Successful");
			selectDropDownValue(BroadBand_FlowGate_Locators.flowgateSourceSystem,
					FlowgateSourceSystem);
			waitForPageToLoad();

		} else {
			extentTest.log(LogStatus.FAIL, "Login:","Login is unSuccessful");
		}

		driver.switchTo().defaultContent();
		snooze(2000);

		driver.switchTo().frame(locateElement(BroadBand_FlowGate_Locators.framelp));
		snooze(1000);

		if (isDisplayed(BroadBand_FlowGate_Locators.lnkCCMMapper))	{
			extentTest.log(LogStatus.PASS, "Login:","CCM Mapper link is displayed");
			clickOn(BroadBand_FlowGate_Locators.lnkCCMMapper);
			waitForPageToLoad();
		} else {
			extentTest.log(LogStatus.FAIL, "Login:","CCM Mapper link is not displayed");
		}

		driver.switchTo().defaultContent();
		snooze(2000);

		driver.switchTo().frame(locateElement(BroadBand_FlowGate_Locators.framerp));
		snooze(1000);

		Set<String> windowsInitial = driver.getWindowHandles();
		int initialCount = windowsInitial.size();

		if (isDisplayed(BroadBand_FlowGate_Locators.lnkTestHarness)){
			extentTest.log(LogStatus.PASS, "Login:","lnkTestHarness is displayed");
			clickOn(BroadBand_FlowGate_Locators.lnkTestHarness);
			waitForPageToLoad();
			snooze(10000);
			System.out.println("Link Test Harness is clicked");
		} else {
			extentTest.log(LogStatus.FAIL, "Login:","lnkTestHarness is displayed");
		}

		driver.switchTo().defaultContent();
		snooze(2000);

		String parentWindow = driver.getWindowHandle();

		for (int i = 1; i <= 5; i++) {

			Set<String> windows = driver.getWindowHandles();
			if (initialCount != windows.size()) {
				for (String WindowName : windows) {
					System.out.println("window name is::"+WindowName.toString());
					if (WindowName.equalsIgnoreCase(parentWindow))
						continue;

					driver.switchTo().window(WindowName);
						System.out.println("started with switching to window name");
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					if (locateElements(BroadBand_FlowGate_Locators.flowgateuAuditUserID).size() >= 1) {
						blnnavigate = true;
						System.out.println("user id is avaialble");
						typeIn(BroadBand_FlowGate_Locators.flowgateuAuditUserID, "*");
						typeIn(BroadBand_FlowGate_Locators.uCHISSurrogateUserID, "*");
						typeIn(BroadBand_FlowGate_Locators.olOrderLineID,FlowOrderLineNo);
						clickOn(BroadBand_FlowGate_Locators.Submit);

						waitForPageToLoad();

						if (isDisplayed(BroadBand_FlowGate_Locators.flowRipSuccess)) {
							extentTest.log(LogStatus.PASS, "FlowGateRippen:","Rippen is successful");
							businessComponentresult = true;
						} else {
							extentTest.log(LogStatus.FAIL, "Login:","Rippen is Failure");
						}

						break;
					}
				}

				if (blnnavigate) {
					break;
				}
			} else {

				driver.switchTo().window(parentWindow);
				snooze(1000);
			} // #if(initialCount != windows.size())
		}
		return businessComponentresult;
	}
}
*/