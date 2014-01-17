package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.UserRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17054 extends SugarTest {	
	AccountRecord myAccount;
	UserRecord qauser;
	
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
		qauser=  new UserRecord(sugar.users.getQAUser());
		sugar.logout();
		qauser.login();		
	}

	/**
	 * Date Created/Modified group field display format on edit view
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myAccount.navToRecord();
		sugar.accounts.recordView.edit();
		sugar.accounts.recordView.showMore();		
		
		// TODO VOOD-597
		new VoodooControl ("span", "css", "span[data-fieldname='date_entered_by'] span.fld_date_entered.detail").assertExists(true);
		new VoodooControl ("span", "css", "span[data-fieldname='date_entered_by'] span.fld_created_by_name.detail").assertExists(true);
		String create = new VoodooControl ("span", "css", "span[data-fieldname='date_entered_by'] span.fld_date_entered.detail").getText();
		new VoodooControl ("input", "css", "span[data-fieldname='date_entered_by'] span.fld_date_entered.detail input").assertExists(false);
		new VoodooControl ("input", "css", "span[data-fieldname='date_entered_by'] span.fld_created_by_name.detail input").assertExists(false);
		
		new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by'] span.fld_date_modified.detail").assertExists(true);
		new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by'] span.fld_modified_by_name.detail").assertExists(true);
		String befmodify = new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by'] span.fld_date_modified.detail").getText();
		new VoodooControl ("input", "css", "span[data-fieldname='date_modified_by'] span.fld_date_modified.detail input").assertExists(false);
		new VoodooControl ("input", "css", "span[data-fieldname='date_modified_by'] span.fld_modified_by_name.detail input").assertExists(false);
		
		sugar.accounts.recordView.save();
		sugar.accounts.recordView.edit();		
		new VoodooControl ("span", "css", "span[data-fieldname='date_entered_by'] span.fld_date_entered.detail").assertEquals(create, true);
		new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by'] span.fld_date_modified.detail").assertEquals(befmodify, false);
		new VoodooControl ("span", "css", "span[data-fieldname='date_modified_by'] span.fld_modified_by_name.detail").assertEquals(qauser.recordData.get("userName"), true);
				
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {		
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}