package com.sugarcrm.test.grimoire;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Save_and_CreateNew_Accounts extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}
	
	// Test Library Support for save and create new method.
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		FieldSet testData = new FieldSet();
		testData.put("name", "Test");
		
		sugar.accounts.navToListView();
		sugar.accounts.listView.create();
		sugar.accounts.createDrawer.getEditField("name").set(testData.get("name"));
		sugar.accounts.createDrawer.saveAndCreateNew();
		
		sugar.accounts.createDrawer.getEditField("name").set(testData.get("name"));
		sugar.accounts.createDrawer.cancel();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}