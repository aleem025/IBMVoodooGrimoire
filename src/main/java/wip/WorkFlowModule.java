package wip;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.automation.VInterface;


/**
 * Workflow module object which contains tasks associated with the Workflow
 * module like create/deleteAll
 * 
 * @author
 * 
 */
public class WorkFlowModule {
	protected static AppModel sugar;
	protected static WorkFlowModule workflow;
	protected static DataSource workflowOptions = new DataSource();
	protected View editView, detailView;

	public static WorkFlowModule getInstance() throws Exception {
		if (workflow == null)
			workflow = new WorkFlowModule();
		return workflow;
	}

	private WorkFlowModule() {
		try {
			sugar = AppModel.getInstance();
			editView = new View();
			detailView = new View();

			// TODO: Replace with loading data from disk.
			editView.addControl("workflow_management", "a", "id", "workflow_management");
			editView.addControl("nameBasic", "input", "id", "name_basic");
			editView.addControl("name", "input", "id", "name");
			editView.addControl("targetModule", "select", "name", "base_module");
			editView.addControl("saveWorkflow", "input", "id", "save_workflow");

			detailView.addControl("createCondition", "a", "id", "NewWorkFlowTriggerShells");
			detailView.addControl("executionCondition", "input", "id", "type0");
			
			detailView.addControl("conditionFieldLink", "a", "id", "href_compare_specific_1");
			detailView.addControl("conditionFieldSelection","select","id","selector");
			detailView.addControl("conditionFieldLinkSave", "input", "name", "Save");
			detailView.addControl("conditionNext", "input", "id", "next");
			
			detailView.addControl("modPastTrigger", "input", "id", "mod_past_trigger");
			detailView.addControl("pastValueLink", "a", "id", "href_past_trigger");
			detailView.addControl("pastValueField", "input", "id", "past_trigger__field_value");
			
			detailView.addControl("futureValueLink", "a", "id", "href_future_trigger");
			detailView.addControl("futureValueField", "input", "id", "future_trigger__field_value");
			detailView.addControl("futureValueSave", "input", "id", "save");
			
			detailView.addControl("saveExecution", "input", "id", "save");
			
			detailView.addControl("createAction", "a", "id", "NewWorkFlowActionShells");
			detailView.addControl("actionSave", "input", "css", "table.edit.view tbody tr:nth-of-type(1) td:nth-of-type(1) table tbody tr:nth-of-type(1) td:nth-of-type(1) input.button");
			detailView.addControl("actionNext", "input", "id", "step1_next");
			
			detailView.addControl("modifyNameCheckbox", "input", "id", "mod_field_0");
			detailView.addControl("modifyNameLink", "a", "id", "href_0");
			detailView.addControl("modifyNameFieldValue", "input", "id", "field_0__field_value");
			detailView.addControl("modifyNameFieldSave", "input", "css", "table.edit.view tbody tr:nth-of-type(1) td:nth-of-type(1) table tbody tr:nth-of-type(1) td:nth-of-type(1) input.button");
			
			detailView.addControl("modifyModifiedByCheckbox", "input", "id", "mod_field_1");
			detailView.addControl("modifyModifiedByLink", "a", "id", "href_1");
			detailView.addControl("modifyModifiedBySelect", "select", "id", "field_1__field_value");
			detailView.addControl("modifyModifiedBySave", "input", "css", "table.edit.view tbody tr:nth-of-type(1) td:nth-of-type(1) table tbody tr:nth-of-type(1) td:nth-of-type(1) input.button");
			
			detailView.addControl("modifyDescriptionCheckbox", "input", "id", "mod_field_2");
			detailView.addControl("modifyDescriptionLink", "a", "id", "href_2");
			detailView.addControl("modifyDescriptionField", "textarea", "id", "field_2__field_value");
			detailView.addControl("modifyDescriptionSave", "input", "css", "table.edit.view tbody tr:nth-of-type(1) td:nth-of-type(1) table tbody tr:nth-of-type(1) td:nth-of-type(1) input.button");

			// TODO: Load more fields into the HashMap

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * creates a workflow based on the passed in DataSource
	 * @param workflowOptions
	 * @return WorkFlowRecord
	 * @throws Exception
	 */
	public WorkFlowRecord create(DataSource workflowOptions) throws Exception {
		// TODO: PLEASE MAKE SURE TO CHECK THE DATASOURCE FOR ACTUAL STEPS TO BE
		// PERFORMED -- NO HARDCORDED STEPS
		// Navigate to the Admin Tools
		VoodooUtils.voodoo.log.info("Creating Workflow...");
		sugar.navbar.selectUserAction("admin_link");

		// Navigate to Workflow module
		/*
		sugar.workflow.editView.getControl("workflow_management").click();
		
		for(FieldSet workflow : workflowOptions){
			// Start the create process for Workflow
			sugar.navbar.selectAction("moduleTab_AllWorkFlow", "CreateWorkflowDefinitionAll");
	
			// Name the workflow
			sugar.workflow.editView.getControl("name").set(workflow.get("name"));
	
			// Choose workflow options like Execution, Target Module etc.
	//		sugar.workflow.editView.getControl("targetModule").set(workflowOptions.get(0).get("module"));
	
			// Save workflow definition
			sugar.workflow.editView.getControl("saveWorkflow").click();
			
			//Verify the workflow was created
			//TODO: Need a way to verify workflow creation
	
			// Call a method to Create a condition for triggering the workflow
			createTriggerCondition(workflow);
			createAction(workflow);
		}
		*/
		return new WorkFlowRecord(workflowOptions.get(0));
	}// end create()

	/**
	 * create a condition for a workflow based on values in the DataSource
	 * @param workflowOptions
	 * @throws Exception
	 */
	public void createTriggerCondition(FieldSet workflowOptions) throws Exception{
		/*
		//Check to see what the first step in the workflow condition process is
		// If the type of condition is "When a field in the target module changes to or from a specified value "		
		if (workflowOptions.get("executionCondition").equals("type0")) {
			// Create a trigger condition
			sugar.workflow.detailView.getControl("createCondition").click();
			VoodooUtils.pause(1000);
			
			// Attach to the new pop-up window
			VoodooUtils.focusByIndex(1);
			
			// Choose Execution Condition option
			// TODO: Need to add support for select/radio/checkbox elements
			sugar.workflow.detailView.getControl(workflowOptions.get("executionCondition")).click();

			// Click on conditions field link
			sugar.workflow.detailView.getControl("conditionFieldLink").click(); //triggers pop-up
			
			// Attach to the new pop-up window
			VoodooUtils.focusByIndex(2);

			// Choose which field to use for condition trigger
			//TODO: Need to add support for select/radio/checkbox elements
			//sugar.workflow.detailView.getControl("conditionFieldSelection").set(fieldset.get("fieldSelection"));
			
			// Save conditionFieldLink value
			sugar.workflow.detailView.getControl("conditionFieldLinkSave").click(); //closes pop-up 2
			
			// Attach back to parent popup
			VoodooUtils.focusByIndex(1);

			// Click next button to move to step two in Condition create
			sugar.workflow.detailView.getControl("conditionNext").click(); 

			// Click on future value link
			sugar.workflow.detailView.getControl("futureValueLink").click(); //triggers new pop-up 2
			VoodooUtils.focusByIndex(2);
			
			// Set the future value field and then save
			sugar.workflow.detailView.getControl("futureValueField").set(workflowOptions.get("futureValue"));
			sugar.workflow.detailView.getControl("futureValueSave").click(); //saving value closes pop-up 2
			VoodooUtils.focusByIndex(1);
			
			// Save to complete trigger condition setup
			sugar.workflow.detailView.getControl("futureValueSave").click(); //saving closes pop-up 1
			
			// Attach back to main sugar instance
			VoodooUtils.focusByIndex(0);

			// Verify the workflow creation
			// TODO: Need to come up with a way to verify

		}
		// If the type of condition is "When the target module changes  "
		if (workflowOptions.get("executionCondition").equals("type1")) {
			// Create a trigger condition
			sugar.workflow.detailView.getControl("createCondition").click();

			// Attach to the new pop-up window
			VoodooUtils.focusByIndex(1);
			sugar.workflow.detailView.getControl("executionCondition").click();
		}
		// If the type of condition is "When a field on the target module changes "
		if (workflowOptions.get("executionCondition").equals("type2")) {
			// Create a trigger condition
			sugar.workflow.detailView.getControl("createCondition").click();

			// Attach to the new pop-up window
			VoodooUtils.focusByIndex(1);
			sugar.workflow.detailView.getControl("executionCondition").click();
		}
		if (workflowOptions.get("executionCondition").equals("type3")) {
			// Create a trigger condition
			sugar.workflow.detailView.getControl("createCondition").click();

			// Attach to the new pop-up window
			VoodooUtils.focusByIndex(1);
			sugar.workflow.detailView.getControl("executionCondition").click();
		}
		// If the type of condition is "When a field in the target module contains a specified value "
		if (workflowOptions.get("executionCondition").equals("type4")) {
			// Create a trigger condition
			sugar.workflow.detailView.getControl("createCondition").click();

			// Attach to the new pop-up window
			VoodooUtils.focusByIndex(1);
			sugar.workflow.detailView.getControl("executionCondition").click();
		}
		// If the type of condition is "When the target module changes and a field in a related module contains a specified value "
		if (workflowOptions.get("executionCondition").equals("type5")) {
			// Create a trigger condition
			sugar.workflow.detailView.getControl("createCondition").click();

			// Attach to the new pop-up window
			VoodooUtils.focusByIndex(1);
			sugar.workflow.detailView.getControl("executionCondition").click();
		}
		*/
	}//end setTriggerCondition
	
	/**
	 * createAction will create Actions for a workflow to be performed when the trigger conditions are met
	 * @param fieldset - data to create an Action with
	 */
	public void createAction(FieldSet fieldset) throws Exception{
		/*
		//Click on Create for Actions
		sugar.workflow.detailView.getControl("createAction").click(); //triggers popup #1
		VoodooUtils.focusByIndex(1);
		
		// Check which type of Action to choose based on the value in the fieldset passed in
		if(fieldset.get("actionType").equals("action_type0")){
			sugar.workflow.detailView.getControl("actionType").click();
			
			// Click Next to move to next step in Create Action process
			sugar.workflow.detailView.getControl("actionNext").click();
			
			// Choose which field to update as the Action based on values in the FieldSet
			for(int i=1; i <= Integer.parseInt(fieldset.get("numModifyField")); i++){
				if(fieldset.get("modifyField_"+i).equals("Name")){
					// Check the box next to Name
					sugar.workflow.detailView.getControl("modifyNameCheckbox").click();
					
					// Click on the Name link to set the new value for Name field
					sugar.workflow.detailView.getControl("modifyNameLink").click(); //triggers popup #2
					VoodooUtils.focusByIndex(1);
					
					// Set new value for Name field
					sugar.workflow.detailView.getControl("modifyNameFieldValue").set(fieldset.get("modifyFieldValue_" + i));
					
					// Save
					sugar.workflow.detailView.getControl("modifyNameFieldSave").click(); // closes popup #2
					
					// Save
					sugar.workflow.detailView.getControl("actionSave").click(); // closes popup #1
				}
				if(fieldset.get("modifyField_"+i).equals("Modified By")){
					sugar.workflow.detailView.getControl("modifyModifiedByCheckbox").click();
				}
				if(fieldset.get("modifyField_"+i).equals("Description")){
					sugar.workflow.detailView.getControl("modifyDescriptionCheckbox").click();
				}
			}
			
		}
		if(fieldset.get("actionType").equals("action_type1")){
			
		}
		if(fieldset.get("actionType").equals("action_type2")){
			
		}
		if(fieldset.get("actionType").equals("action_type3")){
			
		}
		*/
	}//end setAction
	
	/**
	 * deletes all workflows in the listview
	 */
	public void deleteAll() {
		/*
		sugar.navbar.selectUserAction("admin_link");

		// Navigate to Workflow module
		sugar.workflow.editView.getControl("workflow_management").click();

		// Click the down arrow to expand the list of Select options
		sugar.contacts.listView.getControl("sugarActionButton").click();
		try {
			VoodooUtils.pause(800);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Click Select All
		sugar.contacts.listView.getControl("buttonSelectAllTop").click();

		// Click on Delete
		sugar.contacts.listView.getControl("deleteListviewTop").click();

		// Accept the alert pop-up
		try {
			VoodooUtils.acceptDialog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	} // end deleteAll
} // end WorkFlowModule