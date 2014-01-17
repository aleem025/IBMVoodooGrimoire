package com.sugarcrm.test.tasks;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.TaskRecord;

public class Tasks_update extends SugarTest {
	TaskRecord myTask;

	public void setup() throws Exception {
		sugar.login();
		//TODO: VOOD-549 - 
		// Comment this because api create does not input the start/due date and time.
		// Use the ui create to work around it for now.
		// myTask = (TaskRecord)sugar.tasks.api.create();
		myTask = (TaskRecord)sugar.tasks.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		FieldSet newData = new FieldSet();
		newData.put("dueDate", "10/01/2023");
		newData.put("status", "In Progress");

		// Edit the task using the UI.
		myTask.edit(newData);
		
		// Verify the task was edited.
		myTask.verify(newData);
		
		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.tasks.api.deleteAll();
		sugar.logout();
	}
}
