package com.sugarcrm.sugar.modules;

import java.util.ArrayList;
import java.util.HashMap;

import com.sugarcrm.candybean.datasource.DS;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.SugarField;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.WsRest;
import com.sugarcrm.sugar.WsRestV10;
import com.sugarcrm.sugar.records.Record;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Base class from which all modules with records extend. Methods and data
 * which are common to all modules with records are stored here.
 * 
 * Note: this module should not be extended directly into a SugarCRM module;
 * for that, use one of its subclasses (e.g. StandardModule, BWCModule). 
 * 
 * @author David Safar <dsafar@sugarcrm.com>
 */
public abstract class RecordsModule extends Module {	
	/**
	 * A hash representing this module's fields. The keys should contain the
	 * VoodooGrimoire names of the fields (e.g. billingAddressStreet).
	 */
	public HashMap<String, SugarField> fields = new HashMap<String, SugarField>();
	/**
	 * A hash containing modules to which this module has a *-to-many
	 * relationship. Theoretically these should correlate to subpanels in the
	 * UI.
	 */
	public HashMap<String, RecordsModule> relatedModulesMany = new HashMap<String, RecordsModule>();
	/**
	 * A hash containing modules to which this module has a *-to-one
	 * relationship. Theoretically these should correlate to relationship fields
	 * in the UI.
	 */
	public HashMap<String, String> relatedModulesOne = new HashMap<String, String>();
	/** A representation of the standard subpanel for this module. */
	public Subpanel subpanel;
	/** A representation of the Mass Update panel for this module. */
	public MassUpdate massUpdate;
	/**
	 * This method verifies a Text present in the page through WebDriver
	 * 
	 * @throws Exception
	 */
	public boolean containsText(String text) throws Exception{
		return VoodooUtils.iface.wd.getPageSource().contains(text);		
	}
	/**
	 * Default data for this module. The keys should contain the VoodooGrimoire
	 * names of the fields (e.g. billingAddressStreet), and the values should
	 * contain String representations of reasonable default values for those
	 * fields.
	 */
	
	public FieldSet defaultData = new FieldSet();
	public String recordClassName;
	
	public Api api = new Api();

	public RecordsModule() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
		
	/**
	 * Returns the requested SugarField
	 * 
	 * @param fieldName
	 *            the VoodooGrimoire name for the desired field.
	 * @see SugarField
	 * @return the requested SugarField
	 */
	public SugarField getField(String fieldName) throws Exception {
		return fields.get(fieldName);
	}

	/**
	 * Loads this module's fields from its CSV file. Field CSV files should
	 * follow this naming convention - AccountsModuleFields.csv,
	 * ContactsModuleFields.csv etc...
	 * 
	 * @throws Exception
	 */
	public void loadFields() throws Exception {
		DataSource fieldDefinitions = null;
		String fileName = RecordsModule.this.getClass().getSimpleName()
				+ "Fields";
		System.out.println(fileName);
		DS dsWrapper = new DS(fileName);
		String propNameCsvBaseDir = "datasource.csv.baseDir";
		String propValueCsvBaseDir = "src/main/resources/data";
		dsWrapper
				.init(DS.DataType.CSV, propNameCsvBaseDir, propValueCsvBaseDir);

		fieldDefinitions = dsWrapper.getDataSource(fileName);

		// Check value, if equal to "null" set SugarField value to null
		for(FieldSet field : fieldDefinitions) {
			fields.put(field.get("sugarField"), new SugarField(field));
			if(field.get("defaultValue") != null
					&& !(field.get("defaultValue").isEmpty()))
				defaultData.put(field.get("sugarField"),
						field.get("defaultValue"));
		}		
	}

	/**
	 * Creates a single record via the UI using default data.
	 * 
	 * @return a Record object representing the record created
	 * @throws Exception
	 */
	public Record create() throws Exception {
		VoodooUtils.voodoo.log.fine("Loading default data...");
		return create(defaultData);
	}

	/**
	 * Creates multiple Record objects via the UI from the data in a DataSource.
	 * 
	 * @param recordData
	 * @return an ArrayList of Records representing the accounts created.
	 * @throws Exception
	 */
	public ArrayList<Record> create(DataSource recordData) throws Exception {
		VoodooUtils.voodoo.log.info("Creating " + recordData.size()
				+ "records...");
		ArrayList<Record> results = new ArrayList<Record>();
		for(FieldSet record : recordData) { // iterate over records
			results.add(create(record));
		}
		VoodooUtils.voodoo.log.info(recordData.size() + "records created.");
		return results;
	}

	public abstract Record create(FieldSet testData) throws Exception;

	/**
	 * Returns a single subpanel of this module type
	 * 
	 * @return a model of the default subpanel for this module
	 */
	public Subpanel getSubpanel() throws Exception {
		return subpanel;
	}

	/**
	 * Returns a single mass update panel of this module type
	 * 
	 * @return a model of the default mass update panel of this module
	 */
	public MassUpdate getMassUpdate() throws Exception {
		return massUpdate;
	}

	/**
	 * Returns an independent copy of the defaultData FieldSet.
	 * 
	 * @return - a deepClone of the default data
	 * @throws Exception
	 */
	public FieldSet getDefaultData() {
		return defaultData.deepClone();
	}
		
	/**
	 * Navigates to the list view of the current module.
	 * 
	 * @throws Exception
	 */
	public void navToListView() throws Exception {
		VoodooUtils.voodoo.log.info("Navigating to " + moduleNamePlural
				+ " module ListView...");
		if (new VoodooControl("a", "id", "moreDrop").queryVisible()){
			new VoodooControl("a", "id", "moreDrop").click();
		}
		if (moduleNamePlural == "Accounts"){
			moduleNamePlural = "Clients";
		}
		new VoodooControl("a", "xpath", "//div[@class = 'btn-group']/a[contains(text(),'" + moduleNamePlural + "')]").click();
		VoodooUtils.pause(3000);
		VoodooUtils.closeAlert();
	}
		
	public void navToDetailView() throws Exception {
		VoodooUtils.voodoo.log.info("Navigating to " + moduleNamePlural
				+ " module DetailView...");
		sugar.navbar.navToModule(moduleNamePlural);
		VoodooUtils.closeAlert();
	}
	


	/**
	 * Groups together API methods for convenient access.
	 * 
	 * @author Mazen Louis <mlouis@sugarcrm.com>
	 */
	public class Api {
		HashMap<String, String> prepForREST(FieldSet toPrep) {
			HashMap<String, String> translatedData = new HashMap<String, String>();
			SugarField currentField;

			// For each field in the fieldset...
			for(String key : toPrep.keySet()) {
				// ...grab the field definition...
				currentField = fields.get(key);
				// ..and if it has a REST name, store the data under that name
				// instead.
				if(currentField.get("rest_name") != null) {
					translatedData.put(currentField.get("rest_name"),
							toPrep.get(key));
				}
			}

			// If there is no assigned user, assign admin.
			//if(translatedData.get("assigned_user_id") == null) {
			//	translatedData.put("assigned_user_id", "1");
			//}

			return translatedData;
		}

		/**
		 * Creates a new record with the default data using REST.
		 * 
		 * @return
		 * @throws Exception
		 */
		public Record create() throws Exception {
			return create(getDefaultData());
		}
		
		/**
		 * IBM: Creates a new record with the default data using REST.
		 * 
		 * @return FieldSet of this data
		 * @throws Exception
		 */
		public FieldSet createFS() throws Exception {
			return createFS(getDefaultData());
		}
		
		
		/**
		 * IBM: Add stamp to the end of record name.
		 * 
		 * @param recordData
		 * @return recordData wich was modified
		 * @throws Exception
		 */			
		private FieldSet addStamp(FieldSet recordData) throws Exception{
			String timeStamp = VoodooUtils.getCurrentTimeStamp("MMddHHmmss");
			if (recordData.get("name") != null) recordData.put("name", recordData.get("name") + timeStamp);				
			if (recordData.get("lastName") != null) recordData.put("lastName", recordData.get("lastName") + timeStamp);
			if (recordData.get("subject") != null) recordData.put("subject", recordData.get("subject") + timeStamp);
						
			return recordData;
		}

		// TODO: DRY the FieldSet and DataSource versions of this method.
		/**
		 * Creates a new record with the specified data using REST.
		 * 
		 * @param recordData
		 * @return
		 * @throws Exception
		 */					
		public Record create(FieldSet recordData) throws Exception {
			// Prep the record data.
			FieldSet finalRecordData = getDefaultData();
			this.addStamp(recordData);			
			finalRecordData.putAll(recordData);
			ArrayList<HashMap<String, String>> listOfMaps = new ArrayList<HashMap<String, String>>();
			listOfMaps.add(prepForREST(finalRecordData));
			// TODO: This is a terrible hack. Fix email address relationship
			// handling.
			recordData.remove("emailAddress");
			VoodooUtils.voodoo.log.info("Maps ready.  Creating "
					+ listOfMaps.size() + " records.");
			// Create the record.
			WsRestV10 rest = new WsRestV10();			
			int totalCount = rest.getTotalCount(moduleNamePlural);			
			VoodooUtils.voodoo.log.info("Before create: totalCount = "
					+ totalCount);
			rest.create(moduleNamePlural, listOfMaps);
			
			totalCount = rest.getTotalCount(moduleNamePlural);
			VoodooUtils.voodoo.log.info("After create: totalCount = "
					+ totalCount);		
			// Return the record.
			return (Record) Class.forName(RecordsModule.this.recordClassName)
					.getConstructor(FieldSet.class).newInstance(recordData);
		}
				
		/**
		* Creates a new record with the specified data using REST.
		* 
		* @param recordData
		* @return FieldSet of record data
		* @throws Exception
		*/		
		public FieldSet createFS(FieldSet recordData) throws Exception {
			// Prep the record data.
			FieldSet finalRecordData = getDefaultData();
			this.addStamp(recordData);			
			finalRecordData.putAll(recordData);
			ArrayList<HashMap<String, String>> listOfMaps = new ArrayList<HashMap<String, String>>();
			listOfMaps.add(prepForREST(finalRecordData));
			// TODO: This is a terrible hack. Fix email address relationship
			// handling.
			recordData.remove("emailAddress");
			VoodooUtils.voodoo.log.info("Maps ready.  Creating "
					+ listOfMaps.size() + " records.");
			// Create the record.
			WsRestV10 rest = new WsRestV10();			
			int totalCount = rest.getTotalCount(moduleNamePlural);			
			VoodooUtils.voodoo.log.info("Before create: totalCount = "
					+ totalCount);
			rest.create(moduleNamePlural, listOfMaps);
			
			totalCount = rest.getTotalCount(moduleNamePlural);
			VoodooUtils.voodoo.log.info("After create: totalCount = "
					+ totalCount);		
			// Return the record.
			return recordData;
		}
		

		/**
		 * Creates one or more new records with the specified data using REST.
		 * 
		 * @param recordData
		 * @return
		 * @throws Exception
		 */
		public ArrayList<Record> create(DataSource recordData) throws Exception {
			ArrayList<Record> createdRecords = new ArrayList<Record>();
			ArrayList<HashMap<String, String>> listOfMaps = new ArrayList<HashMap<String, String>>();

			for(FieldSet record : recordData) {
				// Add the record to the payload for the REST call
				listOfMaps.add(prepForREST(record));
				// TODO: This is a terrible hack. Fix email address relationship
				// handling.
				record.remove("emailAddress");

				createdRecords.add((Record)(Class.forName(
						RecordsModule.this.recordClassName).getConstructor(
						FieldSet.class).newInstance(record)));
			}

			// Create the records.
			WsRest rest = new WsRest();
			String totalCount = rest.getTotalCount(moduleNamePlural);
			VoodooUtils.voodoo.log.info("Before create: totalCount = "
					+ totalCount);
			rest.create(moduleNamePlural, listOfMaps);
			totalCount = rest.getTotalCount(moduleNamePlural);
			VoodooUtils.voodoo.log.info("After create: totalCount = "
					+ totalCount);

			return createdRecords;
		}

		/**
		 * Deletes all records in the current module using REST.
		 */
		public void deleteAll() throws Exception {
			WsRestV10 rest = new WsRestV10();
			int totalCount = rest.getTotalCount(moduleNamePlural);
			VoodooUtils.voodoo.log.info("Before delete: totalCount = "
					+ totalCount);
			rest.deleteAll(moduleNamePlural);
			totalCount = rest.getTotalCount(moduleNamePlural);
			VoodooUtils.voodoo.log.info("After delete: totalCount = "
					+ totalCount);
		}
		
		/**
		 * Deletes the record in the current module using REST.
		 */
		public void deleteRecord(FieldSet record) throws Exception {
			String recordIdentifier = "name";	
			String recordIdentifierValue = record.get("name");			
			if (record.get("lastName") != null) {
				recordIdentifier = "last_name";
				recordIdentifierValue = record.get("lastName");
			}
			if (record.get("subject") != null) {
				recordIdentifier ="name";
				recordIdentifierValue = record.get("subject");
			}
			
			WsRestV10 rest = new WsRestV10();			
			rest.deleteRecodById(moduleNamePlural, recordIdentifier, recordIdentifierValue);
			VoodooUtils.log.info("Record '" + recordIdentifierValue + "' of " + moduleNamePlural + " deleted!");
		}
	}// API
}