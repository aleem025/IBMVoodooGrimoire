package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Currencies_8071 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			CurrencyRecord myCurrency = sugar.currencies.create();  

			// Verify the returned data object using the GUI
			
//			myCurrency.verify();
	}

	public void cleanup() throws Exception {
//		sugar.currencies.api.deleteAll();
		sugar.logout();
	}
}
