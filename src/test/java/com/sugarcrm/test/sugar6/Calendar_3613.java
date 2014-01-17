package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.candybean.automation.control.VHook.Strategy;
import com.sugarcrm.candybean.automation.control.VHook;


public class Calendar_3613 extends SugarTest {
	public void setup() throws Exception {
//		1. Login to system as valid user.
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
// TODO: Rewrite this whole thing to use tasks.
//		FieldSet callData = new FieldSet();
//		callData[0].add("calendar_panel", "Day");
//		callData[0].add("date", ""); // Omit, set to "", or specify today's date for today.
//		callData[0].add("time", "09:00");
//		callData[0].add("name", "Test Call");
//
//		sugar.calendar.logCallByGrid(callData)
		
//		2. Click "Calendar" tab in navigation bar.
		new VoodooControl("a", "id", "moduleTab_AllCalendar").click();

//		3. Click any time link on Day Calender panel.
		new VoodooControl("div", "xpath", "/html/body/div[5]/div/table/tbody/tr/td/div[10]/div[2]/div[2]/div[1]/div[1]").click();

//		4. Click "Schedule Meeting" radio button and enter the "Subject" mandatory fields.
		new VoodooControl("input", "id", "radio_meeting").click();
		new VoodooControl("input", "id", "name").set("CW");
		
//		5. Click on "Save" button.
		new VoodooControl("a", "id", "btn-save");
//		  5. Call detail view is displayed with the detail information of the call entered by step #4.
		// TODO: Verify.
		
	}

	public void cleanup() throws Exception {
//		sugar.calls.api.deleteAll();
		sugar.logout();
	}
}