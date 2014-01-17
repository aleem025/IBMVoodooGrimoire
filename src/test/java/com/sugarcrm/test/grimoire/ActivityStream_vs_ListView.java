package com.sugarcrm.test.grimoire;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class ActivityStream_vs_ListView extends SugarTest {
	
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.accounts.navToListView();
		// Go to Activity Stream View
		sugar.accounts.listView.showActivityStream();
		
		// verify activityStream view
		new VoodooControl("button", "css", ".inputactions button").assertExists(true);
		
		// Go to ListView View
		sugar.accounts.listView.showListView();
		
		// verify listView view
		sugar.accounts.listView.toggleSelectAll();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
