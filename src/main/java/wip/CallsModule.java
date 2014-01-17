package wip;

import com.sugarcrm.sugar.modules.BWCModule;

/**
 * Call module object which contains tasks associated with the Calls module
 * @author 
 *
 */
public class CallsModule extends BWCModule {
	protected static CallsModule module;

	public static CallsModule getInstance() throws Exception {
		if(module == null) module = new CallsModule();
		return module;
	}

	private CallsModule() throws Exception {
		moduleNameSingular = "Call";
		recordClassName = CallRecord.class.getName();
		api = new Api();

		// TODO: Replace with loading data from disk.
		// TODO: Move the common controls for each view to its View subclass. 
		detailView.addControl("name", "span", "css", "span.fld_name input[name='name']");
		detailView.addControl("actionDropDown", "a", "css", "a.btn.dropdown-toggle.btn-primary span.icon-caret-down");
		detailView.addControl("deleteRecord", "a", "css", "span.fld_delete_button a[name='delete_button']");
		detailView.addControl("edit", "a", "css", "span.fld_edit_button a[name='edit_button']");
		
		editView.addControl("name", "input", "css", "span.fld_name input[name='name']");
		editView.addControl("save", "a", "css", "span.fld_save_button a[name='save_button']");
		editView.addControl("saveConfirm", "a", "xpath", "//*[@id='alerts']/div[1]/div/a");
		
		editView.addControl("name", "input", "css", "span.fld_name input[name='first_name']");
		editView.addControl("editSave", "a", "css", "span.fld_save_button a[name='save_button']");
		
		defaultData.put("name", "Test Call");
	}
} //CallsModule