package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Accounts_17697 extends SugarTest {
	DataSource ds;
	
	public void setup() throws Exception {
		sugar.login();
		ds = testData.get(testName);
		sugar.accounts.navToListView();
		
		// TODO VOOD-483 
		//sugar.accounts.listView.openFilterDropdown();
		//sugar.accounts.listView.selectFilterCreateNew();
		
		//create new filter
		new VoodooSelect("span", "css", "span[data-voodoo-name='filter-filter-dropdown'] span.select2-choice-type").set("Create New");
		
		// TODO VOOD-482
		new VoodooControl("input", "css", "div.filter-options.extend.hide input.inherit-width").set(ds.get(0).get("name"));
		
		new VoodooControl("div", "css", "div.filter-definition-container div.select2-container.tleft").click();
		new VoodooControl("div", "xpath", "//div[@id='select2-drop']//div[contains(text(),'Name')]").click();
		
		new VoodooControl("a", "css", "div.filter-operator.controls.span3 a.select2-choice.select2-default").click();
		new VoodooControl("div", "xpath", "//div[@id='select2-drop']//div[contains(text(),'matches')]").click();
		
		new VoodooControl("input", "css", "div.filter-value.controls.span4 input.inherit-width").set(ds.get(0).get("data"));
		
		new VoodooControl("a", "css", "div.span6 a.btn.btn-primary.save_button").click();
		
		sugar.accounts.listView.openFilterDropdown();
		sugar.accounts.listView.selectFilterAll();
	}

	/**
	 * Verify user can delete a defined filter
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
				
		sugar.accounts.listView.openFilterDropdown();
		new VoodooControl("div", "xpath", "//div[@id='select2-drop']//div[contains(text(),'"+ ds.get(0).get("name") +"')]").click();
		VoodooUtils.pause(1000);
		
		new VoodooControl("div", "css", "span[data-voodoo-name='filter-filter-dropdown'] div.choice-filter").click();
		new VoodooControl("div", "css", "div.span6").waitForVisible();
		new VoodooControl("a", "css", "div.span6 a.btn.btn-link.btn-invisible.delete_button").assertContains("Delete", true);
		new VoodooControl("a", "css", "div.span6 a.btn.btn-link.btn-invisible.delete_button").click();
		VoodooUtils.pause(2000);
		new VoodooControl("div", "css", "span[data-voodoo-name='filter-filter-dropdown'] div.choice-filter").assertContains("All Accounts", true);
		sugar.accounts.listView.openFilterDropdown();
		new VoodooControl("div", "css", "div#select2-drop").assertContains(ds.get(0).get("name"), false);
		sugar.accounts.listView.selectFilterAll();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
