package com.hadoop;



import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


public class HadoopCilent {

	public static Configuration getClient() {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://master:9000");
		return conf;
	}
	
	/**
	 * hdfs �����ļ�
	 * @return
	 */
	private boolean create() {
		try {
			FileSystem fileSystem = FileSystem.get(getClient());
			if (fileSystem.exists(new Path("/hadoops"))) {
				System.out.println("file is exist");
				return false;
			} else {
				fileSystem.create(new Path("/hadoops"));
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * hdfs �ϴ��ļ�
	 * @return
	 */
	private boolean upload() {
		try {
			FileSystem fileSystem = FileSystem.get(getClient());
			FSDataOutputStream outputStream = fileSystem.create(new Path("/pom.data"),true);
		    FileInputStream inputStream = new FileInputStream("D:\\pom.xml");
		    IOUtils.copyBytes(inputStream, outputStream, 4096, true);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * �о�hdfs�����ļ���Ϣ
	 */
	private void allFile() {
		try {
			FileSystem fileSystem = FileSystem.get(getClient());
			FileStatus[] fileStatus = fileSystem.listStatus(new Path("/"));
			for (FileStatus status : fileStatus) {
				System.out.println(status.getPath());
				System.out.println(status.getPermission());
				System.out.println(status.getReplication());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		HadoopCilent cilents = new HadoopCilent();
//		boolean result = cilent.create();
//		boolean result = cilents.upload();	
//		System.out.println(result);
		cilents.allFile();
	}
}
