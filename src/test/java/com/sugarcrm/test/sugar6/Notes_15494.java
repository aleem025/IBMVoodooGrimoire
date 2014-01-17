package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

/*import wip.NoteRecord;
import com.sugarcrm.sugar.records.NoteRecord;
*/
public class Notes_15494 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
/*		// To be created with default data.
		NoteRecord myNote = (NoteRecord) sugar.notes.create();  

		// Verify the returned data object using the GUI
		myNote.verify();
*/	}

	public void cleanup() throws Exception {
//		sugar.Notes.api.deleteAll();
		sugar.logout();
	}
}
