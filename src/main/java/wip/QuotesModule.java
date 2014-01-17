package wip;

import com.sugarcrm.sugar.modules.BWCModule;

/**
 * Quote module object which contains tasks associated with the Quote module
 * like create/deleteAll
 * @author 
 *
 */
public class QuotesModule extends BWCModule {
	protected static QuotesModule module;

	public static QuotesModule getInstance() throws Exception {
		if (module == null) module = new QuotesModule();
		return module;
	}

	private QuotesModule() throws Exception {
		moduleNameSingular = "Quote";
		recordClassName = QuoteRecord.class.getName();
		//api = new Api();

		// TODO: Replace with loading data from disk.
		editView.addControl("quoteSubject", "input", "id", "name");
		editView.addControl("quoteStage", "select", "id", "quote_stage");
		editView.addControl("validUntil", "input", "id", "date_quote_expected_closed");
		editView.addControl("billingAccountName", "input", "id", "billing_account_name");
		editView.addControl("billingAccountNameButton", "button", "id", "btn_billing_account_name");
		editView.addControl("saveFooter", "input","id","SAVE_FOOTER");
		
		listView.addControl("sugarActionButton", "span", "css", "ul#selectLinkTop li.sugar_action_button span");
		listView.addControl("buttonSelectAllTop", "a", "id", "button_select_all_top");
		listView.addControl("deleteListviewTop", "a", "id", "delete_listview_top");
		listView.addControl("headerNumber", "a", "css", "table.list.view tr th:nth-child(1)");
		listView.addControl("headerSubject", "a", "css", "table.list.view tr th:nth-child(2)");
		listView.addControl("headerAccountName", "a", "css", "table.list.view tr th:nth-child(3)");
		listView.addControl("headerStage", "a", "css", "table.list.view tr th:nth-child(4)");
		listView.addControl("headerTotalAmount", "a", "css", "table.list.view tr th:nth-child(5)");
		listView.addControl("headerValidUntil", "a", "css", "table.list.view tr th:nth-child(6)");
		listView.addControl("headerUser", "a", "css", "table.list.view tr th:nth-child(7)");
		listView.addControl("headerDateCreated", "a", "css", "table.list.view tr th:nth-child(8)");

/* TODO: Update this to the new syntax for column headers. 
		listViewColumns.put("defaultColumnPrimary", "4");
		listViewColumns.put("defaultColumnSecondary", "5");
		listViewColumns.put("headerNumber", "4");
		listViewColumns.put("headerSubject", "5");
		listViewColumns.put("headerAccountName", "6");
		listViewColumns.put("headerStage", "7");
		listViewColumns.put("headerTotalAmount", "8");
		listViewColumns.put("headerValidUntil", "9");
		listViewColumns.put("headerheaderUser", "10");
		listViewColumns.put("headerDateCreated", "11");
*/
		relatedModulesOne.put("billingAccountName", "Accounts");

		//Load default data
		// TODO: Replace with loading data from disk.

		defaultData.put("quoteSubject", "42 Aperture Science Handheld Portal Devices for Black Mesa");
		defaultData.put("quoteStage", "Draft");
		defaultData.put("validUntil", "2020-10-31");
		defaultData.put("relBillingAccountName", "Black Mesa");

	}
} // end Quotes