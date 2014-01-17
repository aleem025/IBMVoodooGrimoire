package com.sugarcrm.sugar;

import com.sugarcrm.candybean.configuration.Configuration;
import com.sugarcrm.candybean.webservices.WS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.io.File;
import java.math.BigInteger;

import org.json.simple.JSONObject;

/**
 * WsRest encapsulates REST calls for Sugar CRUD functions.
 *
 * @author Soon Han
 */
public class WsRest {
	private String url;
	private String username;
	private String password;
	private String sessionId;
    private Logger logger;
	private Map<String, String> payload = new LinkedHashMap<String, String>();

	public WsRest() {
		this.logger = getLogger();
		
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		loadBasicParams();
		this.sessionId = getSessionId(this.username, this.password);
	}
	
	private Logger getLogger() {
	    try {
            this.logger = Logger.getLogger(WsRest.class.getName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    
	    return logger;
	}

	/**
     *  Load basic parameters such as the Sugar url, username, password, sugar REST entry point, etc.
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
			String baseUrl = grimoireConfig.getValue("env.base_url", "http://localhost/sugar");
			String restEntryPoint = grimoireConfig.getValue("rest_entry_point", "service/v4_1/rest.php");
			this.url = baseUrl + "/" + restEntryPoint;
			this.username = grimoireConfig.getValue("sugar_user", "admin");
			this.password = grimoireConfig.getValue("sugar_pass", "asdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     *  Compute and return the MD5 value of the Sugar password 
     *
     * @param  password, an unencoded password 
     * @return MD5 encoded password 
     */
	private static String getMD5(String password) {
		// Eg. "f78spx" gives md5="0b54f7d8c5926903d31576da3518a167"
		String pwMD5 = "";
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(), 0, password.length());
			pwMD5 = new BigInteger(1, m.digest()).toString(16);
			while (pwMD5.length() < 32) {
				pwMD5 = "0" + pwMD5;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pwMD5;
	}

	private Map<String, String> getPayload() {

		return payload;
	}

	/**
     *  Create module records 
     *
     * @param module name 
     * @param listOfMaps as Map<String, Object>
     * @return result as Map<String, Object> 
     */
	public Map<String, Object> create(String module,
			ArrayList<HashMap<String, String>> listOfMaps) {

		initPayload("set_entries");
		addCreatePayload(module, listOfMaps);

		Map<String, Object> mapParse = postRequest();

		return mapParse;
	}

	/**
     *  Delete module records 
     *
     * @param module name 
     * @param recordIds as List<String>
     * @return deleted record in the form of Map<String, Object> 
     */
	public Map<String, Object> delete(String module, List<String> recordIds) {

		initPayload("set_entries");
		addDeletePayload(module, recordIds);

		Map<String, Object> mapParse = postRequest();

		return mapParse;
	}

	/**
     *  Delete all the module records 
     *
     * @param module name 
     * @return deleted record in the form of Map<String, Object> 
     */
	public Map<String, Object> deleteAll(String module) {

		ArrayList<Map<String, Object>> acctIds = getEntryListAttribValues(
				module, "id");
		
		long totalCnt = acctIds.size();
		
		List<String> arrList = new ArrayList<String>();
		for (int i = 0; i < totalCnt; i++) {
			String id = (String) acctIds.get(i).get("id");
			arrList.add(id);
		}

		Map<String, Object> acctsDeleted = delete(module, arrList);
		return acctsDeleted;
	}

	/**
     * Get entry_list attribute values 
     *
     * @param module name as String 
     * @param attrib as String 
     * @return results in ArrayList<Map<String, Object>> 
     */
	public ArrayList<Map<String, Object>> getEntryListAttribValues(
			String module, String attrib) {

		ArrayList<Map<String, Object>> entryList = (ArrayList<Map<String, Object>>) getEntryList(module);
		;

		ArrayList<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> entry : entryList) {
			for (Map.Entry<String, Object> item : entry.entrySet()) {
				String key = item.getKey();
				Object value = item.getValue();

				if (key.equals(attrib)) {
					Map<String, Object> keyVal = new HashMap<String, Object>();
					keyVal.put(key, value);
					listOfMaps.add(keyVal);
				}
			}
		}

		return listOfMaps;
	}

	/**
     * Get name_value_list attribute values 
     *
     * @param module name as String 
     * @param attrib as String 
     * @return results in ArrayList<Map<String, Object>> 
     */
	public ArrayList<Map<String, Object>> getNameValueListAttribValues(
			String module, String attrib) {

		ArrayList<Map<String, Object>> entryList = (ArrayList<Map<String, Object>>) getEntryList(module);
		;

		ArrayList<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> entry : entryList) {
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> name_value = (Map<String, Map<String, String>>) entry
					.get("name_value_list");
			String value = name_value.get(attrib).get("value");
			Map<String, Object> keyVal = new HashMap<String, Object>();
			keyVal.put(attrib, value);
			listOfMaps.add(keyVal);
		}

		printListOfMaps(module, listOfMaps);
		return listOfMaps;

	}

	/**
     * Read module info 
     *
     * @param module name 
     * @return module info in Map<String, Object> 
     */
	public Map<String, Object> readModuleInfo(String module) {

		initPayload("get_entry_list");
		addModuleInfoPayload(module);

		Map<String, Object> mapParse = postRequest();

		return mapParse;
	}
	
	/**
     * Get the entry_list 
     *
     * @param method name 
     * @return entry_list in ArrayList<Map<String, Object>>
     */
	public ArrayList<Map<String, Object>> getEntryList(String module) {

		Map<String, Object> mapParse = readModuleInfo(module);

		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> arrList = (ArrayList<Map<String, Object>>) mapParse
				.get("entry_list");

		return arrList;
	}

	/**
     * Get total_count of records in module 
     *
     * @param method name 
     * @return total_count in String 
     */
	public String getTotalCount(String module) throws Exception {

		Map<String, Object> mapParse = readModuleInfo(module);

		String resultCount = String.valueOf(mapParse.get("result_count"));

		return resultCount;
	}

	/**
     *  Set payload with method name. Also set input and output types as JSON 
     *
     * @param method name 
     * @return the payload which is a Map<String, String>
     */
	private Map<String, String> initPayload(String method) {

		Map<String, String> pl = getPayload();

		pl.put("method", method);
		pl.put("input_type", "JSON");
		pl.put("response_type", "JSON");

		return pl;
	}

	/**
     * Add create module records parameters to payload 
     *
     * @param method name 
     * @param listOfMaps as ArrayList<HashMap<String, String>>
     * @return the payload which is a Map<String, String>
     */
	private Map<String, String> addCreatePayload(String module,
			ArrayList<HashMap<String, String>> listOfMaps) {

		Map<String, String> pl = getPayload();

		Map<String, Object> restData = new LinkedHashMap<String, Object>();
		restData.put("session", sessionId);
		restData.put("module", module);

		restData.put("name_value_lists", listOfMaps);

		String restDataJs = JSONObject.toJSONString(restData);
		pl.put("rest_data", restDataJs);

		return pl;
	}

	/**
     * Add delete module records parameters to payload 
     *
     * @param method name 
     * @param recordIds as List<String> 
     * @return the payload which is a Map<String, String>
     */
	private Map<String, String> addDeletePayload(String module,
			List<String> recordIds) {

		Map<String, Object> restData = new LinkedHashMap<String, Object>();
		restData.put("session", sessionId);
		restData.put("module", module);

		ArrayList<HashMap<String, String>> deleteInfo = new ArrayList<HashMap<String, String>>();
		for (String id : recordIds) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("id", id);
			hashMap.put("deleted", "1");
			deleteInfo.add(hashMap);
		}
		restData.put("name_value_list", deleteInfo);

		String restDataJs = JSONObject.toJSONString(restData);

		Map<String, String> pl = getPayload();
		pl.put("rest_data", restDataJs);

		return pl;
	}

	/**
     * Post the request 
     *
     * @param None 
     * @return results in Map<String, Object> 
     */
	private Map<String, Object> postRequest() {

		Map<String, Object> mapParse = null;

		try {
			mapParse = WS.request(WS.OP.POST, url, getPayload(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapParse;
	}

	/**
     * Print module info for debugging only 
     *
     * @param method name 
     * @param listOfMaps as ArrayList<HashMap<String, String>>
     * @return void 
     */
	private void printListOfMaps(String module,
			ArrayList<Map<String, Object>> listOfMaps) {

		for (Map<String, Object> map : listOfMaps) {
			for (Map.Entry<String, Object> item : map.entrySet()) {
				String key = item.getKey();
				Object value = item.getValue();
				System.out.println("getEntryListAttribValues(): " + module
						+ " " + key + " = " + value);
			}
		}
	}

	/**
     * Print entry_list for debugging only 
     *
     * @param entryList as ArrayList<Map<String, Object>>
     * @param listOfMaps as ArrayList<HashMap<String, String>>
     * @return void 
     */
	@SuppressWarnings("unused")
	private void printEntryList(ArrayList<Map<String, Object>> entryList) {
		for (Map<String, Object> entry : entryList) {
			for (Map.Entry<String, Object> item : entry.entrySet()) {
				String key = item.getKey();
				Object value = item.getValue();
				System.out.println("printEntryList(): key = " + key
						+ "  value = " + value);
			}
		}
	}

	/**
     * Get the sessionId 
     *
     * @param username as String 
     * @param password (unencoded) as String 
     * @return sessionId as String 
     */
	private String getSessionId(String username, String password) {

		initPayload("login");
		addSessionIdPayload();

		Map<String, Object> mapParse = postRequest();

		this.sessionId = (String) mapParse.get("id");
		return this.sessionId;
	}

	/**
     * Add sessionId to payload
     *
     * @param None 
     * @return payload in Map<String, String>
     */
	private Map<String, String> addSessionIdPayload() {

		Map<String, String> userCredentials = new LinkedHashMap<String, String>();
		userCredentials.put("user_name", username);
		userCredentials.put("password", getMD5(this.password));

		// The order matters, use an ordered map
		Map<String, Object> request = new LinkedHashMap<String, Object>();
		// user_auth must be first. Otherwise null sessionId returned
		request.put("user_auth", userCredentials);
		request.put("application_name", "RestClient"); // optional

		String restData = JSONObject.toJSONString(request);

		Map<String, String> pl = getPayload();
		pl.put("rest_data", restData);

		return pl;
	}

	/**
     * Add parameters to payload for requesting module info
     *
     * @param module name 
     * @return payload in Map<String, String>
     */
	private Map<String, String> addModuleInfoPayload(String module) {

		Map<String, Object> restData = new LinkedHashMap<String, Object>();
		restData.put("session", this.sessionId);
		restData.put("module", module);
		restData.put("query", "");
		restData.put("order_by", "");
		restData.put("offset", "0");

		// Must be arrayList, not String[]. Otherwise parse result is null
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("id");
		arrList.add("name");
		restData.put("select_fields", arrList);
		ArrayList<String> linkNameToFieldsArrayList = new ArrayList<String>();
		restData.put("link_name_to_fields_array", linkNameToFieldsArrayList);
		restData.put("max_results", "100000"); // a large number to retrieve all
												// the elements
		restData.put("Favorites", false);

		String restDataJs = JSONObject.toJSONString(restData);

		Map<String, String> pl = getPayload();
		pl.put("rest_data", restDataJs);

		return pl;
	}
}
