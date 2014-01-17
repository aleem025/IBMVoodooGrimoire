package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Cases_1262 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			CaseRecord myCase = (CaseRecord)sugar.cases.create();  

			// Verify the returned data object using the GUI
			
//			myCase.verify();
	}
	public void cleanup() throws Exception {
//		sugar.cases.api.deleteAll();
		sugar.logout();
	}
}
