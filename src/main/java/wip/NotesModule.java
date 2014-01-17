package wip;

import com.sugarcrm.sugar.modules.BWCModule;

/**
 * Notes module object which contains tasks associated with the Notes module
 * @author mlouis 
 */
public class NotesModule extends BWCModule{
	protected static NotesModule module;

	public static NotesModule getInstance() throws Exception {
		if (module == null) module = new NotesModule();
		return module;
	}
	
	private NotesModule() throws Exception {
		moduleNameSingular = "Note";
		recordClassName = NoteRecord.class.getName();
		
		// TODO: Move the common controls for each view to its View subclass. 
		editView.addControl("name", "input", "css", "span.fld_name input[name='name']");
		editView.addControl("note", "textarea", "css", "span.fld_description.textarea-text textarea[name='description']");
		editView.addControl("selectRelatedModule", "select", "css", "span.fld_parent_name select[name='parent_type']");
		editView.addControl("selectRelatedButton", "b", "css", "span.fld_parent_name div:nth-of-type(2) b");
		editView.addControl("selectRelatedRecordInput", "input", "css", "div.select2-search input.select2-input");
		editView.addControl("selectRelatedRecordSuggestion", "span", "css", "ul.select2-search li div span.select2-match");
		editView.addControl("save", "a", "css", "span.fld_save_button a[name='save_button']");		
		editView.addControl("saveConfirm", "a", "xpath", "//*[@id='alerts']/div[1]/div/a");
		
		detailView.addControl("edit", "a", "css", "span.fld_edit_button a[name='edit_button']");
		detailView.addControl("name", "div", "css", "span.fld_name div");
		detailView.addControl("note", "span", "css", "span.fld_description.textarea-text");
		
		editView.addControl("name", "input", "css", "span.fld_name input[name='name']");
		editView.addControl("note", "textarea", "css", "span.fld_description.textarea-text textarea[name='description']");
		editView.addControl("save", "a", "css", "span.fld_save_button a[name='save_button']");
	}
}