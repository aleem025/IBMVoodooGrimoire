package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Accounts_20251 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * Verify Navigation and Cancel from menu Quick Create Accounts
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		// TODO Replace VoodooControls with LIB calls when VOOD-588 support for
		// Quick create menu is implemented
		new VoodooControl("li", "id", "createList").click();
		new VoodooControl("a", "css", "a[data-module='Accounts']").click();
		sugar.accounts.createDrawer.getEditField("name").assertVisible(true);
		sugar.accounts.createDrawer.cancel();
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}