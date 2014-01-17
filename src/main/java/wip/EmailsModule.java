package wip;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.views.View;

/**
 * Email module object which contains tasks associated with the Email module
 * like create/verify/deleteAll
 * @author wli
 *
 */
public class EmailsModule {
	protected static AppModel sugar;
	protected static EmailsModule emails;
	protected View editView, listView;
	
	public static EmailsModule getInstance() throws Exception {
		if (emails == null) emails = new EmailsModule();
		return emails;
	}

	private EmailsModule() {
		try {
			sugar = AppModel.getInstance();
			editView = new View();
			listView = new View();

			// TODO: Replace with loading data from disk.
			
			// ::CREATE::
			// Organize by Name
			editView.addControl("nameLink", "", "xpath", "//*[@id='MassUpdate']/table/tbody/tr[2]/th[1]/div/a"); 
			// Get control for first option from list
			editView.addControl("firstLink", "", "xpath", "//*[@id='MassUpdate']/table/tbody/tr[3]/td[4]/b/a"); 
			// Get control for 'To'
			editView.addControl("emailTo", "", "id", "addressTO0"); 
			// Get control for 'Subject'
			editView.addControl("emailSubject", "", "id", "emailSubject0"); 
			
			// TODO: Get control for 'Email Body' and add text 
			
			// Get control for 'Save Draft' button
			editView.addControl("saveDraft", "", "xpath", "//*[@id='composeHeaderTable0']//button[2]"); 
			// Get control to accept pop-up message
			editView.addControl("errorAccept", "", "xpath", "//*[@id='sugarMsgWindow']/div[3]/span/button"); 
			// Get Control to open Drop down Menu from subPanel Activities
			listView.addControl("openMenu", "", "xpath", "//*[@id='list_subpanel_activities']//span");
			// Get Control to select 'Compose Email' from subPanel
			listView.addControl("composeEmail", "", "id", "Activities_composeemail_button");
			
			// ::VERIFY::
			// Get Control for selecting Draft Email created from the 'My Email' list
			editView.addControl("editFirstEmail", "", "xpath", "//*[@id='container']//div[@id='yui-gen0']//div[@id='emailtabs']//div[@class='yui-content']//div[@id='listViewDiv']//div[@id='yui-gen7']//div[@id='emailGrid']//div[3]//table//tbody[@class='yui-dt-data']/tr");			
			// Get Control for the Subject line of the Draft Email
			editView.addControl("draftEmailSubject", "", "xpath", "//*[@id='emailGrid']/div[3]/table/tbody[@class='yui-dt-data']/tr/td[5]/div[@class='yui-dt-liner']"); 
			
			
			// ::DELETE::
			// Get Control for right clicking on the Draft Email from the 'My Email' list
			editView.addControl("rightClickEmail", "", "xpath", "//*[@id='container']//div[@id='yui-gen0']//div[@id='emailtabs']//div[@class='yui-content']//div[@id='listViewDiv']//div[@id='yui-gen7']//div[@id='emailGrid']//div[3]//table//tbody[@class='yui-dt-data']/tr");
			// Get Control to delete email
			editView.addControl("deleteEmail", "", "xpath", "//*[@id='emailContextMenu']//ul[@class='first-of-type']/li[7]");
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * create an Email record via the UI in Sugar
	 * @return boolean - true if successful - false if not
	 * @throws Exception
	 */
	public boolean create() throws Exception {
		
		/*
		 * TODO: THIS NEEDS TO BE IMPLEMENTED ON SUGAR 7
		 */
		// Locate Contacts Navigation
		/*
		sugar.navbar.showAllMenus();
		
		// Select Contact and then 'View Contact'
		sugar.navbar.selectAction("moduleTab_AllContacts", "ViewContactsAll");
		VoodooUtils.pause(800);
		sugar.footer.toggle();
		
		// Organize by name
		sugar.emails.editView.getControl("nameLink").click(); 
		// Select first option	
		sugar.emails.editView.getControl("firstLink").click(); 	
		VoodooUtils.pause(800);
		sugar.footer.toggle();
		
		// Open the Drop down Menu from Activities SubPanel and Select Compose Email
		sugar.emails.listView.getControl("openMenu").click();
		sugar.emails.listView.getControl("composeEmail").click();
		VoodooUtils.pause(1000);
		
		// Accept Error Message:
		sugar.emails.editView.getControl("errorAccept").click();
		VoodooUtils.pause(800);
		
		// Fill in the Email Fields
		// TODO: Get related Contact - sugar.emails.editView.getControl("emailTo").set("ADD ALL CONTACT");
		
		sugar.emails.editView.getControl("emailSubject").set("Draft Email Test");
		// TODO: add content to Email body
		VoodooUtils.pause(800);
		
		// Select the 'Save Draft' Button
		sugar.emails.editView.getControl("saveDraft").click();
		VoodooUtils.pause(2000);
		sugar.footer.toggle();
		*/
		return true;		
	}

	public boolean getContact() throws Exception {
		return true;
	}
	
	public boolean verify() throws Exception {
		
		// Navigate to View Emails
		sugar.navbar.showAllModules();

		// The following call is incorrect, please look at selectMenuItem() method for the new definition
//		sugar.navbar.selectMenuItem("moduleTab_AllEmails", "ViewMyEmailAll");
		VoodooUtils.pause(800);
		sugar.footer.toggle();
		
		// Click on the Email to Open and to verify Data
		//sugar.emails.editView.getControl("editFirstEmail").click();
		VoodooUtils.pause(5000);
		
		//TODO:: VERIFY DATA
		
		return true;
	}
	
	/**
	 * deleteAll() will delete all the records that were created in Accounts
	 */
	public void deleteAll() throws Exception {
		
		// Navigate to View Email
		sugar.navbar.showAllModules();

		// The following call is incorrect, please look at selectMenuItem() method for the new definition
//		sugar.navbar.selectMenuItem("moduleTab_AllEmails", "ViewMyEmailAll");
		VoodooUtils.pause(800);
		sugar.footer.toggle();

		// Right Click on Draft Email and Delete
		//sugar.emails.editView.getControl("rightClickEmail").rightClick();
		//sugar.emails.editView.getControl("deleteEmail").click();
		VoodooUtils.pause(800);
		
		// Accept to Delete
		VoodooUtils.acceptDialog();
		VoodooUtils.pause(2000);
		
	}//end deleteAll()

}//end Accounts