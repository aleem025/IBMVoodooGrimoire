package wip;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.views.View;

/**
 * Reports module object which contains tasks associated with the Reports module
 * like create/deleteAll
 * @author 
 *
 */
public class ReportsModule {
	protected static AppModel sugar;
	protected static ReportsModule reports;
	protected static DataSource reportData = new DataSource();
	protected View editView, reportView, listView;
	
	public static ReportsModule getInstance() throws Exception {
		if (reports == null) reports = new ReportsModule();
		return reports;
	}
	
	private ReportsModule(){
		try {
			sugar = AppModel.getInstance();
			editView = new View();
			reportView = new View();
			listView = new View();

			// TODO: Replace with loading data from disk.
			editView.addControl("rows_and_columns", "img", "name", "rowsColsImg");
			editView.addControl("accounts", "a","css","#Accounts tbody tr td a.studiolink");
			editView.addControl("contacts", "a","css","#Contacts tbody tr td a.studiolink");
			editView.addControl("next_button", "input","id","nextBtn");
			
			editView.addControl("account_tree", "a","id","ygtvlabelel1");
			editView.addControl("account_name", "div","css","#Accounts_name div:nth-of-type(1).yui-dt-liner");
			
			editView.addControl("assignedToUser_tree", "a","id","ygtvlabelel2");
			editView.addControl("assignedToUser_fullName", "div","css","#Users_full_name div:nth-of-type(1).yui-dt-liner");
			
			editView.addControl("contacts_tree", "a","id","ygtvlabelel8");
			editView.addControl("contacts_lastName", "div","css","#Contacts_last_name div:nth-of-type(1).yui-dt-liner");

			editView.addControl("report_name", "input","id","save_report_as");
			editView.addControl("save_and_run", "input","id","saveAndRunButton");
			
			listView.addControl("name", "input","css","#Reportsadvanced_searchSearchForm input.dataField");
			listView.addControl("clear_button", "input","id","search_form_clear_advanced");
			listView.addControl("search_button", "input","id","search_form_submit_advanced");
			listView.addControl("sugarActionButton", "span", "css", "ul#selectLinkTop li.sugar_action_button span");
			listView.addControl("buttonSelectAllTop", "a", "id", "button_select_all_top");
			listView.addControl("deleteListviewTop", "a", "id", "delete_listview_top");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ReportRecord create(DataSource reportGen) throws Exception{
		/*
		//Navigate to reports module and start the create process
		sugar.navbar.showAllMenus();
		sugar.navbar.selectAction("moduleTab_AllReports", "CreateReportAll");
		
		for(FieldSet report : reportGen){
			//Check to see what type of Report to create, e.g. 'Rows and Columns', 'Summation' etc.
			if(report.get("type").equals("rows_and_columns")){
				//First step in report Generation, choose Report Type "rows and columns"
				//sugar.reports.editView.getControl("rows_and_columns").click();
				VoodooUtils.pause(800);
				new VoodooControl("img", "name", "rowsColsImg")).click();
				VoodooUtils.pause(800);
				
				//TODO: Need to check which module we need to choose to make a report on
				if(report.get("module").equals("Accounts")){
					//Second step in report Generation, choose module "Accounts"
					sugar.reports.editView.getControl("accounts").click();
					VoodooUtils.pause(800);
				}
				if(report.get("module").equals("Contacts")){
					//Second step in report Generation, choose module "Accounts"
					sugar.reports.editView.getControl("contacts").click();
					VoodooUtils.pause(800);
				}
				
				//Third Step, Define Filters or click next for no filters
				//TODO: Check to see if num_filter is zero, if so simply click next
				//		If its not, then click on the appropriate fields for filters
				if(Integer.parseInt(report.get("num_filters")) > 0){
					for(int i = 1; i <= report.size(); i++){
						sugar.reports.editView.getControl("filter_" + i).click();
					}
				}
				
				//If there are no filters then simply click next
				sugar.reports.editView.getControl("next_button").click();
				VoodooUtils.pause(800);
				
				//Fourth Step, Choose Display Columns
				//for(String control : report.keySet()){
					//TODO: Check how many display columns are needed using num_display_columns 
					//		then use the display_column_module/name to choose the fields to display, 
					//		after click next button
				//}
				
				//For now the report path for columns to display is hard coded
				sugar.reports.editView.getControl("account_name").click(); //Accounts > Name
				VoodooUtils.pause(800);
				sugar.reports.editView.getControl("assignedToUser_tree").click(); //Click on Assigned To User in module tree
				VoodooUtils.pause(3000);
				sugar.reports.editView.getControl("assignedToUser_fullName").click(); //Assigned To User > Full Name
				VoodooUtils.pause(800);
				sugar.reports.editView.getControl("contacts_tree").click(); //Click on Contacts in module tree
				VoodooUtils.pause(800);
				sugar.reports.editView.getControl("contacts_lastName").click(); //Click on Contacts in module tree
				VoodooUtils.pause(800);
				
				sugar.reports.editView.getControl("next_button").click(); //click next
				VoodooUtils.pause(800);
				
				//Fifth Step, name report then click Save and Run
				sugar.reports.editView.getControl("report_name").set(report.get("report_name")); //Name the report
				sugar.reports.editView.getControl("save_and_run").click(); //click Save and Run
				VoodooUtils.pause(800);
			}
		}
		*/
		return new ReportRecord(reportGen.get(0));
	}//end create
	
	/**
	 * deleteReport() will delete the report that was created
	 */
	public void deleteReport() throws Exception {
		/*
		sugar.navbar.showAllMenus();
		sugar.navbar.selectAction("moduleTab_AllReports", "ViewReportsAll");
		
		//Clear the search filters
		sugar.reports.listView.getControl("clear_button").click();
		VoodooUtils.pause(800);
		
		//Enter in the name of the Report to search for
		sugar.reports.listView.getControl("name").set("QA_Created");
		VoodooUtils.pause(800);
		
		//Search for the Report
		sugar.reports.listView.getControl("search_button").click();

		//Click the down arrow to expand the list of Select options
		sugar.reports.listView.getControl("sugarActionButton").click();
		VoodooUtils.pause(800);
		
		//Click Select All
		sugar.reports.listView.getControl("buttonSelectAllTop").click();

		//Click on Delete
		sugar.reports.listView.getControl("deleteListviewTop").click();

		//Accept the alert pop-up
		VoodooUtils.acceptDialog();
		
		//Clear and search to populate the remaining reports back into ListView
		sugar.reports.listView.getControl("clear_button").click();
		VoodooUtils.pause(800);
		sugar.reports.listView.getControl("search_button").click();
		*/
	}//end deleteAll()
}//end ReportsModule