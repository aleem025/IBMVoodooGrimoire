package wip;

import com.sugarcrm.sugar.modules.BWCModule;
import com.sugarcrm.sugar.views.View;

/**
 * Project module object which contains tasks associated with the Project module
 * like create/deleteAll
 * @author 
 *
 */
public class ProjectsModule extends BWCModule {
	protected static ProjectsModule module;
	public View GanttView;
	public Api api;
	
	public static ProjectsModule getInstance() throws Exception {
		if (module == null) module = new ProjectsModule();
		return module;
	}

	private ProjectsModule() throws Exception {
		moduleNameSingular = "Project";
		recordClassName = ProjectRecord.class.getName();
		//api = new Api();
		GanttView = new View();

		// TODO: Replace with loading data from disk.
		editView.addControl("name", "input", "id", "name");
		editView.addControl("startDate", "input", "id", "estimated_start_date");
		editView.addControl("endDate", "input", "id", "estimated_end_date");
		editView.addControl("saveFooter", "input", "id", "SAVE_FOOTER");
		
		detailView.addControl("editButton", "a", "id", "edit_button");
		detailView.addControl("viewGantt", "a", "id", "project_task_submit_button");
		
		listView.addControl("sugarActionButton", "span", "css", "ul#selectLinkTop li.sugar_action_button span");
		listView.addControl("buttonSelectAllTop", "a", "id", "button_select_all_top");
		listView.addControl("deleteListviewTop", "a", "id", "delete_listview_top");
		
		GanttView.addControl("taskNameRow1DoubleClick", "td", "css", "#project_task_row_1 td:nth-of-type(3)");
		GanttView.addControl("taskRow1Duration", "select", "id", "duration_unit_1");
		GanttView.addControl("taskNameRow1", "input", "id", "description_1");
		GanttView.addControl("taskNameRow2DoubleClick", "td", "css", "#project_task_row_2 td:nth-of-type(3)");
		GanttView.addControl("taskRow2Duration", "select", "id", "duration_unit_2");
		GanttView.addControl("taskNameRow2", "input", "id", "description_2");
		GanttView.addControl("saveTask", "a", "id", "saveGridLink");

		//Load default data
		// TODO: Replace with loading data from disk.

		defaultData.put("name", "Voodoo 2");
		defaultData.put("startDate", "11/13/2012");
		defaultData.put("endDate", "11/13/2020");

	}
} // end Projects