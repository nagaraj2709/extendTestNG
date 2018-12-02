package com.nv.extendtestng.uimaps;

import org.openqa.selenium.By;

public class BroadBand_FlowGate_Locators {
	public static final By frameTop = By.name("top");
	public static final By framelp = By.name("lp");
	public static final By framerp = By.name("rp");

	public static final By flowgateSourceSystem = By.name("sub");
	public static final By lnkCCMMapper = By.linkText("CCM Mapper- Source system for WBC provisioning by Flow");
	public static final By lnkTestHarness = By.linkText("BB3.3 Test Harness to Update EVTimeout Date");

	public static final By flowgateuAuditUserID = By.name("uAuditUserID");
	public static final By uCHISSurrogateUserID = By.name("uCHISSurrogateUserID");
	public static final By olOrderLineID = By.xpath("//input[starts-with(@name,'olOrderLineID')]");

	public static final By flowRipSuccess = By.xpath("//font[contains(text(),'Enter order successful, order number')]");
	public static final By Submit = By.name("Submit");
}
