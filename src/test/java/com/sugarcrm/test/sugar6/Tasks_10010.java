package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;


public class Tasks_10010 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			TaskRecord myTask = sugar.Tasks.create();  

			// Verify the returned data object using the GUI
			
//			myTask.verify();
	}

	public void cleanup() throws Exception {
//		sugar.Tasks.api.deleteAll();
		sugar.logout();
	}
}
