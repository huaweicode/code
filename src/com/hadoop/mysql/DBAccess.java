package com.hadoop.mysql;


import java.io.IOException;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.mapred.lib.db.*;
import org.junit.Test;

public class DBAccess {
	
	/**
	 * hdfs read mysql
	 * @throws Exception
	 */
//	@Test
	public void readMysql() throws Exception {
		JobConf conf = new JobConf(DBAccess.class);
		conf.setOutputKeyClass(LongWritable.class);
		conf.setOutputValueClass(Text.class);
		conf.setInputFormat(DBInputFormat.class);
		Path path = new Path("hdfs://master:9000/mysql/data");
		FileOutputFormat.setOutputPath(conf, path);
		DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
				"jdbc:mysql://10.247.182.21:3306/chenpei","root","root");
		String [] fields = {"id","name","type"};
		DBInputFormat.setInput(conf, DBRecord.class, "hadoop_home",
				null,"id", fields);
		conf.setMapperClass(DBRecordMapper.class);
		conf.setReducerClass(IdentityReducer.class);
//		DBOutputFormat.setOutput(conf, "hdoop_home", fields);
		JobClient.runJob(conf);
	}
	
//	@Test
	public void writeMysql() throws IOException {
		JobConf conf = new JobConf(WriteDB.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(DBOutputFormat.class);
		
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(DBRecord.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		
		conf.setMapperClass(WriteDB.Map.class);
		conf.setReducerClass(WriteDB.Reduce.class);
		
		FileInputFormat.setInputPaths(conf, new Path("hdfs://master:9000/mysql/data"));
		
		DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://10.247.182.21:3306/chenpei","root","root");
		
		String[] fields = {"id","name","type"};
		DBOutputFormat.setOutput(conf, "hadoop_home", fields);
		JobClient.runJob(conf);
	}
	
	public static void main(String[] args) throws Exception {
		
	}

}
