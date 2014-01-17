package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import wip.RoleRecord;
import com.sugarcrm.sugar.records.UserRecord;
import com.sugarcrm.candybean.datasource.FieldSet;

public class Roles_0595 extends SugarTest {
	private UserRecord myUser;
	private RoleRecord myRole;

	public void setup() throws Exception {
		sugar.login();

		// Create a standard User.
		//myUser = sugar.users.api.create(); 

		// Create a default Role.
		//myRole = sugar.roles.api.create(); 

		// Relate the Role to the User.
//		myUser.api.relateRole(myRole);
//		myUser.relateToRole(myRole);
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		// Populate Modules to be disabled.
		FieldSet roleModuleOptions = new FieldSet();
		roleModuleOptions.put("role", myRole.name); 
		roleModuleOptions.put("num_modules", "2");
		roleModuleOptions.put("module_1", "Opportunities"); 
		roleModuleOptions.put("module_2", "Contacts"); 

		myRole.disableModules(roleModuleOptions); 

		// Need to log out as admin and log in as the myUser.name
		sugar.logout();
		myUser.login();

		// Verify the disabled modules, the same FieldSet that disabled the modules can be used 		
//		sugar.Roles.verifyDisabledModules(roleModuleOptions);

		// Log back in as admin and verify the modules are enabled
		sugar.logout();
		sugar.login();

		// The same FieldSet that disabled the modules for myUser can be used for verification
//		sugar.Roles.verifyEnabledModules(roleModuleOptions);
		
//		2. Go to "Admin -> Role Management" page.
//		3. Create a new role, disable some modules in it, such as Opportunities module and Leads module.
//		4. Assign this role to a new user.
//		3. Create a new role, disable some modules in it, such as Opportunities module and Leads module.
//		4. Assign this role to a new user.
//		5. Logout and login as the new user.
//		  5. The Opportunities module and Leads module are disabled. Input the url: sugarinstance/index.php?module=Opportunities&action=index, show the following message on page: You do not have access to this area. Contact your site administrator to obtain access. Show the same result when you input the leads module's url
//		6. Goto the Access tab of the user account's detail view page .
//		  6. The Opportunities module and Leads module not shown in the module access table.
//		7.Logout and login as admin user.
//		  7. The Opportunities module and Leads module are displayed in navigation bar.
	}

	public void cleanup() throws Exception {
//		sugar.users.api.delete(myUser); 
	//	sugar.roles.api.delete(myRole);
		sugar.logout();
	}
}
