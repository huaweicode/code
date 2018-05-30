package com.hadoop;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.junit.Before;
import org.junit.Test;

public class ShowFileStatusTest {
	
	private MiniDFSCluster cluster;
	private FileSystem fs;
	
	
	@Test
	public void setUp() throws IOException {
		Configuration conf = new Configuration();
		if (System.getProperty("test.build.data")==null) {
			System.setProperty("test.bulid.data","/tmp");
		}
		cluster = new MiniDFSCluster(conf,1,true,null);
		fs = cluster.getFileSystem();
		OutputStream out = fs.create(new Path("/dir/file"));
		out.write("content".getBytes("UTF-8"));
		out.close();
		
	}

}
