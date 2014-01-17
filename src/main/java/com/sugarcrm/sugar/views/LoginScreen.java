package com.sugarcrm.sugar.views;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.UserRecord;

/**
 * Models the login screen of SugarCRM.
 * @author David Safar <dsafar@sugarcrm.com>
 */
public class LoginScreen extends View {
	protected static LoginScreen loginScreen;
	protected static AppModel sugar;
	private DataSource ds;
	
	public static LoginScreen getInstance() throws Exception {
		if (loginScreen == null) loginScreen = new LoginScreen();
		return loginScreen;
	}

	/**
	 * Initializes the login screen.
	 * @throws Exception
	 */
	public LoginScreen() throws Exception {
		super();
		sugar = AppModel.getInstance();
		ds = VoodooUtils.getData("LoginScreen");
		addControl("loginUserName", "input", "name", "username");
		addControl("loginPassword", "input", "name", "password");
		addControl("login", "a", "name", "login_button");
	}

	/**
	 * Logs into SugarCRM as the user configured in grimoire.properties
	 * @throws Exception
	 */
	public void login() throws Exception {
		if (sugar.navbar.userAction.getControl("userActions").queryVisible()){
			sugar.logout();
		}
		getControl("loginUserName").waitForVisible(30000);
		getControl("loginUserName").set(VoodooUtils.getGrimoireConfig().getValue("sugar_user", "admin"));
		getControl("loginPassword").set(VoodooUtils.getGrimoireConfig().getValue("sugar_pass", "asdf"));
		getControl("login").click();
		
		VoodooUtils.waitForAlertExpiration(30000); // Wait for the 'Loading...' alert dialog to NOT be visible
		VoodooUtils.closeAlert();
		sugar.navbar.getControl("showAllModules").waitForVisible(30000);
		sugar.dashboard.getControl("headerPane").waitForVisible();
		VoodooUtils.pause(3000);
	}
	// TODO: Other login methods.
// To be implemented at a later date.
//	
//	public void login(UserRecord user) throws Exception {
//		
//	}
		
	public void login(String username, String password) throws Exception {
		if (sugar.navbar.userAction.getControl("userActions").queryVisible()){
			sugar.logout();
		}
		FieldSet userData = new FieldSet();
		userData.put("userName", username);
		userData.put("password", password);
		userData.put("emailAddress", "default@imb.com");
		userData.put("timeZone", "America/Los Angeles (GMT-8:00)");
		userData.put("lastName", "Default");
		userData.put("phone", "333333333");
		
		getControl("loginUserName").waitForVisible(300000);
		getControl("loginUserName").set(userData.get("userName"));
		getControl("loginPassword").set(userData.get("password"));
		getControl("login").click();
				
		VoodooUtils.waitForAlertExpiration(300000); // Wait for the 'Loading...' alert dialog to NOT be visible
		if(sugar.newUserWizard.getControl("nextButton").queryVisible()) {
			sugar.newUserWizard.setupNewUser(userData);
		}
		VoodooUtils.waitForAlertExpiration(300000); // Wait for the 'Loading...' alert dialog to NOT be visible
		VoodooUtils.closeAlert();
		sugar.navbar.getControl("showAllModules").waitForVisible(120000);
		sugar.dashboard.getControl("headerPane").waitForVisible();
		VoodooUtils.pause(3000);		
		VoodooUtils.username = username;
		VoodooUtils.password = password;
	}
	
	public void login(FieldSet userData) throws Exception {
		getControl("loginUserName").waitForVisible(300000);
		getControl("loginUserName").set(userData.get("userName"));
		getControl("loginPassword").set(userData.get("password"));
		getControl("login").click();
		
		VoodooUtils.waitForAlertExpiration(); // Wait for the 'Loading...' alert dialog to NOT be visible
		if(sugar.newUserWizard.getControl("nextButton").queryVisible()) {
			sugar.newUserWizard.setupNewUser(userData);
		}
		VoodooUtils.waitForAlertExpiration(); // Wait for the 'Loading...' alert dialog to NOT be visible
		VoodooUtils.closeAlert();
		sugar.navbar.getControl("showAllModules").waitForVisible(120000);
		sugar.dashboard.getControl("headerPane").waitForVisible();
		VoodooUtils.pause(3000);
		VoodooUtils.username = userData.get("userName");
		VoodooUtils.password = userData.get("password");
	}		
		
	/**
	 * Verify if any user is Logged in and Logout if yes.
	 * Set FieldSet data to be used in login
	 * 
	 * @param index - the number of row in data source LoginScreen.csv file
	 * @throws Exception
	 */
	public void loginActions(int index) throws Exception{
		FieldSet User = new FieldSet();
		if (sugar.navbar.userAction.getControl("userActions").queryVisible()){
			sugar.logout();
		}
		User.put("userName", ds.get(index).get("username"));
		User.put("password", ds.get(index).get("password"));
		User.put("emailAddress", ds.get(index).get("email"));
		User.put("timeZone", "America/Los Angeles (GMT-8:00)");		
		this.login(User);
		VoodooUtils.username = User.get("userName");
		VoodooUtils.password = User.get("password");
	}
}
