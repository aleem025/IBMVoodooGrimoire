package wip;

import java.util.ArrayList;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.StandardRecord;

public class ProjectRecord extends StandardRecord {
	String projectName, startDate, endDate;
	ArrayList<ProjectTask> projectTasks = new ArrayList<ProjectTask>(); 
	protected static FieldSet defaultProjectTaskData = new FieldSet();
	
	public ProjectRecord(FieldSet data) throws Exception {
		super(data);
		projectName = data.get("name");
		startDate = data.get("startDate");
		endDate = data.get("endDate");
		defaultProjectTaskData.put("projectTaskName", "Project Review");
	}
	
	class ProjectTask {
		String name;
		protected ProjectTask(FieldSet taskData) {
			name = taskData.get("name");
		}
	}

	/**
	 * Create a single project task via the UI using default data. 
	 * @return a Record object representing the record created
	 * @throws Exception
	 * @author dsafar
	 */
	public void initProjectTasks() throws Exception {
		/*
		sugar.projects.detailView.getControl("viewGantt").click();

		for(int currentTask = 1; currentTask <= 2; currentTask++)
		{
			sugar.projects.GanttView.getControl("taskNameRow" + currentTask + "DoubleClick").doubleClick();
			VoodooUtils.pause(2000);
			
			sugar.projects.GanttView.getControl("taskNameRow" + currentTask).set(defaultProjectTaskData.get("projectTaskName"));
			VoodooUtils.pause(2000);
	
			sugar.projects.GanttView.getControl("taskRow" + currentTask + "Duration").set("Hours");
		}
		
		sugar.projects.GanttView.getControl("saveTask").click();
		VoodooUtils.pause(2000);
		*/
	}
	

	/**
	 * Create a single project task via the UI using default data. 
	 * @return a Record object representing the record created
	 * @throws Exception
	 * @author dsafar
	 */
	public ProjectTask createProjectTask() throws Exception {
		VoodooUtils.voodoo.log.info("Loading default data...");
		return createProjectTask(defaultProjectTaskData);		
	}

	/**
	 * Create a single project task via the UI using the supplied data.
	 * @param projectTaskData
	 * @throws Exception
	 * @author mlouis, dsafar
	 */
	public ProjectTask createProjectTask(FieldSet projectTaskData) throws Exception {
		/*
		// TODO: Support more than just name by iterating the FieldSet.
		int currentTask = projectTasks.size() + 1;
		sugar.projects.detailView.getControl("viewGantt").click();
		sugar.projects.GanttView.getControl("taskNameRow" + currentTask + "DoubleClick").doubleClick();
		VoodooUtils.pause(2000);
		sugar.projects.GanttView.getControl("taskNameRow" + currentTask).set(projectTaskData.get("projectTaskName"));
		VoodooUtils.pause(2000);
		sugar.projects.GanttView.getControl("taskRow" + currentTask + "Duration").set("Hours");
		sugar.projects.GanttView.getControl("saveTask").click();
		VoodooUtils.pause(2000);
		// TODO: Support more than just name by iterating the FieldSet (see Record for an example).
		*/
		return new ProjectTask(projectTaskData);
	}


	/**
	 * Create multiple project tasks via the UI from the data in a DataSource. 
	 * @param data
	 * @return an ArrayList of Records representing the accounts created.
	 * @throws Exception
	 * @author dsafar
	 */

	public void createProjectTasks(DataSource data) throws Exception {
		VoodooUtils.voodoo.log.info("Creating " + data.size() + " project tasks...");
		for(FieldSet record : data) {
			projectTasks.add(createProjectTask(record));
		}
		VoodooUtils.voodoo.log.info(data.size() + "project tasks created.");
	}
}