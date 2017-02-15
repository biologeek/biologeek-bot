package net.biologeek.bot.plugin.serialization;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.biologeek.bot.plugin.Article;

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
		Article article = new Article();

		ObjectMapper map = (ObjectMapper) arg0.getCodec();

		ObjectNode root = map.readTree(arg0);
		article.setBatchComplete(root.get("batchcomplete").toString());
		Iterator<JsonNode> it = root.get("query").get("pages").iterator();
		
		System.out.println("AAA " + root);
		while (it.hasNext()) {
			JsonNode node = it.next();

			if (node.has("pageid")) {
				article.getQuery().getPage().setPageid(node.get("pageid").toString());
			}
			if (node.has("ns")) {
				article.getQuery().getPage().setNs(node.get("ns").toString());
			}
			if (node.has("title")) {
				article.getQuery().getPage().setTitle(node.get("title").toString());
			}
			for (ContentQueryType type : ContentQueryType.values()){
				if (node.has(type.name())){
					article.getQuery().getPage().setValue(node.get(type.name()).toString());
				}
			}

		}
		return article;
	}

}
