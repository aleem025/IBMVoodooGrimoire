package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

/*import wip.CallRecord;
*/
public class Calls_13153 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
/*		// To be created with default data.
		CallRecord myCall = (CallRecord) sugar.calls.create();  

		// Verify the returned data object using the GUI
		myCall.verify();
*/	}
	public void cleanup() throws Exception {
//		sugar.calls.api.deleteAll();
		sugar.logout();
	}
}
