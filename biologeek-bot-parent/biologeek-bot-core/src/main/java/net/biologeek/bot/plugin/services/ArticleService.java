package net.biologeek.bot.plugin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.repositories.ArticleRepository;

@Service
public class ArticleService<T> {
	
	@Autowired
	ArticleRepository<T> repository;
	
	public T save (T arg0){
		return repository.save(arg0);
	}
	
	public T getOne(Long id){
		return repository.findOne(id);
	}

}
