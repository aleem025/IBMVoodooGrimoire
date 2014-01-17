package wip;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.modules.BWCModule;

/**
 * Knowledgebase module object which contains tasks associated with the Knowledgebase module
 * like create/deleteAll
 * @author 
 *
 */
public class KBModule extends BWCModule {
	protected static KBModule module;
	protected static int count = 0;
	
	public static KBModule getInstance() throws Exception {
		if (module == null) module = new KBModule();
		return module;
	}

	private KBModule() throws Exception {
		moduleNameSingular = "Knowledgebase";
		recordClassName = KBDocRecord.class.getName();
		//api = new Api();

		// TODO: Replace with loading data from disk.
		editView.addControl("title", "input", "id", "kbdocument_name");
		editView.addControl("articleBody", "body", "id", "tinymce");
		editView.addControl("selectTag", "input", "name", "btn_tags");
		editView.addControl("save", "input","id","btn_save");
		editView.addControl("removeTag", "img", "css", "div#Linked_Tags div img");

		popupSearch.addControl("searchTagName", "input", "id", "tags_search");
		popupSearch.addControl("searchTags", "input", "id", "searchTags");
		popupSearch.addControl("firstTag", "a", "css", "#tagstree div:nth-of-type(1) div:nth-of-type(1) div:nth-of-type(1) div:nth-of-type(1) div:nth-of-type(1) table:nth-of-type(1) tr:nth-of-type(1) td:nth-of-type(1)");
		
		listView.addControl("searchField", "input", "id", "searchText");
		listView.addControl("clearFormBasic", "input", "id", "clearFormBasic");
		listView.addControl("searchFormBasic", "input", "id", "fts_search");
		listView.addControl("article", "a", "css", "#fts_results_form table:nth-of-type(1) tr:nth-of-type(3) td:nth-of-type(1) span:nth-of-type(1) a");
		
		// KB Admin
		listView.addControl("adminAction", "select", "id", "action_id");
		listView.addControl("tagTreeRoot", "a", "id", "ygtvlabelel1");
		listView.addControl("tagName", "input", "id", "tag_new");
		listView.addControl("tagSave", "input", "id", "tag_save");
	
		detailView.addControl("menuExpand", "ul", "css", "#detail_header_action_menu li ul");
		detailView.addControl("delete", "a", "id", "delete_button");
		
		//Load default data
		// TODO: Replace with loading data from disk.

		defaultData.put("title", "Test Article");

	}
	
	public KBTagRecord createTag(FieldSet tagData) throws Exception{
		/*
		sugar.navbar.showAllMenus();
		sugar.navbar.selectAction("moduleTab_AllKBDocuments", "AdministerKnowledgeBaseAll");
		VoodooUtils.pause(800);
		sugar.knowledgeBase.listView.getControl("adminAction").set("Create New Tag");
		sugar.knowledgeBase.listView.getControl("tagTreeRoot").click();
		sugar.knowledgeBase.listView.getControl("tagName").set(tagData.get("name"));
		sugar.knowledgeBase.listView.getControl("tagSave").click();
		*/
		return new KBTagRecord(tagData);
	}
	
	/**
	 * deleteAll() will delete all the records that were created in Knowledgebase
	 */
	public void deleteAll(DataSource knowledgebaseData) throws Exception {
		/*
		sugar.navbar.showAllMenus();
		sugar.navbar.selectAction("moduleTab_AllKBDocuments", "ViewArticlesAll");
		VoodooUtils.pause(800);
		for(FieldSet articleData : knowledgebaseData) {
			// Clear form and search for specific articles
			sugar.knowledgeBase.listView.getControl("clearFormBasic").click();
			sugar.knowledgeBase.listView.getControl("searchField").set(articleData.get("title"));
			sugar.knowledgeBase.listView.getControl("searchFormBasic").click();
			
			// Go to detailview of first article found and delete it
			sugar.knowledgeBase.listView.getControl("article").click();
			VoodooUtils.pause(800);
			sugar.knowledgeBase.listView.getControl("menuExpand").click();
			VoodooUtils.pause(800);
			sugar.knowledgeBase.listView.getControl("delete").click();
			VoodooUtils.pause(800);
			
			// Accept pop up dialog message
			VoodooUtils.acceptDialog();
		}
		*/
	}
}