package com.nv.extendtestng.uimaps;

import org.openqa.selenium.By;

public class BroadBand_SEMP_Locators {

		public static final By loginId = By.name("loginId");
		public static final By password = By.name("password");
		public static final By btnLogin = By.xpath("//input[@value='Login']");
		public static final By lnkLogout = By.linkText("Logout");
		
		
			
		public static final By lnkConfigurationManagement = By.linkText("Configuration Management");
		public static final By lnkNotifications =  By.linkText("Notifications");
		public static final By lnkRouting =  By.linkText("Routing");
		public static final By lnkRoutingMgmt =  By.linkText("Routing Management");
		public static final By OrderStatusUpdate = By.name("formOSU - OrderStatusUpdate");
		public static final By FaultStatusUpdate = By.xpath("//select[@id='formTRSU - TroubleReportStatusUpdate']");
		public static final By EnvironmentDetails = By.xpath("//FORM[@id='OSUForm']//LEGEND");
		
		public static final By FaultEnvironmentDetails = By.xpath("//legend[contains(text(),'Environment Details')]");
		public static final By stackName = By.id("MODEL-A-522695198_FTTC_FULFILMENT(Only)");
		public static final By sellersItemIdentification = By.name("sellersItemIdentification");
		public static final By empVersion = By.name("empVersion");
		public static final By orderType = By.name("orderType");
		public static final By buyersId = By.name("buyersId");
		
		public static final By appointmentDate = By.name("appointmentDate");
		public static final By appointmentTimeslot = By.name("appointmentTimeslot");
		public static final By appointmentChanged = By.name("appointmentChanged");
		public static final By appointmentAllocated = By.name("appointmentAllocated");
		public static final By appointmentNotNeeded = By.name("appointmentNotNeeded");
		public static final By fttcAvailable = By.name("fttcAvailable");
		public static final By outerTag = By.name("outerTag");
		public static final By innerTag = By.name("innerTag");
		public static final By serviceId = By.name("serviceId");
		public static final By installationDN = By.name("installationDN");
		public static final By districtCode = By.name("districtCode");
		public static final By Submit = By.xpath("//input[@value='Submit']");
		
		
		// Capture T2r
		public static final By TroubleReportStatusUpdate = By.name("formTRSU - TroubleReportStatusUpdate");
		public static final By reportingReference = By.name("reportingReference");
		public static final By maintenanceReference = By.name("maintenanceReference");
		public static final By status = By.name("status");
		public static final By subStatus = By.name("subStatus");
		public static final By message= By.name("message");
		public static final By messsageButton = By.name("messsageButton");
		public static final By clearCode= By.name("clearCode");
		
		//public static final By removeMessageFromArray = By.xpath("//tr[@ondblclick='removeMessageFromArray(\'0\')']");
		//public static final By removeMessageFromArrayFirst = By.xpath("//tr[@ondblclick='removeMessageFromArray(\'1\')']");
		
		public static final By removeMessageFromArrayFirst = By.xpath(".//*[contains(text(),'(TR)Trouble Report AcceptedXXXX')]");
		
		//Semp MSG Routing for NAD,DN,
		public static final By msgKeyVal = By.name("messageKeyValue");
		public static final By msgType = By.name("messageType");
		public static final By Sender = By.name("senderPartnerId");
		public static final By Receiver = By.name("recieverPartnerId");
		public static final By Add = By.xpath("//input[@value='Add']");
		
		public static final By SuccessMsg = By.xpath("//center[b[Contains(text(),'Operation performed successfully.')]]");
		public static final By MsgRoutingEntries = By.xpath("//tbody[tr[th[contains(text(),'Message Type')]]]");
		
		public static final By MsgKeySearch = By.xpath("//tr[td[contains(text(),'Message Key :')]]//input");
		public static final By btnSearch = By.xpath("//input[@value='Search']");
		
		
		
}
