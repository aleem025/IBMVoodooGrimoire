package com.sugarcrm.test.bvt.calls;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.views.CreateCallView;

public class Calls_0003 extends SugarTest {
	FieldSet call;
	FieldSet callEdited;
		
	public void setup() throws Exception {
		sugar.login_regularUser();
		call = sugar.callCreateView.createCallLibFull();
	}
	/** Edits created call via pencil icon on List View
	 *  Verifying that all fields edited correctly
	 *  @throws Exception
	 */
	@Test
	public void execute() throws Exception {
	
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		callEdited = (FieldSet) testData.get(testName).get(0).clone();
		callEdited.put("call_name", callEdited.get("call_name") + VoodooUtils.getCurrentTimeStamp("MMddHHmmss"));
		
		sugar.calls.navToListView();
		VoodooUtils.switchToBWCFrame();
		sugar.calls.listView.clickPencilButton(call.get("call_name"));
		
		VoodooUtils.switchToBWCFrame();
		sugar.callCreateView.CALL_NAME.set(callEdited.get("call_name"));
		sugar.callCreateView.DATE_START.set(callEdited.get("date_start"));
		sugar.callCreateView.HOURS_START.set(callEdited.get("hours_start"));
		sugar.callCreateView.MINUTES_START.set(callEdited.get("minutes_start"));
		sugar.callCreateView.DURATION.set(callEdited.get("duration"));
		sugar.callCreateView.DESCRIPTION.set(callEdited.get("description"));
		
		sugar.callCreateView.BTN_CLEAR_ASSIGNED_USER.click();
		sugar.callCreateView.BTN_ASSIGNED_USER.click();
		sugar.calls.searchForm.popupSelectRecord(callEdited.get("assigned_user_name"));
		sugar.callCreateView.ASSSIGNED_USER_NAME.assertContains(callEdited.get("assigned_user_name_full"), true);
		
		sugar.callCreateView.BTN_ADDITIONAL_ASSIGNEES.click();
		sugar.calls.searchForm.popupSelectRecord(callEdited.get("team_name"));
		sugar.callCreateView.ADDITIONAL_ASSIGNEES.assertContains(callEdited.get("team_name_full"), true);
		sugar.callCreateView.CALL_TYPE.set(callEdited.get("call_type"));
		sugar.calls.editView.save();
		
		sugar.calls.navToRecord(callEdited.get("call_name"));
		
		sugar.calls.verifyDetailView(callEdited);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.calls.deleteRecord(callEdited.get("call_name"));
		sugar.logout();
	}
}