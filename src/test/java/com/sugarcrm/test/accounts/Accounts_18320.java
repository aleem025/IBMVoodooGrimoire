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

public class Accounts_18320 extends SugarTest {
	protected AccountRecord myAccount;
	protected ArrayList<Record> myContacts;
	
	public void setup() throws Exception {
		sugar.login();
		// create account and contacts, and related contacts to account
		myAccount = (AccountRecord) sugar.accounts.api.create();
		DataSource contactsList = new DataSource();
		contactsList = testData.get(testName);
		myContacts = (ArrayList<Record>)sugar.contacts.api.create(contactsList);
		String relAcc = myAccount.getRecordIdentifier();
		for(Record createdRecord : myContacts) {	
			createdRecord.navToRecord();
			sugar.contacts.recordView.edit();
			sugar.contacts.recordView.getEditField("relAccountName").set(relAcc);
			VoodooUtils.pause(1000);
			new VoodooControl ("a", "css", "#alerts a.btn-link").click();			
			sugar.contacts.recordView.save();
		}
	}

	/**
	 * Subpanel preview pane pagination
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		int s = myContacts.size()-1;
			
		ContactRecord contact;		
	
		myAccount.navToRecord();
				
		// TODO VOOD-476 need replace these control defines after lib file is done 		
		// define controls for preview
		
		VoodooControl preview = new VoodooControl ("a", "xpath", "//div[@class='filtered tabbable tabs-left layout_Contacts']//tr[1]//a[@data-original-title='Preview']");
		preview.click();
		VoodooUtils.pause(2000);
		VoodooControl previewPane = new VoodooControl ("div", "css", "div.block.preview-data");
			
		VoodooControl nextIconEnable = new VoodooControl ("a", "xpath", "//div[@class='preview-pane active']//a[@class='btn btn-invisible'][@data-direction='right']");
		VoodooControl prevIconEnable = new VoodooControl ("a", "xpath", "//div[@class='preview-pane active']//a[@class='btn btn-invisible'][@data-direction='left']");
		VoodooControl nextIconDisable = new VoodooControl ("a", "xpath", "//div[@class='preview-pane active']//a[@class='btn btn-invisible disabled'][@data-direction='right']");
		VoodooControl prevIconDisable =	new VoodooControl ("a", "xpath", "//div[@class='preview-pane active']//a[@class='btn btn-invisible disabled'][@data-direction='left']");	
		
		prevIconDisable.assertExists(true);		
		
		// navigation in preview pane
		int j = 1;
		for(int i = s;i>0;i--){
			
			contact = (ContactRecord) myContacts.get(i);
			previewPane.assertElementContains(contact.recordData.get("firstName"), true);
			previewPane.assertElementContains(contact.recordData.get("lastName"), true);
			previewPane.assertElementContains(contact.recordData.get("department"), true);
			previewPane.assertElementContains(contact.recordData.get("phoneMobile"), true);
			previewPane.assertElementContains(contact.recordData.get("salutation"), true);	
			VoodooControl currRow = new VoodooControl ("tr", "xpath", "//div[@class='filtered tabbable tabs-left layout_Contacts']//tbody[1]//tr[position()=" + j + "]");
			
			currRow.assertAttribute("class", "single current highlighted");	
			nextIconEnable.click();
			j++;
			
			VoodooUtils.pause(1000);
			
		}
		nextIconDisable.assertExists(true);

		for(int i = 1;i<=s;i++){
			prevIconEnable.click();
			VoodooUtils.pause(1000);
			contact = (ContactRecord) myContacts.get(i);
			previewPane.assertElementContains(contact.recordData.get("firstName"), true);
			previewPane.assertElementContains(contact.recordData.get("lastName"), true);
			previewPane.assertElementContains(contact.recordData.get("department"), true);
			previewPane.assertElementContains(contact.recordData.get("phoneMobile"), true);
			previewPane.assertElementContains(contact.recordData.get("salutation"), true);				
			
		}
		prevIconDisable.assertExists(true);		
			
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
		
	}
	public void cleanup() throws Exception {
		
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();
		
	}
}