package com.sugarcrm.sugar.views;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;

public class CreateCallView extends View{	
	protected static CreateCallView callCreateView;
	protected static AppModel sugar;
	private DataSource ds;
	public final VoodooControl CALL_NAME;
	public final VoodooControl DATE_START;
	public final VoodooControl HOURS_START;
	public final VoodooControl MINUTES_START;
	public final VoodooControl MERIDIEM_START;
	public final VoodooControl DURATION;
	public final VoodooControl STATUS;
	public final VoodooControl DESCRIPTION;
	
	public final VoodooControl CALL_TYPE;
	public final VoodooControl ASSSIGNED_USER_NAME;
	public final VoodooControl BTN_ASSIGNED_USER;
	public final VoodooControl BTN_CLEAR_ASSIGNED_USER;
	public final VoodooControl ADDITIONAL_ASSIGNEES;
	public final VoodooControl BTN_ADDITIONAL_ASSIGNEES;
	public final VoodooControl RELATED_TO_TYPE;
	public final VoodooControl RELATED_TO;
	public final VoodooControl BTN_RELATED_TO;
	public final VoodooControl BTN_CLEAR_RELATED_TO;
	public final VoodooControl ADD_ANOTHER_LINK;
	
	public final VoodooControl SUB_CREATE_BTN_MENU;
	public final VoodooControl SUB_CREATE_BTN;
	public final VoodooControl SUB_CALL_NAME;
	public final VoodooControl SUB_SAVE_BTN;
	public FieldSet defaultCall;
	private FieldSet callFull;
	
	
	/**
	 * Initializes the CreateCallView screen.
	 * @throws Exception
	 */
	public CreateCallView() throws Exception {
		super();
		sugar = AppModel.getInstance();
		ds = VoodooUtils.getData("CreateCallView");
		defaultCall = (FieldSet) ds.get(0).clone();
		callFull = new FieldSet();
		CALL_NAME = new VoodooControl("input", "id", "name");
		DATE_START = new VoodooControl("input", "id", "date_start_date");
		HOURS_START = new VoodooControl("select", "id", "date_start_hours");
		MINUTES_START = new VoodooControl("select", "id", "date_start_minutes");
		MERIDIEM_START = new VoodooControl("select", "id", "date_start_meridiem");
		DURATION = new VoodooControl("select", "id", "duration_minutes");
		STATUS = new VoodooControl("select", "id", "status");
		DESCRIPTION = new VoodooControl("textarea", "id", "description");
		
		CALL_TYPE = new VoodooControl("select", "id", "call_type");
		ASSSIGNED_USER_NAME = new VoodooControl("input", "id", "assigned_user_name");
		BTN_ASSIGNED_USER = new VoodooControl("button", "id", "btn_assigned_user_name");
		BTN_CLEAR_ASSIGNED_USER = new VoodooControl("button", "id", "btn_clr_assigned_user_name");
		ADDITIONAL_ASSIGNEES = new VoodooControl("input", "id", "additional_assignees_ac");
		BTN_ADDITIONAL_ASSIGNEES = new VoodooControl("button", "id", "btn_additional_assignees");
		RELATED_TO_TYPE = new VoodooControl("select", "id", "parent_type_EditView_1");
		RELATED_TO = new VoodooControl("input", "id", "related_to_c_1");
		BTN_RELATED_TO = new VoodooControl("button", "id", "btn_related_to_c_1");
		BTN_CLEAR_RELATED_TO = new VoodooControl("button", "id", "btn_clr_related_to_c_1");
		ADD_ANOTHER_LINK = new VoodooControl("a", "id", "EditView_related_to_c_add_another");
		
		SUB_CREATE_BTN_MENU = new VoodooControl("span","xpath", "//*[@id='list_subpanel_activities']/table/tbody/tr[1]/td/table/tbody/tr/td[1]/ul/li/span");
		SUB_CREATE_BTN = new VoodooControl("a","id", "Activities_logcall_button");
		SUB_CALL_NAME = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Calls']//input[@id='name']");
		SUB_SAVE_BTN = new VoodooControl("input", "xpath", "//form[@id='form_SubpanelQuickCreate_Calls']//input[@id='Calls_subpanel_save_button']");
	}
		
	public static CreateCallView getInstance() throws Exception {
		if (callCreateView == null) callCreateView = new CreateCallView();
		return callCreateView;
	}
	
	/**Method creates Call through UI using data from csv
	 * 
		 * @throws Exception
	 */
	public FieldSet createCallLibFull() throws Exception{
		VoodooUtils.log.info("Running createCallLibFull...");
		sugar.navbar.navToModule("Calls");
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		callFull.put("call_name", defaultCall.get("call_name") + timeStamp);
		VoodooUtils.switchToBWCFrame();
		
		
		CALL_NAME.assertVisible(true);
		CALL_NAME.set(callFull.get("call_name"));
		DATE_START.set(defaultCall.get("date_start"));
		callFull.put("date_start", defaultCall.get("date_start"));
		HOURS_START.set(defaultCall.get("hours_start"));
		callFull.put("hours_start", defaultCall.get("hours_start"));
		MINUTES_START.set(defaultCall.get("minutes_start"));
		callFull.put("minutes_start", defaultCall.get("minutes_start"));
		MERIDIEM_START.set(defaultCall.get("meridiem_start"));
		callFull.put("meridiem_start", defaultCall.get("meridiem_start"));
		DURATION.set(defaultCall.get("duration"));
		callFull.put("duration", defaultCall.get("duration"));
		STATUS.set(defaultCall.get("status"));
		callFull.put("status", defaultCall.get("status"));
		DESCRIPTION.set(defaultCall.get("description"));
		callFull.put("description", defaultCall.get("description"));
		CALL_TYPE.set(defaultCall.get("call_type"));
		callFull.put("call_type", defaultCall.get("call_type"));
		BTN_CLEAR_ASSIGNED_USER.click();
		BTN_ASSIGNED_USER.click();
		
		sugar.calls.searchForm.popupSelectRecord(defaultCall.get("assigned_user_name"));
		
		ASSSIGNED_USER_NAME.assertContains(defaultCall.get("assigned_user_name_full"), true);
		callFull.put("assigned_user_name_full", defaultCall.get("assigned_user_name_full"));
		BTN_ADDITIONAL_ASSIGNEES.click();
		sugar.calls.searchForm.popupSelectRecord(defaultCall.get("team_name"));
		
		ADDITIONAL_ASSIGNEES.assertContains(defaultCall.get("team_name_full"), true);
		callFull.put("team_name_full", defaultCall.get("team_name_full"));
		sugar.calls.editView.save();
		
		//Verify that call saved successfully
		VoodooUtils.switchToBWCFrame();
		new VoodooControl("span", "id", "name").assertContains(callFull.get("call_name"), true);
		VoodooUtils.switchBackToWindow();
		VoodooUtils.log.info("CreateCallLibFull finished.");
		return callFull;
	}
	/**Method creates Call via sub panel on clients using data from csv
	 * 
	 * @throws Exception
	 */
	
	public FieldSet createCallLibSub() throws Exception{
		VoodooUtils.log.info("Running createCallLibSub...");
		SUB_CREATE_BTN_MENU.click();
		VoodooUtils.pause(2000);
		SUB_CREATE_BTN.click();
		VoodooUtils.pause(2000);
		String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
		callFull.put("call_name", defaultCall.get("call_name") + timeStamp);
		VoodooUtils.switchBackToWindow();
		VoodooUtils.switchToBWCFrame();
		
		SUB_CALL_NAME.assertVisible(true);
		SUB_CALL_NAME.set(callFull.get("call_name"));
				
		DATE_START.set(defaultCall.get("date_start"));
		callFull.put("date_start", defaultCall.get("date_start"));
		HOURS_START.set(defaultCall.get("hours_start"));
		callFull.put("hours_start", defaultCall.get("hours_start"));
		MINUTES_START.set(defaultCall.get("minutes_start"));
		callFull.put("minutes_start", defaultCall.get("minutes_start"));
		MERIDIEM_START.set(defaultCall.get("meridiem_start"));
		callFull.put("meridiem_start", defaultCall.get("meridiem_start"));
		DURATION.set(defaultCall.get("duration"));
		callFull.put("duration", defaultCall.get("duration"));
		STATUS.set(defaultCall.get("status"));
		callFull.put("status", defaultCall.get("status"));
		DESCRIPTION.set(defaultCall.get("description"));
		callFull.put("description", defaultCall.get("description"));
		CALL_TYPE.set(defaultCall.get("call_type"));
		callFull.put("call_type", defaultCall.get("call_type"));
		BTN_CLEAR_ASSIGNED_USER.click();
		BTN_ASSIGNED_USER.click();
		
		sugar.calls.searchForm.popupSelectRecord(defaultCall.get("assigned_user_name"));
		ASSSIGNED_USER_NAME.assertContains(defaultCall.get("assigned_user_name_full"), true);
		callFull.put("assigned_user_name_full", defaultCall.get("assigned_user_name_full"));
		
		SUB_SAVE_BTN.click();
		VoodooUtils.pause(20000);
		VoodooUtils.switchBackToWindow();
		sugar.calls.assertRecord(callFull.get("call_name"));
		
		VoodooUtils.log.info("CreateCallLibSub finished.");
		return callFull;
	}
}
