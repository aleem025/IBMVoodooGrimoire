package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Contacts_23503 extends SugarTest {
	AccountRecord acc1;

	public void setup() throws Exception {		
		sugar.login();		
		acc1 = (AccountRecord) sugar.accounts.api.create();
		acc1.navToRecord();
	}

	/**
	 *  Verify that contact can be created through sub panel
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		DataSource ds = testData.get(testName);
		sugar.contacts.subpanel.addRecord();
		sugar.contacts.createDrawer.getEditField("lastName").set(ds.get(0).get("lastname"));
		sugar.contacts.createDrawer.save();
		VoodooUtils.waitForAlertExpiration();
		// TODO VOOD-609
		new VoodooControl("div", "css", "div.layout_Contacts").assertElementContains(ds.get(0).get("lastname"),true);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}

}