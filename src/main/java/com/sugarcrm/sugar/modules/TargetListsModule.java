package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.SugarField;
import com.sugarcrm.sugar.records.TargetListRecord;
import com.sugarcrm.sugar.VoodooUtils;

/**
 * TargetLists module object exposing controls and tasks for the TargetLists module
 * 
 * @author Eric Yang <eyang@sugarcrm.com>
 */
public class TargetListsModule extends StandardModule {
	protected static TargetListsModule module;

	public static TargetListsModule getInstance() throws Exception {
		if (module == null) module = new TargetListsModule();
		return module;
	}

	private TargetListsModule() throws Exception {
		moduleNameSingular = "ProspectList";
		moduleNamePlural = "ProspectLists";
		recordClassName = TargetListRecord.class.getName();
		api = new Api();
		
		// Define the columns on the ListView.
		listView.addHeader("name");
		listView.addHeader("list_type");
		listView.addHeader("description");
		listView.addHeader("assigned_user_name");
		listView.addHeader("date_entered");
		
	}
	
	/**
	 * Perform setup which depends on other modules already being constructed. 	  
	 */
	@Override
	public void init()
	{
		VoodooUtils.voodoo.log.info("Init TargetLists...");
	}
} // end TargetListsModule
