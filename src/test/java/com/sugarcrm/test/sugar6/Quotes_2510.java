package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;

public class Quotes_2510 extends SugarTest {
	DataSource accounts = new DataSource();
	DataSource quotes = new DataSource();

	public void setup() throws Exception {
		sugar.login();

// TODO: Use this instead of creating new accounts each time?
//		// TODO: Replace with CSV syntax.
//		// Create four accounts for sorting tests.
		FieldSet row = new FieldSet();
//		row.put("name", "1_Account");
//		accounts.add(row);
//		row.put("name", "2_Account");
//		accounts.add(row);
//		row.put("name", "A_Account");
//		accounts.add(row);
//		row.put("name", "B_Account");
//		accounts.add(row);
//		
//		sugar.Accounts.api.create(accounts);
		
		// TODO: Replace with CSV syntax.
		// Create several test quotes.
		// Fix this to actually create quotes correctly.  :(
		row.clear(); // re-using this var
		row.put("number", "1");
		row.put("name", "1_quote");
		row.put("quote_stage", "Closed Accepted");
		row.put("account_name", "1_account");
		row.put("date_quote_expected_closed", "06/30/2012");
		row.put("field_asc", "1");
		row.put("field_desc", "4");
		quotes.add(row);
		row.clear();
		row.put("number", "2");
		row.put("name", "2_quote");
		row.put("quote_stage", "Delivered");
		row.put("account_name", "2_account");
		row.put("date_quote_expected_closed", "07/01/2012");
		row.put("field_asc", "2");
		row.put("field_desc", "3");
		quotes.add(row);
		row.clear();
		row.put("number", "3");
		row.put("name", "A_quote");
		row.put("quote_stage", "Draft");
		row.put("account_name", "A_account");
		row.put("date_quote_expected_closed", "07/02/2012");
		row.put("field_asc", "3");
		row.put("field_desc", "2");
		quotes.add(row);
		row.clear();
		row.put("number", "4");
		row.put("name", "B_quote");
		row.put("quote_stage", "Negotiation");
		row.put("account_name", "A_account");
		row.put("date_quote_expected_closed", "08/01/2012");
		row.put("field_asc", "4");
		row.put("field_desc", "1");
		quotes.add(row);
		row.clear();
		//sugar.quotes.api.create(quotes);
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		/*
		// name asc
		//sugar.quotes.sortListViewBy("name", true);
		for(FieldSet row : quotes)
		{
			//sugar.quotes.verifyListViewField(row.get("field_asc"), "headerSubject", row.get("name"));
		}

		// name desc
		//sugar.quotes.sortListViewBy("name", false);
		for(FieldSet row : quotes)
		{
			//sugar.quotes.verifyListViewField(row.get("field_desc"), "headerSubject", row.get("name"));
		}

		// account asc
		sugar.quotes.sortListViewBy("account", true);
		for(FieldSet row : quotes)
		{
			sugar.quotes.verifyListViewField(row.get("field_asc"), "headerAccountName", row.get("account_name"));
		}

		// account desc
		sugar.quotes.sortListViewBy("account", false);
		for(FieldSet row : quotes)
		{
			sugar.quotes.verifyListViewField(row.get("field_desc"), "headerAccountName", row.get("account_name"));
		}

		// stage asc
		sugar.quotes.sortListViewBy("stage", true);
		for(FieldSet row : quotes)
		{
			sugar.quotes.verifyListViewField(row.get("field_asc"), "headerStage", row.get("quote_stage"));
		}

		// stage desc
		sugar.quotes.sortListViewBy("stage", false);
		for(FieldSet row : quotes)
		{
			sugar.quotes.verifyListViewField(row.get("field_desc"), "headerStage", row.get("quote_stage"));
		}

		// close by asc
		sugar.quotes.sortListViewBy("close_by_date", true);
		for(FieldSet row : quotes)
		{
			sugar.quotes.verifyListViewField(row.get("field_asc"), "headerValidUntil", row.get("date_quote_expected_closed"));
		}

		// close by desc
		sugar.quotes.sortListViewBy("close_by_date", false);
		for(FieldSet row : quotes)
		{
			sugar.quotes.verifyListViewField(row.get("field_desc"), "headerValidUntil", row.get("date_quote_expected_closed"));
		}
		*/
	}

	public void cleanup() throws Exception {
		//sugar.quotes.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
