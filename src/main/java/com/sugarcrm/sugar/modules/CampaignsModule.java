package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CampaignRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Campaigns module, such as field
 * data.
 * 
 * @author David Safar <dsafar@sugarcrm.com> 
 */
public class CampaignsModule extends BWCModule {
	protected static CampaignsModule module;
	
	public static CampaignsModule getInstance() throws Exception {
		if(module == null) 
			module = new CampaignsModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * @throws Exception
	 */
	private CampaignsModule() throws Exception {
		moduleNameSingular = "Campaign";
		moduleNamePlural = "Campaigns";
		recordClassName = CampaignRecord.class.getName();
		
		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("campaign");
		listView.addHeader("status");
		listView.addHeader("type");
		listView.addHeader("end_date");
		listView.addHeader("user");
		listView.addHeader("date_created");

		// Relate Widget access
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("parentName", "Accounts");
		relatedModulesOne.put("teamName", "Teams");
	
		// Campaigns Subpanel
		subpanel = new Subpanel(this);
		
		// Campaigns Module Menu items
		menu = new Menu(this);
		// This is a dup of Classic, needed for standard code to work.
		menu.addControl("createCampaign", "a", "css", "[data-navbar-menu-item='LNK_NEW_CAMPAIGN']");
		menu.addControl("createCampaignWizard", "a", "css", "[data-navbar-menu-item='LNL_NEW_CAMPAIGN_WIZARD']");
		menu.addControl("createCampaignClassic", "a", "css", "[data-navbar-menu-item='LNK_NEW_CAMPAIGN']");
		menu.addControl("viewCampaigns", "a", "css", "[data-navbar-menu-item='LNK_CAMPAIGN_LIST']");
		menu.addControl("viewNewsletters", "a", "css", "[data-navbar-menu-item='LBL_NEWSLETTERS']");
		menu.addControl("createEmailTemplate", "a", "css", "[data-navbar-menu-item='LNK_NEW_EMAIL_TEMPLATE']");
		menu.addControl("viewEmailTemplates", "a", "css", "[data-navbar-menu-item='LNK_EMAIL_TEMPLATE_LIST']");
		menu.addControl("setUpEmail", "a", "css", "[data-navbar-menu-item='LBL_EMAIL_SETUP_WIZARD']");
		menu.addControl("viewDiagnostics", "a", "css", "[data-navbar-menu-item='LBL_DIAGNOSTIC_WIZARD']");
		menu.addControl("createLeadForm", "a", "css", "[data-navbar-menu-item='LBL_WEB_TO_LEAD']");
	}

	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Campaigns...");

		// Related Subpanels
		relatedModulesMany.put("campaign_accounts", sugar.accounts);
		relatedModulesMany.put("campaign_contacts", sugar.contacts);
		relatedModulesMany.put("campaign_leads", sugar.leads);
		relatedModulesMany.put("campaign_opportunities", sugar.opportunities);
		
		// Add Subpanels
		detailView.addSubpanels();
		
		// Account Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();		
	}
} // AccountsModule
