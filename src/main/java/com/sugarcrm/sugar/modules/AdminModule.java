package com.sugarcrm.sugar.modules;

import java.io.File;
import java.util.ArrayList;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.records.StandardRecord;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.configuration.Configuration;
import com.sugarcrm.candybean.datasource.FieldSet;

/**
 * Admin module object which contains tasks associated with the Admin Tools module
 * like user management, roles management, teams management etc..
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 */
public class AdminModule extends Module {
	protected static AppModel sugar;
	protected static AdminModule module;
	public View adminTools, passwordManagement, demoTour, configureTabs;
	public final VoodooControl TOOL_PASSWORD_MANAGEMENT;
	public final VoodooControl TOOL_USER_MANAGEMENT;
	public final VoodooControl TOOL_ROLES_MANAGEMENT;
	public final VoodooControl TOOL_TEAM_MANAGEMENT;
	public final VoodooControl TOOL_SYSTEM_SETTINGS;
	public final VoodooControl TOOL_EMAIL_SETTINGS;
	public final VoodooControl TOOL_CONFIGURE_TABS;
	public final VoodooControl TOOL_STUDIO;	
	public final VoodooControl PASSWORD_MIN_LENGTH;
	public final VoodooControl PASSWORD_SETTING_ONE_UPPER;
	public final VoodooControl PASSWORD_SETTING_ONE_NUMBER;
	public final VoodooControl PASSWORD_SETTING_ONE_LOWER;
	public final VoodooControl PASSWORD_SETTING_ONE_SPECIAL;
	public final VoodooControl PASSWORD_SYSGENERATED_PASSWORD_CHBOX;
	public final VoodooControl PASSWORD_SAVE;
	
	public static AdminModule getInstance() throws Exception {
		if(module == null) 
			module = new AdminModule();
		return module;
	}

	private AdminModule() throws Exception {
		moduleNameSingular = "Admin";
		moduleNamePlural = "Admin"; // We don't pluralize this one.
		sugar = AppModel.getInstance();
		adminTools = new View();
		passwordManagement = new View();
		demoTour = new View();
		configureTabs = new View();
		// TODO: Replace with loading data from disk.
		
		// Sugar7 UI elements
		adminTools.addControl("moduleTitle", "div", "css", ".moduleTitle");
		adminTools.addControl("userManagement", "a", "id", "user_management");
		adminTools.addControl("rolesManagement", "a", "id", "roles_management");
		adminTools.addControl("teamsManagement", "a", "id", "teams_management");
		adminTools.addControl("passwordManagement", "a", "id", "password_management");
		adminTools.addControl("systemSettings", "a", "id", "configphp_settings");
		adminTools.addControl("emailSettings", "a", "id", "mass_Email_config");
		adminTools.addControl("configureTabs", "a", "id", "configure_tabs");
		adminTools.addControl("studio", "a", "id", "studio");
		
		TOOL_USER_MANAGEMENT = adminTools.getControl("userManagement");
		TOOL_ROLES_MANAGEMENT = adminTools.getControl("rolesManagement");
		TOOL_TEAM_MANAGEMENT = adminTools.getControl("teamsManagement");
		TOOL_PASSWORD_MANAGEMENT = new VoodooControl("a", "xpath", "//a[@id='password_management']");
		TOOL_SYSTEM_SETTINGS = adminTools.getControl("systemSettings");
		TOOL_EMAIL_SETTINGS = adminTools.getControl("emailSettings");
		TOOL_CONFIGURE_TABS = adminTools.getControl("configureTabs");
		TOOL_STUDIO = adminTools.getControl("studio");
		
		passwordManagement.addControl("passwordManagementLink", "a", "id", "password_management");
		passwordManagement.addControl("passwordMinLength", "input", "id", "passwordsetting_minpwdlength");
		passwordManagement.addControl("passwordSettingOneUpper", "input", "id", "passwordsetting_oneupper");
		passwordManagement.addControl("passwordSettingOneNumber", "input", "id", "passwordsetting_onenumber");
		passwordManagement.addControl("passwordSettingOneLower", "input", "id", "passwordsetting_onelower");
		passwordManagement.addControl("passwordSettingOneSpecial", "input", "id", "passwordsetting_onespecial");
		passwordManagement.addControl("SystemGeneratedPasswordCheckbox", "input", "id", "SystemGeneratedPassword_checkbox");
		passwordManagement.addControl("save", "input", "id", "btn_save");
		
		PASSWORD_MIN_LENGTH = passwordManagement.getControl("passwordMinLength");
		PASSWORD_SETTING_ONE_UPPER = passwordManagement.getControl("passwordSettingOneUpper");
		PASSWORD_SETTING_ONE_NUMBER = passwordManagement.getControl("passwordSettingOneNumber");
		PASSWORD_SETTING_ONE_LOWER = passwordManagement.getControl("passwordSettingOneLower");
		PASSWORD_SETTING_ONE_SPECIAL = passwordManagement.getControl("passwordSettingOneSpecial");
		PASSWORD_SYSGENERATED_PASSWORD_CHBOX = passwordManagement.getControl("SystemGeneratedPasswordCheckbox");
		PASSWORD_SAVE = passwordManagement.getControl("save");
		
		// Module tabs to display/hide
		// TODO: The following controls need to be uniquely identified, please perform JIRA task VOOD-466 for this
		configureTabs.addControl("AccountsModule", "div", "xpath", "//*[contains(@class,'add_table')]//div[.='Accounts']");
		configureTabs.addControl("ContactsModule", "div", "xpath", "//*[contains(@class,'add_table')]//div[.='Contacts']");
		configureTabs.addControl("LeadsModule", "div", "xpath", "//*[contains(@class,'add_table')]//div[.='Leads']");
		configureTabs.addControl("ProductsModule", "div", "xpath", "//*[contains(@class,'add_table')]//div[.='Revenue Line Items']");
		configureTabs.addControl("ProjectsModule", "div", "xpath", "//*[contains(@class,'add_table')]//div[.='Projects']");

		// Subpanel tabs to display/hide
		// TODO: The following controls need to be uniquely identified, please perform JIRA task VOOD-466 for this
		configureTabs.addControl("AccountsSubpanel", "div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='Accounts']");
		configureTabs.addControl("ContactsSubpanel", "div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='Contacts']");
		configureTabs.addControl("LeadsSubpanel", "div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='Leads']");
		configureTabs.addControl("ProductsSubpanel", "div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='Revenue Line Items']");
		configureTabs.addControl("ProjectsSubpanel", "div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='Projects']");
		
		// Module and Subpanel columns to drag to
		configureTabs.addControl("displayedModules", "tbody", "xpath", "#enabled_div div:nth-of-type(2) table tbody:nth-of-type(2)");
		configureTabs.addControl("displayedSubpanels", "tbody", "xpath", "#enabled_subpanels_div div:nth-of-type(2) table tbody:nth-of-type(2)");
		configureTabs.addControl("hiddenModules", "tbody", "xpath", "#disabled_div div:nth-of-type(2) table tbody:nth-of-type(2)");
		configureTabs.addControl("hiddenSubpanels", "tbody", "xpath", "#disabled_subpanels_div div:nth-of-type(2) table tbody:nth-of-type(2)");
		
		configureTabs.addControl("save", "input", "css", "input[value='Save']");
		
		demoTour.addControl("done", "a", "css", "div#tutorial-controls div.btn-group a[title='Done']");
	}
	
	/**
	 * Method to manage password settings.
	 * @param passSetting FieldSet of fields and values for fields to be set
	 * @throws Exception
	 */
	public void passwordSettings(FieldSet passSetting) throws Exception {
		sugar.navbar.navToAdminTools();

		VoodooUtils.focusFrame("bwc-frame"); // focus on iframe document to get access to elements in backwards compatible mode
		VoodooUtils.pause(1000);
		adminTools.getControl("passwordManagement").click();

		for(String controlName : passSetting.keySet()){
			VoodooUtils.voodoo.log.info(controlName);
			if(passSetting.get(controlName) != null) {
				String toSet = passSetting.get(controlName);
				VoodooUtils.voodoo.log.info("Setting " + controlName + " to " + toSet);
				passwordManagement.getControl(controlName).set(toSet);
				VoodooUtils.pause(300);
			}
		}
		passwordManagement.getControl("save").click();
		VoodooUtils.pause(3000);
		VoodooUtils.focusDefault(); // focus back to the default content
		VoodooUtils.waitForAlertExpiration(100000); // Wait for Loading... message to expire
	}

	/**
	 * Enable a single module to be displayed
	 * @param module single module to enable display
	 * @throws Exception
	 */
	public void enableModuleDisplay(Module module) throws Exception {
		navToConfigureTabs();
		
		// Drag and Drop Module from Hidden Module to Displayed Module column
		new VoodooControl("div", "xpath", "//*[contains(@class,'add_table')]//div[.='"+ module.moduleNamePlural + "']")
			.dragNDrop(configureTabs.getControl("displayedModules"));
		VoodooUtils.pause(300);
		saveModulesAndSubpanels();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Enables multiple modules to be displayed
	 * @param modules ArrayList of Modules to enable display
	 * @throws Exception
	 */
	public void enableModuleDisplay(ArrayList<Module> modules) throws Exception {
		navToConfigureTabs();
		
		for(Module module : modules){
			// Drag and Drop Module from Hidden Module to Displayed Module column
			new VoodooControl("div", "xpath", "//*[contains(@class,'add_table')]//div[.='"+ module.moduleNamePlural + "']")
				.dragNDrop(configureTabs.getControl("displayedModules"));
			VoodooUtils.pause(300);
		}
		saveModulesAndSubpanels();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Disable a single module
	 * @param module single module to hide
	 * @throws Exception
	 */
	public void disableModuleDisplay(Module module) throws Exception {
		navToConfigureTabs();
		
		// Drag and Drop Module from Displayed Module to Hidden Module column
		new VoodooControl("div", "xpath", "//*[contains(@class,'add_table')]//div[.='"+ module.moduleNamePlural + "']")
			.dragNDrop(configureTabs.getControl("hiddenModules"));
		saveModulesAndSubpanels();	
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Disables multiple modules
	 * @param modules ArrayList of Modules to hide
	 * @throws Exception
	 */
	public void disableModuleDisplay(ArrayList<Module> modules) throws Exception {
		navToConfigureTabs();
		
		for(Module module : modules){
			// Drag and Drop Module from Displayed Module to Hidden Module column
			new VoodooControl("div", "xpath", "//*[contains(@class,'add_table')]//div[.='"+ module.moduleNamePlural + "']")
				.dragNDrop(configureTabs.getControl("hiddenModules"));
		}
		saveModulesAndSubpanels();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Enable a single subpanel to be displayed
	 * @param module single subpanel to enable display
	 * @throws Exception
	 */
	public void enableSubpanel(Module module) throws Exception {
		navToConfigureTabs();
		
		// Drag and Drop Subpanel from Hidden Subpanel to Displayed Subpanel column
		new VoodooControl("div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='"+ module.moduleNamePlural + "']")
			.dragNDrop(configureTabs.getControl("displayedSubpanels"));
		VoodooUtils.pause(300);
		saveModulesAndSubpanels();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Enables multiple subpanels to be displayed
	 * @param module single subpanel to enable display
	 * @throws Exception
	 */
	public void enableSubpanel(ArrayList<Module> modules) throws Exception {
		navToConfigureTabs();
		
		// Drag and Drop Subpanel from Hidden Subpanel to Displayed Subpanel column
		for(Module module : modules){
			new VoodooControl("div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='"+ module.moduleNamePlural + "']")
				.dragNDrop(configureTabs.getControl("displayedSubpanels"));
			VoodooUtils.pause(300);
		}
		saveModulesAndSubpanels();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Disable a single subpanel
	 * @param module single subpanel to disable display
	 * @throws Exception
	 */
	public void disableSubpanel(Module module) throws Exception {
		navToConfigureTabs();
		
		// Drag and Drop Subpanel from Displayed Subpanel to Hidden Subpanel column
		new VoodooControl("div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='"+ module.moduleNamePlural + "']")
			.dragNDrop(configureTabs.getControl("hiddenSubpanels"));
		VoodooUtils.pause(300);
		saveModulesAndSubpanels();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Disables multiple subpanels
	 * @param module ArrayList of subpanels to disable display
	 * @throws Exception
	 */
	public void disableSubpanel(ArrayList<Module> modules) throws Exception {
		navToConfigureTabs();
		
		// Drag and Drop Subpanel from Hidden Subpanel to Displayed Subpanel column
		for(Module module : modules){
			new VoodooControl("div", "xpath", "//*[contains(@class,'add_subpanels')]//div[.='"+ module.moduleNamePlural + "']")
				.dragNDrop(configureTabs.getControl("hiddenSubpanels"));
			VoodooUtils.pause(300);
		}
		saveModulesAndSubpanels();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}
	
	/**
	 * Method to navigate to Display Modules and Subpanels in Admin Tools.
	 * This navigation method will leave you focused on the "bwc-frame" (back ward compatible frame). If needed use VoodooUtils.focusDefault()
	 * to focus back to the main Sugar content.
	 * @throws Exception
	 */
	public void navToConfigureTabs() throws Exception {
		sugar.navbar.navToAdminTools();
		VoodooUtils.pause(2000);
		VoodooUtils.focusFrame("bwc-frame"); // focus on iframe document to get access to elements in backwards compatible mode
		adminTools.getControl("configureTabs").click();
		VoodooUtils.pause(1000);
	}
	
	/**
	 * This method will click the save button, refresh the page and then wait 20seconds for the page to be fully rendered.
	 * You must be on an Admin page that has a save button.
	 * This will leave you on the same page with the save button.
	 * @throws Exception
	 */
	public void saveModulesAndSubpanels() throws Exception {
		configureTabs.getControl("save").click();
		VoodooUtils.pause(2000);
		VoodooUtils.focusDefault(); // focus back on default content
		// TODO: This line is required update the meta data for modules, this will not be needed as soon as the core code it updated.
		VoodooUtils.refresh(); // <------  HACK HACK HACK
		VoodooUtils.pause(20000);
	}
	
	/**
	 * This method will setup and configure the Google Connector in the user's profile
	 * @throws Exception
	 */
	public static void enableGoogledocsConnector() throws Exception {
		//TODO - VOOD-637 LIB for Connectors
		Configuration grimoireConfig = VoodooUtils.getGrimoireConfig();
		sugar.navbar.navToProfile();
		//This pause is needed to allow the page to render
		VoodooUtils.pause(3000);
		VoodooUtils.focusFrame("bwc-frame");
		new VoodooControl("a", "id", "edit_button").click();	
		VoodooUtils.pause(2000);
		//Tab5 is External Accounts
		new VoodooControl("a", "id", "tab5").click();	
		VoodooUtils.pause(2000);	
		new VoodooControl("a", "id", "eapm_assigned_user_create_button").click();	
		VoodooUtils.pause(2000);
		VoodooUtils.getGrimoireConfig();
		new VoodooControl("select", "id", "application").set("Google");	
		new VoodooControl("input", "id", "name").set(grimoireConfig.getValue("googledocs_user"));	
		new VoodooControl("input", "id", "password").set(grimoireConfig.getValue("googledocs_pass"));	
		new VoodooControl("input", "id", "EditViewSave").click();	
		VoodooUtils.pause(2000);	
		new VoodooControl("input", "id", "SAVE_HEADER").click();	
		VoodooUtils.focusDefault();	
		VoodooUtils.pause(5000);
	}
	
	/**
	 * This method will remove the Google Connector in the user's profile
	 * @throws Exception
	 */
	public static void disableGoogledocsConnector() throws Exception {
		//TODO - VOOD-637 LIB for Connectors
		sugar.navbar.navToProfile();	
		//This pause is needed to allow the page to render
		VoodooUtils.pause(3000);	
		VoodooUtils.focusFrame("bwc-frame");		
		new VoodooControl("a", "id", "edit_button").click();	
		VoodooUtils.pause(2000);
		//Tab5 is External Accounts
		new VoodooControl("a", "id", "tab5").click();
		//The first row in the External Apps area will be the only External App currently active
		new VoodooControl("a", "css", "div#eapm_area tr.oddListRowS1 a").click();		
		new VoodooControl("input", "id", "delete_button").click();		
		VoodooUtils.acceptDialog();		
		VoodooUtils.focusDefault();	
		VoodooUtils.pause(5000);
	}
} // AdminModule