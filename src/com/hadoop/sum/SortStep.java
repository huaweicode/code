package com.hadoop.sum;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SortStep {
	
	public static class SortMapper extends Mapper<LongWritable, Text, InfoBean, NullWritable> {
		
		private InfoBean k = new InfoBean();
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, InfoBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = line.split("\\s+");
			String account = fields[0];
			double in = Double.parseDouble(fields[1]);
			double out = Double.parseDouble(fields[2]);
			k.set(account, in, out);
			context.write(k, NullWritable.get());
		}
	}
	
	public static class SortReducer extends Reducer<InfoBean, NullWritable, Text, InfoBean> {
		private Text k = new Text();
		@Override
		protected void reduce(InfoBean bean, Iterable<NullWritable> values,Reducer<InfoBean, NullWritable, Text, InfoBean>.Context context) throws IOException, InterruptedException {
			String account = bean.getAccount();
			k.set(account);
			context.write(k, bean);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(SortStep.class);
		
		job.setMapperClass(SortMapper.class);
		job.setMapOutputKeyClass(InfoBean.class);
		job.setMapOutputValueClass(NullWritable.class);
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/file/out/part-r-00000"));
		
		job.setReducerClass(SortReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(InfoBean.class);
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/file/out1"));
		
		job.waitForCompletion(true);
		
	}
	
	
}
