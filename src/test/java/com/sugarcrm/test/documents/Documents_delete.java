package com.sugarcrm.test.documents;

import org.junit.Test;
import org.junit.Ignore;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.DocumentRecord;
import static org.junit.Assert.assertEquals;

public class Documents_delete extends SugarTest {
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

		//Delete the document using the UI.
		myDocument.delete();
		
		// Verify the document was deleted.
		sugar.documents.navToListView();		
		sugar.documents.listView.clearSearchForm();
		sugar.documents.listView.submitSearchForm();
		VoodooUtils.focusFrame("bwc-frame");
		assertEquals(VoodooUtils.contains(myDocument.getRecordIdentifier(), true), false);
		VoodooUtils.focusDefault();

		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.documents.api.deleteAll();
		sugar.logout();
	}
}
