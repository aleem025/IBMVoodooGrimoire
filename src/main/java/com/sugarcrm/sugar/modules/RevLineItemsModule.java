package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.SugarField;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.RevLineItemRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Revenue Line Item module, such as field
 * data.
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com> 
 */
public class RevLineItemsModule extends StandardModule {
	protected static RevLineItemsModule module;
	public final VoodooControl OFFERING_LIST_RADIO;
	public final VoodooControl SOLUTION_CATALOG_RADIO;
	public final VoodooControl OFFERING_SEARCH;
	public final VoodooControl OFFERING_L10;
	public final VoodooControl SUB_BRAND;
	public final VoodooControl BRAND_CODE;
	public final VoodooControl PRODUCT_INFORMATION;
	public final VoodooControl MACHINE_TYPE;
	public final VoodooControl COMPETITORS;
	public final VoodooControl SOLUTION_NAME;
	
	public final VoodooControl OWNER;
	public final VoodooControl OWNER_SELECT;
	public final VoodooControl OWNER_CLEAR;
	public final VoodooControl ROADMAP_STATUS;
	public final VoodooControl PROBABILITY;
	
	public final VoodooControl COTACT_TYPE_TRANSACTIONAL;
	public final VoodooControl COTACT_TYPE_SIGNINGS;
	public final VoodooControl REVENUE_GREEN;
	public final VoodooControl REVENUE_BLUE;
	public final VoodooControl AMOUNT;
	
	public final VoodooControl BILL_DATE;
	public final VoodooControl BILL_DATE_SELECT;
	
	public final VoodooControl FULLFILLMENT_TYPE;
	public final VoodooControl CONTRACT_TYPE;
	public final VoodooControl BILLING_TYPE;
	public final VoodooControl TOTAL_CONTRACT_VALUE;
	public final VoodooControl BOOKABLE_AMOUNT;
	public final VoodooControl DURATION;
	public final VoodooControl CLOSE_DATE;
	public final VoodooControl CLOSE_DATE_SELECT;
	public final VoodooControl REVENUE_MONTH1;
	public final VoodooControl REVENUE_MONTH2;
	public final VoodooControl REVENUE_MONTH3;
	public final VoodooControl REVENUE_TOTAL;
	
	public final VoodooControl BOOKING_TYPE;
	public final VoodooControl BOOKING_DETAIL;
	
	public static RevLineItemsModule getInstance() throws Exception {
		if(module == null) 
			module = new RevLineItemsModule();
		return module;
	}

	private RevLineItemsModule() throws Exception {
		moduleNameSingular = "RevenueLineItem";
		moduleNamePlural = "RevenueLineItems";
		recordClassName = RevLineItemRecord.class.getName();

		loadFields();
		
		// Populate default data.
		for(String currentFieldName : fields.keySet()) {
			SugarField currentField = fields.get(currentFieldName);
			String defaultValue = currentField.get("default_value");

			if(defaultValue != null && !(defaultValue.isEmpty()))
				defaultData.put(currentFieldName, defaultValue);
		}
		
		//Define controls from RLI edit view
		OFFERING_LIST_RADIO = new VoodooControl("input", "xpath", "//*[@id='search_type_radio' and @value='product']");
		SOLUTION_CATALOG_RADIO = new VoodooControl("input", "xpath", "//*[@id='search_type_radio' and @value='sei']");
		OFFERING_SEARCH = new VoodooControl("input", "id", "level_search-input");
		OFFERING_L10 = new VoodooControl("input", "id", "level10-input");
		SUB_BRAND = new VoodooControl("input", "id", "level15-input");
		BRAND_CODE = new VoodooControl("input", "id", "level20-input");
		PRODUCT_INFORMATION = new VoodooControl("input", "id", "level30-input");
		MACHINE_TYPE = new VoodooControl("input", "id", "level40-input");
		COMPETITORS = new VoodooControl("input", "id", "competitor_ac");	
		SOLUTION_NAME = new VoodooControl("input", "id", "solution_id-input");
		
		OWNER = new VoodooControl("input", "id", "assigned_user_name");
		OWNER_SELECT = new VoodooControl("button", "id", "btn_assigned_user_name");
		OWNER_CLEAR = new VoodooControl("button", "id", "btn_clr_assigned_user_name");
		ROADMAP_STATUS = new VoodooControl("select", "id", "roadmap_status");
		PROBABILITY = new VoodooControl("select", "id", "probability");
		
		COTACT_TYPE_TRANSACTIONAL = new VoodooControl("input", "xpath", "//*[@id='revenue_type' and @value='Transactional']");
		COTACT_TYPE_SIGNINGS = new VoodooControl("input", "xpath", "//*[@id='revenue_type' and @value='Signings']");
		REVENUE_GREEN = new VoodooControl("input", "xpath", "//*[@id='green_blue_revenue' and @value='Green']");
		REVENUE_BLUE = new VoodooControl("input", "xpath", "//*[@id='green_blue_revenue' and @value='Blue']");
		
		AMOUNT = new VoodooControl("input", "id", "revenue_amount");
		BILL_DATE = new VoodooControl("input", "id", "fcast_date_tran");
		BILL_DATE_SELECT = new VoodooControl("span", "id", "fcast_date_tran_trigger");
		
		FULLFILLMENT_TYPE = new VoodooControl("select", "id", "stg_fulfill_type");
		CONTRACT_TYPE = new VoodooControl("select", "id", "srv_work_type");
		BILLING_TYPE = new VoodooControl("select", "id", "srv_billing_type");
		TOTAL_CONTRACT_VALUE = new VoodooControl("input", "id", "revenue_amount");
		BOOKABLE_AMOUNT = new VoodooControl("input", "id", "bookable_value");
		DURATION = new VoodooControl("input", "id", "duration");
		CLOSE_DATE = new VoodooControl("input", "id", "fcast_date_sign");
		CLOSE_DATE_SELECT = new VoodooControl("span", "id", "fcast_date_sign_trigger");
		REVENUE_MONTH1 = new VoodooControl("input", "id", "srv_inqtr_month1");
		REVENUE_MONTH2 = new VoodooControl("input", "id", "srv_inqtr_month2");
		REVENUE_MONTH3 = new VoodooControl("input", "id", "srv_inqtr_month3");
		REVENUE_TOTAL = new VoodooControl("span", "id", "srv_inqtr_total_span");
		
		BOOKING_TYPE = new VoodooControl("select", "id", "swg_book_new");
		BOOKING_DETAIL = new VoodooControl("select", "id", "swg_tran_det");
		
		// Define the columns on the ListView.
		listView.addHeader("name");
		listView.addHeader("opportunity_name");
		listView.addHeader("account_name");
		listView.addHeader("sales_stage");
		listView.addHeader("probability");
		listView.addHeader("date_closed");
		listView.addHeader("commit_stage");
		listView.addHeader("product_template_name");
		listView.addHeader("quantity");
		listView.addHeader("likely_case");
		listView.addHeader("best_case");
		listView.addHeader("sales_status");
		listView.addHeader("assigned_user_name");
		
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("teamName", "Teams");
		relatedModulesOne.put("opportunityName", "Opportunities");
		relatedModulesOne.put("salesStage", "Sales Stage");

		// RevLineItem Subpanel
		subpanel = new Subpanel(this);
		
		// RevLineItem Menu Items
		menu = new Menu(this);
		menu.addControl("createRevenueLineItem", "a", "css", "li[data-module='RevenueLineItems'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_PRODUCT']");
		menu.addControl("viewRevenueLineItems", "a", "css", "li[data-module='RevenueLineItems'] ul[role='menu'] a[data-navbar-menu-item='LNK_PRODUCT_LIST']");
		menu.addControl("importRevenueLineItems", "a", "css", "li[data-module='RevenueLineItems'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_RLI']");
	}
	
	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Revenue Line Items...");

		// Related Subpanels
		relatedModulesMany.put("contact_products", sugar.contacts);
		
		// Add Subpanels
		recordView.addSubpanels();
		
		// Account Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();
	}
} // RevLineItemModule