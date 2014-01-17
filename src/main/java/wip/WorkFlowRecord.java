package wip;


import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;

public class WorkFlowRecord {
	protected static AppModel Sugar;
	protected static WorkFlowModule workflow;
	public String name, targetModule;
	
	public WorkFlowRecord()
	{
		
	}
	
	public WorkFlowRecord(FieldSet data){
		try {
			Sugar = AppModel.getInstance();
			name = data.get("name");
			targetModule = data.get("module");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}//end WorkFlowRecord