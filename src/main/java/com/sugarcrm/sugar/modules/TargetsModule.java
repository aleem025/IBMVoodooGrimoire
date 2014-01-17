package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.TargetRecord;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.modules.StandardModule;
import com.sugarcrm.sugar.views.Menu;

/**
 * Targets module object exposing controls and tasks for the Targets module
 * 
 * @author Eric Yang <eyang@sugarcrm.com>
 */
public class TargetsModule extends StandardModule {
	protected static TargetsModule module;

	public static TargetsModule getInstance() throws Exception {
		if (module == null) module = new TargetsModule();
		return module;
	}

	private TargetsModule() throws Exception {
		moduleNameSingular = "Prospect";
		moduleNamePlural = "Prospects";
		recordClassName = TargetRecord.class.getName();

		//Load Targets Module element definitions from CSV
		loadFields();
		
		// Define the columns on the ListView.
		listView.addHeader("full_name");
		listView.addHeader("title");
		listView.addHeader("email");
		listView.addHeader("phone_work");
		listView.addHeader("date_entered");

		recordView.addControl("selectListCampaignLog", "span", "xpath", "//*[contains(@class,'select2-result-label')]//span[@innerHtml='Campaign Log']");
		
		// Targets Module Menu Items
		menu = new Menu(this);
		menu.addControl("createTarget", "a", "css", "li[data-module='Prospects'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_PROSPECT']");
		menu.addControl("createtargetFromVcard", "a", "css", "li[data-module='Prospects'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_VCARD']");
		menu.addControl("viewtargets", "a", "css", "li[data-module='Prospects'] ul[role='menu'] a[data-navbar-menu-item='LNK_PROSPECT_LIST']");
		menu.addControl("importTargets", "a", "css", "li[data-module='Prospects'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_PROSPECTS']");
		
	}
	
	/**
	 * Creates a single record record via the UI from the data in a FieldSet. 
	 * @param recordData
	 * @return a Record object representing the record created
	 * @throws Exception
	 */
	public Record create(FieldSet testData, boolean saveandview ) throws Exception {
		VoodooUtils.voodoo.log.info("Reconciling record data.");

		// Merge default data and user-specified data.
		FieldSet recordData = getDefaultData();
		recordData.putAll(testData);

		VoodooUtils.voodoo.log.info("Creating a " + moduleNameSingular + " via UI...");
		sugar.navbar.navToModule(moduleNamePlural);

		// Move to the CreateDrawer and show hidden fields.
		listView.create();
		
		createDrawer.showMore();

		// Iterate over the field data and set field values.
		for(String controlName : recordData.keySet()) {
			if(recordData.get(controlName) != null) {
				VoodooUtils.voodoo.log.info("Setting " + controlName);
				// Handle relate fields using Sugar7 widget
				String toSet = recordData.get(controlName);
				VoodooUtils.voodoo.log.info("Setting " + controlName + " to " + toSet);
				createDrawer.getEditField(controlName).set(toSet);
				VoodooUtils.pause(3000);
				// Hack to get Revenue Line Item record to be created.
				// Appear that relating an Opportunity to a RevLineItem causes the system to believe that we are
				// overwriting an existing value, e.g. Account Name
				// Confirmed with Riley that the confirm popup is only a Hack by Dev's to get around a bug they couldn't fix before SugarCON 2013
				// TODO: When the above issue is taken care of in the product, the following few lines should be reviewed and possibly removed.
				if(controlName.equals("relOpportunityName"))
					sugar.revLineItems.recordView.getControl("overWriteConfirm").click();
			}
		}
		VoodooUtils.pause(3000);
		if (saveandview) {
			createDrawer.save();
		}else {
			createDrawer.saveAndView();
		}
		VoodooUtils.voodoo.log.info("Record created.");

		return (Record)Class.forName(this.recordClassName).getConstructor(FieldSet.class).newInstance(recordData);
	}
	
	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init()
	{
		VoodooUtils.voodoo.log.info("Init Targets...");
	}
} // end TargetsModule
