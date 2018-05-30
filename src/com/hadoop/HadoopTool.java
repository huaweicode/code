package com.hadoop;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.Progressable;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

public class HadoopTool {
	
	/**
	 * @throws Exception 
	 *��ȡhdfs�ļ�����
	 */
	private void fileOutput() throws Exception {
		String uri = "hdfs://master:9000/file/input.txt";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		InputStream in = null;
		try {
			in = fs.open(new Path(uri));
			IOUtils.copyBytes(in, System.out, 4096 , false);
		} finally {
			IOUtils.closeStream(in);
		}
	}
	
	
	
	/**
	 * �������ļ����Ƶ�hadoopϵͳ
	 * @throws Exception 
	 */
	private void FileCopyWithProgress() throws Exception {
		String localStr = "D:\\BaiduNetdiskDownload\\bigdata\\zookeeper\\zookeeper-3.4.10.tar.gz";
		String dst = "hdfs://master:9000/zookeeper/";
		InputStream in = new BufferedInputStream(new FileInputStream(localStr));
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst),conf);
		OutputStream out = fs.create(new Path(dst), new Progressable() {
			@Override
			public void progress() {
				System.out.print(".");
			}
		});
		IOUtils.copyBytes(in, out, 4096, true);
 	}
	
	/**
	 * ��ʾhdfs�ļ�ϵͳ��һ��·�����ļ���Ϣ
	 * @throws Exception
	 */
//	@Test
	public void ListStatus() throws Exception {
		String[] args = {"/file/","/tmp/","/user/","/zookeeper/"};
		String uri = "hdfs://master:9000/";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		Path[] paths = new Path[args.length];
		for (int i = 0; i < paths.length; i++) {
			paths[i] = new Path(args[i]);
		}
		FileStatus[] fileStatus = fs.listStatus(paths);
		Path[] listPath = FileUtil.stat2Paths(fileStatus);
		for (Path path : listPath) {
			System.out.println(path);
		}
	}
	
	/**
	 * hdfs��ѹ���ļ�
	 * @throws Exception 
	 */
//	@Test
	public void streamCompressor() throws Exception {
		String codeClassName = "hdfs://master:9000/zookeeper/";
		Class<?> codecClass = Class.forName(codeClassName);
		Configuration conf = new Configuration();
		CompressionCodec codec = 
				(CompressionCodec) ReflectionUtils.newInstance(codecClass, conf);
		CompressionOutputStream out = codec.createOutputStream(System.out);
		IOUtils.copyBytes(System.in, out, 4096, false);
		out.finish();
	}
	
	@Test
	public void fileDecompressor() throws Exception {
		String uri = "hdfs://master:9000/user/zookeeper.tar.gz";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		Path inputPath = new Path(uri);
		CompressionCodecFactory factory = new CompressionCodecFactory(conf);
		CompressionCodec codec = factory.getCodec(inputPath);
		if (codec == null) {
			System.err.println("no codec found for:" + uri);
			System.exit(1);
		}
		String outputUri =
				CompressionCodecFactory.removeSuffix(uri, codec.getDefaultExtension());
		InputStream in = null;
		OutputStream out = null;
		try {
			in = codec.createInputStream(fs.open(inputPath));
			out = fs.create(new Path(outputUri));
			IOUtils.copyBytes(in, out, conf);
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
		
		
	}
	
}
