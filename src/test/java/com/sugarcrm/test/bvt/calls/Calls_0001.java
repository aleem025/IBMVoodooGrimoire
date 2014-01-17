/* QA UPDATES PACKAGE TO MATCH THE TEST FILENAME AND LOCATION */
package com.sugarcrm.test.bvt.calls;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.views.CreateCallView;

public class Calls_0001 extends SugarTest {
	FieldSet call;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
	}
	/** Creating new call through full form 
	 *  Open it in detail view and verify if all call info saved correctly
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		call = sugar.callCreateView.createCallLibFull();
		sugar.calls.navToRecord(call.get("call_name"));						
		sugar.calls.verifyDetailView(call);
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.calls.deleteRecord(call.get("call_name"));
		sugar.logout();
	}
}