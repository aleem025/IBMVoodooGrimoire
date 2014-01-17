package com.sugarcrm.sugar.views;

import java.util.HashMap;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.Module;
import com.sugarcrm.sugar.modules.RecordsModule;
import com.sugarcrm.sugar.modules.StandardModule;

/**
 * Models the RecordView for standard SugarCRM modules.
 * @author David Safar <dsafar@sugarcrm.com>
 *
 */
public class RecordView extends View {
	public String bottomPaneState = "";
	public HashMap<String, Subpanel> subpanels = new HashMap<String, Subpanel>();
	public ActivityStream activityStream;
	
	/**
	 * Initializes the RecordView and specifies its parent module so that it knows which fields are available.  
	 * @param parentModule - the module that owns this RecordView, likely passed in using the module's this variable when constructing the RecordView.
	 * @throws Exception
	 */
	public RecordView(RecordsModule parentModule) throws Exception {
		super(parentModule);
		activityStream = new ActivityStream();
		
		// Common control definitions. 
		addControl("saveButton", "input", "id", "SAVE_FOOTER");
		addControl("primaryButtonDropdown", "a", "css", ".btn.dropdown-toggle.btn-primary");
		addControl("saveAndViewButton", "a", "css", ".fld_save_view_button a");		
		addControl("saveAndCreateNewButton", "a", "css", ".fld_save_create_button a");		
		addControl("editButton", "a", "css", ".fld_edit_button a");
		addControl("deleteButton", "a", "css", ".fld_delete_button a");
		addControl("copyButton", "a", "css", ".fld_duplicate_button a");
		addControl("cancelButton", "a", "css", ".fld_cancel_button a");

		addControl("dismissAlert", "a", "css", "#alerts a.close");
		addControl("actionDropDown", "span", "css", ".headerpane span.icon-caret-down");
		addControl("confirmDelete", "a", "css", ".confirm");
		addControl("showMore", "button", "css", "div.record-cell .more");
		addControl("showLess", "button", "css", "div.record-cell .less");
		addControl("toggleSidebar", "a", "css", ".fld_sidebar_toggle a");

		// Subpanel and Activity Stream buttons
		addControl("dataViewButton", "a", "css", "a[data-view='subpanels']");
		addControl("activityStreamButton", "a", "css", "a[data-view='activitystream']");
	}
	
	/**
	 * Click the Save button.
	 * 
	 * You must already be on the RecordView in edit mode to use this method.
	 * Takes you to the ListView if successful, remains on the RecordView otherwise.
	 * 
	 * @throws Exception
	 */
	public void save() throws Exception {
		getControl("saveButton").click();
		VoodooUtils.pause(3000);
	}
	
	/**
	 * Opens the primary button's drop down list to expose alternate actions.
	 * 
	 * You must already be on the RecordView to use this method.
	 * Remains on the RecordView.
	 *
	 * @throws Exception
	 */
	public void openPrimaryButtonDropdown() throws Exception {
		getControl("primaryButtonDropdown").click();
		VoodooUtils.pause(500);
	}
	
	/**
	 * Click the Save and View button.
	 * 
	 * You must already be on the RecordView in edit mode to use this method.
	 * Remains on the RecordView, switching it to Detail mode if successful, remains in Edit mode otherwise.
	 * 
	 * @throws Exception
	 */
	public void saveAndView() throws Exception {
		openPrimaryButtonDropdown();
		getControl("saveAndViewButton").click();
		VoodooUtils.pause(3000); // Added to handle dual alerts
		VoodooUtils.waitForAlertExpiration();
	}
	
	/**
	 * Click the Save and Create New button.
	 * 
	 * You must already be on the RecordView in edit mode to use this method.
	 * Takes you to a blank RecordView if successful, remains on the current one otherwise.
	 * 
	 * @throws Exception
	 */
	public void saveAndCreateNew() throws Exception {
		openPrimaryButtonDropdown();
		getControl("saveAndCreateNewButton").click();
	}
	
	/**
	 * Click the Cancel button.
	 * 
	 * You must already be on the RecordView in edit mode to use this method.
	 * Remains on the RecordView, switching it to Detail mode.
	 * 
	 * @throws Exception
	 */
	public void cancel() throws Exception {
		getControl("cancelButton").click();
	}

	/**
	 * Click the Edit button.
	 * 
	 * You must already be on the RecordView in detail mode to use this method.
	 * Remains on the RecordView, switching it to edit mode.
	 * 
	 * @throws Exception
	 */
	public void edit() throws Exception {
		openPrimaryButtonDropdown();
		getControl("editButton").click();
	}
	
	/**
	 * Click the Delete button.
	 * 
	 * You must already be on the RecordView in detail mode to use this method.
	 * Remains on the RecordView and displays the delete confirmation alert.
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {
		openPrimaryButtonDropdown();
		getControl("deleteButton").click();
	}
	
	/**
	 * Click the Copy button.
	 * 
	 * You must already be on the RecordView in detail mode to use this method.
	 * Takes you to a RecordView in edit mode, prepopulated with the current record's data.
	 * 
	 * @throws Exception
	 */
	public void copy() throws Exception {
		openPrimaryButtonDropdown();
		getControl("copyButton").click();
	}
	
	/**
	 * Click the Show More link.
	 * 
	 * You must already be on the RecordView to use this method.
	 * Remains on the RecordView and displays the portion of the page hidden behind the "Show More" link.
	 * If the Show More link does not exist, this method does nothing (i.e. does not fail).
	 * If the Show More link is present but invisible (e.g. because it has already been clicked), an error will occur.    This behavior is likely to change in the future.
	 * 
	 * @throws Exception
	 */
	public void showMore() throws Exception {
		if(getControl("showMore").queryVisible()) {
			getControl("showMore").click();
		}
	}
	
	/**
	 * Click the Show Less link.
	 * 
	 * You must already be on the RecordView to use this method.
	 * Remains on the RecordView and hides the portion of the page previously hidden behind the "Show More" link.
	 * If the Show Less link is not present, this method does nothing (i.e. does not fail).
	 * If the Show Less link is present but invisible (e.g. because it has already been clicked), an error will occur.  This behavior is likely to change in the future.
	 * 
	 * @throws Exception
	 */
	public void showLess() throws Exception {
		if(getControl("showLess").queryVisible()) {
			getControl("showLess").click();
		}
	}
	
	/**
	 * Retrieve a reference to the detail mode version of a field on the RecordView. 
	 * @param fieldName - The VoodooGrimoire name for the desired control.
	 * @return a reference to the control.
	 * @throws Exception
	 */
	public VoodooControl getDetailField(String fieldName) throws Exception {
		return ((RecordsModule)parentModule).getField(fieldName).detailControl;
	}

	/**
	 * Retrieve a reference to the edit mode version of a field on the RecordView. 
	 * @param fieldName - The VoodooGrimoire name for the desired control.
	 * @return a reference to the control.
	 */
	public VoodooControl getEditField(String fieldName) throws Exception {
		return ((RecordsModule)parentModule).getField(fieldName).editControl;
	}
	
	/**
	 * Click the Activity Stream button will toggle the RecordView bottom pane state into the Activity Stream 
	 * @throws Exception
	 */
	public void showActivityStream() throws Exception {
		this.getControl("activityStreamButton").click();
		bottomPaneState = "activityStream";
		VoodooUtils.pause(2000);
	}
	
	/**
	 * Click the Data View (Subpanel) button will toggle the RecordView bottom pane state into the Data View
	 * @throws Exception
	 */
	public void showDataView() throws Exception {
		this.getControl("dataViewButton").click();
		bottomPaneState = "dataView";
		VoodooUtils.pause(3000);
	}
	
	/**
	 * Add all subpanels to the HashMap of available related subpanels displayed on this RecordView
	 * @throws Exception
	 */
	public void addSubpanels() throws Exception {
		for(Module relatedModule : ((RecordsModule)parentModule).relatedModulesMany.values()){
			subpanels.put(relatedModule.moduleNamePlural, ((RecordsModule)relatedModule).getSubpanel());
		}
	}
	
	/**
	 * Assume you are in RecordView edit mode.  It will loop through the fields to enter FieldSet data.
	 * @param editedData
	 * @throws Exception
	 */
	public void setFields(FieldSet editedData) throws Exception {
		for (String controlName : editedData.keySet()) {
			if (editedData.get(controlName) != null) {
				VoodooUtils.voodoo.log.fine("Editing field: " + controlName);
				String toSet = editedData.get(controlName);
				VoodooUtils.pause(100);
				VoodooUtils.voodoo.log.fine("Setting " + controlName + " field to: "
						+ toSet);
				getEditField(controlName).set(toSet);
				VoodooUtils.pause(100);
			}
		}
	}
	// TODO: Add getSubpanel method to return a specific subpanel
}