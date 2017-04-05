package com.simple;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class CopyFile implements Job{
	
	public static void main(String[] args) throws SchedulerException, FileNotFoundException, IOException{
		Map<String,String> map=System.getenv();
		for(String key:map.keySet()){
			System.out.println(key);
		}
		String copyFile=System.getenv("COPY_CONFIG");
		Properties properties=new Properties();
		properties.load(new FileInputStream(new File(copyFile)));
		System.out.println(properties.get("a"));
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//		JobDetail job = newJob(CopyFile.class)
//			      .withIdentity("job1", "group1")
//			      .build();
//			  Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
//			      .startNow()
//			            .withSchedule(cronSchedule("0/1 * * * * ?"))            
//			      .build();
//
//			  // Tell quartz to schedule the job using our trigger
//			  scheduler.scheduleJob(job, trigger);
//
//			  scheduler.start();

             // scheduler.shutdown();
	}

	public static void findFile(){
		
		
		
	}
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("1234");
	}
}