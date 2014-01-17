package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17052 extends SugarTest {	
	AccountRecord myAccount;
		
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
	}

	/**
	 * Date Created/Modified group field display format on detail view
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myAccount.navToRecord();
		sugar.accounts.recordView.showMore();
		
		//TODO VOOD-597
		new VoodooControl ("span", "css", "span[data-fieldname='date_entered_by'] span.fld_date_entered.detail").assertExists(true);
		new VoodooControl ("span", "css", "span[data-fieldname='date_entered_by'] span.fld_created_by_name.detail").assertExists(true);
		new VoodooControl ("span", "css", "span[data-fieldname='date_entered_by']").hover();
		new VoodooControl ("i", "css", "div[data-name='date_entered_by'] i.icon-pencil").assertExists(false);
		
		new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by'] span.fld_date_modified.detail").assertExists(true);
		new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by'] span.fld_modified_by_name.detail").assertExists(true);
		new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by']").hover();
		new VoodooControl ("i", "css", "div[data-name='date_entered_by'] i.icon-pencil").assertExists(false);		
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}