package com.sugarcrm.test.subpanels;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;

public class Subpanels_17736 extends SugarTest {
	FieldSet casesRecord;
	ContactRecord myContact;
	CaseRecord myCase;

	public void setup() throws Exception {
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

		new VoodooControl(
				"div",
				"css",
				"div[data-voodoo-name=Cases] .table.table-striped.dataTable tbody tr:nth-of-type(1) .fld_name.list div")
				.assertEquals(myCase.recordData.get("name"), true);
	}

	/**
	 * Verify the subpanel row level action - Unlink and confirm
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myContact.navToRecord();
		Subpanel casesSubpanel = sugar.contacts.recordView.subpanels
				.get("Cases");

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		sugar.contacts.recordView.showDataView();

		casesSubpanel.unlinkRecord(1);

		// TODO This line is to be replaced when a general class exists for the
		// Confirm Delete pop-up
		sugar.cases.listView.confirmDelete();

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		// Expand the Cases Sub Panel to Verify no records are currently linked
		new VoodooControl("a", "css",
				"div[data-voodoo-name=Cases] .btn.btn-invisible").click();
		new VoodooControl("div", "css",
				"div[data-voodoo-name=Cases] .block-footer").assertContains(
				"No records were found at this time", true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.cases.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
