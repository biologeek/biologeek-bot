package net.biologeek.bot.api.plugin.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.biologeek.bot.api.plugin.category.Category;

public class CategoryDeserializer extends JsonDeserializer<Category> {

	@Override
	public Category deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Category category = null;
		
		
		
		
		return null;
	}

}
