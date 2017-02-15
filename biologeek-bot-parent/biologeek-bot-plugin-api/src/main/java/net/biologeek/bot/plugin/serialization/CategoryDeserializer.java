package net.biologeek.bot.plugin.serialization;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.biologeek.bot.plugin.category.Category;
import net.biologeek.bot.plugin.category.CategoryMembers.CategoryMember;

public class CategoryDeserializer extends JsonDeserializer<Category> {

	@Override
	public Category deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Category category = null;
		
		
		
		
		return null;
	}

}
