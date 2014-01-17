package com.sugarcrm.test.meetings;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.MeetingRecord;
import static org.junit.Assert.assertEquals;

public class Meetings_delete extends SugarTest {
	MeetingRecord myMeeting;
	
	public void setup() throws Exception {
		sugar.login();
		myMeeting = (MeetingRecord)sugar.meetings.api.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Delete the meeting using the UI.
		myMeeting.delete();
		
		// Verify the meeting was deleted.
		sugar.meetings.navToListView();
		
		sugar.meetings.listView.clearSearchForm();
		sugar.meetings.listView.submitSearchForm();
		VoodooUtils.focusFrame("bwc-frame");
		assertEquals(VoodooUtils.contains(myMeeting.getRecordIdentifier(), true), false);
		VoodooUtils.focusDefault();

		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.meetings.api.deleteAll();
		sugar.logout();
	}
}
