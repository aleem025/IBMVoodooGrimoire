package com.sugarcrm.test.admin;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Admin_20236 extends SugarTest {
	// Common VoodooControl references
	// TODO VOOD-648 - Admin Oauth LIB support: for all VoodooControls
	VoodooControl OAuthFirstRow;

	public void setup() throws Exception {
		// Initialize the common Control references
		OAuthFirstRow = new VoodooControl("a", "css", "tr.oddListRowS1 a");
		sugar.login();
	}

	/**
	 * Verify the Standard OAuth Username & Password Key is read-only and cannot
	 * be edited
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");
		new VoodooControl("a", "id", "oauth").click();
		VoodooUtils.focusFrame("bwc-frame");
		// The standard OAuth key should be the first in the list
		OAuthFirstRow.assertContains("Standard OAuth Username & Password Key",
				true);
		OAuthFirstRow.click();
		// Only the Delete button should Exist
		VoodooUtils.focusFrame("bwc-frame");
		new VoodooControl("a", "id", "delete_button").assertExists(true);
		new VoodooControl("a", "id", "edit_button").assertExists(false);
		new VoodooControl("a", "id", "duplicate_button").assertExists(false);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		VoodooUtils.focusDefault();
		sugar.logout();
	}
}
