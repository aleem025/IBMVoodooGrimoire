package com.sugarcrm.test.ListView;

import org.junit.Test;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;

public class ListView_16985 extends SugarTest {	
  FieldSet accountRecord;
  AccountRecord myAccount;

  public void setup() throws Exception {
	accountRecord = testData.get("ListView_16985").get(0);
    sugar.login();
    myAccount = (AccountRecord)sugar.accounts.api.create(); 
  }
      
  /**
   *  Verify cancel of inline edits on Accounts.
   */
  @Test
  public void execute() throws Exception {
    VoodooUtils.voodoo.log.info("Running " + testName + "...");

    // Go to Account list view and select inline edit button.
    sugar.accounts.navToListView();
    sugar.accounts.listView.editRecord(1);
    
    // TODO: JIRA story VOOD-612 about the ability to perform inline edit in Account list view.
    new VoodooControl("input", "css", "tr.single.tr-inline-edit input[name='name']").set(accountRecord.get("name"));
    new VoodooControl("input", "css", "tr.single.tr-inline-edit input[name='billing_address_city']").set(accountRecord.get("billingAddressCity"));
    new VoodooControl("input", "css", "tr.single.tr-inline-edit input[name='billing_address_country']").set(accountRecord.get("billingAddressCountry"));
    new VoodooControl("input", "css", "tr.single.tr-inline-edit input[name='phone_office']").set(accountRecord.get("workPhone"));
    new VoodooControl("input", "css", "tr.single.tr-inline-edit input[name='email']").set(accountRecord.get("emailAddress"));
    
    // Click "Cancel".
    sugar.accounts.listView.cancelRecord(1);
    VoodooUtils.pause(5000);
   
    // Verify all edited fields show previous values.
    sugar.accounts.listView.verifyField(1, "name", myAccount.recordData.get("name"));
    sugar.accounts.listView.verifyField(1, "billing_address_city", myAccount.recordData.get("billingAddressCity"));
    sugar.accounts.listView.verifyField(1, "billing_address_country", myAccount.recordData.get("billingAddressCountry"));
    sugar.accounts.listView.verifyField(1, "phone_office", myAccount.recordData.get("workPhone"));
    sugar.accounts.listView.verifyField(1, "email", "");
    
    VoodooUtils.voodoo.log.info(testName + " complete.");
  }

  public void cleanup() throws Exception {
    sugar.accounts.api.deleteAll();
    sugar.logout();
  }
}