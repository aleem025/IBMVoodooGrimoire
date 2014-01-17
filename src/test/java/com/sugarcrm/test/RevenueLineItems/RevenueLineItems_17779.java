package com.sugarcrm.test.RevenueLineItems;
import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.OpportunityRecord;
import com.sugarcrm.sugar.views.Alerts;

import com.sugarcrm.test.SugarTest;

public class RevenueLineItems_17779 extends SugarTest {
	
	AccountRecord myAccount;
	OpportunityRecord myOpp;
	
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
		// Used UI because of lack of relationship setting functionality in Opportunity Create API
		// TODO: Update API creates to be able to establish relationships 
		myOpp = (OpportunityRecord)sugar.opportunities.create();		
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.revLineItems.navToListView();
		sugar.revLineItems.listView.create();
		sugar.revLineItems.createDrawer.getEditField("relOpportunityName").set(myOpp.getRecordIdentifier());
		
		new VoodooControl("a","css",".fld_account_name a").assertContains(myAccount.getRecordIdentifier(), true);
		
		sugar.revLineItems.createDrawer.getEditField("expectedCloseDate").set( sugar.revLineItems.defaultData.get("expectedCloseDate"));
		sugar.revLineItems.createDrawer.getEditField("name").set( sugar.revLineItems.defaultData.get("name"));
		sugar.revLineItems.createDrawer.getEditField("likelyCase").set( sugar.revLineItems.defaultData.get("likelyCase"));
		VoodooUtils.pause(1000);
		sugar.revLineItems.createDrawer.save();
		
		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.revLineItems.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.opportunities.api.deleteAll();
		
		sugar.logout();
	}
}