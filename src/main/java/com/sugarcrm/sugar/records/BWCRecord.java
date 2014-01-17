package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.BWCModule;

/**
 * Represents a single record in a backwards compatibility module in Sugar 7.
 * Data and methods that are common to records in all bwc modules are
 * stored here.
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 * @author David Safar <dsafar@sugarcrm.com>
 */
public abstract class BWCRecord extends Record {
	protected BWCModule module;

	/**
	 * Pass-through constructor.
	 * 
	 * @param	data	A FieldSet containing the data for the new record. 
	 * @throws Exception
	 */
	public BWCRecord(FieldSet data) throws Exception {
		super(data);
	}

	/**
	 * Edit the calling Record with the supplied FieldSet data.
	 * 
	 * @param editedData	Data to edit in the record.
	 * @throws Exception
	 */
	public void edit(FieldSet editedData) throws Exception {

		navToRecord();

		module.detailView.edit();
		module.editView.setFields(editedData);

		recordData.putAll(editedData);

		module.editView.save();
		VoodooUtils.waitForAlertExpiration();
	} // edit

	/**
	 * Verifies the current record using its stored data.
	 * 
	 * @throws Exception
	 */
	public void verify() throws Exception {
		verify(recordData);
	} // verify()

	/**
	 * Verifies the current record using the specified data.
	 * 
	 * @param	verifyThis	a FieldSet of field names and expected values.
	 * @throws Exception
	 */
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

				module.detailView.getDetailField(controlName).assertEquals(
						toVerify, true);
			}
		}
		VoodooUtils.focusDefault();
	}

	/**
	 * Navigates to the record's detailView and deletes the record
	 */
	public void delete() throws Exception {
		VoodooUtils.voodoo.log.info("Deleting record " + getRecordIdentifier() +".");

		navToRecord();
		VoodooUtils.pause(7000);
		module.detailView.delete();
		VoodooUtils.pause(500);
		VoodooUtils.acceptDialog();
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration();
	}

	/**
	 * Navigates to this record by searching for it on the ListView.
	 * @throws Exception 
	 */
	public void navToRecord() throws Exception {
		VoodooUtils.voodoo.log.info("Navigating to " + getRecordIdentifier() + '.');

		// Navigate to ListView of this record's module
		module.navToListView();
		module.listView.basicSearch(getRecordIdentifier());
		module.listView.clickRecord(1);
		VoodooUtils.waitForAlertExpiration();
	}

	/**
	 * Returns the Record's name by default but has to
	 * be overridden when the Identifier isn't a name but subject or title
	 * 
	 * @return - String of the records Identification (name, subject, title
	 *         etc.)
	 */
	public String getRecordIdentifier() {
		return BWCRecord.this.recordData.get("name");
	}
} // BWCRecord
