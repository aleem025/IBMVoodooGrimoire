package com.sugarcrm.test.grimoire;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Some_ListView_methods extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
		FieldSet account1 = new FieldSet();
		account1.put("name", "Account 1");
		sugar.accounts.api.create(account1);
		
		FieldSet account2 = new FieldSet();
		account2.put("name", "Account 2");
		sugar.accounts.api.create(account2);
		
		sugar.accounts.navToListView();
		sugar.accounts.listView.toggleFavorite(1);
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Test selecting of pre-defined My Favorites filter
		sugar.accounts.listView.openFilterDropdown();
		sugar.accounts.listView.selectFilterMyFavorites();
		
		sugar.accounts.listView.verifyField(1, "name", "Account 2");
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
