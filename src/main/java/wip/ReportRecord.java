package wip;


import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;

public class ReportRecord {
	protected static AppModel Sugar;
	protected static ReportsModule reports;
	public String name, lastname, assignedTo;
	
	public ReportRecord()
	{
		
	}
	
	public ReportRecord(FieldSet data){
		try {
			Sugar = AppModel.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}//end ReportRecord