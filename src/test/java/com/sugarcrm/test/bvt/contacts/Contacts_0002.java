package com.sugarcrm.test.bvt.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_0002 extends SugarTest{
	FieldSet contact;
	FieldSet contactEdited;

	public void setup() throws Exception {
		sugar.login_regularUser();
		contact = sugar.contacts.api.createFS();
	}
	
	/** Edit contact and verify if changes are applied
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
			
		contactEdited = (FieldSet) testData.get(testName).get(0).clone();
		contactEdited.put("last_name", contactEdited.get("last_name") + VoodooUtils.getCurrentTimeStamp("MMddHHmmss"));
		contactEdited.put("email", VoodooUtils.getCurrentTimeStamp("MMddHHmmss") + contactEdited.get("email"));		
		
		// Open contact in detail view and edit it
		sugar.contacts.navToRecord(contact.get("lastName"));
		sugar.contacts.detailView.edit();
		VoodooUtils.switchToBWCFrame();
		sugar.contactCreateView.SALUTATION.set(contactEdited.get("salutation"));
		sugar.contactCreateView.FIRST_NAME.set(contactEdited.get("first_name"));
		sugar.contactCreateView.LAST_NAME.set(contactEdited.get("last_name"));
		sugar.contactCreateView.PREFERRED_NAME.set(contactEdited.get("preferred_name"));		
		sugar.contactCreateView.CONTACT_STATUS.set(contactEdited.get("contact_status"));
		sugar.contactCreateView.KEY_CONTACT.set(contactEdited.get("key_contact"));
		sugar.contactCreateView.KEY_RELATION.set(contactEdited.get("key_relation"));
		sugar.contactCreateView.JOB_TITLE.set(contactEdited.get("job_title"));
		sugar.contactCreateView.CONTACT_TAG.set(contactEdited.get("contact_tag"));
		sugar.contactCreateView.STREET_ADDRESS.set(contactEdited.get("street_address"));
		sugar.contactCreateView.STREET_ADDRESS_SUSPENDED.set(contactEdited.get("street_address_suppressed"));
		sugar.contactCreateView.CITY.set(contactEdited.get("city"));
		sugar.contactCreateView.selectCountry(contactEdited.get("pri_country_search"), contactEdited.get("pri_country_select"));
		sugar.contactCreateView.POSTAL_CODE.set(contactEdited.get("postal_code"));
		sugar.contactCreateView.selectState(contactEdited.get("pri_state_search"), contactEdited.get("pri_state_select"));
		
		sugar.contactCreateView.selectAltCountry(contactEdited.get("alt_country_search"), contactEdited.get("alt_country_select"));
		sugar.contactCreateView.ALT_STREET_ADDRESS.set(contactEdited.get("alt_street_address"));
		sugar.contactCreateView.ALT_CITY.set(contactEdited.get("alt_city"));
		sugar.contactCreateView.selectAltCountry(contactEdited.get("alt_country_search"), contactEdited.get("alt_country_select"));
		sugar.contactCreateView.ALT_POSTAL_CODE.set(contactEdited.get("alt_postal_code"));
		
		sugar.contactCreateView.OFFICE_PHONE.set(contactEdited.get("office_phone"));
		sugar.contactCreateView.OFFICE_PHONE_SUSPENDED.set(contactEdited.get("office_phone_suppressed"));
		sugar.contactCreateView.MOBILE.set(contactEdited.get("mobile"));
		sugar.contactCreateView.MOBILE_SUSPENDED.set(contactEdited.get("mobile_suppressed"));
		sugar.contactCreateView.EMAIL.set(contactEdited.get("email"));
		sugar.contactCreateView.EMAIL_SUSPENDED.set(contactEdited.get("email_suspended"));
		sugar.contactCreateView.EMAIL_INVALID.set(contactEdited.get("email_invalid"));
		sugar.contactCreateView.FAX.set(contactEdited.get("fax"));
		sugar.contactCreateView.FAX_SUSPENDED.set(contactEdited.get("fax_suppressed"));
		sugar.contactCreateView.selectLanguage(contactEdited.get("lang_select"), contactEdited.get("lang_select"));
		sugar.contacts.editView.save();
		
		sugar.contacts.navToRecord(contactEdited.get("last_name"));
		
		// Verify if changes are applied
		sugar.contacts.verifyDetailView(contactEdited);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");		
	}


	public void cleanup() throws Exception {
		sugar.contacts.deleteRecord(contactEdited.get("last_name"));
		sugar.logout();	
	}

}
