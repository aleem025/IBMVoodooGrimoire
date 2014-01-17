package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;

public class Accounts_17436 extends SugarTest {
	protected AccountRecord myAccount;	

	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord) sugar.accounts.api.create();
	}

	/**
	 * Verify subpanel header contains "Associate Existing Record" action
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds;
		ds = testData.get(testName);

		// TODO VOOD-510 need replace these controls after lib file is done
		myAccount.navToRecord();
		for(FieldSet data : ds) {
			new VoodooControl("span", "css", "div.filtered.tabbable.tabs-left.layout_"+data.get("subpanel")+" span.icon-caret-down").click();
			VoodooControl dropdown = new VoodooControl("span", "css",
				"div.filtered.tabbable.tabs-left.layout_"+ data.get("subpanel") + 
				" span.fld_select_button.panel-top");
			dropdown.assertElementContains(data.get("assert1"), true);
			dropdown.click();
			new VoodooControl("span", "css", "div[data-voodoo-name='selection-headerpane'] span.module-title").assertContains(data.get("assert2"), true);
			new VoodooControl("a", "css", "div[data-voodoo-name='selection-headerpane'] a[name='close']").click();
		}

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {		
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}