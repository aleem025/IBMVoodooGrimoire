package com.sugarcrm.sugar;

import com.sugarcrm.candybean.configuration.Configuration;
import com.sugarcrm.candybean.webservices.WS;
import com.sugarcrm.sugar.VoodooUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.io.File;

/**
 * WsRestV10 encapsulates REST calls for Sugar CRUD functions. The class targets
 * sugar7 V10 REST api.
 * 
 * @author Soon Han
 */
public class WsRestV10 {

	private String url;
	private String username;
	private String password;
	private String token;
    private Logger logger;
	private Map<String, String> payload = new LinkedHashMap<String, String>();

	private ArrayList<HashMap<String, String>> listOfPostHeaders = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> postHeaders = new HashMap<String, String>();

	public WsRestV10() {
		this.logger = getLogger();

		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		loadBasicParams();
		this.token = getToken(this.username, this.password);
	}

	private Logger getLogger() {
		try {
            this.logger = Logger.getLogger(WsRestV10.class.getName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return logger;
	}

	/**
	 * Load basic parameters such as the Sugar url, username, password, sugar
	 * REST entry point, etc.
	 * 
	 * @param None
	 * @return void
	 */
	private void loadBasicParams() {
		final String currentWorkingPath = System.getProperty("user.dir");
		final String relativeResourcesPath = File.separator + "src"
				+ File.separator + "test" + File.separator + "resources"
				+ File.separator;

		String grimoirePropsPath = currentWorkingPath + relativeResourcesPath
				+ "grimoire.properties";
		Configuration grimoireConfig = new Configuration();
		try {
			grimoireConfig.load(new File(grimoirePropsPath));
			String baseUrl = grimoireConfig.getValue("env.base_url",
					"http://localhost/sugar");
			String restEntryPoint = grimoireConfig.getValue("restV10",
					"rest/v10");
			this.url = baseUrl + "/" + restEntryPoint;
			this.username = VoodooUtils.username;
			this.password = VoodooUtils.password;
			this.logger.info("REST entry point = " + this.url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create module records
	 * 
	 * @param module
	 *            name
	 * @param listOfMaps
	 *            as Map<String, Object>
	 * @return result as Map<String, Object>
	 */
	public ArrayList<HashMap<String, Object>> create(String module,
			ArrayList<HashMap<String, String>> listOfMaps) {

		ArrayList<HashMap<String, Object>> listOfMapsResult = new ArrayList<HashMap<String, Object>>();

		for (HashMap<String, String> hashMap : listOfMaps) {
			Map<String, String> pl = new LinkedHashMap<String, String>();
			pl.putAll(hashMap);
			Map<String, Object> result = postRequest(WS.OP.POST, module, pl);
			listOfMapsResult.add((HashMap<String, Object>) result);
		}

		return listOfMapsResult;
	}

	/**
	 * Post the request
	 * 
	 * @param None
	 * @return results in Map<String, Object>
	 */
	private Map<String, Object> postRequest(WS.OP op, String endPoint,
			Map<String, String> pl) {

		Map<String, Object> mapParse = null;

		String restEntry = this.url + "/" + endPoint;

		postHeaders.clear();
		listOfPostHeaders.clear();
		postHeaders.put("Accept", "application/json");
		postHeaders.put("OAuth-Token", this.token);
		listOfPostHeaders.add(postHeaders);

		try {
			mapParse = WS.request(op, restEntry, pl, null, listOfPostHeaders);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapParse;
	}

	/**
	 * Get the token for the rest session
	 * 
	 * @param username
	 *            as String
	 * @param password
	 *            as String
	 * @return token as String
	 */
	private String getToken(String username, String password) {

		Map<String, String> pl = fillTokePayload();
		Map<String, Object> mapParse = postRequest(WS.OP.POST, "oauth2/token",
				pl);

		this.token = (String) mapParse.get("access_token");
		return this.token;
	}

	/**
	 * Fill in the parameter values required for obtaining the token.
	 * 
	 * @param None
	 * @return payload as Map<String, String>
	 */
	private Map<String, String> fillTokePayload() {

		// Not sure if order matters, use an ordered map
		Map<String, String> pl = new LinkedHashMap<String, String>();
		pl.put("grant_type", "password");
		pl.put("username", this.username);
		pl.put("password", this.password); // no md5 encryption
		pl.put("client_id", "sugar");
		pl.put("client_secret", "");
		pl.put("platform", "base");

		return pl;
	}

	/**
	 * Add the token to the payload
	 * 
	 * @param None
	 * @return payload as Map<String, String>
	 */
	private Map<String, String> addTokenToPayload() {

		payload = new LinkedHashMap<String, String>();
		// Strange, requires "OAuth-Token" in both the payload and POST header.
		// Otherwise, got HTTP 401 error
		payload.put("OAuth-Token", this.token);

		return payload;
	}

	/**
	 * Get records of a module
	 * 
	 * @param module
	 *            as String
	 * @return POST request results as an ArrayList<Map<String, Object>>
	 */
	private ArrayList<Map<String, Object>> getRecords(String module) {

		Map<String, String> pl = addTokenToPayload();
		Map<String, Object> mapParse = postRequest(WS.OP.GET, module, pl);

		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> arrList = (ArrayList<Map<String, Object>>) mapParse
				.get("records");

		return arrList;
	}

	/**
	 * Get record ids of a module
	 * 
	 * @param module
	 *            as String
	 * @return ids in an ArrayList<String>
	 */
	private ArrayList<String> getRecordsIds(String module) {

		ArrayList<Map<String, Object>> records = getRecords(module);

		String attrib = "id";
		ArrayList<String> myArrList = new ArrayList<String>();
		for (Map<String, Object> entry : records) {
			for (String key : entry.keySet()) {
				if (key.equals(attrib)) {
					myArrList.add((String) entry.get(key));
				}
			}
		}

		return myArrList;
	}

	/**
	 * Delete a record specified by record id and module
	 * 
	 * @param module
	 *            as String and id as String
	 * @return id of deleted record. id is a String
	 */
	private String deleteRecord(String module, String id) {

		Map<String, String> pl = addTokenToPayload();
		Map<String, Object> mapParse = postRequest(WS.OP.DELETE, module + "/"
				+ id, pl);

		return (String) mapParse.get("id");
	}

	/**
	 * Delete all records in a specified module
	 * 
	 * @param module
	 *            as String and id as String
	 * @return ids of deleted records, in ArrayList<String>
	 */
	public ArrayList<String> deleteAll(String module) {

		ArrayList<String> ids = getRecordsIds("Accounts");

		ArrayList<String> idsDeleted = new ArrayList<String>();
		for (String id : ids) {
			String idDeleted = deleteRecord(module, id);
			idsDeleted.add(idDeleted);
		}

		return idsDeleted;
	}
	
	/**
	 * Delete the record in a specified module
	 * 
	 * @param module
	 *            as String and id as String
	 * @param recordIdentifier
	 * @param recordIdentifierValue
	 * @return id of deleted record, in ArrayList<String>
	 */
	public ArrayList<String> deleteRecodById(String module, String recordIdentifier, String recordIdentifierValue) {
		VoodooUtils.voodoo.log.info("Start to delete record " + recordIdentifierValue + " of module " + module);
		
		ArrayList<String> ids = getRecordId(module, recordIdentifier, recordIdentifierValue);

		ArrayList<String> idsDeleted = new ArrayList<String>();
		for (String id : ids) {
			String idDeleted = deleteRecord(module, id);
			idsDeleted.add(idDeleted);
		}

		return idsDeleted;
	}

	/**
	 * Get total_count of records in module
	 * 
	 * @param method
	 *            name
	 * @return total_count in String
	 * @throws Exception
	 */
	public int getTotalCount(String module) throws Exception {

		ArrayList<String> ids = getRecordsIds(module);
		return ids.size();
	}
	
	/**
	 * Get record id of a module
	 * 
	 * @param module as String
	 * @param recordIdentifier
	 * @param recordIdentifierValue
	 * @return ids in an ArrayList<String>
	 */	
	private ArrayList<String> getRecordId(String module, String recordIdentifier, String recordIdentifierValue) {

		ArrayList<Map<String, Object>> records = getRecords(module);

		String attrib = "id";
		ArrayList<String> myArrList = new ArrayList<String>();
		for (Map<String, Object> entry : records) {
			for (String key : entry.keySet()) {
				if (key.equals(attrib) && entry.get(recordIdentifier).equals(recordIdentifierValue)) {
					myArrList.add((String) entry.get(key));
				}
			}
		}

		return myArrList;
	}
	
	/**
	 * Print list of maps. Used for debugging only
	 * 
	 * @param arrList
	 *            as ArrayList<Map<String, Object>>
	 * @param listOfMaps
	 *            as ArrayList<HashMap<String, String>>
	 * @return void
	 */
	@SuppressWarnings("unused")
	private void printListOfMaps(ArrayList<Map<String, Object>> arrList) {
		for (Map<String, Object> entry : arrList) {
			for (Map.Entry<String, Object> item : entry.entrySet()) {
				String key = item.getKey();
				Object value = item.getValue();
				System.out.println("printListOfMaps(): key = " + key
						+ "  value = " + value);
			}
		}
	}

	/**
	 * This main() illustrates usage of the class
	 * 
	 */	
	public static void main(String[] args) {

		try {
			WsRestV10 rest = new WsRestV10();

			int total = rest.getTotalCount("Accounts");
			System.out.println("Before adding accounts: number of accounts = "
					+ total);

			HashMap<String, String> accountInfo = new HashMap<String, String>();
			accountInfo.put("name", "myName");
			accountInfo.put("account_type", "Special customer");
			accountInfo.put("description", "My Special Example Account");

			ArrayList<HashMap<String, String>> listOfMaps = new ArrayList<HashMap<String, String>>();

			for (int i = 0; i < 2; i++) {
				listOfMaps.add(accountInfo);
			}

			ArrayList<HashMap<String, Object>> listOfMapsResult = rest.create(
					"Accounts", listOfMaps);

			total = rest.getTotalCount("Accounts");
			System.out.println("After adding accounts: number of accounts = "
					+ total);

			rest.deleteAll("Accounts");
			total = rest.getTotalCount("Accounts");
			System.out
					.println("After delete all accounts: number of accounts = "
							+ total);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
