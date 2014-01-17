package wip;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.StandardRecord;

public class QuoteRecord extends StandardRecord {
	// TODO: Complete the list of fields.  A data structure may be better.
	public String subject, stage, validUntil, relBillingAccountName;
	
	public QuoteRecord(FieldSet data) throws Exception {
		super(data);
		//module = sugar.quotes;
		subject = data.get("subject");
		stage = data.get("stage");
		validUntil = data.get("validUntil");
		relBillingAccountName = data.get("relBillingAccountName");
	}
}