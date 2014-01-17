package com.sugarcrm.test.bvt.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_0001 extends SugarTest {
	FieldSet contact;
	
	public void setup() throws Exception {
		sugar.login_regularUser();
	}
	
	/** Creating new contact through full form 
	 *  Open it in detail view and verify if all contact info saved correctly
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		contact = sugar.contactCreateView.contactCreateLibFull();		
		sugar.contacts.navToRecord(contact.get("last_name"));						
		sugar.contacts.verifyDetailView(contact);	
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.deleteRecord(contact.get("last_name"));
		sugar.logout();
	}
}
