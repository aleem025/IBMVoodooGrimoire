package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;

public class Contacts_17463 extends SugarTest {
	ContactRecord myContact;
	
	public void setup() throws Exception {
		sugar.login();
		
		//Create a default contact name
		myContact = (ContactRecord)sugar.contacts.api.create();
	}

	/** Verify if the user selects Related: "All", 
	 *  he gets a stacked view of all related modules in Contact's sub panel
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Navigate to record view
		myContact.navToRecord();
		
		// TODO: Need the supported lib for related dropdown on subpanel. Refer to Bug VOOD-601
		// Verify related dropdown on subpanel
		new VoodooControl("span", "css", "span[data-voodoo-name='filter-module-dropdown']").assertContains("Related", true);
		
		// Click on related button to show module list
		new VoodooControl("span", "css", "span[data-voodoo-name='filter-module-dropdown']").click();
		
		// Verify module lists from related dropdown
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='All']").assertExists(true);
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='Leads']").assertExists(true);
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='Opportunities']").assertExists(true);
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='Cases']").assertExists(true);
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='Bugs']").assertExists(true);
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='Direct Reports']").assertExists(true);
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='Documents']").assertExists(true);
		new VoodooControl("div", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='Quotes']").assertExists(true);

		// Click to close the dropdown menu
		new VoodooControl("all_subpanel", "xpath", "//*[contains(@class,'select2-result-label')]//div[.='All']").click();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}
