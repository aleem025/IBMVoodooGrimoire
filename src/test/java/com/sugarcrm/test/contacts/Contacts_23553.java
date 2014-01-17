package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Contacts_23553 extends SugarTest {
	ContactRecord con1;
	CaseRecord case1;
	AccountRecord acc1, acc2;
	Subpanel caseSub;
	DataSource ds;
	public void setup() throws Exception {
		sugar.login();
		ds= testData.get(testName);
		con1 = (ContactRecord) sugar.contacts.api.create();
		case1 = (CaseRecord) sugar.cases.api.create();
		acc1 = (AccountRecord) sugar.accounts.api.create();
		
		FieldSet newData1 = new FieldSet();
		newData1.put("relAccountName", acc1.getRecordIdentifier());
		case1.edit(newData1);
		
		FieldSet newData2 = new FieldSet();
		newData2.put("name", ds.get(0).get("account"));
		acc2 = (AccountRecord) sugar.accounts.api.create(newData2);
		
		con1.navToRecord();                
		caseSub = new Subpanel(sugar.cases);
		caseSub.linkExistingRecord();                
		new VoodooControl("input", "css", "input[name='Cases_select']").click();
		VoodooUtils.waitForAlertExpiration();
		// TODO VOOD-609
		new VoodooControl("a", "css", "div[data-voodoo-name='Cases'] span.fld_name.list a").assertContains(case1.getRecordIdentifier(), true);
	}

	/** 
	 * Verify that related case can be edited from contact detail view. 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		caseSub.editRecord(1);
		
		// TODO VOOD-503
		new VoodooControl("input", "css", "input[name='name']").set(ds.get(0).get("name"));
		new VoodooSelect("span", "css", "span.fld_account_name.edit").set(acc2.getRecordIdentifier());
		new VoodooSelect("span", "css", "span.fld_status.edit").set(ds.get(0).get("status"));
		new VoodooSelect("span", "css", "span.fld_assigned_user_name.edit").set(ds.get(0).get("user"));
		caseSub.saveAction(1);
		VoodooUtils.waitForAlertExpiration();
		
		// TODO VOOD-609
		new VoodooControl("a", "css", "div[data-voodoo-name='Cases'] span.fld_name.list a").assertContains(ds.get(0).get("name"), true);
		new VoodooControl("a", "css", "div[data-voodoo-name='Cases'] span.fld_account_name.list a").assertContains(acc2.getRecordIdentifier(), true);
		new VoodooControl("a", "css", "div[data-voodoo-name='Cases'] span.fld_status.list a").assertContains(ds.get(0).get("status"), true);
		new VoodooControl("a", "css", "div[data-voodoo-name='Cases'] span.fld_assigned_user_name.list a").assertContains(ds.get(0).get("user"), true);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.cases.api.deleteAll();
		sugar.logout();
	}
}