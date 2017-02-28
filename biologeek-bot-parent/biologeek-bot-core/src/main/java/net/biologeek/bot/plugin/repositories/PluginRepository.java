package net.biologeek.bot.plugin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.biologeek.bot.plugin.beans.PluginBean;

public interface PluginRepository extends JpaRepository<PluginBean, Long> {
	PluginBean findByName(String name);
}
