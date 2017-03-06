package net.biologeek.bot.api.plugin.category;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember;

/**
 * CategoryMembers contains a list of categories that belong to the current
 * category or to which it belongs
 * <br><br>
 * It is used for listing categories of a category.
 * <br><br>
 * Attribute cmcontinue serves for paging. <br>Indeed, by default mediawiki API
 * returns only 10 results and provides cmcontinue to get next items.
 *
 */
public class CategoryMembers extends Category<List<CategoryMember>> {

	private List<CategoryMember> value;

	public CategoryMembers() {
		super();
		value = new ArrayList<>();
	}

	@Override
	public void setValue(List<CategoryMember> value) {
		this.value = value;
	}

	public List<CategoryMember> getValue() {
		return value;
	}

	public class CategoryMember {

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

}
