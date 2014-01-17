package com.sugarcrm.test.bvt.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_0004 extends SugarTest{
	FieldSet contact;
	
	public void setup() throws Exception {
		sugar.login_regularUser();
		contact = sugar.contactCreateView.contactCreateLibFull();		
	}
	
	/** Searching for contact through Advanced search form
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
				
		sugar.contacts.navToListView();
		VoodooUtils.switchToBWCFrame();
		
		// Search contact by first name
		if (sugar.contacts.searchForm.advancedSearch(sugar.contacts.ADVANCED_SEARCH_NAME, contact.get("first_name"), contact.get("last_name"))) 
			VoodooUtils.voodoo.log.info("(*) Search the contact by first_name '" + contact.get("first_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by first_name '" + contact.get("first_name") + "' - NOT FOUNDED" );
		
		//Search contact by last name
		if (sugar.contacts.searchForm.advancedSearch(sugar.contacts.ADVANCED_SEARCH_NAME, contact.get("last_name"), contact.get("last_name"))) 
			VoodooUtils.voodoo.log.info("(*) Search the contact by first_name '" + contact.get("last_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by first_name '" + contact.get("last_name") + "' - NOT FOUNDED" );
		
		//Search contact by email
		if (sugar.contacts.searchForm.advancedSearch(sugar.contacts.ADVANCED_SEARCH_EMAIL, contact.get("email"), contact.get("last_name")))		
			VoodooUtils.voodoo.log.info("(*) Search the contact by email '" + contact.get("email") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by email '" + contact.get("email") + "' - NOT FOUNDED" );
		
		//Search contact by tag		
		if (sugar.contacts.searchForm.advancedSearch(sugar.contacts.ADVANCED_SEARCH_TAGS, contact.get("contact_tags"), contact.get("last_name")))		
			VoodooUtils.voodoo.log.info("(*) Search the contact by tag '" + contact.get("contact_tags") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by tag '" + contact.get("contact_tags") + "' - NOT FOUNDED" );
		
		//Verify if contact not found when search request is wrong
		String timestamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		if (sugar.contacts.searchForm.advancedSearch(sugar.contacts.ADVANCED_SEARCH_NAME, timestamp, contact.get("last_name")))  
			VoodooUtils.voodoo.log.warning("(!) Search the contact by wrong name '" + timestamp + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.info("(*) Search the contact by wrong name '" + timestamp + "' - NOT FOUNDED" );
		
		// Verify search by combined parameters: name + country		
		sugar.contacts.ADVANCED_CLEAR_BUTTON.click();
		sugar.contacts.ADVANCED_CURRENT_USER_CHBOX.set("true");
		sugar.contacts.ADVANCED_FAVORITES_CHBOX.set("false");
		sugar.contacts.ADVANCED_SEARCH_NAME.set(contact.get("first_name") + " " + contact.get("last_name"));			
		sugar.contacts.ADVANCED_SEARCH_COUNTRY.set(contact.get("country_select"));			
		
		sugar.contacts.ADVANCED_SEARCH_BUTTON.click();
		if (new VoodooControl("a", "xpath", "//a[contains(text(), '" + contact.get("last_name") + "')]").queryVisible())
			VoodooUtils.voodoo.log.info("(*) Search the contact by combined parameters name + country: '" + contact.get("last_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by combined parameters name + country: '" + contact.get("last_name") + "' - NOT FOUNDED" );
		
		// Verify search by combined parameters: name + city	
		sugar.contacts.ADVANCED_CLEAR_BUTTON.click();
		sugar.contacts.ADVANCED_CURRENT_USER_CHBOX.set("true");
		sugar.contacts.ADVANCED_FAVORITES_CHBOX.set("false");
		sugar.contacts.ADVANCED_SEARCH_NAME.set(contact.get("first_name") + " " + contact.get("last_name"));		
		sugar.contacts.ADVANCED_SEARCH_CITY.set(contact.get("city"));		
		
		sugar.contacts.ADVANCED_SEARCH_BUTTON.click();
		if (new VoodooControl("a", "xpath", "//a[contains(text(), '" + contact.get("last_name") + "')]").queryVisible())
			VoodooUtils.voodoo.log.info("(*) Search the contact by combined parameters name + city: '" + contact.get("last_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by combined parameters name + city: '" + contact.get("last_name") + "' - NOT FOUNDED" );
		
		// Verify search by combined parameters: name + state		
		sugar.contacts.ADVANCED_CLEAR_BUTTON.click();
		sugar.contacts.ADVANCED_CURRENT_USER_CHBOX.set("true");
		sugar.contacts.ADVANCED_FAVORITES_CHBOX.set("false");
		sugar.contacts.ADVANCED_SEARCH_NAME.set(contact.get("first_name") + " " + contact.get("last_name"));			
		sugar.contacts.ADVANCED_SEARCH_STATE.set(contact.get("state_select"));			
		
		sugar.contacts.ADVANCED_SEARCH_BUTTON.click();
		if (new VoodooControl("a", "xpath", "//a[contains(text(), '" + contact.get("last_name") + "')]").queryVisible())
			VoodooUtils.voodoo.log.info("(*) Search the contact by combined parameters name + state: '" + contact.get("last_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by combined parameters name + state: '" + contact.get("last_name") + "' - NOT FOUNDED" );
				
		// Verify search by combined parameters: name + country + city + state		
		sugar.contacts.ADVANCED_CLEAR_BUTTON.click();
		sugar.contacts.ADVANCED_CURRENT_USER_CHBOX.set("true");
		sugar.contacts.ADVANCED_FAVORITES_CHBOX.set("false");
		sugar.contacts.ADVANCED_SEARCH_NAME.set(contact.get("first_name") + " " + contact.get("last_name"));
		sugar.contacts.ADVANCED_SEARCH_COUNTRY.set(contact.get("country_select"));		
		sugar.contacts.ADVANCED_SEARCH_STATE.set(contact.get("state_select"));		
		sugar.contacts.ADVANCED_SEARCH_CITY.set(contact.get("city"));
		
		sugar.contacts.ADVANCED_SEARCH_BUTTON.click();
		if (new VoodooControl("a", "xpath", "//a[contains(text(), '" + contact.get("last_name") + "')]").queryVisible())
			VoodooUtils.voodoo.log.info("(*) Search the contact by combined parameters name + state: '" + contact.get("last_name") + "' - FOUNDED" );
		else VoodooUtils.voodoo.log.warning("(!) Search the contact by combined parameters name + state: '" + contact.get("last_name") + "' - NOT FOUNDED" );
		
		VoodooUtils.switchBackToWindow();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}
	
	public void cleanup() throws Exception {
		sugar.contacts.deleteRecord(contact.get("last_name"));
		sugar.logout();			
	}

}
