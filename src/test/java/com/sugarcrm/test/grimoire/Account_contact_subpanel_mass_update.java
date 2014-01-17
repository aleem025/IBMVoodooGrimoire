package com.sugarcrm.test.grimoire;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;

public class Account_contact_subpanel_mass_update extends SugarTest {
	AccountRecord myAccount;
	ContactRecord myContact;
	
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
		myContact = (ContactRecord)sugar.contacts.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myAccount.navToRecord();
		sugar.accounts.recordView.showDataView();
		
		Subpanel contactsSubpanel = sugar.accounts.recordView.subpanels.get("Contacts");
		contactsSubpanel.selectAll();
		contactsSubpanel.openMassUpdate();
		
		FieldSet massUpdateData = new FieldSet();
		massUpdateData.put("Lead Source", "Cold Call");
		massUpdateData.put("Assigned To", "qauser");
		
		contactsSubpanel.massUpdate.performMassUpdateSubpanel(massUpdateData);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}
