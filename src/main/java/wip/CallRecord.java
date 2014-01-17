package wip;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.StandardRecord;

public class CallRecord extends StandardRecord {
	public String name;
	
	public CallRecord(FieldSet data) throws Exception {
		super(data);
//		module = sugar.calls;
		name = data.get("name");
		// TODO Auto-generated constructor stub
	}

}
