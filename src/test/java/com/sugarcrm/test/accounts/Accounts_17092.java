package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17092 extends SugarTest {
	AccountRecord myAccount;
	ContactRecord myContact;

	public void setup() throws Exception {
		sugar.login();
		// create account and contacts, and related contacts to account
		myAccount = (AccountRecord) sugar.accounts.api.create();
		myContact = (ContactRecord) sugar.contacts.api.create();
		myContact.navToRecord();
		sugar.contacts.recordView.edit();
		sugar.contacts.recordView.getEditField("relAccountName").set(myAccount.getRecordIdentifier());
		VoodooUtils.pause(1500);
		sugar.alerts.confirmAlert();
		sugar.contacts.recordView.save();
	}

	/**
	 * Expand/collapse a specific subpanel
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myAccount.navToRecord();
		VoodooUtils.pause(2000);

		// TODO VOOD-477
		VoodooControl conSub = new VoodooControl("div", "css",
				"div.filtered.tabbable.tabs-left.layout_Contacts div[data-voodoo-name='subpanel-for-accounts']");
		conSub.assertVisible(true);
		conSub.assertContains(myContact.getRecordIdentifier(), true);
		sugar.contacts.subpanel.toggleSubpanel();
		conSub.assertVisible(false);
		sugar.contacts.subpanel.toggleSubpanel();
		conSub.assertVisible(true);
		conSub.assertContains(myContact.getRecordIdentifier(), true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}
