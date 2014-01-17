package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.OpportunityRecord;
import com.sugarcrm.sugar.views.Menu;

/**
 * Opportunities module object which contains data and element associated with the Opportunity module
 * @author mlouis
 */
public class OpportunitiesModule extends StandardModule {
	protected static OpportunitiesModule module;
	public final VoodooControl BASIC_CLIENT_NAME;
	public final VoodooControl BASIC_CLIENT_NAME_SELECT;
	public final VoodooControl BASIC_DECISION_DATE;
	public final VoodooControl BASIC_TAGS;
	public final VoodooControl BASIC_CLIENT_OPPTY_CHBOX;
	public final VoodooControl BASIC_RESTRICTED_OPPTY_CHBOX;
	
	public final VoodooControl ADVANCED_DATE_CREATED_RANGE;
	public final VoodooControl ADVANCED_DATE_CREATED_INPUT;
	public final VoodooControl ADVANCED_DATE_CREATED_SELECT;
	public final VoodooControl ADVANCED_OPPTY_DESCRIPTION;
	public final VoodooControl ADVANCED_ADDITIONAL_TEAM_MEMBER;
	public final VoodooControl ADVANCED_ADDITIONAL_TEAM_MEMBER_SELECT;
	public final VoodooControl ADVANCED_ADDITIONAL_TEAM_MEMBER_CLEAR;
	public final VoodooControl ADVANCED_TOTAL_PROBABILITY;
	public final VoodooControl ADVANCED_OPPTY_NUMBER;
	public final VoodooControl ADVANCED_CLIENT_NAME;
	public final VoodooControl ADVANCED_CLIENT_NAME_SELECT;
	public final VoodooControl ADVANCED_TAGS;
	public final VoodooControl ADVANCED_CLIENT_ID;
	public final VoodooControl ADVANCED_CLIENT_ID_SELECT;
	public final VoodooControl ADVANCED_CLIENT_ID_CLEAR;
	public final VoodooControl ADVANCED_SOURCE;
	public final VoodooControl ADVANCED_PRIMARY_CONTACT;
	public final VoodooControl ADVANCED_PRIMARY_CONTACT_SELECT;
	public final VoodooControl ADVANCED_PRIMARY_CONTACT_CLEAR;
	public final VoodooControl ADVANCED_SALES_STAGE;
	public final VoodooControl ADVANCED_OPPTY_OWNER;
	public final VoodooControl ADVANCED_OPPTY_OWNER_SELECT;
	public final VoodooControl ADVANCED_OPPTY_OWNER_CLEAR;
	public final VoodooControl ADVANCED_DECISION_DATE_RANGE;
	public final VoodooControl ADVANCED_DECISION_DATE_INPUT;
	public final VoodooControl ADVANCED_DECISION_DATE_SELECT;
	public final VoodooControl ADVANCED_FAVORITS_CHBOX;
	public final VoodooControl ADVANCED_CLIENT_OPPTY_CHBOX;
	public final VoodooControl ADVANCED_RESTRICTED_OPPTY_CHBOX;
	
	public final VoodooControl POPUP_CLIENT_NAME;
	public final VoodooControl POPUP_TAGS;
	public final VoodooControl POPUP_CLIENT_OPPTY_CHBOX;
	public final VoodooControl POPUP_RESTRICTED_OPPTY_CHBOX;
	
	public static OpportunitiesModule getInstance() throws Exception {
		if(module == null) 
			module = new OpportunitiesModule();
		return module;
	}

	private OpportunitiesModule() throws Exception {
		moduleNameSingular = "Opportunity";
		moduleNamePlural = "Opportunities";
		recordClassName = OpportunityRecord.class.getName();
		
		// Search forms on list view
		BASIC_CLIENT_NAME = new VoodooControl("input", "id", "account_name_basic_ac");
		BASIC_CLIENT_NAME_SELECT = new VoodooControl("button", "id", "btn_account_name_basic");
		BASIC_DECISION_DATE = new VoodooControl("select", "id", "date_closed_basic_range_choice");
		BASIC_TAGS = new VoodooControl("input", "id", "Opportunities_tags_search");
		BASIC_CLIENT_OPPTY_CHBOX = new VoodooControl("input", "id", "clients_opportunities_only_basic");
		BASIC_RESTRICTED_OPPTY_CHBOX = new VoodooControl("input", "id", "restricted_opportunities_only_basic");
		
		ADVANCED_DATE_CREATED_RANGE = new VoodooControl("select", "id", "date_entered_advanced_range_choice");
		ADVANCED_DATE_CREATED_INPUT = new VoodooControl("input", "id", "range_date_entered_advanced");
		ADVANCED_DATE_CREATED_SELECT = new VoodooControl("span", "id", "date_entered_advanced_trigger");
		ADVANCED_OPPTY_DESCRIPTION = new VoodooControl("input", "id", "description_advanced");
		ADVANCED_ADDITIONAL_TEAM_MEMBER = new VoodooControl("input", "id", "additional_users_name_advanced");
		ADVANCED_ADDITIONAL_TEAM_MEMBER_SELECT = new VoodooControl("button", "id", "btn_additional_users_name_advanced");
		ADVANCED_ADDITIONAL_TEAM_MEMBER_CLEAR = new VoodooControl("button", "id", "btn_clr_additional_users_name_advanced");
		ADVANCED_TOTAL_PROBABILITY = new VoodooControl("select", "name", "probability_advanced[]");
		ADVANCED_OPPTY_NUMBER = new VoodooControl("input", "xpath", "//div[@id='Opportunitiesadvanced_searchSearchForm']//input[@id='name_advanced']");
		ADVANCED_CLIENT_NAME = new VoodooControl("input", "id", "account_name_advanced_ac");
		ADVANCED_CLIENT_NAME_SELECT = new VoodooControl("button", "id", "btn_account_name_advanced");
		ADVANCED_TAGS = new VoodooControl("input", "xpath", "//div[@id='Opportunitiesadvanced_searchSearchForm']//input[@id='Opportunities_tags_search']");
		ADVANCED_CLIENT_ID = new VoodooControl("input", "id", "account_id_advanced");
		ADVANCED_CLIENT_ID_SELECT = new VoodooControl("button", "name", "btn_account_id_advanced");
		ADVANCED_CLIENT_ID_CLEAR = new VoodooControl("button", "name", "btn_clr_account_id_advanced");
		ADVANCED_SOURCE = new VoodooControl("select", "name", "lead_source_advanced[]");
		ADVANCED_PRIMARY_CONTACT = new VoodooControl("input", "id", "pcontact_id_c_advanced");
		ADVANCED_PRIMARY_CONTACT_SELECT = new VoodooControl("button", "name", "btn_pcontact_id_c_advanced");
		ADVANCED_PRIMARY_CONTACT_CLEAR = new VoodooControl("button", "name", "btn_clr_pcontact_id_c_advanced");
		ADVANCED_SALES_STAGE = new VoodooControl("select", "name", "sales_stage_advanced[]");
		ADVANCED_OPPTY_OWNER = new VoodooControl("input", "id", "assigned_user_name_advanced");
		ADVANCED_OPPTY_OWNER_SELECT = new VoodooControl("button", "id", "btn_assigned_user_name_advanced");
		ADVANCED_OPPTY_OWNER_CLEAR = new VoodooControl("button", "id", "btn_clr_assigned_user_name_advanced");
		ADVANCED_DECISION_DATE_RANGE = new VoodooControl("select", "id", "date_closed_advanced_range_choice");
		ADVANCED_DECISION_DATE_INPUT = new VoodooControl("input", "id", "range_date_closed_advanced");
		ADVANCED_DECISION_DATE_SELECT = new VoodooControl("span", "id", "date_closed_advanced_trigger");
		ADVANCED_FAVORITS_CHBOX = new VoodooControl("span", "id", "current_user_only_advanced");
		ADVANCED_CLIENT_OPPTY_CHBOX = new VoodooControl("input", "id", "clients_opportunities_only_advanced");
		ADVANCED_RESTRICTED_OPPTY_CHBOX = new VoodooControl("input", "id", "restricted_opportunities_only_advanced");
		
		POPUP_CLIENT_NAME = new VoodooControl("input", "id", "account_name_advanced_ac");
		POPUP_TAGS = new VoodooControl("input", "id", "Opportunities_tags_search");
		POPUP_CLIENT_OPPTY_CHBOX = new VoodooControl("input", "id", "clients_opportunities_only_advanced");
		POPUP_RESTRICTED_OPPTY_CHBOX = new VoodooControl("input", "id", "restricted_opportunities_only_advanced");
		
		// Load fields
		loadFields();
		
		// Add this line back into the OpportunitiesModuleField.csv after SC-1120 is merged in Sugar7 Core (select2 lib update)
		// relAssignedUserName,a,css,.fld_assigned_user_name.detail a,a,css,.fld_assigned_user_name.edit a,assigned_user_name,Administrator
		
		relatedModulesOne.put("assignedUserName", "User");
		relatedModulesOne.put("accountName", "Account");
		relatedModulesOne.put("campaignName", "Campaign");
		relatedModulesOne.put("teamName", "Team");
		
		// Define the columns on the ListView.
		listView.addHeader("name");
		listView.addHeader("sales_status");
		listView.addHeader("amount");
		listView.addHeader("opportunity_type");
		listView.addHeader("next_step");
		listView.addHeader("lead_source");
		listView.addHeader("date_closed");
		listView.addHeader("date_entered");
		listView.addHeader("account_name");
		listView.addHeader("created_by_name");
		listView.addHeader("assigned_user_name");
		listView.addHeader("modified_by_name");
		
		// Opportunities Module Menu Items
		menu = new Menu(this);
		menu.addControl("createOpportunity", "a", "css", "li[data-module='Opportunities'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_OPPORTUNITY']");
		menu.addControl("viewOpportunities", "a", "css", "li[data-module='Opportunities'] ul[role='menu'] a[data-navbar-menu-item='LNK_OPPORTUNITY_LIST']");
		menu.addControl("viewOpportunityReports", "a", "css", "li[data-module='Opportunities'] ul[role='menu'] a[data-navbar-menu-item='LNK_OPPORTUNITY_REPORTS']");
		menu.addControl("importOpportunities", "a", "css", "li[data-module='Opportunities'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_OPPORTUNITIES']");
				
	}
	
	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init()
	{
		VoodooUtils.voodoo.log.info("Init Opportunities...");
	}
} // OpportunitiesModule