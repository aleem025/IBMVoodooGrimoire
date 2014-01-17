package com.sugarcrm.test.ListView;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;

public class ListView_17026 extends SugarTest {
	AccountRecord myAccount;

	public void setup() throws Exception {
		myAccount = (AccountRecord)sugar.accounts.api.create();
		sugar.login();		
	}
			
	/**
	 *  Verify close action on preview panel.
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.accounts.navToListView();
		sugar.accounts.listView.previewRecord(1);
		VoodooUtils.pause(5000);
	
		// Close the preview pane by click the "X" button on upper right corner of preview panel.
		sugar.accounts.previewPane.closePreview();
		
		// Verify the Account name does not exist in RHS panel.
		sugar.accounts.previewPane.getPreviewPaneField("name").assertExists(false);
		
		// Verify the "My Dashboard" exist in RHS panel.
		// TODO: JIRA story VOOD-621 about the ability to verify "My Dashboard" exist in RHS panel under Accounts list view.
		new VoodooControl("a", "css", ".fld_name.detail a").assertEquals("My Dashboard", true);  
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}