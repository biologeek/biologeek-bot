package net.biologeek.bot.plugin.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import net.biologeek.bot.plugin.serialization.ArticleDeserializer;
import net.biologeek.bot.plugin.serialization.ContentQueryType;

/**
 * Represents a raw Wikipedia article response. Example JSON :
 * 
 * { "batchcomplete": "", "query": { "normalized": [ { "from": "tintin", "to":
 * "Tintin" } ], "pages": { "3886536": { "pageid": 3886536, "ns": 0, "title":
 * "Tintin", "extract": "
 * <p>
 * <b>Tintin</b> may refer " } } } }
 * 
 * @author xcaron
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ArticleDeserializer.class)
public abstract class Article<T> {

	private String batchComplete;

	@JsonProperty("pageid")
	private String pageid;
	@JsonProperty("ns")
	private String ns;
	@JsonProperty("title")
	private String title;

	public Article() {
		super();
	}

	public String getBatchComplete() {
		return batchComplete;
	}

	public void setBatchComplete(String batchComplete) {
		this.batchComplete = batchComplete;
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract void setValue(T value);

	public static Article<?> getInstance(ContentQueryType type) {
		switch (type) {

		case extract:
			return new ArticleContent();
		case categories:
			return new ArticleCategories();
		default:
			return null;
		}
	}

}
