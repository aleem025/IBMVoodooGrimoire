package com.sugarcrm.sugar;

import com.sugarcrm.sugar.views.View;

/**
 * Exposes functionality for interacting directly with select lists in
 * SugarCRM.  The present implementation is specific to Select2; regular select
 * tags should be handled with VoodooControl, not with this class.
 * 
 * @author David Safar <dsafar@sugarcrm.com>
 */
public class VoodooSelect extends VoodooControl {
	View selectWidget = new View();

	public VoodooSelect(String tagIn, String strategyNameIn, String hookStringIn)
			throws Exception {
		super(tagIn, strategyNameIn, hookStringIn);

		selectWidget.addControl("searchBox", "input", "css", "div#select2-drop div input");
		selectWidget.addControl("hiddenSearchBox", "input", "css", "div#select2-drop div.select2-search-hidden input");
		selectWidget.addControl("textOption", "span", "css", "div#select2-drop ul li div span");
	}

	/**
	 * Set the text of a select replacement widget.
	 * @param toSet	text/state/value to be set
	 * @throws Exception 
	 */
	public void set(String toSet) throws Exception {
		VoodooUtils.voodoo.log.info("Setting " + this + " to " + toSet);
		
		waitForElement().click();
		
		// Support dropdowns that use a search box as well as ones that don't.
		if(selectWidget.getControl("searchBox").queryVisible()) {
			selectWidget.getControl("searchBox").set(toSet);
			VoodooUtils.pause(1000);
		}

		new VoodooControl("span", "XPATH", "/html//div[@id='select2-drop']//*[contains(text(), '" + toSet + "')]").click();
	}
}
