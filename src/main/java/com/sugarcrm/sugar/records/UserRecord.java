package com.sugarcrm.sugar.records;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.FieldSet;

public class UserRecord extends BWCRecord {
	
	public UserRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.users;
		recordData.putAll(data);
	}
	
	/**
	 * Log in as this user.
	 * @throws Exception
	 */
	public void login() throws Exception{
		sugar.loginScreen.getControl("loginUserName").set(recordData.get("userName"));
		sugar.loginScreen.getControl("loginPassword").set(recordData.get("password"));
		sugar.loginScreen.getControl("login").click();
	}
	
	/**
	 * delete() will navigate and delete the user record that calls this
	 * function. Re-assigns Users record to no-one.
	 * 
	 * @param user - User Record to be deleted
	 * @throws Exception
	 */
	public void delete(UserRecord user) throws Exception {
		//TODO: Need to implement the proper delete of user using the UI in Sugar7
	}
	
	/**
	 * Navigate to the User Record object
	 */
	public void navToRecord() throws Exception {
		// Navigate to ListView of Record Object module
		module.navToListView();
		
		//Search for the User to make it the only record in the ListView
		VoodooUtils.focusFrame("bwc-frame");
		module.listView.getControl("searchField").set(recordData.get("firstName"));
		module.listView.getControl("searchButton").click();
		VoodooUtils.pause(500);
		// Click on record to go to its detailview
		VoodooUtils.voodoo.log.info("Clicking on '" + recordData.get("firstName") + " " + recordData.get("lastName") + "'...");
		module.listView.getControl("firstRecordListView").click();
		VoodooUtils.pause(1500);
		VoodooUtils.focusDefault();
	}
	
} //end UserRecord