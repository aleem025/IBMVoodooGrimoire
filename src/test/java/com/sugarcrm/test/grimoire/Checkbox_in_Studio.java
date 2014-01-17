package com.sugarcrm.test.grimoire;

import org.junit.Test;

import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;

public class Checkbox_in_Studio extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		// Navigate to Admin > Studio > Accounts > Fields > Name
		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");
		sugar.admin.adminTools.getControl("studio").click();
		VoodooUtils.pause(3000);
		new VoodooControl("a", "id", "studiolink_Accounts").click(); // Click on Accounts module
		VoodooUtils.pause(3000);
		new VoodooControl("a", "css", "#Buttons td:nth-of-type(2) tr:nth-of-type(2) a").click(); // Click on Fields
		VoodooUtils.pause(3000);
		new VoodooControl("a", "css", "#field_table #name").click(); // Click on Fields
		VoodooUtils.pause(3000);
		new VoodooControl("input", "css", "input[name='required']").set("false"); // Set checkbox "required" to un-checked
		
		try {
			new VoodooControl("input", "css", "input[name='required']").assertAttribute("checked", "");
			VoodooUtils.voodoo.log.info("The checkbox is still checked. Test Failed!");
		} catch(Exception e) {
			VoodooUtils.voodoo.log.info("The checkbox is NOT checked. Test Passed!");
		}
		
		VoodooUtils.focusDefault();
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
