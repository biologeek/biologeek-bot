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

import net.biologeek.bot.api.plugin.category.Category;
import net.biologeek.bot.api.plugin.category.CategoryMembers;
import net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember;

public class CategoryDeserializer extends JsonDeserializer<Category> {

	@Override
	public Category deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		Category category = null;
		ObjectMapper map = (ObjectMapper) p.getCodec();
		ObjectNode root = map.readTree(p);
		JsonNode node = null;

		if (root.get("query") != null && root.get("query").get(0) != null) {
			node = root.get("query").get(0);
			switch (CategoryContentQueryType.value(node.toString())) {

			case categorymembers:
				category = new CategoryMembers();
				Iterator<JsonNode> it = node.elements();
				while (it.hasNext()) {
					JsonNode currentNode = it.next();
					CategoryMember member = ((CategoryMembers) category).new CategoryMember();
					member.setPageid(currentNode.get("pageid").intValue());
					member.setNs(currentNode.get("ns").intValue());
					member.setTitle(currentNode.get("title").toString());
					((CategoryMembers) category).getValue().add(member);
				}
				break;
			}
		}
		if (root.get("continue") != null) {
			category.setCmContinue(root.get("continue").get("cmcontinue").toString());
		}

		if (root.has("batchcomplete")) {
			category.setBatchcomplete(root.get("batchcomplete").toString());
		}

		return category;
	}

}
