package com.nv.extendtestng.uimaps;

import org.openqa.selenium.By;



public class BroadBand_Flow_Locators {

	
	public static final By txtmyuserid = By.name("myuserid");
	public static final By txtmypassword = By.name("mypassword");
	public static final By btnLogin = By.xpath("//input[@value='Login to FLOWManage']");
	public static final By txtmyauthorised = By.name("myauthor");
	public static final By btnConfirm = By.xpath("//input[@value='Confirm']");
	public static final By lnkNewFlowDesktop = By.linkText("New Flow Desktop Link");
	
	//objects under Flow desktop link page
	public static final By txtAttrId = By.id("txtAttrId");
	public static final By cmbAttrId = By.id("id_cmbAttrId");
	public static final By btnSubmitID = By.name("btnSubmitID");
	
	//Search Results Page
	public static final By IdResultTable = By.id("IdResultTable");
	public static  String searchResultTable = "IdResultTable";
	
	//Monitor Progress Service Details Page
	public static final By lblShowHide = By.id("EventsCE");
	public static final By imgShowHide = By.id("ByEvents");
	public static final By tblEvents = By.id("EventsTab");
	
	//Capture Attributes
	public static final By lblEMPVersion = By.xpath( "//TD[contains(@class,'FDResultsCell') and contains(text(), 'EMP Version')]/following-sibling::td");
	public static final By lblServiceID = By.xpath( "//TD[contains(@class,'FDResultsCell') and contains(text(), 'Service ID')]/following-sibling::td");
	public static final By lblExchangeDistrictID = By.xpath( "//TD[contains(@class,'FDResultsCell') and contains(text(), 'Exchange District ID')]/following-sibling::td");
	public static final By lblFlowOrderlineNo = By.xpath( "//TD[contains(@class,'FDResultsCell') and contains(text(), 'Flow Orderline No')]/following-sibling::td");
	public static final By lblFlowOrderNo = By.xpath( "//TD[contains(@class,'FDResultsCell') and contains(text(), 'Flow Order No')]/following-sibling::td");
	public static final By lblOrderType = By.xpath( "//TD[contains(@class,'FDResultsCell') and contains(text(), 'Order Type')]/following-sibling::td");
	public static final By lblGEAID = By.xpath( "//TD[contains(@class,'FDResultsCell') and contains(text(), 'GEA ID')]/following-sibling::td");
	public static final By SellersId = By.xpath("//td[.='Sellers ID']/following-sibling::td[1]");
	
	
	
	
	//TR[TD[contains(@class,'FDResultsCell') and contains(text(), 'EMP Version')]]/TD[4]

	
}