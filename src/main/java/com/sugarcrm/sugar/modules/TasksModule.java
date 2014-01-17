package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.TaskRecord;

/**
 * Task module object exposing controls and tasks for the Tasks module
 * 
 * @author Jessica Cho
 */
public class TasksModule extends StandardModule {
	protected static TasksModule module;
	public final VoodooControl SEARCH_NAME;
	public final VoodooControl SEARCH_EMAIL_ADVANCED;
	public final VoodooControl SEARCH_LOTUS_NOTES;
	public final VoodooControl SEARCH_FORM_SUBMIT;
	public final VoodooControl SEARCH_FORM_CLEAR;

	public static TasksModule getInstance() throws Exception {
		if (module == null) module = new TasksModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * @throws Exception
	 */
	private TasksModule() throws Exception {
		moduleNameSingular = "Task";
		moduleNamePlural = "Tasks";
		recordClassName = TaskRecord.class.getName();
		
		//Fields and Buttons of Search Form
		SEARCH_NAME = new VoodooControl("input", "id", "search_name_advanced");
		SEARCH_EMAIL_ADVANCED = new VoodooControl("input", "id", "email_advanced"); 
		SEARCH_LOTUS_NOTES = new VoodooControl("input", "id", "lotus_notes_id_advanced");
		SEARCH_FORM_SUBMIT = new VoodooControl("input", "id", "search_form_submit");
		SEARCH_FORM_CLEAR = new VoodooControl("input", "id", "search_form_clear");
		
		
		//Load Tasks Module element definitions from CSV
		loadFields();
		
		// Define the columns on the ListView.
		listView.addHeader("name");
		listView.addHeader("contact_name");
		listView.addHeader("parent_name");
		listView.addHeader("date_due");
		listView.addHeader("time_due");
		listView.addHeader("team_name");
		listView.addHeader("date_start");
		listView.addHeader("status");
		listView.addHeader("assigned_user_name");
		listView.addHeader("date_entered");
	}
	
	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() {
		VoodooUtils.voodoo.log.info("Init Tasks...");
		// TODO: https://sugarcrm.atlassian.net/browse/VOOD-564
	}
} // end TasksModule
