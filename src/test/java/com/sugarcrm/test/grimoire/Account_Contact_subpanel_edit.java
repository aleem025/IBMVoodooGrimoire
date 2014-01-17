package com.sugarcrm.test.grimoire;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;

public class Account_Contact_subpanel_edit extends SugarTest {
	AccountRecord myAccount;
	ContactRecord myContact;
	
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
		
		FieldSet account = new FieldSet();
		account.put("relAccountName", myAccount.getRecordIdentifier());
		
		myContact = (ContactRecord)sugar.contacts.create(account);
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myAccount.navToRecord();
		VoodooUtils.pause(1500);
		Subpanel contactsSubpanel = sugar.accounts.recordView.subpanels.get("Contacts");
		contactsSubpanel.toggleSubpanel();
		VoodooUtils.pause(1000);
		contactsSubpanel.editRecord(1);
		VoodooUtils.pause(1000);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}
