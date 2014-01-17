package com.sugarcrm.test.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.Format;
import java.text.SimpleDateFormat;
import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest; 

public class Tasks_17112 extends SugarTest {	
	@Override
	public void setup() throws Exception {	
		sugar.login();	
	}

	/**
	 * Verify user can specify date and time for the Tasks module.
	 * @throws Exception
	 */	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "..."); 
		
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	     
		int intMonth = localCalendar.get(Calendar.MONTH) + 1;
		String currentDay = String.valueOf(localCalendar.get(Calendar.DATE));
		String currentMonth = String.valueOf(intMonth);
		Date dTime = new Date(localCalendar.getTimeInMillis());
		Format formatter = new SimpleDateFormat("MMMM");
		String currentMonthName = formatter.format(dTime);
		String currentYear = String.valueOf(localCalendar.get(Calendar.YEAR));
		
		sugar.navbar.navToModule("Tasks");
		// TODO: VOOD-611. Once it is fixed, please update the following steps.
		new VoodooControl("a", "css", ".fld_create_button.list-headerpane a").click();
		new VoodooControl("i", "css", ".icon-calendar").click();
		new VoodooControl("th", "css", ".datepicker.dropdown-menu table.table-condensed tr:first-child th:nth-child(2)").assertContains(currentYear, true);
		new VoodooControl("th", "css", ".datepicker.dropdown-menu table.table-condensed tr:first-child th:nth-child(2)").assertContains(currentMonthName, true);
		new VoodooControl("div", "css", ".fld_date_start.edit .datepicker").click();
		new VoodooControl("td", "css", ".day.active").click();

		//Verify that today is selected
		new VoodooControl("input", "css", ".fld_date_start.edit .input-append.date input").assertContains(currentYear, true);
		new VoodooControl("input", "css", ".fld_date_start.edit div input.datepicker").assertContains(currentMonth, true);
		new VoodooControl("input", "css", ".fld_date_start.edit div input.datepicker").assertContains(currentDay, true);
		
		//Verify that time drop down.  15 minutes apart between 2 time selected.
		String firstTime = "12:00am";
		String secondTime = "12:15am";
		new VoodooControl("i", "css", ".icon-time").click();

		new VoodooControl("li", "css", ".ui-timepicker-list li:first-child").assertContains(firstTime, true);
		new VoodooControl("li", "css", ".ui-timepicker-list li:nth-child(2)").assertContains(secondTime, true);
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
