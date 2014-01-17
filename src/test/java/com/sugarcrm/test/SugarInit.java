package com.sugarcrm.test;

import org.junit.Test;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;

public class SugarInit extends SugarTest {

	// Login has been moved to execute() for this class.
	public void setup() throws Exception {}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		// Handles Admin user first log in
		FieldSet adminUser = new FieldSet();
		adminUser.put("userName", "admin");
		adminUser.put("password", "asdf");
		adminUser.put("emailAddress", "qa.sugar.qa@gmail.com");
		adminUser.put("timeZone", "America/Los Angeles (GMT-8:00)");
		sugar.login(adminUser);
		
		// TODO: Close Twitter dashlet?

		// Change password min length to 3 chars, no other requirements, and unchecks System Generated 
		FieldSet passwordSetting = new FieldSet();
		passwordSetting.put("passwordMinLength", "3");
		passwordSetting.put("passwordSettingOneUpper", "false");
		passwordSetting.put("passwordSettingOneNumber", "false");
		passwordSetting.put("passwordSettingOneLower", "false");
		passwordSetting.put("SystemGeneratedPasswordCheckbox", "false");
		sugar.admin.passwordSettings(passwordSetting);

		// Create qauser.
		sugar.users.create(sugar.users.getQAUser());
		
		// Log out of Sugar
		sugar.logout();
		VoodooUtils.pause(2000);
		
		// Log into qauser for the first time to handle new user wizard
		sugar.login(sugar.users.getQAUser());

		// TODO: Close twitter dashlet?
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}