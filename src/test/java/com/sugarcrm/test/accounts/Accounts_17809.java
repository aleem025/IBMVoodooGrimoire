package com.sugarcrm.test.accounts;

import java.util.ArrayList;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.test.SugarTest;

public class Accounts_17809 extends SugarTest {
	protected AccountRecord myAccount;
	protected ContactRecord myContact;
	protected ArrayList<Record> contacts;
	protected VoodooControl container;

	public void setup() throws Exception {
		sugar.login();
		// create account and contacts, and related contacts to account
		myAccount = (AccountRecord) sugar.accounts.api.create();
		String relAcc = myAccount.getRecordIdentifier();
		contacts = sugar.contacts.api.create(testData.get(testName));

		for (Record con : contacts) {
			con.navToRecord();
			sugar.contacts.recordView.edit();
			sugar.contacts.recordView.getEditField("relAccountName").set(relAcc);
			VoodooUtils.pause(1000);
			new VoodooControl("a", "css", "#alerts a.btn-link").click();
			sugar.contacts.recordView.save();
		}

	}

	/**
	 * Verify clearing of search criteria from search field after module
	 * selection - record view
	 * @throws Exception
	 */

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// TODO VOOD-468 need replace these controls after lib file is done
		myAccount.navToRecord();
		container = new VoodooSelect("a", "css", "div.related-filter a");
		container.set("Contacts");
		VoodooUtils.pause(2000);
		VoodooControl searchFilter = new VoodooControl("input", "css", "div.filter-view.search input.search-name");
		String filter1 = contacts.get(0).recordData.get("firstName");
		String filter2 = contacts.get(1).recordData.get("firstName");
		searchFilter.set(filter1);
		VoodooUtils.pause(2000);
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='Contacts']").assertContains(filter1, true);
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='Contacts']").assertContains(filter2, false);

		container.set("Leads");
		VoodooUtils.pause(2000);
		searchFilter.assertContains(contacts.get(0).recordData.get("firstName"), false);
		new VoodooControl("div", "css", "div.main-content").assertContains(filter1, false);
		new VoodooControl("div", "css", "div.main-content").assertContains(filter2, false);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		myAccount.navToRecord();
		container.set("All");
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();

	}
}
