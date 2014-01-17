package com.sugarcrm.test.subpanels;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.FieldSet;

public class Subpanels_17781 extends SugarTest {
	FieldSet casesRecord;
	ContactRecord myContact;
	CaseRecord myCase;
	// Common VoodooControl references
	VoodooControl caseRow;
	VoodooControl filterQuickSearch;

	public void setup() throws Exception {
		// Initialize the common Control references
		caseRow = new VoodooControl("div", "css",
				"div[data-voodoo-name=Cases] table tbody tr:nth-of-type(1) .fld_name.list div");
		filterQuickSearch = new VoodooControl("input", "css",
				"input[data-voodoo-name=filter-quicksearch]");

		casesRecord = testData.get("Subpanels_17781").get(0);

		sugar.login();

		myContact = (ContactRecord) sugar.contacts.api.create();
		sugar.accounts.api.create();

		// For the set up a Case is related to a Contact via the Subpanel
		// TODO : Change this to use the api lib once library supports creating
		// relationships via API.
		myCase = (CaseRecord) sugar.cases.create();

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		myContact.navToRecord();
		Subpanel casesSubpanel = sugar.contacts.recordView.subpanels
				.get("Cases");

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		sugar.contacts.recordView.showDataView();

		casesSubpanel.linkExistingRecord();

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		// Select the first Case from the list, should be the only one created
		new VoodooControl("input", "css", ".fld_Cases_select.list input")
				.click();

		// Need to pause for the Search Select screen to close and focus
		// returned to the original screen
		VoodooUtils.pause(1000);

		caseRow.assertEquals(myCase.recordData.get("name"), true);
	}

	/**
	 * Verify the dynamic filter related records in subpanel
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myContact.navToRecord();

		sugar.contacts.recordView.showDataView();

		new VoodooSelect(
				"a",
				"css",
				"div[data-voodoo-name=Contacts] span[data-voodoo-name=filter-module-dropdown] a.select2-choice")
				.set("Cases");

		// Pause until screen is rendered
		VoodooUtils.pause(2000);

		// Filter for a Case that doesn't exist to clear the current list
		filterQuickSearch.set(casesRecord.get("name"));
		// Pause until screen is rendered
		VoodooUtils.pause(2000);
		caseRow.assertExists(false);

		// Now filter for a Case that exists
		filterQuickSearch.set(myCase.recordData.get("name"));
		// Pause until screen is rendered
		VoodooUtils.pause(2000);
		caseRow.assertEquals(myCase.recordData.get("name"), true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.cases.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
