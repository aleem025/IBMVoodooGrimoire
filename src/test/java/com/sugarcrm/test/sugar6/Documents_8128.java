package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;


public class Documents_8128 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		try {
			// To be created with default data.
			
//			DocumentRecord myDocument = sugar.Documents.create();  

			// Verify the returned data object using the GUI
			
//			myDocument.verify();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cleanup() throws Exception {
//		sugar.Documents.api.deleteAll();
		sugar.logout();
	}
}
