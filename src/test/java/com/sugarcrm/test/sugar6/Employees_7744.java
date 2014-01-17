package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Employees_7744 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			EmployeeRecord myEmployee = sugar.Employees.create();  

			// Verify the returned data object using the GUI
			
//			myEmployee.verify();
	}

	public void cleanup() throws Exception {
//		sugar.Employees.api.deleteAll();
		sugar.logout();
	}
}
