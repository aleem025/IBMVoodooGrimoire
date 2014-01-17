package wip;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.StandardRecord;

public class KBTagRecord extends StandardRecord{
	public String tag;

	public KBTagRecord(FieldSet data) throws Exception{
		super(data);
		//module = sugar.knowledgeBase;
		tag = data.get("tag");
	}
	
}//end KBTag