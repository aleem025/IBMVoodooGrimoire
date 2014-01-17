package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.LeadRecord;
//import wip.WorkFlowRecord;
import com.sugarcrm.sugar.records.ContactRecord;

public class Studio_11681 extends SugarTest {
	LeadRecord myLead;
	ContactRecord myContact;

	public void setup() throws Exception {
		sugar.login();

		// Set Description as a calculated field for Contacts.
		FieldSet ModuleFieldOptions = new FieldSet();
		ModuleFieldOptions.put("module", "Contacts");
		ModuleFieldOptions.put("moduleComponent", "Fields");
		ModuleFieldOptions.put("fieldName", "Description");
		ModuleFieldOptions.put("fieldCalculated", "true");
		ModuleFieldOptions.put("fieldFormula", "related($leads,\"title\")");
		
		DataSource studioOptions = new DataSource();
		studioOptions.add(ModuleFieldOptions);
		
		//sugar.studio.navigate(studioOptions); 

		// Create a default contact.
		myContact = (ContactRecord)sugar.contacts.api.create();

		// Create a related lead.
//		myLead = (LeadRecord)sugar.leads.api.create();
//		myContact.api.relateTo(myLead); //TODO: Need to implement a relateTo method
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		// Populate Workflow create with this data.
		FieldSet workflow = new FieldSet(); //Create a FieldSet called row to house key/values
		workflow.put("name", "lead");
		workflow.put("module", "Leads");
		workflow.put("executionCondition", "type0");
		workflow.put("actionType", "action_type0");
		workflow.put("numModifyField", "1");
		workflow.put("modifyField_1", "Title");
		workflow.put("modifyFieldValue_1", "new title");

		DataSource workflowOptions = new DataSource(); //Create DataSource to store FieldSet in
		workflowOptions.add(workflow);
		
		// Create the WorkFlow
		//WorkFlowRecord myWorkFlow = sugar.workflow.create(workflowOptions); 

		//Manual Creation of data for lead edit
		FieldSet leadRow = new FieldSet();
		leadRow.put("name", myLead.recordData.get("name"));
		leadRow.put("description", "another description");
		
		DataSource LeadOptions = new DataSource();
		LeadOptions.add(leadRow);
		
		// Trigger the WorkFlow by editing the Lead using data from above DataSource
//		myLead = new LeadRecord();
//		myLead.edit(LeadOptions); 
			
		// Populate Lead Data with the expected Lead results for verification
		FieldSet leadResults = new FieldSet();
		leadResults.put("name", myLead.recordData.get("name"));
		leadResults.put("title", "new title");
		
		DataSource LeadData = new DataSource(); //Create DataSource to store FieldSet in
		LeadData.add(leadResults);

//		sugar.Leads.verifyData(LeadData);
		
		// Populate Contact Data with the expected Contact results from triggered workflow.
		FieldSet contactResults = new FieldSet();
		contactResults.put("name", myContact.firstName);
		contactResults.put("description", "new title");
		
		DataSource ContactData = new DataSource(); //Create DataSource to store FieldSet in
		ContactData.add(leadResults);
		
		// verify the updated Contact data

//		sugar.contacts.verifyData(contactResults);
//		1.  Login as admin
//		2.  Go to Admin | Studio
//		3.  Create a calculated field in a module, such as Contacts
//		4.  Set formula contain related function, such as related($leads,"title")
//		5.  Add the calculated field on editview/detailview layouts
//		6.  Create a lead record with the 'title' field populated with the value 'test' and all other required fields populated
//		7.  Open a Contact record detailview
//		8.  From the Lead sub-panel relate the Lead created in step 6 to the Contact
//		9.  Create a workflow that will update the lead's title info as noted below
//		     a. Admin > Workflow Management > Create Workflow Definition
//		     b. Specify a name, select the module as 'Leads' and leave all others as defaults and 'save'
//		     c. Create 'condition' - 'when the target module changes' and 'save'
//		     d. Create 'action' - 'update fields in the target module' and hit 'next'
//		     e. Check the box next to 'Modify the field: Title'
//		     f. Click the link for 'Title' that is displayed
//		     g. Populate the field 'Set Title: as' with the value 'New Title' and 'save'
//		     h. When returned to the previous screen 'save' again
//		10. Navigate to the Lead created in step 6 and change any field and save
//		11. Go to the detail view and edit view page of the contact record
//		
//		10. Confirm that the workflow is triggered and the Lead created in step 6 now shows the title as 'New Title' as expected 
//		11. The calculated field value (created in step 5) in the related Contact record shows  the updated title
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
//		sugar.leads.api.deleteAll();
		
		// Reverse the Description as a calculated field for Contacts.
		FieldSet ModuleFieldOptionsUndo = new FieldSet();
		ModuleFieldOptionsUndo.put("module", "Contacts");
		ModuleFieldOptionsUndo.put("field_name", "Description");		ModuleFieldOptionsUndo.put("field_calculated", "false");
		
		DataSource studioOptionsUndo = new DataSource();
		studioOptionsUndo.add(ModuleFieldOptionsUndo);
		
		//sugar.studio.navigate(studioOptionsUndo); 
		sugar.logout();
	}
}