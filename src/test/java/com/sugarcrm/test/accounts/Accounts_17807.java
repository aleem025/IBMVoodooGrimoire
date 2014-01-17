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

public class Accounts_17807 extends SugarTest {
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
	 * Verify dynamic filtering of related records data
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// TODO VOOD-468 need replace these controls after lib file is done
		myAccount.navToRecord();
		VoodooUtils.pause(2000);
		container = new VoodooSelect("a", "css", "div.related-filter a");
		container.set("Contacts");
		VoodooUtils.pause(3000);
		new VoodooControl("div", "css", "div.main-content div.layout_Accounts div[data-voodoo-name='Accounts']").assertAttribute("class", "hide");
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='Opportunities']").assertAttribute("class", "hide");
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='Leads']").assertAttribute("class", "hide");
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='Cases']").assertAttribute("class", "hide");
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='Documents']").assertAttribute("class", "hide");
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='Quotes']").assertAttribute("class", "hide");
		new VoodooControl("div", "css", "div.main-content div[data-voodoo-name='CampaignLog']").assertAttribute("class", "hide");
				
		VoodooControl conSub = new VoodooControl("div", "xpath", "//div[@class='main-content']//div[@data-voodoo-name='Contacts']");
		conSub.assertElementContains(contacts.get(0).getRecordIdentifier(),	true);
		conSub.assertElementContains(contacts.get(1).getRecordIdentifier(),	true);
		VoodooControl searchFilter = new VoodooControl("input", "css", "div.filter-view.search input.search-name");
		String filter1 = contacts.get(0).recordData.get("firstName");
		searchFilter.set(filter1);
		VoodooUtils.pause(2000);
		conSub.assertElementContains(contacts.get(0).getRecordIdentifier(),	true);
		conSub.assertElementContains(contacts.get(1).getRecordIdentifier(),	false);		

		VoodooUtils.voodoo.log.info(testName + " complete.");
		
	}

	public void cleanup() throws Exception {
		myAccount.navToRecord();
		container.set("All");
		VoodooUtils.pause(3000);
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}