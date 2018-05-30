package com.hadoop.inverseindex;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class InverseIndex {
	
	public static class IndexMapper extends Mapper<LongWritable, Text, Text, Text> {
		
		private Text k = new Text();
		private Text v = new Text();
		@Override 
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\\s+");
			FileSplit inputSplit = (FileSplit) context.getInputSplit();
			String path = inputSplit.getPath().toString();
			for (String w : words) {
				k.set(w + "->" + path);
				v.set("1");
				context.write(k, v);
			}
		}
	}
	
	public static class IndexCombiner extends Reducer<Text, Text, Text, Text> {
		
		private Text k = new Text();
		private Text v = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] wordAndPath = key.toString().split("->");
			String word = wordAndPath[0];
			String path = wordAndPath[1];
			int counter = 0;
			for (Text t : values) {
				counter += Integer.parseInt(t.toString());
			}
			k.set(word);
			v.set(path + "->" + counter);
			context.write(k, v);
		}
	}
	
	public static class IndexReducer extends Reducer<Text, Text, Text, Text> {
		
		private Text v = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String result = "";
			for (Text text : values) {
				result += text.toString() + "\t";
			}
			v.set(result);
			context.write(key, v);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(InverseIndex.class);
	    
		job.setMapperClass(IndexMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setCombinerClass(IndexCombiner.class);
		FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/file/index"));
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(IndexReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/file/out0"));
		
		job.waitForCompletion(true);
	}

}
