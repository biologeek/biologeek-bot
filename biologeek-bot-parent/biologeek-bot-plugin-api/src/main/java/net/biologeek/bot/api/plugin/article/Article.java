package net.biologeek.bot.api.plugin.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import net.biologeek.bot.api.plugin.serialization.ArticleContentQueryType;
import net.biologeek.bot.api.plugin.serialization.ArticleDeserializer;

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

	private String clcontinue;

	@JsonProperty("pageid")
	private String pageid;
	@JsonProperty("ns")
	private String ns;
	@JsonProperty("title")
	private String title;

	public Article() {
		super();
	}

	public String getClcontinue() {
		return clcontinue;
	}

	public void setClcontinue(String clcontinue) {
		this.clcontinue = clcontinue;
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

	public static class ArticleFactory {
		public static Article<?> getInstance(ArticleContentQueryType type) {
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
}
