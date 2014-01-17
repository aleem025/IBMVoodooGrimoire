package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;

public class Accounts_17155 extends SugarTest {
	
	AccountRecord account1;
	
	public void setup() throws Exception {
		sugar.login();
		account1 = (AccountRecord) sugar.accounts.api.create();
	}
	
	/**
	 * Team set should be reverted on cancel of detail view inline edit
	 * @throws Exception
	 */	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		DataSource ds = testData.get(testName);
		
		account1.navToRecord();							
		sugar.accounts.recordView.showMore();
		// TODO VOOD-518 need lib support for team widget
		new VoodooControl("span", "css", "span[data-voodoo-name='team_name']").hover();
		new VoodooControl("i", "css", "span[data-name='team_name'] i.icon-pencil").click();
		new VoodooControl("input", "css", "div#select2-drop input.select2-input.select2-focused").set(ds.get(0).get("team1"));
		new VoodooControl("span", "css", "div#select2-drop span.select2-match").click();
		
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