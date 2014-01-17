package com.sugarcrm.test.calls;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CallRecord;
import static org.junit.Assert.assertEquals;

public class Calls_delete extends SugarTest {
	CallRecord myCall;

	public void setup() throws Exception {
		sugar.login();
		myCall = (CallRecord) sugar.calls.api.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Delete the call using the UI.
		myCall.delete();

		// Verify the Call does not exist
		sugar.calls.navToListView();

		//sugar.calls.searchForm.clearSearchForm();
		//sugar.calls.searchForm.submitSearchForm();
		VoodooUtils.focusFrame("bwc-frame");
		assertEquals(VoodooUtils.contains(myCall.getRecordIdentifier(), true),
				false);
		VoodooUtils.focusDefault();

		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.calls.api.deleteAll();
		sugar.logout();
	}
}
