package net.biologeek.bot.plugin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.biologeek.bot.plugin.serialization.Errorable;

/**
 * 
 * {
 *	"batchcomplete": "",
 *	"continue": {
 *		"cmcontinue": "page|2b2f414d2f4904433104452f492b4f4b4b3743410118018f17|2664158",
 *		"continue": "-||"
 *	},
 *	"query": {
 *		"categorymembers": [{
 *				"pageid": 22939,
 *				"ns": 0,
 *				"title": "Physics"
 *			}, {
 *				"pageid": 3445246,
 *				"ns": 0,
 *				"title": "Glossary of classical physics"
 *			}
 *		]
 *	}
 *}
 *
 * @author xcaron
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Category implements Errorable {
	
	@JsonProperty("batchcomplete")
	private String batchcomplete;
	@JsonProperty("query")
	private Query query;
	
	public static class Query{
		@JsonProperty("categorymembers")
		private List<CategoryMember> categorymembers;

		public List<CategoryMember> getCategorymembers() {
			return categorymembers;
		}

		public void setCategorymembers(List<CategoryMember> categorymembers) {
			this.categorymembers = categorymembers;
		}
		
	}

	public static class CategoryMember{
		@JsonProperty("pageid")
		private int pageid;
		@JsonProperty("ns")
		private int ns;
		@JsonProperty("title")
		private String title;
		public int getNs() {
			return ns;
		}
		public void setNs(int ns) {
			this.ns = ns;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getPageid() {
			return pageid;
		}
		public void setPageid(int pageid) {
			this.pageid = pageid;
		}
		@Override
		public String toString() {
			return "CategoryMember [pageid=" + pageid + ", ns=" + ns + ", title=" + title + "]";
		}
		
	}

	public String getBatchcomplete() {
		return batchcomplete;
	}

	public void setBatchcomplete(String batchcomplete) {
		this.batchcomplete = batchcomplete;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

}
