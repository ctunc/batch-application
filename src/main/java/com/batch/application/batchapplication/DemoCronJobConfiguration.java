package com.batch.application.batchapplication;

import com.batch.common.config.batchcommonconfig.CronJobConfig;
import com.batch.common.config.batchcommonconfig.DbConfig;
import com.batch.common.config.batchcommonconfig.TaskJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

@Configuration
@Import({DbConfig.class})
public class DemoCronJobConfiguration {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Qualifier("h2DataSource")
    private DataSource h2DataSource;


    @Autowired
    private JtaTransactionManager transactionManager;

    @Bean
    public Job demoJob(@Autowired JobRepository batchJobRepository) {
        SimpleJob job = new SimpleJob("demoJob");
        String runParameter = "run.id";
        job.setSteps(java.util.Arrays.asList(demoJobStep(batchJobRepository)));
        job.setJobRepository(batchJobRepository);
        job.setJobParametersIncrementer(new JobParametersIncrementer() {
            @Override
            public JobParameters getNext(JobParameters parameters) {
                if (parameters==null || parameters.isEmpty()) {
                    return new JobParametersBuilder().addLong(runParameter, 1L).toJobParameters();
                }
                long id = parameters.getLong(runParameter,1L) + 1;
                return new JobParametersBuilder().addLong(runParameter, id).toJobParameters();
            }
        });
        logger.info("------job instance created-------");
        return job;
    }

    @Bean
    public Step demoJobStep(@Autowired JobRepository batchJobRepository) {
        DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
        attribute.setTimeout(2000);

        TaskletStep step = new TaskletStep("demoJobStep");
        step.setTasklet(generateTasklet());
        step.setJobRepository(batchJobRepository);
        transactionManager.setAllowCustomIsolationLevels(true);
        step.setTransactionManager(transactionManager);
        step.setTransactionAttribute(attribute);
        return step;
    }

    @Bean
    public DemoCronJobTasklet generateTasklet(){

        return new DemoCronJobTasklet();
    }
}
