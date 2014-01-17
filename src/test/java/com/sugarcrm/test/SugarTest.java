package com.sugarcrm.test;

import java.util.HashMap;

import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.sugarcrm.sugar.*;
import com.sugarcrm.candybean.configuration.Configuration;
import com.sugarcrm.candybean.datasource.DS;
import com.sugarcrm.candybean.datasource.DataSource;

public abstract class SugarTest {
	protected static AppModel sugar;
	protected String testName;
	public HashMap<String, DataSource> testData = null;
	
	public String CountrySelect;
	DS dsWrapper = null;
	String propNameCsvBaseDir = "datasource.csv.baseDir";
	String propValueCsvBaseDir = "src/test/resources/data";
	String baseUrl;
	Configuration config;

	@BeforeClass
	public static void setupOnce() throws Exception {}

	@Before
	public void baseSetup() throws Exception {
		VoodooUtils.init();
		VoodooUtils.voodoo.log.info("Setting up SugarTest...");
		
		VoodooUtils.launchApp();
		config = VoodooUtils.getGrimoireConfig();
		testName = SugarTest.this.getClass().getSimpleName();
		baseUrl = config.getValue("env.base_url", "http://localhost/sugar");
		
		if(config.getValue("code_coverage", "false").equals("true")) {
			VoodooUtils.go(baseUrl + "?coverageStartTest=" + testName);
		}

		sugar = AppModel.getInstance();
		sugar.init();

		VoodooUtils.voodoo.log.info("Setting up " + testName + "...");
		dsWrapper = new DS(testName);
		dsWrapper.init(DS.DataType.CSV, propNameCsvBaseDir, propValueCsvBaseDir);

		testData = dsWrapper.getDataSources(testName + ".*");
		if(testData == null || testData.size() == 0)
			VoodooUtils.voodoo.log.warning("The file " + testName + " does not exist, or is misspelled!");

		SugarTest.this.setup();
		VoodooUtils.voodoo.log.info(testName + " setup complete.");
		VoodooUtils.voodoo.log.info("SugarTest setup complete.");
	}
	
	@After
	public void baseCleanup() throws Exception {
		VoodooUtils.voodoo.log.info("Cleaning up SugarTest...");
		
		VoodooUtils.voodoo.log.info("Cleaning up " + testName + "...");
		VoodooUtils.takeScreenshot(testName + "_" + VoodooUtils.getCurrentTimeStamp("yyyy.MM.dd_HH.mm"));
		
		SugarTest.this.cleanup();

		VoodooUtils.voodoo.log.info(testName + " cleanup complete.");
		
		if(config.getValue("code_coverage", "false").equals("true")) {
			VoodooUtils.go(baseUrl + "?coverageEndTest=1");
		}

		VoodooUtils.closeApp();
		
		// TODO: Remove this try/catch after VOOD-370 is fixed.
		try {
			dsWrapper.cleanup();
		} catch(AssertionFailedError e) {
			VoodooUtils.voodoo.log.warning("An exception occurred while cleaning up the DataSource wrapper object.");
			e.printStackTrace();			
		}
		
		VoodooUtils.voodoo.log.info("SugarTest cleanup complete.");
	}

	@AfterClass
	public static void cleanupOnce() {}
	
	public abstract void setup() throws Exception;
	public abstract void cleanup() throws Exception;
}