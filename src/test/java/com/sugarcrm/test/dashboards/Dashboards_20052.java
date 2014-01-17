package com.sugarcrm.test.dashboards;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Dashboards_20052 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	/**
	 * Verify the default dashlets are present on My Dashboard
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Need to pause otherwise the first assertVisible will fail
		VoodooUtils.pause(2000);

		new VoodooControl("div", "css", "div[data-voodoo-name='twitter']")
				.assertVisible(true);
		new VoodooControl("div", "css",
				"div[data-voodoo-name='forecast-pipeline']")
				.assertVisible(true);
		new VoodooControl("div", "css", "div[data-voodoo-name='countrychart']")
				.assertVisible(true);
		new VoodooControl("div", "css", ".list-view.contacts")
				.assertVisible(true);
		new VoodooControl("div", "css", "div[data-voodoo-name='bubblechart']")
				.assertVisible(true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
