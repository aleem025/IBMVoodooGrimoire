package com.sugarcrm.test.bvt.opportunities;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Opportunities_0002 extends SugarTest {
	private FieldSet opportunity;
	private FieldSet opportunity_edited;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
		opportunity = sugar.opportunityCreateView.opportunityCreateLibFull();
	}
	
	/** Edit oppty and verify if changes are applied. 
	 *  Verify type-ahead feature
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		DataSource ds = testData.get(testName);
		opportunity_edited = new FieldSet();
		opportunity_edited= (FieldSet) ds.get(0).clone();
				
		sugar.opportunities.navToRecord(opportunity.get("opportunity_number"));
		sugar.opportunities.detailView.edit();
		VoodooUtils.switchToBWCFrame();
		sugar.opportunityCreateView.DESCRIPTION.assertVisible(true);
		sugar.opportunityCreateView.DESCRIPTION.set(ds.get(0).get("description"));
		sugar.opportunityCreateView.SALES_STAGE.set(ds.get(0).get("sales_stage"));
		sugar.opportunityCreateView.REASON_WON.set(ds.get(0).get("reason_won"));
		//TODO: desicion date is disabled - bug 40361
		//sugar.opportunityCreateView.DECISION_DATE.set(ds.get(0).get("date_closed"));
		sugar.opportunityCreateView.TAGS.set(ds.get(0).get("tags"));
		
		//Verifying type-ahead for contact's field
		sugar.opportunityCreateView.CONTACT_CLEAR.click();
		sugar.opportunityCreateView.PRIMARY_CONTACT.assertAttribute("value", "", true);
		//sugar.opportunityCreateView.PRIMARY_CONTACT.set(opportunity.get("contact_searchname"));
		//TODO: typeahead at primary contact doesnt work. Bug 
		//new VoodooControl("li", "xpath", "//li[@data-text='" + opportunity.get("contact_fullname") + "']").assertVisible(true);
		
		// Verifying type-ahead for client's field
		sugar.opportunityCreateView.CLIENT_CLEAR.click();
		sugar.opportunityCreateView.CLIENT_NAME.assertAttribute("value", "", true);
		//sugar.opportunityCreateView.CLIENT_NAME.set(opportunity.get("client_fullname"));
		//TODO: typeahead at client field doesnt work
		//new VoodooControl("li", "xpath", "//li[@data-text='" + opportunity.get("client_fullname") + "']").assertVisible(true);
		
		//Set contact and client through picker pop-up
		sugar.opportunityCreateView.selectContactFromPopup(opportunity.get("contact_searchname"), opportunity.get("contact_fullname"));
		sugar.opportunityCreateView.selectClientFromPopup(opportunity.get("client_fullname"));
		
		sugar.opportunities.editView.save();
		VoodooUtils.switchBackToWindow();
		VoodooUtils.switchToBWCFrame();
		sugar.opportunityCreateView.OPPORTUNITY_NUMBER.waitForVisible(2000);
		VoodooUtils.switchBackToWindow();
		
		//Verify oppty edited correctly
		sugar.opportunities.verifyDetailView(opportunity_edited);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		//TODO: in seems doesn't work
		//sugar.opportunities.api.deleteRecord(opportunity);
		sugar.logout();
	}
}
