package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

/*import wip.LeadRecord;
import com.sugarcrm.candybean.datasource.FieldSet;
*/
public class Leads_1892 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
/*		// To be created with default data.
		LeadRecord myLead = (LeadRecord)sugar.leads.create();  

		// Verify the returned data object using the GUI
		myLead.verify();
		
		FieldSet editedData = new FieldSet();
		editedData.put("firstName", "Mazen");
		editedData.put("lastName", "Louis");
		editedData.put("website", "http://www.yahoo.com");
		
		myLead.edit(editedData);
		
		myLead.verify(editedData);
*/	}

	public void cleanup() throws Exception {
//		sugar.leads.deleteAll();
		sugar.logout();
	}
}
