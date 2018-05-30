package com.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateZnode implements Watcher {
	
	private static final int SESSION_TIMEOUT = 5000;
	private ZooKeeper zk;
	private CountDownLatch connectedSingal = new CountDownLatch(1);
	
	/**
	 * 连接zookeeper
	 * @param hosts
	 * @throws Exception
	 */
	public void connect(String hosts) throws Exception {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		connectedSingal.await();
	}
	
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSingal.countDown();
		}
	}
	
	public void create(String groupName) throws Exception {
		String path = "/" + groupName;
		String createPath = zk.create(path, "hello".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("created" + createPath);
	}
	
	public void close() throws Exception {
		zk.close();
	}
	
	public static void main(String[] args) throws Exception {
		CreateZnode znode = new CreateZnode();
		try {
			znode.connect("slave1:2181");
			znode.create("yiyajie");
		} finally {
			znode.close();
		}
	}
	

}
