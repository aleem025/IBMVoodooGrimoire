package com.sugarcrm.test.grimoire;

import java.util.ArrayList;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.Module;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.OpportunityRecord;
import com.sugarcrm.sugar.records.RevLineItemRecord;

public class Enable_Disable_module_subpanel extends SugarTest {
	AccountRecord myAcc;
	OpportunityRecord myOpp;
	RevLineItemRecord myRev;
	
	public void setup() throws Exception {
		sugar.login();
		myAcc = (AccountRecord)sugar.accounts.api.create();
	}
	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Test enableModuleDisplay(module)
		sugar.admin.enableModuleDisplay(sugar.revLineItems);
		// TODO: update the following line and all similar lines to use the new NavBar method from JIRA VOOD-396
		new VoodooControl("a", "css", "ul#module_list.nav li.dropdown.more a.dropdown-toggle").click();
		new VoodooControl("li","css","li[data-module='RevenueLineItems']").assertExists(true);
		
		// Test disableModuleDisplay(module)
		sugar.admin.disableModuleDisplay(sugar.revLineItems);
		new VoodooControl("a", "css", "ul#module_list.nav li.dropdown.more a.dropdown-toggle").click();
		new VoodooControl("li","css","li[data-module='RevenueLineItems']").assertExists(false);
		
		ArrayList<Module> modules = new ArrayList<Module>();
		modules.add(sugar.contacts);
		modules.add(sugar.opportunities);
		
		// Test disableModuleDisplay(ArrayList)
		sugar.admin.disableModuleDisplay(modules);
		
		new VoodooControl("a", "css", "ul#module_list.nav li.dropdown.more a.dropdown-toggle").click();
		new VoodooControl("li","css","li[data-module='Contacts']").assertExists(false);
		new VoodooControl("li","css","li[data-module='Opportunities']").assertExists(false);
		
		// Test enableModuleDisplay(ArrayList)
		sugar.admin.enableModuleDisplay(modules);
		
		new VoodooControl("a", "css", "ul#module_list.nav li.dropdown.more a.dropdown-toggle").click();
		new VoodooControl("li","css","li[data-module='Contacts']").assertExists(true);
		new VoodooControl("li","css","li[data-module='Opportunities']").assertExists(true);
		
		// Test disableSubpanel(module)
		sugar.admin.disableSubpanel(sugar.contacts);
		myAcc.navToRecord();
		VoodooUtils.pause(5000);
		new VoodooControl("div", "css", "div[data-voodoo-name='Contacts']").assertExists(false);
		
		// Test enableSubpanel(module)
		sugar.admin.enableSubpanel(sugar.contacts);
		myAcc.navToRecord();
		VoodooUtils.pause(5000);
		new VoodooControl("div", "css", "div[data-voodoo-name='Contacts']").assertExists(true);
		
		// Test disableSubpnel(ArrayList)
		sugar.admin.disableSubpanel(modules);
		myAcc.navToRecord();
		VoodooUtils.pause(5000);
		new VoodooControl("div", "css", "div[data-voodoo-name='Contacts']").assertExists(false);
		new VoodooControl("div", "css", "div[data-voodoo-name='Opportunities']").assertExists(false);
		
		//Test enableSubpanel(ArrayList)
		sugar.admin.enableSubpanel(modules);
		myAcc.navToRecord();
		VoodooUtils.pause(5000);
		new VoodooControl("div", "css", "div[data-voodoo-name='Contacts']").assertExists(true);
		new VoodooControl("div", "css", "div[data-voodoo-name='Opportunities']").assertExists(true);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
