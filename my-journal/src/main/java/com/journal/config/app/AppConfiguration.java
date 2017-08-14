/**
 *
 */
package com.journal.config.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Praveen Sivasamy
 *
 */
@Configuration
public class AppConfiguration {

	private static final Logger log = LoggerFactory.getLogger(AppConfiguration.class);

	@Bean(name = "baseConfiguration")
	public String BaseConfiguration() {
		log.info("Loaded Base Configuration");
		return "BaseConfiguration";
	}
}
