package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;

import wip.ReportRecord;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;

public class Reports_7734 extends SugarTest {
	AccountRecord myAccount;
	ContactRecord myContact;
	ReportRecord myReport;
	
	public void setup() throws Exception {
		sugar.login();
/*
		// Create a default account.
		myAccount = (AccountRecord)sugar.accounts.api.create();

		// Create a related contact.
		myContact = (ContactRecord)sugar.contacts.api.create(); // default contact record
		myAccount.api.relate(myContact);
		
//		1. Login to SugarCRM as a valid user.
//		2. Click "Reports" tab on navigation bar.
//		3. Click "Create Report" link from navigation Shortcuts.
//		4. Select a report type, such as "Rows and Columns Report"
//		5. Select module, such as "Documents".
//		6. Verify the related module are displayed in related modules list.
//		7. Select several columns in step "Choose Display Columns"
//		8. Click "Created User" and then click "Full Name"
//		9. Run the report.
//		
//		5. Valid label name standing for module is displayed in UI.
//		9. Report result is displayed according to selected items in steps 7 and 8.
 		*/
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			// Populate reportOptions with this data.
			//TODO: Load data from a file
			FieldSet row = new FieldSet(); //Create a FieldSet called row to house key/values
			row.put("type", "rows_and_columns");
			row.put("module", "Accounts");
			row.put("num_filters", "0");
			row.put("num_display_columns", "3");
			row.put("display_column_1_module", ""); // or "Accounts"
			row.put("display_column_1_name", "Name");
			row.put("display_column_2_module", "Assigned to User");
			row.put("display_column_2_name", "Full Name");
			row.put("display_column_3_module", "Contacts");
			row.put("display_column_3_name", "Last Name");
			row.put("report_name", "QA_Created");
			
			DataSource reportOptions = new DataSource(); //Create DataSource to store FieldSet in
			reportOptions.add(row);
/*
			myReport = sugar.reports.create(reportOptions); // Should save & run and verify that all specified options are present.

			// Populate reportData with the expected results from the datasource.  Syntax???
			DataSource reportData = new DataSource();
			row.clear();
			row.put("name", myAccount.name);
			//row.put("full_name", myAccount.assignedTo.lastName); //TODO: Need to make a User Object for assignedTo
			row.put("assignedTo", myAccount.assignedTo);
			row.put("last_name", myContact.lastName);
			reportData.add(row);


//			myReport.verifyData(reportData);
*/
	}

	public void cleanup() throws Exception {
//		myReport.delete();
		//sugar.reports.deleteReport();
		sugar.accounts.deleteAll();
		sugar.contacts.deleteAll();
		sugar.logout();
	}
}
