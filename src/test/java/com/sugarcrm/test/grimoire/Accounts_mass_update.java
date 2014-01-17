package com.sugarcrm.test.grimoire;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.AccountRecord;

public class Accounts_mass_update extends SugarTest {

	public void setup() throws Exception {
		sugar.login();
		sugar.accounts.api.create();
		sugar.accounts.api.create();
		sugar.accounts.api.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.accounts.navToListView();
		
		FieldSet massUpdateData = new FieldSet();
		massUpdateData.put("Industry", "Banking");
		massUpdateData.put("Assigned to", "qauser");
		massUpdateData.put("Type", "Analyst");
		
		sugar.accounts.listView.checkRecord(2);
		sugar.accounts.massUpdate.performMassUpdate(massUpdateData);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
