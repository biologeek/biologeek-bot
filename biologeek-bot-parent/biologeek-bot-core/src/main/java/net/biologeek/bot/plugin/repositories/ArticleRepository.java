package net.biologeek.bot.plugin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository<T> extends JpaRepository<T, Long>{

}
