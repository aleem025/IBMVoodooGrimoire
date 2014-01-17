package com.sugarcrm.test.calls;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Calls_20255 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * Verify Navigation and Cancel from menu Quick Create Log Call
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		// TODO Replace VoodooControls with LIB calls when VOOD-588 support for
		// Quick create menu is implemented
		new VoodooControl("li", "id", "createList").click();
		new VoodooControl("a", "css", "a[data-module='Calls']").click();
		// This pause is needed to navigate to the BWC Calls module
		VoodooUtils.pause(3000);
		// TODO Replace VodooControl calls for BWC Calls when VOOD-416 - Calls
		// BWC module is available
		VoodooUtils.focusFrame("bwc-frame");
		new VoodooControl("input", "id", "name").assertVisible(true);
		new VoodooControl("input", "xpath", "//input[@value='Cancel']").click();
		VoodooUtils.focusDefault();
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}