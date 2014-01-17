package com.sugarcrm.sugar.views;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.Module;
import com.sugarcrm.sugar.modules.StandardModule;

/**
 * Models the Preview Pane for standard SugarCRM modules.
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 * 
 */
public class PreviewPane extends View {

	// TODO the parentModule has been included pending VOOD-454 which will make
	// PreviewPane a part of AppModel
	public PreviewPane(Module parentModule) throws Exception {
		super(parentModule);
		// Common controls for the right hand side preview pane
		addControl("close", "a", "css", ".btn.btn-invisible.closeSubdetail");
		addControl("showMore", "button", "css", "div[data-voodoo-name='preview'] .more");
		addControl("showLess", "button", "css", "div[data-voodoo-name='preview'] .less");
	}

	/**
	 * This method will click on the "x" icon to close the Preview Pane.
	 * 
	 * You must already have the preview pane open. This method will leave you
	 * in the listview with the preview pane closed.
	 * 
	 * @throws Exception
	 */
	public void closePreview() throws Exception {
		getControl("close").click();
		VoodooUtils.pause(500);
	}

	/**
	 * This method will click on the "show more" link in a preview pane.
	 * 
	 * You must already have the preview pane open. This method will leave you
	 * in the preview pane with "more" information shown.
	 * 
	 * @throws Exception
	 */
	public void showMore() throws Exception {
		try { // TODO: This try catch needs to be revised to use
				// queryVisible/isVisible when JIRA ticket 430/437 are
				// completed.
			if (getControl("showMore").queryExists()) {
				getControl("showMore").click();
				VoodooUtils.pause(500);
			}
		} catch (Exception e) {
			if (e instanceof org.openqa.selenium.ElementNotVisibleException) {
				VoodooUtils.voodoo.log.warning("Element not visible: "
						+ e.getMessage());
			} else {
				throw e;
			}
		}
	}

	/**
	 * This method will click on the "show less" link in the preview pane.
	 * 
	 * You must already have the preview pane open and have already clicked on
	 * the "show more" link This method will leave you in the preview pane with
	 * "less" information shown.
	 * 
	 * @throws Exception
	 */
	public void showLess() throws Exception {
		try { // TODO: This try catch needs to be revised to use
				// queryVisible/isVisible when JIRA ticket 430/437 are
				// completed.
			if (getControl("showLess").queryExists()) {
				getControl("showLess").click();
				VoodooUtils.pause(500);
			}
		} catch (Exception e) {
			if (e instanceof org.openqa.selenium.ElementNotVisibleException) {
				VoodooUtils.voodoo.log.warning("Element not visible: "
						+ e.getMessage());
			} else {
				throw e;
			}
		}
	}

	/**
	 * Retrieve a reference to the detail mode version of a field on the Preview
	 * Pane.
	 * 
	 * @param fieldName
	 *            - The VoodooGrimoire name for the desired control.
	 * @return a reference to the control.
	 */
	public VoodooControl getPreviewPaneField(String fieldName)
			throws Exception {
		return ((StandardModule)parentModule).getField(fieldName).previewPaneControl;
	}
}
