package com.sugarcrm.test.grimoire;

import com.sugarcrm.test.SugarTest;

import org.junit.Test;

import java.util.ArrayList;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.sugar.records.StandardRecord;

public class Api extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void createRecords() throws Exception {
		VoodooUtils.voodoo.log.info("Running createRecords...");

		// Create default record.
		// Create a new Account record using default data via the API.
		AccountRecord myAccount = (AccountRecord)sugar.accounts.api.create();
		// Using the UI, verify the account was created with the correct data.
		myAccount.verify();

		// Create custom record.
		FieldSet record = new FieldSet();
		record.put("name", "Record1");

		// Create a new Account record using custom data via the API.
		myAccount = (AccountRecord)sugar.accounts.api.create(record);
		// Using the UI, verify the account was created with the correct data.
		myAccount.verify();

		// Create multiple records.
		DataSource recordList = new DataSource();
		record = new FieldSet();
		record.put("name", "Record2");
		recordList.add(record);
		record = new FieldSet();
		record.put("name", "Record3");
		recordList.add(record);
		record = new FieldSet();
		record.put("name", "Record4");
		recordList.add(record);

		// Create a new Account record using default data via the API.
		ArrayList<Record> myAccounts = sugar.accounts.api.create(recordList);
		// Using the UI, verify the account was created with the correct data.
		for(Record createdRecord : myAccounts)
		{
			createdRecord.verify();
		}
	}

	public void cleanup() throws Exception {
		// Delete all Account records via the API.
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}