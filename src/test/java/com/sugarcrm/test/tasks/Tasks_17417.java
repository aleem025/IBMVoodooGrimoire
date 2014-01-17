package com.sugarcrm.test.tasks;

import org.junit.Test;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest; 
import com.sugarcrm.candybean.datasource.FieldSet;

public class Tasks_17417 extends SugarTest {	
	FieldSet firstTasks; 	
	@Override
	public void setup() throws Exception {	
		firstTasks = testData.get("Tasks_17417").get(0); 
		sugar.login();	
		sugar.contacts.api.create();
	}

	/**
	 * Verify user can specify date and time for the Tasks module.
	 * @throws Exception
	 */	
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "..."); 
		
		sugar.tasks.api.create(firstTasks); 
		sugar.tasks.navToListView();
		sugar.tasks.listView.clickRecord(1);
		sugar.tasks.recordView.edit();
		// TODO: VOOD-607. Once it is fixed. Please update the step accordingly.
		new VoodooControl("i", "css", ".btn-link.btn-invisible.more i.icon-chevron-down").click();
		
		// TODO: VOOD-610.  Once it is fixed. Please update the following steps accordingly.
		//Setup Related to
		new VoodooControl("div", "css", ".span5 div.select2-container.select2.inherit-width a div").click();
		new VoodooControl("li", "css", ".select2-results li:nth-child(2)").click();
		new VoodooControl("div", "css", ".select2-container.select2.inherit-width.select2-parent a div").click();
		new VoodooControl("div", "css", ".select2-parent.select2-with-searchbox.select2-drop-active li:nth-child(1) div").click();
		new VoodooControl("input", "css", ".fld_Contacts_select input").click();
		
		//Setup Contacts Name
		new VoodooControl("div", "css", ".span12.record-cell div.select2-container.select2.inherit-width a div").click();
		new VoodooControl("div", "css", ".select2-with-searchbox.select2-drop-active li.select2-result div").click();
		new VoodooControl("span", "css", ".flex-list-view-content tr:first-child td:first-child span").click();		
		sugar.tasks.recordView.save();
		
		//Get values for these fields
		String originalSubject = sugar.tasks.recordView.getDetailField("subject").getText();
		String originalPriority = sugar.tasks.recordView.getDetailField("priority").getText();
		String originalrelAssignedTo = sugar.tasks.recordView.getDetailField("relAssignedTo").getText();
		String orifinalrelRelatedToParentType = sugar.tasks.recordView.getDetailField("relRelatedToParentType").getText();
		String originalrelRelatedToParent = sugar.tasks.recordView.getDetailField("relRelatedToParent").getText();
		String originalcontactName = sugar.tasks.recordView.getDetailField("contactName").getText();
		String originalrelTeam = sugar.tasks.recordView.getDetailField("relTeam").getText();
			
		sugar.tasks.navToListView();
		sugar.tasks.listView.clickRecord(1);
		sugar.tasks.recordView.copy();
		sugar.tasks.createDrawer.save();
		//TOD: VOOD-652. Once it is fixed. Please update the step accordingly. 
		VoodooUtils.pause(1000);
		sugar.tasks.navToListView();
		sugar.tasks.listView.clickRecord(1);
		
		//verify the copied record has kept values from original record. 
		sugar.tasks.recordView.getDetailField("subject").assertEquals(originalSubject, true);
		sugar.tasks.recordView.getDetailField("priority").assertEquals(originalPriority, true);
		sugar.tasks.recordView.getDetailField("relAssignedTo").assertElementContains(originalrelAssignedTo, true);
		sugar.tasks.recordView.getDetailField("relRelatedToParentType").assertContains(orifinalrelRelatedToParentType, true);
		sugar.tasks.recordView.getDetailField("relRelatedToParent").assertContains(originalrelRelatedToParent, true);
		sugar.tasks.recordView.getDetailField("contactName").assertContains(originalcontactName, true);
		sugar.tasks.recordView.getDetailField("relTeam").assertContains(originalrelTeam, true);
		
		VoodooUtils.voodoo.log.info(testName + " complete."); 
	}

	@Override
	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.tasks.api.deleteAll();		
		sugar.logout();
	}
}
