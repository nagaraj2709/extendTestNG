package com.nv.extendtestng.testlogic;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.nv.extendtestng.frameworkutils.WebDriverFactory;

public class DemoTest2 extends WebDriverFactory{

	public DemoTest2(WebDriver driver, Map<String, String> dataBook) {
		super(driver, dataBook);
	}
	
	public void testmethod2(){
		System.out.println("In Demo Test2" + dataBook.get("OrderID"));
	}
}
