package com.sugarcrm.test.accounts;

import java.util.ArrayList;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.DataSource;

public class Accounts_17181 extends SugarTest {
	protected AccountRecord myAccount;
	protected ContactRecord myContact;
	protected ArrayList<Record> myContacts;

	public void setup() throws Exception {
		sugar.login();
		// create account and contacts, and related contacts to account
		myAccount = (AccountRecord) sugar.accounts.api.create();
		myContact = (ContactRecord) sugar.contacts.api.create();
		myContact.navToRecord();
		sugar.contacts.recordView.edit();
		sugar.contacts.recordView.getEditField("relAccountName").set(myAccount.getRecordIdentifier());
		VoodooUtils.pause(1000);
		new VoodooControl ("a", "css", "#alerts a.btn-link").click();			
		sugar.contacts.recordView.save();
	}

	/**
	 * Verify inline edit on record view subpanels
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds = testData.get(testName);
		
		myAccount.navToRecord();			
		
		//TODO VOOD-502 subpanel editRecord function doesn't work correctly
		//Subpanel conSub = sugar.accounts.recordView.subpanels.get("Contacts");	
		//conSub.editRecord(1);
		new VoodooControl("a", "css" ,"div[data-voodoo-name='Contacts'] .actions.btn-group.pull-right.list a.btn.dropdown-toggle").click();
		new VoodooControl("span", "css" ,"div[data-voodoo-name='Contacts'] .fld_edit_button.list").click();
		//TODO VOOD-503 need replace these controls after lib file done
		new VoodooControl("input", "css" ,"div[data-voodoo-name='Contacts'] table.table.table-striped.dataTable input[name='primary_address_state']").assertVisible(true);
		new VoodooControl("input", "css" ,"div[data-voodoo-name='Contacts'] table.table.table-striped.dataTable input[name='email']").assertVisible(true);
		new VoodooControl("input", "css" ,"div[data-voodoo-name='Contacts'] table.table.table-striped.dataTable input[name='first_name']").set(ds.get(0).get("firstName"));
		new VoodooControl("input", "css" ,"div[data-voodoo-name='Contacts'] table.table.table-striped.dataTable input[name='last_name']").set(ds.get(0).get("lastName"));
		new VoodooControl("input", "css" ,"div[data-voodoo-name='Contacts'] table.table.table-striped.dataTable input[name='primary_address_city']").set(ds.get(0).get("primaryAddressCity"));
		new VoodooControl("input", "css" ,"div[data-voodoo-name='Contacts'] table.table.table-striped.dataTable input[name='phone_work']").set(ds.get(0).get("phoneWork"));
		new VoodooControl("a", "css" ,"a[name='inline-save']").click();
		
		VoodooControl conSubpanel = new VoodooControl("div", "css", "div[data-voodoo-name='Contacts']");
		conSubpanel.assertElementContains(ds.get(0).get("firstName"), true);
		conSubpanel.assertElementContains(ds.get(0).get("lastName"), true);
		conSubpanel.assertElementContains(ds.get(0).get("primaryAddressCity"), true);
		conSubpanel.assertElementContains(ds.get(0).get("phoneWork"), true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}
	
	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}