package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17605 extends SugarTest {
	AccountRecord myAccount;

	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord) sugar.accounts.api.create();
	}

	/**
	 * Verify not see a success saved message while clicking on the favorite icon on record view
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		myAccount.navToRecord();
		//TODO VOOD-509 need lib support for the favorite icon on the RecordView
		new VoodooControl("i", "css", "div.headerpane .icon-favorite").click();
		new VoodooControl("div", "css", "div.alert.alert-success.alert-block").assertExists(false);	
		new VoodooControl("div", "css", "div#alerts").assertElementContains("Success", false);	
		new VoodooControl("i", "css", "div.headerpane .icon-favorite.active").assertExists(true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}