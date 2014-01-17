package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import wip.WorkFlowRecord;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;

public class Workflow_8146 extends SugarTest {
	WorkFlowRecord myWorkflow;
	AccountRecord myAccount;

	public void setup() throws Exception {
		sugar.login();
		
		// Create Account
		myAccount = (AccountRecord)sugar.accounts.create();
		
		VoodooUtils.voodoo.log.info("Loading Data for workflow...");
		FieldSet workflow = new FieldSet();
		workflow.put("name", "Workflow_8146_Test");
		workflow.put("module", "Accounts");
		workflow.put("executionCondition", "type0");
		workflow.put("fieldSelection", "Name");
		workflow.put("futureValue", "1");
		workflow.put("actionType", "action_type0");
		workflow.put("numModifyField", "2");
		workflow.put("modifyField_1", "Name");
		workflow.put("modifyField_2", "Description");
		workflow.put("modifyFieldValue_1", "Edited Name");
		workflow.put("modifyFieldValue_2", "This is a description");
		
		DataSource workflowOptions = new DataSource();
		workflowOptions.add(workflow);
		VoodooUtils.voodoo.log.info("Workflow Data Loaded");
		
		//Create Workflow using passed in values
		//myWorkflow = sugar.workflow.create(workflowOptions);
		
		VoodooUtils.voodoo.log.info("Workflow Created");
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
//		1. Create workflow for each module as target module as follows:
//		'Execution Occurs' is 'When record saved'
//		'Applies to' is 'New and Existing Records'.
//		2. Set Condition as 'When a field in the target module change to or from a specified value' to specified value.
//		3. Click the condition created in step 2 and edit it to update the condition, such as update the condition as another field and another value.
//		4. Set Action for the workflow, such as "Create a record associated with a module related to the target module".
//		5. Trigger the workflow.
//		
//		5. The action mentioned in step 4 is triggered.
		
		//Load new values for Account
		FieldSet accountData = new FieldSet();
		accountData.put("name", "Edited Account Name");
		myAccount = (AccountRecord)sugar.accounts.create(accountData);
		
		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		//sugar.workflow.deleteAll();
		sugar.accounts.deleteAll();
		sugar.logout();
	}
}