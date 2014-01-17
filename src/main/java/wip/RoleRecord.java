package wip;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.sugar.records.UserRecord;
import com.sugarcrm.sugar.VoodooUtils;

public class RoleRecord {
	protected static AppModel sugar;
	protected static RoleRecord roles;
	protected View listView;
	public String name, assignedTo;
	
	public RoleRecord() {}
	
	public RoleRecord(FieldSet data){
		try {
			sugar = AppModel.getInstance();
			name = data.get("name");
			assignedTo = data.get("assignedTo");
			listView.addControl("rolesManagement", "a", "id", "roles_management");
			listView.addControl("roleName", "a", "value", name);
			listView.addControl("searchName", "input", "id", "name_basic");
			listView.addControl("searchButton", "input", "id", "search_form_submit");
			listView.addControl("sugarActionButton", "span", "css", "ul#selectLinkTop li.sugar_action_button span");
			listView.addControl("buttonSelectAllTop", "a", "id", "button_select_all_top");
			listView.addControl("deleteListviewTop", "a", "id", "delete_listview_top");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * relateTo will relate the current Role record to a passed in User record
	 * @param user
	 */
	public void relateToUser(UserRecord user){
		//Check to see if we are at the Role Detail View
		//If not on the proper Role Detail View, navigate to it
		//When on the Role Detail View, click on the Select button for User's
		//On the pop-up, search for the proper user by name and select it
		//Done
	}
	
	/**
	 * disableModules will disable access to the modules in the passed in FieldSet
	 * @param disable - FieldSet of modules to disable
	 * @throws Exception
	 */
	public void disableModules(FieldSet disable) throws Exception{
		/*
		//TODO:
		//Navigate to Role to disable access to modules from FieldSet
		sugar.navbar.selectAction("globalLinksModule", "admin_link");
		sugar.roles.listView.getControl("rolesManagement").click();
		
		//Search for Role
		sugar.roles.listView.getControl("searchName").set(name);
		sugar.roles.listView.getControl("searchButton").click();
		VoodooUtils.pause(800);
		
		//Click on Role to get to DetailView
		sugar.roles.listView.getControl("roleName").click();
		VoodooUtils.pause(800);
		
		//Set Access to the modules in FieldSet to "Disabled"
		for(int i = 1; i <= Integer.parseInt(disable.get("num_modules")) ; i++){
			//TODO: This part is the crucial step which requires making changes to the ACL grid's hidden select elements!!!
			sugar.roles.detailView.getControl("module_" + i).set("Disabled");
			VoodooUtils.pause(500);
		}
		
		//Save changes to Role record
		sugar.roles.detailView.getControl("saveHeader").click();
		VoodooUtils.pause(800);
		*/
	}
	
	/**
	 * This function will delete the Role Record via the UI
	 * @throws Exception
	 * @author mlouis
	 */
	public void delete() throws Exception{
		//Navigate to Role Management ListView
		//TODO: Need to check where we are, if on the DetailView of a role then we simply delete role, if we are on the ListView 
		//		then we search for the role to delete by name, and delete it, if we are not in Roles Management, then we have to navigate
		//		there and delete the role.
		/*
		sugar.navbar.selectAction("globalLinksModule", "admin_link");
		sugar.roles.listView.getControl("rolesManagement").click();
		//Search for Role
		sugar.roles.listView.getControl("searchName").set(name);
		sugar.roles.listView.getControl("searchButton").click();
		VoodooUtils.pause(800);
		//Select All
		sugar.roles.listView.getControl("sugarActionButton").click();
		VoodooUtils.pause(800);
		sugar.roles.listView.getControl("buttonSelectAllTop").click();
		VoodooUtils.pause(800);
		//Delete
		sugar.roles.listView.getControl("deleteListviewTop").click();
		// Accept the alert pop-up
		VoodooUtils.acceptDialog();
		VoodooUtils.pause(800);
	*/
	}//end delete()
	
}//end RoleRecord
