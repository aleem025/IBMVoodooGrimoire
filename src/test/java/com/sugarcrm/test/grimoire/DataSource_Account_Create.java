package com.sugarcrm.test.grimoire;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class DataSource_Account_Create extends SugarTest {
	
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		FieldSet account1 = new FieldSet();
		account1.put("name", "Account 1");
		
		FieldSet account2 = new FieldSet();
		account2.put("name", "Account 2");
		
		DataSource data = new DataSource();
		data.add(account1);
		data.add(account2);
		
		sugar.accounts.create(data);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
