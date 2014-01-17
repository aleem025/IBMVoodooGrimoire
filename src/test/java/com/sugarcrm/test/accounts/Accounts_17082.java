package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17082 extends SugarTest {
    AccountRecord myAccount;

    public void setup() throws Exception {
	sugar.login();
	myAccount = (AccountRecord) sugar.accounts.api.create();
    }

    // Verify user can toggle the activity stream and subpanels on the record view
    @Test
    public void execute() throws Exception {
	VoodooUtils.voodoo.log.info("Running " + testName + "...");

	myAccount.navToRecord();
	// default is data view.

	// Verify the Related drop down
	VoodooControl related_dropdown = new VoodooControl("span", "css", "span[data-voodoo-name='filter-module-dropdown']");
	related_dropdown.assertContains("Related", true);

	// Verify first subpanel
	new VoodooControl("div", "css", "div.filtered.layout_Accounts").assertContains("Member Organizations", true);

	sugar.accounts.recordView.showActivityStream();
	// Verify the word "Module" in filter
	new VoodooControl("span", "css", "span[data-voodoo-name='filter-module-dropdown']").assertContains("Module", true);

	// Verify Submit button is visible
	new VoodooControl("button", "css", "div.inputactions button").assertVisible(true);

	// Make sure you can click data view again.
	sugar.accounts.recordView.showDataView();
	related_dropdown.assertContains("Related", true);

	VoodooUtils.voodoo.log.info(testName + " complete.");
    }

    public void cleanup() throws Exception {
	sugar.accounts.api.deleteAll();
	sugar.logout();
    }
}