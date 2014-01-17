package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.NoteRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Notes module, such as field data.
 * 
 * @author Jessica Cho
 */
public class NotesModule extends StandardModule {
	protected static NotesModule module;
	public final VoodooControl SEARCH_NAME;
	public final VoodooControl SEARCH_EMAIL_ADVANCED;
	public final VoodooControl SEARCH_LOTUS_NOTES;
	public final VoodooControl SEARCH_FORM_SUBMIT;
	public final VoodooControl SEARCH_FORM_CLEAR;

	public static NotesModule getInstance() throws Exception {
		if (module == null) module = new NotesModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * @throws Exception
	 */
	private NotesModule() throws Exception {
		moduleNameSingular = "Note";
		moduleNamePlural = "Notes";
		recordClassName = NoteRecord.class.getName();
		api = new Api();
		
		//Fields and Buttons of Search Form
		SEARCH_NAME = new VoodooControl("input", "id", "search_name_advanced");
		SEARCH_EMAIL_ADVANCED = new VoodooControl("input", "id", "email_advanced"); 
		SEARCH_LOTUS_NOTES = new VoodooControl("input", "id", "lotus_notes_id_advanced");
		SEARCH_FORM_SUBMIT = new VoodooControl("input", "id", "search_form_submit");
		SEARCH_FORM_CLEAR = new VoodooControl("input", "id", "search_form_clear");

		//Load Tasks Module element definitions from CSV
		loadFields();
		
		// Define the columns on the ListView. This name is a reference of the data-fieldname attribute of the element.
		listView.addHeader("name");
		listView.addHeader("contact_name");
		listView.addHeader("parent_name");
		listView.addHeader("filename");
		listView.addHeader("created_by_name");	
		
		// Related to fields
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("contactName", "Contacts");
		relatedModulesOne.put("teamName", "Teams");
		
		// Notes Module Menu items
		menu = new Menu(this);
		menu.addControl("createNote", "a", "css", "li[data-module='Notes'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_NOTE']");
		menu.addControl("viewNotes", "a", "css", "li[data-module='Notes'] ul[role='menu'] a[data-navbar-menu-item='LNK_NOTE_LIST']");
		menu.addControl("importNotes", "a", "css", "li[data-module='Notes'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_NOTES']");
	}
	
	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 * @throws Exception 
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Notes...");

		// TODO: There is no subpanel in notes module right now.  
		// Other modules have notes subpanel (7.1.5 or above)
		
		// Notes Mass Update Panel
		massUpdate = new MassUpdate(this);
	}
} // end NotesModule
