/* QA UPDATES PACKAGE TO MATCH THE TEST FILENAME AND LOCATION */
package com.sugarcrm.test.sugarinit;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;


public class SugarInit extends SugarTest {
	
	public void setup() throws Exception {
		sugar.login_admin();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		DataSource ds = testData.get(testName);
		
		//Go through all nav links to avoid cache problems during running
		sugar.navbar.navToModule("Accounts");
		VoodooUtils.switchToBWCFrame();
		sugar.accountCreateView.ACCOUNT_NAME.assertVisible(true);
		VoodooUtils.switchBackToWindow();
		sugar.accounts.navToListView();
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("div", "class", "listViewBody").assertExists(true);
		VoodooUtils.switchBackToWindow();		
		
		sugar.navbar.navToModule("Opportunities");
		VoodooUtils.switchToBWCFrame();
		sugar.opportunityCreateView.DESCRIPTION.assertVisible(true);
		VoodooUtils.switchBackToWindow();
		sugar.opportunities.navToListView();
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("div", "class", "listViewBody").assertVisible(true);
		VoodooUtils.switchBackToWindow();
		
		sugar.navbar.navToModule("Contacts");
		VoodooUtils.switchToBWCFrame();
		sugar.contactCreateView.FIRST_NAME.assertVisible(true);
		VoodooUtils.switchBackToWindow();
		sugar.contacts.navToListView();
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("div", "class", "listViewBody").assertVisible(true);
		VoodooUtils.switchBackToWindow();
		
		sugar.navbar.navToModule("Calls");
		VoodooUtils.switchToBWCFrame();
		sugar.callCreateView.CALL_NAME.assertVisible(true);
		VoodooUtils.switchBackToWindow();
		sugar.calls.navToListView();
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("div", "class", "listViewBody").assertVisible(true);
		VoodooUtils.switchBackToWindow();
		
		sugar.navbar.navToModule("Notes");
		VoodooUtils.switchToBWCFrame();
		sugar.noteCreateView.NOTE_NAME.assertVisible(true);
		VoodooUtils.switchBackToWindow();
		sugar.notes.navToListView();
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("div", "class", "listViewBody").assertVisible(true);
		VoodooUtils.switchBackToWindow();
		
		sugar.navbar.navToModule("Notes");
		VoodooUtils.switchToBWCFrame();
		sugar.taskCreateView.TASK_NAME.assertVisible(true);
		VoodooUtils.switchBackToWindow();
		sugar.tasks.navToListView();
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("div", "class", "listViewBody").assertVisible(true);
		VoodooUtils.switchBackToWindow();
		
		//Turn off auto-generated passwords		
		sugar.navbar.selectUserAction("admin");
		VoodooUtils.switchToBWCFrame();
		sugar.admin.TOOL_PASSWORD_MANAGEMENT.waitForVisible(30000);
		sugar.admin.TOOL_PASSWORD_MANAGEMENT.click();
		sugar.admin.PASSWORD_MIN_LENGTH.waitForVisible();
		sugar.admin.PASSWORD_MIN_LENGTH.set(ds.get(0).get("min"));
		sugar.admin.PASSWORD_SETTING_ONE_NUMBER.set("false");
		sugar.admin.PASSWORD_SETTING_ONE_LOWER.set("false");
		sugar.admin.PASSWORD_SETTING_ONE_SPECIAL.set("false");
		sugar.admin.PASSWORD_SYSGENERATED_PASSWORD_CHBOX.set("false");
		sugar.admin.PASSWORD_SAVE.click();		
		VoodooUtils.switchBackToWindow();
		
		// Change the date formation and navigation paradigm for Admin
		sugar.navbar.navToProfile();		
		sugar.users.detailView.edit();
		VoodooUtils.switchToBWCFrame();
		sugar.users.USER_TAB_PROFILE_EMAIL.set(ds.get(0).get("email"));
		sugar.users.USER_TAB_ADVANCED.click();
		sugar.users.USER_TAB_ADVANCED_DATE_FORMAT.set(ds.get(0).get("dateformat"));
		sugar.users.USER_TAB_ADVANCED_TIME_FORMAT.set(ds.get(0).get("timeformat"));
		sugar.users.USER_SAVE.click();		
		VoodooUtils.switchBackToWindow();
		
		//Setup Japanese user
		sugar.navbar.selectUserAction("admin");
		VoodooUtils.switchToBWCFrame();
		sugar.admin.TOOL_USER_MANAGEMENT.waitForVisible();
		sugar.admin.TOOL_USER_MANAGEMENT.click();
		VoodooUtils.switchToBWCFrame();
		if (sugar.users.USER_LIST_SEARCH_BACIS_LINK.queryVisible())
			sugar.users.USER_LIST_SEARCH_BACIS_LINK.click();
		sugar.users.USER_LIST_SEARCH_CLEAR_BUTTON.click();
		sugar.users.USER_LIST_BASIC_SEARCH_NAME.set(ds.get(0).get("Japanese_last_name"));
		sugar.users.USER_LIST_SEARCH_BUTTON.click();
		new VoodooControl("a", "xpath", "//a[contains(text(),'" + ds.get(0).get("Japanese_full_name") + "')]").click();
		sugar.users.detailView.edit();
		VoodooUtils.switchToBWCFrame();
		sugar.users.USER_TAB_PROFILE_EMAIL.set(ds.get(0).get("Japanese_username"));
		sugar.users.USER_TAB_PROFILE_FIRST_NAME.set(ds.get(0).get("Japanese_username"));
		sugar.users.USER_SAVE.click();	
		VoodooUtils.switchBackToWindow();		
		
		//name display format - checking the salutation
		sugar.login_regularUser();	
		checkingNameDisplayFormat();
		
		sugar.login_IGFRep();
		checkingNameDisplayFormat();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}
	
	public void checkingNameDisplayFormat() throws Exception{
		sugar.navbar.navToProfile();
		sugar.users.detailView.edit();
		VoodooUtils.switchToBWCFrame();
		sugar.users.USER_TAB_ADVANCED.click();
		sugar.users.USER_TAB_ADVANCED_INCLUDE_SOLUTATION.set("true");
		sugar.users.USER_SAVE.click();
		VoodooUtils.switchBackToWindow();
	}		

	public void cleanup() throws Exception {
		sugar.logout();
	}
}