package com.sugarcrm.test.contacts;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Contacts_23552 extends SugarTest {
	ContactRecord con1;
	CaseRecord case1;
	
	public void setup() throws Exception {
		sugar.login();
		con1 = (ContactRecord) sugar.contacts.api.create();
		case1 = (CaseRecord) sugar.cases.api.create();
		con1.navToRecord();
        }

        /** 
         * Verify that a related case can be selected into a contact from contact record view 
         * @throws Exception
         */
        @Test
        public void execute() throws Exception {
        	VoodooUtils.voodoo.log.info("Running " + testName + "...");
        	
        	Subpanel caseSub = new Subpanel(sugar.cases);
        	caseSub.linkExistingRecord();
        	
        	new VoodooControl("input", "css", "input[name='Cases_select']").click();
        	             
        	// TODO VOOD-609
        	new VoodooControl("a", "css", "div[data-voodoo-name='Cases'] span.fld_name.list a").assertContains(case1.getRecordIdentifier(), true);
        	
        	VoodooUtils.voodoo.log.info(testName + " complete.");
        }

        public void cleanup() throws Exception {
        	sugar.contacts.api.deleteAll();
        	sugar.cases.api.deleteAll();
        	sugar.logout();
        }
}