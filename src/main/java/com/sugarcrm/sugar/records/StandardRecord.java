package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.StandardModule;

/**
 * Base class from which the Record objects for all standard modules extend.
 * Data and methods that are common to records in all standard modules are
 * stored here.
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 * @author David Safar <dsafar@sugarcrm.com>
 */
public abstract class StandardRecord extends Record {
	protected StandardModule module;

	/**
	 * Pass-through constructor.
	 * 
	 * @param	data	A FieldSet containing the data for the new record. 
	 * @throws Exception
	 */
	public StandardRecord(FieldSet data) throws Exception {
		super(data);
	}

	/**
	 * Edit the calling Record with the supplied FieldSet data.
	 * 
	 * @param	editedData	Data to edit in the record.
	 * @throws Exception
	 */
	public void edit(FieldSet editedData) throws Exception {

		navToRecord();

		module.recordView.edit();
		module.recordView.showMore();
		module.recordView.setFields(editedData);

		recordData.putAll(editedData);

		module.recordView.save();
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
	 * @param verifyThis
	 * @throws Exception
	 */
	public void verify(FieldSet verifyThis) throws Exception {
		navToRecord();

		module.recordView.showMore();

		if(module.moduleNamePlural.equalsIgnoreCase("Users")) {
			VoodooUtils.focusFrame("bwc-frame");
		}

		for(String controlName : verifyThis.keySet()) {
			if(verifyThis.get(controlName) != null) {
				if(module.recordView.getDetailField(controlName) == null) {
					continue;
				}
				VoodooUtils.voodoo.log.info("Verifying field " + controlName);
				String toVerify = verifyThis.get(controlName);

				module.recordView.getDetailField(controlName).assertEquals(
						toVerify, true);
			}
		}
		if(module.moduleNamePlural.equalsIgnoreCase("Users")) {
			VoodooUtils.focusDefault();
		}
	}

	/**
	 * Verifies the preview pane of the current record using its stored data.
	 * 
	 * @throws Exception
	 */
	public void verifyPreview() throws Exception {
		verifyPreview(recordData);
	}

	/**
	 * Verifies the preview pane of the current record using the specified data.
	 * 
	 * @throws Exception
	 */
	public void verifyPreview(FieldSet verifyThis) throws Exception {
		module.navToListView();
		module.listView.setSearchString(getRecordIdentifier());

		// Click the preview record icon
		module.listView.previewRecord(1);

		module.previewPane.showMore();

		for(String controlName : verifyThis.keySet()) {
			if(verifyThis.get(controlName) != null) {
				if(module.getField(controlName).previewPaneControl == null) {
					continue;
				}
				VoodooUtils.voodoo.log.info("Verifying field " + controlName);
				String toVerify = verifyThis.get(controlName);
				module.getField(controlName).previewPaneControl.assertEquals(toVerify, true);
			}
		}
	}

	/**
	 * saveDraft will start the process to send an email but save it as a draft
	 * instead. All data is contained in the passed in DataSource
	 * 
	 * @param draftOptions
	 *            - values for fields in email draft
	 * @throws Exception
	 */
	public void saveDraft(DataSource draftOptions) throws Exception {
		// TODO: Need logic to check if you are in the correct view to compose
		// an email
		// if not, need to navigate to the correct view. For now, force
		// navigation

		// Click on Compose Email
		module.recordView.getControl("activityOption").click();
		module.recordView.getControl("composeEmail").click();

		// Accept Popup Dialog warning that no outbound server information is
		// present, if needed
		if(new VoodooControl("div", "id", "sugarMsgWindow").getAttribute("visible").equals("true")) {
			module.recordView.getControl("acceptOk").click();
		}

		// Enter subject and body.
		module.recordView.getControl("subject").set(draftOptions.get(0).get("subject"));
		module.recordView.getControl("emailBody").set(draftOptions.get(0).get("body"));

		// Click Save Draft
		module.recordView.getControl("saveDraft").click();
		VoodooUtils.waitForAlertExpiration();
	}

	/**
	 * Navigates to the record's detailView and deletes the record
	 */
	public void delete() throws Exception {
		VoodooUtils.voodoo.log.info("Deleting record " + getRecordIdentifier() +".");

		navToRecord();
		VoodooUtils.pause(7000);
		module.recordView.delete();
		VoodooUtils.pause(500);
		module.recordView.getControl("confirmDelete").click();
		VoodooUtils.waitForAlertExpiration();
	}

	/**
	 * Navigates to this record by searching for it on the ListView.
	 * @throws Exception 
	 */
	public void navToRecord() throws Exception {
		VoodooUtils.voodoo.log.info("Navigating to " + getRecordIdentifier() + ".");

		// Navigate to ListView of this record's module
		module.navToListView();
		module.listView.setSearchString(getRecordIdentifier());
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
		return StandardRecord.this.recordData.get("name");
	}

	/**
	 * Creates Comment in Activity Stream on a Record.
	 * 
	 * This method leaves you on the Activity Stream view of this Record.
	 * 
	 * @param content Content of the Entry
	 * @throws Exception
	 */
	public void createComment(String content) throws Exception {
		navToRecord();
		// If the Comment button is NOT visible we are not on the Activity Stream view, so click on the Show Activity Stream button
		if(!(new VoodooControl("button","css","div[data-voodoo-name='activitystream-omnibar'] button").queryVisible())){
			module.recordView.showActivityStream();
			VoodooUtils.pause(1000);
		}
		module.recordView.activityStream.createComment(content);
	}

	/**
	 * Creates a Reply to an Entry.
	 * 
	 * This method leaves you on the Activity Stream view of this Record.
	 * 
	 * @param content String content of Reply
	 * @param rowNum int index of Entry
	 * @throws Exception
	 */
	public void createReply(String content, int rowNum) throws Exception {
		navToRecord();
		// If the Comment button is NOT visible we are not on the Activity Stream view, so click on the Show Activity Stream button
		if(!(new VoodooControl("button","css","div[data-voodoo-name='activitystream-omnibar'] button").queryVisible())) {
			module.recordView.showActivityStream();
			VoodooUtils.pause(1000);
		}
		module.recordView.activityStream.createReply(content, rowNum);
	}

	/**
	 * Asserts the comment does or does not contain the string content.
	 * 
	 * There must already be a comment in the Activity Stream.
	 * This assert leaves you on the Activity Stream view of this Record.
	 * 
	 * @param content content to be asserted
	 * @param entryRow 1-based index of the comment
	 * @param shouldContain true for positive assert, false for negative "assertnot"
	 * @throws Exception
	 */
	public void assertCommentContains(String content, int entryRow, boolean shouldContain) throws Exception {
		navToRecord();
		// If the Comment button is NOT visible we are not on the Activity Stream view, so click on the Show Activity Stream button
		if(!(new VoodooControl("button","css","div[data-voodoo-name='activitystream-omnibar'] button").queryVisible())){
			module.recordView.showActivityStream();
			VoodooUtils.pause(1000);
		}
		new VoodooControl("span", "css", ".activitystream-list.results li:nth-of-type(" + entryRow + ") div .tagged").assertElementContains(content, shouldContain);
	}

	/**
	 * Asserts the reply does or does not contain the string content
	 * 
	 * There must already be a reply in the Activity Stream.
	 * This assert leaves you on the Activity Stream view of this Record.
	 * 
	 * @param content content to be asserted
	 * @param entryRow 1-based index of the comment where a reply exists
	 * @param replyRow 1-based index of the reply
	 * @param shouldContain true for positive assert, false for negative "assertnot"
	 * @throws Exception
	 */
	public void assertReplyContains(String content, int entryRow, int replyRow, boolean shouldContain) throws Exception {
		navToRecord();
		// If the Comment button is NOT visible we are not on the Activity Stream view, so click on the Show Activity Stream button
		if(!(new VoodooControl("button","css","div[data-voodoo-name='activitystream-omnibar'] button").queryVisible())){
			module.recordView.showActivityStream();
			VoodooUtils.pause(1000);
		}
		new VoodooControl("span", "css", ".activitystream-list.results li:nth-of-type(" + entryRow + ") .comments li:nth-of-type(" + replyRow + ") .tagged").assertElementContains(content, shouldContain);
	}
} // Record