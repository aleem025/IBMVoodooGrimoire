package com.sugarcrm.test.subpanels;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Subpanels_17560 extends SugarTest {
	ContactRecord myContact;
	CaseRecord myCase;
	// Common VoodooControl and VoodooSelect references
	VoodooSelect filterModuleDropdown;
	VoodooSelect filterFilterDropdown;
	VoodooControl choiceRelated;
	VoodooControl caseRow;

	public void setup() throws Exception {
		// Initialize the common Control references
		filterModuleDropdown = new VoodooSelect("a", "css",
				"div.related-filter a");

		filterFilterDropdown = new VoodooSelect("a", "css",
				"span[data-voodoo-name='filter-filter-dropdown'] div a");

		choiceRelated = new VoodooControl("div", "css",
				"span[data-voodoo-name=filter-module-dropdown] div.choice-related");

		caseRow = new VoodooControl("div", "css",
				"div[data-voodoo-name=Cases] table tbody tr:nth-of-type(1) .fld_name.list div");

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
	 * Verify record view sub panel - ability to dynamically filter related
	 * records data
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myContact.navToRecord();

		// Select the Cases module
		filterModuleDropdown.set("Cases");

		// Pause until screen is rendered
		VoodooUtils.pause(2000);
		choiceRelated.assertEquals("Cases", true);

		// Filter for a Case that exists
		new VoodooControl("input", "css",
				"input[data-voodoo-name=filter-quicksearch]")
				.set(myCase.recordData.get("name"));
		// Pause until screen is rendered
		VoodooUtils.pause(2000);
		caseRow.assertEquals(myCase.recordData.get("name"), true);
		// Verify the filter has changed to include All Cases
		filterFilterDropdown.set("All Cases");

		// Change the Related to a different module - Leads
		filterModuleDropdown.set("Leads");
		// Pause until screen is rendered
		VoodooUtils.pause(2000);
		choiceRelated.assertEquals("Leads", true);
		// Verify the filter has changed to include All Leads
		filterFilterDropdown.set("All Leads");

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.cases.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
