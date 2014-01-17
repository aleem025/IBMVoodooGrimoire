package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.MeetingRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Meetings module, such as field
 * data.
 * 
 * @author Ian Fleming <ifleming@sugarcrm.com>
 */
public class MeetingsModule extends BWCModule {
	protected static MeetingsModule module;

	public static MeetingsModule getInstance() throws Exception {
		if (module == null)
			module = new MeetingsModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * 
	 * @throws Exception
	 */
	private MeetingsModule() throws Exception {
		moduleNameSingular = "Meeting";
		moduleNamePlural = "Meetings";
		recordClassName = MeetingRecord.class.getName();

		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("subject");
		listView.addHeader("contact");
		listView.addHeader("related_to");
		listView.addHeader("start_date");
		listView.addHeader("assigned_user");
		listView.addHeader("date_created");

		// This will override the standard link identifiers in BWCListView as
		// the Meetings list view is different from the standard BWC list view
		for (int i = 1; i <= 99; i++) {
			// Build internal Voodoo names for each control in a row.
			String link = String.format("link%02d", i);
			// Build a string prefix that represents the current row in each
			// control.
			String currentRow = "table.list.view tbody tr:nth-of-type("
					+ (i + 2) + ")";
			// changed type index from 4 to 5 for Meetings
			listView.addControl(link, "a", "css", currentRow
					+ " td:nth-of-type(5) a");
		}

		// Relate Widget access
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("teamName", "Teams");

		// Campaigns Subpanel
		subpanel = new Subpanel(this);

		// Campaigns Module Menu items
		menu = new Menu(this);
		// This is a dup of Classic, needed for standard code to work.
		menu.addControl("createMeeting", "a", "css",
				"[data-module='Meetings'] [data-navbar-menu-item='LNK_NEW_MEETING']");
		menu.addControl("viewMeetings", "a", "css",
				"[data-navbar-menu-item='LNK_MEETING_LIST']");
		menu.addControl("importMeetings", "a", "css",
				"[data-navbar-menu-item='LNK_IMPORT_MEETINGS']");
	}

	/**
	 * Perform setup which depends on other modules already being constructed.
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Campaigns...");

		// Related Subpanels
		relatedModulesMany.put("call_contacts", sugar.contacts);
		relatedModulesMany.put("call_leads", sugar.leads);
		relatedModulesMany.put("call_users", sugar.users);

		// Add Subpanels
		detailView.addSubpanels();

		// Account Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();
	}
} // MeetingsModule
