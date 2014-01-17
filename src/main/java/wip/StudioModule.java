package wip;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.sugar.VoodooUtils;

/**
 * Contact module object which contains tasks associated with the Contact module
 * like create/deleteAll
 * @author 
 *
 */
public class StudioModule {
	protected static AppModel sugar;
	protected static StudioModule studio;
	protected View editView;
	
	public static StudioModule getInstance() throws Exception {
		if (studio == null) studio = new StudioModule();
		return studio;
	}

	private StudioModule() {
		try {
			sugar = AppModel.getInstance();
			editView = new View();

			// TODO: Replace with loading data from disk.
			editView.addControl("studioLink", "a", "id", "studio");
			editView.addControl("studioLinkContacts", "a", "id", "studiolink_Contacts");
			editView.addControl("fieldBtn", "td", "id", "fieldsBtn");
			editView.addControl("description", "div", "id", "description");
			editView.addControl("calculated", "input", "id", "calculated");
			editView.addControl("editFormula", "input", "name", "editFormula");
			editView.addControl("formulaInput", "input", "id", "formulaInput");
			editView.addControl("fomulaSaveButton", "input", "id", "fomulaSaveButton");
			editView.addControl("studioSaveBtn", "input", "id", "fsavebtn");
			
			//TODO: Load more fields into the HashMap
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * navigate the Studio Module to make changes to field, labels, relationships, layouts, subpanels etc.
	 * TODO: Need to change this methods name and make it more data driven from the passed in DataSource
	 * @param studioOptions - DataSource which contains the path to follow in studio as well as values to make changes etc.
	 * @return boolean - true if successful - false if not
	 * @throws Exception
	 */
	public boolean navigate(DataSource studioOptions) throws Exception {
		/*
		//Navigate currently only navigates a hard coded path.
		//TODO: Navigate should take a DataSource with all the options the tester wants. 
		//Navigate to the Admin Tools
		for(FieldSet studio : studioOptions){
			sugar.navbar.selectAction("globalLinksModule", "admin_link");
			
			//Navigate to Studio
			sugar.studio.editView.getControl("studioLink").click();
			
			//Navigate to the correct module from the DataSource
			// TODO: Depending on the Value for module, need control statements to navigate to each module, right now its setup for Contacts
			if(studio.get("module").equals("Contacts")){
				sugar.studio.editView.getControl("studioLinkContacts").click();
				
				// TODO: Choose the proper module component based on passed in DataSource e.g. Labels, Fields, Relationships, Layouts, Subpanels, Mobile Layouts
				if(studio.get("moduleComponent").equals("Fields")){
					//Click on the Field button in Studio > Contacts
					sugar.studio.editView.getControl("fieldBtn").click();
					
					// Click on the field name based on passed in DataSource
					if(studio.get("fieldName").equals("Description")){
						//Click on the description field in Studio > Contacts > Fields
						sugar.studio.editView.getControl("description").click();
						
						//TODO: Need to add actions to perform on field attributes in studio e.g. changing name, making dependent, reportable etc..
						//Check to see if we need to click on the calculated checkbox
						if(studio.get("fieldCalculated") == "true"){
							//Check the Calculated checkbox
							sugar.studio.editView.getControl("calculated").click();
							
							//Click the Edit Formula button
							sugar.studio.editView.getControl("editFormula").click();
							VoodooUtils.voodoo.wait(1000);
							
							//Enter in the calculated field formula
							sugar.studio.editView.getControl("formulaInput").set(studio.get("field_formula"));
							
							//Click on the formula builder save button
							sugar.studio.editView.getControl("fomulaSaveButton").click();
							VoodooUtils.voodoo.wait(1000);
						}
					}	
				}
			}
			//Click on the save button for the field customizations
			sugar.studio.editView.getControl("studioSaveBtn").click();
			VoodooUtils.voodoo.wait(1000);
		}
		// Verify
		//TODO: Need a general method to verify that the navigation method succeeded.
		*/
		return true;		
	}//end navigate()
}//end StudioModule