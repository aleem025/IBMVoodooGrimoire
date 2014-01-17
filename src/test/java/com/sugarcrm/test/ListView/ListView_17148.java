package com.sugarcrm.test.ListView;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;

public class ListView_17148 extends SugarTest {
  FieldSet accountRecord;
  
  public void setup() throws Exception { 
	accountRecord = testData.get("ListView_17148").get(0);
    
	sugar.login();
    sugar.accounts.api.create();
    sugar.contacts.api.create();  
  }
      
  /**
   *  Related-to widget select value inline edit mode
   */
  @Test
  public void execute() throws Exception {
    VoodooUtils.voodoo.log.info("Running "  + testName + "...");

    // Go to Contact list view
    sugar.contacts.navToListView();
    sugar.contacts.listView.editRecord(1);  
    // TODO: JIRA story VOOD-612 about the ability to perform inline edit in list view.
    new VoodooSelect("a", "css", ".fld_account_name.edit a").set(accountRecord.get("name"));  
    // TODO: JIRA story VOOD-624 about ability to select confirm to continue copy values to Contact.
    new VoodooControl("a", "css", "div.alert.alert-warning.alert-block a.btn-link.confirm").waitForElement();    
    new VoodooControl("a", "css", "div.alert.alert-warning.alert-block a.btn-link.confirm").click();
  
   // Verify the selected account is displayed on "Account Name" field.
    sugar.contacts.listView.saveRecord(1);
    //TODO: JIRA story VOOD-626 about add element definitions into six ListView columns
    sugar.contacts.listView.verifyField(1, "relAccountName", accountRecord.get("name"));
    
    VoodooUtils.voodoo.log.info(testName  + " complete.");
  }

  public void cleanup() throws Exception {
    sugar.accounts.api.deleteAll();
    sugar.contacts.api.deleteAll();
    sugar.logout();
  }
}