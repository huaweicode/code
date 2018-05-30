package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class Test {
	
	public static void main(String[] args) throws Exception {
		Configuration config = new Configuration();

//		config.set("fs.defaultFS", "hdfs://master:9000/");
//		config.set("mapreduce.job.jar", "wc.jar");
//		config.set("mapreduce.framework.name", "yarn");
//		config.set("yarn.resourcemnanger.hostname", "master");;
//		config.set("mapreduce.app.submission.cross.platform", "true");
		
		config.set("fs.defaultFS", "hdfs://master:9000/");
		 
		config.set("mapreduce.job.jar", "wc.jar");
		config.set("mapreduce.framework.name", "yarn");
		config.set("yarn.resourcemanager.hostname", "master");
		config.set("mapreduce.app-submission.cross-platform", "true");
		
		Job job = Job.getInstance(config);
		
		job.setMapperClass(WordMapper.class);
		job.setReducerClass(WordReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		
//		FileInputFormat.setInputPaths(job, "D:\\hadoopData\\inputFile\\test.txt");
//		FileOutputFormat.setOutputPath(job, new Path("D:\\hadoopData\\outputFile\\"));
		FileInputFormat.setInputPaths(job, "/wcinput/input.txt");
		FileOutputFormat.setOutputPath(job, new Path("/wcoutput3/"));
		
		job.waitForCompletion(true);
		
	}

}
