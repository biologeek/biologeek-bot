package net.biologeek.bot.plugin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import net.biologeek.bot.plugin.serialization.ArticleDeserializer;

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
@JsonDeserialize(using=ArticleDeserializer.class)
public class Article {

	@JsonProperty("query")
	private Query query;

	public Article() {
		super();
		query = new Query();
	}

	@Override
	public String toString() {
		return "Article [query=" + query + "]";
	}

	private String batchComplete;

	public String getBatchComplete() {
		return batchComplete;
	}

	public void setBatchComplete(String batchComplete) {
		this.batchComplete = batchComplete;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public static class Query {
		private Page page;

		@Override
		public String toString() {
			return "Query [page=" + page + "]";
		}

		public Query() {
			super();
			page = new Page();
		}

		public Page getPage() {
			return page;
		}

		public void setPage(Page page) {
			this.page = page;
		}
	}

	public static class Page {
		@JsonProperty("pageid")
		private String pageid;
		@JsonProperty("ns")
		private String ns;
		@JsonProperty("title")
		private String title;

		private String value;

		@Override
		public String toString() {
			return "Page [pageid=" + pageid + ", ns=" + ns + ", title=" + title + ", value=" + value + "]";
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

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}
