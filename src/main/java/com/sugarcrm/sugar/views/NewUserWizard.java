package com.sugarcrm.sugar.views;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;

/**
 * Models the login screen of SugarCRM.
 * @author Mazen Louis <mlouis@sugarcrm.com>
 */
public class NewUserWizard extends View {
	protected static NewUserWizard newUserwizard;
	protected static AppModel sugar;
	
	public static NewUserWizard getInstance() throws Exception {
		if (newUserwizard == null) newUserwizard = new NewUserWizard();
		return newUserwizard;
	}

	/**
	 * Initializes the login screen.
	 * @throws Exception
	 */
	public NewUserWizard() throws Exception {
		super();
		sugar = AppModel.getInstance();
		
		// Common to all pages
		addControl("nextButton", "a", "name", "next_button");
		
		// First page of new user wizard
		addControl("firstName", "input", "name", "first_name");
		addControl("lastName", "input", "name", "last_name");
		addControl("phone", "input", "name", "phone_work");
		addControl("emailAddress", "input", "name", "email");
		
		// Second page of new user wizard
		addControl("previousButton", "a", "name", "previous_button");
		addSelect("timeZone", "a", "xpath", "//*[@id='s2id_autogen1']/a/span[1]");
		addSelect("timeZoneSpan", "span", "xpath", "//*[@id='s2id_autogen1']/a/span[1]");
		addSelect("timeFormat", "a", "xpath", "//*[@id='s2id_autogen3']/a/span[1]");
		
		// Third page of new user wizard
		addControl("startSugarButton", "a", "name", "start_sugar_button");
	}

	/**
	 * Clicks on the Next Button in the new user wizard.
	 * 
	 * @throws Exception
	 */
	public void clickNextButton() throws Exception {
		getControl("nextButton").click();
		VoodooUtils.waitForAlertExpiration();
	}
	
	/**
	 * Clicks the start sugar button the last step of new user wizard.
	 * 
	 * @throws Exception
	 */
	public void clickStartSugar() throws Exception {
		getControl("startSugarButton").click();
		VoodooUtils.waitForAlertExpiration();
	}
	
	/**
	 * Perform actions for a users first log in.
	 * 
	 * Once done this method will leave the user at the Home Page screen.
	 * 
	 * @param userData FieldSet of data of the user to setup.
	 * @throws Exception
	 */
	public void setupNewUser(FieldSet userData) throws Exception {
		// Handle users' first log in
		VoodooUtils.pause(1500);
		if(!(getControl("emailAddress").getText().equals(userData.get("emailAddress")))) {
			getControl("emailAddress").set(userData.get("emailAddress"));
			getControl("lastName").set(userData.get("userName"));
			getControl("phone").click();
		}
		clickNextButton();
		VoodooUtils.waitForAlertExpiration();
		// Pause for backend sync to be performed to populate drop down lists "time zones"
		VoodooUtils.pause(1000);
		getControl("nextButton").waitForVisible(15000);
		if(!(getControl("timeZoneSpan").getText().equals(userData.get("timeZone")))) {
			getControl("timeZone").set(userData.get("timeZone"));
			VoodooUtils.pause(250);
		}
		clickNextButton();
		VoodooUtils.waitForAlertExpiration();
		VoodooUtils.pause(1000);
		getControl("startSugarButton").waitForVisible(15000);
		clickStartSugar();
	}
}
