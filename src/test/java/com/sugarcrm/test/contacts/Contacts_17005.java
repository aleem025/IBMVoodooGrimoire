package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_17005 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
		
		//Create a default contact name
		sugar.contacts.api.create();
	}

	/** Verify auto duplicate check on both first name and last name while creating a contact 
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Go to contacts list view
		sugar.contacts.navToListView();
		
		// Click create button 
		sugar.contacts.listView.create();
		
		// Fill in start letter of first name and last name of default data
		sugar.contacts.createDrawer.getEditField("firstName").set("R");
		sugar.contacts.createDrawer.getEditField("lastName").set("Z");
		
		// Click save button
		sugar.contacts.createDrawer.save();
		
		// Verify the duplicate
		sugar.contacts.createDrawer.getControl("duplicateCount").assertContains("1 duplicates found.", true);
		sugar.contacts.createDrawer.getControl("duplicateHeaderRow").assertExists(true);
		
		// TODO: refer to VOOD-566 to support field access in duplicate check
		new VoodooControl("span", "css", "span[data-voodoo-name='first_name']").assertContains("Ran", true);
		new VoodooControl("span", "css", "span[data-voodoo-name='last_name']").assertContains("Zhou", true);
		sugar.contacts.createDrawer.save();
	    
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}

