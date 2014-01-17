package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Alerts;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Contacts_23545 extends SugarTest {
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
         * Verify that a related lead can be unlink from contact record view 
         * @throws Exception
         */
        @Test
        public void execute() throws Exception {
        	VoodooUtils.voodoo.log.info("Running " + testName + "...");
        	
        	leadSub.unlinkRecord(1);   
        	Alerts alert = new Alerts();
        	alert.confirmAlert();
        	VoodooUtils.waitForAlertExpiration();
        	
        	// TODO VOOD-609
        	new VoodooControl("a", "css", "div[data-voodoo-name='Leads'] div.flex-list-view-content tbody").assertElementContains(ds.get(0).get("lastName"), false);
        	VoodooUtils.voodoo.log.info(testName + " complete.");
        }

        public void cleanup() throws Exception {
        	sugar.contacts.api.deleteAll();
        	sugar.leads.api.deleteAll();
        	sugar.logout();
        }
}