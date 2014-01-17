package com.sugarcrm.test.ListView;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class ListView_16939 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();

		// Create 41 accounts (test data)
		DataSource toCreate = new DataSource();
		FieldSet toAdd;
		for(int i = 1; i < 42; i++) {
			toAdd = new FieldSet();
			toAdd.put("name", "Account " + i);
			toCreate.add(toAdd);
		}
		sugar.accounts.api.create(toCreate);
	}

	/**
	 * Verify link to "Clear selections"
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Go to list view and click "select all" checkbox.
		sugar.accounts.navToListView();
		sugar.accounts.listView.toggleSelectAll();
		
		// Verify the message "You have selected all 20 records in this view. Select all records in the result set." appears.
		sugar.accounts.listView.getControl("selectedRecordsAlert").assertContains("You have selected all 20 records in this view.", true);
		sugar.accounts.listView.getControl("selectedRecordsAlert").assertContains("Select all records", true);
		sugar.accounts.listView.getControl("selectedRecordsAlert").assertContains("in the result set.", true);
		
		// Click on Select all records link and verify Clear selection link exist.
		new VoodooControl ("a", "css", "div[data-voodoo-name='recordlist'] tr.alert.alert-warning td div a").click();
		VoodooUtils.pause(5000);
		sugar.accounts.listView.getControl("selectedRecordsAlert").assertContains("You have selected all 41 records in this result set.", true);
		sugar.accounts.listView.getControl("selectedRecordsAlert").assertContains("Clear selections", true);
		
		// Verify all records are selected on list view and have check mark next to them.
		// TODO: JIRA story VOOD-436 about the ability to assert the checked/unchecked state of a checkbox.
		
		// Click on Clear selections link.
		new VoodooControl ("a", "css", "div[data-voodoo-name='recordlist'] tr.alert.alert-warning td div a").click();
		VoodooUtils.pause(5000);
		
	    // Verify all the records no longer selected.
		// TODO: JIRA story VOOD-436 about the ability to assert the checked/unchecked state of a checkbox.
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}