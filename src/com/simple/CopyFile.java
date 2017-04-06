package com.simple;


//import static org.quartz.JobBuilder.*;
//import static org.quartz.TriggerBuilder.*;
//import static org.quartz.CronScheduleBuilder.*;


import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyFile implements Job{
	public static Logger logger=LoggerFactory.getLogger(CopyFile.class);
	
	public static Properties properties=new Properties();
	static{
		String copyFile=System.getenv("COPY_CONFIG");
		try {
			properties.load(new FileInputStream(new File(copyFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SchedulerException, FileNotFoundException, IOException{
		
		List<File> files=new ArrayList<File>();
		createDir(files);
//		String cron=properties.get("cron").toString();
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//		JobDetail job = newJob(CopyFile.class)
//			      .withIdentity("job1", "group1")
//			      .build();
//			  Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
//			      .startNow()
//			            .withSchedule(cronSchedule(cron))            
//			      .build();
//
//			  // Tell quartz to schedule the job using our trigger
//			  scheduler.scheduleJob(job, trigger);
//
//			  scheduler.start();

             // scheduler.shutdown();
	}

	public static List<File> findFile(){
		List<File> list=new ArrayList<File>();
		String files=properties.get("files").toString();
		for(String fileDir:files.split(",")){
			File file=new File(fileDir);
			if(file.exists()){
				list.add(file);
			}
		}
		System.out.println(list.size());
		return list;
	}
	public static void createDir(List<File> files){
		String dir=properties.get("dir").toString();
		if(dir.lastIndexOf("/")!=0){
			dir+="/"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmss");
		}
		File file=new File(dir);
		if(!file.exists()){
			logger.info(dir);
			file.mkdirs();
			for(File f:files){
				
			}
		}
	}
	
	
	
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("1234");
	}
}