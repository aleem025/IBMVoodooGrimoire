package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
//import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;

public class Contacts_23504 extends SugarTest {
	ContactRecord con1;

	public void setup() throws Exception {		
		sugar.login();		
		con1 =  (ContactRecord) sugar.contacts.api.create();
		con1.navToRecord();
	}

	/**
	 *  Verify that contact can be duplicated
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds = testData.get(testName);
		sugar.contacts.recordView.copy();
		sugar.contacts.createDrawer.getEditField("firstName").set(ds.get(0).get("firstname"));
		sugar.contacts.createDrawer.getEditField("lastName").set(ds.get(0).get("lastname"));
		sugar.contacts.createDrawer.saveAndView();
		
		// TODO VOOD-582
		VoodooUtils.pause(3000);
			
		// TODO VOOD-581
		new VoodooControl("span", "css", "span[data-fieldname='full_name']").assertContains(ds.get(0).get("firstname")+" "+ds.get(0).get("lastname"),true);
		
		// TODO VOOD-583 contact verify() function not work
		/*FieldSet dupCon = con1.recordData;
		dupCon.put("firstName", ds.get(0).get("firstname"));
		dupCon.put("lastName", ds.get(0).get("lastname"));
		ContactRecord con2 = new ContactRecord(dupCon);
		con2.verify();*/
				
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}

}