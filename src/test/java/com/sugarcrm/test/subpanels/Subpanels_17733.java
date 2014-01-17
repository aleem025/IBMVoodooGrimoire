package com.sugarcrm.test.subpanels;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;

public class Subpanels_17733 extends SugarTest {
	ContactRecord myContact;
	CaseRecord myCase;

	public void setup() throws Exception {
		sugar.login();

		myContact = (ContactRecord) sugar.contacts.api.create();
		sugar.accounts.api.create();

		// TODO : Change this to use the api lib once library supports creating
		// relationships via API.
		myCase = (CaseRecord) sugar.cases.create();
	}

	/**
	 * Verify the supported actions are present in the subpanel list view
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

		// TODO The ExpandAction function to be a seperate public exposed method
		// in the Subpanel class to replace the following line
		new VoodooControl(
				"a",
				"css",
				"div[data-voodoo-name=Cases] .table.table-striped.dataTable tbody tr:nth-of-type(1) .actions.btn-group.pull-right.list a.dropdown-toggle")
				.click();

		// Verify the supported Edit action exists and is Visible
		new VoodooControl(
				"a",
				"css",
				"div[data-voodoo-name=Cases] .table.table-striped.dataTable tbody tr:nth-of-type(1) .actions.btn-group.pull-right.list .dropdown-menu .fld_edit_button.list a")
				.assertVisible(true);
		// Verify the supported Unlink action exists and is Visible
		new VoodooControl(
				"a",
				"css",
				"div[data-voodoo-name=Cases] .table.table-striped.dataTable tbody tr:nth-of-type(1) .actions.btn-group.pull-right.list .dropdown-menu a[data-event='list:unlinkrow:fire']")
				.assertVisible(true);
	}

	public void cleanup() throws Exception {
		sugar.cases.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
