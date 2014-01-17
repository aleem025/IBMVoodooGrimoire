package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.records.StandardRecord;
import com.sugarcrm.sugar.views.Navbar;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.candybean.datasource.FieldSet;

/**
 * Contains tasks and data associated with the Forecasts module like forecasting setup.
 * @author Mazen Louis <mlouis@sugarcrm.com>
 */
public class ForecastsModule extends Module {
	protected static ForecastsModule module;
	public Navbar navbar;
	private View setup;
	
	public static ForecastsModule getInstance() throws Exception {
		if(module == null) module = new ForecastsModule();
		return module;
	}

	private ForecastsModule() throws Exception {
		moduleNameSingular = "Forecast";
		moduleNamePlural = "Forecasts";
		navbar = Navbar.getInstance();
		setup = new View();

		
		// TODO: Replace with loading data from disk.
		setup.addControl("setupNext", "a", "css", ".fld_next_button.forecastsConfigWizardButtons a");
		setup.addControl("setupDone", "a", "css", ".fld_done_button.forecastsConfigWizardButtons a");
	}

	public void forecastSetup(FieldSet setup) throws Exception {
		navbar.navToModule(moduleNamePlural);
		VoodooUtils.pause(5000);
		this.setup.getControl("setupNext").click();
		VoodooUtils.pause(300);
		this.setup.getControl("setupNext").click();
		VoodooUtils.pause(300);
		this.setup.getControl("setupNext").click();
		VoodooUtils.pause(300);
		this.setup.getControl("setupDone").click();
		VoodooUtils.pause(20000);
	}
} // ForecastsModule