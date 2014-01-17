package com.sugarcrm.test.accounts;
import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_18319 extends SugarTest {
	protected AccountRecord myAccount;
	protected ContactRecord myContact;
	protected CaseRecord myCase;

	public void setup() throws Exception {
		sugar.login();
		// create account and contacts, and related contacts to account
		myAccount = (AccountRecord) sugar.accounts.api.create();
		String relAcc = myAccount.getRecordIdentifier();
		myCase = (CaseRecord) sugar.cases.api.create();
		myContact = (ContactRecord) sugar.contacts.api.create();
		myContact.navToRecord();
		sugar.contacts.recordView.edit();
		sugar.contacts.recordView.getEditField("relAccountName").set(relAcc);
		VoodooUtils.pause(1000);
		new VoodooControl("a", "css", "#alerts a.btn-link").click();
		sugar.contacts.recordView.save();

		myCase.navToRecord();
		sugar.cases.recordView.edit();
		sugar.cases.recordView.getEditField("relAccountName").set(relAcc);
		VoodooUtils.pause(1000);
		sugar.cases.recordView.save();

	}

	/**
	 * Preview record from subpanel list
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myAccount.navToRecord();

		// define controls for preview
		VoodooControl previewPane = new VoodooControl("div", "css",
				"div.block.preview-data");
		VoodooControl contactPreview = new VoodooControl(
				"a",
				"xpath",
				"//div[@class='filtered tabbable tabs-left layout_Contacts']//a[@data-original-title='Preview']");
		contactPreview.click();
		VoodooUtils.pause(3000);
		previewPane.assertElementContains(myContact.recordData.get("firstName"), true);
		previewPane.assertElementContains(myContact.recordData.get("lastName"), true);
		previewPane.assertElementContains(myContact.recordData.get("department"), true);
		previewPane.assertElementContains(myContact.recordData.get("phoneMobile"), true);
		previewPane.assertElementContains(myContact.recordData.get("salutation"), true);

		VoodooControl casePreview = new VoodooControl("a", "xpath", "//div[@class='filtered tabbable tabs-left layout_Cases']//a[@data-original-title='Preview']");
		casePreview.click();
		VoodooUtils.pause(3000);
		previewPane.assertElementContains(myCase.recordData.get("name"), true);
		previewPane.assertElementContains(myCase.recordData.get("resolution"), true);
		previewPane.assertElementContains(myCase.recordData.get("description"), true);
		previewPane.assertElementContains(myCase.recordData.get("relAccountName"), true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {

		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.cases.api.deleteAll();
		sugar.logout();

	}
}
