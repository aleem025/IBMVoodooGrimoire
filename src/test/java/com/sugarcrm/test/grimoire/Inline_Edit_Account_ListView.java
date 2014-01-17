package com.sugarcrm.test.grimoire;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.FieldSet;

public class Inline_Edit_Account_ListView extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
		sugar.accounts.api.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		sugar.accounts.navToListView();
		
		FieldSet data = new FieldSet();
		data.put("name", "Edited Name");
		data.put("workPhone", "408.454.9600");

		// Inline Edit the record and save
		sugar.accounts.listView.updateRecord(1, data);
		
		// Verify Record updated
		sugar.accounts.listView.verifyField(1, "name", data.get("name"));
		sugar.accounts.listView.verifyField(1, "workPhone", data.get("workPhone"));
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
