package com.hadoop.sum;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SumStep {
	
	public static class SumMapper extends Mapper<LongWritable, Text, Text, InfoBean> {
		
		private Text k = new Text();
		private InfoBean v = new InfoBean();
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, InfoBean>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] filelds = line.split(" ");
			String account = filelds[0];
			double in = Double.parseDouble(filelds[1]);
			double out = Double.parseDouble(filelds[2]);
			k.set(account);
			v.set(account, in, out);
			context.write(k, v);
		}
		
	}

	public static class SumReducer extends Reducer<Text, InfoBean, Text, InfoBean> {

		private InfoBean v = new InfoBean();

		@Override
		protected void reduce(Text key, Iterable<InfoBean> values,Reducer<Text, InfoBean, Text, InfoBean>.Context context) throws IOException, InterruptedException {
			double in_sum = 0;
			double out_sum = 0;
			for (InfoBean infoBean : values) {
				in_sum += infoBean.getIncome();
				out_sum += infoBean.getExpenses();
			}
			v.set("", in_sum, out_sum);
			context.write(key, v);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(SumStep.class);
		
		job.setMapperClass(SumMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(InfoBean.class);
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/file/account"));
		
		job.setReducerClass(SumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(InfoBean.class);
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/file/out"));
		
		job.waitForCompletion(true);
	}

}
