package net.biologeek.bot.plugin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;
import net.biologeek.bot.plugin.beans.category.MediaType;

@Repository
public interface SimpleCategoryMembersRepository extends JpaRepository<SimpleCategoryMember, Long>{

	@Query("from SimpleCategoryMember order by id asc limit 1")
	SimpleCategoryMember getNextItemToRead();

	@Query("from SimpleCategoryMember where mediaType = ?1 order by id asc limit 1")
	SimpleCategoryMember getNextItemToRead(MediaType... mediatype);	
}
