package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17178 extends SugarTest {
	AccountRecord account1;
	
	public void setup() throws Exception {
		sugar.login();
		account1 = (AccountRecord)sugar.accounts.api.create();
		account1.navToRecord();
	}

	/**
	 * Add post to activity stream on record view
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.accounts.recordView.showActivityStream();
		DataSource ds = testData.get(testName);
		
		sugar.accounts.recordView.activityStream.createComment(ds.get(0).get("post"));
		sugar.accounts.recordView.activityStream.assertCommentContains(ds.get(0).get("post"), 1, true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
