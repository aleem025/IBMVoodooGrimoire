package wip;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.modules.StandardModule;
import com.sugarcrm.sugar.records.StandardRecord;
import com.sugarcrm.sugar.views.View;

/**
 * Calendar module object which contains tasks associated with the Calendar module
 * like create/deleteAll
 * @author 
 *
 */
public class CalendarModule extends StandardModule {
	protected static CalendarModule module;
	protected View allViews, weekView, dayView, logCallForm;

	public static CalendarModule getInstance() throws Exception {
		if(module == null) module = new CalendarModule();
		return module;
	}

	private CalendarModule() throws Exception {
		moduleNameSingular = "Calendar";
		//api = new Api();
		allViews = new View();
		weekView = new View();
		dayView = new View();
		
		weekView.addControl("dayButton", "input", "id", "day-tab");
		
		dayView.addControl("08:00", "div", "css", "div#cal-grid div#cal-scrollable div.week div.col div:nth-of-type(33)");
		
		allViews.addControl("settings", "input", "id", "cal_settings");
	}
	
	public StandardRecord calendarCreate(FieldSet calendarRecord){
		return null;
		/*
		// Take FieldSet and determine what record type needs to be created, call that module type's create method
		switch(calendarRecord.get("module")){
			case "Call":
				return Sugar.Calls.create(calendarRecord);
				break;
			case "Meeting":
				return Sugar.Meetings.create(calendarRecord);
				break;
		}
		*/
	}
} // end CalendarModule
