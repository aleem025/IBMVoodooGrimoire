package com.sugarcrm.test.activitystream;

import org.junit.Ignore;
import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Activitystream_17457_2 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * Show tip message in the comments input box
	 * 
	 * @throws Exception
	 * 
	 */
	@Ignore("This may not be a valid test, the tool tips visibility is handled by the browser and not our product")
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds = testData.get(testName);
		String tip = ds.get(0).get("tip");
		VoodooUtils.voodoo.log.info("Check tip message on module list");
		sugar.accounts.navToListView();
		sugar.accounts.listView.showActivityStream();
		VoodooUtils.pause(3000);
		// The following checks the tool tip string in the Activity Stream input
		// field is correct.
		sugar.accounts.listView.activityStream.getControl("streamInput").assertAttribute("data-placeholder", tip);
		sugar.accounts.listView.activityStream.createComment(ds.get(0).get("post"));
		VoodooUtils.pause(3000);
		// The use of sugar.accounts. is because we do not have a
		// HomeModule.java file for support
		// When HomeModule.java is available then the following lines should be
		// updated to use it.
		sugar.accounts.listView.activityStream.assertCommentContains(ds.get(0).get("post"), 1, true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}