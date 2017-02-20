package net.biologeek.bot.plugin.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.biologeek.bot.plugin.PluginBean;

public interface PluginRepository extends JpaRepository<PluginBean, Long> {
	PluginBean findByName(String name);
}
