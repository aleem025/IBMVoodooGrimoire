package com.sugarcrm.test.documents;

import org.junit.Ignore;
import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.DocumentRecord;
import com.sugarcrm.test.SugarTest;

public class Documents_create extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
		//TODO VOOD-651 Need Lib support for a filefield tag and Doc api create
	}

	@Test
	@Ignore("VOOD-651 Need Lib support for a filefield tag and Doc api create")
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
	
		DocumentRecord myDocument = (DocumentRecord)sugar.documents.create();
		myDocument.verify();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.documents.api.deleteAll();
		sugar.logout();
	}
}
