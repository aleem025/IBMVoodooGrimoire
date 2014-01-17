package com.sugarcrm.test.accounts;

import java.util.List;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.test.SugarTest;

public class Accounts_17785 extends SugarTest {	
	DataSource ds;
	List<Record> accountRecords;
	
	public void setup() throws Exception {
		sugar.login();
		ds = testData.get(testName);
		accountRecords = sugar.accounts.api.create(ds);
		sugar.accounts.navToListView();
		
		// TODO VOOD-483 
		//sugar.accounts.listView.openFilterDropdown();
		//sugar.accounts.listView.selectFilterCreateNew();
		new VoodooSelect("span", "css", "span[data-voodoo-name='filter-filter-dropdown'] span.select2-choice-type").set("Create New");

		//create new filter
		// TODO VOOD-482
		new VoodooControl("input", "css", "div.filter-options.extend.hide input.inherit-width").set(ds.get(0).get("billingAddressCity"));
		
		new VoodooControl("div", "css", "div.filter-definition-container div.select2-container.tleft").click();
		new VoodooControl("div", "xpath", "//div[@id='select2-drop']//div[contains(text(),'City')]").click();
		
		new VoodooControl("a", "css", "div.filter-operator.controls.span3 a.select2-choice.select2-default").click();
		new VoodooControl("div", "xpath", "//div[@id='select2-drop']//div[contains(text(),'matches')]").click();
		
		new VoodooControl("input", "css", "div.filter-value.controls.span4 input.inherit-width").set(ds.get(0).get("billingAddressCity"));
		
		new VoodooControl("a", "css", "div.span6 a.btn.btn-primary.save_button").click();
		
		sugar.accounts.listView.openFilterDropdown();
		sugar.accounts.listView.selectFilterAll();
	}

	/**
	 * Verify that clicking on a custom filter doesn't reveal the menu
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		sugar.accounts.listView.openFilterDropdown();
		new VoodooControl("div", "xpath", "//div[@id='select2-drop']//div[contains(text(),'"+ ds.get(0).get("billingAddressCity") +"')]").click();
		VoodooUtils.pause(1000);
		new VoodooControl("div", "css", "div.flex-list-view-content").assertElementContains(accountRecords.get(0).getRecordIdentifier(), true);
		new VoodooControl("div", "css", "div.flex-list-view-content").assertElementContains(accountRecords.get(1).getRecordIdentifier(), false);
		new VoodooControl("div", "css", "div.span6").assertVisible(false);
	
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		new VoodooControl("div", "css", "span[data-voodoo-name='filter-filter-dropdown'] div.choice-filter").click();
		new VoodooControl("a", "css", "div.span6 a.btn.btn-link.btn-invisible.delete_button").click();		
		sugar.logout();
	}
}
