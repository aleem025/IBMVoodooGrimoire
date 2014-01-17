package com.sugarcrm.sugar;

import java.util.HashMap;

import com.sugarcrm.candybean.datasource.FieldSet;

public class SugarField extends HashMap<String, String> {
	private static final long serialVersionUID = 1L;
	
	public VoodooControl editControl, detailControl, massUpdateControl,
							previewPaneControl, listViewDetailControl, listViewEditControl;

	public SugarField(FieldSet values) throws Exception {
		// Change string "null"s to object nulls.
		for(String column : values.keySet()) {
			if(values.get(column).equalsIgnoreCase("null")) {
				values.put(column, null);
			}
		}

		// Save the parameters to this object.
		// TODO: Consider simplifying this with a putAll with standard keys.
		put("detail_hook_tag", values.get("detailHookTag"));
		put("detail_hook_strategy", values.get("detailHookStrategy"));
		put("detail_hook_value", values.get("detailHookValue"));
		put("edit_hook_tag", values.get("editHookTag"));
		put("edit_hook_strategy", values.get("editHookStrategy"));
		put("edit_hook_value", values.get("editHookValue"));
		put("rest_name", values.get("restName"));
		put("mass_update_tag", values.get("massUpdateTag"));
		put("mass_update_strategy", values.get("massUpdateStrategy"));
		put("mass_update_value", values.get("massUpdateValue"));
		put("default_value", values.get("defaultValue"));
		put("type", values.get("type"));
		put("preview_tag", values.get("previewTag"));
		put("preview_strategy", values.get("previewStrategy"));
		put("preview_value", values.get("previewValue"));
		put("listview_detail_tag", values.get("listViewDetailTag"));
		put("listview_detail_strategy", values.get("listViewDetailStrategy"));
		put("listview_detail_value", values.get("listViewDetailValue"));
		put("listview_edit_tag", values.get("listViewEditTag"));
		put("listview_edit_strategy", values.get("listViewEditStrategy"));
		put("listview_edit_value", values.get("listViewEditValue"));
		
		// Save a detail-mode control for later use if the hook def is complete. 
		if(get("detail_hook_tag") != null && get("detail_hook_strategy") != null &&
				get("detail_hook_value") != null) {
				detailControl = new VoodooControl(get("detail_hook_tag"),
					get("detail_hook_strategy"), get("detail_hook_value"));
			
		}
		else
			detailControl = null;

		// Save an edit-mode control for later use if the hook def is complete. 
		if(get("edit_hook_tag") != null && get("edit_hook_strategy") != null &&
				get("edit_hook_value") != null) {

			// Use a VoodooSelect if it's a select field, VoodooControl otherwise.
			if(get("type") != null && get("type").equals("select"))
				editControl = new VoodooSelect(get("edit_hook_tag"),
						get("edit_hook_strategy"), get("edit_hook_value"));
			else
				editControl = new VoodooControl(get("edit_hook_tag"),
						get("edit_hook_strategy"), get("edit_hook_value"));
		}
		else
			editControl = null;

		// Save a mass update control for later use if the hook def is complete. 
		if(get("mass_update_tag") != null && get("mass_update_strategy") != null &&
				get("mass_update_value") != null) {

			// Use a VoodooSelect if it's a select field, VoodooControl otherwise.
			if(get("type") != null && get("type").equals("select"))
				massUpdateControl = new VoodooSelect(get("mass_update_tag"),
						get("mass_update_strategy"), get("mass_update_value"));
			else
				massUpdateControl = new VoodooControl(get("mass_update_tag"),
						get("mass_update_strategy"), get("mass_update_value"));
		}
		else
			massUpdateControl = null;

		// Save a mass preview pane control for later use if the hook def is complete. 
		if(get("preview_tag") != null && get("preview_strategy") != null &&
				get("preview_value") != null) {

			// Use a VoodooSelect if it's a select field, VoodooControl otherwise.
			if(get("type") != null && get("type").equals("select"))
				previewPaneControl = new VoodooSelect(get("preview_tag"),
						get("preview_strategy"), get("preview_value"));
			else
				previewPaneControl = new VoodooControl(get("preview_tag"),
						get("preview_strategy"), get("preview_value"));
		}
		else
			previewPaneControl = null;
		
		// Save a listViewDetail control for later use if the hook def is complete. 
		if(get("listview_detail_tag") != null && get("listview_detail_strategy") != null &&
				get("listview_detail_value") != null) {

			listViewDetailControl = new VoodooControl(
					get("listview_detail_tag"),
					get("listview_detail_strategy"),
					get("listview_detail_value"));
		}
		else
			listViewDetailControl = null;
		
		// Save a listViewEdit control for later use if the hook def is complete. 
		if(get("listview_edit_tag") != null && get("listview_edit_strategy") != null &&
				get("listview_edit_value") != null) {

			// Use a VoodooSelect if it's a select field, VoodooControl otherwise.
			if(get("type") != null && get("type").equals("select"))
				listViewDetailControl = new VoodooSelect(get("listview_edit_tag"),
						get("listview_edit_strategy"), get("listview_edit_value"));
			else
				listViewDetailControl = new VoodooControl(get("listview_edit_tag"),
						get("listview_edit_strategy"), get("listview_edit_value"));
		}
		else
			listViewEditControl = null;
	}
	
	/**
	 * Return a VoodooControl or VoodooSelect representing this fields edit mode control on the listView
	 * @param rowNum 1-based index of the row
	 * @param field SugarField name of the desired field
	 * @return
	 * @throws Exception
	 */
	public VoodooControl getListViewEditControl(int rowNum) throws Exception {
		if(get("type").equals("select")){
			return new VoodooSelect(get("listview_edit_tag"), "css", "div[data-voodoo-name='recordlist'] tbody tr:nth-of-type(" + rowNum + ") " + get("listview_edit_value"));
		}
		else
			return new VoodooControl(get("listview_edit_tag"), "css", "div[data-voodoo-name='recordlist'] tbody tr:nth-of-type(" + rowNum + ") " + get("listview_edit_value"));
	}
	
	/**
	 * Return a VoodooControl representing this fields detail mode control on the listView
	 * @param rowNum 1-based index of the row
	 * @param field SugarField name of the desired field
	 * @return
	 * @throws Exception
	 */
	public VoodooControl getListViewDetailControl(int rowNum) throws Exception {
		return new VoodooControl(get("listview_detail_tag"), "css", "div[data-voodoo-name='recordlist'] tbody tr:nth-of-type(" + rowNum + ") " + get("listview_detail_value"));
	}
}