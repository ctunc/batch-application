package com.batch.application.batchapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class DemoCronJobTasklet implements Tasklet {
    private static Logger logger= LoggerFactory.getLogger(DemoCronJobTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            logger.info("hello demo job");
            return RepeatStatus.FINISHED;
    }
}
