package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Accounts_17729 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();		
		sugar.accounts.api.create();		
	}

	/**
	 * Verify the inline editing on email address can be saved in the list view 
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds = testData.get(testName);	

		// TODO VOOD-487 need replace these controls after lib file is done

		sugar.accounts.navToListView();
		sugar.accounts.listView.editRecord(1);
		new VoodooControl("input", "css", "tr.single.tr-inline-edit input[name='email']").set(ds.get(0).get("emailAddress"));
		sugar.accounts.listView.saveRecord(1);
		VoodooUtils.pause(2000);
		
		// TODO VOOD-488 listView.verifyField method doesn't work
		new VoodooControl("span", "css", "div[data-voodoo-name='recordlist'] tbody tr:nth-of-type(1) span[data-voodoo-name='email']" ).assertContains(ds.get(0).get("emailAddress"), true);
		//sugar.accounts.listView.verifyField(1, "email", ds.get(0).get("emailAddress"));		

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();		
		sugar.logout();
	}
}