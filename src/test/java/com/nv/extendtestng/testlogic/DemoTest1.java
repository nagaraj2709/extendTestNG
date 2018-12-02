package com.nv.extendtestng.testlogic;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.nv.extendtestng.frameworkutils.WebDriverFactory;

public class DemoTest1 extends WebDriverFactory {

	public DemoTest1(WebDriver driver, Map<String, String> dataBook) {
		super(driver, dataBook);
	}
	
	public void testmethod1(){
		System.out.println(dataBook.get("OrderID"));
		updateOutputValue("OrderID", "Nagaraj");
	}

}
