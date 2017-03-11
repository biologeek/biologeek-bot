package net.biologeek.bot.plugin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.biologeek.bot.plugin.beans.article.ArticleElement;

@Repository
public interface ArticleRepository<ArticleElement> extends JpaRepository<ArticleElement, Long>{

}
