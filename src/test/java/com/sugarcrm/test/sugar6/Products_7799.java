package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Products_7799 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			ProductRecord myProduct = (ProductRecord)sugar.Products.create();  

			// Verify the returned data object using the GUI
			
//			myProduct.verify();
	}

	public void cleanup() throws Exception {
//		sugar.Products.api.deleteAll();
		sugar.logout();
	}
}
