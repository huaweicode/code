package com.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class WordReducer extends Reducer<Text, IntWritable, Text, LongWritable> {
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
		long count = 0;
		for (IntWritable intWritable : values) {
			count += intWritable.get();
		}
		context.write(key, new LongWritable(count));
	}
	

}
