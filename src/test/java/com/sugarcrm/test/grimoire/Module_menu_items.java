package com.sugarcrm.test.grimoire;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;

public class Module_menu_items extends SugarTest {

	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.navbar.selectMenuItem(sugar.accounts, "createAccount");
		new VoodooControl("a", "css", "a[name='save_button']").assertExists(true);
		
		sugar.navbar.selectMenuItem(sugar.accounts, "viewAccounts");
		new VoodooControl("a", "css", "a[name='create_button']").assertExists(true);
		
		sugar.navbar.selectMenuItem(sugar.accounts, "importAccounts");
		VoodooUtils.focusFrame("bwc-frame");
		new VoodooControl("h2", "css", "div.moduleTitle h2").assertContains("Upload Import File", true);
		VoodooUtils.focusDefault();
		
		sugar.navbar.selectMenuItem(sugar.accounts, "viewAccountReports");
		VoodooUtils.focusFrame("bwc-frame");
		new VoodooControl("h2", "css", "div.moduleTitle h2").assertContains("Search", true);
		VoodooUtils.focusDefault();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
