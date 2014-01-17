package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Manufacturers_557 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			ManufacturerRecord myManufacturer = sugar.Manufacturers.create();  

			// Verify the returned data object using the GUI
			
//			myManufacturer.verify();
	}

	public void cleanup() throws Exception {
//		sugar.Manufacturers.api.deleteAll();
		sugar.logout();
	}
}
