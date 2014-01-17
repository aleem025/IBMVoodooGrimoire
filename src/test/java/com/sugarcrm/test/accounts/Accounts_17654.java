package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.DataSource;

public class Accounts_17654 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * open a new account create page after clicking save and create new button
	 * to create a new record
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
		sugar.accounts.createDrawer.saveAndCreateNew();
		new VoodooControl("div", "css", "div.alert.alert-success.alert-block").assertContains(ds.get(0).get("assert"), true);
		sugar.accounts.createDrawer.getEditField("name").assertVisible(true);		
		sugar.accounts.navToListView();
		sugar.alerts.confirmAlert();

		// Verify name on list view.
		sugar.accounts.listView.verifyField(1, "name", ds.get(0).get("name"));

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
