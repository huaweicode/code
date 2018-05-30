package com.hadoop;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

public class HadoopTest {
	
	final static String uri = "hdfs://master:9000";
	
//	public static void main(String[] args) throws Exception {
//		
//		Configuration conf = new Configuration();
//		FileSystem fileSystem = FileSystem.get(URI.create(uri), conf);
//		InputStream inputStream = null;
//		try {
//			 inputStream = fileSystem.open(new Path(uri));
//			IOUtils.copyBytes(inputStream, System.out, 4096, false);
//		} finally {
//			IOUtils.closeStream(inputStream);
//		}
//		
//	}
	@Test
	public void downLoad() throws Exception {
		Configuration conf = new Configuration();
		FileSystem fileSystem = FileSystem.get(URI.create(uri), conf);
		InputStream input = fileSystem.open(new Path("/mysql/data/part-00000"));
		OutputStream output = new FileOutputStream("D:\\");
		IOUtils.copyBytes(input, output, 4096, true);
	}

}
