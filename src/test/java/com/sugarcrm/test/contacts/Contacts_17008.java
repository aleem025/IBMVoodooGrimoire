package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_17008 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
		sugar.contacts.api.create();
	}

	/** 
	 * Verify auto duplicate check on office phone while creating a contact 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		// Click create button under Contact list view
		sugar.contacts.listView.create();
		
		// Fill in the last name and existing phone number
		sugar.contacts.createDrawer.getEditField("lastName").set("test1");
		sugar.contacts.createDrawer.getEditField("phoneWork").set("+1 408.454.9600");
		
		// Click Save button
		sugar.contacts.createDrawer.save();
		
		// Verify the duplicate on office phone
		sugar.contacts.createDrawer.getControl("duplicateCount").assertContains("1 duplicates found.", true);
		new VoodooControl("span", "css", "span[data-voodoo-name='first_name']").assertContains("Ran", true);
		new VoodooControl("span", "css", "span[data-voodoo-name='last_name']").assertContains("Zhou", true);
		
		// Click on Cancel button
		sugar.contacts.createDrawer.cancel();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}