package com.sugarcrm.test.ListView;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class ListView_16940 extends SugarTest {
	
	DataSource accounts;
	
	@Override
	public void setup() throws Exception {
		accounts = testData.get(testName);

		sugar.login();
		sugar.accounts.api.create(accounts);
	}

	/**
	 *  Verify "Select all records" message/link does not appear when only a few records are selected.
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Go to Accounts list view and select 3 records.
		sugar.accounts.navToListView();
		sugar.accounts.listView.checkRecord(1);
		sugar.accounts.listView.checkRecord(2);
		sugar.accounts.listView.checkRecord(3);
		
		// Verify the message "Select all records in the result set." does not appears.
		sugar.accounts.listView.getControl("selectedRecordsAlertRow").assertVisible(false);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}
	
	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}