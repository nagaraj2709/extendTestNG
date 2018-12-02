package com.nv.extendtestng.uimaps;


import org.openqa.selenium.By;

public class BroadBand_SaaS_Locators {
	
	
	//public static final By frameTop = By.name("top");
	public static final By continueLnkForIe = By.xpath("//a[contains(text(),'Continue')]");
	public static final By txtUserName = By.cssSelector("input[id=login-username]");
	public static final By txtpassword =By.cssSelector("input#login-password");
	public static final By lnkBTWholeSale = By.xpath("//a[@href='/portalzone/portalzone/homeLogin.do']");
	public static final By btnLogin = By.cssSelector("button.btn-primary.login-submit.BTFont-Bd"); // shortened the xpath as it was not working fine in Firefox
	//public static final By lnkLogout = By.cssSelector("a#logout");
	public static final By lnkLogout = By.id("logout");
	//********************************  First time user Popup********************************************//
	public static final String FTPopup = "//div[contains(@class,'PopupFirstTimeVisitor')]";
	public static final By popupFirstTimeUser = By.xpath(FTPopup);
	public static final By lnkCloseCross_FirstTimePopup = By.xpath(FTPopup + "//a[@title='Close']");
	
	//********************************  First time user Popup********************************************//
	public static final By SAAS_MY_APPS = By.xpath("//*[contains(text(),'My apps')]");
	public static final By SAAS_Ecoplus_Link = By.xpath("//a[@appname='Eco Plus']");
	///////////////////////////////////////////////
	
	public String test1 = "";
	public static final By SAAS_close_image = By.xpath("//a[@title='Close']//img");
	//public static final By SAAS_Ecoplus_Link = By.cssSelector("a[appname='Eco Plus']");
	public static final By SAAS_Order_new_Services_Link = By.xpath("//a[contains(text(),'Order new services')]");
	public static final By SAAS_Select_Account_List = By.xpath("//tr[td[contains(text(),'Please select the account you wish to raise your order against')]]//select");
	public static final By SAAS_Broadband_Link = By.xpath("//a[text()='Wholesale broadband connect']");
	public static final By SAAS_BroadBand_Access = By.xpath("//tr[td[span[text()='End user broadband access']]]//Img[@title='Record Selected']");
	public static final By SAAS_Select_btn = By.xpath("//img[@title='select']");
	public static final By SAAS_Iwantto_selection = By.xpath("//tr[td[contains(text(),'I want to')]]//select");
	
	//WBC page - Details
	public static final By SAAS_WBC_ItemSelection_list = By.xpath("//tr[td[contains(text(),'Order types')]]//select");
	public static final By SAAS_WBC_ItemDetailsPage_TD = By.xpath("//td[contains(text(),' Add  WBC End User Access (EUA)')]");
	public static final By SAAS_WBC_DN_Text = By.xpath("//tr[td[contains(text(),'Directory number (DN)')]]//input");
	public static final By SAAS_WBC_PostCode_Text = By.xpath("//tr[td[contains(text(),'Postcode')]]//input");
	public static final By SAAS_WBC_check_Avaibilink_link = By.xpath("//img[@title='check availability']");
	public static final By SAAS_WBC_Access_Technology_Edit = By.xpath("//tr[td[contains(text(),'Access technology')]]//Select");
	public static final By SAAS_WBC_Result_Details = By.xpath("//tr[td[contains(text(),'Result details')]]");
	public static final By SAAS_WBC_Continue = By.xpath("//img[@title='Continue']");
	public static final By SAAS_WBC_Customer_Required_Date = By.xpath("//td[contains(text(),'Customer required date')]");
	public static final By SAAS_WBC_CRD_Date = By.xpath("//tr[td[contains(text(),'Customer required date')]]//input");
	
	public static final By SAAS_WBC_Customer_Required_LeadTime = By.xpath("//img[@src='images/icon_calendar.gif']");
	public static final By SAAS_WBC_Month = By.xpath("//select[@name='month']");
	public static final By SAAS_WBC_Year = By.xpath("//select[@name='year']");
	public static final By SAAS_WBC_Save = By.xpath("//img[@src='/static/amp/images/save.gif']");
	public static final By SAAS_WBC_Billing_Account = By.xpath("//tr[td[contains(text(),'Billing account')]]//input");
	public static final By SAAS_WBC_Retailer_ID = By.xpath("//tr[td[contains(text(),'Retailer ID')]]//input");
	public static final By SAAS_WBC_First_Name = By.xpath("//td[contains(text(),'First name')]/following-sibling::td/descendant::input");
	public static final By SAAS_WBC_Last_Name = By.xpath("//td[contains(text(),'Last name')]/following-sibling::td/descendant::input");
	public static final By SAAS_WBC_Search = By.xpath("//img[@title='Search']");
	public static final By SAAS_WBC_Record_Selection = By.xpath("//img[contains(@title,'Record Selected')]");
	public static final By SAAS_WBC_Record_Select_Btn = By.xpath("//img[@ title='select']");
	
	
	//Prodcut Options
	public static final By SAAS_WBC_ProductOptions_Managed_Install_Module1 = By.xpath("//tbody[tr[td[contains(text(),'Managed Install Module 1')]]]//input[@type='checkbox']");
	public static final By SAAS_WBC_ProductOptions_Managed_Install_Module1_Select = By.xpath("//tbody[tr[td[contains(text(),'Managed Install Module 1')]]]//select");
	public static final By SAAS_WBC_ProductOptions_done_Img = By.xpath("(//img[@title='Done'])[2]");
	
	//Order validatiion Results
	public static final By SAAS_WBC_OrderValidation_Results = By.xpath("//td[contains(text(),'Order validation results')]");
	public static final By SAAS_WBC_OrderValidation_Cease_Results = By.xpath("//td[contains(text(),'Validation results')]");
	
	public static final By SAAS_WBC_OrderValidation_Results_Msg = By.xpath("//td[contains(text(),\"We have checked your order, please press 'continue' to proceed.\")]");
	public static final By SAAS_WBC_OrderValidation_conitune = By.xpath("//input[@type='image']");
	
	
    //Confirm page details.	
	public static final By SAAS_WBC_Order_Ref = By.xpath("//tr[td[contains(text(),'Your order reference')]]//input");	
	public static final By SAAS_WBC_Order_Desc = By.xpath("//tr[td[contains(text(),'Your order description')]]//textarea");     
	public static final By SAAS_WBC_Order_Update_method = By.xpath("//tr[td[contains(text(),'Order update method')]]//select");	
	public static final By SAAS_WBC_confirm_Order_Details = By.xpath("//input[@title='Confirm order details']");
	public static final By SAAS_WBC_confirm_Order_Details_chkbox = By.xpath("//tr[td[contains(text(),'I accept the terms and conditions and understand')]]//input[@type='checkbox']");
	
	
	public static final By SAAS_WBC_Submit_btn = By.xpath("//img[@title='submit']");
	public static final By SAAS_Portal_Order = By.xpath("//tr[td[contains(text(),'BT reference:')]]//span");
	public static final By SAAS_Portal_Order1 = By.cssSelector("input#login-password");
	
	
	//Cease Journey
	public static final By SAAS_Modify_Cease_Services_Link = By.xpath("//a[contains(text(),'Modify or cease services')]");
	public static final By SAAS_Select_accout = By.xpath("//tr[td[contains(text(),'Select account')]]//select");
	public static final By SAAS_DN = By.xpath("//tr[td[contains(text(),'Directory number')]]//input");
	public static final By SAAS_Status = By.xpath("//tr[td[contains(text(),'Status')]]//select");
	public static final By SAAS_search = By.xpath("//img[@title='Search']");
	public static final By SAAS_select_Cease_Option = By.xpath("//select/option[@value='CEASE']");
	public static final By SAAS_select_Cease_select = By.xpath("//select");
	public static final By SAAS_select_Cease_Go_btn = By.xpath("//img[@title='Go']");
	public static final By SAAS_select_DN_Status = By.xpath("//tr[td[contains(text(),'Status')]]//span");
	
	//=====================================
	//Item details: Cease - BBEU
	//=====================================	
	public static final By SAAS_select_DN_Validation = By.xpath("//tr[td[contains(text(),'Directory number')]]//span");	
	public static final By SAAS_CRD_Validation = By.xpath("//tr[td[contains(text(),'Customer required date')]]//input");	
	public static final By SAAS_select_Cease_Reason = By.xpath("//tr[td[contains(text(),'Cease reason')]]//select"); //--- Change of supplier	
	public static final By SAAS_Continue_btn = By.xpath("//img[@title='Continue']");	
	public static final By SAAS_Cancel_btn = By.xpath("//img[@title='Cancel']");
	
	//============================================
		//Data - Cancel Orders
	//============================================
	
	public static final By SAAS_TrackOrder = By.xpath("//a[contains(text(),'Track orders')]");
	public static final By SAAS_BT_Ref = By.xpath("//tr[td[contains(text(),'BT reference')]]//input");
	public static final By SAAS_BT_Status = By.xpath("//tr[td[contains(text(),'Status')]]//select");
	public static final By SAAS_Select_Data_account = By.xpath("//tr[td[contains(text(),'Select account')]]//select");
	

	public static final By SAAS_OrderDetails_Verify = By.xpath("//td[contains(text(),'Order details')]");
		
	public static final By SAAS_Designcomplete_status = By.xpath("//span[contains(text(),'Design complete')]");
	public static final By SAAS_CancelButton = By.xpath("//img[@title='Cancel']");
	
	public static final By SAAS_CancelReason = By.xpath("//tr[td[contains(text(),'Cancellation reason')]]//select"); //
	public static final By SAAS_ContinueButton = By.xpath("//img[@title='Continue']");
	
	public By SAAS_ContinueButton1 = By.xpath("//img[@title='"+test1+"']");
	//call baseket and submit order
	
	//Data - Provide Order
	public static By SAAS_Navigate_To_MyOrders = By.xpath("//a[contains(text(),'My orders')]");
	public static final By SAAS_To_Place_New_Order_Menu = By.xpath("//div[@class='place_order_popup orders_place_order_popup pos_abs hidden']//h2[contains(text(),'Place a new order')]");
	public static final By SAAS_Place_New_Order_Account_select = By.id("placeorder_cuglist");
	public static final By SAAS_Place_New_Order_button = By.xpath("//a[contains(text(),'Place a new order')]");
	public static final By SAAS_clcikon_Etherway_Link = By.xpath("//a[@id='S0120322']");
	public static final By SAAS_select_network = By.xpath("//span[@id='SelectNetwork_Label']");
	public static final By SAAS_search_network = By.xpath("//span[contains(text(),'Search networks')]");
	public static final By SAAS_search_Field = By.className("searchField");
	public static final By SAAS_search_Input = By.className("searchInput");
	public static final By SAAS_search_Img = By.id("SearchNetwork_Label");
	public static final By SAAS_select_Network_ref = By.xpath("//td[@id='1Network_service_reference']");
	public static final By SAAS_select_Network_Confirm = By.xpath("//span[@id='BTWButtonConfirm_Label']");
	
	public static final By SAAS_selected_Network_Label = By.xpath("//span[contains(text(),'You have selected the following network')]");
	public static final By SAAS_selected_Network_Label_ref = By.xpath("//tr[td[div[*[@id='LabelServiceRef_Label']]]]//label[contains(text(),'ETHN00393270')]");
	public static final By SAAS_Next_Label = By.id("Next_Label");
	//===============================================================================================
	//search for bandwidth
	//===============================================================================================
	public static final By SAAS_Bandwidth_input = By.xpath("//tr[td[div[*[@id='Bandwidth_NEW_Label']]]]//input");
	public static final By SAAS_Bandwidth_input_search_input = By.xpath("//tr[td[div[*[@id='Bandwidth_NEW_Label']]]]//input");
	public static final By SAAS_Bandwidth_selection = By.xpath("//a[contains(text(),'10Mbit/s')]");
	public static final By SAAS_Post_code_search = By.xpath("//tbody[tr[td[div[span[@id='Site_address_Label']]]]]//input");
	public static final By SAAS_Post_code_search_btn = By.xpath("//*[@id='Search_Button_Label']");
	public static final By SAAS_Post_code_availabiltiy = By.xpath("//tr[@aria-selected='true']");
	public static final By SAAS_Post_code_confirm = By.id("ConfirmButton_Label");
	public static final By SAAS_Post_code_visibility = By.xpath("//div[@class='bt-fieldReadOnly']//label");
	public static final By SAAS_Post_code_check_availabiltiy = By.xpath("//*[@id='Check_availability_Label']");
	public static final By SAAS_check_availabiltiy_Message = By.xpath("	//span[contains(text(),'The service you require is available.')]");
	public static final By SAAS_Friendly_Name_Ref = By.xpath("//input[@aria-label='Friendly Name']");
	
	
	public static final By SAAS_Floor = By.xpath("//input[@aria-label='Floor']");
	public static final By SAAS_Room = By.xpath("//input[@aria-label='Room']");
	public static final By SAAS_Location = By.xpath("//input[@aria-label='Location']");
	public static final By SAAS_Password = By.xpath("//input[@aria-label='Password']");
	public static final By SAAS_Sitetype = By.xpath("//input[@aria-label='Site Type']");
	public static final By SAAS_Sitetype_dropdownimg = By.id("SiteType_Label_icon");
	public static final By SAAS_companyname = By.id("ThirdPartyName_Label");
	
	
	public static final By SAAS_Primary_contact = By.id("LabelsetUpPrimary_Label");
	public static final By SAAS_existing_contact = By.xpath("//span[contains(text(),'Add existing contact')]");
	public static final By SAAS_Search_By_lastName_value = By.id("custsearchip");
	public static final By SAAS_Search_By_lastName_img = By.id("SearchContact_Label");
	public static final By SAAS_Confirmlabel = By.id("BTWButtonConfirm_Label");
	public static final By SAAS_Next_btn = By.id("Next_Label");
	//search by - .//*[@id='custsearchip']
	
	//============================================================================================================		
	//customizations
	public static final By SAAS_segmentation_type = By.xpath("//tr[td[div[div[div[contains(text(),'Segmentation type')]]]]]//select"); //VLAN Segmentation or Port Based
	public static final By SAAS_Landlord_Name = By.xpath("//tr[td[div[div[div[contains(text(),'name')]]]]]//input");
	public static final By SAAS_Landlord_phonenumber = By.xpath("//tr[td[div[div[div[contains(text(),'phone number')]]]]]//input");
	public static final By SAAS_Landlord_13ampsocket = By.xpath("(//tr[td[div[div[div[contains(text(),'13amp socket within 1m of the connection')]]]]]//span[contains(text(),'Yes')])/preceding-sibling::input");
	public static final By SAAS_Currently_occupy_premises = By.xpath("(//tr[td[div[div[div[contains(text(),'Currently occupy premises')]]]]]//span[contains(text(),'Yes')])/preceding-sibling::input");
	public static final By SAAS_Termination_Ready = By.xpath("(//tr[td[div[div[div[contains(text(),'Termination area ready')]]]]]//span[contains(text(),'Yes')])/preceding-sibling::input");
	public static final By SAAS_LandLord_consent = By.xpath("(//tr[td[div[div[div[contains(text(),'Is the landlord')]]]]]//span[contains(text(),'Yes')])/preceding-sibling::input");
	public static final By SAAS_Pre_authorized_charges = By.xpath("//tr[td[div[div[div[contains(text(),'Pre-Authorised Excess Construction Charge up to a maximum value of')]]]]]//input");
	public static final By SAAS_customization_next = By.xpath("//div[contains(text(),'Next')]");
	//============================================================================================================	
	//Services in Basket
	//============================================================================================================
									
	public static final By SAAS_Services_in_Basket = By.xpath("//div[span[contains(text(),'Services in your basket')]]");
	public static final By SAAS_Etherway_Services = By.xpath("//span[contains(text(),'Etherway')]");
	public static final By SAAS_Basket_Next = By.id("Next_Label");		
				
	//============================================================================================================
	//Validate network
	//============================================================================================================
	public static final By SAAS_Validate_network_Service_Header = By.xpath("//span[contains(text(),'Validate network')]");
	public static final By SAAS_Validate_network_Service_btn = By.xpath("//*[@id='bt-ValidateNetwork']/span");
	public static final By SAAS_Validate_network_Service_success_msg = By.xpath("//*[@id='bt-NetworkSuccessText']");								
	// - Your network has been validated. You can now continue to the next step and place your order.	
									//*[@id='Next_Label']
	//============================================================================================================								
	public static final By SAAS_Step2_Contacts_Header = By.xpath("//div[contains(text(),'Step 2. Set up your contacts')]");
	public static final By SAAS_KCI_Update = By.xpath("//input[@aria-label='KCI Type']");									
									//*[@id='Next_Label']
	//============================================================================================================
									//*[@id='Billing_accounts_Label'] - Billing accounts
	public static final By SAAS_Step3_BillingAc_Header = By.id("Billing_accounts_Label");
	public static final By SAAS_Step3_BillingAc_value = By.id("1Billing_Account_Number"); //- 0455812521							
	//============================================================================================================
		public static final By SAAS_Step4_Order_confirmation = By.className("bt-PageTitle");
		public static final By SAAS_Step4_Product_details_check = By.xpath("//span[contains(text(),'Etherway')]");
		public static final By SAAS_one_off_charge = By.id("1CalcNetNonRecCharge");
		public static final By SAAS_Monthly_charge = By.id("1CalcNetRecCharge");
		
									//*[@id='LabelConfirmation_Label']  - Confirmation
									
									//*[@id='ReviewOrderMoreText_Label']
		public static final By SAAS_Order_ref = By.xpath("//tr[td[div[span[contains(text(),'Your order reference')]]]]//input");										
		public static final By SAAS_Order_desc = By.xpath("//tr[td[div[span[contains(text(),'Your order description')]]]]//textarea");									
		public static final By SAAS_Order_Accept_Terms_condition_chekbox = By.xpath("//tr[td[span[span[@id='LabelConfirmation1_Label']]]]//input");							
		public static final By SAAS_Place_Order_Button = By.id("LabelPlaceOrder_Label");										
									
	//============================================================================================================							
									//*[@id='']
		public static final By SAAS_Order_confirmation_Header = By.id("BTW_FirstThanks");							
		public static final By SAAS_Order_id = By.id("QuoteNum");
		

	//============================================================================================================
	//T2R Journey 
		public static final By SAAS_Raise_a_Fault = By.xpath("//tr[td[contains(text(),'I want to')]]//select");
		public static final By SAAS_Track_a_Fault = By.xpath("//tr[td[contains(text(),'I want to')]]//select");
		//public static final By SAAS_Raise_a_Fault = By.xpath("//select[@id='s_2_1_0_0']");
		public static final By SAAS_Enter_ServiceID = By.cssSelector("input[id*='rf']");
		public static final By SAAS_Click_Go = By.xpath("//img[@title='Go']");
		public static final By SAAS_T2R_KBD_Sq1 = By.xpath(".//*[@id='lblbbVKBD1']/b");	
		public static final By SAAS_T2R_KBD_Ans1 = By.xpath("//select[@id='bbVKBD1']");
		public static final By SAAS_T2R_KBD_Sq2 = By.xpath(".//*[@id='lblbbVKBD2']/b");
		public static final By SAAS_T2R_KBD_Ans2 = By.xpath("//select[@id='bbVKBD2']");
		public static final By SAAS_T2R_KBD_Sq3 = By.xpath(".//*[@id='lbl1900']/b");
		public static final By SAAS_T2R_KBD_Ans3 = By.xpath("//select[@id='1900']");
		public static final By SAAS_T2R_KBD_Sq4 = By.xpath("//label[@id='lbl1901']");
		public static final By SAAS_T2R_KBD_Ans4 = By.xpath("//input[@id='1901']");
		public static final By SAAS_T2R_KBD_Sq5 = By.xpath(".//*[@id='lblbbEV1019']/b");
		public static final By SAAS_T2R_KBD_Ans5 = By.xpath("//select[@id='bbEV1019']");
		public static final By SAAS_T2R_KBD_Sq6 = By.xpath(".//*[@id='lblbbEV1009']/b");
		public static final By SAAS_T2R_KBD_Ans6 = By.xpath("//select[@id='bbEV1009']");
		public static final By SAAS_T2R_KBD_Sq7 = By.xpath("//label[@id='lblbbVKBD1161']");
		public static final By SAAS_T2R_KBD_Ans7 = By.xpath("//input[@id='bbVKBD1161']");
		public static final By SAAS_T2R_KBD_Sq8 = By.xpath("//label[@id='lblbbVKBD1163']");
		public static final By SAAS_T2R_KBD_Ans8 = By.xpath("//input[@id='bbVKBD1163']");
		public static final By SAAS_T2R_KBD_Sq9 = By.xpath("//label[@id='lblbbVKBD1164']");
		public static final By SAAS_T2R_KBD_Ans9 = By.xpath("//input[@id='bbVKBD1164']");
		public static final By SAAS_T2R_KBD_Sq10 = By.xpath("//label[@id='lblbbVKBD1166']");
		public static final By SAAS_T2R_KBD_Ans10 = By.xpath("//input[@id='bbVKBD1166']");
		public static final By SAAS_T2R_KBD_Sq11 = By.xpath("//label[@id='lblbbEV1009']");
		public static final By SAAS_T2R_KBD_Ans11 = By.xpath("//input[@id='bbVKBD1168']");
		public static final By SAAS_T2R_KBD_Sq12 = By.xpath(".//*[@id='lblbbVKBD1169_ADSL']/b");
		public static final By SAAS_T2R_KBD_Ans12 = By.xpath("//select[@id='bbVKBD1169_ADSL']");
		public static final By SAAS_T2R_KBD_Sq13 = By.xpath(".//*[@id='lblbbVKBD1182']/b");
		public static final By SAAS_T2R_KBD_Ans13 = By.xpath("//select[@id='bbVKBD1182']");
		public static final By SAAS_T2R_KBD_Sq14 = By.xpath(".//*[@id='lblbbVKBD1170']/b");
		public static final By SAAS_T2R_KBD_Ans14 = By.xpath("//select[@id='bbVKBD1170']");
		public static final By SAAS_T2R_Next = By.id("btnNext");
		public static final By SAAS_T2R_Reference = By.xpath("//tr[td[contains(text(),'Fault description')]]//textarea");
		public static final By SAAS_T2R_Agree = By.xpath("//input[@type='checkbox']");
		public static final By SAAS_T2R_Submit= By.xpath("//img[@title='Submit']");
		public static final By SAAS_T2R_Fault = By.xpath("//tr[td[contains(text(),'BT reference')]]//a");
		
		//Data T2R Bys
		public static final By lnk_FaultDiagnostics = By.cssSelector("a#s_1_1_36_0");
		public static final By Search_all_Account = By.xpath("//a[contains(text(),'Search all accounts')]");
		
		public static final By txt_ServiceReference = By.xpath("//tr[td[contains(text(),'Service reference')]]//input");
		public static final By btnSearch = By.xpath("//img[@title='Search']");
		public static final By imgFAULTDIAGNOSTICS = By.xpath("//img[@title='FAULT DIAGNOSTICS']");
		public static final By tblTestResultStatus = By.xpath("//tr[td[span[@class='hubLayoutTableThree']]]//span[contains(text(),'In Progress')]");
		public static final By btnRefreshResults = By.xpath("//img[@title='Refresh Results']");
		//public static final By lnkFaultRef = By.xpath("//tr[td[*[contains(text(),'Test outcome details')]]]//a[contains(@href,'style.visibility = ')]");
		public static final By lnkFaultRef = By.xpath("//a[@id='s_3_1_1_0']");
		public static final By btnReportFault = By.xpath("//img[@title='Report Fault']");
		public static final By btnAcceptFault = By.xpath("//img[@title='accept']");
		
		//public static final By statusofFaultDiagonistic = By.xpath("//tr[td[font[contains(text(),'Your Fault Diagnostic test is now completed')]]]");
		
		//Data T2R KBD

		public static final By txtDiagnosticsummary = By.xpath("//tr[td[contains(text(),'Diagnostic summary')]]");
		//SQ1
		public static final By DataSQ1 = By.xpath(".//*[@id='lbl21ETHA-1']/b");
			
		//public static final By DataSQ1_Modc = By.xpath", ".//*[@id='lbl21ETHA-1']/b");
		//public static final By DataSQ1_v1 = By.xpath", "//label[@id='lbl21ETHA-1']");
		//DataSQ1=//b[contains(text(),'Has this service ever worked within specification')]
		public static final By DataAns1 = By.xpath("//select[@id='21ETHA-1']");   
		//SQ2
		public static final By DataSQ2 = By.xpath(".//*[@id='lblDIRPEER1']/b");
		public static final By DataAns2 = By.xpath("//select[@id='DIRPEER1']"); 
		
		//Select 2 check boxes
		public static final By DataChkboxMyequipment = By.xpath("//input[@value='I confirm the customer equipment has been checked and has power']"); 
		public static final By DataChkboxBTequipment = By.xpath("//input[@value='I confirm that any BT equipment on site is correctly plugged into a working power supply']"); 
			
		//SQ3
		public static final By DataSQ3 = By.xpath(".//*[@id='lbl21ETHA-31.1']/b");
		public static final By DataAns3 = By.xpath("//select[@id='21ETHA-31.1']"); 
		
		//SQ for MOD C (BT has detected that this is a copper delivered service)
		public static final By DataSQ3_MODC = By.xpath(".//*[@id='lbl21ETHA-5']/b"); 
		public static final By DataAns3_MODC= By.xpath("//select[@id='21ETHA-5']"); 
		
		//SQ4
		public static final By DataSQ4 = By.xpath(".//*[@id='lbl21ETHA-31.2']/b");
		public static final By DataAns4 = By.xpath("//select[@id='21ETHA-31.2']"); 
		
		//SQ5
		public static final By DataSQ5 = By.xpath(".//*[@id='lbl21ETHA-31.7']/b");
		public static final By DataAns5 = By.xpath("//select[@id='21ETHA-31.7']"); 
			
			
		//SQ6
		public static final By DataSQ6 = By.xpath(".//*[@id='lbl21ETHA-39.4']/b");
		public static final By DataAns6 = By.xpath("//textarea[@id='21ETHA-39.4']"); 
		
		//SQ for MOD C(Please select a description of the fault you are experiencing.*)
		public static final By DataSQ4_MODC = By.xpath(".//*[@id='lbl21ETHA-6']/b");
		public static final By DataAns4_MODC= By.xpath("//select[@id='21ETHA-6']"); 
			
		//SQ for MOD C(Please select a low level description of the fault you are experiencing)
			
		public static final By DataSQ5_MODC = By.xpath(".//*[@id='lbl21ETHA-7']/b");
		public static final By DataAns5_MODC= By.xpath("//select[@id='21ETHA-7']"); 
			
		//SQ for MOD C(Please indicate if any alarm LEDs are lit on the network terminating equipment?*)
			
		public static final By DataSQ6_MODC = By.xpath(".//*[@id='lbl21ETHA-17']/b");	
		public static final By DataAns6_MODC= By.xpath("//textarea[@id='21ETHA-17']"); 

		//SQ7
		public static final By DataSQ7 = By.xpath(".//*[@id='lbl21ETHA-92Fname']/b");
		public static final By DataAns7 = By.xpath("//input[@id='21ETHA-92Fname']"); 
		
		//SQ8
		
		public static final By DataSQ8 = By.xpath(".//*[@id='lbl21ETHA-92Lname']/b");
		public static final By DataAns8 = By.xpath("//input[@id='21ETHA-92Lname']");
		
		//SQ9
		public static final By DataSQ9 = By.xpath(".//*[@id='lbl21ETHA-92Lname']/b");
		public static final By DataAns9 = By.xpath("//input[@id='21ETHA-92TelNo']");
		
		//SQ10
		public static final By DataSQ10 = By.xpath(".//*[@id='lbl21ETHA-96_1']/b");
		public static final By DataAns10 = By.xpath("//input[@id='21ETHA-96_1']");
		
		//Last Name 
		public static final By DataSQLastName = By.xpath(".//*[@id='lbl21ETHA-96_2']/b");
		public static final By DataAnsLastName = By.xpath("//input[@id='21ETHA-96_2']");
		
		//SQ11
		public static final By DataSQ11 = By.xpath(".//*[@id='lbl21ETHC-96AA']/b");
		public static final By DataAns11 = By.xpath("//input[@id='21ETHC-96AA']");
		
		//SQ12
		public static final By DataSQ12 = By.xpath(".//*[@id='lbl21ETHA-96_3']/b");
		public static final By DataAns12 = By.xpath("//input[@id='21ETHA-96_3']");
		
		//SQ13
		public static final By DataSQ13 = By.xpath(".//*[@id='lbl21ETHA-96_7']/b");
		public static final By DataAns13 = By.xpath("//input[@id='21ETHA-96_7']");
		
		//SQ14
		public static final By DataSQ14 = By.xpath(".//*[@id='lbl21CN_EMAIL_A']/b");
		public static final By DataAns14 = By.xpath("//input[@id='21CN_EMAIL_A']");
		
		//SQ15
		public static final By DataSQ15 = By.xpath(".//*[@id='lbl21ETHA_Access_Res']/b");
		public static final By DataAns15 = By.xpath("//textarea[@id='21ETHA_Access_Res']");
				
		//SQ16
		public static final By DataSQ16 = By.xpath(".//*[@id='lbl21ETHA_Site_Hazard']/b");
		public static final By DataAns16 = By.xpath("//textarea[@id='21ETHA_Site_Hazard']");
				
		//SQ17
		public static final By DataSQ17 = By.xpath(".//*[@id='lbl21ETHA_24_Access']/b");
		public static final By DataAns17 = By.xpath("//select[@id='21ETHA_24_Access']");
		
		public static final By oneFaultMsg = By.xpath("//td[font[contains(text(),'Please note that you can only raise one fault per asset at any one time.')]]");
		
		
}
