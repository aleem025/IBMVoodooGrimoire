package com.sugarcrm.test.accounts;

import java.util.ArrayList;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.DataSource;

public class Accounts_17172 extends SugarTest {
	protected ArrayList<Record> myAccounts;
	DataSource ds = testData.get(testName);
	
	public void setup() throws Exception {
		sugar.login();
		
		// create accounts
		myAccounts = sugar.accounts.api.create(ds);			
	}

	/**
	 * Verify display the recently viewed record in the mega menu
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		for(Record createdRecord : myAccounts) {	
			createdRecord.navToRecord();
		}
		sugar.accounts.navToListView();
		sugar.navbar.clickModuleDropdown(sugar.accounts);
		//TODO VOOD-508 need lib support for Recently Viewed container in the navbar
		VoodooControl recentList = new VoodooControl("div", "css" ,"div.dropdown-menu.scroll div.recentContainer");
		if (ds.size() > 3) {
			for(int i=ds.size()-1; i>=ds.size()-3 && i>=0; i--){
				recentList.assertContains(myAccounts.get(i).getRecordIdentifier(), true);
			}
			for(int i=ds.size()-4; i>=0; i--) {
				recentList.assertContains(myAccounts.get(i).getRecordIdentifier(), false);
			}	
		} else {
			for(int i=ds.size()-1; i>=0; i--) {
				recentList.assertContains(myAccounts.get(i).getRecordIdentifier(), true);
			}
		}			

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}
	
	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}