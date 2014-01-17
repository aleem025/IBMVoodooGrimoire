package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17141 extends SugarTest {
	
	AccountRecord account1;
	
	public void setup() throws Exception {
		sugar.login();
		account1 = (AccountRecord)sugar.accounts.api.create();
	}

	/**
	 * Display the save/cancel button as soon as the detail view inline edit action is triggered
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		account1.navToRecord();
		
		//Verify Save button does not exist.
		sugar.accounts.recordView.getControl("saveButton").assertVisible(false);

		// Click a pencil edit of a field.
		new VoodooControl ("span", "css", "div.record-cell[data-name='website']").hover();
		new VoodooControl ("a", "css", "a[data-name='website']").click();
		// Just want to make sure the editbox appear.
		sugar.accounts.recordView.getEditField("website").set("abc");	
		
		// VOOD-430 is logged for asserting if element is visible on the page. Update following if it is available.
		// Verify action button is displayed.
		sugar.accounts.recordView.getControl("saveButton").assertAttribute("style", "display: none", false);
		sugar.accounts.recordView.getControl("cancelButton").assertAttribute("style", "display: none", false);
		
		// Verify the edit button parent is not displayed
		new VoodooControl("span", "css", "div.headerpane span.actions.btn-group").assertAttribute("style", "display: none");
		// Verify the above element does contain edit button
		new VoodooControl("span", "css", "div.headerpane span.actions.btn-group span.fld_edit_button").assertAttribute("data-voodoo-name", "edit_button");

		// Wait for loading message to disappear.
		// TODO: Find a better way to do this.
		VoodooUtils.pause(3000);
		sugar.accounts.recordView.cancel();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
