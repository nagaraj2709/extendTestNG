package com.nv.extendtestng.testlogic;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.nv.extendtestng.frameworkutils.WebDriverFactory;
import com.nv.extendtestng.uimaps.AutomationPractice_Locators;

public class AutomationPractice extends WebDriverFactory{

	public AutomationPractice(WebDriver driver, Map<String, String> dataBook) {
		super(driver, dataBook);
	}
	
	public void loginToAutomationPracticeSite() {
		launchAPP(dataBook.get("AT_URL"));
		waitForPageToLoad();
		
	}
	
	public void clickOnSignInAndForgotPassword() {
		clickOn(AutomationPractice_Locators.lnkSigIn);
		clickOn(AutomationPractice_Locators.lnkForgorPassword);
	}

}
