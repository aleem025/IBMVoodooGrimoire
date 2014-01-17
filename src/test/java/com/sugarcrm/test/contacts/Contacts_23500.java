package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Contacts_23500 extends SugarTest {
	
	public void setup() throws Exception {		
		sugar.login();		
	}

	/**
	 *  Verify that contact can be created through "Create Contact" mega menu.
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds = testData.get(testName);
		sugar.contacts.navToListView();				
		sugar.navbar.selectMenuItem(sugar.contacts,ds.get(0).get("menu"));
		sugar.contacts.createDrawer.getEditField("lastName").set(ds.get(0).get("lastname"));
		sugar.contacts.createDrawer.saveAndView();
		// TODO VOOD-582
		VoodooUtils.pause(3000);
		
		// TODO VOOD-581
		new VoodooControl("span", "css", "span[data-fieldname='full_name']").assertContains(ds.get(0).get("lastname"),true);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}

}
