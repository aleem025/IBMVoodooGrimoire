package com.sugarcrm.sugar.views;

/**
 * Models the Dashboard (Home) screen in SugarCRM.
 * @author David Safar <dsafar@sugarcrm.com>
 */
public class Dashboard extends View {
	protected static Dashboard dashboard;
	
	public static Dashboard getInstance() throws Exception {
		if (dashboard == null) dashboard = new Dashboard();
		return dashboard;
	}

	public Dashboard() throws Exception {
		super();
		addControl("headerPane", "div", "css", ".headerpane");
	}
}
