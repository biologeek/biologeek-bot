package net.biologeek.bot.plugin.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;
import net.biologeek.bot.plugin.beans.category.MediaType;
import net.biologeek.bot.plugin.repositories.SimpleCategoryMembersRepository;

@Service
public class SimpleCategoryMembersServices {

	@Autowired
	SimpleCategoryMembersRepository scmRepository;
	private Logger logger;

	public SimpleCategoryMembersServices() {
		super();
		logger = Logger.getLogger(this.getClass().getName());
	}

	/**
	 * Gets the next item to process in flow table. <br>
	 * If mediaTypes == null, it retrieves whatever the next item
	 * 
	 * @param mediaTypes
	 *            the {@link MediaType}
	 * @return a {@link SimpleCategoryMember} if there is one, null else
	 */
	public SimpleCategoryMember getNextItemToRead(MediaType... mediaTypes) {
		List<SimpleCategoryMember> scm = mediaTypes == null ? scmRepository.getNextItemToRead()
				: scmRepository.getNextItemToRead(mediaTypes);
		SimpleCategoryMember result = scm.size() == 0 ? null : scm.get(0);
		if (scm == null) {
			logger.info("No item to read");
			return null;
		} else {
			return result;
		}
	}

}
