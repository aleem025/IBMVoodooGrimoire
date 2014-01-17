package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_20252 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * Verify Navigation and Cancel from menu Quick Create Contacts
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		// TODO Replace VoodooControls with LIB calls when VOOD-588 support for
		// Quick create menu is implemented
		new VoodooControl("li", "id", "createList").click();
		new VoodooControl("a", "css", "a[data-module='Contacts']").click();
		sugar.contacts.createDrawer.getEditField("lastName")
				.assertVisible(true);
		sugar.contacts.createDrawer.cancel();
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}