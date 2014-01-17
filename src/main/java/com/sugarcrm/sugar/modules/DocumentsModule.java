package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.DocumentRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Documents module, such as field
 * data.
 * 
 * @author Ian Fleming <ifleming@sugarcrm.com> 
 */
public class DocumentsModule extends BWCModule {
	protected static DocumentsModule module;
	
	public static DocumentsModule getInstance() throws Exception {
		if(module == null) 
			module = new DocumentsModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * @throws Exception
	 */
	private DocumentsModule() throws Exception {
		moduleNameSingular = "Document";
		moduleNamePlural = "Documents";
		recordClassName = DocumentRecord.class.getName();
		
		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("document");
		listView.addHeader("status");
		listView.addHeader("type");
		listView.addHeader("end_date");
		listView.addHeader("user");
		listView.addHeader("date_created");

		// Relate Widget access
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("teamName", "Teams");
	
		// Documents Subpanel
		subpanel = new Subpanel(this);
		
		// Documents Module Menu items
		menu = new Menu(this);
		// This is a dup of Classic, needed for standard code to work.
		menu.addControl("createDocument", "a", "css", "[data-navbar-menu-item='LNK_NEW_DOCUMENT']");
		menu.addControl("viewDocuments", "a", "css", "[data-navbar-menu-item='LNK_DOCUMENT_LIST']");
	}

	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Documents...");

		// Related Subpanels
		relatedModulesMany.put("document_accounts", sugar.accounts);
		relatedModulesMany.put("document_contacts", sugar.contacts);
		relatedModulesMany.put("document_opportunities", sugar.opportunities);
		
		// Add Subpanels
		detailView.addSubpanels();
		
		// Account Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();		
	}
} // DocumentsModule
