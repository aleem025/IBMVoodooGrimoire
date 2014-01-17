package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;

import wip.KBDocRecord;
import wip.KBTagRecord;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.test.SugarTest;

public class KnowledgeBase_8096 extends SugarTest {
	KBDocRecord myKnowledgeBaseArticle;
	KBTagRecord myKnowledgeBaseTag1;
	KBTagRecord myKnowledgeBaseTag2;

	public void setup() throws Exception {
		/*
		sugar.login();
		// Create a 'from' KnowledgeBase Tag.
		FieldSet KBTagOptions1 = new FieldSet();
		KBTagOptions1.put("name", "Tag1");
		
		myKnowledgeBaseTag1 = sugar.knowledgeBase.createTag(KBTagOptions1); 

		// Create a 'to' KnowledgeBase Tag.
		FieldSet KBTagOptions2 = new FieldSet();
		KBTagOptions2.put("name", "Tag2");
	
		myKnowledgeBaseTag2 = sugar.knowledgeBase.createTag(KBTagOptions2); 

		// Create a KnowledgeBase article associated with Tag1, the 'from' tag

		FieldSet KBArticleOptions = new FieldSet();
		KBArticleOptions.put("title", "QA_Article1");
		KBArticleOptions.put("tag", myKnowledgeBaseTag1.tag);
		
//		myKnowledgeBaseArticle = sugar.knowledgeBase.create(KBArticleOptions); 
*/
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		// Populate KnowledgeBase Article move Tag Options with this data.
		FieldSet KBArticleOptions = new FieldSet();
		KBArticleOptions.put("title", myKnowledgeBaseArticle.title);
		KBArticleOptions.put("from_tag", myKnowledgeBaseTag1.tag);
		KBArticleOptions.put("to_tag", myKnowledgeBaseTag2.tag);

		// Valid move from one tag to another, method will validate the move
//		sugar.KnowledgeBase.moveTag(KBArticleOptions); 

		// Invalid move to the same tag the Article is on
		FieldSet KBArticleOptions1 = new FieldSet();
		KBArticleOptions1.put("title", myKnowledgeBaseArticle.title);
		KBArticleOptions1.put("from_tag", myKnowledgeBaseTag1.tag);
		KBArticleOptions1.put("to_tag", myKnowledgeBaseTag1.tag);

		// This method will detect the invalid condition and verify the correct behavior
//		sugar.KnowledgeBase.moveTag(KBArticleOptions1); 

		// Populate KnowledgeBase Article with valid move Tag Options and a Cancel action
		FieldSet KBArticleOptions2 = new FieldSet();
		KBArticleOptions2.put("name", myKnowledgeBaseArticle.title);
		KBArticleOptions2.put("from_tag", myKnowledgeBaseTag1.tag);
		KBArticleOptions2.put("to_tag", myKnowledgeBaseTag2.tag);
		KBArticleOptions2.put("Action", "Cancel");

		// Cancel an otherwise valid move - Cancel could also be another method  
//		sugar.KnowledgeBase.moveTag(KBArticleOptions2); 
//	    Login as a valid admin user
//	    Go to KB module
//	    Go to Knowledge Base Admin
//	    Select "Move Selected Articles" from "-Admin Actions-" dropdown list
//	    Select a tag by clicking its name link, make sure at least one article is put under this tag
//	    Click "Selete Tag" button without selecting any article
//	    See Verification Step 1
//	    Select article(s) by checking checkbox in front of them
//	    Click "Select Tag" button
//	    See Verification Step 2
//	    Select the tag that is the same tag as selected, then click "Move" button
//	    See Verification Step 3
//	    Select a tag except current tag, then click "Cancel" button
//	    See Verification Step 4
//	    Select a tag except current tag, then click "Move" button
//	    See Verification Step 5
		
//		Verification Step 1	    Verify that "Selete Articles First" message returns.
//	    Verification Step 2	    Verify that select tag window is displayed with correct tag tree.
//		Verification Step 3	    Verify that message "Source and Target tags are same" returns.
//		Verification Step 4	    Verify that no tag is selected and the select window is still displayed.
//		Verification Step 5	    Verify that selected article(s) are moved from original tag to the newly selected tag.
	}

	public void cleanup() throws Exception {
//		sugar.KnowledgeBase.api.deleteArticle(myKnowledgeBaseArticle);
//		sugar.KnowledgeBase.api.deleteTag(myKnowledgeBaseTag1);
//		sugar.KnowledgeBase.api.deleteTag(myKnowledgeBaseTag2);
		sugar.logout();
	}
}
