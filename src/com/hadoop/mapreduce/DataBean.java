package com.hadoop.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class DataBean implements Writable {
	
	private String telNo;//手机号码
	private long upPayLoad;//上行流量
	private long downPayLoad;//下行流量
	private long totalPayLoad;//总流量

	//序列化
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(telNo);
		out.writeLong(upPayLoad);
		out.writeLong(downPayLoad);
		out.writeLong(totalPayLoad);
	}
	//反序列化
	@Override
	public void readFields(DataInput in) throws IOException {
		this.telNo = in.readUTF();
		this.upPayLoad = in.readLong();
		this.downPayLoad = in.readLong();
		this.totalPayLoad = in.readLong();
	}
	
	public DataBean() {
		
	}
	
	public DataBean(String telNo,long upPayLoad,long downPayLoad) {
		this.telNo = telNo;
		this.upPayLoad = upPayLoad;
		this.downPayLoad = downPayLoad;
		this.totalPayLoad = upPayLoad + downPayLoad;
	}
	
	@Override
	public String toString() {
		return this.upPayLoad + " " + this.downPayLoad + " " + this.totalPayLoad;
	}
	
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public long getUpPayLoad() {
		return upPayLoad;
	}
	public void setUpPayLoad(long upPayLoad) {
		this.upPayLoad = upPayLoad;
	}
	public long getDownPayLoad() {
		return downPayLoad;
	}
	public void setDownPayLoad(long downPayLoad) {
		this.downPayLoad = downPayLoad;
	}
	public long getTotalPayLoad() {
		return totalPayLoad;
	}
	public void setTotalPayLoad(long totalPayLoad) {
		this.totalPayLoad = totalPayLoad;
	}
	
}
