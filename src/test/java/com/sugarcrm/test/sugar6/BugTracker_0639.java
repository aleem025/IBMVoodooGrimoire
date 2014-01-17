package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

/*import wip.BugRecord;

import com.sugarcrm.sugar.*;
import com.sugarcrm.test.SugarTest;
*/
public class BugTracker_0639 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
/*		// To be created with default data.
		BugRecord myBugTracker = (BugRecord)sugar.bugs.create();  

		// Verify the returned data object using the GUI
		myBugTracker.verify();
*/	}

	public void cleanup() throws Exception {
/*		sugar.bugs.api.deleteAll();
		sugar.logout();
*/	}
}
