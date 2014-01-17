package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;

public class CallRecord extends StandardRecord {
	public CallRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.calls;
		recordData.putAll(data);
	}
	
	/**
	 * Verifies the current Call record using the specified data.
	 * 
	 * @param verifyThis
	 * @throws Exception
	 */
	@Override
	public void verify(FieldSet verifyThis) throws Exception {
		navToRecord();

		VoodooUtils.focusFrame("bwc-frame");

		for(String controlName : verifyThis.keySet()) {
			if(verifyThis.get(controlName) != null) {
				if(module.detailView.getDetailField(controlName) == null) {
					continue;
				}
				VoodooUtils.voodoo.log.info("Verifying field " + controlName);
				String toVerify = verifyThis.get(controlName);

				// In order to verify compound fields containing date and time
				// elements the assertContains is needed
				module.detailView.getDetailField(controlName).assertContains(
						toVerify, true);
			}
		}
		VoodooUtils.focusDefault();
	}
} // CallRecord
