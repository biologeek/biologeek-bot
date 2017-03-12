package net.biologeek.bot.plugin.beans.article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.biologeek.bot.plugin.beans.Prop;

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
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ArticleElement<T> {

	@Id@GeneratedValue
	protected long id;
	protected long pageId;
	protected String title;

	public ArticleElement() {
		super();
	}

	public long getPageId() {
		return pageId;
	}

	public void setPageId(long pageId) {
		this.pageId = pageId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract T getValue();

	public abstract void setValue(T value);

	public static class ArticleFactory {
		public static ArticleElement<?> getInstance(Prop type) {
			switch (type) {
			case CONTENT:
				return new ArticleContent();
			case BELONG_TO_CATEGORIES:
				return new ArticleCategories();
			case CONTRIBUTORS:
				break;
			case MODIFICATION_HISTORY:
				break;
			case SUB_CATEGORIES:
				throw new IllegalArgumentException("Articles do not have categories !");
			case TITLE:
				break;
			default:
				return null;
			}
			return null;
		}
	}

}
