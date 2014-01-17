package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Contacts_23559 extends SugarTest {
	ContactRecord con1, con2;
	Subpanel contactSub;
	DataSource ds;
	public void setup() throws Exception {
		sugar.login();
		ds= testData.get(testName);
		con1 = (ContactRecord) sugar.contacts.api.create(ds.get(0));
		con2 = (ContactRecord) sugar.contacts.api.create();
		con1.navToRecord();                
	}

	/** 
	 * Verify that the selected contacts are displayed in "Direct Report" sub-panel of "Contact Detail View" page
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");		
		
		contactSub = new Subpanel(sugar.contacts);
		// TODO VOOD-634
		//contactSub.linkExistingRecord();
		new VoodooControl("a", "css", "div.filtered.layout_Contacts a.btn.dropdown-toggle").click();
		contactSub.getControl("linkExistingRecord").click();
		
		new VoodooControl("input", "css", "input[name='Contacts_select']").click();
		VoodooUtils.waitForAlertExpiration();             
        // TODO VOOD-609
        new VoodooControl("a", "css", "div[data-voodoo-name='Contacts'] td[data-type='fullname'] a").assertContains(con2.recordData.get("salutation")+" "+con2.recordData.get("firstName")+" "+con2.recordData.get("lastName"), true);
       
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {		
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}