package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Accounts_17170 extends SugarTest {
		
	public void setup() throws Exception {
		sugar.login();
	}
	
	/**
	 * Shouldn't save duplicated teams in team field widget
	 * @throws Exception
	 */	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		DataSource ds = testData.get(testName);
		sugar.accounts.navToListView();
		sugar.accounts.listView.create();
		sugar.accounts.createDrawer.getEditField("name").set(ds.get(0).get("name"));	
		sugar.accounts.createDrawer.showMore();
		
		//TODO VOOD-518 need lib support for team widget
		new VoodooControl("button", "css", "button.btn.first").click();
		new VoodooSelect("a", "css", "span[data-voodoo-name='team_name'] div.control-group:nth-child(2) a.select2-choice").set(ds.get(0).get("team"));
		new VoodooControl("button", "css", "button.btn.first").click();
		new VoodooSelect("a", "css", "span[data-voodoo-name='team_name'] div.control-group:nth-child(3) a.select2-choice").set(ds.get(0).get("team"));
		new VoodooControl("i", "css", "span[data-voodoo-name='team_name'] div.control-group:nth-child(3) i.icon-star").click();
				
		sugar.accounts.createDrawer.saveAndView();
		// TODO VOOD-582
		VoodooUtils.pause(3000);
		new VoodooControl("div", "css", "span[data-voodoo-name='team_name'] div:nth-child(1)").assertContains(ds.get(0).get("team"), true);
		new VoodooControl("div", "css", "span[data-voodoo-name='team_name'] div:nth-child(1)").assertContains(ds.get(0).get("primary"), true);		
		new VoodooControl("div", "css", "span[data-voodoo-name='team_name'] div:nth-child(2)").assertContains(ds.get(0).get("team1"), true);
		
		new VoodooControl("div", "css", "span[data-voodoo-name='team_name'] div:nth-child(3)").assertExists(false);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
