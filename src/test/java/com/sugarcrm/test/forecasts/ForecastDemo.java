package com.sugarcrm.test.forecasts;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.records.OpportunityRecord;
import com.sugarcrm.sugar.records.RevLineItemRecord;
import com.sugarcrm.sugar.records.UserRecord;

public class ForecastDemo extends SugarTest {
	UserRecord jim, sally, max, sarah;
	AccountRecord acc1, acc2, acc3, acc4, acc5;
	OpportunityRecord opp1, opp2, opp3, opp4, opp5;
	RevLineItemRecord rev1, rev2, rev3, rev4, rev5;

	public void setup() throws Exception {
		sugar.login();
		
		FieldSet forecastSetup = new FieldSet();
		sugar.forecasts.forecastSetup(forecastSetup);
		
		FieldSet jimData = new FieldSet();
		jimData.put("firstName", "Jim");
		jimData.put("lastName", "Brennan");
		jimData.put("emailAddress", "jim.brennan@example.com");
		jimData.put("userName", "jim");
		jimData.put("newPassword", "jim");
		jimData.put("confirmPassword", "jim");

		FieldSet sarahData = new FieldSet();
		sarahData.put("firstName", "Sarah");
		sarahData.put("lastName", "Smith");
		sarahData.put("emailAddress", "sarah.smith@example.com");
		sarahData.put("userName", "sarah");
		sarahData.put("newPassword", "sarah");
		sarahData.put("confirmPassword", "sarah");
		sarahData.put("reportsTo", "jim");

		FieldSet maxData = new FieldSet();
		maxData.put("firstName", "Max");
		maxData.put("lastName", "Jensen");
		maxData.put("emailAddress", "max.jensen@example.com");
		maxData.put("userName", "max");
		maxData.put("newPassword", "max");
		maxData.put("confirmPassword", "max");
		maxData.put("reportsTo", "sarah");

		FieldSet sallyData = new FieldSet();
		sallyData.put("firstName", "Sally");
		sallyData.put("lastName", "Bronsen");
		sallyData.put("emailAddress", "sally.bronsen@example.com");
		sallyData.put("userName", "sally");
		sallyData.put("newPassword", "sally");
		sallyData.put("confirmPassword", "sally");
		sallyData.put("reportsTo", "sarah");

		UserRecord jim = (UserRecord) sugar.users.create(jimData);
		UserRecord sarah = (UserRecord) sugar.users.create(sarahData);
		UserRecord max = (UserRecord) sugar.users.create(maxData);
		UserRecord sally = (UserRecord) sugar.users.create(sallyData);

		FieldSet account1 = new FieldSet();
		account1.put("name", "Account 1");
		account1.put("relAssignedUserName", "sarah");

		FieldSet account2 = new FieldSet();
		account2.put("name", "Account 2");
		account2.put("relAssignedUserName", "max");

		FieldSet account3 = new FieldSet();
		account3.put("name", "Account 3");
		account3.put("relAssignedUserName", "sally");

		FieldSet account4 = new FieldSet();
		account4.put("name", "Account 4");
		account4.put("relAssignedUserName", "max");

		FieldSet account5 = new FieldSet();
		account5.put("name", "Account 5");
		account5.put("relAssignedUserName", "sally");

		AccountRecord acc1 = (AccountRecord) sugar.accounts.create(account1);
		AccountRecord acc2 = (AccountRecord) sugar.accounts.create(account2);
		AccountRecord acc3 = (AccountRecord) sugar.accounts.create(account3);
		AccountRecord acc4 = (AccountRecord) sugar.accounts.create(account4);
		AccountRecord acc5 = (AccountRecord) sugar.accounts.create(account5);

		FieldSet opportunity1 = new FieldSet();
		opportunity1.put("name", "Opp 1");
		opportunity1.put("relAssignedUserName", "sally");
		opportunity1.put("relAccountName", "Account 3");

		FieldSet opportunity2 = new FieldSet();
		opportunity2.put("name", "Opp 2");
		opportunity2.put("relAssignedUserName", "max");
		opportunity2.put("relAccountName", "Account 2");

		FieldSet opportunity3 = new FieldSet();
		opportunity3.put("name", "Opp 3");
		opportunity3.put("relAssignedUserName", "max");
		opportunity3.put("relAccountName", "Account 4");

		FieldSet opportunity4 = new FieldSet();
		opportunity4.put("name", "Opp 4");
		opportunity4.put("relAssignedUserName", "sarah");
		opportunity4.put("relAccountName", "Account 1");

		FieldSet opportunity5 = new FieldSet();
		opportunity5.put("name", "Opp 5");
		opportunity5.put("relAssignedUserName", "sally");
		opportunity5.put("relAccountName", "Account 5");

		OpportunityRecord opp1 = (OpportunityRecord) sugar.opportunities.create(opportunity1);
		OpportunityRecord opp2 = (OpportunityRecord) sugar.opportunities.create(opportunity2);
		OpportunityRecord opp3 = (OpportunityRecord) sugar.opportunities.create(opportunity3);
		OpportunityRecord opp4 = (OpportunityRecord) sugar.opportunities.create(opportunity4);
		OpportunityRecord opp5 = (OpportunityRecord) sugar.opportunities.create(opportunity5);

		FieldSet revLineItem1 = new FieldSet();
		revLineItem1.put("name", "Product 1");
		revLineItem1.put("relOpportunityName", "Opp 1");
		revLineItem1.put("relSalesStage", "Negotiation/Review");
		revLineItem1.put("likelyCase", "1234.99");
		revLineItem1.put("expectedCloseDate", "04/13/2013");
		
		FieldSet revLineItem2 = new FieldSet();
		revLineItem2.put("name", "Product 2");
		revLineItem2.put("relOpportunityName", "Opp 2");
		revLineItem2.put("relSalesStage", "Negotiation/Review");
		revLineItem2.put("likelyCase", "1000.99");
		revLineItem2.put("expectedCloseDate", "05/13/2013");
		
		FieldSet revLineItem3 = new FieldSet();
		revLineItem3.put("name", "Product 3");
		revLineItem3.put("relOpportunityName", "Opp 3");
		revLineItem3.put("relSalesStage", "Negotiation/Review");
		revLineItem3.put("likelyCase", "999.00");
		revLineItem3.put("expectedCloseDate", "05/10/2013");
		
		FieldSet revLineItem4 = new FieldSet();
		revLineItem4.put("name", "Product 4");
		revLineItem4.put("relOpportunityName", "Opp 4");
		revLineItem4.put("relSalesStage", "Negotiation/Review");
		revLineItem4.put("likelyCase", "1050.50");
		revLineItem4.put("expectedCloseDate", "04/24/2013");
		
		FieldSet revLineItem5 = new FieldSet();
		revLineItem5.put("name", "Product 5");
		revLineItem5.put("relOpportunityName", "Opp 5");
		revLineItem5.put("relSalesStage", "Negotiation/Review");
		revLineItem5.put("likelyCase", "5000.25");
		revLineItem5.put("expectedCloseDate", "05/11/2013");

		RevLineItemRecord item1 = (RevLineItemRecord) sugar.revLineItems.create(revLineItem1);
		RevLineItemRecord item2 = (RevLineItemRecord) sugar.revLineItems.create(revLineItem2);
		RevLineItemRecord item3 = (RevLineItemRecord) sugar.revLineItems.create(revLineItem3);
		RevLineItemRecord item4 = (RevLineItemRecord) sugar.revLineItems.create(revLineItem4);
		RevLineItemRecord item5 = (RevLineItemRecord) sugar.revLineItems.create(revLineItem5);
	}

	@Ignore("Forecasts setup not implemented.")
	@Test
	public void execute() throws Exception {
		/*
		 * Jim is in upper management and needs to set his team's quota for the
		 * current time period. He accesses his Forecasts module and assigns the
		 * quotas, then commits them. Sarah is a manager reporting to Jim. When
		 * she accesses her Forecasts module she sees the updated quota assigned
		 * to her team by Jim, and divides it up between her sales reps, then
		 * commits the values. Max and Sally are sales reps who would like to
		 * create opportunities and assign different products for different time
		 * periods to the opportunities. Sally uses the Forecasts module with
		 * her product line items and commits the values up to Sarah. Sarah and
		 * Jim can then view Sally's forecasts and track how she is meeting her
		 * quota. Max wants to create a quote for a client, and uses the Product
		 * Line Items module to create one quickly.
		 */
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		jim.login();
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}