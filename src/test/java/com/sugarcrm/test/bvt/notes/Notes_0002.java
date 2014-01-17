package com.sugarcrm.test.bvt.notes;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.views.CreateCallView;

public class Notes_0002 extends SugarTest {
	FieldSet note;
	FieldSet noteEdited;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
		note = sugar.notes.api.createFS();
	}

	@Test
	public void execute() throws Exception {
	
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		noteEdited = (FieldSet) testData.get(testName).get(0).clone();
		noteEdited.put("note_name", noteEdited.get("note_name") + VoodooUtils.getCurrentTimeStamp("MMddHHmmss"));
		
		sugar.notes.navToRecord(note.get("subject"));
		sugar.notes.detailView.edit();
		
		VoodooUtils.switchToBWCFrame();
		sugar.noteCreateView.NOTE_NAME.set(noteEdited.get("note_name"));
		sugar.noteCreateView.DESCRIPTION.set(noteEdited.get("description"));
		sugar.noteCreateView.BTN_ADDITIONAL_ASSIGNEES.click();
		sugar.notes.searchForm.popupSelectRecord(noteEdited.get("team_name"));
		sugar.noteCreateView.ADDITIONAL_ASSIGNEES.assertContains(noteEdited.get("team_name_full"), true);
		sugar.notes.editView.save();
		
		sugar.notes.navToRecord(noteEdited.get("note_name"));
		
		sugar.notes.verifyDetailView(noteEdited);		
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	
	}

	public void cleanup() throws Exception {
		sugar.notes.deleteRecord(noteEdited.get("note_name"));
		sugar.logout();
	}
}