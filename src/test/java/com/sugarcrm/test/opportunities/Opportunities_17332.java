package com.sugarcrm.test.opportunities;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.OpportunityRecord;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.candybean.datasource.DataSource;

public class Opportunities_17332 extends SugarTest {
	AccountRecord account1, account2;
	OpportunityRecord myOpp;
	DataSource oppRecords;
	
	public void setup() throws Exception {
		sugar.login();
				
		account1 = (AccountRecord)sugar.accounts.api.create();
				
		//modified account1 name.
		oppRecords = testData.get("Opportunities_17332");	
		
		FieldSet newData = new FieldSet();
		newData.put("name", oppRecords.get(0).get("accountName"));
		account1.edit(newData);
		
		//create account2
		account2 = (AccountRecord)sugar.accounts.api.create();

		//create an opp.
		myOpp = (OpportunityRecord)sugar.opportunities.create();	
	}

	/**
	 * Verify Cancel button work properly under Opportunities Record edit view 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
	
		myOpp.navToRecord();

		// edit opp record and click Cancel.
		FieldSet newData = new FieldSet();	
		newData.put("nextStep", oppRecords.get(0).get("nextStep"));
		newData.put("description", oppRecords.get(0).get("description"));
		newData.put("relAccountName", account1.getRecordIdentifier());
	
		sugar.opportunities.recordView.edit();
		sugar.opportunities.recordView.showMore();
		sugar.opportunities.recordView.setFields(newData);
		
		sugar.opportunities.recordView.cancel();
	
		// Verify with origin data.
		myOpp.verify();	

		// click Show Less to reset the view.
		sugar.opportunities.recordView.showLess();
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.opportunities.api.deleteAll();
		sugar.logout();
	}
}
