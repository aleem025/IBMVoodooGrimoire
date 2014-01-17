package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;


public class Project_2845 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
//		[Precondition]Project record exists.[Step]
//		1. Login to system as valid user.
//		2. Click "Projects" tab on top navigation bar.
//		3. Click a link of project name in "Project" list view.
//		4. Create a project task for Project. ("View Gantt" button -> Click the left most icon with a "+" sign )
//		
//		4. The created Project Task is displayed in Project detail view page.
		/*
		FieldSet projectData = new FieldSet(); 
		projectData.put("name", "Voodoo 2");
		projectData.put("startDate", "2013-11-11");
		projectData.put("endDate", "2013-12-11");
		ProjectRecord myProject = (ProjectRecord)sugar.projects.create();
		myProject.initProjectTasks();
		*/
		// TODO: Verification.
	}

	public void cleanup() throws Exception {
		//sugar.projects.deleteAll();
		sugar.logout();
	}
}
