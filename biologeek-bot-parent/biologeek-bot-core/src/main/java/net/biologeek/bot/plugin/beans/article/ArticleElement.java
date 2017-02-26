package net.biologeek.bot.plugin.beans.article;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

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
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ArticleElement<T> {

	
	private long id;
	private String title;
	
	private T value;

	public ArticleElement() {
		super();
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
