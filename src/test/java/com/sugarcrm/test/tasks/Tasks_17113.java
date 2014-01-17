package com.sugarcrm.test.tasks;

import java.util.Calendar;
import java.util.TimeZone;
import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest; 

public class Tasks_17113 extends SugarTest {	
	@Override
	public void setup() throws Exception {	
		sugar.login();	
	}

	/**
	 * Verify default values on time fields.
	 * @throws Exception
	 */	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "..."); 
		
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	     
		int intMonth = localCalendar.get(Calendar.MONTH) + 1;
		String currentDay = String.valueOf(localCalendar.get(Calendar.DATE));
		String currentMonth = String.valueOf(intMonth);
		String currentYear = String.valueOf(localCalendar.get(Calendar.YEAR));
		
		sugar.navbar.navToModule("Tasks");
		// TODO: VOOD-611. Once it is fixed, please update the following step.
		new VoodooControl("a", "css", ".fld_create_button.list-headerpane a").click();
		new VoodooControl("i", "css", ".icon-calendar").click();
		new VoodooControl("div", "css", ".fld_date_start.edit .datepicker").click();
		new VoodooControl("td", "css", ".day.active").click();

		//Verify that today is selected
		new VoodooControl("input", "css", ".fld_date_start.edit div input.datepicker").assertContains(currentYear, true);
		new VoodooControl("input", "css", ".fld_date_start.edit div input.datepicker").assertContains(currentMonth, true);
		new VoodooControl("input", "css", ".fld_date_start.edit div input.datepicker").assertContains(currentDay, true);
		
		String firstTime = "12:00am";		
		new VoodooControl("i", "css", ".icon-time").click();

		new VoodooControl("li", "css", ".ui-timepicker-list li:first-child").assertContains(firstTime, true);
		new VoodooControl("li", "css", ".ui-timepicker-selected").click();	
		
		//Just Cancel the create process
		new VoodooControl("a", "css", ".btn-toolbar.pull-right .fld_cancel_button.detail .btn-link").click();
		
		VoodooUtils.voodoo.log.info(testName + " complete."); 
	}

	@Override
	public void cleanup() throws Exception {
		sugar.logout();
	}
}
