package com.sugarcrm.sugar.modules;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.SugarField;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.BWCRecord;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.sugar.records.StandardRecord;
import com.sugarcrm.sugar.records.UserRecord;
import com.sugarcrm.sugar.views.Menu;

/**
 * User module object which contains tasks associated with the User module like
 * create
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 */
public class UsersModule extends BWCModule {
	protected static UsersModule module;
	public FieldSet qaUser = new FieldSet();
	public FieldSet adminUser = new FieldSet();
	public final VoodooControl USER_TAB_PROFILE_EMAIL;
	public final VoodooControl USER_TAB_PROFILE_FIRST_NAME;
	public final VoodooControl USER_TAB_ADVANCED;
	public final VoodooControl USER_TAB_ADVANCED_DATE_FORMAT;
	public final VoodooControl USER_TAB_ADVANCED_TIME_FORMAT;
	public final VoodooControl USER_TAB_ADVANCED_INCLUDE_SOLUTATION;
	public final VoodooControl USER_SAVE;
	public final VoodooControl USER_LIST_BASIC_SEARCH_NAME;
	public final VoodooControl USER_LIST_SEARCH_BACIS_LINK;
	public final VoodooControl USER_LIST_SEARCH_ADVANSED_LINK;
	public final VoodooControl USER_LIST_SEARCH_BUTTON;
	public final VoodooControl USER_LIST_SEARCH_CLEAR_BUTTON;
	public final VoodooControl USER_LIST_FIRST_RECORD_LISTVIEW;

	public static UsersModule getInstance() throws Exception {
		if (module == null)
			module = new UsersModule();
		return module;
	}

	private UsersModule() throws Exception {
		moduleNameSingular = "User";
		moduleNamePlural = "Users";
		recordClassName = UserRecord.class.getName();

		// TODO: Load more fields into the HashMap
		loadFields();
		
		// Sugar7 UI Elements
		USER_TAB_PROFILE_EMAIL = new VoodooControl("input", "id", "Users0emailAddress0");
		USER_TAB_PROFILE_FIRST_NAME = new VoodooControl("input", "id", "first_name");
		USER_TAB_ADVANCED = new VoodooControl("a", "id", "tab4");
		USER_TAB_ADVANCED_DATE_FORMAT = new VoodooControl("select", "name", "dateformat");
		USER_TAB_ADVANCED_TIME_FORMAT = new VoodooControl("select", "name", "timeformat");
		USER_TAB_ADVANCED_INCLUDE_SOLUTATION = new VoodooControl("input", "name", "locale_name_salutation");
		USER_SAVE = new VoodooControl("input", "id", "Save");
		
		editView.addControl("passwordTab", "a", "id", "tab2");
		editView.addControl("profileTab", "a", "id", "tab1");
		editView.addControl("reportsTo", "input", "id", "reports_to_name");
		editView.addControl("reportsToSelect", "button", "id", "btn_reports_to_name");
		editView.addControl("save", "input", "id", "SAVE_HEADER");
		editView.addControl("confirmCreate", "a", "css", "#sugarMsgWindow .container-close");
		
		popupSearch.addControl("userName", "input", "id", "user_name_advanced");
		popupSearch.addControl("search", "input", "id", "search_form_submit");
		popupSearch.addControl("firstRecordPopupView", "a", "css", "table.list.view tr.oddListRowS1 td.oddListRowS1 a");
		
		listView.addControl("searchField", "input", "css", "#search_name_basic");
		listView.addControl("searchButton", "input", "css", "#search_form_submit");
		listView.addControl("clearButton", "input", "css", "#search_form_clear");
		listView.addControl("firstRecordListView", "a", "css", "#MassUpdate table.list.view td:nth-of-type(3) a");
		
		USER_LIST_BASIC_SEARCH_NAME = new VoodooControl("input", "id", "search_name_basic");
		USER_LIST_SEARCH_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Usersbasic_searchSearchForm']//input[@id='search_form_submit']");
		USER_LIST_SEARCH_CLEAR_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Usersbasic_searchSearchForm']//input[@id='search_form_clear']");
		USER_LIST_FIRST_RECORD_LISTVIEW = new VoodooControl("a", "css", "#MassUpdate table.list.view td:nth-of-type(3) a");
		USER_LIST_SEARCH_BACIS_LINK = new VoodooControl("input", "id", "basic_search_link");
		USER_LIST_SEARCH_ADVANSED_LINK = new VoodooControl("input", "id", "advanced_search_link");
		
		// TODO: Move this into a CSV file.
		qaUser.put("userName", "qauser");
		qaUser.put("password", "QAuser1");
		qaUser.put("emailAddress", "qa.sugar.qa@gmail.com");
		qaUser.put("newPassword", "QAuser1");
		qaUser.put("confirmPassword", "QAuser1");
		qaUser.put("firstName", "");
		qaUser.put("lastName", "qauser");
		qaUser.put("timeZone", "America/Los Angeles (GMT-8:00)");
				
		// Populate default data.
		for(String currentFieldName : fields.keySet()) {
			SugarField currentField = fields.get(currentFieldName);
			String defaultValue = currentField.get("default_value");

			if(defaultValue != null && !(defaultValue.isEmpty()))
				defaultData.put(currentFieldName, defaultValue);
		}
		
		// Users Module Menu Items
		menu = new Menu(this);
		menu.addControl("createNewUser", "a", "css", "li[data-module='Users'] ul[role='menu'] a:nth-of-type(1)");
		menu.addControl("createGroupUser", "a", "css", "li[data-module='Users'] ul[role='menu'] a:nth-of-type(2)");
		menu.addControl("createPortalApiUser", "a", "css", "li[data-module='Users'] ul[role='menu'] a:nth-of-type(3)");
		menu.addControl("reassignRecords", "a", "css", "li[data-module='Users'] ul[role='menu'] a:nth-of-type(4)");
		menu.addControl("importUsers", "a", "css", "li[data-module='Users'] ul[role='menu'] a:nth-of-type(5)");
	}

	/**
	 * Creates a single user record via the UI from the data in a FieldSet.
	 * This method overloads the BWCModule method with the same signature
	 * because the Users module has non-standard create/edit/detail views.
	 * 
	 * @param recordData	a FieldSet containing the data for the new record.
	 * @return	a Record object representing the record created
	 * @throws Exception
	 */
	public Record create(FieldSet userData) throws Exception {	
		VoodooUtils.voodoo.log.info("Reconciling user data.");
		// Merge default data and user-specified data.
		FieldSet recordData = getDefaultData();
		recordData.putAll(userData);

		VoodooUtils.voodoo.log.info("Creating a user via UI...");
		VoodooUtils.pause(1000);
		// Navigate to Users List View
		navToListView();
		
		// Start the Create process
		sugar.navbar.selectMenuItem(sugar.users, "createNewUser");
		
		// Find the elements in the fieldset and set their values
		VoodooUtils.focusFrame("bwc-frame"); //focus on iframe document to get access to elements in backwards compatible mode
		for(String controlName : recordData.keySet()) {			
			if(recordData.get(controlName) != null) {
				if(editView.getEditField(controlName) == null)
					continue;
				String toSet = recordData.get(controlName);
				if(controlName.equalsIgnoreCase("reportsTo")){
					//TODO: Update flow when user create is accessible withing the Sugar7 UI
					editView.getControl("reportsToSelect").click();
					VoodooUtils.pause(500);
					VoodooUtils.focusWindow(1); //focus on user search pop up
					popupSearch.getControl("userName").set(toSet);
					VoodooUtils.pause(300);
					popupSearch.getControl("search").click();
					VoodooUtils.pause(1000);
					popupSearch.getControl("firstRecordPopupView").click();
					VoodooUtils.pause(300);
					VoodooUtils.focusWindow(0); //focus on main browser tab
					VoodooUtils.focusFrame("bwc-frame"); //focus on iframe document to get access to elements in backwards compatible mode
				}
				else if(controlName.contains("Password")){ //Check element, if it has the word password in it, perform the following steps.
					editView.getControl("passwordTab").click();
					VoodooUtils.pause(300);
					VoodooUtils.voodoo.log.fine("Setting " + controlName + " to " + toSet);
					editView.getEditField(controlName).set(toSet);
					VoodooUtils.pause(300);
					editView.getControl("profileTab").click();
					VoodooUtils.pause(300);
				}
				else{
					VoodooUtils.voodoo.log.fine("Setting " + controlName + " to " + toSet);
					editView.getEditField(controlName).set(toSet);
				}
			}
		}
		VoodooUtils.pause(500);
		
		//Click Save
		editView.getControl("save").click();
		VoodooUtils.pause(2000);
		//Click on the Confirm close
		editView.getControl("confirmCreate").click();
		VoodooUtils.pause(500);
		VoodooUtils.focusDefault(); //focus back to default Sugar7 UI

		Record toReturn = (Record) Class.forName(UsersModule.module.recordClassName).getConstructor(FieldSet.class).newInstance(recordData);
		return toReturn;	
	}

	/**
	 * simple helper method to navigate to the user's listview
	 */
	public void navToListView() throws Exception {
		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame"); //focus on iframe document to get access to elements in backwards compatible mode
		sugar.admin.adminTools.getControl("userManagement").click();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(30000);
	}
	
	/**
	 * Method to return the data for qauser
	 * @return
	 * @throws Exception
	 */
	public FieldSet getQAUser() throws Exception {
		return qaUser.deepClone();
	}
	
}// end UsersModule