package com.sugarcrm.test.bvt.opportunities;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Opportunities_0001 extends SugarTest {
	FieldSet opportunity;
	
	public void setup() throws Exception {
		sugar.login_regularUser();
	}
	
	/** Creating new opportunity through full form 
	 *  Open it in detail view and verify if all opportunity info saved correctly
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		opportunity = sugar.opportunityCreateView.opportunityCreateLibFull();
		sugar.opportunities.navToRecord(opportunity.get("opportunity_number"));						
		sugar.opportunities.verifyDetailView(opportunity);
		VoodooUtils.pause(20000);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		//TODO: in seems doesnt work
		sugar.opportunities.api.deleteRecord(opportunity);
		sugar.logout();
	}
}
