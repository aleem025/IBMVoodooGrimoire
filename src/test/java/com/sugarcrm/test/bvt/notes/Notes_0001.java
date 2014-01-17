package com.sugarcrm.test.bvt.notes;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.views.CreateCallView;

public class Notes_0001 extends SugarTest {
	FieldSet note;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
	}
	/** Creating new note through full form 
	 *  Open it in detail view and verify if all note info saved correctly
	 *  
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		note = sugar.noteCreateView.createNoteLibFull();
		sugar.notes.navToRecord(note.get("note_name"));						
		sugar.notes.verifyDetailView(note);
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.notes.deleteRecord(note.get("note_name"));
		sugar.logout();
	}
}