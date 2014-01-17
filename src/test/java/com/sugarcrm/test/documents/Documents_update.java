package com.sugarcrm.test.documents;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.DocumentRecord;

public class Documents_update extends SugarTest {
	DocumentRecord myDocument;

	public void setup() throws Exception {
		sugar.login();
		//TODO VOOD-651 Need Lib support for a filefield tag and Doc api create
		myDocument = (DocumentRecord)sugar.documents.api.create();
	}

	@Test
	@Ignore("VOOD-651 Need Lib support for a filefield tag and Doc api create")
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		FieldSet newData = new FieldSet();
		newData.put("documentName", "A New Doc Name");
		myDocument.edit(newData);
		myDocument.verify(newData);
		
		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.documents.api.deleteAll();
		sugar.logout();
	}
}