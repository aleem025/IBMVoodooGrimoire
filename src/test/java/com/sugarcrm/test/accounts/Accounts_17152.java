package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Accounts_17152 extends SugarTest {		
	public void setup() throws Exception {
		sugar.login();
	}
	
	/**
	 * Open Search and select Teams drawer after click the "Search for more..." option
	 * @throws Exception
	 */		
	@Test	
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		DataSource ds = testData.get(testName);
		sugar.accounts.navToListView();
		sugar.accounts.listView.create();
		
		if(sugar.accounts.createDrawer.getControl("showMore").queryVisible() == true){
			sugar.accounts.createDrawer.getControl("showMore").click();
		}
		
		//TODO VOOD-518 need lib support for team widget
		new VoodooControl("a", "css", "span[data-voodoo-name='team_name'] div.control-group a.select2-choice").click();
		
		new VoodooControl("div", "css", "div#select2-drop div.select2-result-label").click();
		
		new VoodooControl("div", "css", "div.layout_Teams.drawer.transition div.headerpane").assertContains(ds.get(0).get("title"), true);
		new VoodooControl("a", "css", "a[name='close']").click();
		
		sugar.accounts.createDrawer.cancel();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
