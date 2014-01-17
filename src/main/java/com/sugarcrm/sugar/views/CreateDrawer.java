package com.sugarcrm.sugar.views;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.StandardModule;

/**
 * Models the CreateDrawer for standard SugarCRM modules.  
 * @author David Safar <dsafar@sugarcrm.com>
 *
 */
public class CreateDrawer extends View {
	// These will be used to dynamically construct controls 
	String dupRecordRow = "div[data-voodoo-name='dupecheck-list-edit'] table tbody tr";
	String dupSelectAndEditButton = "a[data-event='list:dupecheck-list-select-edit:fire']";
	String dupPreviewButton = "a[data-event='list:preview:fire']";

	/**
	 * Initializes the CreateDrawer and specifies its parent module so that it knows which fields are available.  
	 * @param parentModule - the module that owns this CreateDrawer, likely passed in using the module's this variable when constructing the CreateDrawer.
	 * @throws Exception
	 */
	public CreateDrawer(StandardModule parentModule) throws Exception {
		super(parentModule);

		// Common control definitions. 
		// saveButton represents both "Save" and "Ignore Duplicate and Save".
		addControl("saveButton", "a", "css", "#drawers .fld_save_button a");
		addControl("primaryButtonDropdown", "a", "css", "#drawers .btn.dropdown-toggle.btn-primary");
		addControl("saveAndViewButton", "a", "css", "#drawers .fld_save_view_button a");		
		addControl("saveAndCreateNewButton", "a", "css", "#drawers .fld_save_create_button a");		
		addControl("cancelButton", "a", "css", "#drawers .fld_cancel_button a");

		addControl("dismissAlert", "a", "css", "#alerts a.close");
		addControl("actionDropDown", "span", "css", "#drawers .headerpane span.icon-caret-down");
		addControl("showMore", "button", "css", "#drawers .more");
		addControl("showLess", "button", "css", "#drawers .less");
		addControl("toggleSidebar", "a", "css", "#drawers .fld_sidebar_toggle a");

		// Dup check controls.  "Ignore Duplicate and Save" is handled by the "saveButton" control.
		addControl("resetToOriginalButton", "a", "css", "#drawers .fld_restore_button a");
		addControl("duplicateCount", "span", "css", ".duplicate_count");
		addControl("duplicateHeaderRow", "tr", "css", "div[data-voodoo-name='dupecheck-list-edit'] table thead tr");
	}
	
	/**
	 * Click the Save button.
	 * 
	 * You must already be on the CreateDrawer in edit mode to use this method.
	 * Takes you to the ListView if successful, remains on the CreateDrawer otherwise.
	 * 
	 * @throws Exception
	 */
	public void save() throws Exception {
		getControl("saveButton").click();
		VoodooUtils.waitForAlertExpiration();
	}
	
	/**
	 * Opens the primary button's drop down list to expose alternate actions.
	 * 
	 * You must already be on the CreateDrawer to use this method.
	 * Remains on the CreateDrawer.
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
	 * You must already be on the CreateDrawer to use this method.
	 * Takes you to the RecordView in Detail mode if successful, remains on the CreateDrawer otherwise.
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
	 * You must already be on the CreateDrawer to use this method.
	 * Takes you to a blank CreateDrawer if successful, remains on the current one otherwise.
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
	 * You must already be on the CreateDrawer to use this method.
	 * Takes you to the RecordView in detail mode.
	 * 
	 * @throws Exception
	 */
	public void cancel() throws Exception {
		getControl("cancelButton").click();
	}

	/**
	 * Click the Show More link.
	 * 
	 * You must already be on the CreateDrawer to use this method.
	 * Remains on the CreateDrawer and displays the portion of the page hidden behind the "Show More" link.
	 * If the Show More link does not exist, this method does nothing (i.e. does not fail).
	 * If the Show More link is present but invisible (e.g. because it has already been clicked), an error will occur.  This behavior is likely to change in the future.
	 * 
	 * @throws Exception
	 */
	public void showMore() throws Exception {
		try{ // TODO: This try catch needs to be revised to use queryVisible/isVisible when JIRA ticket 430/437 are completed.
			if(getControl("showMore").queryExists()) {
				getControl("showMore").click();
				VoodooUtils.pause(2000);
			}
		} catch(Exception e) {
			if(e instanceof org.openqa.selenium.ElementNotVisibleException){
				VoodooUtils.voodoo.log.warning("Element not visible: " + e.getMessage());
			}
			else{
				throw e;
			}
		}
	}
	
	/**
	 * Click the Show Less link.
	 * 
	 * You must already be on the CreateDrawer to use this method.
	 * Remains on the CreateDrawer and hides the portion of the page previously hidden behind the "Show More" link.
	 * If the Show Less link is not present, this method does nothing (i.e. does not fail).
	 * If the Show Less link is present but invisible (e.g. because it has already been clicked), an error will occur.  This behavior is likely to change in the future.
	 * 
	 * @throws Exception
	 */
	public void showLess() throws Exception {
		try{ // TODO: This try catch needs to be revised to use queryVisible/isVisible when JIRA ticket 430/437 are completed.
			if(getControl("showLess").queryExists()) {
				getControl("showLess").click();
				VoodooUtils.pause(2000);
			}
		}catch(Exception e){
			if(e instanceof org.openqa.selenium.ElementNotVisibleException){
				VoodooUtils.voodoo.log.warning("Element not visible: " + e.getMessage());
			}
			else{
				throw e;
			}
		}
	}
	
	/**
	 * Retrieve a reference to a field on the RecordView. 
	 * @param fieldName - The VoodooGrimoire name for the desired control.
	 * @return a reference to the control.
	 */
	public VoodooControl getEditField(String fieldName) throws Exception {
		return ((StandardModule)parentModule).getField(fieldName).editControl;
	}
	
	/**
	 * Click the Select and Edit button on the specified row of the dup check
	 * panel.
	 * @param row	1-based integer specifying the row on which to click.
	 * @throws Exception
	 */
	public void selectAndEditDuplicate(int row) throws Exception {
		new VoodooControl("a", "css", dupRecordRow + ":nth-child(" + row + ") " +
			dupSelectAndEditButton).click();
	}
	
	/**
	 * Click the Preview button on the specified row of the dup check panel.
	 * @param row
	 * @throws Exception
	 */
	public void previewDuplicate(int row) throws Exception {
		new VoodooControl("a", "css", dupRecordRow + ":nth-child(" + row + ") " +
			dupPreviewButton).click();
	}
	
	/**
	 * Reset the CreateDrawer fields to their original values before they were
	 * overwritten during dup check.
	 * @throws Exception
	 */
	public void resetToOriginal() throws Exception {
		getControl("resetToOriginalButton").click();
	}
}