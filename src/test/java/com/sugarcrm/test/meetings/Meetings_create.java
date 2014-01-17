package com.sugarcrm.test.meetings;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.MeetingRecord;
import com.sugarcrm.test.SugarTest;

public class Meetings_create extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		MeetingRecord myMeeting = (MeetingRecord)sugar.meetings.create();
		myMeeting.verify();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.meetings.api.deleteAll();
		sugar.logout();
	}
}
