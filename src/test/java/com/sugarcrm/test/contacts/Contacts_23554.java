package com.sugarcrm.test.contacts;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Alerts;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Contacts_23554 extends SugarTest {
	ContactRecord con1;
	CaseRecord case1;
	Subpanel caseSub;
	
	public void setup() throws Exception {
		sugar.login();		
		con1 = (ContactRecord) sugar.contacts.api.create();
		case1 = (CaseRecord) sugar.cases.api.create();		
		con1.navToRecord();                
		caseSub = new Subpanel(sugar.cases);
		caseSub.linkExistingRecord();                
		new VoodooControl("input", "css", "input[name='Cases_select']").click();
		VoodooUtils.waitForAlertExpiration();
		// TODO VOOD-609
		new VoodooControl("a", "css", "div[data-voodoo-name='Cases'] span.fld_name.list a").assertContains(case1.getRecordIdentifier(), true);
	}

	/** 
	 * Verify that related case can be unlinked from contact detail view 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		caseSub.unlinkRecord(1);   
		Alerts alert = new Alerts();
		alert.confirmAlert();
		VoodooUtils.waitForAlertExpiration();		
		// TODO VOOD-609
		new VoodooControl("tbody", "css", "div[data-voodoo-name='Cases'] div.flex-list-view-content tbody").assertContains(case1.getRecordIdentifier(), false);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.cases.api.deleteAll();
		sugar.logout();
	}
}