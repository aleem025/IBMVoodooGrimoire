package com.sugarcrm.test.accounts;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Accounts_17651 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * Verify the user is taken back to the list view after saving a new Account
	 * record
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.navbar.selectMenuItem(sugar.accounts, "createAccount");
		sugar.accounts.createDrawer.getEditField("name").set(testName);
		sugar.accounts.createDrawer.save();
		sugar.accounts.listView.verifyField(1, "name", testName);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}