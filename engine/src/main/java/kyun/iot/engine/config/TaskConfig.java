package kyun.iot.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import kyun.iot.engine.batch.BatchScheduler;

@Configuration
@Profile("batch")
public class TaskConfig {

	@Bean
	public BatchScheduler batchScheduler() {
		return new BatchScheduler();
	}
}
