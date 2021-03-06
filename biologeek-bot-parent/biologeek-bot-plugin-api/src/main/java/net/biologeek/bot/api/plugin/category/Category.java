package net.biologeek.bot.api.plugin.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.biologeek.bot.api.plugin.serialization.CategoryContentQueryType;

/**
 * 
 * { "batchcomplete": "", "continue": { "cmcontinue":
 * "page|2b2f414d2f4904433104452f492b4f4b4b3743410118018f17|2664158",
 * "continue": "-||" }, "query": { "categorymembers": [{ "pageid": 22939, "ns":
 * 0, "title": "Physics" }, { "pageid": 3445246, "ns": 0, "title": "Glossary of
 * classical physics" } ] } }
 *
 * @author xcaron
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Category<T> {

	@JsonProperty("batchcomplete")
	private String batchcomplete;
	private String cmContinue;

	public String getBatchcomplete() {
		return batchcomplete;
	}

	public void setBatchcomplete(String batchcomplete) {
		this.batchcomplete = batchcomplete;
	}

	public String getCmContinue() {
		return cmContinue;
	}

	public void setCmContinue(String cmContinue) {
		this.cmContinue = cmContinue;
	}
	public abstract void setValue(T value);
	
	public static class CategoryFactory {
		public static Category<?> getInstance(CategoryContentQueryType type) {
			switch (type) {
			case categorymembers:
				return new CategoryMembers();
			default:
				return null;
			}
		}
	}
}
