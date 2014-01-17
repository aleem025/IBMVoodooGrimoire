package com.sugarcrm.sugar.views;

import com.sugarcrm.sugar.VoodooUtils;

public class Footer extends View {
	protected static Footer Footerbar;
	
	public static Footer getInstance() throws Exception {
		if (Footerbar == null) Footerbar = new Footer();
		return Footerbar;
	}

	private Footer() throws Exception {
		addControl("downArrow", "div", "id", "arrow");
	}
	
	public void toggle() throws Exception {
		getControl("downArrow").click();
	}
}
