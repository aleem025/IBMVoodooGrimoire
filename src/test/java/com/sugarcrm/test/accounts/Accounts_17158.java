package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17158 extends SugarTest {
	
	AccountRecord account1;
	
	public void setup() throws Exception {
		sugar.login();
		account1 = (AccountRecord) sugar.accounts.api.create();
	}

	/**
	 * Team set should be reverted on cancel of edit
	 * @throws Exception
	 */		
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		DataSource ds = testData.get(testName);
		
		account1.navToRecord();
		sugar.accounts.recordView.edit();					
		sugar.accounts.recordView.showMore();
		
		//TODO VOOD-518 need lib support for team widget
		new VoodooControl("button", "css", "button.btn.first").click();
		new VoodooSelect("a", "css", "span[data-voodoo-name='team_name'] div.control-group:nth-child(2) a.select2-choice").set(ds.get(0).get("team1"));
		new VoodooControl("i", "css", "span[data-voodoo-name='team_name'] div.control-group:nth-child(2) i.icon-star").click();
		
		sugar.accounts.recordView.cancel();
		new VoodooControl("div", "css", "span[data-voodoo-name='team_name'] div:nth-child(1)").assertContains(ds.get(0).get("team"), true);
		new VoodooControl("div", "css", "span[data-voodoo-name='team_name'] div:nth-child(1)").assertContains(ds.get(0).get("primary"), true);		
		new VoodooControl("div", "css", "span[data-voodoo-name='team_name'] div:nth-child(2)").assertExists(false);
		new VoodooControl("span", "css", "span[data-voodoo-name='team_name']").assertContains(ds.get(0).get("team1"), false);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
