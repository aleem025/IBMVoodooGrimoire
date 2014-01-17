package com.sugarcrm.test.bvt.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_0003 extends SugarTest{
	FieldSet contact;
	
	@Override
	public void setup() throws Exception {
		sugar.login_regularUser();
		contact = sugar.contactCreateView.contactCreateLibFull();		
	}
	
	/** Searching for contact through Basic search form
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
				
		sugar.contacts.navToListView();
		VoodooUtils.switchToBWCFrame();
		// Search contact by first name
		if (sugar.contacts.searchForm.searchByName(contact.get("first_name"))) 
			VoodooUtils.voodoo.log.info("(*) Search the contact by first_name '" + contact.get("first_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by first_name '" + contact.get("first_name") + "' - NOT FOUNDED" );
		
		//Search contact by last name
		if (sugar.contacts.searchForm.searchByName(contact.get("last_name"))) 
			VoodooUtils.voodoo.log.info("(*) Search the contact by first_name '" + contact.get("last_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by first_name '" + contact.get("last_name") + "' - NOT FOUNDED" );
		
		//Search contact by email
		if (sugar.contacts.searchForm.basicSearch(sugar.contacts.BASIC_SEARCH_EMAIL, contact.get("email"), contact.get("last_name")))		
			VoodooUtils.voodoo.log.info("(*) Search the contact by email '" + contact.get("email") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by email '" + contact.get("email") + "' - NOT FOUNDED" );
		
		//Search contact by tag		
		if (sugar.contacts.searchForm.basicSearch(sugar.contacts.BASIC_SEARCH_TAGS, contact.get("contact_tags"), contact.get("last_name")))		
			VoodooUtils.voodoo.log.info("(*) Search the contact by tag '" + contact.get("contact_tags") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by tag '" + contact.get("contact_tags") + "' - NOT FOUNDED" );
		
		//Verify if contact not found when search request is wrong
		String timestamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		if (sugar.contacts.searchForm.searchByName(timestamp)) 
			VoodooUtils.voodoo.log.warning("(!) Search the contact by wrong name '" + timestamp + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.info("(*) Search the contact by wrong name '" + timestamp + "' - NOT FOUNDED" );
		
		// Verify search by combined parameters		
		sugar.contacts.BASIC_CLEAR_BUTTON.click();
		sugar.contacts.BASIC_CURRENT_USER_CHBOX.set("true");
		sugar.contacts.BASIC_FAVORITES_CHBOX.set("false");
		sugar.contacts.BASIC_SEARCH_NAME.set(contact.get("first_name") + " " + contact.get("last_name"));			
		sugar.contacts.BASIC_SEARCH_EMAIL.set(contact.get("email"));			
		sugar.contacts.BASIC_SEARCH_TAGS.set(contact.get("contact_tags"));				
		
		sugar.contacts.BASIC_SEARCH_BUTTON.click();
		if (new VoodooControl("a", "xpath", "//a[contains(text(), '" + contact.get("last_name") + "')]").queryVisible())
			VoodooUtils.voodoo.log.info("(*) Search the contact by combined parameters: '" + contact.get("last_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by combined parameters: '" + contact.get("last_name") + "' - NOT FOUNDED" );
		
		VoodooUtils.switchBackToWindow();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	@Override
	public void cleanup() throws Exception {
		sugar.contacts.deleteRecord(contact.get("last_name"));
		sugar.logout();			
	}

}
