package net.biologeek.bot.plugin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.article.ArticleElement;
import net.biologeek.bot.plugin.repositories.ArticleRepository;

@Service
@SuppressWarnings("rawtypes")
public class ArticleService<T extends ArticleElement> {

	@Autowired
	private ArticleRepository<T> repository;

	public T save(T arg0) {
		return repository.save(arg0);
	}

	public T getOne(Long id) {
		return (T) repository.findOne(id);
	}

	public ArticleRepository<T> getRepository() {
		return repository;
	}

	public void setRepository(ArticleRepository<T> repository) {
		this.repository = repository;
	}

}
