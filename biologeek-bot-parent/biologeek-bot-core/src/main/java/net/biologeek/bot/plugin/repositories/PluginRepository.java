package net.biologeek.bot.plugin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.biologeek.bot.plugin.beans.PluginBean;

@Repository
public interface PluginRepository extends JpaRepository<PluginBean, Long> {
	PluginBean findByName(String name);
}
