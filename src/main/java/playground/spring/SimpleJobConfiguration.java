package playground.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job simpleJob() {
        log.info("Creating simple job");
        return new JobBuilder("simpleJob", jobRepository)
                .start(simpleStep1())
                .next(simpleStep2())
                .build();
    }

    @Bean
    public Step simpleStep1() {
        log.info("Creating simple step");
        return new StepBuilder("simpleStep1", jobRepository)
                .tasklet(simpleTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Step simpleStep2() {
        log.info("Creating simple step 2");
        return new StepBuilder("simpleStep2", jobRepository)
                .tasklet(simpleTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet simpleTasklet() {
        log.info("Creating simple tasklet");
        return (contribution, chunkContext) -> {
            log.info("Executing simple tasklet");
            return RepeatStatus.FINISHED;
        };
    }
}
