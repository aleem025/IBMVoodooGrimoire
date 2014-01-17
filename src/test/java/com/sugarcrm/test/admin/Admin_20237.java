package com.sugarcrm.test.admin;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Admin_20237 extends SugarTest {
	// Common VoodooControl references
	// TODO VOOD-648 - Admin Oauth LIB support: for all VoodooControls
	VoodooControl OAuthFirstRow;
	VoodooControl sugarPortal;
	VoodooControl configurePortal;
	VoodooControl enablePortal;
	VoodooControl savePortal;

	public void setup() throws Exception {
		// Initialize the common Control references
		OAuthFirstRow = new VoodooControl("a", "css", "tr.oddListRowS1 a");
		sugarPortal = new VoodooControl("a", "id", "sugarportal");
		configurePortal = new VoodooControl("a", "css", ".studiolink");
		enablePortal = new VoodooControl("input", "id", "appStatus");
		savePortal = new VoodooControl("input", "id", "gobutton");

		sugar.login();
		// Enable Sugar Portal
		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");
		sugarPortal.click();
		configurePortal.click();
		enablePortal.click();
		new VoodooControl("input", "id", "gobutton").click();
		VoodooUtils.focusDefault();
	}

	/**
	 * Verify the support portal key is read-only and cannot be edited in OAuth
	 * Keys
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
		// When Portal has been enabled the Portal OAuth key will be the first
		// in the list
		OAuthFirstRow.assertContains("OAuth Support Portal Key", true);
		OAuthFirstRow.click();
		// Only the Delete button should Exist
		VoodooUtils.focusFrame("bwc-frame");
		new VoodooControl("a", "id", "delete_button").assertExists(true);
		new VoodooControl("a", "id", "edit_button").assertExists(false);
		new VoodooControl("a", "id", "duplicate_button").assertExists(false);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		// Disable Sugar Portal
		VoodooUtils.focusDefault();
		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");
		sugarPortal.click();
		configurePortal.click();
		enablePortal.click();
		savePortal.click();
		VoodooUtils.focusDefault();
		sugar.logout();
	}
}
