package com.salesforcebatch.configuration;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler { 
  
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("distributeJob")
	private Job distributeJob;

	@Scheduled(cron = "0 * * * * *") // Every minute 
	public void runProcessJob() throws Exception {
		JobParameters jobParameters=new JobParametersBuilder()
										.addLong("dateTime", System.currentTimeMillis())
										.toJobParameters();
		JobExecution execution = jobLauncher.run(distributeJob, jobParameters);
		System.out.println("Job Execution Status: {}"+execution.getStatus());
	}
} 
