package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Quotes_2479 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		//sugar.quotes.create();
		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		//sugar.quotes.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
