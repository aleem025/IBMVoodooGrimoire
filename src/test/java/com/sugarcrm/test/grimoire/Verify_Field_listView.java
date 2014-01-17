package com.sugarcrm.test.grimoire;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.FieldSet;

public class Verify_Field_listView extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
		sugar.accounts.api.create();
		
		FieldSet secondAccount = new FieldSet();
		secondAccount.put("name", "Second Account");
		secondAccount.put("billingAddressCity", "Mountain View");
		sugar.accounts.api.create(secondAccount);
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Check verifyField method for listView works
		sugar.accounts.navToListView();
		
		sugar.accounts.listView.verifyField(1, "name", "Second Account");
		sugar.accounts.listView.verifyField(1, "billing_address_city", "Mountain View");
		sugar.accounts.listView.verifyField(2, "name", "SugarCRM Inc.");
		sugar.accounts.listView.verifyField(2, "billing_address_city", "Cupertino");
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
