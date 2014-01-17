package com.sugarcrm.sugar.views;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.FieldSet;


public class CreateAccountView extends View{
	protected static CreateAccountView accountCreateView;
	protected static AppModel sugar;
	public final VoodooControl ACCOUNT_NAME;
	public final VoodooControl ACCOUNT_PARENT_NAME;
	public final VoodooControl ACCOUNT_PARENT_SEARCH;
	public final VoodooControl ACCOUNT_PARENT_CLEAR;

	public final VoodooControl TAGS;
	public final VoodooControl BILLING_COUNTRY;
	public final VoodooControl BILLING_COUNTRY_DROPDOWN;
	public final VoodooControl BILLING_STREET;
	public final VoodooControl BILLING_CITY;
	public final VoodooControl BILLING_STATE;
	public final VoodooControl BILLING_STATE_DROPDOWN;
	public final VoodooControl BILLING_POSTALCODE;
	
	public final VoodooControl SHIPPING_CHECKBOX;
	public final VoodooControl SHIPPING_STREET;
	public final VoodooControl SHIPPING_CITY;
	public final VoodooControl SHIPPING_STATE;
	public final VoodooControl SHIPPING_STATE_DROPDOWN;
	public final VoodooControl SHIPPING_POSTALCODE;
	public final VoodooControl SHIPPING_COUNTRY;
	public final VoodooControl SHIPPING_COUNTRY_DROPDOWN;
	
	public final VoodooControl OFFICE_PHONE;
	public final VoodooControl FAX;
	public final VoodooControl RESTRICTED;
	public final VoodooControl WEBSITE;
	
	public final VoodooControl INDUSTRY_SOLUTION_UNIT;
	public final VoodooControl INDUSTRY_SOLUTION_UNIT_DROPDOWN;
	public final VoodooControl INDUSTRY;
	public final VoodooControl INDUSTRY_DROPDOWN;
	public final VoodooControl INDUSTRY_CLASS;
	public final VoodooControl INDUSTRY_CLASS_DROPDOWN;
	public final VoodooControl INDUSTRY_SOLUTION_CLASSIFICATION;
	public final VoodooControl INDUSTRY_SOLUTION_CLASSIFICATION_DROPDOWN;
	public final VoodooControl QUAD_TIER_CODE;
	public final VoodooControl QUAD_TIER_CODE_DROPDOWN;	
		
	public FieldSet defaultAccount;
	private FieldSet accountFull;
	
	/**
	 * Initializes the AccountCreateView screen.
	 * @throws Exception
	 */
	public CreateAccountView() throws Exception {
		super();
		sugar = AppModel.getInstance();
		ACCOUNT_NAME = new VoodooControl("input", "id", "name");
		
		ACCOUNT_PARENT_SEARCH = new VoodooControl("button", "id", "btn_parent_name");
		ACCOUNT_PARENT_NAME = new VoodooControl("input", "id", "parent_name");
		ACCOUNT_PARENT_CLEAR = new VoodooControl("button", "id", "btn_clr_parent_name");
		TAGS = new VoodooControl("input", "id", "tags");
		
		BILLING_COUNTRY = new VoodooControl("input", "id", "billing_address_country-input");
		BILLING_COUNTRY_DROPDOWN = new VoodooControl("button", "id", "billing_address_country-image");
		BILLING_STREET = new VoodooControl("input", "id", "billing_address_street");
		BILLING_CITY = new VoodooControl("input", "id", "billing_address_city");
		BILLING_STATE = new VoodooControl("input", "id", "billing_address_state-input");
		BILLING_STATE_DROPDOWN = new VoodooControl("button", "id", "billing_address_state-image");
		BILLING_POSTALCODE = new VoodooControl("input", "id", "billing_address_postalcode");
		
		SHIPPING_CHECKBOX = new VoodooControl("input", "id", "shipping_address_checkbox");
		SHIPPING_STREET = new VoodooControl("input", "id", "shipping_address_street");
		SHIPPING_CITY = new VoodooControl("input", "id", "shipping_address_city");
		SHIPPING_STATE = new VoodooControl("input", "id", "shipping_address_state-input");
		SHIPPING_STATE_DROPDOWN = new VoodooControl("button", "id", "shipping_address_state-image");
		SHIPPING_POSTALCODE = new VoodooControl("input", "id", "shipping_address_postalcode");
		SHIPPING_COUNTRY = new VoodooControl("input", "id", "shipping_address_country-input");
		SHIPPING_COUNTRY_DROPDOWN = new VoodooControl("button", "id", "shipping_address_country-image");
		
		OFFICE_PHONE = new VoodooControl("input", "id", "phone_office");
		FAX = new VoodooControl("input", "id", "phone_fax");
		RESTRICTED = new VoodooControl("input", "id", "restricted");
		WEBSITE = new VoodooControl("input", "id", "website");
		
		INDUSTRY_SOLUTION_UNIT = new VoodooControl("input", "id", "indus_isu_name-input");
		INDUSTRY_SOLUTION_UNIT_DROPDOWN = new VoodooControl("button", "id", "indus_isu_name-image");
		INDUSTRY = new VoodooControl("input", "id", "industry_list-input");
		INDUSTRY_DROPDOWN = new VoodooControl("button", "id", "industry_list-image");
		INDUSTRY_CLASS = new VoodooControl("input", "id", "indus_class_name-input");
		INDUSTRY_CLASS_DROPDOWN = new VoodooControl("button", "id", "indus_class_name-image");
		INDUSTRY_SOLUTION_CLASSIFICATION = new VoodooControl("input", "id", "indus_sic_code-input");
		INDUSTRY_SOLUTION_CLASSIFICATION_DROPDOWN = new VoodooControl("button", "id", "indus_sic_code-image");
		QUAD_TIER_CODE = new VoodooControl("input", "id", "indus_quad_tier_code-input");
		QUAD_TIER_CODE_DROPDOWN = new VoodooControl("button", "id", "indus_quad_tier_code-image");						
		
		defaultAccount = (FieldSet) VoodooUtils.getData("CreateAccountView").get(0).clone();
		accountFull = new FieldSet();
	}
	
	public static CreateAccountView getInstance() throws Exception {
		if (accountCreateView == null) accountCreateView = new CreateAccountView();
		return accountCreateView;
	}		
	
	/**
	 *  Selection through the comboboxes
	 *  <Search> means base symbols for the search
	 *  <Name> means full name for the selection entry
	 */
	//Country&State
	public void selectBillingCountry(String countrySearch, String countryName) throws Exception{
		new VoodooControl("input", "id", "billing_address_country-input").set(countrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + countryName + "']").click();
	}
	
	public void selectBillingState(String stateSearch, String stateName) throws Exception{
		new VoodooControl("input", "id", "billing_address_state-input").set(stateSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + stateName + "']").click();
	}
	
	public void selectShippingCountry(String altCountrySearch, String altCountryName) throws Exception{
		new VoodooControl("input", "id", "shipping_address_country-input").set(altCountrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + altCountryName + "']").click();
	}
	
	public void selectShippingState(String altStateSearch, String altStateName) throws Exception{
		new VoodooControl("input", "id", "shipping_address_state-input").set(altStateSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + altStateName + "']").click();
	}
	
	//ISU fields
	public void selectISU(String isuSearch, String isuName) throws Exception{
		new VoodooControl("input", "id", "indus_isu_name-input").set(isuSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + isuName + "']").click();
	}
	
	public void selectIndustry(String industrySearch, String industryName) throws Exception{
		new VoodooControl("input", "id", "industry_list-input").set(industrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + industryName + "']").click();
	}
	
	public void selectIndustryClass(String industryClassSearch, String industryClassName) throws Exception{
		new VoodooControl("input", "id", "indus_class_name-input").set(industryClassSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + industryClassName + "']").click();
	}
	
	public void selectIndustrySolutionClassification(String industrySolutionClassificationSearch, String industrySolutionClassificationName) throws Exception{
		new VoodooControl("input", "id", "indus_sic_code-input").set(industrySolutionClassificationSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + industrySolutionClassificationName + "']").click();
	}
	
	public void selectQuadTierCode(String quadTierCodeSearch, String quadTierCodeName) throws Exception{
		new VoodooControl("input", "id", "indus_quad_tier_code-input").set(quadTierCodeSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + quadTierCodeName + "']").click();
	}
	
	//Gray space client selection
	public void selectGraySpaceClient(String clientName) throws Exception{
		new VoodooControl("button", "id", "btn_parent_name").click();
		sugar.accounts.searchForm.popupSelectRecord(clientName);		
	}
	
	
	public FieldSet accountCreateLibFull() throws Exception{
		VoodooUtils.log.info(" accountCreateLibFull is running ");
		sugar.navbar.navToModule("Accounts");
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		accountFull.put("name", defaultAccount.get("name") + timeStamp);
		VoodooUtils.switchToBWCFrame();
		this.ACCOUNT_NAME.assertVisible(true);
		this.ACCOUNT_NAME.set(accountFull.get("name"));
		this.TAGS.set(defaultAccount.get("tag"));
		accountFull.put("tag", defaultAccount.get("tag"));
		//physical address
		this.selectBillingCountry(defaultAccount.get("search_phy_country"), defaultAccount.get("billing_address_country"));
		accountFull.put("billing_address_country", defaultAccount.get("billing_address_country"));
		this.BILLING_STREET.set(defaultAccount.get("billing_address_street"));
		accountFull.put("billing_address_street", defaultAccount.get("billing_address_street"));
		this.BILLING_CITY.set(defaultAccount.get("billing_address_city"));
		accountFull.put("billing_address_city", defaultAccount.get("billing_address_city"));
		this.selectBillingState(defaultAccount.get("search_phy_state"), defaultAccount.get("billing_address_state"));	
		accountFull.put("billing_address_state", defaultAccount.get("billing_address_state"));
		this.BILLING_POSTALCODE.set(defaultAccount.get("billing_address_postalcode"));
		accountFull.put("billing_address_postalcode", defaultAccount.get("billing_address_postalcode"));
		//mailing address
		this.selectShippingCountry(defaultAccount.get("search_mail_country"), defaultAccount.get("shipping_address_country"));
		accountFull.put("shipping_address_country", defaultAccount.get("shipping_address_country"));
		this.SHIPPING_STREET.set(defaultAccount.get("shipping_address_street"));
		accountFull.put("shipping_address_street", defaultAccount.get("shipping_address_street"));
		this.SHIPPING_CITY.set(defaultAccount.get("shipping_address_city"));
		accountFull.put("shipping_address_city", defaultAccount.get("shipping_address_city"));
		this.SHIPPING_POSTALCODE.set(defaultAccount.get("shipping_address_postalcode"));
		accountFull.put("shipping_address_postalcode", defaultAccount.get("shipping_address_postalcode"));
		//additional info
		this.OFFICE_PHONE.set(defaultAccount.get("phone_office"));
		accountFull.put("phone_office", defaultAccount.get("phone_office"));
		this.FAX.set(defaultAccount.get("phone_fax"));
		accountFull.put("phone_fax", defaultAccount.get("phone_fax"));
		this.WEBSITE.set(defaultAccount.get("website"));
		accountFull.put("website", defaultAccount.get("website"));
		//industry solution
		this.selectISU(defaultAccount.get("industry_solution_input"), defaultAccount.get("industry_solution_select"));
		this.selectIndustry(defaultAccount.get("industry_input"), defaultAccount.get("industry_select"));
		this.selectIndustryClass(defaultAccount.get("industry_class_input"), defaultAccount.get("industry_class_select"));
		this.selectIndustrySolutionClassification(defaultAccount.get("ISIC_input"), defaultAccount.get("ISIC_select"));
		this.selectQuadTierCode(defaultAccount.get("gb_input"), defaultAccount.get("gb_select"));				
		
		sugar.accounts.editView.save();
		//acceptDialog - confirm create for build > 5
		VoodooUtils.confirmCreateDialog();
		
		VoodooUtils.switchBackToWindow();
		VoodooUtils.pause(5000);
		sugar.accounts.navToRecord(accountFull.get("name"));
		VoodooUtils.log.info(" accountCreateLibFull complete");
		return accountFull;
	}
		
}
