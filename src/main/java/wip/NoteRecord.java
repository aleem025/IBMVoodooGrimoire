package wip;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.StandardRecord;

public class NoteRecord extends StandardRecord {
	public String name, assignedTo;
	
	public NoteRecord(FieldSet data) throws Exception {
		// TODO Auto-generated constructor stub
		super(data);
//		module = sugar.notes;
		name = data.get("name");
	}
} // NoteRecord
