package com.sugarcrm.sugar.views;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;

public class Alerts extends View {
	protected static Alerts alert;
	
	public static Alerts getInstance() throws Exception {
		if (alert == null) alert = new Alerts();
		return alert;
	}

	public Alerts() throws Exception {		
		addControl("confirmAlert", "a", "css", "#alerts .btn-link.confirm");
		addControl("cancelAlert", "a", "css", "#alerts .btn-link.cancel");
		addControl("closeAlert", "a", "css", "#alerts .close");
	}
	
	/**
	 * Clicks the Confirm link in this Alert Dialog.
	 * 
	 * An Alert dialog box must be on the screen to use this method.
	 * 
	 * When used, this alert dialog box will not be visible and the action
	 * that triggered the alert box to appear will continue.
	 * 
	 * @throws Exception if no Alert Dialog exists
	 */
	public void confirmAlert() throws Exception {
		getControl("confirmAlert").click();
		VoodooUtils.pause(500);
	}
	
	/**
	 * Clicks the Cancel link in this Alert Dialog.
	 * 
	 * An Alert dialog box must be on the screen to use this method.
	 * The Alert dialog box must also have a cancel link/button.
	 * 
	 * When used, this alert dialog box will not be visible and the action
	 * that triggered the alert box to appear will not continue.
	 * 
	 * @throws Exception if no Alert Dialog exists
	 */
	public void cancelAlert() throws Exception {
		getControl("cancelAlert").click();
		VoodooUtils.pause(500);
	}
	
	/**
	 * Clicks the Close "x" icon in this Alert Dialog.
	 * 
	 * An Alert dialog box must be on the screen to use this method.
	 * 
	 * When used, this alert dialog box will be forcibly closed.
	 * 
	 * @throws Exception if no Alert Dialog exists
	 */
	public void closeAlert() throws Exception {
		getControl("closeAlert").click();
		VoodooUtils.pause(500);
	}
	
	/**
	 * Clicks on the desired link by its index in the Alert Dialog DOM
	 * 
	 * An Alert dialog box must be on the screen to use this method.
	 * There also must be at least 1 link in this Alert dialog box.
	 * 
	 * When used, this alert dialog box will not be visible and the action
	 * associated with the link will commence
	 * 
	 * @param index 1-based index of the link
	 * @throws Exception if no Alert Dialog exists
	 */
	public void clickLink(int index) throws Exception {
		// We use (index+1) because the close "x" link is always the first a link in the Alert Dialog
		new VoodooControl("a","css","#alerts a:nth-of-type(" + (index+1) + ")").click();
		VoodooUtils.pause(500);
	}
}
