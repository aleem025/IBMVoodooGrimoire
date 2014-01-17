package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17014 extends SugarTest {
	AccountRecord myAccount;

	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord) sugar.accounts.api.create();
	}

	/**
	 * Verify auto duplicate check on account name while creating a new account
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds = testData.get(testName);
		sugar.accounts.navToListView();
		sugar.accounts.listView.create();
		sugar.accounts.createDrawer.getEditField("name").set(ds.get(0).get("name"));
		sugar.accounts.createDrawer.save();
		VoodooUtils.pause(1000);
		sugar.accounts.createDrawer.getControl("duplicateCount").assertVisible(true);

		// TODO VOOD-513 need lib support for duplicate check panel
		new VoodooControl("div", "css", "div[data-voodoo-name='dupecheck-list-edit']").assertContains(
				myAccount.getRecordIdentifier(), true);
		sugar.accounts.createDrawer.cancel();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
