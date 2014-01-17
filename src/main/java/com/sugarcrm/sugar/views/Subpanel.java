package com.sugarcrm.sugar.views;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.Module;
import com.sugarcrm.sugar.modules.RecordsModule;

/**
 * Models the Subpanel for standard SugarCRM modules.
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 * 
 */
public class Subpanel extends View {
    RecordsModule identityModule = null;
    public MassUpdate massUpdate = null;

    public Subpanel(RecordsModule identityModule) throws Exception {
	this.identityModule = identityModule;
	// Common control definitions.
	addControl("selectAll", "input", "css", "div[data-voodoo-name='"
		+ identityModule.moduleNamePlural
		+ "'] .fld_undefined.list input");
	addControl("subpanelActionsMenu", "a", "css", "div[data-voodoo-name='"
		+ identityModule.moduleNamePlural + "'] .fld_undefined.list a");
	addControl("massUpdate", "a", "css", "div[data-voodoo-name='"
		+ identityModule.moduleNamePlural
		+ "'] .fld_undefined.list .dropdown-menu a[name='edit_button']");
	addControl(
		"delete",
		"a",
		"css",
		"div[data-voodoo-name='"
			+ identityModule.moduleNamePlural
			+ "'] .fld_undefined.list .dropdown-menu a[name='delete_button']");
	addControl(
		"export",
		"a",
		"css",
		"div[data-voodoo-name='"
			+ identityModule.moduleNamePlural
			+ "'] .fld_undefined.list .dropdown-menu a[name='export_button']");

	addControl("toggleSubpanel", "a", "css", "div[data-voodoo-name='"
		+ identityModule.moduleNamePlural
		+ "'] .fld_undefined.panel-top a");
	addControl("addRecord", "a", "css", "div[data-voodoo-name='"
		+ identityModule.moduleNamePlural
		+ "'] .fld_create_button.panel-top a");
	addControl("expandSubpanelActions", "a", "css",
		"div[data-voodoo-name='" + identityModule.moduleNamePlural
			+ "'] .btn.dropdown-toggle");
	addControl("linkExistingRecord", "a", "css", "div[data-voodoo-name='"
		+ identityModule.moduleNamePlural
		+ "'] .fld_select_button.panel-top a");

	// Add 99 rows of element definitions that correspond to the possible
	// rows in a subpanel
	for (int i = 1; i <= 99; i++) {
	    String checkbox = String.format("subpanelRow%02d", i);
	    String lastName = String.format("subpanelLinkRow%02d", i);
	    String expandAction = String.format("expandActionRow%02d", i);
	    String editAction = String.format("editActionRow%02d", i);
	    String unlinkAction = String.format("unlinkActionRow%02d", i);
	    String cancelAction = String.format("cancelActionRow%02d", i);
	    String saveAction = String.format("saveActionRow%02d", i);

	    addControl(checkbox, "input", "css", "div[data-voodoo-name='"
		    + identityModule.moduleNamePlural
		    + "'] .table.table-striped.dataTable tbody tr:nth-of-type("
		    + i + ") .fld_undefined.list input[name='check']");
	    addControl(lastName, "a", "css", "div[data-voodoo-name='"
		    + identityModule.moduleNamePlural
		    + "'] .table.table-striped.dataTable tbody tr:nth-of-type("
		    + i + ") .fld_last_name.list a");
	    addControl(expandAction, "a", "css", "div[data-voodoo-name='"
		    + identityModule.moduleNamePlural
		    + "'] .table.table-striped.dataTable tbody tr:nth-of-type("
		    + i
		    + ") .actions.btn-group.pull-right.list a.dropdown-toggle");
	    addControl(
		    editAction,
		    "a",
		    "css",
		    "div[data-voodoo-name='"
			    + identityModule.moduleNamePlural
			    + "'] .table.table-striped.dataTable tbody tr:nth-of-type("
			    + i
			    + ") .actions.btn-group.pull-right.list .dropdown-menu .fld_edit_button.list a");
	    addControl(
		    unlinkAction,
		    "a",
		    "css",
		    "div[data-voodoo-name='"
			    + identityModule.moduleNamePlural
			    + "'] .table.table-striped.dataTable tbody tr:nth-of-type("
			    + i
			    + ") .actions.btn-group.pull-right.list .dropdown-menu .fld_undefined.list a");
	    addControl(cancelAction, "a", "css", "div[data-voodoo-name='"
		    + identityModule.moduleNamePlural
		    + "'] .table.table-striped.dataTable tbody tr:nth-of-type("
		    + i + ") .fld_inline-cancel.edit a");
	    addControl(saveAction, "a", "css", "div[data-voodoo-name='"
		    + identityModule.moduleNamePlural
		    + "'] .table.table-striped.dataTable tbody tr:nth-of-type("
		    + i + ") .fld_inline-save.edit a.btn.btn-primary");
	}
    }

    public void initSubpanel() throws Exception {
	massUpdate = identityModule.getMassUpdate();
    }

    /**
     * Clicks on the "+" icon to add a Record of subpanel type.
     * 
     * You must be on the RecordView to click on this icon. The subpanel does
     * not need to be expanded to use this method.
     * 
     * This action will trigger the createDrawer to come into view while still
     * on the parent's RecordView
     * 
     * @throws Exception
     */
    public void addRecord() throws Exception {
	getControl("addRecord").click();
    }

    /**
     * Toggle the subpanel between collapsed view and expanded view.
     * 
     * You must be on the RecordView of the parent record to toggle subpanel.
     * 
     * Leaves you on the RecordView of the parent record with the subpanel in
     * the opposite open/closed state.
     * 
     * @throws Exception
     */
    public void toggleSubpanel() throws Exception {
	getControl("toggleSubpanel").click();
    }

    /**
     * Select all records in this subpanel. (check all checkboxes for all
     * records in list view of subpanel).
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * This method leaves you on the RecordView with all record in this subpanel
     * checked.
     * 
     * @throws Exception
     */
    public void selectAll() throws Exception {
	getControl("selectAll").click();
    }

    /**
     * Select a specific rows checkbox. (check a single check box in the list
     * view of subpanel)
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * Leaves you on the parents records RecordView with the specified record in
     * this subpanel checked.
     * 
     * @param row
     *            - Int that represents the row # to check
     * @throws Exception
     */
    public void selectRow(int row) throws Exception {
	getControl(String.format("subpanelRow%02d", row)).click();
    }

    /**
     * Click on the link to a record in the subpanel by row.
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * This method takes you to the RecordView of the record you click on.
     * 
     * @param row
     *            - Int that represents the row # to click on
     * @throws Exception
     */
    public void clickRecord(int row) throws Exception {
	getControl(String.format("subpanelLinkRow%02d", row)).click();
    }

    /**
     * Edit a record in the subpanel, choose by row #. (inline edit of a single
     * record).
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * This method triggers the inline edit of a single specified record in this
     * subpanel.
     * 
     * @param row
     *            - Int value that represents the row # to edit
     * @throws Exception
     */
    public void editRecord(int row) throws Exception {
	getControl(String.format("expandActionRow%02d", row)).click();
	VoodooUtils.pause(300);
	getControl(String.format("editActionRow%02d", row)).click();
    }

    /**
     * Unlink a record in the subpanel, choose by row #.
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * This method will trigger the unlink action for the specified row in this
     * subpanel and prompt you to confirm the action while leaving you on the
     * RecordView.
     * 
     * @param row
     *            - Int value that represents the row # to unlink
     * @throws Exception
     */
    public void unlinkRecord(int row) throws Exception {
	getControl(String.format("expandActionRow%02d", row)).click();
	VoodooUtils.pause(300);
	getControl(String.format("unlinkActionRow%02d", row)).click();
    }

    /**
     * Start the mass update process in the subpanel. Should be used in
     * conjunction with selectAll or selectRow.
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * This method will add an additional field to be used in the Mass Update
     * process while leaving you on the RecordView.
     * 
     * @throws Exception
     */
    public void openMassUpdate() throws Exception {
	getControl("subpanelActionsMenu").click();
	VoodooUtils.pause(300);
	getControl("massUpdate").click();
    }

    /**
     * Delete the selected records. Should be used in conjunction with selectAll
     * or selectRow.
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * This method will prompt you to confirm the delete action while still on
     * the RecordView of the parent module.
     * 
     * @throws Exception
     */
    public void delete() throws Exception {
	getControl("subpanelActionsMenu").click();
	VoodooUtils.pause(300);
	getControl("delete").click();
    }

    /**
     * Starts the process to link an existing record to relate to the parent
     * Record.
     * 
     * You must be on the RecordView. Subpanel must be expanded to use this
     * method.
     * 
     * This method will bring a drawer down to search for an existing record of
     * this subpanel type while still on the RecordView of the parent module.
     * 
     * @throws Exception
     */
    public void linkExistingRecord() throws Exception {
	getControl("expandSubpanelActions").click();
	VoodooUtils.pause(300);
	getControl("linkExistingRecord").click();
    }

    /**
     * Clicks the save button on a particular row of the current Subpanel View.
     * 
     * You must be on the Subpanel View to use this method. Leaves you on the
     * Subpanel View with the original record displayed.
     * 
     * @param rowNum
     *            one-based number of the row you want to save.
     * @throws Exception
     */
    public void saveAction(int rowNum) throws Exception {
	getControl(String.format("saveActionRow%02d", rowNum)).click();
    }

    /**
     * Clicks the cancel button on a particular row of the current Subpanel
     * View.
     * 
     * You must be on the Subpanel View to use this method. Leaves you on the
     * Subpanel View with the original record displayed.
     * 
     * @param rowNum
     *            one-based number of the row you want to cancel.
     * @throws Exception
     */
    public void cancelAction(int rowNum) throws Exception {
	getControl(String.format("cancelActionRow%02d", rowNum)).click();
    }
} // Subpanel
