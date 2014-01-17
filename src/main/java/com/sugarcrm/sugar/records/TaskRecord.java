package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;

public class TaskRecord extends StandardRecord {
	
	public TaskRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.tasks;
		recordData.putAll(data);
	}
	
	/**
	 * getRecordIdentifier will return the Task Records Subject
	 * @return - String of the records Identification (subject)
	 */
	@Override
	public String getRecordIdentifier(){
		//TODO: This method needs to be made to handle different situations
		return recordData.get("subject");
	}
	
	/**
	 * Verifies the current record using the specified data.
	 * 
	 * @param verifyThis
	 * @throws Exception
	 */
	@Override
	public void verify(FieldSet verifyThis) throws Exception {
		navToRecord();

		module.recordView.showMore();

		if (module.moduleNamePlural.equalsIgnoreCase("Users")) {
			VoodooUtils.focusFrame("bwc-frame");
		}

		for (String controlName : verifyThis.keySet()) {
			if (verifyThis.get(controlName) != null) {
				if (module.recordView.getDetailField(controlName) == null) {
					continue;
				}
				VoodooUtils.voodoo.log.info("Verifying field " + controlName);
				String toVerify = verifyThis.get(controlName);

				if (controlName.contains("Date")) {
					module.recordView.getDetailField(controlName).assertContains(toVerify, true);
				} else {
					module.recordView.getDetailField(controlName).assertEquals(toVerify, true);
				}
			}
		}
		
		if (module.moduleNamePlural.equalsIgnoreCase("Users")) {
			VoodooUtils.focusDefault();
		}
	}
} // end TaskRecord