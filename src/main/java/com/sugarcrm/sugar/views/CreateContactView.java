package com.sugarcrm.sugar.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;

public class CreateContactView extends View{
	protected static CreateContactView contactCreateView;
	protected static AppModel sugar;	
	public final VoodooControl SALUTATION;
	public final VoodooControl FIRST_NAME;
	public final VoodooControl LAST_NAME;
	public final VoodooControl PREFERRED_NAME;
	public final VoodooControl ALTERNATIVE_FIRST_NAME;
	public final VoodooControl ALTERNATIVE_LAST_NAME;
	public final VoodooControl ALTERNATIVE_PREFERRED_NAME;
	
	public final VoodooControl CLIENT_NAME;
	public final VoodooControl CLEAR_CLIENT_NAME;
	public final VoodooControl CONTACT_STATUS;
	public final VoodooControl KEY_CONTACT;
	public final VoodooControl KEY_RELATION;
	public final VoodooControl JOB_TITLE;
	public final VoodooControl CONTACT_TAG;
	
	public final VoodooControl STREET_ADDRESS; 
	public final VoodooControl CITY;
	public final VoodooControl POSTAL_CODE;
	public final VoodooControl ALT_STREET_ADDRESS;
	public final VoodooControl ALT_CITY;
	public final VoodooControl ALT_POSTAL_CODE;
	
	public final VoodooControl OFFICE_PHONE;
	public final VoodooControl MOBILE;
	public final VoodooControl EMAIL;
	
	public final VoodooControl FAX;
	public final VoodooControl FAX_SUSPENDED;
	public final VoodooControl RELATIONSHIP_SURVEY;
	public final VoodooControl COVERAGE_STRATEGY;
	
	public final VoodooControl SAVE_HEADER;
	public final VoodooControl SAVE_FOOTER;
	
	public final VoodooControl STREET_ADDRESS_SUSPENDED;
	public final VoodooControl OFFICE_PHONE_SUSPENDED;
	public final VoodooControl MOBILE_SUSPENDED;
	public final VoodooControl EMAIL_SUSPENDED;
	public final VoodooControl EMAIL_INVALID;
	
	public final VoodooControl SUB_EXPAND_BTN;
	public final VoodooControl SUB_CREATE_BTN;
	public final VoodooControl SUB_CONTACT_NAME;
	public final VoodooControl SUB_SAVE_BTN;
	public FieldSet defaultContact;
	private FieldSet contactFull;
		
	/**
	 * Initializes the ContactCreateView screen.
	 * @throws Exception
	 */
	public CreateContactView() throws Exception {
		super();
		sugar = AppModel.getInstance();
		defaultContact = (FieldSet)  VoodooUtils.getData("CreateContactView").get(0).clone();
		contactFull = new FieldSet();
		
		SALUTATION = new VoodooControl("input", "id", "salutation");
		FIRST_NAME = new VoodooControl("input", "id", "first_name");
		LAST_NAME = new VoodooControl("input", "id", "last_name");
		PREFERRED_NAME = new VoodooControl("input", "id", "preferred_name_c");
		ALTERNATIVE_FIRST_NAME = new VoodooControl("input", "id", "alt_lang_first_c");
		ALTERNATIVE_LAST_NAME = new VoodooControl("input", "id", "alt_lang_last_c");
		ALTERNATIVE_PREFERRED_NAME = new VoodooControl("input", "id", "alt_lang_preferred_name_c");
		CLIENT_NAME = new VoodooControl("input", "id", "account_name");
		CLEAR_CLIENT_NAME = new VoodooControl("button", "id", "btn_clr_account_name");
		CONTACT_STATUS = new VoodooControl("select", "id", "contact_status_c");
		KEY_CONTACT = new VoodooControl("input", "id", "key_contact_c");
		KEY_RELATION = new VoodooControl("input", "id", "key_contact_relation_c");
		JOB_TITLE = new VoodooControl("input", "id", "title");
		CONTACT_TAG = new VoodooControl("input", "id", "tags");
		
		STREET_ADDRESS = new VoodooControl("input", "id", "primary_address_street");
		STREET_ADDRESS_SUSPENDED = new VoodooControl("input", "id", "address_suppressed");
		CITY = new VoodooControl("input", "id", "primary_address_city");
		POSTAL_CODE = new VoodooControl("input", "id", "primary_address_postalcode");
		ALT_STREET_ADDRESS = new VoodooControl("input", "id", "alt_address_street");
		ALT_CITY = new VoodooControl("input", "id", "alt_address_city");
		ALT_POSTAL_CODE = new VoodooControl("input", "id", "alt_address_postalcode");
		
		OFFICE_PHONE = new VoodooControl("input", "id", "phone_work");
		OFFICE_PHONE_SUSPENDED = new VoodooControl("input", "id", "phone_work_suppressed");
		MOBILE = new VoodooControl("input", "id", "phone_mobile");
		MOBILE_SUSPENDED = new VoodooControl("input", "id", "phone_mobile_suppressed");
		EMAIL = new VoodooControl("input", "id", "Contacts0emailAddress0");
		EMAIL_SUSPENDED = new VoodooControl("input", "id", "Contacts0emailAddressOptOutFlag0");
		EMAIL_INVALID = new VoodooControl("input", "id", "Contacts0emailAddressInvalidFlag0");
		
		FAX = new VoodooControl("input", "id", "phone_fax");
		FAX_SUSPENDED = new VoodooControl("input", "id", "phone_fax_suppressed");
		RELATIONSHIP_SURVEY = new VoodooControl("input", "id", "client_value_survey_c");
		COVERAGE_STRATEGY = new VoodooControl("input", "id", "contact_coverage_strategy_c");
		
		SAVE_HEADER = new VoodooControl("input", "id", "SAVE_HEADER");
		SAVE_FOOTER = new VoodooControl("input", "id", "SAVE_FOOTER");
		
		SUB_EXPAND_BTN = new VoodooControl("span","id", "show_link_contacts");
		SUB_CREATE_BTN = new VoodooControl("a","id", "accounts_contacts_create_button");
		SUB_CONTACT_NAME = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Contacts']//input[@id='first_name']");
		SUB_SAVE_BTN = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Contacts']//input[@id='Contacts_subpanel_save_button']");
	}
	
	public static CreateContactView getInstance() throws Exception {
		if (contactCreateView == null) contactCreateView = new CreateContactView();
		return contactCreateView;
	}	
	
	/**
	 * Set primary Country/Area 
	 * @countrySearch - Search country by this text
	 * @countryName - Select country by full name
	 */
	public void selectCountry(String countrySearch, String countryName) throws Exception{
		new VoodooControl("input", "id", "primary_address_country-input").set(countrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + countryName + "']").click();
	}
	
	public void selectState(String stateSearch, String stateName) throws Exception{
		new VoodooControl("input", "id", "primary_address_state-input").set(stateSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + stateName + "']").click();
	}
	
	public void selectAltCountry(String altCountrySearch, String altCountryName) throws Exception{
		new VoodooControl("input", "id", "alt_address_country-input").set(altCountrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + altCountryName + "']").click();
	}
	
	public void selectAltState(String altStateSearch, String altStateName) throws Exception{
		new VoodooControl("input", "id", "alt_address_state-input").set(altStateSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + altStateName + "']").click();
	}
	
	public void selectLanguage(String languageSearch, String languageName) throws Exception{
		new VoodooControl("input", "id", "language_c_ac").set(languageSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + languageName + "']").click();
	}
	
	public void selectClientTypeAhead(String clientSearch, String clientName) throws Exception{
		new VoodooControl("input", "id", "account_name").set(clientSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + clientName + "']").click();
	}
	
	public void selectClientButton(String clientName) throws Exception{
		new VoodooControl("button", "id", "btn_account_name").click();
		//Switch to pop-up?
		VoodooUtils.switchToWindow("SalesConnect");
		new VoodooControl("button", "id", "search_form_clear").click();
		new VoodooControl("input", "id", "name_advanced").set(clientName);
		new VoodooControl("button", "id", "search_form_submit").click();
		new VoodooControl("a", "xpath", "//a[contains(text(),'" + clientName + "')]").click();
		//switch back to main window?
		VoodooUtils.switchBackToWindow();
	}	
	
	/**
	 * Create Full contact with data from CreateContactView.csv
	 * returns FieldSet of used data.
	 * 
	 */
	public FieldSet contactCreateLibFull() throws Exception{
		VoodooUtils.log.info("Running contactCreateLibFull...");
		sugar.navbar.navToModule("Contacts");
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");		
		contactFull.put("last_name", defaultContact.get("last_name") + timeStamp);		
		contactFull.put("email", timeStamp + defaultContact.get("email"));
		
		VoodooUtils.switchToBWCFrame();
		this.FIRST_NAME.assertVisible(true);
		this.FIRST_NAME.set(defaultContact.get("first_name"));
		contactFull.put("first_name", defaultContact.get("first_name"));
		this.LAST_NAME.set(contactFull.get("last_name"));
		this.SALUTATION.set(defaultContact.get("salutation"));
		contactFull.put("salutation", defaultContact.get("salutation"));
		this.PREFERRED_NAME.set(defaultContact.get("preferred_name"));
		contactFull.put("preferred_name", defaultContact.get("preferred_name"));
		this.ALTERNATIVE_FIRST_NAME.set(defaultContact.get("alt_first_name"));		
		this.ALTERNATIVE_LAST_NAME.set(defaultContact.get("alt_last_name"));
		this.ALTERNATIVE_PREFERRED_NAME.set(defaultContact.get("alt_preferred_name"));
		
		this.CONTACT_STATUS.set(defaultContact.get("contact_status"));
		contactFull.put("contact_status", defaultContact.get("contact_status"));
		this.KEY_CONTACT.set(defaultContact.get("key_contact"));
		contactFull.put("key_contact", defaultContact.get("key_contact"));
		this.KEY_RELATION.set(defaultContact.get("key_relation"));
		contactFull.put("key_relation", defaultContact.get("key_relation"));
		this.JOB_TITLE.set(defaultContact.get("job_title"));
		contactFull.put("job_title", defaultContact.get("job_title"));
		this.CONTACT_TAG.set(defaultContact.get("contact_tags"));
		contactFull.put("contact_tags", defaultContact.get("contact_tags"));
		this.STREET_ADDRESS.set(defaultContact.get("street_address"));
		contactFull.put("street_address", defaultContact.get("street_address"));
		this.STREET_ADDRESS_SUSPENDED.set(defaultContact.get("street_address_suppressed"));
		contactFull.put("street_address_suppressed", defaultContact.get("street_address_suppressed"));
		this.CITY.set(defaultContact.get("city"));
		contactFull.put("city", defaultContact.get("city"));
		this.selectCountry(defaultContact.get("country_search"), defaultContact.get("country_select"));
		contactFull.put("country_select", defaultContact.get("country_select"));
		this.selectState(defaultContact.get("state_search"), defaultContact.get("state_select"));
		contactFull.put("state_select", defaultContact.get("state_select"));
		this.POSTAL_CODE.set(defaultContact.get("postal_code"));
		contactFull.put("postal_code", defaultContact.get("postal_code"));
		
		this.OFFICE_PHONE.set(defaultContact.get("office_phone"));
		contactFull.put("office_phone", defaultContact.get("office_phone"));
		this.OFFICE_PHONE_SUSPENDED.set(defaultContact.get("office_phone_suppressed"));
		contactFull.put("office_phone_suppressed", defaultContact.get("office_phone_suppressed"));
		this.MOBILE.set(defaultContact.get("mobile"));
		contactFull.put("mobile", defaultContact.get("mobile"));
		this.MOBILE_SUSPENDED.set(defaultContact.get("mobile_suppressed"));
		contactFull.put("mobile_suppressed", defaultContact.get("mobile_suppressed"));
		
		this.selectAltCountry(defaultContact.get("alt_country_search"), defaultContact.get("alt_country_select"));
		contactFull.put("alt_country_select", defaultContact.get("alt_country_select"));
		this.ALT_STREET_ADDRESS.set(defaultContact.get("alt_street_address"));
		contactFull.put("alt_street_address", defaultContact.get("alt_street_address"));
		this.ALT_CITY.set(defaultContact.get("alt_city"));
		contactFull.put("alt_city", defaultContact.get("alt_city"));
		this.ALT_POSTAL_CODE.set(defaultContact.get("alt_postal_code"));
		contactFull.put("alt_postal_code", defaultContact.get("alt_postal_code"));
		
		this.EMAIL.set(contactFull.get("email"));
		this.EMAIL_SUSPENDED.set(defaultContact.get("email_suspended"));
		contactFull.put("email_suspended", defaultContact.get("email_suspended"));
		this.EMAIL_INVALID.set(defaultContact.get("email_invalid"));
		contactFull.put("email_invalid", defaultContact.get("email_invalid"));
		this.FAX.set(defaultContact.get("fax"));
		contactFull.put("fax", defaultContact.get("fax"));
		this.FAX_SUSPENDED.set(defaultContact.get("fax_suppressed"));
		contactFull.put("fax_suppressed", defaultContact.get("fax_suppressed"));
		
		this.selectLanguage(defaultContact.get("lang_search"), defaultContact.get("lang_select"));
		contactFull.put("lang_select", defaultContact.get("lang_select"));
		
		sugar.contacts.editView.save();		
		sugar.contacts.navToRecord(contactFull.get("last_name"));
		VoodooUtils.log.info("contactCreateLibFull complete.");
		return contactFull;
	}
	/**Method creates Call via sub panel on clients using data from csv
	 * 
	 * @throws Exception
	 */
	
	public FieldSet createContactLibSub() throws Exception{
		VoodooUtils.log.info("Running createContactLibSub...");
		this.SUB_EXPAND_BTN.click();
		this.SUB_CREATE_BTN.click();
		VoodooUtils.pause(2000);
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		contactFull.put("last_name", defaultContact.get("last_name") + timeStamp);
		contactFull.put("email", timeStamp + defaultContact.get("email"));
		VoodooUtils.switchBackToWindow();
		VoodooUtils.switchToBWCFrame();
		
		this.SUB_CONTACT_NAME.assertVisible(true);
		this.SUB_CONTACT_NAME.set(defaultContact.get("first_name"));
		
		this.LAST_NAME.set(contactFull.get("last_name"));
		this.OFFICE_PHONE.set(defaultContact.get("office_phone"));
		contactFull.put("office_phone", defaultContact.get("office_phone"));		
		this.MOBILE.set(defaultContact.get("mobile"));
		contactFull.put("mobile", defaultContact.get("mobile"));
		this.EMAIL.set(contactFull.get("email"));
		
		
		this.SUB_SAVE_BTN.click();
		VoodooUtils.pause(20000);
		VoodooUtils.switchBackToWindow();
		sugar.contacts.assertRecord(contactFull.get("last_name"));
		
		VoodooUtils.log.info("CreateContactLibSub finished.");
		return contactFull;
	}
	
}
