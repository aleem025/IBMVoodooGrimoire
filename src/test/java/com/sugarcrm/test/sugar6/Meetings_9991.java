package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Meetings_9991 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// To be created with default data.
			
//			MeetingRecord myMeeting = sugar.Meetings.create();  

			// Verify the returned data object using the GUI
			
//			myMeeting.verify();
	}

	public void cleanup() throws Exception {
//		sugar.Meetings.api.deleteAll();
		sugar.logout();
	}
}
