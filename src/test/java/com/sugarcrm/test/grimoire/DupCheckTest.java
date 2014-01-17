package com.sugarcrm.test.grimoire;

import com.sugarcrm.test.SugarTest;
import org.junit.Test;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;

public class DupCheckTest extends SugarTest {
	AccountRecord firstAccount, secondAccount;
	
	public void setup() throws Exception {
		sugar.login();
		
		// Create a record to be the duplicate match.
		FieldSet record = new FieldSet();
		record.put("website", "http://www.sugarcrm.com/");
		firstAccount = (AccountRecord)sugar.accounts.api.create(record);
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running DupCheckTest...");

		// Create custom record.
		FieldSet record = new FieldSet();
		record.put("website", "http://www.ibm.com/");

		// Create a new Account record using custom data via the API.
		secondAccount = (AccountRecord)sugar.accounts.create(record);
		sugar.accounts.createDrawer.getControl("duplicateCount").assertEquals("1 duplicates found.", true);
		sugar.accounts.createDrawer.getControl("duplicateHeaderRow").assertExists(true);
		sugar.accounts.createDrawer.getEditField("website").assertEquals("http://www.ibm.com/", true);
		sugar.accounts.createDrawer.previewDuplicate(1);
		new VoodooControl("div", "css", ".fld_name.preview").assertElementContains(secondAccount.recordData.get("name"), true);
		sugar.accounts.createDrawer.selectAndEditDuplicate(1);
		sugar.accounts.createDrawer.getEditField("website").assertEquals("http://www.sugarcrm.com/", true);
		sugar.accounts.createDrawer.resetToOriginal();
		sugar.accounts.createDrawer.getEditField("website").assertEquals("http://www.ibm.com/", true);
		sugar.accounts.createDrawer.cancel();
	}

	public void cleanup() throws Exception {
		// Delete all Account records via the API.
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
