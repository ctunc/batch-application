package com.batch.application.batchapplication;

import com.batch.common.config.batchcommonconfig.CronJobConfig;
import com.batch.common.config.batchcommonconfig.DbConfig;
import com.batch.common.config.batchcommonconfig.TaskJobConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.configuration.SimpleTaskAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableBatchProcessing
@EnableTask
@EnableAutoConfiguration(exclude = {SimpleTaskAutoConfiguration.class, DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Import({CronJobConfig.class,
		TaskJobConfig.class,DbConfig.class})
@ComponentScan(value = {"com.batch.common.config.batchcommonconfig",
"com.batch.application.batchapplication"})
public class SpringBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}
}
