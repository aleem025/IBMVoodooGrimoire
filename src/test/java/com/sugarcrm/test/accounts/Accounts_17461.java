package com.sugarcrm.test.accounts;

import java.util.ArrayList;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.test.SugarTest;

public class Accounts_17461 extends SugarTest {
	protected AccountRecord myAccount;
	protected ContactRecord myContact;
	protected CaseRecord myCase;
	protected ArrayList<Record> contacts;
	protected VoodooControl container;
	
	public void setup() throws Exception {
		sugar.login();
		// create account and contacts, and related contacts to account
		DataSource contactsList = testData.get(testName);
		myAccount = (AccountRecord) sugar.accounts.api.create();
		FieldSet newData = new FieldSet();
		newData.put("name", contactsList.get(0).get("firstName") );
		myCase = (CaseRecord) sugar.cases.api.create(newData);
		String relAcc = myAccount.getRecordIdentifier();
		
		contacts = sugar.contacts.api.create(contactsList);
		for (Record con : contacts) {
			con.navToRecord();			
			sugar.contacts.recordView.edit();
			sugar.contacts.recordView.getEditField("relAccountName").set(relAcc);
			VoodooUtils.pause(1000);
			new VoodooControl("a", "css", "#alerts a.btn-link").click();
			sugar.contacts.recordView.save();
		}
		
		myCase.navToRecord();		
		sugar.cases.recordView.edit();
		sugar.cases.recordView.getEditField("relAccountName").set(relAcc);
		sugar.contacts.recordView.save();
	}

	/**
	 * Verify subpanel filters update current data displayed on subpanel
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// TODO VOOD-468 VOOD-477 need replace these controls after lib file is done
		myAccount.navToRecord();
		VoodooUtils.pause(2000);
		container = new VoodooSelect("a", "css", "div.related-filter a");
		container.set("Contacts");
		VoodooUtils.pause(3000);
		
		VoodooControl accSub = new VoodooControl("div", "css",
				"div.main-content div.layout_Accounts div[data-voodoo-name='Accounts']");
		accSub.assertAttribute("class", "hide");
		VoodooControl oppSub = new VoodooControl("div", "css",
				"div.main-content div[data-voodoo-name='Opportunities']");
		oppSub.assertAttribute("class", "hide");
		VoodooControl leadSub = new VoodooControl("div", "css",
				"div.main-content div[data-voodoo-name='Leads']");
		leadSub.assertAttribute("class", "hide");
		VoodooControl caseSub = new VoodooControl("div", "css",
				"div.main-content div[data-voodoo-name='Cases']");
		caseSub.assertAttribute("class", "hide");
		VoodooControl docSub = new VoodooControl("div", "css",
				"div.main-content div[data-voodoo-name='Documents']");
		docSub.assertAttribute("class", "hide");
		VoodooControl quoSub = new VoodooControl("div", "css",
				"div.main-content div[data-voodoo-name='Quotes']");
		quoSub.assertAttribute("class", "hide");
		VoodooControl camSub = new VoodooControl("div", "css",
				"div.main-content div[data-voodoo-name='CampaignLog']");
		camSub.assertAttribute("class", "hide");
				
		VoodooControl conSub = new VoodooControl("div", "xpath",
				"//div[@class='main-content']//div[@data-voodoo-name='Contacts']");
		conSub.assertElementContains(contacts.get(0).getRecordIdentifier(),	true);
		conSub.assertElementContains(contacts.get(1).getRecordIdentifier(),	true);
		VoodooControl searchFilter = new VoodooControl("input", "css",
				"div.filter-view.search input.search-name");
		String filter1 = contacts.get(0).recordData.get("firstName");
		searchFilter.set(filter1);
		VoodooUtils.pause(2000);
		conSub.assertElementContains(contacts.get(0).getRecordIdentifier(),	true);
		conSub.assertElementContains(contacts.get(1).getRecordIdentifier(),	false);		
		
		container.set("All");
		VoodooUtils.pause(3000);
		accSub.assertElementContains("Member Organizations", true);
		oppSub.assertElementContains("Opportunities", true);
		leadSub.assertElementContains("Leads", true);
		docSub.assertElementContains("Documents", true);
		quoSub.assertElementContains("Quotes", true);
		camSub.assertElementContains("Campaigns", true);
		
		searchFilter.set(filter1);
		conSub.assertElementContains(contacts.get(0).getRecordIdentifier(),	true);
		conSub.assertElementContains(contacts.get(1).getRecordIdentifier(),	false);		
		caseSub.assertElementContains(myCase.getRecordIdentifier(), true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {		
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.cases.api.deleteAll();
		sugar.logout();
	}
}