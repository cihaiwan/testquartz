package com.simple;


//import static org.quartz.JobBuilder.*;
//import static org.quartz.TriggerBuilder.*;
//import static org.quartz.CronScheduleBuilder.*;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
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
	
	public static void execution(){
		logger.info("开始复制文件");
		List<File> files=findFile();
		createDir(files);
		logger.info("结束复制文件");
	}
	
	public static void main(String[] args) throws SchedulerException, FileNotFoundException, IOException{
		
		execution();
		String cron=properties.get("cron").toString();
		System.out.println(cron);
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		JobDetail job = newJob(CopyFile.class)
			      .withIdentity("job1", "group1")
			      .build();
			  Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
			      .startNow()
			            .withSchedule(cronSchedule(cron))            
			      .build();

			  // Tell quartz to schedule the job using our trigger
			  scheduler.scheduleJob(job, trigger);

			  scheduler.start();

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
		return list;
	}
	public static void createDir(List<File> files){
		String dir=properties.get("dir").toString();
		if(dir.lastIndexOf("/")!=0){
			dir+="/"+DateFormatUtils.format(new Date(),properties.getProperty("fileformat").toString());
		}
		File file=new File(dir);
		try {
			FileUtils.deleteDirectory(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(file.getName()+"文件删除失败",e1);
		}
		if(!file.exists()){
			logger.info(dir);
			file.mkdirs();
			for(File f:files){
				File ff=new File(dir+"/"+f.getName());
				if(f.isDirectory()){
					ff.mkdirs();
					try {
						FileUtils.copyDirectory(f, ff);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("文件复制失败", e);
					}
				}else {
					try {
						ff.createNewFile();
						FileUtils.copyFile(f, ff);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(ff.getName()+"文件未找到，无法创建",e);
					}
				}
				
				
			}
		}
	}
	
	
	
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		execution();
	}
}