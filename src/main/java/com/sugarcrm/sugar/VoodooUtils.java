package com.sugarcrm.sugar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import com.sugarcrm.candybean.automation.VInterface;
import com.sugarcrm.candybean.automation.Candybean;
import com.sugarcrm.candybean.automation.control.VControl;
import com.sugarcrm.candybean.configuration.Configuration;
import com.sugarcrm.candybean.datasource.DS;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.views.Alerts;
import com.sugarcrm.test.SugarTest;

/**
 * Provides general utility functionality that is not part of the application
 * @author David Safar <dsafar@sugarcrm.com>
 */
public class VoodooUtils {
	public static Candybean voodoo;
	public static VInterface iface;
	private static final String currentWorkingPath = System.getProperty("user.dir");
	private static final String relativeResourcesPath = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;
	private static Configuration grimoireConfig;
	private static String currentWindowHandle;
	static FileHandler fileHendler;
	public static Logger log;
	
	private static DS dsWrapper = null;
	private static String propNameCsvLibsDir;
	private static String propValueCsvLibsDir;
	public static HashMap<String, DataSource> testData = null;
	
	public static String username = "admin";
	public static String password = "asdf";
		
	public static void init() throws Exception {
		// Allow users to add configuration file through CLI using -Dvoodoo.conf=[path]
		String CLIvoodooPropsPath = System.getProperty("voodoo.conf");
		String voodooPropsPath = currentWorkingPath + relativeResourcesPath + "voodoo.properties";
		if (CLIvoodooPropsPath != null) voodooPropsPath = CLIvoodooPropsPath;
		//System.out.println("voodoo.properties path: " + voodooPropsPath);
		Configuration voodooConfig = new Configuration();
    	voodooConfig.load(new File(voodooPropsPath));
		voodoo = Candybean.getInstance(voodooConfig);
		iface = voodoo.getInterface();		
		log = voodoo.log;
		
		try {  
		    // This block configure the logger with handler and formatter  		 	   
			fileHendler = new FileHandler("log/Voodoo.log"); 
		    log.addHandler(fileHendler);
		    SimpleFormatter formatter = new SimpleFormatter();  
		    fileHendler.setFormatter(formatter);   

		} catch (SecurityException e) {  
		    e.printStackTrace();  
		} catch (IOException e) {  
		    e.printStackTrace();  
		}  		
	}	
	
	/**
	 * Returns data of csv file from "libs" folder
	 * 
	 * @param csvName - name of csv file;
	 */
	public static DataSource getData(String csvName){
		propNameCsvLibsDir = "datasource.csv.libs." + csvName;
		propValueCsvLibsDir = "src/test/resources/libs";
		
		dsWrapper = new DS(csvName);
		dsWrapper.init(DS.DataType.CSV, propNameCsvLibsDir, propValueCsvLibsDir);

		testData = dsWrapper.getDataSources(csvName + ".*");
		if(testData == null || testData.size() == 0)
			VoodooUtils.voodoo.log.warning("The file " + csvName + " does not exist, or is misspelled!");
		
		return testData.get(csvName);
	}
	
	/**
	 * takeScreenshot(String fileName) takes screenshot;
	 * @param fileName - name of screenshot file; 
	 */
	public static void takeScreenshot(String fileName) {	
		miximizeWindow();
		File screenshot = ((TakesScreenshot)iface.wd).getScreenshotAs(OutputType.FILE);
		try {
            FileUtils.copyFile(screenshot, new File("log/screenshots/" + fileName + ".png").getAbsoluteFile());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

	/**
	 * miximizeWindow() Maximize the browser window;
	 */
	public static void miximizeWindow() {
		try{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
	        org.openqa.selenium.Dimension screenResolution = new org.openqa.selenium.Dimension((int) toolkit.getScreenSize().getWidth(), 
	        		 																		   (int) toolkit.getScreenSize().getHeight());
	        iface.wd.manage().window().setSize(screenResolution);
		}catch(Exception ex){}
	}
	
	
	/**
	 * Gets the Grimoire Specific Properties
	 * @return
	 */
	public static Configuration getGrimoireConfig(){
		return grimoireConfig;
	}
	
	/**
	 * Launches the application on the currently defined platform 
	 * @throws Exception 
	 */
	public static void launchApp() throws Exception {
		voodoo.log.info("Launching app...");
		String grimoirePropsPath = currentWorkingPath + relativeResourcesPath + "grimoire.properties";
		grimoireConfig = new Configuration();
		grimoireConfig.load(new File(grimoirePropsPath));
		try {
			iface.start();
		} catch(Exception e) {
			e.printStackTrace();
			iface.restart();			
		}
		iface.go(grimoireConfig.getValue("env.base_url", "http://localhost/sugar"));
		iface.maximize();
		voodoo.log.info("App launched.");
	} // end launchApp()
	
	/**
	 * Closes the application (web browser, mobile app, etc.)
	 * @throws Exception 
	 */
	public static void closeApp() throws Exception {
		iface.stop();
	} // end closeApp()
	
	/**
	 * Loads a new URL.
	 * @throws Exception 
	 */
	public static void go(String destination) throws Exception {
		iface.go(destination);
	} // end go()

	/**
	 * Gets a set of unique window identifiers and return 
	 */
	public static List <String> getWindowHandles() {
		List <String> windowHandles = null;
		//TODO: Implement getWindowHandles
		return windowHandles;
	}//end getWindowHandles()
	
	/**
	 * Switches to the passed in window by its unique handle
	 */
	public static void switchToWindow(String windowHandle) {
		iface.wd.switchTo().window(windowHandle);		
	}
	
	/**
	 * Switches to BWC frame
	 */
	public static void switchToBWCFrame() throws Exception {
		VoodooUtils.pause(10000);
		currentWindowHandle = iface.wd.getWindowHandle();
		WebElement iframe = iface.wd.findElement(By.xpath("//iframe[@id = 'bwc-frame']"));
		iface.wd.switchTo().frame(iframe);	
	}
	
	/**
	 * Switche back to main window
	 */
	public static void switchBackToWindow() {		
		iface.wd.switchTo().window(currentWindowHandle);			
	}
			
	/**
	 * Closes a browser window where needed
	 */
	public static void closeWindow(String windowHandle) {
		//TODO: Implement closeWindow
	}//end closeWindow()
	
	/**
	 * Takes a lower-case prefix and appends an existing camel-cased string,
	 * preserving camel-case for the resulting string (i.e. capitalizes the
	 * first letter of the existing string).
	 * @param prefix	the lower-case string for the beginning of the result 
	 * @param body	the existing camel-cased string to capitalize and append
	 * @return	the resulting camel-cased string
	 */
	public static String prependCamelCase(String prefix, String body) {
		return prefix + capitalize(body);
	}
	
	/**
	 * Transforms a C-style string (lower-case, words separated by _s) into a
	 * Java-style (camel-cased, no _s) string.
	 * @param toCase	the string to convert to camel case
	 * @return	the camel-cased string
	 */
	public static String camelCase(String toCase) {
	   String[] parts = toCase.split("_");
	   String camelCaseString = "";
	   for (String part : parts){
	      camelCaseString = camelCaseString + capitalize(part);
	   }
	   return camelCaseString;
	}

	/**
	 * Capitalizes the first letter of the passed string.
	 * @param s	the string to capitalize
	 * @return the capitalized string
	 */
	static String capitalize(String s) {
	    return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	}
	
	/**
	 * Switches focus to default content.
	 * 
	 * @throws Exception
	 */
	public static void focusDefault() throws Exception {
		iface.focusDefault();
	}
	
	/**
	 * Switches focus to the IFrame identified by the given zero-based index
	 * 
	 * @param index the serial, zero-based index of the iframe to focus
	 * @throws Exception
	 */
	public static void focusFrame(int index) throws Exception {
		iface.focusFrame(index);
	}
	
	/**
	 * Switches focus to the IFrame identified by the given name or ID string
	 * 
	 * @param nameOrId the name or ID identifying the targeted IFrame
	 * @throws Exception
	 */
	public static void focusFrame(String nameOrId) throws Exception {
		iface.focusFrame(nameOrId);
	}
	
	/**
	 * Switches focus to the IFrame identified by the given {@link VControl}
	 * 
	 * @param control The VControl representing a focus-targeted IFrame
	 * @throws Exception
	 */
	public static void focusFrame(VControl control) throws Exception {
		iface.focusFrame(control);
	}
	
	/**
	 * Focus a browser window by its index.
	 *
	 * <p>The order of browser windows is somewhat arbitrary and not
	 * guaranteed, although window creation time ordering seems to be
	 * the most common.</p>
	 *
	 * @param index  the window index
	 * @throws Exception	 if the specified window cannot be found
	 */
	public static void focusWindow(int index) throws Exception {
		iface.focusWindow(index);
		pause(500);
	}

	/**
	 * Focus a browser window by its window title or URL.
	 * Saves current WindowHandle before focusing to other window
	 * <p>If more than one window has the same title or URL, the first
	 * encountered is the one that is focused.</p>
	 *
	 * @param titleOrUrl the exact window title or URL to be matched
	 * @throws Exception if the specified window cannot be found
	 */
	public static void focusWindow(String titleOrUrl) throws Exception {
		currentWindowHandle = iface.wd.getWindowHandle();
		iface.focusWindow(titleOrUrl);
		pause(500);
	}
	
	/**
	 * Pause the test for the specified duration.
	 *
	 * @param ms  duration of pause in milliseconds
	 * @throws Exception if the underlying {@link Thread#sleep} is interrupted
	 */
	public static void pause(long ms) throws Exception {
		iface.pause(ms);
	}
	
	/**
	 * Click &quot;OK&quot; on a JavaScript dialog box.
	 *
	 * @throws Exception	 if no dialog box is present
	 */
	public static void acceptDialog() throws Exception {
		iface.acceptDialog();
	}
	
	public static void confirmCreateDialog() throws Exception {
		VoodooUtils.switchToBWCFrame();
		if (new VoodooControl("button","xpath", "//button[contains(text(),'Confirm Create')]").queryVisible()) {
			new VoodooControl("button","xpath", "//button[contains(text(),'Confirm Create')]").click();
		}
	}
	
	/**
	 * Dismisses a JavaScript dialog box.
	 *
	 * @throws Exception	 if no dialog box is present
	 */
	public static void dismissDialog() throws Exception {
		iface.dismissDialog();
	}
	
	/**
	 * Returns true if the interface visibly contains the given string in any non-visible=false element.
	 * 
	 * @param s The target string searched for in the interface		
	 * @param caseSensitive	Whether or not the search is case sensitive		
	 * @return Returns true if the interface visibly contains the given string
	 * @throws Exception
	 */
	public static boolean contains(String s, boolean caseSensitive) throws Exception {
		return iface.contains(s, caseSensitive);
	}
	
	/**
	 * Refresh the current webdriver attached browser window
	 * @throws Exception
	 */
	public static void refresh() throws Exception {
		// TODO: JIRA Ticket filed to add this functionality to Core so Library doesn't use the "wd" driver object from Core directly
		// VOOD-309
		iface.wd.navigate().refresh();
	}
	
	/**
	 * Waits up to 15s for an alert dialog box to not be visible.
	 * @throws Exception if no Alert Dialog exists or ms seconds have elapsed
	 * and the Alert Dialog still exists.
	 */
	public static void waitForAlertExpiration() throws Exception {
		waitForAlertExpiration(15000);
	}
	
	/**
	 * Close Alert Dialog
	 * @throws Exception
	 */
	public static void closeAlert() throws Exception {		
		if(new VoodooControl("div","css","#alerts").queryVisible()){
			if (new VoodooControl("a","xpath","//a[@class = 'close']").queryVisible())	
				new VoodooControl("a","xpath","//a[@class = 'close']").click();
			
			//Temporary, should be changed starts from sugar7_migration build 5
			if (new VoodooControl("a","xpath","//a[@class = 'btn-link confirm']").queryVisible())        
                new VoodooControl("a","xpath","//a[@class = 'btn-link confirm']").click(); 
			
			// Actual for sugar7_migration starts from build 5
			//if (new VoodooControl("a","css","a.span6.alert-btn-confirm").queryVisible())	
			//	new VoodooControl("a","css","a.span6.alert-btn-confirm").click();		
		}
	}

	/**
	 * Waits for an alert dialog box to not be visible.
	 * @param	ms	Timeout in milliseconds.
	 * @throws Exception if no Alert Dialog exists or ms seconds have elapsed
	 * and the Alert Dialog still exists.
	 */
	public static void waitForAlertExpiration(int ms) throws Exception {
		long totalTime = 0;
		long iterationStartTime, iterationDuration = 0;

		VoodooControl alertBox = new VoodooControl("div","css","#alerts");
		
		while(totalTime < ms){
			iterationStartTime = System.currentTimeMillis();
			
			if(!alertBox.queryVisible()){
				return;
			}
			// The following line is purely for debugging purposes to test the timing in the while loop.
//			VoodooUtils.voodoo.log.info("Waited for " + totalTime + "ms");
			
			VoodooUtils.pause(250);
			iterationDuration = System.currentTimeMillis() - iterationStartTime;
			totalTime = totalTime + iterationDuration;
		}
		
		Alerts alert = new Alerts();
		
		// Test for Confirmation Navigation, if present, confirm navigation
		// TODO: Need CORE support to confirm the presence of a modal dialog box
		//		 e.g. Navigation confirmation box
		// Filed JIRA VOOD-529 against CORE for this.
//		if(){
//			acceptDialog();
//		}
		// Test for warning, error or other type of alert, if present, click on "x" to close
		while(new VoodooControl("div", "css", "#alerts").queryVisible()) {
			if(new VoodooControl("div","css","#alerts .cancel").queryVisible() == true){
			alert.cancelAlert();
			} else if(new VoodooControl("div","css","#alerts .close").queryVisible() == true){
				alert.closeAlert();
			} else {
				throw new UnexpiredAlertException();
			}
		}
	}
	
	/**
	 * Gets Current Date and Time
	 * @param dateFormat format to be used when forming the date/time stamp e.g. "yyyy/MM/dd HH:mm"
	 * @return String representation of Date and Time e.g. "2013/07/10 16:45"
	 * @throws Exception
	 */
	public static String getCurrentTimeStamp(String dateFormat) throws Exception {
		SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
}
