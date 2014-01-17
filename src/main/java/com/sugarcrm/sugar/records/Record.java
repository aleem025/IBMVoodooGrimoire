package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;

public abstract class Record {
	protected static AppModel sugar;
	public FieldSet recordData = new FieldSet();
	
	public Record(FieldSet data) throws Exception {
		sugar = AppModel.getInstance();
		// TODO: This deepClone should be looked at.
		// Is this needed or can it simply be recordData = data; ?
		recordData = data.deepClone();
	}

	public abstract void delete() throws Exception;
	public abstract void navToRecord() throws Exception;
	public abstract void verify() throws Exception;
	public abstract String getRecordIdentifier();
}