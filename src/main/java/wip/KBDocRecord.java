package wip;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.StandardRecord;

public class KBDocRecord extends StandardRecord {
	public String title, assignedTo;
	
	public KBDocRecord(FieldSet data) throws Exception{
		super(data);
		//module = sugar.knowledgeBase;
		
		title = data.get("title");
		assignedTo = data.get("assignedTo");
	}
	
	public void removeTag(String tag) throws Exception {
		// Navigate to KBModule if not already there
		// TODO: Add logic to determine location in Sugar, navigate to KBModule if not there already
		//sugar.navbar.showAllModules();
		//sugar.navbar.selectMenuItem("moduleTab_AllKBDocuments", "ViewArticlesAll");
		
	}
}//end KBDocRecord