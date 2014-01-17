package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Contracts_1630 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			ContractRecord myContract = sugar.contracts.create();  

			// Verify the returned data object using the GUI
			
//			myContact.verify();
	}

	public void cleanup() throws Exception {
//		sugar.contracts.api.deleteAll();
		sugar.logout();
	}
}
