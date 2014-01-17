package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.DataSource;

public class Accounts_17652 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * should show account detail page after clicking save and view button to
	 * create a new record
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
		sugar.accounts.createDrawer.saveAndView();
		sugar.alerts.closeAlert();
		VoodooUtils.pause(2000);
		sugar.accounts.recordView.getDetailField("name").assertContains(ds.get(0).get("name"), true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
