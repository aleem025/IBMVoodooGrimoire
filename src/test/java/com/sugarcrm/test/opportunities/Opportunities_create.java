package com.sugarcrm.test.opportunities;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.OpportunityRecord;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Opportunities_create extends SugarTest {
	AccountRecord myAccount;
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		OpportunityRecord myOpp = (OpportunityRecord)sugar.opportunities.create();
		myOpp.verify();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.opportunities.api.deleteAll();
		sugar.logout();
	}
}
