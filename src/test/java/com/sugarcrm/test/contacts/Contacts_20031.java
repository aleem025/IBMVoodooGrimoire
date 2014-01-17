package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;

public class Contacts_20031 extends SugarTest {
	ContactRecord myContact;	
	
	public void setup() throws Exception {
		sugar.login();		
		myContact = (ContactRecord) sugar.contacts.api.create();			
	}

	/**
	 * Verify "Search for more" brings up selection view.
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		DataSource ds = testData.get(testName);
		myContact.navToRecord();
		sugar.contacts.recordView.showMore();
		sugar.contacts.recordView.getDetailField("relAssignedTo").hover();
		
		//TODO VOOD-552 
		new VoodooControl("i", "css", "div[data-name='assigned_user_name'] span.record-edit-link-wrapper i").click();
		new VoodooControl("div", "xpath", "//div[@id='select2-drop']//div[text()='Search for more...']").click();
		new VoodooControl("div", "css", "div.layout_Users.drawer.transition").assertVisible(true);	
		new VoodooControl("input", "css", "div.flex-list-view-content table tbody tr:nth-child(1) input").assertVisible(true);
		new VoodooControl("input", "css", "div.flex-list-view-content table tbody tr:nth-child(2) input").assertVisible(true);	
		
		new VoodooControl("tr", "css", "div.flex-list-view-content table tbody tr:nth-child(1)").assertElementContains(ds.get(0).get("username"), true);
		new VoodooControl("tr", "css", "div.flex-list-view-content table tbody tr:nth-child(2)").assertElementContains(ds.get(1).get("username"), true);
		new VoodooControl("input", "css", "div.flex-list-view-content input[name='Users_select']:nth-child(1)").click();
		VoodooUtils.pause(1000);
		new VoodooControl("div", "css", "div[data-name='assigned_user_name']").assertElementContains(ds.get(0).get("username"), true);		
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.logout();		
	}
}