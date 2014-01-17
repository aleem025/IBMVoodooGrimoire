package com.sugarcrm.test.tasks;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.TaskRecord;
import com.sugarcrm.test.SugarTest; 


import com.sugarcrm.candybean.datasource.FieldSet;

public class Tasks_17049 extends SugarTest {
	FieldSet firstTasks;
	FieldSet secondTasks;
	
	@Override
	public void setup() throws Exception {	
		firstTasks = testData.get("Tasks_17049").get(0);
		secondTasks = testData.get("Tasks_17049").get(1);
		sugar.login();					
	}

	/**
	 * Should hide close action of a task record on list view if the action not applicable for the record.
	 * @throws Exception
	 */	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "..."); 
		
		TaskRecord MyTask1 = (TaskRecord)sugar.tasks.api.create(firstTasks);
		TaskRecord MyTask2 = (TaskRecord)sugar.tasks.api.create(secondTasks);	
		
		sugar.tasks.navToListView();	
	
		// TODO: VOOD-596.  If it is fixed, please update the following steps. 
		//first task is Completed status
		sugar.tasks.listView.openRowActionDropdown(2);
		new VoodooControl("ul", "css", ".dataTable tbody tr:nth-of-type(2) .dropdown-menu").assertContains("Close", false);
		
		//second task is Deferred
		sugar.tasks.listView.openRowActionDropdown(1);
		new VoodooControl("ul", "css", ".dataTable tbody tr:nth-of-type(1) .dropdown-menu").assertContains("Close", true);
		
		VoodooUtils.voodoo.log.info(testName + "complete."); 
	}

	@Override
	public void cleanup() throws Exception {
		sugar.tasks.deleteAll();
		sugar.logout();
	}
}
