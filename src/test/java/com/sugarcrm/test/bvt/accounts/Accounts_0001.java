package com.sugarcrm.test.bvt.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.views.CreateCallView;

public class Accounts_0001 extends SugarTest {
	FieldSet account;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
	}
	/** Creating new account via full form 
	 *  Open it in detail view and verify if all account info saved correctly
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		account = sugar.accountCreateView.accountCreateLibFull();
		VoodooUtils.pause(10000);
		sugar.accounts.navToRecord(account.get("name"));						
		sugar.accounts.verifyDetailView(account);
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}