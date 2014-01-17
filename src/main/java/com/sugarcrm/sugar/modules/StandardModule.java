package com.sugarcrm.sugar.modules;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.sugar.records.StandardRecord;
import com.sugarcrm.sugar.views.BWCDetailView;
import com.sugarcrm.sugar.views.BWCEditView;
import com.sugarcrm.sugar.views.BWCListView;
import com.sugarcrm.sugar.views.BWCSearchView;
import com.sugarcrm.sugar.views.CreateDrawer;
import com.sugarcrm.sugar.views.ListView;
import com.sugarcrm.sugar.views.PreviewPane;
import com.sugarcrm.sugar.views.RecordView;

/**
 * Base class from which all standard modules extend. Methods and data which are
 * common to standard modules are stored here.
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 * @author David Safar <dsafar@sugarcrm.com>
 */
public abstract class StandardModule extends RecordsModule {
	private StandardModule module;
	// Views specific to the StandardModule.
	public CreateDrawer createDrawer = new CreateDrawer(StandardModule.this);
	public RecordView recordView = new RecordView(StandardModule.this);
	public ListView listView = new ListView(StandardModule.this);	
	public BWCEditView editView = new BWCEditView(StandardModule.this);
	public PreviewPane previewPane = new PreviewPane(StandardModule.this);
	public BWCDetailView detailView = new BWCDetailView(StandardModule.this);
	public BWCSearchView searchForm = new BWCSearchView();
	
	public StandardModule() throws Exception {
	}
	
	/**
	 * Creates a single record record via the UI from the data in a FieldSet.
	 * 
	 * @param	recordData	a FieldSet containing the data for the new record.
	 * @return	a Record object representing the record created
	 * @throws Exception
	 */
	public Record create(FieldSet testData) throws Exception {
		VoodooUtils.voodoo.log.fine("Reconciling record data.");
		
		// Merge default data and user-specified data.
		FieldSet recordData = getDefaultData();
		recordData.putAll(testData);

		VoodooUtils.voodoo.log.info("Creating a(n) " + moduleNameSingular
				+ " via UI...");
		sugar.navbar.navToModule(moduleNamePlural);

		// Move to the CreateDrawer and show hidden fields.
		listView.create();

		if(createDrawer.getControl("showMore").queryVisible() == true){
			createDrawer.showMore();
		}

		// Iterate over the field data and set field values.
		for(String controlName : recordData.keySet()) {
			if(recordData.get(controlName) != null) {
				VoodooUtils.voodoo.log.fine("Setting " + controlName);
				// Handle relate fields using Sugar7 widget
				String toSet = recordData.get(controlName);
				VoodooUtils.voodoo.log.fine("Setting " + controlName + " to "
						+ toSet);
				createDrawer.getEditField(controlName).set(toSet);
				VoodooUtils.pause(300);
			}
		}

		createDrawer.save();
		VoodooUtils.pause(1000);
		VoodooUtils.waitForAlertExpiration();
		VoodooUtils.voodoo.log.fine("Record created.");

		return (StandardRecord) Class.forName(StandardModule.this.recordClassName)
				.getConstructor(FieldSet.class).newInstance(recordData);
	} // create(FieldSet testData)

	/**
	 * Deletes all records in the current module using the UI.
	 * 
	 * @throws Exception
	 */
	public void deleteAll() throws Exception {
		VoodooUtils.voodoo.log.info("Deleting all " + moduleNameSingular
				+ " records...");
		navToListView();
		listView.toggleSelectAll();
		listView.openActionDropdown();
		listView.delete();
		listView.confirmDelete();
	}

	/**
	 * This method will navigate to the module of the calling record and search
	 * for that record.
	 * 
	 * @throws Exception
	 */
	public void search() throws Exception {
		// TODO: Revise this (including JavaDoc).
		VoodooUtils.voodoo.log.info("Navigating to " + moduleNamePlural
				+ " module");
		navToListView();
		VoodooUtils.voodoo.log.info("Searching for " + moduleNameSingular
				+ " record ");
		listView.getControl("searchFilter").click();
		listView.getControl("searchFilter").set("");
		listView.getControl("searchSuggestion").click();
	}	
	
	/**
	 * Asserts existence of record on page inside bwc-frame
	 * @param recordName
	 * @throws Exception
	 */
	public void assertRecord(String recordName) throws Exception{
		VoodooUtils.switchToBWCFrame();
				if (VoodooUtils.iface.wd.getPageSource().contains(recordName)) {
					VoodooUtils.log.info("Text: " + recordName + " was founded");
				}
				else {
					VoodooUtils.log.warning("(!)Text: " + recordName + " was NOT founded");
					//throw new UnfoundElementException("(!)Text: " + record.get(key) + "was NOT founded");					
				}
		VoodooUtils.switchBackToWindow();		
	}
	
	/**
	 * Verifies if all values of the created item are displayed on the detail view
	 * 
	 * @throws Exception
	 */
	public void verifyDetailView(FieldSet record) throws Exception{
		VoodooUtils.switchToBWCFrame();
		for(String key : record.keySet()) {
			
			if(record.get(key) != null) {
				if (VoodooUtils.iface.wd.getPageSource().contains(record.get(key))) {
					VoodooUtils.log.info("Text: " + record.get(key) + " was founded");
				}
				else {
					VoodooUtils.log.warning("(!)Text: " + record.get(key) + " was NOT founded");
					//throw new UnfoundElementException("(!)Text: " + record.get(key) + "was NOT founded");					
				}
			}
		}
		VoodooUtils.switchBackToWindow();		
	}
		
	/**
	 * Navigate to detail view of the record
	 * 
	 * @param recordName
	 * 
	 */	
	public void navToRecord(String recordName) throws Exception {	
		this.initModule();
		module.navToListView();
		VoodooUtils.switchToBWCFrame();
		module.searchForm.searchByName(recordName);
	    new VoodooControl("a", "xpath", "//a[contains(text(), '" + recordName + "')]").click();
	    VoodooUtils.switchBackToWindow();  
	}
	
	/**
	 * Delete the record
	 * 
	 * @param recordName
	 * 
	 */	
	public void deleteRecord(String recordName) throws Exception {	
		this.initModule();
		module.navToListView();
		VoodooUtils.switchToBWCFrame();
		module.searchForm.searchByName(recordName);
	    new VoodooControl("a", "xpath", "//a[contains(text(), '" + recordName + "')]").click();
	    module.detailView.delete();
	    if (!module.searchForm.searchByName(recordName)) 
	    	VoodooUtils.log.info("The record '" + recordName + "' is deleted successfully");
	    else VoodooUtils.log.warning("The record '" + recordName + "' is NOT deleted");
	    VoodooUtils.switchBackToWindow();  
	}
	
	/**
	 * Init module which call the functions: navToRecord(String recordName), deleteRecord(String recordName)
	 *  
	 */	
	private void initModule(){
		module = sugar.accounts;
		switch (moduleNamePlural){
			case "Contacts":
				module = sugar.contacts;
				break;
			case "Opportunities":
				module = sugar.opportunities;
				break;
			case "Calls":
				module = sugar.calls;
				break;
			case "Notes":
				module = sugar.notes;
				break;
			case "Tasks":
				module = sugar.tasks;
				break;
		}
	}	
} // StandardModule
