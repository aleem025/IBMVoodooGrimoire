package com.sugarcrm.test.opportunities;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.OpportunityRecord;

import static org.junit.Assert.assertEquals;

public class Opportunities_delete extends SugarTest {
	OpportunityRecord myOpp;
	
	public void setup() throws Exception {
		sugar.login();
		sugar.accounts.api.create();
		myOpp = (OpportunityRecord)sugar.opportunities.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Delete the account using the UI.
		myOpp.delete();
		
		// Verify the account was deleted.
		sugar.opportunities.navToListView();
		assertEquals(VoodooUtils.contains(myOpp.getRecordIdentifier(), true), false);

		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.opportunities.api.deleteAll();
		sugar.logout();
	}
}
