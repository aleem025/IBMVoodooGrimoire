package wip;

import java.util.ArrayList;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.automation.VInterface;

/**
 * Contact module object which contains tasks associated with the Contact module
 * like create/deleteAll
 * @author mlouis
 *
 */
public class RolesModule {
	protected static AppModel sugar;
	protected static RolesModule roles;
	protected static DataSource defaultData = new DataSource();
	public View editView, listView, detailView;
	public Api api;
	
	public static RolesModule getInstance() throws Exception {
		if (roles == null) roles = new RolesModule();
		return roles;
	}

	private RolesModule() {
		try {
			sugar = AppModel.getInstance();
			editView = new View();
			listView = new View();
			detailView = new View();

			// TODO: Replace with loading data from disk.
			listView.addControl("rolesManagement", "a", "id", "roles_management");
			listView.addControl("clearButton", "input", "id", "search_form_clear");
			listView.addControl("searchButton", "input", "id", "search_form_submit");
			
			editView.addControl("roleName", "input", "id", "name");
			editView.addControl("description", "textarea", "name", "description");
			editView.addControl("saveRole", "input", "id", "save_button");
			
			detailView.addControl("saveHeader", "input", "id", "SAVE_HEADER");
			detailView.addControl("selectUserButton", "a", "id", "acl_roles_users_select_button");
			
			//TODO: Load more fields into the HashMap
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Groups together API methods for convenient access.
	 * @author dsafar, mlouis
	 */
	public class Api{
		private Api() {}
		public RoleRecord create(){
			//TODO: This is temporary, make this method use the API create
			try {
				return roles.create();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public ArrayList<RoleRecord> create(DataSource rolesData) throws Exception {
			return roles.create(rolesData);
			
		}
		
		public void delete(RoleRecord role) throws Exception{
			//TODO: Need to implement logic to delete a passed in Role. For now this will pass thru to the non-api role delete
			roles.deleteAll();
		}
	}
	
	/**
	 * Create a single Role record via the UI using default data. 
	 * @return an RoleRecord object representing the Role created
	 * @throws Exception
	 * @author dsafar, mlouis
	 */
	public RoleRecord create() throws Exception {
		VoodooUtils.voodoo.log.info("Loading default data...");
		return create(defaultData.get(0));		
	} // end create()
	
	/**
	 * Create a single Role record via the UI from the data in a FieldSet. 
	 * @param accountData
	 * @return an RoleRecord object representing the Role created
	 * @throws Exception
	 * @author dsafar, mlouis
	 */
	public RoleRecord create(FieldSet roleData) throws Exception {
		VoodooUtils.voodoo.log.info("Reconciling record data.");
		// Merge default data and user-specified data.
		FieldSet recordData = defaultData.get(0);
		recordData.putAll(roleData);
		/*

		VoodooUtils.voodoo.log.info("Creating an role via UI...");
		//Navigate to the Admin Tools
		sugar.navbar.selectAction("globalLinksModule", "admin_link");
		
		//Click on Roles Management
		sugar.roles.listView.getControl("rolesManagement").click();
		
		//Create new Role
		sugar.navbar.selectAction("moduleTab_AllACLRoles", "CreateRoleAll");

		for(String controlName : recordData.keySet()) // iterate over fields.
		{
			if(recordData.get(controlName) != null) // allow 
			{
				sugar.roles.editView.getControl(controlName).set(recordData.get(controlName));
			}
		}
		// Locate the Save button and click it
		sugar.roles.editView.getControl("saveRole").click();

		// Verify
		//Assert.assertTrue("Unsuccessful in creating account.", VoodooUtils.voodoo.getText(VAutomation.Strategy.ID,  "name").equals("SugarCRM Inc."));

		VoodooUtils.voodoo.log.info("Role created.");
		*/
		return new RoleRecord(recordData);		
	}
	
	/**
	 * Create multiple Role records via the UI from the data in a DataSource. 
	 * @param roleData
	 * @return
	 * @throws Exception
	 */
	public ArrayList<RoleRecord> create(DataSource roleData) throws Exception {
		VoodooUtils.voodoo.log.info("Creating " + roleData.size() + "roles...");

		ArrayList<RoleRecord> results = new ArrayList<RoleRecord>();
		
		for(FieldSet roleRecord : roleData) // iterate over records
		{
			results.add(create(roleRecord));
		}
		
		VoodooUtils.voodoo.log.info(roleData.size() + "roles created.");
		return results;
	}
	
	/**
	 * deleteAll() will delete the non Out-Of-The-Box Roles created
	 */
	public void deleteAll() throws Exception {
		/*
		//Navigate to the Admin Tools
		sugar.navbar.selectAction("globalLinksModule", "admin_link");
				
		//Click on Roles Management
		sugar.roles.listView.getControl("rolesManagement").click();
		
		//Clear the search filters
		sugar.roles.listView.getControl("clearButton").click();
		VoodooUtils.pause(800);
		
		//Enter in the name of the Report to search for
		sugar.roles.listView.getControl("roleName").set("QA_Created");
		VoodooUtils.pause(800);
		
		//Search for the Report
		sugar.roles.listView.getControl("searchButton").click();

		//Click the down arrow to expand the list of Select options
		sugar.reports.listView.getControl("sugarActionButton").click();
		VoodooUtils.pause(800);
		
		//Click Select All
		sugar.roles.listView.getControl("buttonSelectAllTop").click();

		//Click on Delete
		sugar.roles.listView.getControl("deleteListviewTop").click();

		//Accept the alert pop-up
		VoodooUtils.acceptDialog();
		*/
	}//end deleteAll()
}//end RolesModule