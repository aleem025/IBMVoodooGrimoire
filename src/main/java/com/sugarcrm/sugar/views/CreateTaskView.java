package com.sugarcrm.sugar.views;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.StandardModule;

public class CreateTaskView extends View{	
	protected static CreateTaskView taskCreateView;
	protected static AppModel sugar;

	public final VoodooControl TASK_NAME;
	public final VoodooControl PRIORITY;
	public final VoodooControl STATUS;
	public final VoodooControl TASK_TYPE;
	public final VoodooControl DESCRIPTION;
	
	public final VoodooControl DATE_DUE;
	public final VoodooControl HOURS_DUE;
	public final VoodooControl MINUTES_DUE;
	public final VoodooControl MERIDIEM_DUE;
	public final VoodooControl ASSSIGNED_USER_NAME;
	public final VoodooControl BTN_ASSIGNED_USER;
	public final VoodooControl BTN_CLEAR_ASSIGNED_USER;
	public final VoodooControl ADDITIONAL_ASSIGNEES;
	public final VoodooControl BTN_ADDITIONAL_ASSIGNEES;
	public final VoodooControl RELATED_TO_TYPE;
	public final VoodooControl RELATED_TO;
	public final VoodooControl BTN_RELATED_TO;
	public final VoodooControl BTN_CLEAR_RELATED_TO;
	public final VoodooControl ADD_ANOTHER_LINK;
	
	public final VoodooControl SUB_CREATE_BTN;
	public final VoodooControl SUB_TASK_NAME;
	public final VoodooControl SUB_SAVE_BTN;
	private DataSource ds;
	public FieldSet defaultTask;
	private FieldSet taskFull;
	
	/**
	 * Initializes the CreateTaskView screen.
	 * @throws Exception
	 */
	public CreateTaskView() throws Exception {
		super();
		sugar = AppModel.getInstance();
		ds = VoodooUtils.getData("CreateTaskView");
		defaultTask = (FieldSet) ds.get(0).clone();
		taskFull = new FieldSet();
		TASK_NAME = new VoodooControl("input", "id", "name");
		PRIORITY = new VoodooControl("select", "id", "priority");
		STATUS = new VoodooControl("select", "id", "status");
		TASK_TYPE = new VoodooControl("select", "id", "call_type");
		DESCRIPTION = new VoodooControl("textarea", "id", "description");
		
		DATE_DUE = new VoodooControl("input", "id", "date_due_date");
		HOURS_DUE = new VoodooControl("select", "id", "date_due_hours");
		MINUTES_DUE = new VoodooControl("select", "id", "date_due_minutes");
		MERIDIEM_DUE = new VoodooControl("select", "id", "date_due_meridiem");
		ASSSIGNED_USER_NAME = new VoodooControl("input", "id", "assigned_user_name");
		BTN_ASSIGNED_USER = new VoodooControl("button", "id", "btn_assigned_user_name");
		BTN_CLEAR_ASSIGNED_USER = new VoodooControl("button", "id", "btn_clr_assigned_user_name");
		ADDITIONAL_ASSIGNEES = new VoodooControl("input", "id", "additional_assignees_ac");
		BTN_ADDITIONAL_ASSIGNEES = new VoodooControl("button", "id", "btn_additional_assignees");
		RELATED_TO_TYPE = new VoodooControl("select", "id", "parent_type_EditView_1");
		RELATED_TO = new VoodooControl("input", "id", "related_to_c_1");
		BTN_RELATED_TO = new VoodooControl("button", "id", "btn_related_to_c_1");
		BTN_CLEAR_RELATED_TO = new VoodooControl("button", "id", "btn_clr_related_to_c_1");
		ADD_ANOTHER_LINK = new VoodooControl("a", "id", "EditView_related_to_c_add_another");
		
		SUB_CREATE_BTN = new VoodooControl("a","id", "Activities_createtask_button");
		SUB_TASK_NAME = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Tasks']//input[@id='name']");
		SUB_SAVE_BTN = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Tasks']//input[@id='Tasks_subpanel_save_button']");
	}
		
	public static CreateTaskView getInstance() throws Exception {
		if (taskCreateView == null) taskCreateView = new CreateTaskView();
		return taskCreateView;
	}	

	/**Method creates Task through UI with all fields filled using data from csv
	 * 
	 * @throws Exception
	 */
	public FieldSet createTaskLibFull() throws Exception{
		VoodooUtils.log.info("Running createTaskLibFull...");
		sugar.navbar.navToModule("Tasks");
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		taskFull.put("task_name", defaultTask.get("task_name") + timeStamp);
		VoodooUtils.switchToBWCFrame();
				
		TASK_NAME.assertVisible(true);
		TASK_NAME.set(taskFull.get("task_name"));
				
		PRIORITY.set(defaultTask.get("priority"));
		taskFull.put("priority", defaultTask.get("priority"));
		STATUS.set(defaultTask.get("status"));
		taskFull.put("status", defaultTask.get("status"));
		TASK_TYPE.set(defaultTask.get("task_type"));
		taskFull.put("task_type", defaultTask.get("task_type"));
		DESCRIPTION.set(defaultTask.get("description"));
		taskFull.put("description", defaultTask.get("description"));
		
		DATE_DUE.set(defaultTask.get("date_due"));
		taskFull.put("date_due", defaultTask.get("date_due"));
		HOURS_DUE.set(defaultTask.get("hours_due"));
		taskFull.put("hours_due", defaultTask.get("hours_due"));
		MINUTES_DUE.set(defaultTask.get("minutes_due"));
		taskFull.put("minutes_due", defaultTask.get("minutes_due"));
		MERIDIEM_DUE.set(defaultTask.get("meridiem_due"));
		taskFull.put("meridiem_due", defaultTask.get("meridiem_due"));
		BTN_CLEAR_ASSIGNED_USER.click();
		BTN_ASSIGNED_USER.click();
		
		sugar.tasks.searchForm.popupSelectRecord(defaultTask.get("assigned_user_name"));
		
		ASSSIGNED_USER_NAME.assertContains((defaultTask.get("assigned_user_name_full")), true);
		taskFull.put("assigned_user_name_full", defaultTask.get("assigned_user_name_full"));
		BTN_ADDITIONAL_ASSIGNEES.click();
		sugar.tasks.searchForm.popupSelectRecord(defaultTask.get("team_name"));
		
		ADDITIONAL_ASSIGNEES.assertContains((defaultTask.get("team_name_full")), true);
		taskFull.put("team_name_full", defaultTask.get("team_name_full"));
		sugar.tasks.editView.save();
		//Verify that Task saved successfully
		
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("span", "id", "name").assertContains(taskFull.get("task_name"), true);
		VoodooUtils.switchBackToWindow();
		VoodooUtils.log.info("CreateTaskLibFull finished.");
		return taskFull;
	}
	/**Method creates Task via sub panel on clients using data from csv
	 * 
	 * @throws Exception
	 */
	
	public FieldSet createTaskLibSub() throws Exception{
		VoodooUtils.log.info("Running createTaskLibSub...");
		SUB_CREATE_BTN.click();
		VoodooUtils.pause(2000);
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		taskFull.put("task_name", defaultTask.get("task_name") + timeStamp);
		VoodooUtils.switchBackToWindow();
		VoodooUtils.switchToBWCFrame();
		
		SUB_TASK_NAME.assertVisible(true);
		SUB_TASK_NAME.set(taskFull.get("task_name"));
				
		PRIORITY.set(defaultTask.get("priority"));
		taskFull.put("priority", defaultTask.get("priority"));
		STATUS.set(defaultTask.get("status"));
		taskFull.put("status", defaultTask.get("status"));
		TASK_TYPE.set(defaultTask.get("task_type"));
		taskFull.put("task_type", defaultTask.get("task_type"));
		DESCRIPTION.set(defaultTask.get("description"));
		taskFull.put("description", defaultTask.get("description"));
		
		DATE_DUE.set(defaultTask.get("date_due"));
		taskFull.put("date_due", defaultTask.get("date_due"));
		HOURS_DUE.set(defaultTask.get("hours_due"));
		taskFull.put("hours_due", defaultTask.get("hours_due"));
		MINUTES_DUE.set(defaultTask.get("minutes_due"));
		taskFull.put("minutes_due", defaultTask.get("minutes_due"));
		MERIDIEM_DUE.set(defaultTask.get("meridiem_due"));
		taskFull.put("meridiem_due", defaultTask.get("meridiem_due"));
		BTN_CLEAR_ASSIGNED_USER.click();
		BTN_ASSIGNED_USER.click();
		
		sugar.tasks.searchForm.popupSelectRecord(defaultTask.get("assigned_user_name"));
		ASSSIGNED_USER_NAME.assertContains(defaultTask.get("assigned_user_name_full"), true);
		taskFull.put("assigned_user_name_full", defaultTask.get("assigned_user_name_full"));
		
		SUB_SAVE_BTN.click();
		VoodooUtils.pause(20000);
		VoodooUtils.switchBackToWindow();
		sugar.tasks.assertRecord(taskFull.get("task_name"));
		
		VoodooUtils.log.info("CreateTaskLibSub finished.");
		return taskFull;
	}
}
