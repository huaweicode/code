package com.hadoop.mysql;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class WriteDB {
	
	public static class Map extends MapReduceBase implements
	Mapper<Object, Text, Text, DBRecord> {
		
		private final static DBRecord one = new DBRecord();
		private Text word = new Text();
		@Override
		public void map(Object key, Text value, 
				OutputCollector<Text, DBRecord> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			String[] infos = line.split(" ");
			String id = infos[0].split("	")[1];
			one.setId(new Integer(id));
			one.setName(infos[1]);
			one.setType(infos[2]);
			word.set(id);
			output.collect(word, one);
		}
	}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, DBRecord, DBRecord, Text> {

		@Override
		public void reduce(Text key, Iterator<DBRecord> value, 
				OutputCollector<DBRecord, Text> collector, Reporter reporter)
				throws IOException {
			DBRecord record = value.next();
			collector.collect(record, new Text());
		}
	}
}
