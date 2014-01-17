package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17053 extends SugarTest {	
	AccountRecord myAccount;
		
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
	}

	/**
	 * Date Created/Modified group field display format on preview panel
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.accounts.navToListView();
		sugar.accounts.listView.previewRecord(1);
		sugar.accounts.previewPane.showMore();
		
		// TODO VOOD-597
		new VoodooControl ("span", "css", "div[name='date_entered_by'] span.fld_date_entered.preview").assertExists(true);
		new VoodooControl ("span", "css", "div[name='date_entered_by'] span.fld_created_by_name.preview").assertExists(true);
			
		new VoodooControl ("span", "css", "div[name='date_modified_by'] span.fld_date_modified.preview").assertExists(true);
		new VoodooControl ("span", "css", "div[name='date_modified_by'] span.fld_modified_by_name.preview").assertExists(true);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
