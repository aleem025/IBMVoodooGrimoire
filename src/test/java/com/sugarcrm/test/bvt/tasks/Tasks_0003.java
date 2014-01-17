package com.sugarcrm.test.bvt.tasks;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.views.CreateCallView;
import com.sugarcrm.sugar.views.CreateTaskView;

public class Tasks_0003 extends SugarTest {
	FieldSet task;
	FieldSet taskEdited;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
		task = sugar.taskCreateView.createTaskLibFull();
	}

	/** Edits created task via pencil icon on List View
	 *  Verifying that all fields edited correctly
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		taskEdited = (FieldSet) testData.get(testName).get(0).clone();
		taskEdited.put("task_name", taskEdited.get("task_name") + VoodooUtils.getCurrentTimeStamp("MMddHHmmss"));
		
		sugar.tasks.navToListView();
		VoodooUtils.switchToBWCFrame();
		sugar.tasks.listView.clickPencilButton(task.get("task_name"));
		
		VoodooUtils.switchToBWCFrame();
		sugar.taskCreateView.TASK_NAME.set(taskEdited.get("task_name"));
		sugar.taskCreateView.STATUS.set(taskEdited.get("status"));
		sugar.taskCreateView.DATE_DUE.set(taskEdited.get("date_due"));
		sugar.taskCreateView.HOURS_DUE.set(taskEdited.get("hours_due"));
		sugar.taskCreateView.MINUTES_DUE.set(taskEdited.get("minutes_due"));
		sugar.taskCreateView.MERIDIEM_DUE.set(taskEdited.get("meridiem_due"));
		sugar.taskCreateView.DESCRIPTION.set(taskEdited.get("description"));
		sugar.taskCreateView.PRIORITY.set(taskEdited.get("priority"));
		
		sugar.taskCreateView.BTN_CLEAR_ASSIGNED_USER.click();
		sugar.taskCreateView.BTN_ASSIGNED_USER.click();
		sugar.tasks.searchForm.popupSelectRecord(taskEdited.get("assigned_user_name"));
		sugar.taskCreateView.ASSSIGNED_USER_NAME.assertContains(taskEdited.get("assigned_user_name_full"), true);
			
		sugar.taskCreateView.BTN_ADDITIONAL_ASSIGNEES.click();
		sugar.tasks.searchForm.popupSelectRecord(taskEdited.get("team_name"));
		sugar.taskCreateView.ADDITIONAL_ASSIGNEES.assertContains(taskEdited.get("team_name_full"), true);
		sugar.tasks.editView.save();
		
		sugar.tasks.navToRecord(taskEdited.get("task_name"));
		
		sugar.tasks.verifyDetailView(taskEdited);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.tasks.deleteRecord(taskEdited.get("task_name"));
		sugar.logout();
	}
}