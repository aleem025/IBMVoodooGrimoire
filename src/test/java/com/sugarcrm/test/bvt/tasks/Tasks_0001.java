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
public class Tasks_0001 extends SugarTest {
	FieldSet task;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
	}

	/** Creating new task through full form 
	 *  Open it in detail view and verify if all task info saved correctly
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		task = sugar.taskCreateView.createTaskLibFull();
		sugar.tasks.navToRecord(task.get("task_name"));						
		sugar.tasks.verifyDetailView(task);
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.tasks.deleteRecord(task.get("task_name"));
		sugar.logout();
	}
}