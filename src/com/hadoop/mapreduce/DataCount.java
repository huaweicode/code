package com.hadoop.mapreduce;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class DataCount {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(DataCount.class);
		job.setMapperClass(DCMapper.class);
		job.setReducerClass(DCReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
//		FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/file/phone.txt"));
		
		job.setOutputKeyClass(Text.class); 	
		job.setOutputValueClass(DataBean.class);
		
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
//		FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/file/out1"));
		job.setPartitionerClass(ProviderPartitioner.class);
		job.setNumReduceTasks(Integer.parseInt(args[2]));
		
		job.setCombinerClass(DCReducer.class);
		
		
		
		job.waitForCompletion(true);
	}
	
	/**
	 * mapreduce分区
	 * @author admin
	 *
	 */
	public static class ProviderPartitioner extends Partitioner<Text, DataBean> {

		private static Map<String,Integer> providerMap = new HashMap<>();
		static {
			providerMap.put("137", 1);
			providerMap.put("138", 1);
			providerMap.put("139", 1);
			providerMap.put("182", 1);
			providerMap.put("135", 1);
			providerMap.put("159", 1);
			providerMap.put("136", 1);
			providerMap.put("150", 1);
			providerMap.put("180", 2);
			providerMap.put("173", 2);
			providerMap.put("156", 2);
			providerMap.put("155", 2);
			providerMap.put("181", 3);
			providerMap.put("133", 3);
		}
		@Override
		public int getPartition(Text key, DataBean value, int numPatitions) {
			String account = key.toString();
			String sub_acc = account.substring(0, 3);
			Integer code = (providerMap.get(sub_acc) != null) ? providerMap.get(sub_acc) : 0;
			return code;
		}
		
	}
	
	public static class DCMapper extends Mapper<LongWritable, Text, Text, DataBean>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DataBean>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = line.split("\\s+");
			String telNO = fields[1];
			long up = Long.parseLong(fields[7]);
			long down = Long.parseLong(fields[8]);
			DataBean bean = new DataBean(telNO,up,down);
			context.write(new Text(telNO), bean);
			
		}
	}
	
	public static class DCReducer extends Reducer <Text, DataBean, Text, DataBean> {
		@Override
		protected void reduce(Text key, Iterable<DataBean> value, Reducer<Text, DataBean, Text, DataBean>.Context context)
				throws IOException, InterruptedException {
			long up_sum = 0;
			long down_sum = 0;
			for (DataBean dataBean : value) {
				up_sum += dataBean.getUpPayLoad();
				down_sum += dataBean.getDownPayLoad();
			}
			DataBean bean = new DataBean("",up_sum,down_sum);
			context.write(key, bean);
		}
	}
	
}
