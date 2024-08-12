package com.salesforcebatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean(name = "distributeJob")
	Job distributeJob(@Qualifier("distributionStep") Step distributionStep) {
		return new JobBuilder("distributeJob", jobRepository)
				 .incrementer(new RunIdIncrementer())
				 .start(distributionStep)
				 .preventRestart()
				 .build();
	}

	@Bean(name = "distributionStep")
	Step distributionStep() {
		return new StepBuilder("distributionStep", jobRepository)
				.<String, String>chunk(1, transactionManager)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.build();
	}

	@Bean
	ItemReader<String> itemReader() {
		return new ItemReader<>() {
			@Override
			public String read() {
				return "Hello"; // Simple item for demonstration
			}
		};
	}

	@Bean
	ItemProcessor<String, String> itemProcessor() {
		return item -> {
			System.out.println("Processing: " + item);
			return item;
		};
	}

	@Bean
	ItemWriter<String> itemWriter() {
		return items -> {
			for (String item : items) {
				System.out.println("Writing: " + item);
			}
		};
	}

}
