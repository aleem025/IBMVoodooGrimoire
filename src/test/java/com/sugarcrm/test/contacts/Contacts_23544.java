package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Contacts_23544 extends SugarTest {
	ContactRecord con1;
	Subpanel leadSub;
	DataSource ds = new DataSource();
        
	public void setup() throws Exception {
		sugar.login();
		con1 = (ContactRecord) sugar.contacts.api.create();
		ds = testData.get(testName);
		con1.navToRecord();
		leadSub = new Subpanel(sugar.leads);
		leadSub.addRecord();
		sugar.leads.createDrawer.getEditField("lastName").set(ds.get(0).get("lastName"));
		sugar.leads.createDrawer.save();    
        }

        /** 
         * Verify that a related lead can be edited from contact record view 
         * @throws Exception
         */
        @Test
        public void execute() throws Exception {
        	VoodooUtils.voodoo.log.info("Running " + testName + "...");
        	
        	leadSub.editRecord(1);        	
        	// TODO VOOD-503
        	new VoodooControl("input", "css", "input[name='first_name']").set(ds.get(0).get("firstName"));
        	new VoodooControl("input", "css", "input[name='last_name']").set(ds.get(0).get("lastName2"));
        	new VoodooControl("input", "css", "input[name='phone_work']").set(ds.get(0).get("phoneWork"));
        	leadSub.saveAction(1); 
        	VoodooUtils.waitForAlertExpiration();       	
        	
        	// TODO VOOD-609
        	new VoodooControl("a", "css", "div[data-voodoo-name='Leads'] td[data-type='fullname'] a").assertContains(ds.get(0).get("firstName")+" "+ds.get(0).get("lastName2"), true);
        	new VoodooControl("div", "css", "div[data-voodoo-name='Leads'] td[data-type='phone'] div").assertContains(ds.get(0).get("phoneWork"),true);
        	VoodooUtils.voodoo.log.info(testName + " complete.");
        }

        public void cleanup() throws Exception {
        	sugar.contacts.api.deleteAll();
        	sugar.leads.api.deleteAll();
        	sugar.logout();
        }
}