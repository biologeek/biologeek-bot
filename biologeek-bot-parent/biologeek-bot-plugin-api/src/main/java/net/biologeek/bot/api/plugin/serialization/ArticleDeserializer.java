package net.biologeek.bot.api.plugin.serialization;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.biologeek.bot.api.plugin.article.Article;
import net.biologeek.bot.api.plugin.article.Article.ArticleFactory;

public class ArticleDeserializer extends JsonDeserializer<Article> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3802137266736659663L;
	protected ArticleDeserializer(){
		super();
	}
	
	
	@Override
	public Article deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		Article article = null;

		ObjectMapper map = (ObjectMapper) arg0.getCodec();

		ObjectNode root = map.readTree(arg0);
		Iterator<JsonNode> it = root.get("query").get("pages").iterator();
		
		/*
		 * First to create the correct object we have to determine which object to create
		 */
		while (it.hasNext()) {
			JsonNode node = it.next();
			
			for (ArticleContentQueryType type : ArticleContentQueryType.values()){
				if (node.has(type.name())){
					article = ArticleFactory.getInstance(type);
					article.setValue(node.get(type.name()).toString()); // A little tricky
				}
			}
			if (node.has("pageid")) {
				article.setPageid(node.get("pageid").toString());
			}
			if (node.has("ns")) {
				article.setNs(node.get("ns").toString());
			}
			if (node.has("title")) {
				article.setTitle(node.get("title").toString());
			}

		}
		article.setBatchComplete(root.get("batchcomplete").toString());
		
		return article;
	}

}
