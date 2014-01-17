package com.sugarcrm.test.grimoire;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.OpportunityRecord;
import com.sugarcrm.sugar.records.RevLineItemRecord;

public class RevLineItem_create extends SugarTest {
	AccountRecord myAcc;
	OpportunityRecord myOpp;
	RevLineItemRecord myRev;
	
	public void setup() throws Exception {
		sugar.login();
		VoodooUtils.pause(2000);
		sugar.admin.enableModuleDisplay(sugar.revLineItems);
		myAcc = (AccountRecord)sugar.accounts.api.create();
		myOpp = (OpportunityRecord)sugar.opportunities.create();
		
	}
	
	@Ignore("Blocked by SFA-1074")
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myRev = (RevLineItemRecord)sugar.revLineItems.create();
		myRev.verify();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.opportunities.api.deleteAll();
		sugar.admin.disableModuleDisplay(sugar.revLineItems);
		sugar.logout();
	}
}
