package net.biologeek.bot.plugin.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;
import net.biologeek.bot.plugin.beans.category.MediaType;

@Repository
public interface SimpleCategoryMembersRepository extends JpaRepository<SimpleCategoryMember, Long>{

	
	@Query("from SimpleCategoryMember order by id asc")
	List<SimpleCategoryMember> getNextItemToRead(Pageable pageable);
	
	default List<SimpleCategoryMember> getNextItemReader(){
		return getNextItemToRead(new PageRequest(0, 1));
	}

	@Query("from SimpleCategoryMember where mediaType = ?1 order by id asc")
	List<SimpleCategoryMember> getNextItemToRead(Pageable pageable, MediaType... mediatype);	

	default List<SimpleCategoryMember> getNextItemToRead(MediaType...mediaTypes){
		return getNextItemToRead(new PageRequest(0, 1), mediaTypes);
	}

}
