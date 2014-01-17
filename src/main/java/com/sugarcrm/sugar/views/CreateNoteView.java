package com.sugarcrm.sugar.views;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;

public class CreateNoteView extends View{	
	protected static CreateNoteView noteCreateView;
	protected static AppModel sugar;
	private DataSource ds;
	public final VoodooControl NOTE_NAME;
	public final VoodooControl ASSSIGNED_USER_NAME;
	public final VoodooControl BTN_ASSIGNED_USER;
	public final VoodooControl BTN_CLEAR_ASSIGNED_USER;
	public final VoodooControl ADDITIONAL_ASSIGNEES;
	public final VoodooControl BTN_ADDITIONAL_ASSIGNEES;
	public final VoodooControl DESCRIPTION;
	
	public final VoodooControl RELATED_TO_TYPE;
	public final VoodooControl RELATED_TO;
	public final VoodooControl BTN_RELATED_TO;
	public final VoodooControl BTN_CLEAR_RELATED_TO;
	public final VoodooControl ADD_ANOTHER_LINK;
	
	public final VoodooControl SUB_SAVE_BTN;
	public final VoodooControl SUB_CREATE_BTN;
	public final VoodooControl SUB_NOTE_NAME;
	public FieldSet defaultNote;
	private FieldSet noteFull;
	
	/**
	 * Initializes the AccountCreateView screen.
	 * @throws Exception
	 */
	public CreateNoteView() throws Exception {
		super();
		sugar = AppModel.getInstance();
		ds = VoodooUtils.getData("CreateNoteView");
		defaultNote = (FieldSet) ds.get(0).clone();
		noteFull = new FieldSet();
		NOTE_NAME = new VoodooControl("input", "id", "name");
		ASSSIGNED_USER_NAME = new VoodooControl("input", "id", "assigned_user_name");
		BTN_ASSIGNED_USER = new VoodooControl("button", "id", "btn_assigned_user_name");
		BTN_CLEAR_ASSIGNED_USER = new VoodooControl("button", "id", "btn_clr_assigned_user_name");
		ADDITIONAL_ASSIGNEES = new VoodooControl("input", "id", "additional_assignees_ac");
		BTN_ADDITIONAL_ASSIGNEES = new VoodooControl("button", "id", "btn_additional_assignees");
		DESCRIPTION = new VoodooControl("textarea", "id", "description");
		
		RELATED_TO_TYPE = new VoodooControl("select", "id", "parent_type_EditView_1");
		RELATED_TO = new VoodooControl("input", "id", "related_to_c_1");
		BTN_RELATED_TO = new VoodooControl("button", "id", "btn_related_to_c_1");
		BTN_CLEAR_RELATED_TO = new VoodooControl("button", "id", "btn_clr_related_to_c_1");
		ADD_ANOTHER_LINK = new VoodooControl("a", "id", "EditView_related_to_c_add_another");
		
		SUB_CREATE_BTN = new VoodooControl("a","id", "History_createnote_button");
		SUB_NOTE_NAME = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Notes']//input[@id='name']");
		SUB_SAVE_BTN = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Notes']//input[@id='Notes_subpanel_save_button']");
	}
		
	public static CreateNoteView getInstance() throws Exception {
		if (noteCreateView == null) noteCreateView = new CreateNoteView();
		return noteCreateView;
	}
	
	/**Method creates Note through UI with all fields filled using data from csv
	 * 
	 * @throws Exception
	 */
	public FieldSet createNoteLibFull() throws Exception{
		VoodooUtils.log.info("Running createNoteLibFull...");
	
		sugar.navbar.navToModule("Notes");
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		noteFull.put("note_name", defaultNote.get("note_name") + timeStamp);
		VoodooUtils.switchToBWCFrame();
		
		
		NOTE_NAME.assertVisible(true);
		NOTE_NAME.set(noteFull.get("note_name"));
		
		DESCRIPTION.set(defaultNote.get("description"));
		noteFull.put("description", defaultNote.get("description"));
		BTN_CLEAR_ASSIGNED_USER.click();
		BTN_ASSIGNED_USER.click();
		
		sugar.notes.searchForm.popupSelectRecord(defaultNote.get("assigned_user_name"));
		ASSSIGNED_USER_NAME.assertContains(defaultNote.get("assigned_user_name_full"), true);
		noteFull.put("assigned_user_name_full", defaultNote.get("assigned_user_name_full"));
		BTN_ADDITIONAL_ASSIGNEES.click();
		
		sugar.notes.searchForm.popupSelectRecord(defaultNote.get("team_name"));
		ADDITIONAL_ASSIGNEES.assertContains(defaultNote.get("team_name_full"), true);
		noteFull.put("team_name_full", defaultNote.get("team_name_full"));
		sugar.notes.editView.save();
		
		//Verify that Note saved successfully
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("span", "id", "name").assertContains(noteFull.get("note_name"), true);
		VoodooUtils.switchBackToWindow();
		VoodooUtils.log.info("CreateNoteLibFull finished.");
		return noteFull;
	}

	/**Method creates Note via sub panel on clients using data from csv
	 * 
	 * @throws Exception
	 */
	
	public FieldSet createNoteLibSub() throws Exception{
		VoodooUtils.log.info("Running createNoteLibSub...");
		SUB_CREATE_BTN.click();
		VoodooUtils.pause(2000);
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		noteFull.put("note_name", defaultNote.get("note_name") + timeStamp);
		VoodooUtils.switchBackToWindow();
		VoodooUtils.switchToBWCFrame();
		
		SUB_NOTE_NAME.assertVisible(true);
		SUB_NOTE_NAME.set(noteFull.get("note_name"));
				
		DESCRIPTION.set(defaultNote.get("description"));
		noteFull.put("description", defaultNote.get("description"));
		BTN_CLEAR_ASSIGNED_USER.click();
		BTN_ASSIGNED_USER.click();
		
		sugar.notes.searchForm.popupSelectRecord(defaultNote.get("assigned_user_name"));
		ASSSIGNED_USER_NAME.assertContains(defaultNote.get("assigned_user_name_full"), true);
		noteFull.put("assigned_user_name_full", defaultNote.get("assigned_user_name_full"));
		
		SUB_SAVE_BTN.click();
		VoodooUtils.pause(20000);
		VoodooUtils.switchBackToWindow();
		sugar.notes.assertRecord(noteFull.get("note_name"));
		
		VoodooUtils.log.info("CreateNoteLibSub finished.");
		return noteFull;
	}
}
