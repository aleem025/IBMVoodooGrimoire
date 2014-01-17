package com.sugarcrm.test.tasks;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.TaskRecord;
import com.sugarcrm.test.SugarTest;

public class Tasks_create extends SugarTest {
	
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		TaskRecord myTask = (TaskRecord)sugar.tasks.create();
		myTask.verify();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.tasks.api.deleteAll();
		sugar.logout();
	}
}
