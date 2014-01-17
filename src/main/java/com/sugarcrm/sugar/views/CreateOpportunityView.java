package com.sugarcrm.sugar.views;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;

public class CreateOpportunityView extends View{
	protected static CreateOpportunityView opportunityCreateView;
	protected static AppModel sugar;
	public final VoodooControl DESCRIPTION;
	public final VoodooControl PRIMARY_CONTACT;
	public final VoodooControl CONTACT_SELECT;
	public final VoodooControl CONTACT_CLEAR;
	public final VoodooControl CLIENT_NAME;
	public final VoodooControl CLIENT_SELECT;
	public final VoodooControl CLIENT_CLEAR;
	public final VoodooControl OPPTY_CODES;
	public final VoodooControl SALES_STAGE;
	public final VoodooControl REASON_WON;
	public final VoodooControl REASON_LOST;
	public final VoodooControl DECISION_DATE;
	public final VoodooControl CURRENCY;
	public final VoodooControl TAGS;
	public final VoodooControl SOURCE;
	
	public final VoodooControl OFFERING;
	public final VoodooControl BROWSE_OFFERING;
	public final VoodooControl AMOUNT;
	public final VoodooControl PROBABILITY;
	public final VoodooControl DURATION;
	public final VoodooControl DATE;
	public final VoodooControl DATE_PICKER;
	public final VoodooControl OWNER;
	public final VoodooControl OWNER_SELECT;
	public final VoodooControl OWNER_CLEAR;
	public final VoodooControl RLI_ADD_ANOTHER;
	
	public final VoodooControl OPPTY_OWNER;
	public final VoodooControl OPPTY_OWNER_SELECT;
	public final VoodooControl OPPTY_OWNER_CLEAR;
	public final VoodooControl OTHER_PARTICIPATING;
	//TODO: additional team members
	
	public final VoodooControl CAMPAING_CODE;
	public final VoodooControl RELATED_OPPY_NUMBER;
	public final VoodooControl RELATED_OPPY_SELECT;
	public final VoodooControl RELATED_OPPY_RELATIONSHIP;
	public final VoodooControl RELATED_OPPY_ADD_ANOTHER;
	public final VoodooControl RISK_ASSESSMENT;
	public final VoodooControl RISK_ASSESSMENT_CALCULATE;
	public final VoodooControl RESTRICTIONS;
	public final VoodooControl INDUSTRY;
	public final VoodooControl INDUSTRY_SELECT;
	public final VoodooControl INDUSTRY_CLEAR;
	public final VoodooControl INTERNATIONAL;
	
	public final VoodooControl OPPORTUNITY_NUMBER;
	
	private DataSource ds;
	public FieldSet defaultOpportunity;
	private FieldSet opportunityFull;
	private FieldSet contact;
	private FieldSet client;
	
	public CreateOpportunityView() throws Exception {
		super();
		sugar = AppModel.getInstance();
		ds = VoodooUtils.getData("CreateOpportunityView");
		defaultOpportunity = (FieldSet) ds.get(0).clone();
		opportunityFull = new FieldSet();
		
		DESCRIPTION = new VoodooControl("textarea", "id", "description");
		PRIMARY_CONTACT = new VoodooControl("input","id","pcontact_id_c");
		CONTACT_SELECT = new VoodooControl("button","id","btn_pcontact_id_c");
		CONTACT_CLEAR = new VoodooControl("button","id","btn_clr_pcontact_id_c");
		CLIENT_NAME = new VoodooControl("input","id","account_name");
		CLIENT_SELECT = new VoodooControl("button","id","btn_account_name");
		CLIENT_CLEAR = new VoodooControl("button","id","btn_clr_account_name");
		OPPTY_CODES = new VoodooControl("input","id","solution_codes_c_ac");
		SALES_STAGE = new VoodooControl("select","id","sales_stage");
		REASON_WON = new VoodooControl("select","id","reason_won_c");
		REASON_LOST = new VoodooControl("select","id","reason_lost_c");
		DECISION_DATE = new VoodooControl("input","id","date_closed");
		CURRENCY = new VoodooControl("select","id","currency_id_select");
		TAGS = new VoodooControl("input","id","tags");
		SOURCE = new VoodooControl("select","id","lead_source");
		
		OFFERING = new VoodooControl("input","id","offering_1-input");
		BROWSE_OFFERING = new VoodooControl("a","id","rli_browseSolution_1");
		AMOUNT = new VoodooControl("input","id","revenue_amount_1");
		PROBABILITY = new VoodooControl("select","id","probability_1");
		DURATION = new VoodooControl("input","id","duration_1");
		DATE = new VoodooControl("input","id","duration_1");
		DATE_PICKER = new VoodooControl("span","id","fcast_date_sign_1_trigger");
		OWNER = new VoodooControl("input","id","assigned_user_name_1");
		OWNER_SELECT = new VoodooControl("button","id","btn_assigned_user_name_1");
		OWNER_CLEAR = new VoodooControl("button","id","removebutn_1");
		RLI_ADD_ANOTHER = new VoodooControl("a","id","rli_addAnotherLink");
		
		OPPTY_OWNER = new VoodooControl("input","id","assigned_user_name");
		OPPTY_OWNER_SELECT = new VoodooControl("button","id","btn_assigned_user_name");
		OPPTY_OWNER_CLEAR = new VoodooControl("button","id","btn_clr_assigned_user_name");
		OTHER_PARTICIPATING = new VoodooControl("select","id","cross_lob");
		//TODO: additional team members
		
		CAMPAING_CODE = new VoodooControl("input","id","campaign_code_c_ac");
		RELATED_OPPY_NUMBER = new VoodooControl("input","id","EditView_related_opportunities_relate_item_1");
		RELATED_OPPY_SELECT = new VoodooControl("button","id","EditView_related_opportunities_btn_select_1");
		RELATED_OPPY_RELATIONSHIP = new VoodooControl("select","id","EditView_related_opportunities_reason_code_1");
		RELATED_OPPY_ADD_ANOTHER = new VoodooControl("a","id","addRelatedOpportunityLink");
		RISK_ASSESSMENT = new VoodooControl("input","id","risk_assessment");
		RISK_ASSESSMENT_CALCULATE = new VoodooControl("img","id","btt_calc_open");
		RESTRICTIONS = new VoodooControl("select","id","restricted");
		INDUSTRY = new VoodooControl("input","id","cmr_c");
		INDUSTRY_SELECT = new VoodooControl("button","id","btn_cmr_c");
		INDUSTRY_CLEAR = new VoodooControl("button","id","btn_clr_cmr_c");
		INTERNATIONAL = new VoodooControl("input","id","international_c");
		
		OPPORTUNITY_NUMBER = new VoodooControl("span","id","name");
		
	}
	
	/**
	 * Initializes the OpportunityCreateView screen.
	 * @throws Exception
	 */	
	public static CreateOpportunityView getInstance() throws Exception {
		if (opportunityCreateView == null) opportunityCreateView = new CreateOpportunityView();
		return opportunityCreateView;
	}
	
	public void selectContact(String contactSearch, String contactName) throws Exception{
		this.PRIMARY_CONTACT.set(contactSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + contactName + "']").click();
	}
	
	public void selectContactFromPopup(String contactSearch, String contactName) throws Exception{
		this.CONTACT_SELECT.click();
		sugar.opportunities.searchForm.popupSelectRecord(contactName);
	}
	
	public void selectClient(String clientSearch, String clientName) throws Exception{
		this.CLIENT_NAME.set(clientSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + clientName + "']").click();
	}
	public void selectClientFromPopup(String clientName) throws Exception{
		this.CLIENT_SELECT.click();
		VoodooUtils.focusWindow("SalesConnect");
		new VoodooControl("a", "xpath", "//a[contains(text(), '" + clientName + "')]").click();
	    VoodooUtils.switchBackToWindow();	  
	    VoodooUtils.switchToBWCFrame();
		//sugar.opportunities.searchForm.popupSelectRecord(clientName);
	}
	public void selectOpportunityCode(String codeSearch, String codeName) throws Exception{
		this.OPPTY_CODES.set(codeSearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + codeName + "']").click();
	}
	public void selectOffering(String offeringSearch, String offeringName) throws Exception{
		this.OFFERING.set(offeringSearch);
		new VoodooControl("li", "xpath", "//li[contains(@data-text,'" + offeringName + "')]").click();
	}
	
	public FieldSet opportunityCreateLibFull() throws Exception{		
		//TODO: create contact linked to client
		contact = sugar.contacts.api.createFS();
		client = sugar.accounts.api.createFS();
		
		VoodooUtils.log.info("Running contactCreateLibFull...");
		sugar.navbar.navToModule("Opportunities");
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");		
		opportunityFull.put("description", defaultOpportunity.get("description") + " " +VoodooUtils.username+" "+ timeStamp);
		opportunityFull.put("contact_fullname", contact.get("firstName")+ " " + contact.get("lastName"));
		opportunityFull.put("contact_searchname", contact.get("lastName"));
		opportunityFull.put("client_fullname", client.get("name"));
		
		VoodooUtils.switchToBWCFrame();
		//set description
		this.DESCRIPTION.assertVisible(true);
		this.DESCRIPTION.set(opportunityFull.get("description"));
		//set contact
		this.selectContactFromPopup(opportunityFull.get("contact_searchname"), opportunityFull.get("contact_fullname"));
		//this.selectContact(opportunityFull.get("contact_searchname"), opportunityFull.get("contact_fullname"));
		//set client
		this.selectClientFromPopup(opportunityFull.get("client_fullname"));
		//this.selectClient(opportunityFull.get("client_fullname"), opportunityFull.get("client_fullname"));
		//set solution code
		opportunityFull.put("solution_code",defaultOpportunity.get("solution_code"));
		this.selectOpportunityCode(opportunityFull.get("solution_code"), opportunityFull.get("solution_code"));
		//set sales stage
		opportunityFull.put("sales_stage", defaultOpportunity.get("sales_stage"));
		this.SALES_STAGE.set(opportunityFull.get("sales_stage"));
		//TODO: set decision date
		opportunityFull.put("decision_date", defaultOpportunity.get("date_closed"));
		//this.DECISION_DATE.set(opportunityFull.get("decision_date"));
		//set tags
		opportunityFull.put("tags", timeStamp);
		this.TAGS.set(opportunityFull.get("tags"));
		//set source
		opportunityFull.put("lead_source",  defaultOpportunity.get("lead_source"));
		this.SOURCE.set(opportunityFull.get("lead_source"));
		//set offering/solution
		opportunityFull.put("offering_search", defaultOpportunity.get("off_search"));
		opportunityFull.put("offering_name", defaultOpportunity.get("offering"));
		this.selectOffering(opportunityFull.get("offering_search"), opportunityFull.get("offering_name"));
		//set revenue amount
		opportunityFull.put("revenue_amount", defaultOpportunity.get("revenue_amount"));
		this.AMOUNT.set(opportunityFull.get("revenue_amount"));
		
		sugar.opportunities.editView.save();
		VoodooUtils.switchBackToWindow();
		VoodooUtils.switchToBWCFrame();
		this.OPPORTUNITY_NUMBER.assertVisible(true);
		opportunityFull.put("opportunity_number", this.OPPORTUNITY_NUMBER.getText());
		VoodooUtils.switchBackToWindow();
		return opportunityFull;
	}
}
