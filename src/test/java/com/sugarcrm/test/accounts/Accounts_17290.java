package com.sugarcrm.test.accounts;

import java.util.ArrayList;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.test.SugarTest;

public class Accounts_17290 extends SugarTest {
	protected AccountRecord myAccount;
	protected ArrayList<Record> myAccounts;

	public void setup() throws Exception {
		sugar.login();
		// create accounts
		myAccounts = sugar.accounts.create(testData.get(testName));		
	}

	/**
	 * Verify Next/Previous buttons should be displayed on the preview header when more than one record selected
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		sugar.accounts.navToListView();
		sugar.accounts.listView.toggleFavorite(1);		
		sugar.accounts.listView.toggleFavorite(2);
		sugar.accounts.listView.toggleFavorite(3);

		// TODO: VOOD-499 selectFilterMyFavorites() not work
		// sugar.accounts.listView.selectFilterMyFavorites();
		new VoodooSelect("a", "css", "[data-voodoo-name='filter-filter-dropdown'] a").set("My Favorites");		
		VoodooUtils.pause(2000);
		for(int i = 1; i <= 3; i++) {
			sugar.accounts.listView.getControl("favoriteStar"+String.format("%02d", i)).assertAttribute("class", "active");
		}
		new VoodooControl("i", "css", "div[data-voodoo-name='recordlist']").assertElementContains(myAccounts.get(0).getRecordIdentifier(), false);
		sugar.accounts.listView.previewRecord(1);
		
		//TODO VOOD-511 need lib support for preview pane navigation
		VoodooControl next = new VoodooControl("i", "css", "div[data-voodoo-name='preview-header'] i.icon-chevron-right");
		VoodooControl previous = new VoodooControl("i", "css", "div[data-voodoo-name='preview-header'] i.icon-chevron-left");
		next.assertVisible(true);
		previous.assertVisible(true);
		VoodooControl prePane = new VoodooControl("div", "css", "div[data-voodoo-name='preview']");
		prePane.assertContains(myAccounts.get(3).getRecordIdentifier(), true);
		prePane.assertContains(myAccounts.get(3).recordData.get("workPhone"), true);
		next.click();	
		VoodooUtils.pause(1000);
		prePane.assertContains(myAccounts.get(2).getRecordIdentifier(), true);
		prePane.assertContains(myAccounts.get(2).recordData.get("workPhone"), true);
		previous.click();	
		VoodooUtils.pause(1000);
		prePane.assertContains(myAccounts.get(3).getRecordIdentifier(), true);
		prePane.assertContains(myAccounts.get(3).recordData.get("workPhone"), true);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}
	
	public void cleanup() throws Exception {		
		sugar.accounts.listView.openFilterDropdown();
		sugar.accounts.listView.selectFilterAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}