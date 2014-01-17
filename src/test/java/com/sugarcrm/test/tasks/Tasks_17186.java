package com.sugarcrm.test.tasks;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest; 

public class Tasks_17186 extends SugarTest {
	
	@Override
	public void setup() throws Exception {	
		sugar.login();	
		//A new task is in Not Started status
		sugar.tasks.api.create();  
	}

	/**
	 * Verify close and create new action in the Tasks record view 
	 * @throws Exception
	 */	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "..."); 
		
		sugar.tasks.navToListView();    
        sugar.tasks.listView.clickRecord(1);
        // TODO: VOOD-607.  Once it is fixed, please update the following steps.
        new VoodooControl("span", "css", ".btn.dropdown-toggle.btn-primary span").click();
        new VoodooControl("a", "css", ".fld_record-close-new.detail a").click();
        new VoodooControl("span", "css", ".fld_status.edit span").assertContains("Not Started", true);
        new VoodooControl("a", "css", ".fld_save_button.detail a").click();
        
        sugar.tasks.navToListView();  
        // TODO: VOOD-606.  Once it is fixed, please update the following steps.
        //1st record, which is created from above steps.          
        sugar.tasks.listView.previewRecord(1);
        new VoodooControl("div", "css", ".fld_status.detail div").assertContains("Not Started", true);
        
        //2nd record. Look at previous of the record. Status=Completed
        sugar.tasks.navToListView();  
        sugar.tasks.listView.previewRecord(2);
        new VoodooControl("div", "css", ".fld_status.detail div").assertContains("Completed", true);
        
		VoodooUtils.voodoo.log.info(testName + " complete."); 
	}

	@Override
	public void cleanup() throws Exception {
		sugar.tasks.api.deleteAll();
		sugar.logout();
	}
}
