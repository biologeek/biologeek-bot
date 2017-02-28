package net.biologeek.bot.plugin.beans.article;

import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.Id;

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
@MappedSuperclass 
public abstract class ArticleElement<T> {

	@Id@GeneratedValue
	protected long id;
	private String title;

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

	public abstract T getValue();

	public abstract void setValue(T value);
}
