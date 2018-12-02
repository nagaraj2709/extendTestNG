package com.nv.extendtestng.uimaps;

import org.openqa.selenium.By;



public class BroadBand_CVF_Locators {
	
	public static final By loginusername = By.name("j_id_id2:loginusername");
	public static final By loginpassword = By.name("j_id_id2:loginpassword");
	public static final By submit = By.id("j_id_id2:submit");
	public static final By submission = By.id("cp_submission");
	public static final By lnkLogout = By.xpath("//input[@value='Logout']");
		
	public static final By eventType = By.id("cpportal:eventType");
	public static final By event = By.id("cpportal:event");
	
		
	public static final By event0 = By.id("itemstable:0:itemstabletextbox");
	public static final By event1 = By.id("itemstable:1:itemstabledropdown");
	public static final By event2 = By.id("itemstable:2:itemstabledropdown");
	public static final By event3 = By.id("itemstable:3:itemstabledropdown");
	public static final By event4= By.id("itemstable:4:itemstabledropdown");
	public static final By event5 = By.id("itemstable:5:itemstabletextbox");
	public static final By event6 = By.id("itemstable:6:itemstabledropdown");
	public static final By Submit = By.xpath("//input[@value='Submit']");
	
	//Search
	public static final By Search = By.xpath("//input[@value='Search']");
	public static final By SearchbyID = By.id("cpportal:searchByID");
	public static final By tablelastrequests = By.id("lastrequests");
	
	//Tracking ID
	
	public static final By trackingID = By.xpath("//tr[td[contains(text(),'Tracking ID:')]]//input");
	
	//krishna's locators
	public static final By Thoroughfareno = By.xpath("//tr[td[label[contains(text(),'Thoroughfare No')]]]//input");
	public static final By Thoroughfarename = By.xpath("//tr[td[label[contains(text(),'Thoroughfare Name')]]]//input");
	public static final By Locality = By.xpath("//tr[td[label[contains(text(),'Locality')]]]//input");
	public static final By Post_Town = By.xpath("//tr[td[label[contains(text(),'Post Town')]]]//input");
	public static final By Postcode = By.xpath("//tr[td[label[contains(text(),'Postcode')]]]//input");
	public static final By Key_Type = By.xpath("//tr[td[label[contains(text(),'Key Type')]]]//select");
	public static final By Submit_btn = By.xpath("//input[@value='Submit']"); 
	public static final By Search_btn = By.className("searchButton");
	
	
	//For Gold Key Generation
	public static final By Address_Key = By.xpath("//tr[td[label[contains(text(),'Address Key')]]]//input");
	public static final By Address_Type = By.xpath("//tr[td[label[contains(text(),'Address Type')]]]//select");
	public static final By Transport_Medium = By.xpath("//tr[td[label[contains(text(),'Transport Medium')]]]//select");
	public static final By FTTP_Site_type = By.xpath("//tr[td[label[contains(text(),'FTTP Site Type')]]]//select");
	
	
	//for Pause Scenario
	public static final By Order_number = By.xpath("//tr[td[label[contains(text(),'Order Number')]]]//input");
	public static final By Related_Orders = By.xpath("//tr[td[label[contains(text(),'Related Orders')]]]//select");
	public static final By KCI = By.xpath("//tr[td[label[contains(text(),'KCI')]]]//select");
	

		
}
