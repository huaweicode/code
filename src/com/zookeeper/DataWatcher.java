package com.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class DataWatcher implements Watcher,Runnable {

	private static final String hostPort = "slave1:2181";
	private static final String zooDataPath = "/chen";
	byte zoo_data[] = null;
	ZooKeeper zk;
	
	public DataWatcher() {
		try {
			zk = new ZooKeeper(hostPort,2000,this);
			if (zk != null) {
				if (zk.exists(zooDataPath, this) == null) {
					zk.create(zooDataPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void printData() {
		try {
			zoo_data = zk.getData(zooDataPath, this, null);
			String data = new String(zoo_data);
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void process(WatchedEvent arg0) {
		
	}

	@Override
	public void run() {
		
	}

}
